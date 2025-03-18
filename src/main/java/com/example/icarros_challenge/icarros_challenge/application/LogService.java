package com.example.icarros_challenge.icarros_challenge.application;

import org.springframework.stereotype.Service;

import com.example.icarros_challenge.icarros_challenge.wrappers.SentryWrapper;

/**
 * This service is responsible for handling log messages and exceptions
 */
@Service
public class LogService {

    private final SentryWrapper sentry;

    public LogService(SentryWrapper sentry) {
        this.sentry = sentry;
    }

    /**
     * This method expects an exception and will forward it to third-party
     * libraries so the exception can be properly parsed 
     * @param exception The exception to be parsed
     */
    public void handleException(Exception exception) {
        sentry.captureException(exception);
    }

    /**
     * This method expects a message and will forward it to third-party
     * libraries 
     * @param message
     */
    public void handleMessage(String message) {
        sentry.captureMessage(message);
    }
}
