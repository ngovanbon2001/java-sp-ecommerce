package ihanoi.ihanoi_backend.configuration;

import ihanoi.ihanoi_backend.common.Const;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ZonedDateTimeParamConverter implements Converter<String, ZonedDateTime> {
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern(Const.DateTime.DATETIME_FORMAT).withZone(ZoneId.of("Asia/Ho_Chi_Minh"));

    @Override
    public ZonedDateTime convert(String source) {
        return ZonedDateTime.of(LocalDateTime.parse(source, FORMATTER), ZoneId.of("Asia/Ho_Chi_Minh"));
    }
}