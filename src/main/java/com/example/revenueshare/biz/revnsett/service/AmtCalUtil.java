package com.example.revenueshare.biz.revnsett.service;

public class AmtCalUtil {

    /**
     * 금액을 비율만큼 나누어 반환 한다.
     * @param amount
     * @param rate
     * @return
     */
    public static Long amtByRate(long amount, int rate) throws ArithmeticException{
        double ratio = (double)rate/100;
        return (long)(amount*ratio);
//        return Math.floorDiv((long) (amount*ratio), 10)*10; //1의 자리 버림
    }
}
