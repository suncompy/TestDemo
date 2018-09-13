package com.khy.jwt.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Created by 寇含尧
 */
public class CustomLocalDateTimeDeSerializer extends JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String str = jsonParser.getText();
        if (str.length() == 0) {
            return null;
        }
        DateTimeFormatter formatter;
        try {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            return LocalDateTime.parse(str, formatter);
        } catch (Exception e) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS", Locale.CHINA);
            return LocalDateTime.parse(str, formatter);
        }
    }
}
