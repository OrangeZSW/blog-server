package zorange.online.blogserver.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import zorange.online.blogserver.entity.File;
import zorange.online.blogserver.service.IFileService;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zorange
 * @since 2024-02-22
 */
@RestController
@RequestMapping("/file")
        public class FileController {
    
@Resource
private IFileService fileService;

/**
 * 新增和修改
 * @param file 实体对象
 * @return Result
 */
@PostMapping
public boolean save(@RequestBody File file) {
        return fileService.saveOrUpdate(file);
        }

/**
* 根据id删除
* @param id 主键id
* @return boolean
*/
@DeleteMapping("/{id}")
public boolean deleteById(@PathVariable Integer id) {
        return fileService.removeById(id);
        }

/**
 * 查询所有
 * @return List<File>
 */
@GetMapping
public List<File> findAll() {
        return fileService.list();
        }

/**
 * 根据id查询
 * @param id 主键id
 * @return File
 */
@GetMapping("/{id}")
public File findById(@PathVariable Integer id) {
        return fileService.getById(id);
        }

/**
 * 分页查询
 * @param pageNum 当前页
 * @param pageSize 每页显示的条数
 * @return Page<File>
 */
@GetMapping("/page")
public Page<File> findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        QueryWrapper<File> wrapper=new QueryWrapper<>();
        return fileService.page(new Page<>(pageNum, pageSize), wrapper);
        }

/**
* 批量删除
* @param ids 主键id集合
* @return boolean
*/
@DeleteMapping("del/batch")
public boolean deleteBatchById(@RequestBody List<Integer> ids) {
        return fileService.removeBatchByIds(ids);
        }


        }

