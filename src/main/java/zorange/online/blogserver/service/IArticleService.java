package zorange.online.blogserver.service;

import zorange.online.blogserver.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 文章表 服务类
 * </p>
 *
 * @author zorange
 * @since 2024-02-16
 */
public interface IArticleService extends IService<Article> {

    Object findByUserId(Integer userId);

    Object findAll();
}
