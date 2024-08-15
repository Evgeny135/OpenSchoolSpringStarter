package org.application.openschoolspringstarter.config;

import org.application.openschoolspringstarter.components.HttpInterceptor;
import org.application.openschoolspringstarter.services.LoggingService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class HttpLoggingAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withUserConfiguration(HttpLoggingAutoConfiguration.class);

    @Test
    void givenContextWithNotEnabled_whenRunApplication_thenNoCreateBean(){
        contextRunner.withPropertyValues("http.logging.enabled=false")
                .run(context -> {
                   assertThat(context).doesNotHaveBean(LoggingService.class);
                   assertThat(context).doesNotHaveBean(HttpInterceptor.class);
                });
    }

    @Test
    void givenContextWithoutFormat_whenRunApplication_thenThrowException(){
        contextRunner.withPropertyValues("http.logging.enabled=true", "http.logging.level=debug")
                .run(context -> {
                    assertThatThrownBy(()-> context.getBean(LoggingService.class))
                            .isInstanceOf(RuntimeException.class);
                });
    }


    @Test
    void givenContextWithJsonAndDebug_whenRunApp_thenCreateSingleBeanAndTrueParametersService() {
        contextRunner.withPropertyValues("http.logging.enabled=true", "http.logging.format=json", "http.logging.level=debug")
                .run(context -> {
                    assertThat(context).hasSingleBean(LoggingService.class);
                    LoggingService loggingService = context.getBean(LoggingService.class);
                    assertTrue(loggingService.isDebug());
                    assertTrue(loggingService.isJson());
                    assertThat(loggingService).isNotNull();
                });
    }

}
