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

import com.google.common.collect.Iterables;
import com.lukasgregori.ml.clustering.OnlineClusterer;
import com.lukasgregori.ml.clustering.util.SOClusterUtils;
import com.lukasgregori.ml.util.ContextProvider;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.math3.exception.ConvergenceException;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.ml.clustering.Clusterable;
import org.apache.commons.math3.ml.clustering.DoublePoint;
import org.apache.commons.math3.ml.distance.DistanceMeasure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <h1>Self Oraganized (SO) Density-Based Clustering</h1>
 * <p>
 * Online clustering algorithm based on self organizing structures.
 * Whenever a new point shall be added to a cluster, the following steps are executed:
 * <ul>
 * <li>Find closest cluster to new point</li>
 * <li>If distance below threshold, add point to cluster. If not create new cluster</li>
 * <li>Update clusters centers based on weighting formula</li>
 * <li>Merge possible overlapping clusters</li>
 * </ul>
 * <p>
 * For a more in depth view of this algorithm please see the paper attached
 *
 * @author Lukas Gregori
 * @see <a href="https://dl.acm.org/citation.cfm?id=2358881">SOStream</a>
 * @see SOClusterUtils
 * @see SOCluster
 */
public class SOStreamClusterer<T extends Clusterable> extends OnlineClusterer<T> {

    private static final double MERGE_DISTANCE = ContextProvider.getDouble("so.clusterer.merge.distance");

    private static final double CLUSTER_RADIUS_START_VALUE = 0.1f;

    private List<SOCluster<T>> clusters = new ArrayList<>();

    private double alpha;

    private int minPts;

    public SOStreamClusterer(DistanceMeasure distanceMeasure, int minPts, double alpha) {
        super(distanceMeasure);
        this.minPts = minPts;
        this.alpha = alpha;
    }

    @Override
    public List<SOCluster<T>> cluster(Collection<T> collection) {
        collection.forEach(this::clusterNewFeature);
        return clusters;
    }

    public synchronized List<SOCluster<T>> clusterNewFeature(T feature) {
        if (CollectionUtils.size(clusters) >= minPts) {
            sortClustersForFeature(feature.getPoint());
            SOCluster<T> closestCluster = clusters.remove(0);
            handleWinningCluster(closestCluster, feature);
        } else {
            addNewCluster(feature);
        }

        return clusters;
    }

    private void sortClustersForFeature(double[] featureVector) {
        clusters.sort(Comparator.comparingDouble(cluster ->
                Math.abs(cluster.calculateDistance(featureVector))));
    }

    private void handleWinningCluster(SOCluster<T> winningCluster, T feature) {
        List<SOCluster<T>> neighbors = getNeighbors(winningCluster);
        updateClusterRadius(winningCluster, neighbors);

        if (distance(feature, winningCluster.getCentroid()) <= winningCluster.getRadius()) {
            winningCluster.addPoint(feature);
            updateClusterCentroids(winningCluster, neighbors);
        } else {
            addNewCluster(feature);
        }

        clusters.add(mergeOverlap(winningCluster, neighbors));
    }

    private List<SOCluster<T>> getNeighbors(SOCluster<T> winningCluster) {
        sortClustersForFeature(winningCluster.getCentroid().getPoint());
        return clusters.stream().limit(minPts)
                .collect(Collectors.toList());
    }

    private void updateClusterRadius(SOCluster<T> winningCluster, List<SOCluster<T>> neighbors) {
        SOCluster<T> farthestNeighbor = Iterables.getLast(neighbors);
        double distance = distance(winningCluster, farthestNeighbor);
        winningCluster.setRadius(distance);
    }

    private void updateClusterCentroids(SOCluster<T> winningCluster, List<SOCluster<T>> neighbors) {
        neighbors.forEach(neighbor -> neighbor.setCentroid(
                SOClusterUtils.getUpdatedCentroid(winningCluster, neighbor, alpha)));
    }

    private SOCluster<T> mergeOverlap(SOCluster<T> winningCluster, List<SOCluster<T>> neighbors) {
        for (SOCluster<T> neighbor : neighbors) {
            if (distance(winningCluster, neighbor) < MERGE_DISTANCE) {
                winningCluster = mergeClusters(winningCluster, neighbor);
                clusters.remove(neighbor);
            }
        }
        return winningCluster;
    }

    private SOCluster<T> mergeClusters(SOCluster<T> cl0, SOCluster<T> cl1) {
        DoublePoint mergedCentroid = SOClusterUtils.calculateMergedCentroid(cl0, cl1);
        double r0 = distance(mergedCentroid, cl0.getCentroid()) + cl0.getRadius();
        double r1 = distance(mergedCentroid, cl1.getCentroid()) + cl1.getRadius();
        SOCluster<T> mergedCluster = new SOCluster<>(mergedCentroid, Math.max(r0, r1),
                getDistanceMeasure());
        mergedCluster.addPoints(cl0.getPoints());
        mergedCluster.addPoints(cl1.getPoints());
        return mergedCluster;
    }

    private void addNewCluster(T feature) {
        DoublePoint newCentroid = new DoublePoint(feature.getPoint());
        SOCluster<T> newCluster = new SOCluster<>(newCentroid,
                CLUSTER_RADIUS_START_VALUE, getDistanceMeasure());
        newCluster.addPoint(feature);
        clusters.add(newCluster);
    }

    private double distance(SOCluster<T> cl0, SOCluster<T> cl1) {
        return Math.abs(distance(cl0.getCentroid(), cl1.getCentroid()));
    }

    @Override
    public List<SOCluster<T>> getCurrentClusters() {
        return clusters;
    }

    @Override
    public void setCurrentClusters(List<SOCluster<T>> clusters) {
        this.clusters = clusters;
    }
}
