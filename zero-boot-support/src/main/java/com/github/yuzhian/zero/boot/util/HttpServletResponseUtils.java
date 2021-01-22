package com.github.yuzhian.zero.boot.util;

import com.github.yuzhian.zero.boot.context.ObjectMapperHolder;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.NotSupportedException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yuzhian
 */
public class HttpServletResponseUtils {
    private HttpServletResponseUtils() throws NotSupportedException {
        throw new NotSupportedException("HttpServletResponseUtils instantiation is not allowed");
    }

    public static void response(HttpServletResponse response, String msg) {
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.write(msg);
            out.flush();
        } catch (IOException ignore) {
        }
    }

    public static void response(HttpServletResponse response, String[]... values) {
        Map<String, String> data = new HashMap<>(values.length);
        for (String[] kv : values) {
            data.put(kv[0], kv[1]);
        }
        response(response, data);
    }

    public static void response(HttpServletResponse response, Object data) {
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.write(ObjectMapperHolder.writeValueAsString(data));
            out.flush();
        } catch (IOException ignore) {
        }
    }
}
