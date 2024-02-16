package zorange.online.blogserver.mapper;

import zorange.online.blogserver.entity.Blog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 博客表 Mapper 接口
 * </p>
 *
 * @author zorange
 * @since 2024-02-16
 */
@Mapper
public interface BlogMapper extends BaseMapper<Blog> {

}
