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

package com.lukasgregori.ml.clustering.impl.sostream;

import com.lukasgregori.ml.input.util.CustomLoggingEvent;
import com.lukasgregori.ml.util.LoggingEventTestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.math3.ml.distance.EuclideanDistance;
import org.apache.log4j.Level;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author Lukas Gregori
 */
public class SOStreamClustererTests {

    private static final String LOG_MESSAGE1 = "AA AA AA AA AA";

    private static final String LOG_MESSAGE2 = "BB BB BB BB BB";

    private static final String THREAD_NAME1 = "thread1";

    private static final String THREAD_NAME2 = "thread2";

    private static final int MIN_POINTS = 2;

    private static final double ALPHA = 0.5f;

    @Test
    public void testSOStreamClustering() {
        ArrayList<CustomLoggingEvent> allLoggingEvents = new ArrayList<>();
        allLoggingEvents.add(LoggingEventTestUtils.createSampleLoggingEvent(LOG_MESSAGE1, Level.ERROR, THREAD_NAME1));
        IntStream.range(0, 10).forEach(i -> allLoggingEvents.add(LoggingEventTestUtils.createSampleLoggingEvent(LOG_MESSAGE2, Level.INFO, THREAD_NAME2)));
        SOStreamClusterer<CustomLoggingEvent> clusterer = new SOStreamClusterer<>(new EuclideanDistance(), MIN_POINTS, ALPHA);
        List<SOCluster<CustomLoggingEvent>> clusters = clusterer.cluster(allLoggingEvents);
        Assert.assertNotNull(clusterer);
        Assert.assertEquals(2, CollectionUtils.size(clusters));
    }
}
