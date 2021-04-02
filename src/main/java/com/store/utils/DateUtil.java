package com.store.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil
{
    public final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

    public final static SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");

    public final static SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");

    public final static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public final static SimpleDateFormat sdfTimeStr = new SimpleDateFormat("yyyyMMddHHmmss");
    
    public static String getTimesAfterDay(long day)
    {
        Long seconds = System.currentTimeMillis() + (365 * 86400 * 1000);
        Date date = new Date(86400000L * day + System.currentTimeMillis());
        return sdfTime.format(date);
    }

    public static Date getDate(){
        return new Date();
    }
    /**
     * 
     * @Description (TODO 毫秒转换成时间格式)
     * @param millsec
     * @return
     */
    public static String millsec2TimeStr(Long millsec)
    {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(millsec);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(gc.getTime());
    }
    
    /**
     * 获取YYYY格式
     * 
     * @return
     */
    public static String getYear()
    {
        return sdfYear.format(new Date());
    }
    
    /**
     * 获取YYYY-MM-DD格式
     * 
     * @return
     */
    public static String getDay()
    {
        return sdfDay.format(new Date());
    }
    
    /**
     * 获取YYYYMMDD格式
     * 
     * @return
     */
    public static String getDays()
    {
        return sdfDays.format(new Date());
    }
    
    /**
     * 获取YYYY-MM-DD HH:mm:ss格式
     * 
     * @return
     */
    public static String getTime()
    {
        return sdfTime.format(new Date());
    }
    
    /**
     * 
     * @Description (TODO 返回yyyyMMddHHmmss类型的时间字符串)
     * @return
     */
    public static String getTimeStr()
    {
        return sdfTimeStr.format(new Date());
    }
    
    /**
     * @Title: compareDate
     * @Description: TODO(日期比较，如果s>=e 返回true 否则返回false)
     * @param s
     * @param e
     * @return boolean
     * @throws @author luguosui
     */
    public static boolean compareDate(String s, String e)
    {
        if (fomatDate(s) == null || fomatDate(e) == null)
        {
            return false;
        }
        return fomatDate(s).getTime() >= fomatDate(e).getTime();
    }
    
    /**
     * 格式化日期
     * 
     * @return
     */
    public static Date fomatDate(String date)
    {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            return fmt.parse(date);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 校验日期是否合法
     * 
     * @return
     */
    public static boolean isValidDate(String s)
    {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            fmt.parse(s);
            return true;
        }
        catch (Exception e)
        {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return false;
        }
    }
    
    public static int getDiffYear(String startTime, String endTime)
    {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            int years =
                (int)(((fmt.parse(endTime).getTime() - fmt.parse(startTime).getTime()) / (1000 * 60 * 60 * 24)) / 365);
            return years;
        }
        catch (Exception e)
        {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return 0;
        }
    }
    
    /**
     * <li>功能描述：时间相减得到天数
     * 
     * @param beginDateStr
     * @param endDateStr
     * @return long
     * @author Administrator
     */
    public static long getDaySub(String beginDateStr, String endDateStr)
    {
        long day = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = null;
        Date endDate = null;
        
        try
        {
            beginDate = format.parse(beginDateStr);
            endDate = format.parse(endDateStr);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
        // System.out.println("相隔的天数="+day);
        
        return day;
    }
    
    /**
     * 得到n天之后的日期
     * 
     * @param days
     * @return
     */
    public static String getAfterDayDate(String days)
    {
        int daysInt = Integer.parseInt(days);
        
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdfd.format(date);
        
        return dateStr;
    }
    
    /**
     * 得到n天之后是周几
     * 
     * @param days
     * @return
     */
    public static String getAfterDayWeek(String days)
    {
        int daysInt = Integer.parseInt(days);
        
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        
        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String dateStr = sdf.format(date);
        
        return dateStr;
    }

    /**
     * 获取当天的开始时间
     * @return
     */
    public static Date getDayBegin() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取明天的开始时间
     * @return
     */
    public static Date getBeginDayOfTomorrow() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayBegin());
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }
    
    public static void main(String[] args)
    {
        System.out.println(System.currentTimeMillis());
        System.out.println(86400 * 1000);
        Long seconds = System.currentTimeMillis() + (365 * 86400 * 1000);
        Date date = new Date(86400000L * 365L + System.currentTimeMillis());
        System.out.println(sdfTime.format(date));
    }
    
}