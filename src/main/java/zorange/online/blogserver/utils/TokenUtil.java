package zorange.online.blogserver.utils;


import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import zorange.online.blogserver.common.Constants;
import zorange.online.blogserver.entity.User;
import zorange.online.blogserver.exception.ServiceException;
import zorange.online.blogserver.service.IUserService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class TokenUtil {

    private static IUserService staticUserService;
    @Resource  //注入userService
    private IUserService userService;
    @PostConstruct  //在构造方法执行之后执行该方法
    public void setUserService() {
        staticUserService = userService;
    }

    /***
     * 生成token
     */
    public static String genToken(User user) {
        return JWT.create().withAudience(String.valueOf(user.getUserId()))   //将user id保存在token里面，作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2))  //2小时token失效
                .sign(Algorithm.HMAC256(user.getPassword()));  //将password  作为token密钥
    }
    /***
     * 获取当前登录用户
     * @return User
     */
    public static User getCurrentUser() {
        //获取当前登录用户的head
        String userId = null;
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            userId = JWT.decode(token).getAudience().get(0);
        } catch (ServiceException e) {
            throw new ServiceException(Constants.CODE_ERROR, "获取当前登录用户失败");
        }
        return staticUserService.getById(userId);
    }


}
