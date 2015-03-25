/**
 * SearchUnitTestTest.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 24, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */

package client.communication;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.junit.*;

import client.ClientException;

import server.database.Database;
import server.database.DatabaseException;
import server.database.dao.*;

import shared.communication.SearchRequest;
import shared.communication.SearchResponse;
import shared.model.*;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchUnitTestTest.
 *
 * @author wm
 */
public class SearchUnitTestTest {



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
  static private Project    testProject1;
  static private Project    testProject2;
  static private Project    testProject3;  // @formatter:on

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
    db = new Database();
    db.startTransaction();

    // Prepare database for test case
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
   * Test search.
   *
   * @throws ClientException the client exception
   */
  @Test
  public void testSearch() throws ClientException, DatabaseException {
    db.startTransaction();
    ArrayList<Integer> fieldIDs = new ArrayList<Integer>();
    fieldIDs.add(1);

    ArrayList<String> values = new ArrayList<String>();
    values.add("davis");

    ArrayList<Integer> badFieldIDs = new ArrayList<Integer>();
    badFieldIDs.add(100);

    ArrayList<String> badValues = new ArrayList<String>();
    values.add("BAD_INPUT");

    // valid user
    SearchResponse result =
        clientComm.search(new SearchRequest("userTest1", "pass1", fieldIDs, values));
    assertEquals(result.getFoundRecords().size(), 1);
    // invalid fieldID
    SearchResponse result2 =
        clientComm.search(new SearchRequest("userTest1", "pass1", badFieldIDs, values));
    assertEquals(result2.getUrls().size(), 0);
    // invalid values
    SearchResponse result3 =
        clientComm.search(new SearchRequest("userTest1", "pass1", fieldIDs, badValues));
    assertEquals(result3.getFoundRecords().size(), 0);
    db.endTransaction(true);
  }
}
