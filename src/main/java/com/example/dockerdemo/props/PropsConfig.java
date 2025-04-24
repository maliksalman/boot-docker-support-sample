package com.example.dockerdemo.props;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("file:${config.path:src/main/resources}/config.properties")
public class PropsConfig {

    @Bean
    public Props configuredBean(@Value("${my.foo}") String foo,
                                @Value("${my.bar}") String bar) {
        return new Props(foo, bar);
    }
}
