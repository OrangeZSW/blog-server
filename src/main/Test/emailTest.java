import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.extra.mail.MailUtil;


public class emailTest{
    public static void main(String[] args) {

        // 发送简单邮件
//        MailUtil.send("3166483606@qq.com", "测试", "邮件来自Hutool测试", false);



        // 自定义纯数字的验证码（随机4位数字，可重复）
        RandomGenerator randomGenerator = new RandomGenerator("0123456789", 4);
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        lineCaptcha.setGenerator(randomGenerator);
        // 重新生成code
        System.out.println(lineCaptcha.getCode());
    }
}
