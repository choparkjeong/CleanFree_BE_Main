package site.cleanfree.be_main.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeConvertor {

    public static LocalDate dateTimeToDate(LocalDateTime time) {
        return time.toLocalDate();
    }

    public static LocalDateTime utcToKst(LocalDateTime utcTime) {
        return utcTime.atZone(ZoneId.of("UTC"))
            .withZoneSameInstant(ZoneId.of("Asia/Seoul")).toLocalDateTime();
    }

    public static LocalDate utcDateTimeToKstDate(LocalDateTime utcTime) {
        return dateTimeToDate(utcToKst(utcTime));
    }
}
