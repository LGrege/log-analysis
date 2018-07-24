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

package com.lukasgregori.ml.transformation;

import com.lukasgregori.ml.transformation.util.AbstractSlidingWindow;
import com.lukasgregori.ml.input.util.CustomLoggingEvent;
import org.apache.commons.math3.ml.clustering.DoublePoint;

/**
 * Transformers used to retrieve a numerical representation from a logging event that
 * will later on be used as input for the clustering algorithm.
 *
 * @author Lukas Gregori
 */
public interface NumericLogTransformer {

    DoublePoint getNumberRepresentation(final CustomLoggingEvent event, final AbstractSlidingWindow slidingWindow);
}
