package zorange.online.blogserver.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import zorange.online.blogserver.common.Constants;
import zorange.online.blogserver.common.Result;
import zorange.online.blogserver.entity.Files;
import zorange.online.blogserver.mapper.FilesMapper;
import zorange.online.blogserver.service.IFilesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import zorange.online.blogserver.exception.ServiceException;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zorange
 * @since 2024-02-22
 */
@Service
public class FilesServiceImpl extends ServiceImpl<FilesMapper, Files> implements IFilesService {

    @Value("${files.upload.article}")
    private String uploadArticle;
    @Value("${files.upload.img}")
    private String uploadImg;
    @Value("${server.ip}")
    private String serverIp;

    private ArticleServiceImpl articleService;

    @Override
    public String uploadArticle(MultipartFile file) throws IOException {
        //获取文件名
        String originalFilename = file.getOriginalFilename();
        //获取文件类型
        String type = FileUtil.extName(originalFilename);
        //获取文件大小
        long size = file.getSize();
        //获取文件的父目录
        File upLoadParentFile = new File(uploadArticle);
        //判断父目录是否存在
        if (!upLoadParentFile.exists()) {
            upLoadParentFile.mkdirs();
        }
        //定义一个文件的唯一的一个标识码
        String uuid = IdUtil.fastSimpleUUID();
        //文件的唯一标识码+文件的后缀
        String FileUuid = uuid + "." + type;
        //实际上传文件的路径
        File upLoadArticle = new File(uploadArticle + FileUuid);
        //获取文件的md5,用于判断文件是否存在
        String md5 = SecureUtil.md5(file.getInputStream());
        //文件的访问路径
        String Url;
        //判断文件是否存在,如果存在,则直接返回文件的访问路径
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5", md5);
        List<Files> filesList =this.list(queryWrapper);
        if (filesList.isEmpty()) {
            //将文件写入到指定的路径
            file.transferTo(upLoadArticle);
            //文件的访问路径
            Url = serverIp + "/files/download/" + FileUuid;
        } else {
           return   filesList.get(0).getUrl();
        }
        //将文件信息保存到数据库中
        Files saveFile = new Files();
        saveFile.setName(originalFilename);
        saveFile.setType(type);
        saveFile.setSize(size / 1024);
        saveFile.setUrl(Url);
        saveFile.setMd5(md5);
        this.save(saveFile);

        return Url;
    }

    @Override
    public String uploadImg(MultipartFile file) throws IOException {
        //获取文件名
        String originalFilename = file.getOriginalFilename();
        //获取文件类型
        String type = FileUtil.extName(originalFilename);
        //判断是不是图片
        if (!"jpg".equals(type) && !"png".equals(type) && !"jpeg".equals(type) && !"gif".equals(type)) {
            throw new ServiceException(Constants.CODE_NOT_LOGIN,"文件类型不正确");
        }
        //获取文件大小
        long size = file.getSize();
        //获取文件的父目录
        File upLoadParentFile = new File(uploadImg);
        //判断父目录是否存在
        if (!upLoadParentFile.exists()) {
            upLoadParentFile.mkdirs();
        }
        //定义一个文件的唯一的一个标识码
        String uuid = IdUtil.fastSimpleUUID();
        //文件的唯一标识码+文件的后缀
        String FileUuid = uuid + "." + type;
        //实际上传文件的路径
        File upLoadImg = new File(uploadImg + FileUuid);
        //获取文件的md5,用于判断文件是否存在
        String md5 = SecureUtil.md5(file.getInputStream());
        //文件的访问路径
        String Url;
        //判断文件是否存在,如果存在,则直接返回文件的访问路径
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5", md5);
        List<Files> filesList =this.list(queryWrapper);
        if (filesList.isEmpty()) {
            //将文件写入到指定的路径
            file.transferTo(upLoadImg);
            //文件的访问路径
            Url = serverIp + "/files/download/" + FileUuid;
        } else {
            return   filesList.get(0).getUrl();
        }
        //将文件信息保存到数据库中
        Files saveFile = new Files();
        saveFile.setName(originalFilename);
        saveFile.setType(type);
        saveFile.setSize(size / 1024);
        saveFile.setUrl(Url);
        saveFile.setMd5(md5);
        this.save(saveFile);
        return Url;
    }

    @Override
    public Result download(String fileUuid, HttpServletResponse response) throws IOException {

        System.out.println(fileUuid);
        //判断文件的类型
        //获取文件信息
        String url= serverIp + "/files/download/" + fileUuid;
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url", url);
        Files one = this.getOne(queryWrapper);
        String type = FileUtil.extName(fileUuid);
        //获取文件的路径
        String filePath;
        if ("jpg".equals(type) || "png".equals(type) || "jpeg".equals(type) || "gif".equals(type)) {
            filePath = uploadImg + fileUuid;
        } else {
            filePath = uploadArticle + fileUuid;
        }
        //获取文件
        File file = new File(filePath);
       // 当为图片时
        if ("jpg".equals(type) || "png".equals(type) || "jpeg".equals(type) || "gif".equals(type)) {
            response.setContentType("image/jpeg");
        } else {
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(one.getName(), StandardCharsets.UTF_8));
        }
        //获取文件输入流
        InputStream inputStream = FileUtil.getInputStream(file);
        //获取输出流
        ServletOutputStream outputStream = response.getOutputStream();
        //将文件写入到输出流
        byte[] bytes = new byte[1024];
        int len;
        while ((len = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, len);
        }
        //关闭流
        inputStream.close();
        outputStream.close();
        return Result.success();

    }

    @Override
    public Object updateArticle(MultipartFile file, String url) throws IOException {
        String Filename = file.getOriginalFilename();
        String size = String.valueOf(file.getSize());
        String md5 = SecureUtil.md5(file.getInputStream());
        String uuid = url.substring(url.lastIndexOf("/") + 1);

        String filepath = uploadArticle + uuid;
        File oldFile = new File(filepath);
        //修改文件内容
        try {
            file.transferTo(oldFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //修改文件信息
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url", url);
        Files files = this.getOne(queryWrapper);
        files.setName(Filename);
        files.setSize(Long.parseLong(size));
        files.setMd5(md5);
        this.updateById(files);
        return true;
    }
}
