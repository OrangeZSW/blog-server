package zorange.online.blogserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import zorange.online.blogserver.common.Result;
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

    @Override
    public Object findByUserId(Integer userId, Integer Number, Integer NumberSize) {
        Page<Article> page = new Page<>(Number, NumberSize);
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.orderByDesc("created_at");
        return this.page(page, wrapper);
    }

    @Override
    public Object findAll() {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("created_at");
        return this.list(wrapper);
    }
}
