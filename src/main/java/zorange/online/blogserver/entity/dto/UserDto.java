package zorange.online.blogserver.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class UserDto {
    private Integer userId;
    private String username;
    private String nickname;
    private String password;
    private String avatar;
    private String coverImg;
    private String description;
    private String announcement;
    private String email;
    private String subHeading;
    private String profession;
    @TableField(exist = false) // 表示该字段不是数据库表中的字段
    private String token;
}
