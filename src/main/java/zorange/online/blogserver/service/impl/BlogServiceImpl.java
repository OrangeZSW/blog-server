package zorange.online.blogserver.service.impl;

import zorange.online.blogserver.entity.Blog;
import zorange.online.blogserver.mapper.BlogMapper;
import zorange.online.blogserver.service.IBlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 博客表 服务实现类
 * </p>
 *
 * @author zorange
 * @since 2024-02-16
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements IBlogService {

}
