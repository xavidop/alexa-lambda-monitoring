package com.xavidop.alexa.helloworld.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.exception.AskSdkException;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import com.xavidop.alexa.helloworld.monitoring.LogUtilities;
import io.sentry.Sentry;
import io.sentry.event.Breadcrumb;
import io.sentry.event.BreadcrumbBuilder;

import java.util.Date;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.requestType;

public class LaunchRequestHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(requestType(LaunchRequest.class));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String speechText = "Welcome to the Alexa Skills Kit, you can say hello";
        LogUtilities.log("LaunchRequestIntent executed");
        throw new AskSdkException("this is an exception");
    }

}