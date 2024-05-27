package zorange.online.blogserver.entity.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import zorange.online.blogserver.entity.Article;

import java.util.List;
@Data
public class ArticleInfo {
   private List<String> category;
    private List<String> tag;
    Page<Article> Articles;
}
