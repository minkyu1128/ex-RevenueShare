package com.example.revenueshare.core.utils;

public class RandomUtils {


    public static String randomDay() {
        int iMinMonth = 1;
        int iMaxMonth = 12;
        int iMinDay = 1;
        int iMaxDay = 31;

        int iRandomMonth = (int) (Math.random() * iMaxMonth - iMinMonth + 1) + iMinMonth;
        int iRandomDay = 0;

        switch (iRandomMonth) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                iRandomDay = (int) (Math.random() * iMaxDay - iMinDay + 1) + iMinDay; //최대 31일
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                iRandomDay = (int) (Math.random() * (iMaxDay - 1) - (iMinDay) + 1) + iMinDay; //최대 30일
                break;
            case 2:
                iRandomDay = (int) (Math.random() * (iMaxDay - 3) - (iMinDay) + 1) + iMinDay; //최대 28일
                break;
            default:
                return randomDay();
        }

        return String.format("%02d%02d", iRandomMonth, iRandomDay);
    }


    public static long randomMoney() {
        int min = 10000;
        int max = 10000000;
        return (long) (Math.floor(Math.random() * (min - max + 1)) + min) * -1;
    }

}
