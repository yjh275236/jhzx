package com.yjh.jhzx.common.exception;

import com.yjh.jhzx.model.vo.common.Result;
import com.yjh.jhzx.model.vo.common.ResultCodeEnum;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobleExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody

    public Result error() {
          return Result.build(null, ResultCodeEnum.SYSTEM_ERROR);
    }

    //自定义
    @ExceptionHandler(YjhException.class)
    @ResponseBody
    public Result error(YjhException e) {
        return Result.build(null,e.getResultCodeEnum());
    }

}
