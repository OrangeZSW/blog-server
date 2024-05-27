package zorange.online.blogserver.config.Interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import zorange.online.blogserver.common.Constants;
import zorange.online.blogserver.entity.User;
import zorange.online.blogserver.exception.ServiceException;
import zorange.online.blogserver.service.IUserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private IUserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        //如果不是映射到方法就直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //执行认证
        if (token==null||token.equals("")) {
            throw new ServiceException(Constants.CODE_NOT_LOGIN, "无token,请登录");
        }
        //获取token中的用户id
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new ServiceException(Constants.CODE_NOT_LOGIN, "token验证失败,请重新登录");
        }
        //验证
        User user = userService.getById(userId);
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

        return HandlerInterceptor.super.preHandle(request, response, handler);

    }
}
