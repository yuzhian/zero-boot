package com.github.yuzhian.zero.boot.context;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.transaction.NotSupportedException;
import java.text.SimpleDateFormat;

/**
 * @author yuzhian
 */
public final class ObjectMapperHolder {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    private ObjectMapperHolder() throws NotSupportedException {
        throw new NotSupportedException("ObjectMapperHolder instantiation is not allowed");
    }

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    public static String writeValueAsString(Object value) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(value);
    }

    public static <T> T readValue(String content, Class<T> valueType) throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(content, valueType);
    }
}
