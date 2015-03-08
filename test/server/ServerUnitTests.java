/**
 * ServerUnitTests.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 8, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package server;

import static org.junit.Assert.*;

import org.junit.*;

// TODO: Auto-generated Javadoc
/**
 * The Class ServerUnitTests.
 */
public class ServerUnitTests {
    
    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        String[] testClasses = new String[] { "server.ServerUnitTests",
                "server.database.UserDAOTest", "server.database.BatchDAOUnitTests",
                "server.database.FieldDAOUnitTest",
                "server.database.ProjectDAOUnitTest",
        "server.database.RecordDAOUnitTest" };
        
        org.junit.runner.JUnitCore.main(testClasses);
    }
    
    /**
     * Setup.
     */
    @Before
    public void setup() {
        
    }
    
    /**
     * Teardown.
     */
    @After
    public void teardown() {
        
    }
    
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
