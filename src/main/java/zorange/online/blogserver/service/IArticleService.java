package zorange.online.blogserver.service;

import zorange.online.blogserver.common.Result;
import zorange.online.blogserver.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import zorange.online.blogserver.entity.dto.Search;

import java.util.Arrays;

/**
 * <p>
 * 文章表 服务类
 * </p>
 *
 * @author zorange
 * @since 2024-02-16
 */
public interface IArticleService extends IService<Article> {

    Object findByUserId(Integer userId, Integer Number, Integer NumberSize);

    Object findAll(Integer Number, Integer NumberSize);


    Object search(Search key);

    Object getRecommendation(String userId);
}
