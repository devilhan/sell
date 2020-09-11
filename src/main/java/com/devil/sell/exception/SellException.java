package com.devil.sell.exception;

import com.devil.sell.enums.ResultEnum;
import lombok.Getter;

/**
 * @author Hanyanjiao
 * @date 2020/6/11
 */
@Getter
public class SellException extends RuntimeException {

    private Integer code;

    private String message;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
    }

    public SellException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
