package zorange.online.blogserver.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import zorange.online.blogserver.common.Constants;
import zorange.online.blogserver.common.Result;
import zorange.online.blogserver.entity.User;
import zorange.online.blogserver.entity.dto.UserDto;
import zorange.online.blogserver.exception.ServiceException;
import zorange.online.blogserver.mapper.UserMapper;
import zorange.online.blogserver.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import zorange.online.blogserver.utils.TokenUtil;

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


    /**
     * 登录
     * @param user
     */
    @Override
    public UserDto login(User user) {
        //查询用户
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        // 查看昵称是否存在
        wrapper.eq("nickname", user.getNickname());
        // 查看密码是否存在
        wrapper.eq("password", user.getPassword());
        User one = this.getOne(wrapper);
        // 如果存在用户
        if(one != null) {
            UserDto userDto = new UserDto();
//            System.out.println(one);
            // 生成token
            userDto.setToken(TokenUtil.genToken(one));
            // 复制属性
            BeanUtil.copyProperties(one, userDto);
            return userDto;
        }else{
            throw  new ServiceException(Constants.CODE_PARAM_ERROR,"用户名或密码错误");
        }
    }

    @Override
    public Object findByNickname(String nickname) {
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        wrapper.eq("nickname", nickname);
        User one = this.getOne(wrapper);
        if(one != null) {
            return true;
        }
        return false;
    }

    @Override
    public Object findByUsername(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        wrapper.eq("username", username);
        User one = this.getOne(wrapper);
        if(one != null) {
            return true;
        }
        return false;
    }

    @Override
    public Object checkToken(String token) {
        //执行认证
        if (token == null || token.isEmpty()) {
            throw new ServiceException(Constants.CODE_NOT_LOGIN, "无token,请重新登录");
        }
        //获取token中的用户id
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new ServiceException(Constants.CODE_NOT_LOGIN, "token验证失败，请重新登录");
        }
        //验证
        User user = this.getById(userId);
        if (user == null) {
            throw new ServiceException(Constants.CODE_NOT_LOGIN, "用户不存在，请重新登录");
        }

        //验证token,用户密码加签
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException j) {
            throw new ServiceException(Constants.CODE_NOT_LOGIN, "token验证失败,请重新登录");
        }
        return Constants.CODE_SUCCESS;
    }
}
