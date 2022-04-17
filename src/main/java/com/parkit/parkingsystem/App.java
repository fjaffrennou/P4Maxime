package com.parkit.parkingsystem;

import com.parkit.parkingsystem.service.InteractiveShell;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import java.io.File;
// import org.apache.logging.log4j.core.LoggerContext;

public class App {

    private static final Logger logger = LogManager.getLogger("App");
    public static void main(String args[])     {
        LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
        File file = new File("/Users/maxime/IdeaProjects/parkingsystem/src/main/resources/log4j2.xml");
// this will force a reconfiguration
        context.setConfigLocation(file.toURI());

        logger.info("Initializing Parking System");
        InteractiveShell.loadInterface();
    }
}
