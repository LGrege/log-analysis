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

package com.lukasgregori.ml.clustering;

import com.lukasgregori.ml.clustering.impl.sostream.SOCluster;
import org.apache.commons.math3.ml.clustering.Clusterable;
import org.apache.commons.math3.ml.clustering.Clusterer;
import org.apache.commons.math3.ml.distance.DistanceMeasure;

import java.util.List;

/**
 * Interface used to define online clusterers that are able
 * to cluster points on the go as they are produced
 *
 * @author Lukas Gregori
 */
public abstract class OnlineClusterer<T extends Clusterable> extends Clusterer<T> {

    protected OnlineClusterer(DistanceMeasure measure) {
        super(measure);
    }

    public abstract List<SOCluster<T>> clusterNewFeature(T feature);

    public abstract List<SOCluster<T>> getCurrentClusters();
}