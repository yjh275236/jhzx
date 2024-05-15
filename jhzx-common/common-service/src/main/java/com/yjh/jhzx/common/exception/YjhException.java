package com.yjh.jhzx.common.exception;

import com.yjh.jhzx.model.vo.common.ResultCodeEnum;
import lombok.Data;

@Data
public class YjhException extends RuntimeException {
    private Integer code;
    private String message;
    private ResultCodeEnum resultCodeEnum;
    public YjhException(ResultCodeEnum resultCodeEnum) {
        this.resultCodeEnum = resultCodeEnum;
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
        
    }
}
