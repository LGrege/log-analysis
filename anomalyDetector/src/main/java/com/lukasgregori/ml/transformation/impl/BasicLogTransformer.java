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

package com.lukasgregori.ml.transformation.impl;

import com.lukasgregori.ml.input.util.CustomLoggingEvent;
import com.lukasgregori.ml.util.ContextProvider;

import java.util.ArrayList;

/**
 * @author Lukas Gregori
 */
public class BasicLogTransformer {

    protected static final int MAX_DIMENSIONS = ContextProvider.getInt("feature.vector.dimensions");

    protected ArrayList<Double> createBasicNumericalRepresentation(CustomLoggingEvent event) {
        ArrayList<Double> numericalRepresentation = new ArrayList<>(MAX_DIMENSIONS);
        numericalRepresentation.add(Math.sin(event.getLevel().toInt()));
        numericalRepresentation.add(Math.sin(event.getThreadName().hashCode()));
        numericalRepresentation.add(Math.sin(event.getLogger().getName().hashCode()));
        return numericalRepresentation;
    }
}
