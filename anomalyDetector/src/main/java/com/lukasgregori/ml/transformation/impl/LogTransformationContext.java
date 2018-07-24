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

import com.lukasgregori.ml.transformation.NumericLogTransformer;
import com.lukasgregori.ml.transformation.util.AbstractSlidingWindow;
import com.lukasgregori.ml.input.util.CustomLoggingEvent;
import org.apache.commons.math3.ml.clustering.DoublePoint;

/**
 * @author Lukas Gregori
 */
public class LogTransformationContext {

    private NumericLogTransformer transformer;

    private AbstractSlidingWindow slidingWindow;

    public LogTransformationContext(final NumericLogTransformer transformer, final AbstractSlidingWindow slidingWindow) {
        this.transformer = transformer;
        this.slidingWindow = slidingWindow;
    }

    public DoublePoint transformLogEvent(final CustomLoggingEvent event) {
        return transformer.getNumberRepresentation(event, slidingWindow);
    }

    public void addLoggingEvent(final CustomLoggingEvent loggingEvent) {
        slidingWindow.addEntry(loggingEvent);
    }
}
