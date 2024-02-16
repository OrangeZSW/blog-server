package zorange.online.blogserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 博客表
 * </p>
 *
 * @author zorange
 * @since 2024-02-16
 */
@Getter
@Setter
  @ApiModel(value = "Blog对象", description = "博客表")
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("博客ID，主键")
        @TableId(value = "blog_id", type = IdType.AUTO)
      private Integer blogId;

      @ApiModelProperty("用户ID，外键关联User表")
      private Integer userId;

      @ApiModelProperty("博客标题")
      private String title;

      @ApiModelProperty("博客描述")
      private String description;

      @ApiModelProperty(" 创建时间")
      private LocalDateTime createdAt;

      @ApiModelProperty("最后更新时间")
      private LocalDateTime lastUpdatedAt;


}
