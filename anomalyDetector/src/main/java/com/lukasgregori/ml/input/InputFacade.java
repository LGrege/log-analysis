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

package com.lukasgregori.ml.input;

import com.lukasgregori.ml.input.parser.InputParser;
import com.lukasgregori.ml.input.util.LogEventMonitor;
import io.reactivex.Flowable;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.Resource;

/**
 * @author Lukas Gregori
 */
public class InputFacade implements InitializingBean {

    private static InputFacade instance;

    @Resource(name = "logEventMonitor")
    private LogEventMonitor logEventMonitor;

    @Resource(name = "inputParser")
    private InputParser inputParser;

    public void parseInput() {
        Flowable.fromPublisher(inputParser).subscribe(logEventMonitor);
        inputParser.parseInput();
    }

    @Override
    public void afterPropertiesSet() {
        instance = this;
    }

    public static InputFacade getInstance() {
        return instance;
    }
}
