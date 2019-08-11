/*********************************************************************
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * Lukas Gregori
 * contact@lukasgregori.com
 * www.lukasgregori.com
 *
 * (c) 2018 by Lukas Gregori
 *********************************************************************/

package com.lukasgregori.ml.input.util;

import com.lukasgregori.ml.anomaly.detection.impl.AnomalyDetectionContext;
import com.lukasgregori.ml.anomaly.detection.util.LoggingAnomaly;
import com.lukasgregori.ml.clustering.impl.ClusteringContext;
import com.lukasgregori.ml.input.InputFacade;
import com.lukasgregori.ml.transformation.impl.LogTransformationContext;
import com.lukasgregori.ml.util.ContextProvider;
import org.apache.commons.collections4.CollectionUtils;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * Abstract base implementation. Subscribed to InputParser which publishes new logging events to the monitor.
 * Calls clustering algorithm on new logging events as they arise and triggers
 * anomaly detection on fixed interval cycle.
 *
 * @author Lukas Gregori
 * @see com.lukasgregori.ml.input.parser.InputParser
 * @see InputFacade
 */
public abstract class AbstractLogEventMonitor implements Subscriber<CustomLoggingEvent> {

    private static final int ANOMALY_DETECTION_FREQUENCY = ContextProvider.getInt("anomaly.detection.frequency");

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractLogEventMonitor.class);

    private static final String NEW_LOGGING_EVENT = "New custom logging event: %s";

    private static final String SUBSCRIBE = "Subscribe to publisher: %s";

    private static final String LOG_MONITOR_ERROR = "Log Monitor Error";

    private static final String COMPLETED = "Log Monitor Completed";

    @Resource(name = "anomalyDetectionContext")
    private AnomalyDetectionContext anomalyDetectionContext;

    @Resource(name = "logTransformationContext")
    private LogTransformationContext transformationContext;

    @Resource(name = "clusteringContext")
    private ClusteringContext clusteringContext;

    private static int logEventCount;

    protected abstract void handleAnomalyUpdate(List<LoggingAnomaly> anomalies);

    @Override
    public void onSubscribe(Subscription subscription) {
        LOGGER.info(String.format(SUBSCRIBE, subscription));
    }

    @Override
    public void onNext(CustomLoggingEvent customLoggingEvent) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format(NEW_LOGGING_EVENT,
                    Arrays.toString(customLoggingEvent.getPoint())));
        }

        transformationContext.addLoggingEvent(customLoggingEvent);
        clusteringContext.cluster(customLoggingEvent);
        updateAnomalies();
    }

    private void updateAnomalies() {
        if (logEventCount++ % ANOMALY_DETECTION_FREQUENCY == 0) {
            List<LoggingAnomaly> anomalies = anomalyDetectionContext.findAnomalies();
            LOGGER.error("Found Anomalies: " + CollectionUtils.size(anomalies));
            handleAnomalyUpdate(anomalies);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        LOGGER.error(LOG_MONITOR_ERROR, throwable);
    }

    @Override
    public void onComplete() {
        LOGGER.info(COMPLETED);
    }

    public static int getLogEventCount() {
        return logEventCount;
    }
}
