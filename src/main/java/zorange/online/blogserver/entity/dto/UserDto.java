package zorange.online.blogserver.entity.dto;

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

}
