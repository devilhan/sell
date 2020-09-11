package com.devil.sell.utils;

/**
 * @author Hanyanjiao
 * @date 2020/6/23
 */
public class MathUtil {

    public static final Double MONEY_RANGE = 0.01;

    public static Boolean equals(Double d1,Double d2){
        double abs = Math.abs(d1 - d2);
        if (abs < MONEY_RANGE){
            return true;
        }else{
            return false;
        }

    }
}
