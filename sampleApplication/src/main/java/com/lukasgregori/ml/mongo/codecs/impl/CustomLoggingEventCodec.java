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

import com.google.gson.Gson;
import com.lukasgregori.ml.input.util.CustomLoggingEvent;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;

/**
 * @author Lukas Gregori
 */
public class CustomLoggingEventCodec extends AbstractAnomalyCodec<CustomLoggingEvent> {

    private static final String EVENT_JSON = "eventJSON";
    private static final String EVENT_ID = "_id";

    public CustomLoggingEventCodec(CodecRegistry registry) {
        super(registry, CustomLoggingEvent.class);
    }

    @Override
    void populateEncoding(CustomLoggingEvent source, Document target) {
        target.put(EVENT_ID, source.hashCode());
        String eventJSON = new Gson().toJson(source);
        target.put(EVENT_JSON, eventJSON);
    }
}
