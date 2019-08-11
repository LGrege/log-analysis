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
import com.lukasgregori.ml.anomaly.detection.util.LoggingAnomaly;
import com.lukasgregori.ml.clustering.impl.ClusteringContext;
import com.lukasgregori.ml.clustering.impl.sostream.SOCluster;
import com.lukasgregori.ml.input.util.CustomLoggingEvent;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author Lukas Gregori
 */
public class AnomalyDetectionContext {

    @Resource(name = "clusteringContext")
    private ClusteringContext clusteringContext;

    private AnomalyDetector anomalyDetector;

    public AnomalyDetectionContext(AnomalyDetector anomalyDetector) {
        this.anomalyDetector = anomalyDetector;
    }

    public List<LoggingAnomaly> findAnomalies() {
        List<SOCluster<CustomLoggingEvent>> currentClusters = clusteringContext.getAllClusters();
        List<LoggingAnomaly> anomalies = anomalyDetector.findAnomalies(currentClusters);
        Collections.sort(anomalies);
        return anomalies;
    }
}
