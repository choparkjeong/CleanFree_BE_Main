package site.cleanfree.be_main.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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

    public static String convertWriteTime(String writeTime) {
        // 입력 형식 지정
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(
            "EEE MMM dd HH:mm:ss zzz yyyy", java.util.Locale.ENGLISH);

        // 날짜와 시간 파싱
        ZonedDateTime parsedDateTime = ZonedDateTime.parse(writeTime, inputFormatter);

        // 원하는 형식으로 변환
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return parsedDateTime.format(outputFormatter);
    }

    public static LocalDateTime writeTimeToDateTime(String writeTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd yyyy HH:mm:ss 'GMT'Z", Locale.ENGLISH);

        return LocalDateTime.parse(writeTime, formatter);
    }
}
