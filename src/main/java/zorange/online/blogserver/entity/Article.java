package zorange.online.blogserver.entity;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.beans.Transient;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 文章表
 * </p>
 *
 * @author zorange
 * @since 2024-02-16
 */
@Getter
@Setter
  @ApiModel(value = "Article对象", description = "文章表")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("文章ID，主键")
        @TableId(value = "article_id", type = IdType.AUTO)
      private Integer articleId;

      @ApiModelProperty("分类")
      private String category;

      @ApiModelProperty("用户ID，外键关联User表")
      private Integer userId;

      @ApiModelProperty("文章标题")
      private String title;

      @ApiModelProperty("文章url")
      private String url;

      @ApiModelProperty("文章标签，逗号分隔")
      private String tag;

      @ApiModelProperty("创建时间")
      private LocalDateTime createdAt;

      @ApiModelProperty("最后更新时间")
      private LocalDateTime lastUpdatedAt;

      @ApiModelProperty("封面图片URL")
      private String coverImg;

      private Boolean isDelete;

}
