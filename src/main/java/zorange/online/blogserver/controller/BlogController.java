package zorange.online.blogserver.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import zorange.online.blogserver.entity.Blog;
import zorange.online.blogserver.service.IBlogService;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 博客表 前端控制器
 * </p>
 *
 * @author zorange
 * @since 2024-02-16
 */
@RestController
@RequestMapping("/blog")
        public class BlogController {
    
@Resource
private IBlogService blogService;

/**
 * 新增和修改
 * @param blog 实体对象
 * @return Result
 */
@PostMapping
public boolean save(@RequestBody Blog blog) {
        return blogService.saveOrUpdate(blog);
        }

/**
* 根据id删除
* @param id 主键id
* @return boolean
*/
@DeleteMapping("/{id}")
public boolean deleteById(@PathVariable Integer id) {
        return blogService.removeById(id);
        }

/**
 * 查询所有
 * @return List<Blog>
 */
@GetMapping
public List<Blog> findAll() {
        return blogService.list();
        }

/**
 * 根据id查询
 * @param id 主键id
 * @return Blog
 */
@GetMapping("/{id}")
public Blog findById(@PathVariable Integer id) {
        return blogService.getById(id);
        }

/**
 * 分页查询
 * @param pageNum 当前页
 * @param pageSize 每页显示的条数
 * @return Page<Blog>
 */
@GetMapping("/page")
public Page<Blog> findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        QueryWrapper<Blog> wrapper=new QueryWrapper<>();
        return blogService.page(new Page<>(pageNum, pageSize), wrapper);
        }

/**
* 批量删除
* @param ids 主键id集合
* @return boolean
*/
@DeleteMapping("del/batch")
public boolean deleteBatchById(@RequestBody List<Integer> ids) {
        return blogService.removeBatchByIds(ids);
        }


        }

