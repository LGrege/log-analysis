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

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.IntStream;

/**
 * @author Lukas Gregori
 */

public class LoggingAnomalyTests {

    private static final int NUMB_ANOMALIES = 10;

    @Test
    public void testLoggingAnomalySorting() {
        ArrayList<LoggingAnomaly> anomalies = new ArrayList<>();
        IntStream.rangeClosed(0, NUMB_ANOMALIES).forEach(i ->
                anomalies.add(new LoggingAnomaly(null, i)));
        Assert.assertEquals(0.0f, anomalies.get(0).getAnomalyScore(), 0.01f);
        Collections.sort(anomalies);
        Assert.assertEquals(NUMB_ANOMALIES, anomalies.get(0).getAnomalyScore(), 0.01f);
    }
}
