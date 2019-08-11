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

import com.lukasgregori.ml.anomaly.detection.util.LoggingAnomaly;
import com.lukasgregori.ml.input.InputFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

/**
 * Subscribed to InputParser which publishes new logging events to the monitor.
 * Calls clustering algorithm on new logging events as they arise and triggers
 * anomaly detection on fixed interval cycle.
 *
 * @author Lukas Gregori
 * @see com.lukasgregori.ml.input.parser.InputParser
 * @see InputFacade
 */
public class BasicLogEventMonitor extends AbstractLogEventMonitor {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasicLogEventMonitor.class);

    @Override
    protected void handleAnomalyUpdate(List<LoggingAnomaly> anomalies) {
        anomalies.forEach(anomaly -> LOGGER.error(anomaly.toString()));
    }
}
