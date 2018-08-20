package de.synoa.getting.started.person.out.file.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import de.synoa.getting.started.person.out.file.App;

@Configuration
public class DefaultLogger {

    @Bean
    public Logger createDefaultLogger() {
        return LoggerFactory.getLogger(App.class.getPackage().getName());
    }

}
