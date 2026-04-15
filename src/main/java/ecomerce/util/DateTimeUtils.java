package ecomerce.util;


import ecomerce.common.Const;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
public class DateTimeUtils {

    private DateTimeUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static ZonedDateTime now() {
        return ZonedDateTime.now(ZoneId.of(Const.DateTime.TIME_ZONE));
    }

    public static ZonedDateTime parseDateTime(String dateTime, String format) {
        LocalDateTime ldt = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(format));
        return ldt.atZone(ZoneId.of(Const.DateTime.TIME_ZONE));
    }

    public static ZonedDateTime parseDate(String date, String format) {
        LocalDate ldt = LocalDate.parse(date, DateTimeFormatter.ofPattern(format));
        return ldt.atStartOfDay(ZoneId.of(Const.DateTime.TIME_ZONE));
    }

    public static ZonedDateTime parseTimestamp(String timestamp) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(timestamp, formatter);
        return ZonedDateTime.of(localDateTime, ZoneId.of(Const.DateTime.TIME_ZONE));
    }

    public static LocalDateTime parseTimestampLocal(String timestamp) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(timestamp, formatter);
    }

    public static ZonedDateTime parseTimestamp(long timestamp) {
        Instant instant = Instant.ofEpochSecond(timestamp);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.of(Const.DateTime.TIME_ZONE));
        return localDateTime.atZone(ZoneId.of(Const.DateTime.TIME_ZONE));
    }

    public static String parseTimeToString(ZonedDateTime timestamp) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return timestamp.format(formatter);
    }

    public static LocalDateTime startOfDay(LocalDateTime time) {
        return time.truncatedTo(ChronoUnit.DAYS);
    }

    public static LocalDateTime endOfDay(LocalDateTime time) {
        return time.truncatedTo(ChronoUnit.DAYS).plusDays(1).minusSeconds(1);
    }

    public static LocalDateTime truncateHour(LocalDateTime time) {
        return time.truncatedTo(ChronoUnit.HOURS);
    }

    public static Date startOfMonth(LocalDate today) {
        LocalDate startDateOfMonth = today.withDayOfMonth(1);
        ZonedDateTime startOfDay = startDateOfMonth.atStartOfDay(ZoneId.of(Const.DateTime.TIME_ZONE));
        Date beginDate = Date.from(startOfDay.toInstant());

        return beginDate;
    }

    public static Date endOfMonth(LocalDate today) {
        LocalDate endDateOfMonth = today.withDayOfMonth(today.lengthOfMonth());
        ZonedDateTime endOfDay = endDateOfMonth.plusDays(1).atStartOfDay(ZoneId.of(Const.DateTime.TIME_ZONE));
        Date endDate = Date.from(endOfDay.toInstant());

        return endDate;
    }

    public static long secondDiffTime(LocalDateTime from, LocalDateTime to) {
        return Duration.between(from, to).getSeconds();
    }

    public static Date startOfDay(LocalDate localDate) {
        ZonedDateTime startOfDay = localDate.atStartOfDay(ZoneId.of(Const.DateTime.TIME_ZONE));
        Date beginDate = Date.from(startOfDay.toInstant());

        return beginDate;
    }

    public static Date endOfDay(LocalDate localDate) {
        ZonedDateTime endOfDay = localDate.plusDays(1).atStartOfDay(ZoneId.of(Const.DateTime.TIME_ZONE));
        Date endDate = Date.from(endOfDay.toInstant());

        return endDate;
    }

    public static boolean isDateValid(String date, String format) {
        try {
            DateFormat df = new SimpleDateFormat(format);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static LocalDateTime parseLocalDateTime(String date, String format) {
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(format));
    }

    public static String parseLocalDateTimeToString(LocalDateTime timestamp) {
        return parseLocalDateTimeToString(timestamp, "yyyy-MM-dd HH:mm:ss");
    }

    public static String parseLocalDateTimeToString(LocalDateTime timestamp, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return timestamp.format(formatter);
    }

    public static String parseMinuteToLocalTime(Integer numOfminutes) {
        if (numOfminutes < 0 || numOfminutes > 1439) {
            throw new IllegalArgumentException("Minutes must be >0 and <1439");
        }
        String hours = numOfminutes / 60 > 10 ? numOfminutes / 60 + "" : "0" + numOfminutes / 60;
        String minutes = numOfminutes % 60 > 10 ? numOfminutes % 60 + "" : "0" + numOfminutes % 60;
        return hours + ":" + minutes;
    }

    public static String diffTimeToString(LocalDateTime time1, LocalDateTime time2) {
        if (time1 == null || time2 == null) return "";
        Duration duration = Duration.between(time1, time2);
        StringBuilder durationAsStringBuilder = new StringBuilder();
        durationAsStringBuilder.append(' ');
        if (duration.toDays() > 0) {
            durationAsStringBuilder.append(duration.toDays());
            durationAsStringBuilder.append(" ngày");
        }

        duration = duration.minusDays(duration.toDays());
        long hours = duration.toHours();
        if (hours > 0) {
            String prefix = durationAsStringBuilder.isEmpty() ? "" : " ";
            durationAsStringBuilder.append(prefix);
            durationAsStringBuilder.append(hours);
            durationAsStringBuilder.append(" giờ");
        }

        duration = duration.minusHours(duration.toHours());
        long minutes = duration.toMinutes();
        if (minutes > 0) {
            String prefix = durationAsStringBuilder.isEmpty() ? "" : " ";
            durationAsStringBuilder.append(prefix);
            durationAsStringBuilder.append(minutes);
            durationAsStringBuilder.append(" phút");
        }
        duration = duration.minusMinutes(duration.toMinutes());
        long seconds = duration.toSeconds();
        if (seconds > 0) {
            String prefix = durationAsStringBuilder.isEmpty() ? "" : " ";
            durationAsStringBuilder.append(prefix);
            durationAsStringBuilder.append(seconds);
            durationAsStringBuilder.append(" giây");
        }
        return durationAsStringBuilder.toString();
    }

    public static String shortDiffTimeToString(LocalDateTime time1, LocalDateTime time2) {
        if (time1 == null || time2 == null) return "";
        Duration duration = Duration.between(time1, time2);
        StringBuilder durationAsStringBuilder = new StringBuilder();
        if (duration.toDays() > 0) {
            durationAsStringBuilder.append(duration.toDays());
            durationAsStringBuilder.append('d');
        }

        duration = duration.minusDays(duration.toDays());
        long hours = duration.toHours();
        if (hours > 0) {
            durationAsStringBuilder.append(hours);
            durationAsStringBuilder.append('h');
        }

        duration = duration.minusHours(duration.toHours());
        long minutes = duration.toMinutes();
        if (minutes > 0) {
            durationAsStringBuilder.append(minutes);
            durationAsStringBuilder.append('m');
        }
        duration = duration.minusMinutes(duration.toMinutes());
        long seconds = duration.toSeconds();
        if (seconds > 0) {
            durationAsStringBuilder.append(seconds);
            durationAsStringBuilder.append('s');
        }
        return durationAsStringBuilder.toString();
    }

    public static String parseTimestampToString(long timestamp) {
        LocalDateTime triggerTime = LocalDateTime.now();
        try {
            triggerTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.of(Const.DateTime.TIME_ZONE));
        } catch (DateTimeException ex) {
            log.info("[DEVICE_SERVICE] Fail to parse time from timestamp {}", timestamp);
        }
        return triggerTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static ZonedDateTime fromDate(Date date) {
        return date.toInstant().atZone(ZoneId.of(Const.DateTime.TIME_ZONE));
    }
}
