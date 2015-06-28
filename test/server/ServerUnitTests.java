
package server;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Test;


public class ServerUnitTests {
  
  private static Logger logger;
  public final static String LOG_NAME = "serverTest";

  
  @After
  public void teardown() {}
  
  @Test
  public void test_1() {
    assertEquals("OK", "OK");
    assertTrue(true);
    assertFalse(false);
  }

  
  public static void main(String[] args) {
    try {
      final FileInputStream is = new FileInputStream("logging.properties");
      LogManager.getLogManager().readConfiguration(is);
      logger = Logger.getLogger(LOG_NAME);
    } catch (final IOException e) {
      Logger.getAnonymousLogger().severe("ERROR: unable to load logging properties file...");
      Logger.getAnonymousLogger().severe(e.getMessage());
    }

    logger.info("Running all server tests...");
    final String[] testClasses = new String[] {"server.ServerUnitTests", // @formatter:off
        "server.database.dao.BatchDAOUnitTest",
        "server.database.dao.FieldDAOUnitTest",
        "server.database.dao.ProjectDAOUnitTest",
        "server.database.dao.RecordDAOUnitTest",
        "server.database.dao.UserDAOUnitTest",
        }; // @formatter:on
    org.junit.runner.JUnitCore.main(testClasses);
  }
}
