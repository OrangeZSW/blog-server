package zorange.online.blogserver.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import zorange.online.blogserver.common.Constants;
import zorange.online.blogserver.common.Result;
import zorange.online.blogserver.entity.User;
import zorange.online.blogserver.entity.dto.UserDto;
import zorange.online.blogserver.exception.ServiceException;
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

    @Override
    public Result login(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        wrapper.eq("nickname", user.getNickname());
        wrapper.eq("password", user.getPassword());
        User one = this.getOne(wrapper);
        if(one != null) {
            UserDto userDto = new UserDto();
            System.out.println(one);
            BeanUtil.copyProperties(one, userDto);
            return Result.success(userDto);
        }else{
            throw  new ServiceException(Constants.CODE_PARAM_ERROR,"用户名或密码错误");
        }
    }
}
