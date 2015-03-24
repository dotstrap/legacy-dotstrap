package client.communication;

import static org.junit.Assert.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.*;

import client.ClientException;

import server.database.Database;
import server.database.dao.BatchDAO;
import server.database.dao.UserDAO;

import shared.communication.GetSampleBatchRequest;
import shared.communication.GetSampleBatchResponse;
import shared.model.Batch;
import shared.model.User;

public class GetSampleBatchUnitTest {


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

    final List<User> allUseres = testUserDAO.getAll();
    assertEquals(3, allUseres.size());

    testBatch1 = new Batch("someTestPath/batchTest1", 1, 1);
    testBatch2 = new Batch("someTestPath/batchTest2", 2, 2);
    testBatch3 = new Batch("someTestPath/batchTest3", 3, 3);

    testBatchDAO.create(testBatch1);
    testBatchDAO.create(testBatch2);
    testBatchDAO.create(testBatch3);

    final List<Batch> allBatches = testBatchDAO.getAll();
    assertEquals(3, allBatches.size());
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    db.endTransaction(false);
    db = null;
    testUserDAO = null;
    return;
  }

  @Before
  public void setUp() throws Exception {
    // quick checks to ensure size hasn't changed for some reason
    final List<User> allUseres = testUserDAO.getAll();
    assertEquals(3, allUseres.size());

    final List<Batch> allBatches = testBatchDAO.getAll();
    assertEquals(3, allBatches.size());
  }

  @After
  public void tearDown() throws Exception {
    // quick checks to ensure size hasn't changed for some reason
    final List<User> allUseres = testUserDAO.getAll();
    assertEquals(3, allUseres.size());

    final List<Batch> allBatches = testBatchDAO.getAll();
    assertSame(3, allBatches.size());
  }

  @Test
  public void validUserTest() {
    GetSampleBatchResponse result = null;
    try {
      result = clientComm.getSampleBatch(new GetSampleBatchRequest("userTest1", "pass1", 1));
    } catch (ClientException e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
    }
    assertEquals(testBatch1, result.getSampleBatch());
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
  public void invalidProjectTest() {
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
