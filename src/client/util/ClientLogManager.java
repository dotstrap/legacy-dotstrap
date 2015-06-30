/**
 * ClientLogManager.java
 * JRE v1.8.0_45
 * 
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * The Enum ClientLogManager. (singleton)
 */
public enum ClientLogManager {
  INSTANCE;

  private static Logger logger = Logger.getLogger("client");

  public static Logger getLogger() {
    return logger;
  }

  /**
   * Initializes the client logs.
   */
  public static void initLogs() {

    File logDir = new File("logs");
    if (!logDir.exists()) {
      try {
        logDir.mkdir();
      } catch (SecurityException e) {
        System.out.println("ERROR: unable to create log directory...");
      }
    }

    try {
      FileInputStream is = new FileInputStream("logging.properties");

      LogManager.getLogManager().readConfiguration(is);

      logger = Logger.getLogger("client");

    } catch (IOException e) {
      System.out.println("ERROR: unable to load logging properties file...");
    }
    logger.info("===============Initialized " + logger.getName() + " log...");
  }
}
