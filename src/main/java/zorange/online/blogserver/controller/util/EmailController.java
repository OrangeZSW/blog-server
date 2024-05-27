package zorange.online.blogserver.controller.util;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.extra.mail.MailUtil;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zorange.online.blogserver.common.Result;
import zorange.online.blogserver.controller.UserController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Resource
    private UserController userController;
    private static String EMAIL= "email";

    private static String EMAIL_CODE = "email_code";
    //验证码过期时间（半个小时）
    private static DateTime EMAIL_CODE_EXPIRE_TIME = DateTime.now();




    /*
    * 发送验证码
    * @param email 邮箱
     */
    @GetMapping("/code")
    public Result sendEmailCode(@RequestParam String email) {
        EMAIL = email;
        // 自定义纯数字的验证码（随机4位数字，可重复）
        RandomGenerator randomGenerator = new RandomGenerator("0123456789", 4);
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        lineCaptcha.setGenerator(randomGenerator);
        // 重新生成code
        String code = lineCaptcha.getCode();
        EMAIL_CODE = code;
        DateTime now = DateTime.now();
        EMAIL_CODE_EXPIRE_TIME = now.offset(DateField.MINUTE, 30);
        System.out.println(EMAIL_CODE_EXPIRE_TIME);

        String subject = "Orange Blog 邮箱验证码";
        String content = "您的验证码是：" + code;
        MailUtil.send(email,subject, content, false);
        return Result.success();
    }

    /*
    * 验证验证码
    * @param code 验证
     */
    @GetMapping("/code/verifyEmail")
    public Result verifyEmailCode(@RequestParam String code,@RequestParam String email) {
            if(!EMAIL.equals(email)){
                return Result.error("请先发送验证码");
            }else if(!EMAIL_CODE.equals(code)){
                return Result.error("验证码错误");
            }else if(DateTime.now().isAfter(EMAIL_CODE_EXPIRE_TIME)){
                return Result.error("验证码已过期");
            }
            //清空验证码
            EMAIL = "email";
            EMAIL_CODE = "email_code";
            return Result.success();
    }

    @GetMapping("/code/verifyPassword")
    public Result verifyPassword(@RequestParam String password,@RequestParam String code,@RequestParam String userId) {
        if(EMAIL_CODE.equals("email_code")){
            return Result.error("请先发送验证码");
        }else
       if(!EMAIL_CODE.equals(code)){
           return Result.error("验证码错误");
         }
       else if(DateTime.now().isAfter(EMAIL_CODE_EXPIRE_TIME)){
           return Result.error("验证码已过期");
         }
        //修改密码
        userController.updatePassword(password,userId);
        //清空验证码
        EMAIL_CODE = "email_code";

        return Result.success("修改成功");
    }
}
