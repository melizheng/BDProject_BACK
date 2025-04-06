package com.zheng.business.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * author:
 * Date:2022/2/916:24
 **/
public class DateUtil {

    public static SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");

    //月份由0开始
    public static long getAddDay(StringBuffer first,int add) throws ParseException {
        Calendar calendar=Calendar.getInstance();
        calendar.clear();
        calendar.setTime(simpleDateFormat.parse(first.toString()));
        //1则代表的是对年份操作，2是对月份操作，3是对星期操作，5是对日期操作，11是对小时操作，12是对分钟操作，13是对秒操作，14是对毫秒操作
        calendar.add(5,add);
        return calendar.getTime().getTime();
    }

    /**
     * 今年的第一天
     * @param date
     * @return
     */
    public static String getYearFirstDay(Date date){
        Calendar calendar=Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        //今年一月第一天
        calendar.set(calendar.get(Calendar.YEAR),0,1);
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 今年最后一天
     * @param date
     * @return
     */
    public static String getYearLastDay(Date date){
        Calendar calendar=Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        //下一年的第一个月的前一天
        calendar.set(calendar.get(Calendar.YEAR)+1,0,0);
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 这个月第一天
     * @param date
     * @return
     */
    public static String getMonthFirstDay(Date date){
        Calendar calendar=Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        //这个月第一天
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),1);
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 这个月最后一天
     * @param date
     * @return
     */
    public static String getMonthLastDay(Date date){
        Calendar calendar=Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        //下个月的前一天
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1,0);
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 这周第一天
     * @param date
     * @return
     */
    public static String getWeekFirstDay(Date date){
        Calendar calendar=Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        // 返回值：1=Sunday,2=Monday,,,7=Saturday。
        int i = calendar.get(Calendar.DAY_OF_WEEK);
        if(i==1) {
            i=6;
        }else {
            i=i-2;
        }
        calendar.add(Calendar.DATE, -i);
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 这周最后一天
     * @param date
     * @return
     */
    public static String getWeeklastDay(Date date){
        Calendar calendar=Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        // 返回值：1=Sunday,2=Monday,,,7=Saturday。
        int i = calendar.get(Calendar.DAY_OF_WEEK);
        if(i==1) {
            i=0;
        }else {
            i=8-i;
        }
        //加i天
        calendar.add(Calendar.DATE, i);
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 得到今天的年月日
     * @param date
     * @return
     */
    public static String getToday(Date date){
        return simpleDateFormat.format(date.getTime());
    }


}
