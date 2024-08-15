package org.application.openschoolspringstarter.config;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.application.openschoolspringstarter.exceptions.PropertiesException;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "http.logging")
public class HttpLoggingProperties {
    private boolean enabled;
    private String level;
    private String format;

    @PostConstruct
    public void validate() {
        if (this.enabled) {
            if (this.getFormat() == null) {
                throw new PropertiesException("Не задано свойство: http.logging.format");
            } else if (this.getLevel() == null) {
                throw new PropertiesException("Не задано свойство: http.logging.level");
            }
        }
    }
}
