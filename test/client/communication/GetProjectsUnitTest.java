/**
 * GetProjectsUnitTest.java
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

import client.ClientException;

import server.database.Database;
import server.database.dao.ProjectDAO;
import server.database.dao.UserDAO;

import shared.communication.GetProjectsRequest;
import shared.communication.GetProjectsResponse;
import shared.model.Project;
import shared.model.User;

public class GetProjectsUnitTest {

  /** The logger used throughout the project. */
  private static Logger logger; // @formatter:off
  static {
    logger = Logger.getLogger(ClientCommunicator.LOG_NAME);
  }

  static private ClientCommunicator clientComm;

  static private Database   db;
  static private UserDAO    testUserDAO;
  static private ProjectDAO testProjectDAO;
  static private User       testUser1;
  static private User       testUser2;
  static private User       testUser3;
  static private Project    testProject1;
  static private Project    testProject2;
  static private Project    testProject3;  // @formatter:on

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

    // Prepare database for test case
    testUserDAO = db.getUserDAO();
    testProjectDAO = db.getProjectDAO();
    clientComm = new ClientCommunicator();

    testUserDAO.initTable();
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
  }

  @AfterClass
  public static void tearDownAfterClass() {
    // Roll back this transaction so changes are undone
    db.endTransaction(false);

    testUser1 = null;
    testUser2 = null;
    testUser3 = null;

    testProject1 = null;
    testProject2 = null;
    testProject3 = null;

    testUserDAO = null;
    testProjectDAO = null;
    clientComm = null;

    db = null;
    return;
  }

  @Before
  public void setUp() throws Exception {
    // quick checks to ensure size hasn't changed for some reason
    List<User> allUseres = testUserDAO.getAll();
    assertEquals(3, allUseres.size());

    List<Project> allProjectes = testProjectDAO.getAll();
    assertEquals(3, allProjectes.size());
  }

  @After
  public void tearDown() throws Exception {
    // quick checks to ensure size hasn't changed for some reason
    List<User> allUseres = testUserDAO.getAll();
    assertEquals(3, allUseres.size());

    List<Project> allProjectes = testProjectDAO.getAll();
    assertEquals(3, allProjectes.size());
  }

  @Test
  public void validtestProject() {
    GetProjectsResponse result1 = null;
    try {
      result1 = clientComm.getProjects(new GetProjectsRequest("userTest1", "pass1"));
    } catch (ClientException e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
    }
    assertEquals(3, result1.getProjects().size());
  }

  @Test
  public void invalidPasswordTest() {
    boolean isValidPassword = true;
    try {
      isValidPassword = true;
      clientComm.getProjects(new GetProjectsRequest("userTest2", "INVALID"));
    } catch (Exception e) {
      isValidPassword = false;
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
    }
    assertEquals(false, isValidPassword);
  }

  @Test
  public void misMatchedPasswordTest() {
    boolean isValidPassword = true;
    try {
      clientComm.getProjects(new GetProjectsRequest("userTest2", "pass3"));
      isValidPassword = true;
    } catch (Exception e) {
      isValidPassword = false;
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
    }
    assertEquals(false, isValidPassword);
  }

  @Test
  public void invalidUsernameTest() {
    boolean isValidUsername = true;
    try {
      clientComm.getProjects(new GetProjectsRequest("pass3", "userTest3"));
      isValidUsername = true;
    } catch (Exception e) {
      isValidUsername = false;
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
    }
    assertEquals(false, isValidUsername);
  }

  @Test
  public void invalidCredsTest() {
    boolean isValidCreds = true;
    try {
      clientComm.getProjects(new GetProjectsRequest("userTest2", "userTest2"));
      isValidCreds = true;
    } catch (Exception e) {
      isValidCreds = false;
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
    }
    assertEquals(false, isValidCreds);
  }


}
