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

package com.lukasgregori.ml.transformation;

import com.lukasgregori.ml.transformation.impl.TfIdfLogTransformer;
import com.lukasgregori.ml.input.util.CustomLoggingEvent;
import com.lukasgregori.ml.transformation.impl.TfIdfLogTransformer;
import com.lukasgregori.ml.util.LoggingEventTestUtils;
import com.lukasgregori.ml.transformation.util.TfIdfSlidingWindow;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.math3.ml.clustering.DoublePoint;
import org.apache.log4j.Level;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.stream.IntStream;

/**
 * @author Lukas Gregori
 */

public class TfIdfLogTransformerTests {

    private static final String NUMB_REP_EMPTY = "Error, numerical representation empty";

    private static final String THREAD_NAME1 = "thread1";

    private static final String THREAD_NAME2 = "thread2";

    private static final String LOG_MESSAGE1 = "AA BB";

    private static final String LOG_MESSAGE2 = "BB CC";

    private static TfIdfLogTransformer logTransformer;

    @BeforeClass
    public static void init() {
        logTransformer = new TfIdfLogTransformer();
    }

    @Test
    public void testTfIdfTransformation() {
        TfIdfSlidingWindow slidingWindow = new TfIdfSlidingWindow();
        CustomLoggingEvent sampleEvent1 = LoggingEventTestUtils.createSampleLoggingEvent(LOG_MESSAGE1, Level.INFO, THREAD_NAME1);
        DoublePoint result = logTransformer.getNumberRepresentation(sampleEvent1, slidingWindow);
        checkCoherentDispersion(sampleEvent1, result);

        slidingWindow.addEntry(sampleEvent1);
        CustomLoggingEvent sampleEvent2 = LoggingEventTestUtils.createSampleLoggingEvent(LOG_MESSAGE2, Level.INFO, THREAD_NAME2);
        result = logTransformer.getNumberRepresentation(sampleEvent2, slidingWindow);
        Assert.assertNotNull(NUMB_REP_EMPTY, result);
        double[] numericalRepresentation = result.getPoint();
        Assert.assertEquals(0.25f, numericalRepresentation[3], 0.01f);
        Assert.assertEquals(0.5f, numericalRepresentation[4], 0.01f);
    }

    private void checkCoherentDispersion(CustomLoggingEvent sampleEvent, DoublePoint representation) {
        Assert.assertNotNull(NUMB_REP_EMPTY, representation);

        // Sliding window empty, all evenly distributed
        int numbTokens = CollectionUtils.size(sampleEvent.getTokenizedRepresentation());
        float avgTf = 1.0f / numbTokens;
        IntStream.range(3, 3 + numbTokens).forEach(i ->
                Assert.assertEquals(avgTf, representation.getPoint()[i], 0.01f));
    }
}
