package zorange.online.blogserver.mapper;

import zorange.online.blogserver.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 文章表 Mapper 接口
 * </p>
 *
 * @author zorange
 * @since 2024-02-16
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

}
