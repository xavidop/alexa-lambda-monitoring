package com.xavidop.alexa.helloworld.interceptors.response;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.interceptor.ResponseInterceptor;
import com.amazon.ask.model.Response;
import com.xavidop.alexa.helloworld.interceptors.request.LogRequestInterceptor;
import com.xavidop.alexa.helloworld.monitoring.TimeUtilities;
import io.sentry.Sentry;
import io.sentry.event.Breadcrumb;
import io.sentry.event.BreadcrumbBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

public class LogResponseInterceptor implements ResponseInterceptor {

    static final Logger logger = LogManager.getLogger(LogRequestInterceptor.class);
    @Override
    public void process(HandlerInput input, Optional<Response> output) {
        TimeUtilities.end = new Date().getTime();
        HashMap<String, String> data = new HashMap<String,String>();

        data.put("Response", output.get().toString());

        long time = TimeUtilities.end - TimeUtilities.start;

        data.put("Overall Time", String.valueOf(time) + "ms.");

        Sentry.getContext().recordBreadcrumb(
                new BreadcrumbBuilder()
                        .setLevel(Breadcrumb.Level.DEBUG)
                        .setTimestamp(new Date())
                        .setData(data)
                        .setMessage("New response").build()
        );

        Sentry.capture("request - " + Sentry.getContext().getTags().get("request_id"));
        Sentry.clearContext();;

        logger.info(output.toString());
    }
}