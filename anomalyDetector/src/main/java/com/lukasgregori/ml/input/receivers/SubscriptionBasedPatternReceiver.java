package com.lukasgregori.ml.input.receivers;

import com.lukasgregori.ml.input.util.CustomLoggingEvent;
import com.lukasgregori.ml.transformation.impl.LogTransformationContext;
import com.lukasgregori.ml.util.ContextProvider;
import org.apache.log4j.receivers.varia.LogFilePatternReceiver;
import org.apache.log4j.spi.LoggingEvent;
import org.reactivestreams.Subscriber;

public class SubscriptionBasedPatternReceiver extends LogFilePatternReceiver {

    private static final String TIMESTAMP_FORMAT = ContextProvider.getString("input.timestamp.format");

    private static final String INPUT_FORMAT = ContextProvider.getString("input.logging.format");

    private Subscriber<? super CustomLoggingEvent> subscriber;

    private LogTransformationContext transformationContext;

    public SubscriptionBasedPatternReceiver(Subscriber<? super CustomLoggingEvent> subscriber
            , LogTransformationContext transformationContext) {
        this.transformationContext = transformationContext;
        this.subscriber = subscriber;
        setTimestampFormat(TIMESTAMP_FORMAT);
        setLogFormat(INPUT_FORMAT);
    }

    @Override
    public void doPost(LoggingEvent event) {
        subscriber.onNext(new CustomLoggingEvent(event, transformationContext));
    }
}