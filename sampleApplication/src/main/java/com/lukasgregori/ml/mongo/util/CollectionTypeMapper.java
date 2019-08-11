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

package com.lukasgregori.ml.mongo.util;

import com.lukasgregori.ml.anomaly.detection.util.LoggingAnomaly;
import com.lukasgregori.ml.clustering.impl.sostream.SOCluster;
import com.lukasgregori.ml.util.ContextProvider;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Mapping an input class type to a collection name within the DB
 *
 * @author Lukas Gregori
 * @see com.lukasgregori.ml.mongo.util.MongoDBHelper
 */
class CollectionTypeMapper {

    private static final String ANOMALY_COLLECTION = ContextProvider.getString("db.collection.anomalies");

    private static final String CLUSTER_COLLECTION = ContextProvider.getString("db.collection.clusters");

    private static final String UNKNOWN_TYPE = "Unknown type provided, no collection in db";

    static <T> MongoCollection<T> getCollectionForType(MongoDatabase mongoDatabase, Class<T> itemClass) {
        if (itemClass.equals(LoggingAnomaly.class)) {
            return mongoDatabase.getCollection(ANOMALY_COLLECTION, itemClass);
        } else if (itemClass.equals(SOCluster.class)) {
            return mongoDatabase.getCollection(CLUSTER_COLLECTION, itemClass);
        } else {
            throw new UnsupportedOperationException(UNKNOWN_TYPE);
        }
    }
}
