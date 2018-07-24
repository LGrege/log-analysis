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

package com.lukasgregori.ml.input.util;

import com.lukasgregori.ml.transformation.impl.LogTransformationContext;
import org.apache.commons.math3.ml.clustering.Clusterable;
import org.apache.commons.math3.ml.clustering.DoublePoint;
import org.apache.log4j.spi.LoggingEvent;

import java.util.List;

import org.apache.commons.text.StringTokenizer;

import javax.annotation.Nonnull;

/**
 * @author Lukas Gregori
 */
public class CustomLoggingEvent extends LoggingEvent implements Clusterable {

    private DoublePoint numericalRepresentation;

    public CustomLoggingEvent(final @Nonnull LoggingEvent loggingEvent, final @Nonnull LogTransformationContext transformationContext) {
        super(loggingEvent.getFQNOfLoggerClass(), loggingEvent.getLogger(),
                loggingEvent.getTimeStamp(), loggingEvent.getLevel(), loggingEvent.getMessage(),
                loggingEvent.getThreadName(), loggingEvent.getThrowableInformation(), loggingEvent.getNDC(),
                loggingEvent.getLocationInformation(), loggingEvent.getProperties());
        this.numericalRepresentation = transformationContext.transformLogEvent(this);
    }

    public List<String> getTokenizedRepresentation() {
        return new StringTokenizer(getRenderedMessage()).getTokenList();
    }

    @Override
    public double[] getPoint() {
        return numericalRepresentation.getPoint();
    }
}
