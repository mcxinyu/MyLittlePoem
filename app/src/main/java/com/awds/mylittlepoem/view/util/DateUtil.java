package com.awds.mylittlepoem.view.util;

/**
 * Created by huangyuefeng on 2016/12/15.
 */
public class DateUtil {
    private final static long SECOND_IN_MILLIS = 1000;

    public static long getCurrentTimeStamp() {
        return System.currentTimeMillis() / SECOND_IN_MILLIS;
    }
}
