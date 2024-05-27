package zorange.online.blogserver.service;

import org.springframework.web.multipart.MultipartFile;
import zorange.online.blogserver.common.Result;
import zorange.online.blogserver.entity.Files;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zorange
 * @since 2024-02-22
 */
public interface IFilesService extends IService<Files> {

    String uploadArticle(MultipartFile file) throws IOException;

    String uploadImg(MultipartFile file) throws IOException;

    Result download(String fileUuid, HttpServletResponse response) throws IOException;

    Object updateArticle(MultipartFile file,String url) throws IOException;
}
