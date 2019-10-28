package com.bfly.common;

import org.springframework.scheduling.support.CronSequenceGenerator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author andy_hulibo@163.com
 * @date 2019/9/14 12:42
 */
public class DateUtil {

    /**
     * 按照指定格式格式化时间
     *
     * @param date
     * @param format
     * @return
     * @author 胡礼波-Andy
     * @2015年8月4日上午11:53:40
     */
    public static String formatter(Date date, String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.format(date);
    }

    /**
     * 格式化时间为字符串 格式：yyyy-MM-dd hh:mm:ss
     *
     * @return
     * @author 胡礼波
     * 2012-5-22 下午04:00:42
     */
    public static String formatterDateTimeStr(Date date) {
        return date == null ? null : formatter(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 格式化时间为字符串 格式：yyyy-MM-dd
     *
     * @return
     * @author 胡礼波
     * 2012-5-22 下午04:00:42
     */
    public static String formatterDateStr(Date date) {
        return date == null ? null : formatter(date, "yyyy-MM-dd");
    }

    /**
     * 格式化时间 格式：yyyy-MM-dd
     *
     * @return
     * @author 胡礼波
     * 2012-5-22 下午04:00:42
     */
    public static Date formatterDate(Date date) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return date == null ? null : sf.parse(formatterDateStr(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 格式化时间 格式：yyyy-MM-dd hh:mm:ss
     *
     * @return
     * @author 胡礼波
     * 2012-5-22 下午04:00:42
     */
    public static Date formatterDateTime(Date date) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return date == null ? null : sf.parse(formatterDateTimeStr(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符串日期转化为日期类型 格式：yyyy-MM-dd
     *
     * @param dateStr
     * @return
     * @author 胡礼波
     * 2012-10-30 下午05:56:24
     */
    public static Date parseStrDate(String dateStr) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sf.parse(dateStr);
        } catch (Exception e) {
        }
        return date;

    }

    /**
     * 字符串日期转化为日期时间类型 格式：yyyy-MM-dd hh:mm:ss
     *
     * @param dateStr
     * @return
     * @author 胡礼波
     * 2012-10-30 下午05:56:24
     */
    public static Date parseStrDateTime(String dateStr) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sf.parse(dateStr);
        } catch (Exception e) {
        }
        return date;

    }

    /**
     * 获得当前时间的字符串比如:20080216
     *
     * @return
     * @author 胡礼波
     * 2012-5-9 下午02:06:26
     */
    public static String getCurrentDate() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        String str = year + "" + (month >= 10 ? month : "0" + month) + (day >= 10 ? day : "0" + day);
        return str;
    }

    /**
     * 获得当前的时间字符串格式：年月日时分秒毫秒比如:20120102123602001
     *
     * @return
     * @author 胡礼波
     * 2012-11-9 下午01:58:05
     */
    public static String getCurrentDateTime() {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minute = Calendar.getInstance().get(Calendar.MINUTE);
        int second = Calendar.getInstance().get(Calendar.SECOND);
        int milsecond = Calendar.getInstance().get(Calendar.MILLISECOND);
        String str = getCurrentDate() + "" + (hour >= 10 ? hour : "0" + hour) + (minute >= 10 ? minute : "0" + minute) + (second >= 10 ? minute : "0" + second) + (milsecond >= 100 ? milsecond : "00" + milsecond);
        return str;
    }

    /**
     * 获得文字类型的时间 比如：12小时前 33分钟前 10秒前 刚刚
     *
     * @param date
     * @return
     * @author 胡礼波
     * 2013-3-21 下午4:10:16
     */
    public static String getTimeText(Date date) {
        long subTime = (System.currentTimeMillis() - date.getTime()) / 1000;
        long sub = 0;
        sub = subTime / (24 * 60 * 60);
        //大于1天
        if (sub > 0) {
            return formatterDateTimeStr(date);
        }
        sub = subTime % (24 * 60 * 60) / (60 * 60);
        if (sub > 0) {
            return sub + "小时前";
        }
        sub = subTime % (24 * 60 * 60) % (60 * 60) / 60;
        if (sub > 0) {
            return sub + "分钟前";
        }
        sub = subTime % (24 * 60 * 60) % (60 * 60) % 60;
        return sub + "秒前";
    }

    /**
     * 获得当天0点时间
     *
     * @return
     * @author 胡礼波-Andy
     * @2015年5月10日下午5:11:46
     */
    public static Date getCurrentDayMorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获得当天24点时间
     *
     * @return
     * @author 胡礼波-Andy
     * @2015年5月10日下午5:11:41
     */
    public static Date getCurrentDayNight() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获得当前年份的第一天日期
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/24 22:18
     */
    public static Date getCurrentFirstDayOfYear() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        return getFirstDayOfYear(year);
    }

    /**
     * 获得当前年份的最后一天日期
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/24 22:18
     */
    public static Date getCurrentLastDayOfYear() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        return getLastDayOfYear(year);
    }

    /**
     * 获得指定年份的第一天
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/24 22:19
     */
    public static Date getFirstDayOfYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        return calendar.getTime();
    }

    /**
     * 获得指定年份的最后一天
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/24 22:19
     */
    public static Date getLastDayOfYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }

    /**
     * 获得当前月第一天日期
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/24 22:26
     */
    public static Date getCurrentFirstDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获得当前月份最后一天
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/24 22:27
     */
    public static Date getCurrentLastDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    /**
     * 获得本周第一天日期
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/24 22:39
     */
    public static Date getCurrentFirstDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return calendar.getTime();
    }

    /**
     * 获得本周最后一天
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/24 22:40
     */
    public static Date getCurrentLastDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getActualMaximum(Calendar.DAY_OF_WEEK));
        calendar.add(Calendar.DAY_OF_WEEK, 1);
        return calendar.getTime();
    }

    /**
     * 获得昨天日期
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/24 22:46
     */
    public static Date getYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
    }

    /**
     * 获得计划任务下次执行的时间
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/27 19:32
     */
    public static Date getNextDateByCron(String cron) {
        if (cron == null || !CronSequenceGenerator.isValidExpression(cron)) {
            throw new RuntimeException("Cron表达式无效!");
        }
        CronSequenceGenerator generator = new CronSequenceGenerator(cron);
        return generator.next(Calendar.getInstance().getTime());
    }
}
