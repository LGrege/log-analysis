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

package com.lukasgregori.ml.mongo.codecs.impl;

import com.lukasgregori.ml.clustering.impl.sostream.SOCluster;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;

/**
 * @author Lukas Gregori
 */
public class SOClusterCodec extends AbstractAnomalyCodec<SOCluster> {

    private static final String CLUSTER_ID = "_id";
    private static final String WEIGHT = "weight";
    private static final String RADIUS = "radius";
    private static final String EVENTS = "events";

    public SOClusterCodec(CodecRegistry registry) {
        super(registry, SOCluster.class);
    }

    @Override
    void populateEncoding(SOCluster source, Document target) {
        target.put(CLUSTER_ID, source.hashCode());
        target.put(WEIGHT, source.getWeight());
        target.put(RADIUS, source.getRadius());
        target.put(EVENTS, source.getPoints());
    }
}
