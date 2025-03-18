package com.example.icarros_challenge.icarros_challenge.wrappers;

import io.sentry.Sentry;

/**
 * Wrapper class around the `Sentry` one just for one to be 
 * able mock it during tests
 */
public class SentryWrapper {
    /**
     * Forwards the exception given to the `Sentry.captureException`
     * method
     * @param exception
     */
    public void captureException(Exception exception) {
        Sentry.captureException(exception);
    }

    /**
     * Forwards the message given to the `Sentry.captureMessage`
     * method
     * @param message
     */
    public void captureMessage(String message) {
        Sentry.captureMessage(message);
    }
}
