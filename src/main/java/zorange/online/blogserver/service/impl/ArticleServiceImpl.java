package zorange.online.blogserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import zorange.online.blogserver.common.Result;
import zorange.online.blogserver.controller.DictController;
import zorange.online.blogserver.entity.Article;
import zorange.online.blogserver.entity.Dict;
import zorange.online.blogserver.entity.dto.ArticleInfo;
import zorange.online.blogserver.entity.dto.Search;
import zorange.online.blogserver.mapper.ArticleMapper;
import zorange.online.blogserver.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文章表 服务实现类
 * </p>
 *
 * @author zorange
 * @since 2024-02-16
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

    @Resource
    private UserServiceImpl userService;

    @Resource
    private DictServiceImpl dictService;


    //    处理得到的articles数据，将category和tag和ArticleInfo放入list中
    public ArticleInfo getArticleInfo(Page<Article> Articles) {
        List<String> categoryList = new ArrayList<>();
        List<String> tagList = new ArrayList<>();
        for (Article article : Articles.getRecords()) {
            if (!categoryList.contains(article.getCategory()) && !"".equals(article.getCategory())) {
                categoryList.add(article.getCategory());
            }
        }
        //查询tag不为''且唯一的数据
        for (Article article : Articles.getRecords()) {
            if (!tagList.contains(article.getTag()) && !"".equals(article.getTag())) {
                tagList.add(article.getTag());
            }
        }
        ArticleInfo articleInfo = new ArticleInfo();

        articleInfo.setArticles(Articles);
        articleInfo.setCategory(categoryList);
        articleInfo.setTag(tagList);

        return articleInfo;
    }

    @Override
    public Object findByUserId(Integer userId, Integer Number, Integer NumberSize) {

        Page<Article> page = new Page<>(Number, NumberSize);
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.and(i -> i.eq("is_delete", false));
        wrapper.orderByDesc("created_at");

        Page<Article> Articles = this.page(page, wrapper);
        //查询category不为''且唯一的数据

        //将category和tag和ArticleInfo放入list中
        return getArticleInfo(Articles);
    }

    @Override
    public Object findAll(Integer Number, Integer NumberSize) {
        Page<Article> page = new Page<>(Number, NumberSize);
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.and(i -> i.eq("is_delete", false));
        wrapper.orderByDesc("last_updated_at");
        Page<Article> Articles = this.page(page, wrapper);
        //查询category不为''且唯一的数据
        return getArticleInfo(Articles);
    }

    @Override
    public Object search(Search key) {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();


        //当title,category,tag不为“”时，模糊查询
        if (!key.getTitle().isBlank()) {
            wrapper.like("title", key.getTitle());
        }
        if (!key.getCategory().isBlank()) {
            wrapper.or().like("category", key.getCategory());
        }
        if (!key.getTag().isBlank()) {
            wrapper.or().like("tag", key.getTag());
        }
        if (!key.getUserId().isBlank()) {
            wrapper.eq("user_id", key.getUserId());
        }

        //没有删除的文章
        wrapper.and(i -> i.eq("is_delete", false));

        Integer number = key.getNumber();
        Integer numberSize = key.getNumberSize();

        wrapper.orderByDesc("last_updated_at");
        Page<Article> Articles = this.page(new Page<>(number, numberSize), wrapper);
        return getArticleInfo(Articles);
    }

    //    获取推荐文章
    //   1.没有登录，随机获取两篇文章
    //   2.登录了，根据用户职业和最新一篇文章的tag获取推荐文章
    //   3.如果推荐文章不足两篇，从所有文章中获取
    //   4.返回两篇文章
    @Override
    public Object getRecommendation(String userId) {
        Search search = new Search();
        List<Article> articles = new ArrayList<>();
        search.setNumber(1);
        search.setNumberSize(2);

//      没有登录
        if (userId.isBlank()) {
//            查询随机两篇文章
            QueryWrapper<Article> wrapper = new QueryWrapper<>();
            wrapper.orderByAsc("rand()");
            wrapper.last("limit 2");
            return this.list(wrapper);
        }



//        登录了
        //查询用户职业,根据职业查询文章
        String profession = userService.getById(userId).getProfession();
        //查询字典该职业的所有推荐标签
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("type", "recommendation");
        wrapper.eq("name", profession);
        List<Dict> tag = dictService.list(wrapper);
        //循环遍历tag，获取关键字后搜索
        List<Article> professionArticles = new ArrayList<>();

        for (Dict dict : tag) {
            search.setTitle(dict.getValue());
            search.setTag(dict.getValue());
            search.setCategory(dict.getValue());
            QueryWrapper<Article> wrapper1 = new QueryWrapper<>();
            wrapper1.like("title", dict.getValue()).ne("user_id", userId).or().like("tag", dict.getValue()).ne("user_id", userId).or().like("category", dict.getValue()).ne("user_id", userId);


            professionArticles.addAll(this.list(wrapper1));
        }



        //查询用户最新一篇文章tag，如果tag为空则找下一篇文章的tag
         QueryWrapper<Article> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("user_id", userId);
        List<Article> articles1 = this.list(wrapper2);

        for (Article article : articles1) {
            if (!article.getTag().isBlank()) {
                search.setTag(article.getTag());
                break;
            }
        }
        List<Article> userTagArticles = new ArrayList<>();
        //搜索
        QueryWrapper<Article> wrapper3 = new QueryWrapper<>();
        wrapper3.like("tag", search.getTag()).ne("user_id", userId);
        userTagArticles.addAll(this.list(wrapper3));





        //判断 professionArticles和userTagArticles的文章数量，保证最终返回的文章数量为2，如果不足则从所有文章中获取
        if(!professionArticles.isEmpty()){
            //随机获取一篇文章
            articles.add(professionArticles.get((int) (Math.random() * professionArticles.size())));
        }
        if(!userTagArticles.isEmpty()){
            //随机获取一篇文章
            articles.add(userTagArticles.get((int) (Math.random() * userTagArticles.size())));
        }
        //判断文章数量是否为2，
        if(articles.size() == 2){
            return articles;
        }else{
            if(professionArticles.size()>=2){
                //随机获取一篇文章
                articles.add(professionArticles.get((int) (Math.random() * professionArticles.size())));
            }
        }
        //判断文章数量是否为2，
        if(articles.size() == 2){
            return articles;
        }else{
            if(userTagArticles.size()>=2){
                //随机获取一篇文章
                articles.add(userTagArticles.get((int) (Math.random() * userTagArticles.size())));
            }
        }

        //判断文章数量是否为2，
        while (articles.size() < 2) {
            //随机获取一篇文章，userid不等于当前用户
            QueryWrapper<Article> wrapper4 = new QueryWrapper<>();
            wrapper4.ne("user_id", userId);
            wrapper4.orderByAsc("rand()");
            wrapper4.last("limit 1");
            articles.add(this.getOne(wrapper4));
        }

        return articles;
    }
}
