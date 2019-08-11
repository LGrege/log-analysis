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

package com.lukasgregori.ml.mongo.codecs.impl;

import com.lukasgregori.ml.anomaly.detection.util.LoggingAnomaly;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;

/**
 * @author Lukas Gregori
 */
public class LoggingAnomalyCodec extends AbstractAnomalyCodec<LoggingAnomaly> {

    private static final String ANOMALY_SCORE = "anomalyScore";
    private static final String EVENT_ID = "_id";
    private static final String EVENT = "event";

    public LoggingAnomalyCodec(CodecRegistry registry) {
        super(registry, LoggingAnomaly.class);
    }

    @Override
    void populateEncoding(LoggingAnomaly source, Document target) {
        target.put(EVENT_ID, source.hashCode());
        target.put(ANOMALY_SCORE, source.getAnomalyScore());
        target.put(EVENT, source.getLoggingEvent());
    }
}
