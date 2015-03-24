/**
 * ValidateUserUnitTest.java
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

import server.database.Database;
import server.database.dao.UserDAO;

import shared.communication.ValidateUserRequest;
import shared.model.User;

public class ValidateUserUnitTest {

  /** The logger used throughout the project. */
  private static Logger   logger;
  static {
    logger = Logger.getLogger(ClientCommunicator.LOG_NAME);
  }

  static private ClientCommunicator clientComm; // @formatter:off

  static private Database db;
  static private UserDAO  testUserDAO;
  static private User     testUser1;
  static private User     testUser2;
  static private User     testUser3; // @formatter:on

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    // "setUpBeforeClass");

    // Load database driver
    Database.initDriver();

    /*
     * Populate the database once per test-suite instead of per test-case because it is faster and
     * we wont be modifying it each test-case; just reading from it
     */
    db = new Database();
    db.startTransaction();

    // Prepare database for test case
    testUserDAO = db.getUserDAO();
    clientComm = new ClientCommunicator();

    testUserDAO.initTable();

    testUser1 = new User("userTest1", "pass1", "first1", "last1", "email1", 1, 1);
    testUser2 = new User("userTest2", "pass2", "first2", "last2", "email2", 2, 2);
    testUser3 = new User("userTest3", "pass3", "first3", "last3", "email3", 3, 3);

    testUserDAO.create(testUser1);
    testUserDAO.create(testUser2);
    testUserDAO.create(testUser3);

    final List<User> allUseres = testUserDAO.getAll();
    assertEquals(3, allUseres.size());
    // "tearDownAfterClass");
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    // "tearDownAfterClass");
    // Roll back this transaction so changes are undone
    db.endTransaction(false);

    testUser1 = null;
    testUser2 = null;
    testUser3 = null;

    testUserDAO = null;
    db = null;

    clientComm = null;
    // "tearDownAfterClass");
    return;
  }

  @Before
  public void setUp() throws Exception {
    // quick check to ensure size hasn't changed for some reason
    final List<User> allUseres = testUserDAO.getAll();
    assertEquals(3, allUseres.size());
  }

  @After
  public void tearDown() throws Exception {
    // quick check to ensure size hasn't changed for some reason
    final List<User> allUseres = testUserDAO.getAll();
    assertEquals(3, allUseres.size());
  }

  @Test
  public void invalidPasswordTest() {
    boolean isValidPassword = false;
    try {
      clientComm.validateUser(new ValidateUserRequest("userTest2", "INVALID"));
      isValidPassword = true;
    } catch (final Exception e) {
      isValidPassword = false;
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
    }
    assertEquals(false, isValidPassword);
  }

  @Test
  public void misMatchedPasswordTest() {
    boolean isValidPassword = false;
    try {
      clientComm.validateUser(new ValidateUserRequest("userTest2", "pass3"));
      isValidPassword = true;
    } catch (final Exception e) {
      isValidPassword = false;
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
    }
    assertEquals(false, isValidPassword);
  }

  @Test
  public void invalidUsernameTest() {
    boolean isValidUsername = false;
    try {
      clientComm.validateUser(new ValidateUserRequest("pass3", "userTest3"));
      isValidUsername = true;
    } catch (final Exception e) {
      isValidUsername = false;
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
    }
    assertEquals(false, isValidUsername);
  }

  @Test
  public void invalidCredsTest() {
    // invalid credentials test
    boolean isValidCreds = false;
    try {
      clientComm.validateUser(new ValidateUserRequest("userTest2", "userTest2"));
      isValidCreds = true;
    } catch (final Exception e) {
      isValidCreds = false;
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
    }
    assertEquals(false, isValidCreds);
  }

  @Test
  public void nullPasswordTest() {
    boolean isValidCreds = true;
    try {
      clientComm.validateUser(new ValidateUserRequest("userTest2", ""));
      isValidCreds = true;
    } catch (final Exception e) {
      isValidCreds = false;
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
    }
    assertEquals(false, isValidCreds);
  }

  @Test
  public void nullUsernameTest() {
    boolean isValidCreds = true;
    try {
      clientComm.validateUser(new ValidateUserRequest("", "pass3"));
      isValidCreds = true;
    } catch (final Exception e) {
      isValidCreds = false;
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
    }
    assertEquals(false, isValidCreds);
  }

  @Test
  public void nullCredsTest() {
    boolean isValidCreds = true;
    try {
      clientComm.validateUser(new ValidateUserRequest("", ""));
      isValidCreds = true;
    } catch (final Exception e) {
      isValidCreds = false;
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
    }
    assertEquals(false, isValidCreds);
  }
}
