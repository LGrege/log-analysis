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

package com.lukasgregori.ml.anomaly.detection.util;

import com.lukasgregori.ml.input.util.CustomLoggingEvent;

import javax.annotation.Nonnull;

/**
 * @author Lukas Gregori
 */
public class LoggingAnomaly implements Comparable<LoggingAnomaly> {

    private static final String ANOMALY_MESSAGE = "Anomaly: %s - %s";

    private CustomLoggingEvent loggingEvent;

    private double anomalyScore;

    public LoggingAnomaly(CustomLoggingEvent loggingEvent, double anomalyScore) {
        this.loggingEvent = loggingEvent;
        this.anomalyScore = anomalyScore;
    }

    @Override
    public String toString() {
        return String.format(ANOMALY_MESSAGE, loggingEvent.getMessage(), anomalyScore);
    }

    @Override
    public int compareTo(@Nonnull LoggingAnomaly anomaly) {
        return Double.compare(anomaly.anomalyScore, anomalyScore);
    }

    public CustomLoggingEvent getLoggingEvent() {
        return loggingEvent;
    }

    public double getAnomalyScore() {
        return anomalyScore;
    }
}
