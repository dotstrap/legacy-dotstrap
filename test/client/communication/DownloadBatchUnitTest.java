/**
 * DownloadBatchUnitTest.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 23, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.communication;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.logging.Logger;

import org.junit.*;

import client.ClientException;
import client.ClientUnitTests;

import server.database.Database;
import server.database.DatabaseException;
import server.database.dao.BatchDAO;
import server.database.dao.UserDAO;

import shared.communication.DownloadBatchRequest;
import shared.communication.DownloadBatchResponse;
import shared.model.Batch;
import shared.model.User;

public class DownloadBatchUnitTest {
    /** The logger used throughout the project. */
    private static Logger logger;
    static {
        logger = Logger.getLogger(ClientUnitTests.LOG_NAME);
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // Load database driver
        Database.initDriver();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        return;
    }


 // @formatter:off
    private client.communication.ClientCommunicator clientComm;

    private Database db;
    private UserDAO  testUserDAO;
    private BatchDAO testBatchDAO;

    private User  testUser1  = null;
    private User  testUser2  = null;
    private User  testUser3  = null;

    private Batch testBatch1 = null;
    private Batch testBatch2 = null;
    private Batch testBatch3 = null;
    // @formatter:on

    @Before
    public void setUp() throws Exception {
        logger.entering("shared.communication.DownloadBatchUnitTest", "setUp");

        db = new Database();
        db.startTransaction();

        // Prepare database for test case
        testUserDAO = db.getUserDAO();
        testBatchDAO = db.getBatchDAO();
        clientComm = new ClientCommunicator();

        testUserDAO.initTable();
        testBatchDAO.initTable();

        testUser1 = new User("userTest1", "pass1", "first1", "last1", "email1", 1, 1);
        testUser2 = new User("userTest2", "pass2", "first2", "last2", "email2", 2, 2);
        testUser3 = new User("userTest3", "pass3", "first3", "last3", "email3", 3, 3);

        testUserDAO.create(testUser1);
        testUserDAO.create(testUser2);
        testUserDAO.create(testUser3);

        List<User> allUseres = testUserDAO.getAll();
        assertEquals(3, allUseres.size());

        testBatch1 = new Batch("batchTest1", 10, 10);
        testBatch2 = new Batch("batchTest2", 5, 5);
        testBatch3 = new Batch("batchTest3", 1, 0);

        testBatchDAO.create(testBatch1);
        testBatchDAO.create(testBatch2);
        testBatchDAO.create(testBatch3);

        List<Batch> allBatches = testBatchDAO.getAll();
        assertEquals(3, allBatches.size());

        logger.exiting("shared.communication.DownloadBatchUnitTest", "setUp");
    }

    @After
    public void tearDown() throws Exception {
        db.endTransaction(false);
        db = null;
        testUserDAO = null;
    }

    @Test
    public void testDownloadBatch() throws DatabaseException {
        logger.entering("shared.communication.DownloadBatchUnitTest", "testDownloadBatch");

        // invalid user
        boolean isValid = true;
        try {
            clientComm.downloadBatch(new DownloadBatchRequest("userTest1", "INVALID", 1));
        } catch (ClientException e) {
            isValid = false;
        }
        assertEquals(false, isValid);

        // invalid projectId
        DownloadBatchResponse result2 = new DownloadBatchResponse();
        try {
            result2 = clientComm.downloadBatch(new DownloadBatchRequest("userTest2", "pass2", 100));
        } catch (ClientException e) {
            logger.finer("Caught invalid projectId test...");
        }
        assertEquals(null, result2.getFields());

        logger.exiting("shared.communication.DownloadBatchUnitTest", "testDownloadBatch");
    }
}
