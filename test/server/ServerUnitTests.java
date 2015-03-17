/**
 * ServerUnitTests.java
 * JRE v1.7.0_76
 *
 * Created by William Myers on Mar 15, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package server;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.logging.*;

import org.junit.*;

import server.database.Database;

// TODO: Auto-generated Javadoc
/**
 * The Class ServerUnitTests.
 */
public class ServerUnitTests {

    /** The logger. */
    private static Logger logger;

    /**
     * Initializes the log.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private static void initLog() throws IOException {
        Level logLevel = Level.FINEST;
        String logFile = "logs/server.log";

        logger = Logger.getLogger("server");
        logger.setLevel(logLevel);
        logger.setUseParentHandlers(false);

        // Handler consoleHandler = new ConsoleHandler();
        // consoleHandler.setLevel(logLevel);
        // consoleHandler.setFormatter(new SimpleFormatter());
        // logger.addHandler(consoleHandler);

        // Set up 5 rolling logs each with a max file size of 3MB
        FileHandler fileHandler = new FileHandler(logFile, 0, 5, false);
        fileHandler.setLevel(logLevel);
        fileHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(fileHandler);
    }

    /**
     * Setup.
     */
    @Before
    public void setup() {
        try {
            initLog();
        } catch (IOException e) {
            System.out.println("Could not initialize log: " + e.getMessage());
        }
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

    /**
     * The main method.
     *
     * @param args
     *            the arguments
     */
    public static void main(String[] args) {
        //try {
            //Database.initDriver();
            //Database  db = new Database();
            //db.startTransaction();
            //db.initTables();
            //db.endTransaction(true);
        //} catch(Exception e){
            //logger.log(Level.SEVERE, e.toString());
            //logger.log(Level.FINE, "STACKTRACE: ", e);
        //}

        String[] testClasses = new String[] { "server.ServerUnitTests",
                "server.database.BatchDAOUnitTest",
                "server.database.FieldDAOUnitTest",
                "server.database.ProjectDAOUnitTest",
                "server.database.RecordDAOUnitTest",
                "server.database.UserDAOUnitTest" };
        org.junit.runner.JUnitCore.main(testClasses);
    }
}
