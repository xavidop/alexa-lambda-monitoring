package com.xavidop.alexa.helloworld.monitoring;

import io.sentry.Sentry;
import io.sentry.event.Breadcrumb;
import io.sentry.event.BreadcrumbBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

public final class LogUtilities {

    public static final Logger logger = LogManager.getLogger(LogUtilities.class);

    public static void log(String toLog) {

        logger.info(toLog);

        Sentry.getContext().recordBreadcrumb(
                new BreadcrumbBuilder()
                        .setLevel(Breadcrumb.Level.DEBUG)
                        .setTimestamp(new Date())
                        .setMessage(toLog).build()
        );


    }


}
