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

package com.lukasgregori.ml.math;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.math3.ml.clustering.DoublePoint;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 * @author Lukas Gregori
 */

public class DoublePointUtilsTests {

    @Test
    public void testPointAddition() {
        DoublePoint p0 = new DoublePoint(new double[]{1.0f, 2.0f, 3.0f});
        DoublePoint p1 = new DoublePoint(new double[]{4.0f, 5.0f, 6.0f});
        DoublePoint result = DoublePointUtils.add(p0, p1);
        Assert.assertNotNull(result);
        Assert.assertEquals(3, result.getPoint().length);
        Assert.assertEquals(p0.getPoint()[0] + p1.getPoint()[0], result.getPoint()[0], 0.1f);
        Assert.assertEquals(p0.getPoint()[1] + p1.getPoint()[1], result.getPoint()[1], 0.1f);
        Assert.assertEquals(p0.getPoint()[2] + p1.getPoint()[2], result.getPoint()[2], 0.1f);
    }

    @Test
    public void testPointSubtraction() {
        DoublePoint p0 = new DoublePoint(new double[]{1.0f, 2.0f, 3.0f});
        DoublePoint p1 = new DoublePoint(new double[]{4.0f, 5.0f, 6.0f});
        DoublePoint result = DoublePointUtils.subtract(p0, p1);
        Assert.assertNotNull(result);
        Assert.assertEquals(3, result.getPoint().length);
        Assert.assertEquals(p0.getPoint()[0] - p1.getPoint()[0], result.getPoint()[0], 0.1f);
        Assert.assertEquals(p0.getPoint()[1] - p1.getPoint()[1], result.getPoint()[1], 0.1f);
        Assert.assertEquals(p0.getPoint()[2] - p1.getPoint()[2], result.getPoint()[2], 0.1f);
    }

    @Test
    public void testScalarAddition() {
        DoublePoint p0 = new DoublePoint(new double[]{1.0f, 2.0f, 3.0f});
        double scalar = 2.0f;
        DoublePoint result = DoublePointUtils.addScalar(p0, scalar);
        Assert.assertNotNull(result);
        Assert.assertEquals(3, result.getPoint().length);
        Assert.assertEquals(p0.getPoint()[0] + scalar, result.getPoint()[0], 0.1f);
        Assert.assertEquals(p0.getPoint()[1] + scalar, result.getPoint()[1], 0.1f);
        Assert.assertEquals(p0.getPoint()[2] + scalar, result.getPoint()[2], 0.1f);
    }

    @Test
    public void testScalarMultiplication() {
        DoublePoint p0 = new DoublePoint(new double[]{1.0f, 2.0f, 3.0f});
        double scalar = 2.0f;
        DoublePoint result = DoublePointUtils.multiplyScalar(p0, scalar);
        Assert.assertNotNull(result);
        Assert.assertEquals(3, result.getPoint().length);
        Assert.assertEquals(p0.getPoint()[0] * scalar, result.getPoint()[0], 0.1f);
        Assert.assertEquals(p0.getPoint()[1] * scalar, result.getPoint()[1], 0.1f);
        Assert.assertEquals(p0.getPoint()[2] * scalar, result.getPoint()[2], 0.1f);
    }

    @Test
    public void testPointCreationFromList() {
        ArrayList<Double> list = new ArrayList<>();
        list.add((double) 0.0f);
        list.add((double) 1.0f);
        list.add((double) 2.0f);

        DoublePoint point = DoublePointUtils.fromList(list, 10);
        IntStream.range(0, CollectionUtils.size(list)).forEach(i -> Assert.assertEquals(i, point.getPoint()[i], 0.1f));
        IntStream.range(CollectionUtils.size(list), 10).forEach(i -> Assert.assertEquals(1.0f, point.getPoint()[i], 0.1f));
    }
}
