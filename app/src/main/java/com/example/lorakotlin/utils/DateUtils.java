package com.example.lorakotlin.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
    /**
     * 英文简写（默认）如：2010-12-01
     */
    public static String FORMAT_SHORT = "yyyy-MM-dd";
    /**
     * 英文全称 如：2010-12-01 23:15:06
     */
    public static String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
    /**
     * 精确到毫秒的完整时间 如：yyyy-MM-dd HH:mm:ss.S
     */
    public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";
    /**
     * 中文简写 如：2010年12月01日
     */
    public static String FORMAT_SHORT_CN = "yyyy年MM月dd";
    /**
     * 中文全称 如：2010年12月01日 23时15分06秒
     */
    public static String FORMAT_LONG_CN = "yyyy年MM月dd日  HH时mm分ss秒";
    /**
     * 精确到毫秒的完整中文时间
     */
    public static String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";

    /**
     * 获得默认的 date pattern
     */
    public static String getDatePattern() {
        return FORMAT_LONG;
    }

    public static String[] timeFormat(long ms) {// 将毫秒数换算成x分x秒x毫秒
        String[] times = new String[2];
        int ss = 1000;
        int mi = ss * 60;
        long minute = (ms) / mi;
        long second = (ms - (minute * mi)) / ss;
        String strMinute = minute < 10 ? "0" + minute : "" + minute;
        String strSecond = second < 10 ? "0" + second : "" + second;
        times[0] = strMinute;
        times[1] = strSecond;
        return times;
    }

    /**
     * Java 毫秒转换为（天：时：分：秒）方法
     *
     * @param ms 毫秒
     * @return 一个装了天 时 分 秒的数组
     */
    public static String[] timeFormatMore(long ms) {// 将毫秒数换算成x天x时x分x秒x毫秒
        String[] times = new String[4];
        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        String strDay = day < 10 ? "0" + day : "" + day;
        String strHour = hour < 10 ? "0" + hour : "" + hour;
        String strMinute = minute < 10 ? "0" + minute : "" + minute;
        String strSecond = second < 10 ? "0" + second : "" + second;
        times[0] = strDay;
        times[1] = strHour;
        times[2] = strMinute;
        times[3] = strSecond;
        return times;
    }

    /**
     * 获取当前时间
     *
     * @param formatType yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getCurTime(String formatType) {
        Calendar calendar = new GregorianCalendar();
        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat(formatType);
        //         SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        return format.format(date);
    }

    /**
     * @param time
     * @param formatType yyyy/MM/dd
     * @return
     */
    public static String formatTime(Long time, String formatType) {
        String format;
        try {
            SimpleDateFormat formater = new SimpleDateFormat(formatType);
            format = formater.format(new Date(time));
            return format;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param formatType yyyy/MM/dd
     *                   获取 YYYY-MM-DD 的月和日
     *                   dateTime=2016-06-16
     * @return
     */
    public static String formatMathAndDay(String formatType) {
        try {
            Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse(formatType);//定义起始日期
            SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy");
            SimpleDateFormat sdf1 = new SimpleDateFormat("MM");
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd");
            String str1 = sdf0.format(d1);
            String str2 = sdf1.format(d1);
            String str3 = sdf2.format(d1);
            return str2 + "-" + str3;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param //           time
     * @param //formatType yyyy-MM-dd HH:mm:ss
     *                     转毫秒值
     * @return
     */
    public static long formatDate(String dateStr, String formatType) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(formatType);
            long millionSeconds = sdf.parse(dateStr).getTime();//毫秒
            return millionSeconds;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * @param //           time
     * @param //formatType yyyy-MM-dd HH:mm:ss
     *                     转毫秒值
     * @return
     */
    public static long formatDate(String dateStr) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long millionSeconds = sdf.parse(dateStr).getTime();//毫秒
            return millionSeconds;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 获取当前时间+/-addDate天
     *
     * @param addDate
     * @param formatType
     * @return
     */
    public static String getCurTimeAddND(int addDate, String formatType) {
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DATE, addDate);
        Date date = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat(formatType);
        return format.format(date);
    }

    /**
     * 获取当前时间+/-hour
     *
     * @param hour
     * @param formatType
     * @return
     */
    public static String getCurTimeAddH(int hour, String formatType) {
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.HOUR_OF_DAY, hour);
        Date date = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat(formatType);
        return format.format(date);
    }

    /**
     * 获取当前时间前30分钟
     *
     * @return
     */
    public static String getCurTimeAdd30M() {
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.MINUTE, 30);
        Date date = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat("HHmm");
        return format.format(date);
    }

    /**
     * 获取当前时间前+20分钟
     *
     * @return
     */
    public static String getCurTimeAdd20M() {
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.MINUTE, 20);
        Date date = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat("HHmm");
        return format.format(date);
    }

    /**
     * 获取当前时间前+40分钟
     *
     * @return
     */
    public static String getCurTimeAdd40M() {
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.MINUTE, 40);
        Date date = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat("HHmm");
        return format.format(date);
    }

    /**
     * @param datestr 2009-01-12 09:12:22
     * @return 星期几
     */
    public static String getweekdayBystr(String datestr) {
        if ("".equals(datestr)) {
            return "";
        }
        int y = Integer.valueOf(datestr.substring(0, 4));
        int m = Integer.valueOf(datestr.substring(5, 7)) - 1;
        int d = Integer.valueOf(datestr.substring(8, 10));

        Calendar cal = Calendar.getInstance();
        cal.set(y, m, d);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;// 今天是星期几
        String wstr = null;
        switch (dayOfWeek) {
            case 1:
                wstr = "一";
                break;
            case 2:
                wstr = "二";
                break;
            case 3:
                wstr = "三";
                break;
            case 4:
                wstr = "四";
                break;
            case 5:
                wstr = "五";
                break;
            case 6:
                wstr = "六";
                break;
            case 0:
                wstr = "日";
                break;
            default:
                wstr = "";
                break;
        }
        return "星期" + wstr;
    }

    public static String getweekdayBystrNew(String datestr) {
        if ("".equals(datestr)) {
            return "";
        }
        int y = Integer.valueOf(datestr.substring(0, 4));
        int m = Integer.valueOf(datestr.substring(5, 7)) - 1;
        int d = Integer.valueOf(datestr.substring(8, 10));

        Calendar cal = Calendar.getInstance();
        cal.set(y, m, d);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;// 今天是星期几
        String wstr = null;
        switch (dayOfWeek) {
            case 1:
                wstr = "一";
                break;
            case 2:
                wstr = "二";
                break;
            case 3:
                wstr = "三";
                break;
            case 4:
                wstr = "四";
                break;
            case 5:
                wstr = "五";
                break;
            case 6:
                wstr = "六";
                break;
            case 0:
                wstr = "日";
                break;
            default:
                wstr = "";
                break;
        }
        return "周" + wstr;
    }

    /**
     * 今天的序号
     *
     * @return
     */
    public static int getDayofWeekIndex() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int dayOfWeekIndex = cal.get(Calendar.DAY_OF_WEEK) - 1;// 今天是星期几
        return dayOfWeekIndex;
    }

    /**
     * 获取时间串
     *
     * @param longstr 秒
     * @return 1月前 1周前 1天前 1小时前 1分钟前
     */
    public static String getTimeStrByLong(String longstr) {

        Calendar calendar = new GregorianCalendar();
        Date date = calendar.getTime();
        Long clv = date.getTime();
        Long olv = Long.valueOf(longstr);

        calendar.setTimeInMillis(olv * 1000); // 转毫秒
        Date date2 = calendar.getTime();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String curtime = format.format(date2);

        Long belv = clv - olv * 1000;
        String retStr = "";
        // 24 * 60 * 60 * 1000;
        Long daylong = Long.valueOf("86400000");
        Long hourlong = Long.valueOf("3600000");
        Long minlong = Long.valueOf("60000");

        if (belv >= daylong * 30) {// 月

            Long mul = belv / (daylong * 30);
            retStr = retStr + mul + "月";
            belv = belv % (daylong * 30);
            return retStr + "前";
        }
        if (belv >= daylong * 7) {// 周

            Long mul = belv / (daylong * 7);
            retStr = retStr + mul + "周";
            belv = belv % (daylong * 7);
            return retStr + "前";
        }
        if (belv >= daylong) {// 天

            Long mul = belv / daylong;
            retStr = retStr + mul + "天";
            belv = belv % daylong;
            return retStr + "前";
        }
        if (belv >= hourlong) {// 时
            Long mul = belv / hourlong;
            retStr = retStr + mul + "小时";
            belv = belv % hourlong;
            return retStr + "前";
        }
        if (belv >= minlong) {// 分
            Long mul = belv / minlong;
            retStr = retStr + mul + "分钟";
            return retStr + "前";
        }
        return "";
    }

    /**
     * 今天明天后天
     *
     * @param todaystr
     * @return
     */
    public static String getTodayZh(String todaystr) {
        Calendar calendar = new GregorianCalendar();
        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String today = format.format(date);
        if (today.equals(todaystr))
            return "今天";

        calendar = new GregorianCalendar();
        calendar.add(Calendar.DATE, 1);
        date = calendar.getTime();
        format = new SimpleDateFormat("yyyy-MM-dd");
        String tomorrow = format.format(date);
        if (tomorrow.equals(todaystr))
            return "明天";

        calendar = new GregorianCalendar();
        calendar.add(Calendar.DATE, 2);
        date = calendar.getTime();
        format = new SimpleDateFormat("yyyy-MM-dd");
        String aftertomo = format.format(date);
        if (aftertomo.equals(todaystr))
            return "后天";
        return "";
    }

    /**
     * 判断时间是否过期
     *
     * @param deadLine
     * @return
     */
    public static boolean isDeadLine(String deadLine) {
        try {
            long currentTimeMillis = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(deadLine);
            long anotherTimeMillis = date.getTime();
            if ((currentTimeMillis - anotherTimeMillis) > 0) {// 大于
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 是否是今天
     *
     * @param timeStr
     * @param pattern
     * @return
     */
    public static boolean isToday(String timeStr, String pattern) {
        try {
            SimpleDateFormat formater = new SimpleDateFormat(pattern);
            Date date1 = formater.parse(timeStr);
            Date date = new Date();
            String otherStr = formater.format(date1);
            String curtimeStr = formater.format(date);
            if (otherStr.equals(curtimeStr)) {
                return true;
            }
            return false;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 是否是今天
     *
     * @param finishtimeStr 结束时间
     * @param pattern
     * @return
     */
    public static boolean isToday2(String finishtimeStr, String pattern) {// String finishtimeStr,


        return true;
    }

    public static boolean judgmentDate(String date1, String date2) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

            Date start = sdf.parse(date1);

            Date end = sdf.parse(date2);

            long cha = end.getTime() - start.getTime();

            if (cha < 0) {
                return false;
            }

            double result = cha * 1.0 / (1000 * 60 * 60);

            if (result <= 24) {

                return true;

            } else {

                return false;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 日期格式化转换
     *
     * @param oldDateStr
     * @param oldPattern
     * @param newPattern
     * @return
     */
    public static String changeFormater(String oldDateStr, String oldPattern, String newPattern) {
        if ("".equals(oldDateStr)) {
            return "";
        }
        try {
            SimpleDateFormat oldFormater = new SimpleDateFormat(oldPattern);
            SimpleDateFormat newFormater = new SimpleDateFormat(newPattern);
            Date date = oldFormater.parse(oldDateStr);
            return newFormater.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * 一个星期后的7天
     *
     * @return
     */
    public static String getNextWeekDayStrNew() {
        StringBuffer sb = new StringBuffer();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, 7);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        sb.append(day + "日");
        sb.append("-");
        cal.add(Calendar.DAY_OF_MONTH, 6);
        day = cal.get(Calendar.DAY_OF_MONTH);
        sb.append(day + "日");
        return sb.toString();
    }

    public static String getCurWeekDayStrNew() {
        StringBuffer sb = new StringBuffer();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int day = cal.get(Calendar.DAY_OF_MONTH);
        sb.append(day + "日");
        sb.append("-");
        cal.add(Calendar.DAY_OF_MONTH, 6);
        day = cal.get(Calendar.DAY_OF_MONTH);
        sb.append(day + "日");
        return sb.toString();
    }

    /**
     * 多少时间之前
     *
     * @param time "operatorTime":"2014-02-18 16:39:37"
     * @return
     */
    public static String diffCurTime(String time, String curTime) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date2 = df.parse(curTime);
            Date date1 = df.parse(time);
            long l = date2.getTime() - date1.getTime();
            long day = l / (24 * 60 * 60 * 1000);
            long hour = (l / (60 * 60 * 1000) - day * 24);
            long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            StringBuffer sBuffer = new StringBuffer();
            if (day > 0) {
                sBuffer.append(day + "天");
            }
            if (hour > 0) {
                sBuffer.append(hour + "小时");
            }
            if (min > 0) {
                sBuffer.append(min + "分");
            }
            if (s > 0) {
                sBuffer.append(s + "秒");
            }
            return sBuffer.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 根据预设格式返回当前日期
     *
     * @return
     */
    public static String getNow() {
        return format(new Date());
    }

    /**
     * 根据用户格式返回当前日期
     *
     * @param format
     * @return
     */
    public static String getNow(String format) {
        return format(new Date(), format);
    }

    /**
     * 使用预设格式格式化日期
     *
     * @param date
     * @return
     */
    public static String format(Date date) {
        return format(date, getDatePattern());
    }

    /**
     * 使用用户格式格式化日期
     *
     * @param date    日期
     * @param pattern 日期格式
     * @return
     */
    public static String format(Date date, String pattern) {
        String returnValue = "";
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            returnValue = df.format(date);
        }
        return (returnValue);
    }

    /**
     * 使用预设格式提取字符串日期
     *
     * @param strDate 日期字符串
     * @return
     */
    public static Date parse(String strDate) {
        return parse(strDate, getDatePattern());
    }

    /**
     * 使用用户格式提取字符串日期
     *
     * @param strDate 日期字符串
     * @param pattern 日期格式
     * @return
     */
    public static Date parse(String strDate, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 在日期上增加数个整月
     *
     * @param date 日期
     * @param n    要增加的月数
     * @return
     */
    public static Date addMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return cal.getTime();
    }

    /**
     * 在日期上增加天数
     *
     * @param date 日期
     * @param n    要增加的天数
     * @return
     */
    public static Date addDay(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, n);
        return cal.getTime();
    }

    /**
     * 获取时间戳
     */
    public static String getTimeString() {
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }

    /**
     * 获取日期年份
     *
     * @param date 日期
     * @return
     */
    public static String getYear(Date date) {
        return format(date).substring(0, 4);
    }

    /**
     * 按默认格式的字符串距离今天的天数
     *
     * @param date 日期字符串
     * @return
     */
    public static int countDays(String date) {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse(date));
        long t1 = c.getTime().getTime();
        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
    }

    /**
     * 按用户格式字符串距离今天的天数
     *
     * @param date   日期字符串
     * @param format 日期格式
     * @return
     */
    public static int countDays(String date, String format) {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse(date, format));
        long t1 = c.getTime().getTime();
        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
    }


    private final static long second = 1000;   //1秒
    private final static long minute = 60 * 1000;// 1分钟
    private final static long hour = 60 * minute;// 1小时
    private final static long day = 24 * hour;// 1天
    private final static long month = 31 * day;// 月
    private final static long year = 12 * month;// 年

    /**
     * 返回文字描述的日期
     *
     * @param date
     * @return
     */
    public static String getTimeFormatText(Date date) {
        if (date == null) {
            return null;
        }
        long diff = new Date().getTime() - date.getTime();
        long r = 0;
        if (diff > year) {
            r = (diff / year);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return df.format(date);
            // return r + "年前";
        }
        if (diff > month) {
            r = (diff / month);
            SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");
            return df.format(date);
            //return r + "个月前";
        }


        if (diff > day) {
            r = (diff / day);
            SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");
            return df.format(date);
            //return r + "天前";
        }
        if (diff > hour) {
            r = (diff / hour);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            if (isToday(sdf.format(date), "yyyy-MM-dd")) {
                SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                return "今天 " + df.format(date);
                //return r + "个小时前";
            } else {
                SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                return "昨天 " + df.format(date);
            }
        }
        if (diff > minute) {
            r = (diff / minute);
            return r + "分钟前";
        }

        if (diff > second) {
            r = diff / second;
            return r + "秒前";
        }

        if (diff < second) {
            return "1秒前";
        }
        return null;
    }
}
