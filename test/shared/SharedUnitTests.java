/**
 * SharedUnitTests.java
 * JRE v1.8.0_40
 * 
 * Created by William Myers on Mar 22, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared;

import static org.junit.Assert.*;

import org.junit.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.*;

// TODO: Auto-generated Javadoc
/**
 * The Class SharedUnitTests.
 */
public class SharedUnitTests {
    /** The logger. */
    private static Logger logger;
    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        try {
            final FileInputStream is = new FileInputStream("logging.properties");
            LogManager.getLogManager().readConfiguration(is);
            logger = Logger.getLogger("clientTest");
        } catch (final IOException e) {
            Logger.getAnonymousLogger().severe("ERROR: unable to load logging properties file...");
            Logger.getAnonymousLogger().severe(e.getMessage());
        }

        String[] testClasses = {"shared.SharedUnitTests", "shared.communication.DownloadBatchUnitTest"};
                //new String[] {"client.ClientUnitTests", "client.ValidateUserUnitTest",
                        //"client.GetFieldsUnitTest", "client.DownloadBatchUnitTest",
                        //"client.GetProjectsUnitTest", "client.GetSampleImageUnitTest",
                        //"client.SearchUnitTest", "client.SubmitBatchUnitTest",
                        //"client.DownloadFileUnitTest", "client.QualityCheckUnitTests"};

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
