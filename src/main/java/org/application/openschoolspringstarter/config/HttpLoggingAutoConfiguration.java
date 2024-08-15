package org.application.openschoolspringstarter.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.application.openschoolspringstarter.components.HttpInterceptor;
import org.application.openschoolspringstarter.exceptions.PropertiesException;
import org.application.openschoolspringstarter.services.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableConfigurationProperties(HttpLoggingProperties.class)
@ConditionalOnProperty(prefix = "http.logging", value = "enabled", havingValue = "true")
public class HttpLoggingAutoConfiguration implements WebMvcConfigurer {

    @Bean
    @ConditionalOnProperty(prefix = "http.logging", value = "enabled", havingValue = "true")
    public LoggingService initLoggingService(HttpLoggingProperties httpLoggingProperties) {
        LoggingService loggingService = new LoggingService();

        loggingService.setJson(httpLoggingProperties.getFormat().equals("json"));

        loggingService.setDebug(!httpLoggingProperties.getLevel().equals("info"));

        return loggingService;

    }

    @Bean
    @ConditionalOnMissingBean
    public HttpLoggingProperties httpLoggingProperties() {
        return new HttpLoggingProperties();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (httpLoggingProperties().isEnabled()) registry.addInterceptor(logHttpInterceptor());
    }

    @Bean
    @ConditionalOnProperty(prefix = "http.logging", value = "enabled", havingValue = "true")
    public HttpInterceptor logHttpInterceptor() {
        return new HttpInterceptor(initLoggingService(httpLoggingProperties()));
    }

}
