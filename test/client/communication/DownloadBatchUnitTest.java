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
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.*;

import client.ClientException;

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
  private static Logger logger; // @formatter:off
  static {
    logger = Logger.getLogger(ClientCommunicator.LOG_NAME);
  }

  private static ClientCommunicator clientComm;

  private static Database db;
  private static UserDAO  testUserDAO;
  private static BatchDAO testBatchDAO;
  private static User     testUser1;
  private static User     testUser2;
  private static User     testUser3;
  private static Batch    testBatch1;
  private static Batch    testBatch2;
  private static Batch    testBatch3;  // @formatter:on

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    // Load database driver
    Database.initDriver();

    db = new Database();
    db.startTransaction();

    /*
     * Populate the database once per test-suite instead of per test-case because it is faster and
     * we wont be modifying it each test-case; just reading from it
     */
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
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    db.endTransaction(false);

    testUser1 = null;
    testUser2 = null;
    testUser3 = null;

    testBatch1 = null;
    testBatch2 = null;
    testBatch3 = null;

    testUserDAO = null;
    testBatchDAO = null;

    db = null;
    return;
  }

  @Before
  public void setUp() throws Exception {
    // quick checks to ensure size hasn't changed for some reason
    List<User> allUseres = testUserDAO.getAll();
    assertEquals(3, allUseres.size());

    List<Batch> allBatches = testBatchDAO.getAll();
    assertEquals(3, allBatches.size());
  }

  @After
  public void tearDown() throws Exception {
    // quick checks to ensure size hasn't changed for some reason
    List<User> allUseres = testUserDAO.getAll();
    assertEquals(3, allUseres.size());

    List<Batch> allBatches = testBatchDAO.getAll();
    assertEquals(3, allBatches.size());
  }

  @Test
  public void invalidUserTest() throws DatabaseException {
    boolean isValid = true;
    try {
      clientComm.downloadBatch(new DownloadBatchRequest("userTest1", "INVALID", 1));
    } catch (ClientException e) {
      isValid = false;
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
    }
    assertEquals(false, isValid);
  }

  @Test
  public void invalidProjectIdTest() throws DatabaseException {
    DownloadBatchResponse result2 = new DownloadBatchResponse();
    try {
      result2 = clientComm.downloadBatch(new DownloadBatchRequest("userTest2", "pass2", 100));
    } catch (ClientException e) {
      logger.fine("Caught invalid projectId test...");
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
    }
    assertEquals(null, result2.getFields());
  }
}
