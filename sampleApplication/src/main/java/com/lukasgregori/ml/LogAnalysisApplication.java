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

package com.lukasgregori.ml;

import com.lukasgregori.ml.input.InputFacade;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import org.slf4j.Logger;

@ImportResource("classpath:application-config.xml")
@SpringBootApplication
public class LogAnalysisApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogAnalysisApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(LogAnalysisApplication.class, args);

        InputFacade inputFacade = InputFacade.getInstance();
        inputFacade.parseInput();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Log Analysis Application Start...");
        }
    }
}
