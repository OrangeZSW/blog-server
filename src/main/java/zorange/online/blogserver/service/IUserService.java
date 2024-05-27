package zorange.online.blogserver.service;

import zorange.online.blogserver.common.Result;
import zorange.online.blogserver.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import zorange.online.blogserver.entity.dto.UserDto;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author zorange
 * @since 2024-02-16
 */
public interface IUserService extends IService<User> {

    UserDto login(User user);

    Object findByNickname(String nickname);

    Object findByUsername(String username);

    Object checkToken(String token);
}
