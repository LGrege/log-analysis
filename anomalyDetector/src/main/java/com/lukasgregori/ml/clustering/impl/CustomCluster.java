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

package com.lukasgregori.ml.clustering.impl;

import org.apache.commons.math3.ml.clustering.Cluster;
import org.apache.commons.math3.ml.clustering.Clusterable;
import org.apache.commons.math3.ml.clustering.DoublePoint;
import org.apache.commons.math3.ml.distance.DistanceMeasure;

import java.util.Collection;

/**
 * @author Lukas Gregori
 */
public class CustomCluster<T extends Clusterable> extends Cluster<T> {

    private DistanceMeasure distanceMeasure;

    private DoublePoint centroid;

    private double radius;

    public CustomCluster(DoublePoint centroid, double radius, DistanceMeasure distanceMeasure) {
        this.distanceMeasure = distanceMeasure;
        this.centroid = centroid;
        this.radius = radius;
    }

    public double calculateDistance(double[] feature) {
        return distanceMeasure.compute(centroid.getPoint(), feature);
    }

    public void addPoints(Collection<T> points) {
        points.forEach(this::addPoint);
    }

    public void setCentroid(DoublePoint centroid) {
        this.centroid = centroid;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public DoublePoint getCentroid() {
        return centroid;
    }

    public double getRadius() {
        return radius;
    }

}
