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

import com.lukasgregori.ml.clustering.impl.CustomCluster;
import com.lukasgregori.ml.input.util.CustomLoggingEvent;
import com.lukasgregori.ml.input.util.LogEventMonitor;
import com.lukasgregori.ml.math.DoublePointUtils;
import com.lukasgregori.ml.util.ContextProvider;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.math3.ml.clustering.Clusterable;
import org.apache.commons.math3.ml.clustering.DoublePoint;
import org.apache.commons.math3.ml.distance.DistanceMeasure;
import org.apache.commons.math3.util.MathArrays;

import java.util.OptionalDouble;
import java.util.stream.Stream;

/**
 * @author Lukas Gregori
 * @see SOStreamClusterer
 */
public class SOCluster<T extends Clusterable> extends CustomCluster<T> {

    private static final int DIMENSIONS = ContextProvider.getInt("feature.vector.dimensions");

    public SOCluster(DoublePoint centroid, double radius, DistanceMeasure distanceMeasure) {
        super(centroid, radius, distanceMeasure);
    }

    @Override
    public void addPoint(T point) {
        super.addPoint(point);
        recalculateCentroid();
    }

    private void recalculateCentroid() {
        double[] newCentroid = new double[DIMENSIONS];

        for (T point : getPoints()) {
            newCentroid = MathArrays.ebeAdd(newCentroid, point.getPoint());
        }

        setCentroid(DoublePointUtils.divideScalar(
                new DoublePoint(newCentroid), getPoints().size()));
    }

    public double getWeight() {
        return (float) getPoints().size() / LogEventMonitor.getLogEventCount();
    }
}
