package org.application.openschoolspringstarter.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.application.openschoolspringstarter.config.HttpLoggingProperties;
import org.application.openschoolspringstarter.model.LogData;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@ConditionalOnProperty(prefix = "http.logging", value = "enabled", havingValue = "true")
@Getter
@Setter
public class LoggingService {

    private boolean isDebug;

    private boolean isJson;

    public LoggingService() {
    }

    public void logHttpRequest(HttpServletRequest request) {
        if (isDebug) {
            LogData logData = new LogData();
            Map<String, String> headerRequest = new HashMap<>();
            Enumeration<String> headerRequestNames = request.getHeaderNames();
            while (headerRequestNames.hasMoreElements()) {
                String headerName = headerRequestNames.nextElement();
                headerRequest.put(headerName, request.getHeader(headerName));
            }

            logData.setMethod(request.getMethod());
            logData.setRequest(request.getRequestURI());
            logData.setParameters(request.getQueryString());
            logData.setRequestHeaders(headerRequest);

            loggingLogData(logData);

        } else {
            LogData logData = new LogData();
            logData.setMethod(request.getMethod());
            logData.setRequest(request.getRequestURI());

            loggingLogData(logData);
        }

    }

    public void logHttpResponse(HttpServletRequest request, HttpServletResponse response) {
        if (isDebug) {
            Long startTime = (Long) request.getAttribute("startTime");
            Long timeRunning = System.currentTimeMillis() - startTime;
            Map<String, String> header = new HashMap<>();
            Collection<String> headerNames = response.getHeaderNames();
            for (String headerName : headerNames) {
                header.put(headerName, response.getHeader(headerName));
            }

            LogData logData = new LogData();

            logData.setResponseStatus(response.getStatus());
            logData.setResponseHeaders(header);
            logData.setTimeRunning(timeRunning);

            loggingLogData(logData);
        } else {
            LogData logData = new LogData();
            logData.setResponseStatus(response.getStatus());

            loggingLogData(logData);
        }
    }

    private String loggingLogData(LogData logData) {
        String result = convertToFormat(logData);
        log.info(result);
        return result;
    }


    private String convertToFormat(LogData logData) {
        if (isJson) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                return objectMapper.writeValueAsString(logData);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } else {
            return logData.toString();
        }
    }
}
