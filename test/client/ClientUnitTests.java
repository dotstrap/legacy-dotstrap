/**
 * ClientUnitTests.java
 * JRE v1.8.0_45
 * 
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */

package client;

import static org.junit.Assert.*;

import org.junit.*;

/**
 * The Class ClientUnitTests.
 */
public class ClientUnitTests {

  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {
    // @formatter:off
    final String[] testClasses = new String[] {"client.ClientUnitTests",
        "client.communication.ValidateUserUnitTest",
        "client.communication.GetProjectsUnitTest",
        "client.communication.GetSampleBatchUnitTest",
        "client.communication.SubmitBatchUnitTest",
        "client.communication.DownloadBatchUnitTest",
        "client.communication.DownloadFileUnitTest",
        "client.communication.GetFieldsUnitTest",
        "client.communication.SearchUnitTest",}; // @formatter:on
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
