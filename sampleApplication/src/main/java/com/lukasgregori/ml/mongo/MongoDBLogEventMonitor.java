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

package com.lukasgregori.ml.mongo;

import com.lukasgregori.ml.anomaly.detection.util.LoggingAnomaly;
import com.lukasgregori.ml.clustering.impl.ClusteringContext;
import com.lukasgregori.ml.clustering.impl.sostream.SOCluster;
import com.lukasgregori.ml.input.util.BasicLogEventMonitor;
import com.lukasgregori.ml.mongo.util.MongoDBHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Based on regular LogEventMonitor class. Stores log events in Mongo-DB
 * to be used by SPA later on. Anomalies stored as dedicated collection.
 *
 * @author Lukas Gregori
 * @see com.lukasgregori.ml.input.util.BasicLogEventMonitor
 * @see com.lukasgregori.ml.mongo.util.MongoDBHelper
 */
public class MongoDBLogEventMonitor extends BasicLogEventMonitor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoDBLogEventMonitor.class);

    private static final String ANOMALIES_SAVED = "Saved {} anomalies in MongoDB";

    private static final String CLUSTERS_SAVED = "Saved {} clusters in MongoDB";

    @Resource
    private ClusteringContext clusteringContext;

    @Resource
    private MongoDBHelper mongoDBHelper;

    @Override
    protected void handleAnomalyUpdate(List<LoggingAnomaly> anomalies) {
        super.handleAnomalyUpdate(anomalies);
        mongoDBHelper.storeCollection(anomalies, LoggingAnomaly.class);
        LOGGER.info(ANOMALIES_SAVED, anomalies.size());
        backupCurrentClusters();
    }

    private void backupCurrentClusters() {
        List<SOCluster> clusters = new ArrayList<>(clusteringContext.getAllClusters());
        mongoDBHelper.storeCollection(clusters, SOCluster.class);
        LOGGER.info(CLUSTERS_SAVED, clusters.size());
    }
}