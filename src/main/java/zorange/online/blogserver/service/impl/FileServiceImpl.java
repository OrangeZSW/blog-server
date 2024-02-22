package zorange.online.blogserver.service.impl;

import zorange.online.blogserver.entity.File;
import zorange.online.blogserver.mapper.FileMapper;
import zorange.online.blogserver.service.IFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zorange
 * @since 2024-02-22
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements IFileService {

}
