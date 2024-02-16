package zorange.online.blogserver.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import zorange.online.blogserver.entity.Article;
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
 * 新增和修改
 * @param article 实体对象
 * @return Result
 */
@PostMapping
public boolean save(@RequestBody Article article) {
        return articleService.saveOrUpdate(article);
        }

/**
* 根据id删除
* @param id 主键id
* @return boolean
*/
@DeleteMapping("/{id}")
public boolean deleteById(@PathVariable Integer id) {
        return articleService.removeById(id);
        }

/**
 * 查询所有
 * @return List<Article>
 */
@GetMapping
public List<Article> findAll() {
        return articleService.list();
        }

/**
 * 根据id查询
 * @param id 主键id
 * @return Article
 */
@GetMapping("/{id}")
public Article findById(@PathVariable Integer id) {
        return articleService.getById(id);
        }

/**
 * 分页查询
 * @param pageNum 当前页
 * @param pageSize 每页显示的条数
 * @return Page<Article>
 */
@GetMapping("/page")
public Page<Article> findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        QueryWrapper<Article> wrapper=new QueryWrapper<>();
        return articleService.page(new Page<>(pageNum, pageSize), wrapper);
        }

/**
* 批量删除
* @param ids 主键id集合
* @return boolean
*/
@DeleteMapping("del/batch")
public boolean deleteBatchById(@RequestBody List<Integer> ids) {
        return articleService.removeBatchByIds(ids);
        }


        }

