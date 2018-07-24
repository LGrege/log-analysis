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
