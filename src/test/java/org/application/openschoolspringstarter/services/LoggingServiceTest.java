package org.application.openschoolspringstarter.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoggingServiceTest {
    @InjectMocks
    private LoggingService loggingService;

    @Mock
    private Logger logger;

    @BeforeEach
    public void setUp() {
        loggingService = new LoggingService();
        logger = (Logger) LoggerFactory.getLogger(LoggingService.class);
    }

    @Test
    public void givenServiceWithDebugAndJson_whenLogHttpRequest_thenLogDebugAndJsons() {
        loggingService.setDebug(true);
        loggingService.setJson(true);


        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setMethod("GET");
        request.setRequestURI("/test-uri");
        request.setQueryString("param1=value1&param2=value2");
        request.addHeader("header1", "value1");
        request.addHeader("header2", "value2");

        loggingService.logHttpRequest(request);
    }

    @Test
    public void givenServiceWithDebugAndText_whenLogHttpResponse_thenLogInText() {
        loggingService.setDebug(true);
        loggingService.setJson(false);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setMethod("GET");
        request.setRequestURI("/test-uri");
        request.setQueryString("param1=value1&param2=value2");
        request.addHeader("header1", "value1");
        request.addHeader("header2", "value2");
        request.setAttribute("startTime", System.currentTimeMillis());


        MockHttpServletResponse response = new MockHttpServletResponse();
        response.setStatus(200);
        response.setHeader("header1","value1");

        loggingService.logHttpResponse(request,response);
    }


}
