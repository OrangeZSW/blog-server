package zorange.online.blogserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author zorange
 * @since 2024-02-16
 */
@Getter
@Setter
  @ApiModel(value = "User对象", description = "用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("用户ID，主键")
        @TableId(value = "user_id", type = IdType.AUTO)
      private Integer userId;

      @ApiModelProperty("用户名")
      private String username;

      @ApiModelProperty("密码（加密存储）")
      private String password;

      @ApiModelProperty("电子邮件")
      private String email;

      @ApiModelProperty("昵称")
      private String nickname;

      @ApiModelProperty("头像图片URL")
      private String avatar;

      @ApiModelProperty("注册时间")
      private LocalDateTime registrationTime;

      @ApiModelProperty("最后登录时间")
      private LocalDateTime lastLoginTime;

      @ApiModelProperty("封面图片URL")
      private String coverImg;

      @ApiModelProperty("博客描述")
      private String description;

      @ApiModelProperty("公告")
      private String announcement;

      @ApiModelProperty("副标题")
      private String subHeading;

        @ApiModelProperty("职业")
      private String profession;


}
