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

package com.lukasgregori.ml.anomaly.detection.impl;

import com.lukasgregori.ml.clustering.impl.sostream.SOCluster;
import com.lukasgregori.ml.anomaly.detection.util.LoggingAnomaly;
import com.lukasgregori.ml.input.util.CustomLoggingEvent;
import com.lukasgregori.ml.util.LoggingEventTestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.math3.ml.clustering.DoublePoint;
import org.apache.log4j.Level;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Lukas Gregori
 */
public class PositionBasedAnomalyDetectorTests {

    private static final PositionBasedAnomalyDetector detector = new PositionBasedAnomalyDetector();

    private static final String THREAD_NAME = "thread1";

    private static final String LOG_MESSAGE = "TEST";

    @Test
    public void testSinglePointAnomaly() {
        CustomLoggingEvent sampleEvent = LoggingEventTestUtils.createSampleLoggingEvent(LOG_MESSAGE, Level.INFO, THREAD_NAME);
        SOCluster<CustomLoggingEvent> sampleCluster = LoggingEventTestUtils.createSampleCluster(new DoublePoint(sampleEvent.getPoint()));
        sampleCluster.addPoint(sampleEvent);
        List<LoggingAnomaly> anomalies = detector.findAnomalies(Collections.singletonList(sampleCluster));
        Assert.assertEquals(1, CollectionUtils.size(anomalies));
    }
}
