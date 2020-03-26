package com.xavidop.alexa.helloworld.interceptors.request;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.interceptor.RequestInterceptor;
import com.xavidop.alexa.helloworld.monitoring.TimeUtilities;
import io.sentry.Sentry;
import io.sentry.event.Breadcrumb;
import io.sentry.event.BreadcrumbBuilder;
import io.sentry.event.UserBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.HashMap;

public class LogRequestInterceptor implements RequestInterceptor {

    static final Logger logger = LogManager.getLogger(LogRequestInterceptor.class);
    @Override
    public void process(HandlerInput input) {
        TimeUtilities.start = new Date().getTime();
        HashMap<String, String> data = new HashMap<String,String>();
        Sentry.getContext().addTag("request_id", input.getRequestEnvelope().getRequest().getRequestId());
        Sentry.getContext().addTag("application_id", input.getRequestEnvelope().getSession().getApplication().getApplicationId());
        Sentry.getContext().addTag("session_id", input.getRequestEnvelope().getSession().getSessionId());


        data.put("Request", input.getRequestEnvelope().getRequest().toString());
        data.put("context", input.getRequestEnvelope().getContext().toString());
        data.put("Session", input.getRequestEnvelope().getSession().toString());
        data.put("Version", input.getRequestEnvelope().getVersion());

        HashMap<String, Object> userData = new HashMap<String,Object>();

        userData.put("device_id",input.getRequestEnvelope().getContext().getSystem().getDevice().getDeviceId());

        Sentry.getContext().setUser(
                new UserBuilder()
                        .setData(userData)
                        .setUsername(input.getRequestEnvelope().getSession().getUser().getUserId()).build()
        );

        Sentry.getContext().recordBreadcrumb(
                new BreadcrumbBuilder()
                        .setLevel(Breadcrumb.Level.DEBUG)
                        .setTimestamp(new Date())
                        .setData(data)
                        .setMessage("New request recieved").build()
        );

        logger.info(input.getRequest().toString());
    }
}