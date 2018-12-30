package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

/**
 * 自定义异常类
 */
public class CustomException extends RuntimeException {
    //继承运行器异常,不需要编译期就try,

    //错误代码
    private ResultCode resultCode;

    public CustomException(ResultCode resultCode){

        super("错误代码:"+resultCode.code()+"错误信息:"+resultCode.message());
        this.resultCode=resultCode;

    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
