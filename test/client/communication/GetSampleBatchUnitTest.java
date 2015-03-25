package client.communication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.*;

import client.ClientException;

import server.database.Database;
import server.database.dao.*;

import shared.communication.GetSampleBatchRequest;
import shared.communication.GetSampleBatchResponse;
import shared.model.*;

public class GetSampleBatchUnitTest {
  /** The logger used throughout the project. */
  private static Logger logger; // @formatter:off
  static {
    logger = Logger.getLogger(ClientCommunicator.LOG_NAME);
  }

  private static ClientCommunicator clientComm;

  private static Database   db;
  private static ProjectDAO testProjectDAO;
  private static BatchDAO   testBatchDAO;
  private static UserDAO    testUserDAO;
  private static Project    testProject1;
  private static Project    testProject2;
  private static Project    testProject3;
  private static Batch      testBatch1;
  private static Batch      testBatch2;
  private static Batch      testBatch3;
  private static User       testUser1;
  private static User       testUser2;
  private static User       testUser3; // @formatter:on

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

    testProject1 = new Project("testProject1", 10, 11, 12);
    testProject2 = new Project("testProject2", 20, 21, 22);
    testProject3 = new Project("testProject3", 30, 31, 32);

    testProjectDAO.create(testProject1);
    testProjectDAO.create(testProject2);
    testProjectDAO.create(testProject3);

    List<Project> allProjectes = testProjectDAO.getAll();
    assertEquals(3, allProjectes.size());

    testBatch1 = new Batch("someTestPath/batchTest1", 1, 1);
    testBatch2 = new Batch("someTestPath/batchTest2", 2, 2);
    testBatch3 = new Batch("someTestPath/batchTest3", 3, 3);

    testBatchDAO.create(testBatch1);
    testBatchDAO.create(testBatch2);
    testBatchDAO.create(testBatch3);

    List<Batch> allBatches = testBatchDAO.getAll();
    assertEquals(3, allBatches.size());

    testUserDAO.initTable();
    testBatchDAO.initTable();

    testUser1 = new User("userTest1", "pass1", "first1", "last1", "email1", 1, testBatch1.getBatchId());
    testUser2 = new User("userTest2", "pass2", "first2", "last2", "email2", 2, testBatch2.getBatchId());
    testUser3 = new User("userTest3", "pass3", "first3", "last3", "email3", 3, testBatch3.getBatchId());

    testUserDAO.create(testUser1);
    testUserDAO.create(testUser2);
    testUserDAO.create(testUser3);

    List<User> allUseres = testUserDAO.getAll();
    assertEquals(3, allUseres.size());
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    db.endTransaction(false);

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

  @Test
  public void validUserTest() {
    GetSampleBatchResponse result = null;
    try {
      result =
          clientComm.getSampleBatch(new GetSampleBatchRequest("userTest1", "pass1", testBatch1
              .getBatchId()));
    } catch (ClientException e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
    }
    assertTrue(testBatch1.equals(result.getSampleBatch()));
  }

  @Test
  public void invalidUsernameTest() {
    GetSampleBatchResponse result = null;
    boolean shouldPass = false;
    try {
      result = clientComm.getSampleBatch(new GetSampleBatchRequest("INVALID", "pass1", 1));
    } catch (ClientException e) {
      shouldPass = true; // invalid creds will trigger an exception before we reach server facade
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
    }
    assertTrue(shouldPass);
    assertEquals(null, result);
    // assertEquals(false, result.isValidUser());
  }

  @Test
  public void invalidtestProject() {
    GetSampleBatchResponse result = null;
    boolean shouldPass = false;
    try {
      result = clientComm.getSampleBatch(new GetSampleBatchRequest("userTest1", "pass1", 9999));
    } catch (ClientException e) {
      shouldPass = true;
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
    }
    assertTrue(shouldPass);
    // assertEquals(null, result);
    // assertEquals(false, result.isValidUser());
  }
}
