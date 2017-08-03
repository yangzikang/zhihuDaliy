package yqb.com.zhuhudaliy.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by yangzikang on 2017/8/1.
 */

public class BeforeOneDay {
    public static String nowDate = new SimpleDateFormat("yyyyMMdd").format(new Date());

    public static String getSpecifiedDayBefore(String specifiedDay) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyyMMdd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);
        String dayBefore = new SimpleDateFormat("yyyyMMdd").format(c.getTime());
        return dayBefore;
    }

}
