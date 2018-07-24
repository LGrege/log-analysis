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

package com.lukasgregori.ml.clustering.util;

import com.lukasgregori.ml.clustering.impl.sostream.SOCluster;
import com.lukasgregori.ml.input.util.CustomLoggingEvent;
import com.lukasgregori.ml.util.LoggingEventTestUtils;
import org.apache.commons.math3.ml.clustering.DoublePoint;
import org.apache.log4j.Level;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Lukas Gregori
 */
public class SOClusterUtilsTests {

    private static final String THREAD_NAME = "thread1";

    private static final String LOG_MESSAGE = "AA BB";

    @Test
    public void testMidMergedCentroidCalculation() {
        SOCluster<CustomLoggingEvent> cl0 = createSampleClusterForCentroid(new DoublePoint(new double[]{0.0f, 0.0f}));
        SOCluster<CustomLoggingEvent> cl1 = createSampleClusterForCentroid(new DoublePoint(new double[]{1.0f, 1.0f}));
        DoublePoint mergedCentroid = SOClusterUtils.calculateMergedCentroid(cl0, cl1);
        Assert.assertNotNull(mergedCentroid);
        Assert.assertEquals(0.5f, mergedCentroid.getPoint()[0], 0.01f);
        Assert.assertEquals(0.5f, mergedCentroid.getPoint()[1], 0.01f);
    }

    @Test
    public void testUnequalMergedCentroidCalculation() {
        SOCluster<CustomLoggingEvent> cl0 = createSampleClusterForCentroid(new DoublePoint(new double[]{0.0f, 0.0f}));
        SOCluster<CustomLoggingEvent> cl1 = createSampleClusterForCentroid(new DoublePoint(new double[]{1.0f, 1.0f}));
        CustomLoggingEvent sampleEvent = LoggingEventTestUtils.createSampleLoggingEvent(LOG_MESSAGE, Level.INFO, THREAD_NAME);
        cl1.addPoint(sampleEvent);
        cl1.setCentroid(new DoublePoint(new double[]{1.0f, 1.0f}));
        DoublePoint mergedCentroid = SOClusterUtils.calculateMergedCentroid(cl0, cl1);
        Assert.assertNotNull(mergedCentroid);
        Assert.assertEquals(2, mergedCentroid.getPoint().length);
        Assert.assertEquals(2.0f / 3.0f, mergedCentroid.getPoint()[0], 0.01f);
        Assert.assertEquals(2.0f / 3.0f, mergedCentroid.getPoint()[1], 0.01f);
    }

    private SOCluster<CustomLoggingEvent> createSampleClusterForCentroid(DoublePoint centroid) {
        SOCluster<CustomLoggingEvent> sampleCluster = LoggingEventTestUtils.createSampleCluster(centroid);
        CustomLoggingEvent sampleEvent = LoggingEventTestUtils.createSampleLoggingEvent(LOG_MESSAGE, Level.INFO, THREAD_NAME);
        sampleCluster.addPoint(sampleEvent);
        sampleCluster.setCentroid(centroid);
        return sampleCluster;
    }
}
