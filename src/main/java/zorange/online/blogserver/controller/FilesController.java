package zorange.online.blogserver.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import org.springframework.web.multipart.MultipartFile;
import zorange.online.blogserver.common.Result;
import zorange.online.blogserver.entity.Files;
import zorange.online.blogserver.service.IFilesService;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zorange
 * @since 2024-02-22
 */
@RestController
@RequestMapping("/files")
public class FilesController {

    @Resource
    private IFilesService filesService;


    @PostMapping("/upload/article")
    public Result uploadArticle(@RequestParam MultipartFile file) throws IOException {
        return Result.success(filesService.uploadArticle(file));
    }

    @PostMapping("/upload/img")
    public Result uploadImg(@RequestParam MultipartFile file) throws IOException {
        return Result.success(filesService.uploadImg(file));
    }

    @GetMapping("/download"+"/{fileUuid}")
    public Result download(@PathVariable String fileUuid, HttpServletResponse response) throws IOException {
        return Result.success(filesService.download(fileUuid, response));
    }

    /**
     * 新增和修改
     *
     * @param files 实体对象
     * @return Result
     */
    @PostMapping
    public boolean save(@RequestBody Files files) {
        return filesService.saveOrUpdate(files);
    }

    /**
     * 根据id删除
     *
     * @param id 主键id
     * @return boolean
     */
    @DeleteMapping("/{id}")
    public boolean deleteById(@PathVariable Integer id) {
        return filesService.removeById(id);
    }

    /**
     * 查询所有
     *
     * @return List<Files>
     */
    @GetMapping
    public List<Files> findAll() {
        return filesService.list();
    }

    /**
     * 根据id查询
     *
     * @param id 主键id
     * @return Files
     */
    @GetMapping("/{id}")
    public Files findById(@PathVariable Integer id) {
        return filesService.getById(id);
    }

    /**
     * 分页查询
     *
     * @param pageNum  当前页
     * @param pageSize 每页显示的条数
     * @return Page<Files>
     */
    @GetMapping("/page")
    public Page<Files> findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        QueryWrapper<Files> wrapper = new QueryWrapper<>();
        return filesService.page(new Page<>(pageNum, pageSize), wrapper);
    }

    /**
     * 批量删除
     *
     * @param ids 主键id集合
     * @return boolean
     */
    @DeleteMapping("del/batch")
    public boolean deleteBatchById(@RequestBody List<Integer> ids) {
        return filesService.removeBatchByIds(ids);
    }

    @PostMapping("/updateArticle")
    public Result updateArticle(@RequestParam("file") MultipartFile file,@RequestParam("url") String url) throws IOException {

        return Result.success(filesService.updateArticle(file,url));
    }


}

