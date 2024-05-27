package zorange.online.blogserver.controller;


import cn.hutool.Hutool;
import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import zorange.online.blogserver.common.Result;
import zorange.online.blogserver.entity.Article;
import zorange.online.blogserver.entity.dto.Search;
import zorange.online.blogserver.service.IArticleService;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 文章表 前端控制器
 * </p>
 *
 * @author zorange
 * @since 2024-02-16
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private IArticleService articleService;


    /**
     * 根据用户id查询
     *
     * @param userId 用户id
     * @return Result
     */
    @GetMapping("/userId/{userId}")
    public Result findByUserId(@PathVariable Integer userId,@RequestParam Integer Number, @RequestParam Integer NumberSize) {
        return Result.success(articleService.findByUserId(userId,Number,NumberSize));
    }
    /**
     * 新增和修改
     *
     * @param article 实体对象
     * @return Result
     */
    @PostMapping
    public Result save(@RequestBody Article article) {
        //设置创建时间
        if (article.getArticleId() == null){
            article.setCreatedAt(new Timestamp(new Date().getTime()).toLocalDateTime());
            System.out.println("创建时间"+article.getCreatedAt());
        }
        //设置最后更新时间
        article.setLastUpdatedAt(new Timestamp(new Date().getTime()).toLocalDateTime());
        articleService.saveOrUpdate(article);
        //获取文章id
        return Result.success(article.getArticleId());
    }

    /**
     * 根据id删除
     *
     * @param id 主键id
     * @return boolean
     *
     */
    @DeleteMapping("/{id}")
    public boolean deleteById(@PathVariable Integer id) {
        return articleService.removeById(id);
    }

    /**
     * 查询所有
     *
     * @return List<Article>
     */
    @GetMapping
    public Result findAll(@RequestParam Integer Number, @RequestParam Integer NumberSize) {
        return Result.success(articleService.findAll(Number,NumberSize));
    }

    /**
     * 根据id查询
     *
     * @param id 主键id
     * @return Article
     */
    @GetMapping("/{id}")
    public Article findById(@PathVariable Integer id) {
        return articleService.getById(id);
    }

    /**
     * 分页查询
     *
     * @param pageNum  当前页
     * @param pageSize 每页显示的条数
     * @return Page<Article>
     */
    @GetMapping("/page")
    public Page<Article> findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        return articleService.page(new Page<>(pageNum, pageSize), wrapper);
    }

    /**
     * 批量删除
     *
     * @param ids 主键id集合
     * @return boolean
     */
    @DeleteMapping("del/batch")
    public boolean deleteBatchById(@RequestBody List<Integer> ids) {
        return articleService.removeBatchByIds(ids);
    }

    /*
    * 根据文章id查询用户id
     */
    @GetMapping("/userIdByArticleId/{articleId}")
    public Result findUserIdByArticleId(@PathVariable Integer articleId) {
        return Result.success(articleService.getById(articleId).getUserId());
    }


    /**
     * 搜索
     *
     * @param search 搜索条件
     * @return Result
     */
    @PostMapping("/search")
    public Result search(@RequestBody Search search) {
        System.out.println(search);
        return Result.success(articleService.search(search));
    }

    @GetMapping("/recommendation")
    public Result getRecommendation(@RequestParam String userId) {
        return Result.success(articleService.getRecommendation(userId));
    }

    //假删除文章
    @GetMapping("/delete/{articleId}")
    public Result deleteArticle(@PathVariable Integer articleId){
        Article article = articleService.getById(articleId);
        article.setIsDelete(true);
        articleService.saveOrUpdate(article);
        return Result.success();
    }

}

