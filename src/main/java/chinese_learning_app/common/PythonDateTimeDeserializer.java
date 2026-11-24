package ecomerce.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class PythonDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
        String datetime = jsonParser.getText();
        LocalDateTime created = LocalDateTime.now();
        try {
            created = LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern(Const.DateTime.DATETIME_FORMAT));
        } catch (DateTimeException ex) {
            try {
                created = LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern(Const.DateTime.DATETIME_FORMAT_PYTHON_WITH_MILISECOND));
            } catch (DateTimeException e) {
                log.error("[DatetimeUtil] Can't parse datetime from {}", datetime);
            }
        }
        return created;
    }
}
