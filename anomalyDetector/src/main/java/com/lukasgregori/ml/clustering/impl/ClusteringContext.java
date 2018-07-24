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

import com.lukasgregori.ml.clustering.OnlineClusterer;
import com.lukasgregori.ml.clustering.impl.sostream.SOCluster;
import com.lukasgregori.ml.input.util.CustomLoggingEvent;

import java.util.List;

/**
 * @author Lukas Gregori
 */
public class ClusteringContext {

    private OnlineClusterer<CustomLoggingEvent> clusterer;

    public ClusteringContext(final OnlineClusterer<CustomLoggingEvent> clusterer) {
        this.clusterer = clusterer;
    }

    public void cluster(CustomLoggingEvent event) {
        clusterer.clusterNewFeature(event);
    }

    public List<SOCluster<CustomLoggingEvent>> getAllClusters() {
        return clusterer.getCurrentClusters();
    }
}
