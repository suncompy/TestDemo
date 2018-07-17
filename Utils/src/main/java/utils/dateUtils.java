package utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 时间类
 * Created by 寇含尧 on 2018/6/28.
 */
public class dateUtils {

    public static DateTimeFormatter dfOne = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * java.time.LocalDate --> java.util.Date
     *
     * @param localDate
     * @return
     */
    public static Date localDateToUdate(LocalDate localDate) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);
    }

    public static String now2String() {
        return dfOne.format(LocalDateTime.now());
    }


}
