package zorange.online.blogserver.service.impl;

import zorange.online.blogserver.entity.Article;
import zorange.online.blogserver.mapper.ArticleMapper;
import zorange.online.blogserver.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
