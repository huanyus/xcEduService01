package com.xuecheng.framework.exception;

import com.google.common.collect.ImmutableMap;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 统一异常捕获类
 */
@ControllerAdvice//控制器增强
public class ExceptionCatch {

    //使用 exception存放异常类型的错误代码的映射,
    //这里要用一个谷歌的ImmutableMap,一旦创建不可改变,线程安全
    private static ImmutableMap<Class<? extends Throwable>,ResultCode> EXCEPTION;
    protected static ImmutableMap.Builder<Class<? extends Throwable>,ResultCode> builer
            = ImmutableMap.builder();//用这个对象来构建exception则个map




    //记录日志,一旦捕获到
    private static final Logger LOGGER= LoggerFactory.getLogger(ExceptionCatch.class);

    @ExceptionHandler(CustomException.class)//捕获的哪一类异常,捕获就调用一下方法
    @ResponseBody//返回json字符串
    public ResponseResult customException(CustomException e){

        LOGGER.error("catch exception : {}\r\nexception: ",e.getMessage(),e);
        ResultCode resultCode = e.getResultCode();

        ResponseResult responseResult = new ResponseResult(resultCode);

        return responseResult;

    }

    @ExceptionHandler(Exception.class)//捕获的Exception异常
    @ResponseBody//返回json字符串
    public ResponseResult exception(Exception e){

        LOGGER.error("catch exception : {}\r\nexception: ",e.getMessage(),e);

        if (EXCEPTION==null){
            EXCEPTION=builer.build();//构建map

        }
        //从map中找错误代码,找到就返回,找不到就999999

        ResultCode resultCode = EXCEPTION.get(e.getClass());
        if (resultCode != null){
            //找得到
            return new ResponseResult(resultCode);
        }else{
            //找不到

            return new ResponseResult(CommonCode.SERVER_ERROR);
        }



    }

    //框架或第三方架包抛出的异常,能处理一个处理一个,放入静态代码块中
    static {

        //加入一些基础的异常类型
        builer.put(HttpMessageNotReadableException.class, CommonCode.INVALIN_PARAM);

    }


}
