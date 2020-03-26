package com.xavidop.alexa.helloworld.handlers;

import com.amazon.ask.dispatcher.exception.ExceptionHandler;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.exception.AskSdkException;
import com.amazon.ask.model.Response;
import io.sentry.Sentry;
import io.sentry.event.Breadcrumb;
import io.sentry.event.BreadcrumbBuilder;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

public class MyExceptionHandler implements ExceptionHandler {
    @Override
    public boolean canHandle(HandlerInput input, Throwable throwable) {
        return throwable instanceof Exception;
    }

    @Override
    public Optional<Response> handle(HandlerInput input, Throwable throwable) {

        Sentry.capture(throwable);
        Sentry.clearContext();

        return input.getResponseBuilder()
                .withSpeech("An error was encountered while handling your request. Try again later.")
                .build();
    }
}