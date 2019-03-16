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
import com.lukasgregori.ml.input.util.CustomLoggingEvent;
import org.reactivestreams.Subscriber;

/**
 * Abstract Input parser serving as basis for new parsers
 *
 * @author Lukas Gregori
 * @see CustomLoggingEvent
 */
public abstract class AbstractInputParser implements InputParser {

    Subscriber<? super CustomLoggingEvent> subscriber = null;

    @Override
    public void subscribe(Subscriber<? super CustomLoggingEvent> subscriber) {
        this.subscriber = subscriber;
    }
}