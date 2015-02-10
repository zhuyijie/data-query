package h2.function;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * h2的自定义函数
 * @author zhuyijie
 * @date 14-12-12
 */
public class Function {
    public static Date fromUnixTime(long unixtime) {
        return new Date(unixtime * 1000);
    }

    public static long unixTimestamp(String time) {
        DateFormat dateFormat;
        if (time.contains("-")) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        } else {
            dateFormat = new SimpleDateFormat("yyyyMMddHH");
        }
        Date date;
        try {
            date = dateFormat.parse(time);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date.getTime() / 1000;
    }
}
