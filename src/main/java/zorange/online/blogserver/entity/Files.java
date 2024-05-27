package zorange.online.blogserver.entity;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author zorange
 * @since 2024-02-22
 */
@Getter
@Setter
  @ApiModel(value = "Files对象", description = "")
public class Files implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String type;

    private Long size;

    private String url;

    private Boolean isDelete;

    private Boolean enable;

    private String md5;


}
