/**
 * GetFieldsUnitTest.java
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
import server.database.dao.*;

import shared.communication.GetFieldsRequest;
import shared.model.*;

public class GetFieldsUnitTest {


  /** The logger used throughout the project. */
  static private Logger logger; // @formatter:off
  static {
    logger = Logger.getLogger(ClientCommunicator.LOG_NAME);
  }

  static private ClientCommunicator clientComm;

  static private Database   db;
  static private UserDAO    testUserDAO;
  static private ProjectDAO testProjectDAO;
  static private FieldDAO   testFieldDAO;
  static private User       testUser1;
  static private User       testUser2;
  static private User       testUser3;
  static private Field      fieldTest1;
  static private Field      fieldTest2;
  static private Field      fieldTest3;
  static private Project    projectTest1;
  static private Project    projectTest2;
  static private Project    projectTest3;  // @formatter:on

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    // Load database driver
    Database.initDriver();

    /*
     * Populate the database once per test-suite instead of per test-case because it is faster and
     * we wont be modifying it each test-case; just reading from it
     */
    db = new Database();
    db.startTransaction();

    testUserDAO = db.getUserDAO();
    testProjectDAO = db.getProjectDAO();
    testFieldDAO = db.getFieldDAO();
    clientComm = new ClientCommunicator();

    testUserDAO.initTable();
    testProjectDAO.initTable();
    testFieldDAO.initTable();

    testUser1 = new User("userTest1", "pass1", "first1", "last1", "email1", 1, 1);
    testUser2 = new User("userTest2", "pass2", "first2", "last2", "email2", 2, 2);
    testUser3 = new User("userTest3", "pass3", "first3", "last3", "email3", 3, 3);

    testUserDAO.create(testUser1);
    testUserDAO.create(testUser2);
    testUserDAO.create(testUser3);

    final List<User> allUseres = testUserDAO.getAll();
    assertEquals(3, allUseres.size());

    projectTest1 = new Project("projectTest1", 10, 11, 12);
    projectTest2 = new Project("projectTest2", 20, 21, 22);
    projectTest3 = new Project("projectTest3", 30, 31, 32);

    testProjectDAO.create(projectTest1);
    testProjectDAO.create(projectTest2);
    testProjectDAO.create(projectTest3);

    final List<Project> allProjectes = testProjectDAO.getAll();
    assertEquals(3, allProjectes.size());

    fieldTest1 = new Field(1, "fieldTest1", "knownData1", "helpURL1", 1, 1, 1);
    fieldTest2 = new Field(2, "fieldTest2", "knownData2", "helpURL2", 2, 2, 2);
    fieldTest3 = new Field(3, "fieldTest3", "knownData3", "helpURL3", 3, 3, 3);

    testFieldDAO.create(fieldTest1);
    testFieldDAO.create(fieldTest2);
    testFieldDAO.create(fieldTest3);

    final List<Field> allFields = testFieldDAO.getAll();
    assertEquals(3, allFields.size());
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    db.endTransaction(true);
    db = null;

    clientComm = null;
    testUserDAO = null;
    testFieldDAO = null;

    testUser1 = null;
    testUser2 = null;
    testUser3 = null;

    fieldTest1 = null;
    fieldTest2 = null;
    fieldTest3 = null;
    return;
  }

  @Before
  public void setUp() throws Exception {
    // quick check to ensure size hasnt changed for some reason
    final List<User> allUseres = testUserDAO.getAll();
    assertEquals(3, allUseres.size());

    final List<Project> allProjectes = testProjectDAO.getAll();
    assertEquals(3, allProjectes.size());

    final List<Field> allFieldes = testFieldDAO.getAll();
    assertEquals(3, allFieldes.size());
  }

  @After
  public void tearDown() throws Exception {
    // quick check to ensure size hasnt changed for some reason
    final List<User> allUseres = testUserDAO.getAll();
    assertEquals(3, allUseres.size());

    final List<Project> allProjectes = testProjectDAO.getAll();
    assertEquals(3, allProjectes.size());

    final List<Field> allFieldes = testFieldDAO.getAll();
    assertEquals(3, allFieldes.size());
  }

  // @Test
  // public void testValidField() {
  // GetFieldsResponse result1 = null;
  // try {
  // result1 = clientComm.getFields(new GetFieldsRequest("userTest1", "pass1", 1));
  // } catch (final ClientException e) {
  // fail("ERROR: failed vaild test...");
  // // logger.log(Level.SEVERE, e.toString());
  // // logger.log(Level.FINE, "STACKTRACE: ", e);
  // }
  // assertEquals(1, result1.getFields().size());
  // }
  //
  // @Test
  // public void testValidFieldWithNoProject() {
  // GetFieldsResponse result2 = null;
  // try {
  // result2 = clientComm.getFields(new GetFieldsRequest("userTest1", "pass1"));
  // } catch (final Exception e) {
  // fail("ERROR: failed vaild test...");
  // // logger.log(Level.SEVERE, e.toString());
  // // logger.log(Level.FINE, "STACKTRACE: ", e);
  // }
  // // assertEquals(1, result2.getFields().size());
  // }

  @Test
  public void invalidPasswordTest() {
    boolean isValidPassword = false;
    try {
      clientComm.getFields(new GetFieldsRequest("userTest2", "INVALID"));
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
      clientComm.getFields(new GetFieldsRequest("userTest2", "pass3"));
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
      clientComm.getFields(new GetFieldsRequest("pass3", "userTest3"));
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
      clientComm.getFields(new GetFieldsRequest("userTest2", "userTest2"));
      isValidCreds = true;
    } catch (final Exception e) {
      isValidCreds = false;
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
    }
    assertEquals(false, isValidCreds);
  }

  @Test
  public void invalidUserTest() {
    boolean isValidUser = false;
    try {
      clientComm.getFields(new GetFieldsRequest("INVALID", "pass2", 1));
      isValidUser = true;
    } catch (final Exception e) {
      isValidUser = false;
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
    }
    assertEquals(false, isValidUser);
  }

  @Test
  public void invalidProjectIdTest() {
    boolean isValidProject = false;
    try {
      clientComm.getFields(new GetFieldsRequest("userTest2", "pass2", 100));
      isValidProject = true;
    } catch (final Exception e) {
      isValidProject = false;
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
    }
    assertEquals(false, isValidProject);
  }
}
