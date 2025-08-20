/*
package com.bnguimgo.springboot.security.tymeleafloginform.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

*/
/**
 * Cette classe est nécessaire sinon, impossible de parser les dates
 *//*

@Configuration
//Source: https://www.baeldung.com/spring-boot-formatting-json-dates
// configure à default date format in our application.
// We have to define a bean and override its customize method to set the desired format.
// L’avantage est que cette configuration fonctionne à la fois pour Java 8 et les versions JAVA inférieures à java-8.

public class CustomDateTimeFormatter {

    private static final String dateFormat = "yyyy-MM-dd";
    private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.simpleDateFormat(dateTimeFormat);
            builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)));
            builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)));
        };
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        // Support des nouvelles API Date/Time (Java 8+)
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(new LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)));
        module.addSerializer(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)));
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.registerModule(module);

        // Évite l’écriture des dates en timestamp (ex: 1692000000)
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Définit le format global pour LocalDateTime
        mapper.configOverride(LocalDateTime.class)
                .setFormat(JsonFormat.Value.forPattern("yyyy-MM-dd HH:mm:ss"));

        return mapper;
    }

    @Bean
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
        return new Jackson2ObjectMapperBuilder().serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)))
                .serializationInclusion(JsonInclude.Include.NON_NULL);
    }
}*/
