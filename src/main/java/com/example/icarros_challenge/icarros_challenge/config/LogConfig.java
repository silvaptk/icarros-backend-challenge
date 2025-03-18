package com.example.icarros_challenge.icarros_challenge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.icarros_challenge.icarros_challenge.wrappers.SentryWrapper;

/**
 * Log configuration class
 */
@Configuration
public class LogConfig {
    /**
     * This method method will be used by Spring to properly
     * inject the `SentryWrapper` class when needed
     * @return SentryWrapper A class that wrappers the Sentry SDK 
     */
    @Bean
    public SentryWrapper sentryWrapper() {
        return new SentryWrapper();
    }
}
