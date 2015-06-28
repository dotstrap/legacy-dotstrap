
package client.communication;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.*;

import server.ServerException;
import server.database.Database;
import server.database.dao.UserDAO;

import shared.communication.ValidateUserRequest;
import shared.model.User;


public class ValidateUserUnitTest {





  static private ClientCommunicator clientComm;// @formatter:off

  private Database db;
  private UserDAO  testUserDAO;
  private User     testUser1;
  private User     testUser2;
  private User     testUser3; // @formatter:on

  
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    // Load database driver
    Database.initDriver();
  }

  
  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    return;
  }

  
  @Before
  public void setUp() throws Exception {
    // Empty & populate db for each test case (even though it is slower) to prevent against possible
    // db locking
    db = new Database();
    db.startTransaction();

    // Empty and prepare database for test case
    testUserDAO = db.getUserDAO();
    clientComm = new ClientCommunicator();

    db.initTables();

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
    testUser1 = null;
    testUser2 = null;
    testUser3 = null;

    testUserDAO = null;
    db = null;

    clientComm = null;

    // rollback this db transaction so changes aren't permanent
    db = new Database();
    db.startTransaction(); // FIXME: I wish there was a way to just roll this back
    db.initTables(); // but I need to save the db each testcase
    db.endTransaction(true);
    db = null;
  }

  
  @Test
  public void invalidPasswordTest() throws ServerException {
    assertEquals(null, clientComm.validateUser(new ValidateUserRequest("userTest2", "INVALID"))
        .getUser());
  }

  
  @Test
  public void misMatchedPasswordTest() throws ServerException {
    assertEquals("FALSE\n", clientComm.validateUser(new ValidateUserRequest("userTest2", "pass3"))
        .toString());
  }

  
  @Test
  public void invalidUsernameTest() throws ServerException {
    assertEquals(null, clientComm.validateUser(new ValidateUserRequest("pass3", "userTest3"))
        .getUser());
  }

  
  @Test
  public void invalidCredsTest() throws ServerException {
    assertEquals("FALSE\n",
        clientComm.validateUser(new ValidateUserRequest("10101001", "%$asdf$%&^$%^*")).toString());
  }

  
  @Test
  public void nullCredsTest() throws ServerException {
    assertEquals(null, clientComm.validateUser(new ValidateUserRequest("", "")).getUser());
  }
}
