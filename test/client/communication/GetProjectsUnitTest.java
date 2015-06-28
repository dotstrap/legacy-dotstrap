
package client.communication;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.*;

import server.ServerException;
import server.database.Database;
import server.database.DatabaseException;
import server.database.dao.ProjectDAO;
import server.database.dao.UserDAO;

import shared.communication.GetProjectsRequest;
import shared.communication.GetProjectsResponse;
import shared.model.Project;
import shared.model.User;


public class GetProjectsUnitTest {
   private ClientCommunicator clientComm; // @formatter:off

   private Database   db;

   private UserDAO    testUserDAO;
   private ProjectDAO testProjectDAO;

   private User       testUser1;
   private User       testUser2;
   private User       testUser3;
   private Project    testProject1;
   private Project    testProject2;
   private Project    testProject3;  // @formatter:on

  
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    // Load database driver
    Database.initDriver();
  }

  
  @AfterClass
  public static void tearDownAfterClass() throws DatabaseException {
    return;
  }

  
  @Before
  public void setUp() throws Exception {
    db = new Database();
    db.startTransaction();

    // Prepare database for test case
    testUserDAO = db.getUserDAO();
    testProjectDAO = db.getProjectDAO();
    clientComm = new ClientCommunicator();

    db.initTables();

    testUser1 = new User("userTest1", "pass1", "first1", "last1", "email1", 1, 1);
    testUser2 = new User("userTest2", "pass2", "first2", "last2", "email2", 2, 2);
    testUser3 = new User("userTest3", "pass3", "first3", "last3", "email3", 3, 3);

    testUserDAO.create(testUser1);
    testUserDAO.create(testUser2);
    testUserDAO.create(testUser3);

    List<User> allUsers = testUserDAO.getAll();
    assertEquals(3, allUsers.size());

    testProject1 = new Project("testProject1", 10, 11, 12);
    testProject2 = new Project("testProject2", 20, 21, 22);
    testProject3 = new Project("testProject3", 30, 31, 32);

    testProjectDAO.create(testProject1);
    testProjectDAO.create(testProject2);
    testProjectDAO.create(testProject3);

    List<Project> allProjects = testProjectDAO.getAll();
    assertEquals(3, allProjects.size());

    db.endTransaction(true);
  }

  
  @After
  public void tearDown() throws Exception {
    testUser1      = null; // @formatter:off
    testUser2      = null;
    testUser3      = null;
    testProject1   = null;
    testProject2   = null;
    testProject3   = null;
    testUserDAO    = null;
    testProjectDAO = null;// @formatter:on

    db = null;

    db = new Database();
    db.startTransaction(); // FIXME: I wish there was a way to just roll this back
    db.initTables(); // but I need to save the db each testcase
    db.endTransaction(true);
    db = null;
  }

  
  @Test
  public void validProjectTest() throws ServerException, DatabaseException {
    GetProjectsResponse result =
        clientComm.getProjects(new GetProjectsRequest("userTest1", "pass1"));

    assertEquals(3, result.getProjects().size());

    assertEquals(1, result.getProjects().get(0).getProjectId());
    assertEquals(2, result.getProjects().get(1).getProjectId());
    assertEquals(3, result.getProjects().get(2).getProjectId());

    assertEquals("testProject1", result.getProjects().get(0).getTitle());
    assertEquals("testProject2", result.getProjects().get(1).getTitle());
    assertEquals("testProject3", result.getProjects().get(2).getTitle());
  }

  
  @Test
  public void invalidPasswordTest() throws ServerException {
    assertEquals(null, clientComm.getProjects(new GetProjectsRequest("userTest2", "INVALID")));
  }

  
  @Test
  public void misMatchedPasswordTest() throws ServerException {
    assertEquals(null, clientComm.getProjects(new GetProjectsRequest("userTest2", "pass3")));
  }

  
  @Test
  public void invalidUsernameTest() throws ServerException {
    assertEquals(null, clientComm.getProjects(new GetProjectsRequest("pass3", "userTest3")));
  }

  
  @Test
  public void invalidCredsTest() throws ServerException {
    assertEquals(null, clientComm.getProjects(new GetProjectsRequest("10101001", "%$$%&^$%^*")));
  }

  
  @Test
  public void nullCredsTest() throws ServerException {
    assertEquals(null, clientComm.getProjects(new GetProjectsRequest("", "")));
  }

}
