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

package com.lukasgregori.ml.transformation.util;

import com.lukasgregori.ml.transformation.NumericLogTransformer;
import com.lukasgregori.ml.input.util.CustomLoggingEvent;
import com.lukasgregori.ml.util.ContextProvider;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayDeque;

/**
 * Sliding window data-structure based on ArrayDeque used by NumericLogTransformers
 * to generate the numerical representations based on current events
 *
 * @author Lukas Gregori
 * @see NumericLogTransformer
 */
public abstract class AbstractSlidingWindow {

    private static final int WINDOW_SIZE = ContextProvider.getInt("sliding.window.size");

    static ArrayDeque<CustomLoggingEvent> slidingWindow = new ArrayDeque<>();

    public void addEntry(final CustomLoggingEvent loggingEvent) {
        slidingWindow.addLast(loggingEvent);

        if (CollectionUtils.size(slidingWindow) >= WINDOW_SIZE) {
            slidingWindow.removeFirst();
        }
    }
}
