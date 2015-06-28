
package client;

import static org.junit.Assert.*;

import org.junit.*;


public class ClientUnitTests {
  
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
        "client.communication.SearchUnitTest",
    }; // @formatter:on
    org.junit.runner.JUnitCore.main(testClasses);
  }

  
  @Before
  public void setup() {}

  
  @After
  public void teardown() {}

  
  @Test
  public void test_1() {
    assertEquals("OK", "OK");
    assertTrue(true);
    assertFalse(false);
  }
}
