package zorange.online.blogserver.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
* 接口，统一返回包装类
 */
@Data
//无参构造
@NoArgsConstructor
//有参构造
@AllArgsConstructor
public class Result {
    private String code;
    private String msg;
    //泛型，可以是任意类型
    private Object data;
    //成功的方法，没有返回数据
    public static Result success(){
        return new Result(Constants.CODE_SUCCESS,Constants.MSG_SUCCESS,null);
    }
    //成功的方法，有返回数据
    public static Result success(Object data){
        return new Result(Constants.CODE_SUCCESS,Constants.MSG_SUCCESS,data);
    }
    //失败的方法，没有返回数据
    public static Result error(){
        return new Result(Constants.CODE_ERROR,Constants.MSG_ERROR,null);
    }
    //失败的方法，有返回的信息
    public static Result error(String msg){
        return new Result(Constants.CODE_ERROR,msg,null);
    }
    //失败的方法，有自定义的错误码和错误信息
    public static Result error(String code,String msg){
        return new Result(code,msg,null);
    }
}
