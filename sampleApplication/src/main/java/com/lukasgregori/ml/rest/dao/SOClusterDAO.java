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

package com.lukasgregori.ml.rest.dao;

import java.util.List;

/**
 * @author Lukas Gregori
 */
public class SOClusterDAO {

    private List<LoggingEventDao> events;

    private double weight;

    private double radius;

    public SOClusterDAO(double weight, double radius, List<LoggingEventDao> events) {
        this.weight = weight;
        this.radius = radius;
        this.events = events;
    }

    public List<LoggingEventDao> getEvents() {
        return events;
    }

    public double getWeight() {
        return weight;
    }

    public double getRadius() {
        return radius;
    }
}
