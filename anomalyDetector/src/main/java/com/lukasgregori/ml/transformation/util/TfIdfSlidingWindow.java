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

import com.lukasgregori.ml.input.util.CustomLoggingEvent;

import java.util.HashMap;

/**
 * @author Lukas Gregori
 */
public class TfIdfSlidingWindow extends AbstractSlidingWindow {

    private static HashMap<String, Integer> docFrequencyMap = new HashMap<>();

    @Override
    public void addEntry(final CustomLoggingEvent loggingEvent) {
        super.addEntry(loggingEvent);
        docFrequencyMap.clear();
        slidingWindow.forEach(this::updateFrequencyMapForEvent);
    }

    private void updateFrequencyMapForEvent(CustomLoggingEvent loggingEvent) {
        loggingEvent.getTokenizedRepresentation().forEach(term ->
                docFrequencyMap.put(term, getCount(term) + 1));
    }

    public int getCount(final String token) {
        return docFrequencyMap.getOrDefault(token, 0);
    }
}
