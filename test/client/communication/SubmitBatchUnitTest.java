/**
 * SubmitBatchUnitTest.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 24, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.communication;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.logging.Logger;

import org.junit.*;

import server.ServerException;
import server.database.Database;
import server.database.DatabaseException;
import server.database.dao.*;

import shared.communication.SubmitBatchRequest;
import shared.communication.SubmitBatchResponse;
import shared.model.*;


/**
 * The Class SubmitBatchUnitTest.
 */
public class SubmitBatchUnitTest {



  /** The logger used throughout the project. */
  private static Logger logger; // @formatter:off
  static {
    logger = Logger.getLogger("server");
  }

  private static ClientCommunicator clientComm;

  private static Database   db;

  private static UserDAO    testUserDAO;
  private static BatchDAO   testBatchDAO;
  private static ProjectDAO testProjectDAO;

  private static User     testUser1;
  private static User     testUser2;
  private static User     testUser3;
  private static Batch    testBatch1;
  private static Batch    testBatch2;
  private static Batch    testBatch3;
  private static Project  testProject1;
  private static Project  testProject2;
  private static Project  testProject3;  // @formatter:on

  /**
   * Sets the up before class.
   *
   * @throws Exception the exception
   */
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
    testProjectDAO = db.getProjectDAO();
    clientComm = new ClientCommunicator();

    testUserDAO.initTable();
    testBatchDAO.initTable();
    testProjectDAO.initTable();

    testUser1 = new User("userTest1", "pass1", "first1", "last1", "email1", 1, 1);
    testUser2 = new User("userTest2", "pass2", "first2", "last2", "email2", 2, 2);
    testUser3 = new User("userTest3", "pass3", "first3", "last3", "email3", 3, 3);

    testUserDAO.create(testUser1);
    testUserDAO.create(testUser2);
    testUserDAO.create(testUser3);

    List<User> allUseres = testUserDAO.getAll();
    assertEquals(3, allUseres.size());

    testProject1 = new Project("testProject1", 10, 11, 12);
    testProject2 = new Project("testProject2", 20, 21, 22);
    testProject3 = new Project("testProject3", 30, 31, 32);

    testProjectDAO.create(testProject1);
    testProjectDAO.create(testProject2);
    testProjectDAO.create(testProject3);

    List<Project> allProjectes = testProjectDAO.getAll();
    assertEquals(3, allProjectes.size());

    testBatch1 =
        new Batch(1, "someTestPath/batchTest1", testProject1.getProjectId(), Batch.INCOMPLETE,
            testUser1.getUserId());
    testBatch2 =
        new Batch(2, "someTestPath/batchTest2", testProject2.getProjectId(), Batch.INCOMPLETE,
            testUser2.getUserId());
    testBatch3 =
        new Batch(3, "someTestPath/batchTest3", testProject3.getProjectId(), Batch.INCOMPLETE,
            testUser3.getUserId());

    testBatchDAO.create(testBatch1);
    testBatchDAO.create(testBatch2);
    testBatchDAO.create(testBatch3);

    List<Batch> allBatches = testBatchDAO.getAll();
    assertEquals(3, allBatches.size());
  }

  /**
   * Tear down after class.
   * @throws DatabaseException
   */
  @AfterClass
  public static void tearDownAfterClass() throws DatabaseException {
    // Roll back this transaction so changes are undone
    db.endTransaction(false);

    testUser1 = null;
    testUser2 = null;
    testUser3 = null;
    testBatch1 = null;
    testBatch2 = null;
    testBatch3 = null;
    testProject1 = null;
    testProject2 = null;
    testProject3 = null;

    testUserDAO = null;
    testBatchDAO = null;
    testProjectDAO = null;

    db = null;

    clientComm = null;

    return;
  }

  /**
   * Sets the up.
   *
   * @throws Exception the exception
   */
  @Before
  public void setUp() throws Exception {
    // quick checks to ensure size hasn't changed for some reason
    List<User> allUseres = testUserDAO.getAll();
    assertEquals(3, allUseres.size());

    List<Project> allProjectes = testProjectDAO.getAll();
    assertEquals(3, allProjectes.size());

    List<Batch> allBatches = testBatchDAO.getAll();
    assertEquals(3, allBatches.size());
  }

  /**
   * Tear down.
   *
   * @throws Exception the exception
   */
  @After
  public void tearDown() throws Exception {
    // quick checks to ensure size hasn't changed for some reason
    List<User> allUseres = testUserDAO.getAll();
    assertEquals(3, allUseres.size());

    List<Project> allProjectes = testProjectDAO.getAll();
    assertEquals(3, allProjectes.size());

    List<Batch> allBatches = testBatchDAO.getAll();
    assertEquals(3, allBatches.size());
  }

  /**
   * Bad input test.
   */
  @Test
  public void badInputTest() {
    SubmitBatchResponse result = null;
    boolean isValidInput = true;
    try {
      isValidInput = true;
      result =
          clientComm.submitBatch(new SubmitBatchRequest("userTest1", "pass1", testBatch1
              .getBatchId(), ";"));
    } catch (ServerException e) {
      isValidInput = false;

      // logger.log(Level.SEVERE, "STACKTRACE: ", e);
    }
    assertEquals(false, isValidInput);
    // assertEquals(result.isSuccess(), false);
  }
}
