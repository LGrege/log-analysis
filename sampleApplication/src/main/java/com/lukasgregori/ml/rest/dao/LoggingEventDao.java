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

/**
 * @author Lukas Gregori
 */
public class LoggingEventDao {

    private String level;

    private String logger;

    private String message;

    public LoggingEventDao(String level, String logger, String message) {
        this.level = level;
        this.logger = logger;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getLevel() {
        return level;
    }

    public String getLogger() {
        return logger;
    }
}
