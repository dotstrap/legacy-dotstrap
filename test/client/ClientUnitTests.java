/**
 * ClientUnitTests.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 23, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.junit.*;

// TODO: Auto-generated Javadoc
/**
 * The Class ClientUnitTests.
 */
public class ClientUnitTests {
  /** The logger. */
  private static Logger logger;
  public final static String LOG_NAME = "clientTest";

  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {
    try {
      final FileInputStream is = new FileInputStream("logging.properties");
      LogManager.getLogManager().readConfiguration(is);
      logger = Logger.getLogger(LOG_NAME);
    } catch (final IOException e) {
      Logger.getAnonymousLogger().severe("ERROR: unable to load logging properties file...");
      Logger.getAnonymousLogger().severe(e.getMessage());
    }
    logger.info("Initialized " + LOG_NAME + " log...");
    // @formatter:off
    final String[] testClasses = new String[] {"client.ClientUnitTests",
        "client.communication.DownloadBatchUnitTest",
        "client.communication.GetFieldsUnitTest",
    };
    // @formatter:on
    // "client.communication.GetProjectsUnitTest",
    // "client.communication.ValidateUserUnitTest",
    logger.info("Running all client tests...");
    org.junit.runner.JUnitCore.main(testClasses);
  }

  /**
   * Setup.
   */
  @Before
  public void setup() {}

  /**
   * Teardown.
   */
  @After
  public void teardown() {}

  /**
   * Test_1.
   */
  @Test
  public void test_1() {
    assertEquals("OK", "OK");
    assertTrue(true);
    assertFalse(false);
  }
}
