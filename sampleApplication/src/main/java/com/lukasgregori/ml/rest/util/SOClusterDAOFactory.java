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

package com.lukasgregori.ml.rest.util;

import com.lukasgregori.ml.clustering.impl.sostream.SOCluster;
import com.lukasgregori.ml.input.util.CustomLoggingEvent;
import com.lukasgregori.ml.rest.dao.LoggingEventDao;
import com.lukasgregori.ml.rest.dao.SOClusterDAO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lukas Gregori
 */
public class SOClusterDAOFactory {

    public static SOClusterDAO createCluster(SOCluster<CustomLoggingEvent> cluster) {
        List<LoggingEventDao> events = cluster.getPoints().stream()
                .map(SOClusterDAOFactory::createLoggingEvent)
                .collect(Collectors.toList());
        return new SOClusterDAO(cluster.getWeight(), cluster.getRadius(), events);
    }

    private static LoggingEventDao createLoggingEvent(CustomLoggingEvent event) {
        return new LoggingEventDao(event.getLevel().toString(),
                event.getLoggerName(), event.getMessage().toString());
    }
}
