/**
 * GetFieldsUnitTest.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 24, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.communication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.*;

import server.ServerException;
import server.database.Database;
import server.database.dao.*;

import shared.communication.GetFieldsRequest;
import shared.communication.GetFieldsResponse;
import shared.model.*;

/**
 * The Class GetFieldsUnitTest.
 */
public class GetFieldsUnitTest {
   private ClientCommunicator clientComm;

   private Database   db;

   private UserDAO    testUserDAO;
   private ProjectDAO testProjectDAO;
   private FieldDAO   testFieldDAO;

   private User       testUser1;
   private User       testUser2;
   private User       testUser3;
   private Field      fieldTest1;
   private Field      fieldTest2;
   private Field      fieldTest3;
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

    testUserDAO = db.getUserDAO();
    testProjectDAO = db.getProjectDAO();
    testFieldDAO = db.getFieldDAO();
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

    testProject1 = new Project("testProject1", 10, 11, 12);
    testProject2 = new Project("testProject2", 20, 21, 22);
    testProject3 = new Project("testProject3", 30, 31, 32);

    testProjectDAO.create(testProject1);
    testProjectDAO.create(testProject2);
    testProjectDAO.create(testProject3);

    List<Project> allProjectes = testProjectDAO.getAll();
    assertEquals(3, allProjectes.size());

    fieldTest1 = new Field(1, "fieldTest1", "knownData1", "helpURL1", 1, 1, 1);
    fieldTest2 = new Field(2, "fieldTest2", "knownData2", "helpURL2", 2, 2, 2);
    fieldTest3 = new Field(3, "fieldTest3", "knownData3", "helpURL3", 3, 3, 3);

    testFieldDAO.create(fieldTest1);
    testFieldDAO.create(fieldTest2);
    testFieldDAO.create(fieldTest3);

    List<Field> allFields = testFieldDAO.getAll();
    assertEquals(3, allFields.size());

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
  }

  /**
   * Test valid field.
   */
  @Test
  public void testValidField() {
    GetFieldsResponse result1 = null;
    try {
      result1 =
          clientComm.getFields(new GetFieldsRequest("userTest1", "pass1", testProject1
              .getProjectId()));
    } catch (ServerException e) {
      fail("ERROR: failed vaild test...");
    }
    assertEquals(1, result1.getFields().size());
  }

  /**
   * Test valid field with no project.
   */
  @Test
  public void testValidFieldWithNoProject() {
    GetFieldsResponse result2 = null;
    try {
      result2 = clientComm.getFields(new GetFieldsRequest("userTest1", "pass1"));
    } catch (Exception e) {
      fail("ERROR: failed vaild test...");
    }
    assertEquals(3, result2.getFields().size());
  }

  /**
   * Invalid password test.
   */
  @Test
  public void invalidPasswordTest() {
    boolean isValidPassword = false;
    try {
      clientComm.getFields(new GetFieldsRequest("userTest2", "INVALID"));
      isValidPassword = true;
    } catch (Exception e) {
      isValidPassword = false;
    }
    assertEquals(false, isValidPassword);
  }

  /**
   * Mismatched password test.
   */
  @Test
  public void misMatchedPasswordTest() {
    boolean isValidPassword = false;
    try {
      clientComm.getFields(new GetFieldsRequest("userTest2", "pass3"));
      isValidPassword = true;
    } catch (Exception e) {
      isValidPassword = false;
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
      clientComm.getFields(new GetFieldsRequest("pass3", "userTest3"));
      isValidUsername = true;
    } catch (Exception e) {
      isValidUsername = false;
    }
    assertEquals(false, isValidUsername);
  }

  /**
   * Invalid creds test.
   */
  @Test
  public void invalidCredsTest() {
    boolean isValidCreds = false;
    try {
      clientComm.getFields(new GetFieldsRequest("userTest2", "userTest2"));
      isValidCreds = true;
    } catch (Exception e) {
      isValidCreds = false;
    }
    assertEquals(false, isValidCreds);
  }

  /**
   * Invalid user test.
   */
  @Test
  public void invalidUserTest() {
    boolean isValidUser = false;
    try {
      clientComm.getFields(new GetFieldsRequest("INVALID", "pass2", 1));
      isValidUser = true;
    } catch (Exception e) {
      isValidUser = false;
    }
    assertEquals(false, isValidUser);
  }

  /**
   * Invalid project id test.
   */
  @Test
  public void invalidProjectIdTest() {
    boolean isValidProject = false;
    try {
      clientComm.getFields(new GetFieldsRequest("userTest2", "pass2", 9999));
      isValidProject = true;
    } catch (Exception e) {
      isValidProject = false;
    }
    assertEquals(false, isValidProject);
  }
}
