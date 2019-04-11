package com.bgn.baseframe.utils;

import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 作者：wl on 2017/9/14 16:12
 * 邮箱：wanglun@stosz.com
 */
public class DateUtil {
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT2 = "yyyy/MM/dd HH:mm:ss";

    public static final String DATE_FORMAT_YMD = "yyyy-MM-dd";

    public static final String SERVCE_DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";

    /**
     * 将毫秒值转换为指定格式的字符串
     *
     * @param : @param time	毫秒值
     * @param : @param fomat	格式
     * @return type:String	返回格式化后的字符串
     * @author hujie
     */
    public static String getFormatString(long time, String fomat) {
        if (time < 100000000000L) {
            time = time * 1000;
        }
        String formatString = "";
        if (fomat != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(fomat);
            formatString = dateFormat.format(new Date(time));
        }
        return formatString;
    }

    /**
     * 将字符串转换为毫秒值
     *
     * @param : @param date	日期字符串
     * @param : @param format	日期字符串的格式
     * @return type:long	日期字符串对应的毫秒值
     * @author hujie
     */
    public static long getFormatTime(String date, String format) {
        long time = 0;
        if (date != null && format != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            try {
                Date parse = dateFormat.parse(date);
                time = parse.getTime();
            } catch (ParseException e) {
                Logger.e(e, "日期格式转换错误");
            }
        }
        return time;
    }


    /***
     * 得到系统时间
     * @return
     */
    public static String getSysTime(String format) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String sysNewTime = sdf.format(calendar.getTime());
        return sysNewTime;
    }

    public static String getSysTime() {
        return getSysTime(DATE_FORMAT);
    }

    public static boolean isSameDay(long l1, long l2) {
        if (l1 == 0 || l2 == 0) {
            return false;
        }

        Date d1 = new Date(l1);
        Date d2 = new Date(l2);
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(d1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(d2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
        boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);

        return isSameDate;

    }

    public static String getToday(String formatStr) {
        SimpleDateFormat sf = new SimpleDateFormat(formatStr);
        Calendar c = Calendar.getInstance();
        sf.format(c.getTime());
        return sf.format(c.getTime());
    }


    /**
     * 获取剩余时间 几天几时几分几秒
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static String getRemainTime(String startTime, String endTime) {

        String remainTime = "0"; // 剩余时间

        long dayMsec = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long hourMsec = 1000 * 60 * 60;// 一小时的毫秒数
        long minuteMsec = 1000 * 60;// 一分钟的毫秒数
        long secondMsec = 1000;// 一秒钟的毫秒数
        long diffMsec; // 毫秒差

        if (startTime != null && !startTime.equals("") && endTime != null && !endTime.equals("")) {
            try {
                // 获得两个时间的毫秒时间差异
                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                diffMsec = format.parse(endTime).getTime() - format.parse(startTime).getTime();
                if (diffMsec > 0) {
                    /*判断结束时间是否大于开始时间*/
                    long diffDay = diffMsec / dayMsec;// 计算差多少天
                    long diffHour = diffMsec % dayMsec / hourMsec;// 计算差多少小时
                    long diffMin = diffMsec % dayMsec % hourMsec / minuteMsec;// 计算差多少分钟
                    long diffSec = diffMsec % dayMsec % dayMsec % minuteMsec / secondMsec;// 计算差多少秒//输出结果
                    remainTime = "剩余" + diffDay + "天" + diffHour + "时" + diffMin + "分" + diffSec + "秒";
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return remainTime;
    }
    /**
     * 获取秒杀剩余时间 几天几时几分几秒
     *
     * @param startTime
     * @param type      1:时  2:分  3:秒
     * @return
     */
    public static String getRemainTimes(String startTime, String endTime, int type) {

        String remainTime = "00"; // 剩余时间

        long dayMsec = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long hourMsec = 1000 * 60 * 60;// 一小时的毫秒数
        long minuteMsec = 1000 * 60;// 一分钟的毫秒数
        long secondMsec = 1000;// 一秒钟的毫秒数
        long diffMsec; // 毫秒差

        if (startTime != null && !startTime.equals("") && endTime != null && !endTime.equals("")) {

            try {
                // 获得两个时间的毫秒时间差异
                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                diffMsec = format.parse(endTime).getTime() - format.parse(startTime).getTime();
                if (diffMsec > 0) {
                    /*判断结束时间是否大于开始时间*/
                    long diffDay = diffMsec / dayMsec;// 计算差多少天
                    long diffHour = diffMsec % dayMsec / hourMsec;// 计算差多少小时
                    long diffMin = diffMsec % dayMsec % hourMsec / minuteMsec;// 计算差多少分钟
                    long diffSec = diffMsec % dayMsec % dayMsec % minuteMsec / secondMsec;// 计算差多少秒//输出结果

                    String finDay = "" + diffDay;
                    String finHour = diffHour < 10 ? "0" + diffHour : "" + diffHour;
                    String finMin = diffMin < 10 ? "0" + diffMin : "" + diffMin;
                    String finSec = diffSec < 10 ? "0" + diffSec : "" + diffSec;

                    switch (type) {
                        case 0:
                            remainTime = finDay;
                            break;
                        case 1:
                            remainTime = finHour;

                            break;
                        case 2:
                            remainTime = finMin;

                            break;
                        case 3:
                            remainTime = finSec;

                            break;
                        default:
                            remainTime = "00";
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return remainTime;
    }
}
