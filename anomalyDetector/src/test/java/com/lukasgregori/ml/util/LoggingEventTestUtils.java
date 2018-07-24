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

package com.lukasgregori.ml.util;

import com.lukasgregori.ml.clustering.impl.sostream.SOCluster;
import com.lukasgregori.ml.transformation.impl.TfIdfLogTransformer;
import com.lukasgregori.ml.input.util.CustomLoggingEvent;
import com.lukasgregori.ml.transformation.impl.LogTransformationContext;
import com.lukasgregori.ml.transformation.util.TfIdfSlidingWindow;
import org.apache.commons.math3.ml.clustering.DoublePoint;
import org.apache.commons.math3.ml.distance.EuclideanDistance;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.assertj.core.util.DateUtil;

/**
 * @author Lukas Gregori
 */
public class LoggingEventTestUtils {

    public static CustomLoggingEvent createSampleLoggingEvent(String message, Level logLevel, String threadName) {
        long timeStamp = DateUtil.now().getTime();
        LoggingEvent loggingEvent = new LoggingEvent(null, Logger.getLogger("INFO"), timeStamp, logLevel,
                message, threadName, null, null, null, null);
        LogTransformationContext context = new LogTransformationContext(
                new TfIdfLogTransformer(), new TfIdfSlidingWindow());
        return new CustomLoggingEvent(loggingEvent, context);
    }

    public static SOCluster<CustomLoggingEvent> createSampleCluster(DoublePoint centroid) {
        EuclideanDistance distanceMeasure = new EuclideanDistance();
        return new SOCluster<>(centroid, 0.1f, distanceMeasure);
    }
}
