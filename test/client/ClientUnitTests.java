/**
 * ClientUnitTests.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 14, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client;

import static org.junit.Assert.*;

import org.junit.*;

// TODO: Auto-generated Javadoc
/**
 * The Class ClientUnitTests.
 */
public class ClientUnitTests {

    /**
     * The main method.
     *
     * @param args
     *            the arguments
     */
    public static void main(String[] args) {

        String[] testClasses = new String[] { "client.ClientUnitTests" };

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
