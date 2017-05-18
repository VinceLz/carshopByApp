package com.car.contractcar.myapplication.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * ClassName: DateUtil <br/>
 * Function: 时间工具类 . <br/>
 * date: 2016年10月9日 下午5:17:45 <br/>
 *
 * @author 清算项目组
 * @version 2.0
 * @since JDK 1.7
 */
public class DateUtil {


    public static String getSqlDate() {
        SimpleDateFormat myFmt1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = myFmt1.format(new Date());
        return format;
    }

    public static String getSqlDate2addMouth(int addmouth) {
        Calendar cal = Calendar.getInstance();
        cal.add(2, addmouth);
        SimpleDateFormat myFmt1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = myFmt1.format(cal.getTime());
        return format;
    }

    public static boolean compTo(String currentTime, int number)
            {
        SimpleDateFormat myFmt1 = new SimpleDateFormat("yyyy-MM-dd");

        Date current = new Date();
                Date parse = null;
                try {
                    parse = myFmt1.parse(currentTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long time = parse.getTime();
        long time2 = current.getTime();

        return (time2 - time) / 86400L <= number;
    }


    public static int daysBetween(String smdate) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        long from = df.parse(new Date().toLocaleString()).getTime();
        long to = df.parse(smdate).getTime();

        return (int) ((to - from) / 86400000L);
    }





}
