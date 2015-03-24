/**
 * ProjectDAOUnitTest.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 23, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package server.database.dao;

import static org.junit.Assert.*;

import java.util.List;
import java.util.logging.Logger;

import org.junit.*;

import server.ServerUnitTests;
import server.database.Database;
import server.database.DatabaseException;

import shared.model.Project;

// TODO: Auto-generated Javadoc
/**
 * The Class ProjectDAOUnitTest.
 */
public class ProjectDAOUnitTest {
  /** The logger used throughout the project. */
  private static Logger logger;
  static {
    logger = Logger.getLogger(ServerUnitTests.LOG_NAME);
  }

  /**
   * Sets the up before class.
   *
   * @throws Exception the exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    // "setUpBeforeClass");

    // Load database drivers
    Database.initDriver();
  }

  /**
   * Tear down after class.
   *
   * @throws Exception the exception
   */
  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    // "tearDownAfterClass");
    // "tearDownAfterClass");
    return;
  }

  // @formatter:off
  private Database   db;
  private ProjectDAO testProjectDAO;

  private Project projectTest1 = null;
  private Project projectTest2 = null;
  private Project projectTest3 = null;
  // @formatter:on

  /**
   * Sets the database up.
   *
   * @throws Exception the exception
   */
  @Before
  public void setUp() throws Exception {

    // Prepare database for test case
    db = new Database();
    db.startTransaction();
    testProjectDAO = db.getProjectDAO();
    testProjectDAO.initTable();

    projectTest1 = new Project("projectTest1", 10, 11, 12);
    projectTest2 = new Project("projectTest2", 20, 21, 22);
    projectTest3 = new Project("projectTest3", 30, 31, 32);

    testProjectDAO.create(projectTest1);
    testProjectDAO.create(projectTest2);
    testProjectDAO.create(projectTest3);
  }

  /**
   * Tear down.
   *
   * @throws Exception the exception
   */
  @After
  public void tearDown() throws Exception {

    // Roll back this transaction so changes are undone
    db.endTransaction(false);
    db = null;
    testProjectDAO = null;
  }

  // @formatter:off
  /**
   * Are equal.
   *
   * @param a the first project to compare
   * @param b the second project to compare
   * @param compareIds compare by Ids?
   * @return true, if successful
   */
  private boolean areEqual(Project a, Project b, boolean compareIds) {
    if (compareIds) {
      if (a.getProjectId() != b.getProjectId()) {
        return false;
      }
    }
    return (safeEquals(a.getFirstYCoord(), b.getFirstYCoord())
        && safeEquals(a.getTitle(), b.getTitle())
        && safeEquals(a.getRecordHeight(), b.getRecordHeight())
        && safeEquals(a.getRecordsPerBatch(), b.getRecordsPerBatch()));
  }
  // @formatter:on

  /**
   * Safe equals.
   *
   * @param a the first project to compare
   * @param b the second project to compare
   * @return true, if successful
   */
  private boolean safeEquals(Object a, Object b) {
    if ((a == null) || (b == null)) {
      return ((a == null) && (b == null));
    } else {
      return a.equals(b);
    }
  }

  /**
   * Test get allProjects.
   *
   * @throws DatabaseException the database exception
   */
  @Test
  public void testGetAll() throws DatabaseException {

    final List<Project> allProjectes = testProjectDAO.getAll();
    assertEquals(3, allProjectes.size());
  }

  /**
   * Test create.
   *
   * @throws DatabaseException the database exception
   */
  @Test
  public void testCreate() throws DatabaseException {

    final List<Project> allProjectes = testProjectDAO.getAll();
    assertEquals(3, allProjectes.size());

    boolean hasFoundProject1 = false;
    boolean hasFoundProject2 = false;
    boolean hasFoundProject3 = false;
    for (final Project b : allProjectes) {
      assertFalse(b.getProjectId() == -1);
      if (!hasFoundProject1) {
        hasFoundProject1 = areEqual(b, projectTest1, false);
      }
      if (!hasFoundProject2) {
        hasFoundProject2 = areEqual(b, projectTest2, false);
      }
      if (!hasFoundProject3) {
        hasFoundProject3 = areEqual(b, projectTest3, false);
      }
    }
    assertTrue(hasFoundProject1 && hasFoundProject2 && hasFoundProject3);
  }

  /**
   * Test update.
   *
   * @throws DatabaseException the database exception
   */
  // @Test
  // public void testUpdate() throws DatabaseException {

  // Project projectTest1 = new Project("projectUdateTest1", 10, 10);
  // Project projectTest2 = new Project("projectUdateTest2", 1, 1);
  // Project projectTest3 = new Project("projectUdateTest3", 15, 15);

  // dbprojectTest.create(projectTest1);
  // dbprojectTest.create(projectTest2);
  // dbprojectTest.create(projectTest3);

  // projectTest1.setStatus(0);
  // projectTest2.setStatus(1);
  // projectTest3.setStatus(2);

  // dbprojectTest.update(projectTest1);
  // dbprojectTest.update(projectTest2);
  // dbprojectTest.update(projectTest3);

  // List<Project> allProjectes = dbprojectTest.getAll();
  // assertEquals(3, allProjectes.size());

  // boolean hasFoundProject1 = false;
  // boolean hasFoundProject2 = false;
  // boolean hasFoundProject3 = false;
  // for (Project b : allProjectes) {
  // assertFalse(b.getprojectId() == -1);
  // if (!hasFoundProject1) {
  // hasFoundProject1 = areEqual(b, projectTest1 ,false);
  // }
  // if (!hasFoundProject2) {
  // hasFoundProject2 = areEqual(b, projectTest2 ,false);
  // }
  // if (!hasFoundProject3) {
  // hasFoundProject3 = areEqual(b, projectTest3 ,false);
  // }
  // }
  // assertTrue(hasFoundProject1 && hasFoundProject2 && hasFoundProject3);
  // }

  /**
   * Test delete.
   *
   * @throws DatabaseException the database exception
   */
  @Test
  public void testDelete() throws DatabaseException {

    List<Project> allProjectes = testProjectDAO.getAll();
    assertEquals(3, allProjectes.size());

    testProjectDAO.delete(projectTest1);
    allProjectes = testProjectDAO.getAll();
    assertEquals(2, allProjectes.size());

    testProjectDAO.delete(projectTest2);
    allProjectes = testProjectDAO.getAll();
    assertEquals(1, allProjectes.size());

    testProjectDAO.delete(projectTest3);
    allProjectes = testProjectDAO.getAll();
    assertEquals(0, allProjectes.size());
  }
}
