/**
 * SearchUnitTest.java
 * JRE v1.8.0_45
 * 
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */

package client.communication;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.junit.*;

import server.ServerException;
import server.database.Database;
import server.database.DatabaseException;
import server.database.dao.*;

import shared.communication.SearchRequest;
import shared.communication.SearchResponse;
import shared.model.*;

/**
 * The Class SearchUnitTest.
 */
public class SearchUnitTest {

  private ClientCommunicator clientComm; // @formatter:off

  private Database db;

  private UserDAO testUserDAO;

  private ProjectDAO testProjectDAO;

  private FieldDAO testFieldDAO;

  private User testUser1;

  private User testUser2;

  private User testUser3;

  private Field fieldTest1;

  private Field fieldTest2;

  private Field fieldTest3;

  private Project testProject1;

  private Project testProject2;

  private Project testProject3;

  private List<Integer> fieldIDs;

  private List<Integer> badFieldIDs;

  private List<String> values;

  private List<String> badValues; // @formatter:on

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
    db.initTables();

    // Prepare database for test case
    testUserDAO = db.getUserDAO();
    testProjectDAO = db.getProjectDAO();
    testFieldDAO = db.getFieldDAO();
    clientComm = new ClientCommunicator();

    testUser1 =
        new User("userTest1", "pass1", "first1", "last1", "email1", 1, 1);
    testUser2 =
        new User("userTest2", "pass2", "first2", "last2", "email2", 2, 2);
    testUser3 =
        new User("userTest3", "pass3", "first3", "last3", "email3", 3, 3);

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

    // add test data
    this.fieldIDs = new ArrayList<Integer>();
    fieldIDs.add(1);

    values = new ArrayList<String>();
    values.add("test");

    badFieldIDs = new ArrayList<Integer>();
    badFieldIDs.add(100);

    badValues = new ArrayList<String>();
    values.add("BAD_INPUT");

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

    db = new Database();
    db.startTransaction(); // FIXME: I wish there was a way to just roll this back
    db.initTables(); // but I need to save the db each testcase
    db.endTransaction(true);
  }

  /**
   * Quick test.
   */
  @Test
  public void quickTest() {
    assertEquals(true, true);
  }

  /**
   * Valid user test.
   *
   * @throws ServerException the server exception
   * @throws DatabaseException the database exception
   * @throws MalformedURLException the malformed url exception
   */
  @Test
  public void validUserTest()
      throws ServerException, DatabaseException, MalformedURLException {
    SearchResponse result = clientComm
        .search(new SearchRequest("userTest1", "pass1", fieldIDs, values));
    assertEquals(result.getFoundRecords().size(), 1);
  }

  /**
   * Invalid field id test.
   *
   * @throws ServerException the server exception
   * @throws DatabaseException the database exception
   * @throws MalformedURLException the malformed url exception
   */
  @Test
  public void invalidFieldIdTest()
      throws ServerException, DatabaseException, MalformedURLException {
    SearchResponse result = clientComm
        .search(new SearchRequest("userTest1", "pass1", badFieldIDs, values));
    assertEquals(result.getFoundRecords().size(), 0);
  }

  /**
   * Invalid values test.
   *
   * @throws ServerException the server exception
   * @throws DatabaseException the database exception
   * @throws MalformedURLException the malformed url exception
   */
  @Test
  public void invalidValuesTest()
      throws ServerException, DatabaseException, MalformedURLException {
    SearchResponse result = clientComm
        .search(new SearchRequest("userTest1", "pass1", fieldIDs, badValues));
    assertEquals(result.getFoundRecords().size(), 0);
  }
}
