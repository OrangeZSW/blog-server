package zorange.online.blogserver.mapper;

import zorange.online.blogserver.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author zorange
 * @since 2024-02-16
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
