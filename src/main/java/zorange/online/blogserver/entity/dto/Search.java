package zorange.online.blogserver.entity.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class Search {
    private String title="";
    private String category="";
    private String tag="";
    private Integer Number;
    private Integer NumberSize;
    private String userId;
}
