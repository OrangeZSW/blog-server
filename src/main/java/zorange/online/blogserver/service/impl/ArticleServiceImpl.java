package zorange.online.blogserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import zorange.online.blogserver.entity.Article;
import zorange.online.blogserver.mapper.ArticleMapper;
import zorange.online.blogserver.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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

    @Override
    public Object findByUserId(Integer userId, Integer Number, Integer NumberSize) {
        List<String> categoryList = new ArrayList<>();
        List<String> tagList = new ArrayList<>();
        Page<Article> page = new Page<>(Number, NumberSize);
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.orderByDesc("created_at");
        Page<Article> ArticleInfo = this.page(page, wrapper);
        //查询category不为''且唯一的数据
        for (Article article : ArticleInfo.getRecords()) {
            if (!categoryList.contains(article.getCategory()) && !"".equals(article.getCategory())) {
                categoryList.add(article.getCategory());
            }
        }
        //查询tag不为''且唯一的数据
        for (Article article : ArticleInfo.getRecords()) {
            if (!tagList.contains(article.getTag()) && !"".equals(article.getTag())) {
                tagList.add(article.getTag());
            }
        }
        //将category和tag和ArticleInfo放入list中
        return Map.of("category", categoryList, "tag", tagList, "article", ArticleInfo);
    }

    @Override
    public Object findAll(Integer Number, Integer NumberSize) {
        List<String> categoryList = new ArrayList<>();
        List<String> tagList = new ArrayList<>();
        Page<Article> page = new Page<>(Number, NumberSize);
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("created_at");
        Page<Article> ArticleInfo = this.page(page, wrapper);
        //查询category不为''且唯一的数据
        for (Article article : ArticleInfo.getRecords()) {
            if (!categoryList.contains(article.getCategory()) && !"".equals(article.getCategory())) {
                categoryList.add(article.getCategory());
            }
        }
        //查询tag不为''且唯一的数据
        for (Article article : ArticleInfo.getRecords()) {
            if (!tagList.contains(article.getTag()) && !"".equals(article.getTag())) {
                tagList.add(article.getTag());
            }
        }
        //将category和tag和ArticleInfo放入list中
        return Map.of("category", categoryList, "tag", tagList, "article", ArticleInfo);
    }

}
