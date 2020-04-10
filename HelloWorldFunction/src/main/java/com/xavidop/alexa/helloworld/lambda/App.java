package com.xavidop.alexa.helloworld.lambda;

import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;
import com.xavidop.alexa.helloworld.handlers.*;
import com.xavidop.alexa.helloworld.interceptors.request.LocalizationRequestInterceptor;
import com.xavidop.alexa.helloworld.interceptors.request.LogRequestInterceptor;
import com.xavidop.alexa.helloworld.interceptors.response.LogResponseInterceptor;
import io.sentry.Sentry;

import java.util.UUID;

/**
 * Handler for requests to Lambda function.
 */
public class App extends SkillStreamHandler {

    private static Skill getSkill() {
        Sentry.init();
        return Skills.standard()
                .addRequestHandlers(
                        new CancelandStopIntentHandler(),
                        new HelloWorldIntentHandler(),
                        new HelpIntentHandler(),
                        new LaunchRequestHandler(),
                        new SessionEndedRequestHandler(),
                        new FallbackIntentHandler(),
                        new ErrorHandler())
                .addExceptionHandler(new MyExceptionHandler())
                .addRequestInterceptors(
                        new LogRequestInterceptor(),
                        new LocalizationRequestInterceptor())
                .addResponseInterceptors(new LogResponseInterceptor())
                // Add your skill id below
                //.withSkillId("[unique-value-here]")
                .build();
    }

    public App() {
        super(getSkill());
    }

}
