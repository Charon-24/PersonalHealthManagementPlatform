package com.peace.personalhealthmanagementplatform.common.util;

import com.peace.personalhealthmanagementplatform.common.constant.HttpHeaderConstants;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.UUID;

public final class TraceIdUtil {

    private TraceIdUtil() {
    }

    public static String getOrCreateTraceId() {
        String traceId = MDC.get(HttpHeaderConstants.TRACE_ID);
        if (hasText(traceId)) {
            return traceId;
        }

        HttpServletRequest request = getCurrentRequest();
        if (request != null) {
            traceId = request.getHeader(HttpHeaderConstants.TRACE_ID);
            if (!hasText(traceId)) {
                traceId = request.getHeader("traceId");
            }
            if (hasText(traceId)) {
                MDC.put(HttpHeaderConstants.TRACE_ID, traceId);
                return traceId;
            }
        }

        traceId = UUID.randomUUID().toString().replace("-", "");
        MDC.put(HttpHeaderConstants.TRACE_ID, traceId);
        return traceId;
    }

    private static HttpServletRequest getCurrentRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes servletRequestAttributes) {
            return servletRequestAttributes.getRequest();
        }
        return null;
    }

    private static boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
