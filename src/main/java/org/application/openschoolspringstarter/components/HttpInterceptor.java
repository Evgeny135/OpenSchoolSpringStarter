package org.application.openschoolspringstarter.components;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.application.openschoolspringstarter.model.LogData;
import org.application.openschoolspringstarter.services.LoggingService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@ConditionalOnProperty(prefix = "http.logging", value = "enabled", havingValue = "true")
public class HttpInterceptor implements HandlerInterceptor {

    private final LoggingService loggingService;

    public HttpInterceptor(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("startTime", System.currentTimeMillis());

        loggingService.logHttpRequest(request);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        loggingService.logHttpResponse(request,response);
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
