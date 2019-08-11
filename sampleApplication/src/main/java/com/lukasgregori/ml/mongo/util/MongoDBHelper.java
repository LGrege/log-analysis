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

import com.lukasgregori.ml.mongo.codecs.AnomalyCodecProvider;
import com.lukasgregori.ml.util.ContextProvider;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoTimeoutException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.collections4.CollectionUtils;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * @author Lukas Gregori
 */
public class MongoDBHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoDBHelper.class);

    private static final String DB_CONNECTION_LOST = "MongoDB connection lost, proceeding without it";

    private static final String DB_CONNECTION_SETUP = "Trying to connect to MongoDB";

    private static final String DB_NAME = ContextProvider.getString("db.name");

    private static final String DB_HOST = ContextProvider.getString("db.host");

    private MongoDatabase mongoDatabase;

    private boolean isConnectionAlive;

    private MongoDBHelper() {
        CodecRegistry customRegistry = CodecRegistries.fromProviders(new AnomalyCodecProvider());
        CodecRegistry codecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(), customRegistry);
        MongoClientOptions.Builder options = MongoClientOptions.builder().codecRegistry(codecRegistry);
        MongoClient mongoClient = new MongoClient(DB_HOST, options.build());
        mongoDatabase = mongoClient.getDatabase(DB_NAME);
        isConnectionAlive = isConnectionAlive();
    }

    private boolean isConnectionAlive() {
        try {
            LOGGER.info(DB_CONNECTION_SETUP);
            mongoDatabase.runCommand(new Document("serverStatus", 1));
            return true;
        } catch (MongoTimeoutException ex) {
            LOGGER.error(DB_CONNECTION_LOST);
            return false;
        }
    }

    public <T> void storeCollection(List<T> items, Class<T> itemClass) {
        if (isConnectionAlive && CollectionUtils.isNotEmpty(items)) {
            try {
                MongoCollection<T> mongoCollection = CollectionTypeMapper.getCollectionForType(mongoDatabase, itemClass);
                mongoCollection.drop();
                mongoCollection.insertMany(items);
            } catch (MongoTimeoutException ex) {
                LOGGER.error(DB_CONNECTION_LOST);
                isConnectionAlive = false;
            }
        }
    }

    public <T> List<T> getCollection(Class<T> itemClass) {
        if (isConnectionAlive) {
            try {
                List<T> allItems = new ArrayList<>();
                MongoCollection<T> mongoCollection = CollectionTypeMapper.getCollectionForType(mongoDatabase, itemClass);
                MongoCursor<T> dbItems = mongoCollection.find().iterator();
                dbItems.forEachRemaining(allItems::add);
                return allItems;
            } catch (MongoTimeoutException ex) {
                LOGGER.error(DB_CONNECTION_LOST);
                isConnectionAlive = false;
            }
        }
        return new ArrayList<>();
    }
}
