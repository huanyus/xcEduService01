package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

public class ExceptionCast {

    public static void cast(ResultCode resultCode){
        //抽取出来的目的是,这里可以加日志之类的
        throw new CustomException(resultCode);
    }
}
