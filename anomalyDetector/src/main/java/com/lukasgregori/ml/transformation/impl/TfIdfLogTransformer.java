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

import com.lukasgregori.ml.math.DoublePointUtils;
import com.lukasgregori.ml.transformation.util.AbstractSlidingWindow;
import com.lukasgregori.ml.transformation.NumericLogTransformer;
import com.lukasgregori.ml.input.util.CustomLoggingEvent;

import com.lukasgregori.ml.transformation.util.TfIdfSlidingWindow;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.math3.ml.clustering.DoublePoint;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukas Gregori
 */
public class TfIdfLogTransformer extends BasicLogTransformer implements NumericLogTransformer {

    @Override
    public DoublePoint getNumberRepresentation(CustomLoggingEvent event, final AbstractSlidingWindow slidingWindow) {
        if (!(slidingWindow instanceof TfIdfSlidingWindow)) {
            throw new InvalidParameterException();
        }

        ArrayList<Double> numericalRepresentation = createNumericalRepresentation(event, (TfIdfSlidingWindow) slidingWindow);
        return DoublePointUtils.fromList(numericalRepresentation, MAX_DIMENSIONS);
    }

    private ArrayList<Double> createNumericalRepresentation(CustomLoggingEvent event, final TfIdfSlidingWindow slidingWindow) {
        ArrayList<Double> numericalRepresentation = createBasicNumericalRepresentation(event);

        List<String> allTerms = event.getTokenizedRepresentation();
        allTerms.forEach(term -> numericalRepresentation.add(
                calculateTfIdfValue(allTerms, term, slidingWindow)));

        return numericalRepresentation;
    }

    private double calculateTfIdfValue(List<String> allTerms, String term, TfIdfSlidingWindow slidingWindow) {
        double termFrequency = calculateTermFrequency(allTerms, term);
        double documentFrequency = slidingWindow.getCount(term) + 1;
        return termFrequency / documentFrequency;
    }

    private double calculateTermFrequency(List<String> doc, String term) {
        double occurrences = doc.stream().filter(t -> t.equalsIgnoreCase(term)).count();
        return occurrences / CollectionUtils.size(doc);
    }
}
