package com.devil.sell.utils;

import com.devil.sell.enums.CodeEnum;


/**
 * @author Hanyanjiao
 * @date 2020/6/24
 */
public class EnumUtil {

    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> tClass){
        for (T each : tClass.getEnumConstants()) {
            if(code.equals(each.getCode())){
                return each;
            }
        }
        return null;
    }
}
