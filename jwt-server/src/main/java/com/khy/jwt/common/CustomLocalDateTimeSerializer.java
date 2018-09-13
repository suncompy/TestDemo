package com.khy.jwt.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Created by 寇含尧
 */
public class CustomLocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    public static final DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    @Override
    public void serialize(LocalDateTime dateTime, JsonGenerator generator, SerializerProvider provider) throws IOException, JsonProcessingException {
        String result = pattern.format(dateTime);
        generator.writeString(result);
    }
}
