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

package com.lukasgregori.ml.util;

import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author Lukas Gregori
 */
public class ContextProvider {

    public static String getString(String name) throws MissingResourceException {
        ResourceBundle bundle = ResourceBundle.getBundle("application");
        return bundle.getString(name);
    }

    public static int getInt(String name) {
        Optional<String> property = Optional.of(getString(name));
        return Integer.parseInt(property.orElse("-1"));
    }

    public static double getDouble(String name) {
        Optional<String> property = Optional.of(getString(name));
        return Double.parseDouble(property.orElse("-1"));
    }

    public static boolean getBoolean(String name) {
        Optional<String> property = Optional.of(getString(name));
        return Boolean.valueOf(property.orElse("false"));
    }
}