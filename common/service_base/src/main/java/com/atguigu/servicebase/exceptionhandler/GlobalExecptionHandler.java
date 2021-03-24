package com.atguigu.servicebase.exceptionhandler;

import com.atguigu.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//统一异常处理
@ControllerAdvice
@Slf4j   //报错日志输出到自定义文件中logback-spring.xml中的路径D:/IdeaWork/guli_log/edu
public class GlobalExecptionHandler {

    //1 指定出现什么异常执行这个方法
    @ResponseBody//为了返回数据
    @ExceptionHandler(Exception.class)
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("执行了全局异常处理...");
    }

    //2 Exception可以改成特定的异常，优先级匹配特定异常，修改Exception即可


    //3 自定义异常处理
    @ResponseBody//为了返回数据
    @ExceptionHandler(GuliException.class)
    public R error(GuliException e){
        log.error(e.getMessage());//报错日志输出到自定义文件中
        e.printStackTrace();
        //获取自定义的状态码code(e.getCode())和状态信息message(e.getMsg())
        return R.error().code(e.getCode()).message(e.getMsg());
    }

}

