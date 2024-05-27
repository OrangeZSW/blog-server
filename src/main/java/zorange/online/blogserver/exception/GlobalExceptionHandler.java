package zorange.online.blogserver.exception;
import zorange.online.blogserver.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理ServiceException异常,如果抛出ServiceException异常，就会被该方法捕获，然后运行该方法
     * 继承了RuntimeException，所以不需要在方法上抛出异常
     * @param e 业务异常
     * @return Result
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result handle(ServiceException e){
        return Result.error(e.getCode(),e.getMessage());
    }
}
