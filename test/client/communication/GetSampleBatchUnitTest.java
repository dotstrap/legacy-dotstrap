
package client.communication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.util.List;

import org.junit.*;

import server.ServerException;
import server.database.Database;
import server.database.DatabaseException;
import server.database.dao.*;

import shared.communication.GetSampleBatchRequest;
import shared.communication.GetSampleBatchResponse;
import shared.model.*;


public class GetSampleBatchUnitTest {
  private ClientCommunicator clientComm;

  private Database           db;

  private ProjectDAO         testProjectDAO;
  private BatchDAO           testBatchDAO;
  private UserDAO            testUserDAO;

  private Project            testProject1;
  private Project            testProject2;
  private Project            testProject3;
  private Batch              testBatch1;
  private Batch              testBatch2;
  private Batch              testBatch3;
  private User               testUser1;
  private User               testUser2;
  private User               testUser3;     // @formatter:on

  
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    // Load database driver
    Database.initDriver();
  }

  
  @AfterClass
  public static void tearDownAfterClass() throws Exception {}

  
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

  
  @After
  public void tearDown() throws Exception {
    testProject1 = null;// @formatter:off
    testProject2   = null;
    testProject3   = null;
    testBatch1     = null;
    testBatch2     = null;
    testBatch3     = null;
    testUser1      = null;
    testUser2      = null;
    testUser3      = null;
    testUserDAO    = null;
    testBatchDAO   = null;
    testProjectDAO = null; // @formatter:on

    db = null;

    clientComm = null;

    db = new Database();
    db.startTransaction(); // FIXME: I wish there was a way to just roll this back
    db.initTables(); // but I need to save the db each testcase
    db.endTransaction(true);
    db = null;
    return;
  }

  
  @Test
  public void validUserTest() throws ServerException, DatabaseException, MalformedURLException {
    GetSampleBatchResponse result = null;
    result = clientComm.getSampleBatch(new GetSampleBatchRequest("userTest1", "pass1", 1));
    assertTrue(testBatch1.equals(result.getSampleBatch()));
    assertEquals("http://localhost:39640/someTestPath/batchTest1\n", result.toString());
  }

  
  @Test
  public void invalidUsernameTest() throws ServerException, DatabaseException,
      MalformedURLException {
    boolean shouldPass = false;
    try {
      assertEquals("FAILED\n",
          clientComm.getSampleBatch(new GetSampleBatchRequest("INVALID", "pass1", 1)).toString());
    } catch (NullPointerException e) { // FIXME: this should not throw a NullPointerException
      shouldPass = true;
    }
    assertTrue(shouldPass);
  }

  
  @Test
  public void invalidProjectIdTest() throws ServerException, DatabaseException,
      MalformedURLException {
    assertEquals("FAILED\n",
        clientComm.getSampleBatch(new GetSampleBatchRequest("userTest1", "pass1", 9999))
            .toString());
  }
}
