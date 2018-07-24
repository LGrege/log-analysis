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

package com.lukasgregori.ml.anomaly.detection.impl;

import com.lukasgregori.ml.anomaly.detection.AnomalyDetector;
import com.lukasgregori.ml.clustering.impl.sostream.SOCluster;
import com.lukasgregori.ml.anomaly.detection.util.LoggingAnomaly;
import com.lukasgregori.ml.input.util.CustomLoggingEvent;
import com.lukasgregori.ml.input.util.LogEventMonitor;
import com.lukasgregori.ml.util.ContextProvider;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Stream;

/**
 * Anomaly detector that uses cluster weight as well as distances to cluster center as measure.
 * <p>
 * Score is calculated by multiplying cluster weight with the normalized distance to the cluster center.
 * Points within a cluster are outliers if the score is above a predefined threshold.
 *
 * @author Lukas Gregori
 */
public class PositionBasedAnomalyDetector implements AnomalyDetector {

    private static final double PB_ANOMALY_THRESHOLD = ContextProvider.getDouble("pb.anomaly.threshold");

    private static final double MAX_OUTLIER_SCORE = 1.0f;

    @Override
    public List<LoggingAnomaly> findAnomalies(List<SOCluster<CustomLoggingEvent>> clusters) {
        List<LoggingAnomaly> allOutliers = new ArrayList<>();
        clusters.forEach(cluster -> allOutliers.addAll(getOutliersForCluster(cluster)));
        return allOutliers;
    }

    private List<LoggingAnomaly> getOutliersForCluster(SOCluster<CustomLoggingEvent> cluster) {
        double maxClusterDistance = calculateMaxClusterDistance(cluster);
        List<LoggingAnomaly> outliers = new ArrayList<>();
        cluster.getPoints().forEach(event ->
                getOutlierFromEvent(event, cluster, maxClusterDistance)
                        .ifPresent(outliers::add));
        return outliers;
    }

    private double calculateMaxClusterDistance(SOCluster<CustomLoggingEvent> cluster) {
        Stream<CustomLoggingEvent> eventStream = cluster.getPoints().stream();
        OptionalDouble maxDistance = eventStream.mapToDouble(event ->
                cluster.calculateDistance(event.getPoint())).max();
        return maxDistance.orElse(0.0f);
    }

    private Optional<LoggingAnomaly> getOutlierFromEvent(CustomLoggingEvent event, SOCluster<CustomLoggingEvent> cluster, double maxClusterDistance) {
        double outlierLevel = calculateOutlierLevel(cluster, event, maxClusterDistance);
        return outlierLevel < PB_ANOMALY_THRESHOLD ? Optional.empty() :
                Optional.of(new LoggingAnomaly(event, outlierLevel));
    }

    private double calculateOutlierLevel(SOCluster<CustomLoggingEvent> cluster, CustomLoggingEvent event, double maxClusterDistance) {
        double distance = cluster.calculateDistance(event.getPoint());
        double localOutlierFactor = distance / maxClusterDistance;
        double outlierLevel = localOutlierFactor * (1.0f - cluster.getWeight());
        return Double.isNaN(outlierLevel) ? MAX_OUTLIER_SCORE : outlierLevel;
    }
}