/**
 * ValidateUserUnitTest.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 24, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.communication;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.*;

import server.database.Database;
import server.database.dao.UserDAO;

import shared.communication.ValidateUserRequest;
import shared.model.User;

// TODO: Auto-generated Javadoc
/**
 * The Class ValidateUserUnitTest.
 */
public class ValidateUserUnitTest {




  /** The logger used throughout the project. */
  private static Logger   logger;
  static {
    logger = Logger.getLogger(ClientCommunicator.LOG_NAME);
  }

  static private ClientCommunicator clientComm; // @formatter:off

  private Database db;
  private UserDAO  testUserDAO;
  private User     testUser1;
  private User     testUser2;
  private User     testUser3; // @formatter:on

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
   * @throws Exception the exception
   */
  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    return;
  }

  /**
   * Sets the up.
   *
   * @throws Exception the exception
   */
  @Before
  public void setUp() throws Exception {
    // Empty & populate db for each test (even though it is slower) case to prevent against possible
    // db locking
    db = new Database();
    db.startTransaction();
    db.initTables();

    // Empty and prepare database for test case
    testUserDAO = db.getUserDAO();
    clientComm = new ClientCommunicator();
    // testUserDAO.initTable();

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

    testUser1 = null;
    testUser2 = null;
    testUser3 = null;

    testUserDAO = null;
    db = null;

    clientComm = null;
  }

  /**
   * Invalid password test.
   */
  @Test
  public void invalidPasswordTest() {
    boolean isValidPassword = false;
    try {
      clientComm.validateUser(new ValidateUserRequest("userTest2", "INVALID"));
      isValidPassword = true;
    } catch (Exception e) {
      isValidPassword = false;
      logger.log(Level.SEVERE, "STACKTRACE: ", e);
    }
    assertEquals(false, isValidPassword);
  }

  /**
   * Mis matched password test.
   */
  @Test
  public void misMatchedPasswordTest() {
    boolean isValidPassword = false;
    try {
      clientComm.validateUser(new ValidateUserRequest("userTest2", "pass3"));
      isValidPassword = true;
    } catch (Exception e) {
      isValidPassword = false;
      logger.log(Level.SEVERE, "STACKTRACE: ", e);
    }
    assertEquals(false, isValidPassword);
  }

  /**
   * Invalid username test.
   */
  @Test
  public void invalidUsernameTest() {
    boolean isValidUsername = false;
    try {
      clientComm.validateUser(new ValidateUserRequest("pass3", "userTest3"));
      isValidUsername = true;
    } catch (Exception e) {
      isValidUsername = false;
      logger.log(Level.SEVERE, "STACKTRACE: ", e);
    }
    assertEquals(false, isValidUsername);
  }

  /**
   * Invalid creds test.
   */
  @Test
  public void invalidCredsTest() {
    // invalid credentials test
    boolean isValidCreds = false;
    try {
      clientComm.validateUser(new ValidateUserRequest("userTest2", "userTest2"));
      isValidCreds = true;
    } catch (Exception e) {
      isValidCreds = false;
      logger.log(Level.SEVERE, "STACKTRACE: ", e);
    }
    assertEquals(false, isValidCreds);
  }

  /**
   * Null password test.
   */
  @Test
  public void nullPasswordTest() {
    boolean isValidCreds = true;
    try {
      clientComm.validateUser(new ValidateUserRequest("userTest2", ""));
      isValidCreds = true;
    } catch (Exception e) {
      isValidCreds = false;
      logger.log(Level.SEVERE, "STACKTRACE: ", e);
    }
    assertEquals(false, isValidCreds);
  }

  /**
   * Null username test.
   */
  @Test
  public void nullUsernameTest() {
    boolean isValidCreds = true;
    try {
      clientComm.validateUser(new ValidateUserRequest("", "pass3"));
      isValidCreds = true;
    } catch (Exception e) {
      isValidCreds = false;
      logger.log(Level.SEVERE, "STACKTRACE: ", e);
    }
    assertEquals(false, isValidCreds);
  }

  /**
   * Null creds test.
   */
  @Test
  public void nullCredsTest() {
    boolean isValidCreds = true;
    try {
      clientComm.validateUser(new ValidateUserRequest("", ""));
      isValidCreds = true;
    } catch (Exception e) {
      isValidCreds = false;
      logger.log(Level.SEVERE, "STACKTRACE: ", e);
    }
    assertEquals(false, isValidCreds);
  }
}
