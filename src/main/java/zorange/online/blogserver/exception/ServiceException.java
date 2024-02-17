package zorange.online.blogserver.exception;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException{
    private final String code;
    public ServiceException(String code, String msg){
        //调用父类的构造方法
        super(msg);
        this.code=code;
    }
}
