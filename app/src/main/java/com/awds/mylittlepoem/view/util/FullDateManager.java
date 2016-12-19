package com.awds.mylittlepoem.view.util;

import org.joda.time.DateTime;

import java.util.HashMap;

/**
 * Created by huangyuefeng on 2016/12/13.
 */

public class FullDateManager {
    private final static String YEAR_CHINESE = "年";
    private final static String MONTH_CHINESE = "月";
    private final static String DAY_CHINESE = "日";

    private int year = 0;
    private int month = 0;
    private int day = 0;

    private final static HashMap<Integer, String> intToChinese = new HashMap<>();
    static {
        intToChinese.put(0, "零");
        intToChinese.put(1, "一");
        intToChinese.put(2, "二");
        intToChinese.put(3, "三");
        intToChinese.put(4, "四");
        intToChinese.put(5, "五");
        intToChinese.put(6, "六");
        intToChinese.put(7, "七");
        intToChinese.put(8, "八");
        intToChinese.put(9, "九");
        intToChinese.put(10, "十");
    }

    public FullDateManager() {
    }

    public FullDateManager(long dateSeconds) {
        DateTime dateTime = new DateTime(dateSeconds * 1000);
        year = dateTime.getYear();
        month = dateTime.getMonthOfYear();
        day = dateTime.getDayOfYear();
    }

    public FullDateManager(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    private String yearToChinese(int year) {
        StringBuffer stringBuffer = new StringBuffer();
        while (year > 0){
            //取出当前年份的最后一位，转换为汉字，并插入stringBuffer的第一位
            int y = year % 10;
            stringBuffer.insert(0, intToChinese.get(y));
            //去掉年的最后一位
            year = year / 10;
        }
        return stringBuffer.toString();
    }

    private String otherToChinese(int dayOrMonth) {
        if (dayOrMonth < 0){
            return "";
        }
        if (dayOrMonth < 10){
            return intToChinese.get(dayOrMonth);
        }
        StringBuffer stringBuffer = new StringBuffer();
        //取出十位数字，转换为汉字
        int tens = dayOrMonth / 10;
        stringBuffer.append((tens == 1) ? "" : intToChinese.get(tens)).append("十");
        //取出个位数字，转换为汉字
        int units = dayOrMonth % 10;
        stringBuffer.append((units <= 0) ? "" : intToChinese.get(units));
        return stringBuffer.toString();
    }

    /**
     * 二零一六年 十二月 十三日
     * @return
     */
    public String getFullChineseDate() {
        return getYear(year) + getMonth(month) + getDay(day);
    }

    /**
     * 二零一六年 十二月
     * @return
     */
    public String getYearMonthChineseDate() {
        return getYear(year) + getMonth(month);
    }

    /**
     * 十二月 十三日
     * @return
     */
    public String getMonthDayChineseDate() {
        return getMonth(month) + getDay(day);
    }

    /**
     * 十三日
     * @return
     */
    public String getDayChineseDate(){
        return getDay(day);
    }

    public String getYear(int year) {
        return getPureYear(year) + YEAR_CHINESE + " ";
    }

    public String getMonth(int month) {
        return getPureMonth(month) + MONTH_CHINESE + " ";
    }

    public String getDay(int day) {
        return getPureDay(day) + DAY_CHINESE + " ";
    }

    public String getPureYear(int year) {
        return yearToChinese(year);
    }

    public String getPureMonth(int month) {
        return otherToChinese(month);
    }

    public String getPureDay(int day) {
        return otherToChinese(day);
    }

    /**
     * 获取今天 0 点的秒数
     * @return
     */
    public static long getTodayDateSeconds() {
        DateTime dateTime = new DateTime();
        DateTime today = new DateTime(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth(), 0, 0);
        return today.getMillis() / 1000;
    }
}
