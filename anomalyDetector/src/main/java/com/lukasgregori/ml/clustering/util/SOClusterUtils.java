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
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.math3.ml.clustering.DoublePoint;
import com.lukasgregori.ml.math.DoublePointUtils;

/**
 * @author Lukas Gregori
 */
public class SOClusterUtils {

    public static DoublePoint getUpdatedCentroid(SOCluster cl0, SOCluster cl1, double alpha) {
        DoublePoint c0 = cl0.getCentroid();
        DoublePoint c1 = cl1.getCentroid();
        double betta = calculateBetta(cl0, cl1);
        DoublePoint newCentroid = DoublePointUtils.subtract(c0, c1);
        newCentroid = DoublePointUtils.multiplyScalar(newCentroid, alpha * betta);
        newCentroid = DoublePointUtils.add(c1, newCentroid);
        return newCentroid;
    }

    private static double calculateBetta(SOCluster cl0, SOCluster cl1) {
        double distance = cl0.calculateDistance(cl1.getCentroid().getPoint());
        return Math.exp(-1.0f * distance / (2.0f * Math.pow(cl0.getRadius(), 2.0f)));
    }

    public static DoublePoint calculateMergedCentroid(SOCluster cl0, SOCluster cl1) {
        int w0 = CollectionUtils.size(cl0.getPoints());
        int w1 = CollectionUtils.size(cl1.getPoints());

        DoublePoint m0 = DoublePointUtils.multiplyScalar(cl0.getCentroid(), w0);
        DoublePoint m1 = DoublePointUtils.multiplyScalar(cl1.getCentroid(), w1);

        DoublePoint mergedCentroid = DoublePointUtils.add(m0, m1);
        return DoublePointUtils.multiplyScalar(mergedCentroid, 1.0f / (w0 + w1));
    }

}
