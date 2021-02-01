package com.github.yuzhian.zero.boot.util;

import com.github.yuzhian.zero.boot.context.ObjectMapperHolder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.NotSupportedException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * @author yuzhian
 */
public class ResponseWriter {
    private ResponseWriter() throws NotSupportedException {
        throw new NotSupportedException("ResponseWriter instantiation is not allowed");
    }

    /**
     * response json
     *
     * @param response HttpServletResponse
     * @param data     json string
     */
    public static void response(HttpServletResponse response, String data) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try (PrintWriter out = response.getWriter()) {
            out.write(data);
            out.flush();
        } catch (IOException ignore) {
        }
    }

    /**
     * response Object
     */
    public static <T> void response(HttpServletResponse response, T body) {
        // response json
        response(response, ObjectMapperHolder.writeValueAsString(body));
    }

    /**
     * response ResponseEntity
     */
    public static <T> void response(HttpServletResponse response, ResponseEntity<T> entity) {
        response.setStatus(entity.getStatusCodeValue());
        entity.getHeaders().forEach((key, value) -> value.forEach(v -> response.addHeader(key, v)));
        // response json
        response(response, entity.hasBody()
                ? ObjectMapperHolder.writeValueAsString(entity.getBody())
                : null
        );
    }
}
