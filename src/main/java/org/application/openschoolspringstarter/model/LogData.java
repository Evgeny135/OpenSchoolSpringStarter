package org.application.openschoolspringstarter.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@NoArgsConstructor
@Getter
@Setter
public class LogData {

    private String method;
    private String request;
    private String parameters;
    private Map<String,String> requestHeaders;
    private Integer responseStatus;
    private Map<String,String> responseHeaders;
    private Long timeRunning;

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        if (method != null) sb.append("Метод: " + method+ " ");
        if (request != null) sb.append("URL: " + request+ " ");
        if (parameters != null) sb.append("Параметры: " + parameters+ " ");
        if (responseStatus!=null) sb.append("Код ответа: " + responseStatus+ " ");
        if (requestHeaders != null && !requestHeaders.isEmpty()) sb.append("Заголовок запроса: " + requestHeaders+ " ");
        if (responseHeaders != null && !responseHeaders.isEmpty()) sb.append("Заголовок ответа: " + responseHeaders+ " ");
        if (timeRunning != null) sb.append("Время исполнения: " + timeRunning + " мс");
        return sb.toString();
    }
}
