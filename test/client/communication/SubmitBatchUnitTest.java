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

import org.junit.*;

import server.ServerException;
import server.database.Database;
import server.database.DatabaseException;
import server.database.dao.*;

import shared.communication.SubmitBatchRequest;
import shared.model.*;

/**
 * The Class SubmitBatchUnitTest.
 */
public class SubmitBatchUnitTest {
  private ClientCommunicator clientComm;    // @formatter:off
  private Database   db;
  private UserDAO    testUserDAO;
  private BatchDAO   testBatchDAO;
  private ProjectDAO testProjectDAO;
  private User       testUser1;
  private User       testUser2;
  private User       testUser3;
  private Batch      testBatch1;
  private Batch      testBatch2;
  private Batch      testBatch3;
  private Project    testProject1;
  private Project    testProject2;
  private Project    testProject3;  // @formatter:on

  /**
   * Sets the up before class.
   *
   * @throws Exception the exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    // Load database driver
    Database.initDriver();
  }

  /**
   * Tear down after class.
   *
   * @throws DatabaseException
   */
  @AfterClass
  public static void tearDownAfterClass() throws DatabaseException {
    return;
  }

  /**
   * Sets the up.
   *
   * @throws Exception the exception
   */
  @Before
  public void setUp() throws Exception {
      db = new Database();
      db.startTransaction();

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
  }

  /**
   * Bad input test.
   */
  @Test
  public void badInputTest() {
    boolean isValidInput = true;
    try {
      isValidInput = true;
      clientComm.submitBatch(new SubmitBatchRequest("userTest1", "pass1", 1, ";"));
    } catch (ServerException e) {
      isValidInput = false;
    }
    assertEquals(false, isValidInput);
  }
}
