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

package com.lukasgregori.ml.input.parser.impl;

import com.lukasgregori.ml.input.parser.InputParser;
import com.lukasgregori.ml.input.receivers.SubscriptionBasedPatternReceiver;
import com.lukasgregori.ml.input.util.CustomLoggingEvent;
import com.lukasgregori.ml.transformation.impl.LogTransformationContext;
import com.lukasgregori.ml.util.ContextProvider;

import javax.annotation.Resource;

/**
 * Input parser exentding log4j LogFilePatternReceiver
 * <p>
 * Reads a log file defined by property. By enabling tailing, the log file will be kept open
 * and the parser awaits further log lines
 *
 * @author Lukas Gregori
 * @see AbstractInputParser
 * @see CustomLoggingEvent
 */
public class FileInputParser extends AbstractInputParser implements InputParser {

    private static final String FILE_URL = ContextProvider.getString("input.file.url");

    private static final boolean TAILING = ContextProvider.getBoolean("input.tailing");

    @Resource(name = "logTransformationContext")
    private LogTransformationContext transformationContext;

    public void parseInput() {
        SubscriptionBasedPatternReceiver patternReceiver =
                new SubscriptionBasedPatternReceiver(subscriber, transformationContext);
        patternReceiver.setFileURL(FILE_URL);
        patternReceiver.setTailing(TAILING);
        patternReceiver.activateOptions();
    }
}