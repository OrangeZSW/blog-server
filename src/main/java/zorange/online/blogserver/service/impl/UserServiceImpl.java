package zorange.online.blogserver.service.impl;

import zorange.online.blogserver.entity.User;
import zorange.online.blogserver.mapper.UserMapper;
import zorange.online.blogserver.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author zorange
 * @since 2024-02-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
