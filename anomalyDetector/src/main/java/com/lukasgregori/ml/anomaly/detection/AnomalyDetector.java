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

package com.lukasgregori.ml.anomaly.detection;

import com.lukasgregori.ml.anomaly.detection.util.LoggingAnomaly;
import com.lukasgregori.ml.clustering.impl.sostream.SOCluster;
import com.lukasgregori.ml.input.util.CustomLoggingEvent;

import java.util.List;

/**
 * Used for retrieving logging anomalies based on defined distance measures.
 *
 * @author Lukas Gregori
 * @see LoggingAnomaly
 */
public interface AnomalyDetector {

    List<LoggingAnomaly> findAnomalies(List<SOCluster<CustomLoggingEvent>> clusters);

}
