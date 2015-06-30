/**
 * SubmitBatchUnitTest.java
 * JRE v1.8.0_45
 * 
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */

package client.communication;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.util.List;

import org.junit.*;

import server.ServerException;
import server.database.Database;
import server.database.DatabaseException;
import server.database.dao.*;

import shared.communication.DownloadBatchRequest;
import shared.communication.SubmitBatchRequest;
import shared.model.*;

/**
 * The Class SubmitBatchUnitTest.
 */
public class SubmitBatchUnitTest {

  private ClientCommunicator clientComm; // @formatter:off

  private Database db;

  private UserDAO testUserDAO;

  private BatchDAO testBatchDAO;

  private ProjectDAO testProjectDAO;

  private User testUser1;

  private User testUser2;

  private User testUser3;

  private Batch testBatch1;

  private Batch testBatch2;

  private Batch testBatch3;

  private Project testProject1;

  private Project testProject2;

  private Project testProject3; // @formatter:on

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
   * @throws DatabaseException the database exception
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

    testUser1 =
        new User("userTest1", "pass1", "first1", "last1", "email1", 1, 1);
    testUser2 =
        new User("userTest2", "pass2", "first2", "last2", "email2", 2, 2);
    testUser3 =
        new User("userTest3", "pass3", "first3", "last3", "email3", 3, 3);

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

    testBatch1 = new Batch(1, "someTestPath/batchTest1",
        testProject1.getProjectId(), Batch.INCOMPLETE, testUser1.getUserId());
    testBatch2 = new Batch(2, "someTestPath/batchTest2",
        testProject2.getProjectId(), Batch.INCOMPLETE, testUser2.getUserId());
    testBatch3 = new Batch(3, "someTestPath/batchTest3",
        testProject3.getProjectId(), Batch.INCOMPLETE, testUser3.getUserId());

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
    testUser1 = null;// @formatter:off
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
    testProjectDAO = null;// @formatter:on

    db = null;

    clientComm = null;

    // rollback this db transaction so changes aren't permanent
    db = new Database();
    db.startTransaction(); // FIXME: I wish there was a way to just roll this back
    db.initTables(); // but I need to save the db each testcase
    db.endTransaction(true);
    db = null;

  }

  /**
   * Valid input test.
   *
   * @throws ServerException the server exception
   * @throws MalformedURLException the malformed url exception
   */
  @Test
  public void validInputTest() throws ServerException, MalformedURLException {
    assertEquals("FAILED\n", // FIXME: is this failing because the controller isn't parsing the
                             // input string?
        clientComm
            .submitBatch(new SubmitBatchRequest("userTest1", "pass1", 1,
                "Jones,Fred,13;Rogers,Susan,42;,,;,,;Van Fleet,Bill,23"))
            .toString());
    // TODO: read db to see if the above info was saved & test that the models were updated too
  }

  /**
   * Null input test.
   *
   * @throws ServerException the server exception
   */
  @Test
  public void nullInputTest() throws ServerException {
    assertEquals("FAILED\n",
        clientComm
            .submitBatch(new SubmitBatchRequest("userTest1", "pass1", 1, ";"))
            .toString());
  }

  /**
   * Invalid password test.
   *
   * @throws ServerException the server exception
   */
  @Test
  public void invalidPasswordTest() throws ServerException {
    assertEquals(null, clientComm.submitBatch(
        new SubmitBatchRequest("userTest2", "INVALID", 2, "TEST,")));
  }

  /**
   * Mis matched password test.
   *
   * @throws ServerException the server exception
   */
  @Test
  public void misMatchedPasswordTest() throws ServerException {
    assertEquals(null, clientComm
        .submitBatch(new SubmitBatchRequest("userTest2", "pass3", 2, "TEST,")));
  }

  /**
   * Invalid username test.
   *
   * @throws ServerException the server exception
   */
  @Test
  public void invalidUsernameTest() throws ServerException {
    assertEquals(null, clientComm
        .submitBatch(new SubmitBatchRequest("pass3", "userTest3", 2, "TEST,")));
  }

  /**
   * Invalid creds test.
   *
   * @throws ServerException the server exception
   */
  @Test
  public void invalidCredsTest() throws ServerException {
    assertEquals(null, clientComm.submitBatch(
        new SubmitBatchRequest("10101001", "%$$%&^$%^*", 2, "TEST,")));
  }

  /**
   * Null creds test.
   *
   * @throws ServerException the server exception
   */
  @Test
  public void nullCredsTest() throws ServerException {
    assertEquals(null,
        clientComm.submitBatch(new SubmitBatchRequest("", "", 2, "TEST,")));
  }

}
