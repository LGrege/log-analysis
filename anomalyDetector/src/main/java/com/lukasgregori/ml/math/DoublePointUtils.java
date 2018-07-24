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
import org.apache.commons.math3.util.MathArrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

/**
 * @author Lukas Gregori
 */
public class DoublePointUtils {

    public static DoublePoint add(DoublePoint p0, DoublePoint p1) {
        return new DoublePoint(MathArrays.ebeAdd(p0.getPoint(), p1.getPoint()));
    }

    public static DoublePoint subtract(DoublePoint p0, DoublePoint p1) {
        return new DoublePoint(MathArrays.ebeSubtract(p0.getPoint(), p1.getPoint()));
    }

    public static DoublePoint multiplyScalar(DoublePoint point, double scalar) {
        return new DoublePoint(MathArrays.scale(scalar, point.getPoint()));
    }

    public static DoublePoint divideScalar(DoublePoint point, double scalar) {
        return multiplyScalar(point, 1.0f / scalar);
    }

    public static DoublePoint addScalar(DoublePoint point, double scalar) {
        DoubleStream stream = Arrays.stream(point.getPoint()).parallel();
        return new DoublePoint(stream.map(val -> val + scalar).toArray());
    }

    public static DoublePoint fromList(ArrayList<Double> list, int size) {
        double[] point = new double[size];
        IntStream.range(0, size).parallel().forEach(counter -> {
            if (counter < CollectionUtils.size(list)) {
                point[counter] = list.get(counter);
            } else {
                point[counter] = 1.0f;
            }
        });
        return new DoublePoint(point);
    }
}
