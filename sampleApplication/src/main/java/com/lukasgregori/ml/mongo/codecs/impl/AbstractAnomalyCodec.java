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

import org.apache.commons.lang3.NotImplementedException;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

/**
 * @author Lukas Gregori
 */
abstract class AbstractAnomalyCodec<T> implements Codec<T> {

    private Codec<Document> documentCodec;

    private Class<T> supportedClass;

    AbstractAnomalyCodec(CodecRegistry registry, Class<T> supportedClass) {
        documentCodec = registry.get(Document.class);
        this.supportedClass = supportedClass;
    }

    abstract void populateEncoding(T source, Document target);

    @Override
    public T decode(BsonReader bsonReader, DecoderContext decoderContext) {
        throw new NotImplementedException("Decode not implemented");
    }

    @Override
    public void encode(BsonWriter bsonWriter, T source, EncoderContext encoderContext) {
        Document document = new Document();
        populateEncoding(source,document);
        documentCodec.encode(bsonWriter, document, encoderContext);
    }

    @Override
    public Class<T> getEncoderClass() {
        return supportedClass;
    }
}
