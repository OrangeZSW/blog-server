package zorange.online.blogserver.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import zorange.online.blogserver.common.Result;
import zorange.online.blogserver.entity.Dict;
import zorange.online.blogserver.service.IDictService;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zorange
 * @since 2024-04-10
 */
@RestController
@RequestMapping("/dict")
public class DictController {

    @Resource
    private IDictService dictService;

    /**
     * 新增和修改
     *
     * @param dict 实体对象
     * @return Result
     */
    @PostMapping
    public boolean save(@RequestBody Dict dict) {
        return dictService.saveOrUpdate(dict);
    }

    /**
     * 根据id删除
     *
     * @param id 主键id
     * @return boolean
     */
    @DeleteMapping("/{id}")
    public boolean deleteById(@PathVariable Integer id) {
        return dictService.removeById(id);
    }

    /**
     * 查询所有
     *
     * @return List<Dict>
     */
    @GetMapping
    public List<Dict> findAll() {
        return dictService.list();
    }

    /**
     * 根据id查询
     *
     * @param id 主键id
     * @return Dict
     */
    @GetMapping("/{id}")
    public Dict findById(@PathVariable Integer id) {
        return dictService.getById(id);
    }

    /**
     * 分页查询
     *
     * @param pageNum  当前页
     * @param pageSize 每页显示的条数
     * @return Page<Dict>
     */
    @GetMapping("/page")
    public Page<Dict> findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        return dictService.page(new Page<>(pageNum, pageSize), wrapper);
    }

    /**
     * 批量删除
     *
     * @param ids 主键id集合
     * @return boolean
     */
    @DeleteMapping("del/batch")
    public boolean deleteBatchById(@RequestBody List<Integer> ids) {
        return dictService.removeBatchByIds(ids);
    }


    /*
    * 根据type查询
     */
    @GetMapping("/type")
    public Result findByType(@RequestParam String type) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("type", type);
        List<Dict> dict = dictService.list(wrapper);
        return Result.success(dict);
    }

}



