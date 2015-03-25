/**
 * GetSampleBatchUnitTest.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 24, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.communication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.*;

import client.ClientException;

import server.database.Database;
import server.database.DatabaseException;
import server.database.dao.*;

import shared.communication.GetSampleBatchRequest;
import shared.communication.GetSampleBatchResponse;
import shared.model.*;

// TODO: Auto-generated Javadoc
/**
 * The Class GetSampleBatchUnitTest.
 */
public class GetSampleBatchUnitTest {
  /** The logger used throughout the project. */
  private static Logger logger; // @formatter:off
  static {
    logger = Logger.getLogger(ClientCommunicator.LOG_NAME);
  }

  private  ClientCommunicator clientComm;

  private  Database   db;
  private  ProjectDAO testProjectDAO;
  private  BatchDAO   testBatchDAO;
  private  UserDAO    testUserDAO;
  private  Project    testProject1;
  private  Project    testProject2;
  private  Project    testProject3;
  private  Batch      testBatch1;
  private  Batch      testBatch2;
  private  Batch      testBatch3;
  private  User       testUser1;
  private  User       testUser2;
  private  User       testUser3; // @formatter:on

  /**
   * Sets the up before class.
   *
   * @throws Exception the exception
   */
  @BeforeClass
  public static  void setUpBeforeClass() throws Exception {
    // Load database driver
    Database.initDriver();

  }

  /**
   * Tear down after class.
   *
   * @throws Exception the exception
   */
  @AfterClass
  public  static void tearDownAfterClass() throws Exception {}

  /**
   * Sets the up.
   *
   * @throws Exception the exception
   */
  @Before
  public void setUp() throws Exception {
    db = new Database();
    db.startTransaction();
    // clear db before adding test data to it
    db.initTables();

    testUserDAO = db.getUserDAO();
    testBatchDAO = db.getBatchDAO();
    testProjectDAO = db.getProjectDAO();
    clientComm = new ClientCommunicator();

    testBatch1 = new Batch(1, "someTestPath/batchTest1", 1, 0, -1);
    testBatch2 = new Batch(2, "someTestPath/batchTest2", 2, 0, 2);
    testBatch3 = new Batch(3, "someTestPath/batchTest3", 3, 0, 3);

    testBatchDAO.create(testBatch1);
    testBatchDAO.create(testBatch2);
    testBatchDAO.create(testBatch3);

    List<Batch> allBatches = testBatchDAO.getAll();
    assertEquals(3, allBatches.size());

    testProject1 = new Project(1, "testProject1", 10, 11, 12);
    testProject2 = new Project(2, "testProject2", 20, 21, 22);
    testProject3 = new Project(3, "testProject3", 30, 31, 32);

    testProjectDAO.create(testProject1);
    testProjectDAO.create(testProject2);
    testProjectDAO.create(testProject3);

    List<Project> allProjectes = testProjectDAO.getAll();
    assertEquals(3, allProjectes.size());

    testUser1 = new User("userTest1", "pass1", "first1", "last1", "email1", 1, 1);
    testUser2 = new User("userTest2", "pass2", "first2", "last2", "email2", 2, 2);
    testUser3 = new User("userTest3", "pass3", "first3", "last3", "email3", 3, 3);

    testUserDAO.create(testUser1);
    testUserDAO.create(testUser2);
    testUserDAO.create(testUser3);

    List<User> allUseres = testUserDAO.getAll();
    assertEquals(3, allUseres.size());

    db.endTransaction(true);
  }

  /**
   * Tear down.
   *
   * @throws Exception the exception
   */
  @After
  public void tearDown() throws Exception {
     // empty db and restore it to its original state
    db.startTransaction();
    db.initTables();
    db.endTransaction(true);

    testProject1 = null;
    testProject2 = null;
    testProject3 = null;
    testBatch1 = null;
    testBatch2 = null;
    testBatch3 = null;
    testUser1 = null;
    testUser2 = null;
    testUser3 = null;

    testUserDAO = null;
    testBatchDAO = null;
    testProjectDAO = null;

    db = null;

    clientComm = null;

    return;
  }

  /**
   * Valid user test.
   */
  @Test
  public void validUserTest() throws DatabaseException {
    GetSampleBatchResponse result = null;
    try {
      result = clientComm.getSampleBatch(new GetSampleBatchRequest("userTest1", "pass1", 1));
    } catch (ClientException e) {
      logger.log(Level.SEVERE, "STACKTRACE: ", e);
    }
    assertTrue(testBatch1.equals(result.getSampleBatch()));
  }

  /**
   * Invalid username test.
   */
  @Test
  public void invalidUsernameTest() throws DatabaseException {
    GetSampleBatchResponse result = null;
    boolean shouldPass = false;
    try {
      result = clientComm.getSampleBatch(new GetSampleBatchRequest("INVALID", "pass1", 1));
    } catch (ClientException e) {
      shouldPass = true; // invalid creds will trigger an exception before we reach server facade
      logger.log(Level.SEVERE, "STACKTRACE: ", e);
    }
    assertTrue(shouldPass);
    // assertEquals(null, result);
    // assertEquals(false, result.isValidUser());
  }

  /**
   * Invalidtest project.
   */
  @Test
  public void invalidtestProject() throws DatabaseException {
    GetSampleBatchResponse result = null;
    boolean shouldPass = false;
    try {
      result = clientComm.getSampleBatch(new GetSampleBatchRequest("userTest1", "pass1", 9999));
    } catch (ClientException e) {
      shouldPass = true;
      logger.log(Level.SEVERE, "STACKTRACE: ", e);
    }
    assertTrue(shouldPass);
    // assertEquals(null, result);
    // assertEquals(false, result.isValidUser());
  }
}
