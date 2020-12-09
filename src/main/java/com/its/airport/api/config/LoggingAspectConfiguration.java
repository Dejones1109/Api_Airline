
package com.its.airport.api.config;


import com.its.airport.api.aop.MonitorAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfiguration {

    @Bean
    public MonitorAspect monitorAspect() {
        return new MonitorAspect();
    }
}
