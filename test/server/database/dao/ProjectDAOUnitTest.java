/**
 * ProjectDAOUnitTest.java
 * JRE v1.8.0_45
 * 
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */

package server.database.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.*;

import server.database.Database;
import server.database.DatabaseException;

import shared.model.Project;

/**
 * The Class ProjectDAOUnitTest.
 */
public class ProjectDAOUnitTest {

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
  private Database db;

  private ProjectDAO testProjectDAO;

  private Project testProject1 = null;

  private Project testProject2 = null;

  private Project testProject3 = null;
  // @formatter:on

  /**
   * Sets the up.
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

    testProject1 = new Project("testProject1", 10, 11, 12);
    testProject2 = new Project("testProject2", 20, 21, 22);
    testProject3 = new Project("testProject3", 30, 31, 32);

    testProjectDAO.create(testProject1);
    testProjectDAO.create(testProject2);
    testProjectDAO.create(testProject3);
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
   * @param a the a
   * @param b the b
   * @param compareIds the compare ids
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
   * @param a the a
   * @param b the b
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
   * Test get all.
   *
   * @throws DatabaseException the database exception
   */
  @Test
  public void testGetAll() throws DatabaseException {

    List<Project> allProjectes = testProjectDAO.getAll();
    assertEquals(3, allProjectes.size());
  }

  /**
   * Test create.
   *
   * @throws DatabaseException the database exception
   */
  @Test
  public void testCreate() throws DatabaseException {

    List<Project> allProjectes = testProjectDAO.getAll();
    assertEquals(3, allProjectes.size());

    boolean hasFoundProject1 = false;
    boolean hasFoundProject2 = false;
    boolean hasFoundProject3 = false;
    for (Project b : allProjectes) {
      assertFalse(b.getProjectId() == -1);
      if (!hasFoundProject1) {
        hasFoundProject1 = areEqual(b, testProject1, false);
      }
      if (!hasFoundProject2) {
        hasFoundProject2 = areEqual(b, testProject2, false);
      }
      if (!hasFoundProject3) {
        hasFoundProject3 = areEqual(b, testProject3, false);
      }
    }
    assertTrue(hasFoundProject1 && hasFoundProject2 && hasFoundProject3);
  }

  // @Test
  // public void testUpdate() throws DatabaseException {

  // Project testProject1 = new Project("projectUdateTest1", 10, 10);
  // Project testProject2 = new Project("projectUdateTest2", 1, 1);
  // Project testProject3 = new Project("projectUdateTest3", 15, 15);

  // dbtestProject.create(testProject1);
  // dbtestProject.create(testProject2);
  // dbtestProject.create(testProject3);

  // testProject1.setStatus(0);
  // testProject2.setStatus(1);
  // testProject3.setStatus(2);

  // dbtestProject.update(testProject1);
  // dbtestProject.update(testProject2);
  // dbtestProject.update(testProject3);

  // List<Project> allProjectes = dbtestProject.getAll();
  // assertEquals(3, allProjectes.size());

  // boolean hasFoundProject1 = false;
  // boolean hasFoundProject2 = false;
  // boolean hasFoundProject3 = false;
  // for (Project b : allProjectes) {
  // assertFalse(b.getprojectId() == -1);
  // if (!hasFoundProject1) {
  // hasFoundProject1 = areEqual(b, testProject1 ,false);
  // }
  // if (!hasFoundProject2) {
  // hasFoundProject2 = areEqual(b, testProject2 ,false);
  // }
  // if (!hasFoundProject3) {
  // hasFoundProject3 = areEqual(b, testProject3 ,false);
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

    testProjectDAO.delete(testProject1);
    allProjectes = testProjectDAO.getAll();
    assertEquals(2, allProjectes.size());

    testProjectDAO.delete(testProject2);
    allProjectes = testProjectDAO.getAll();
    assertEquals(1, allProjectes.size());

    testProjectDAO.delete(testProject3);
    allProjectes = testProjectDAO.getAll();
    assertEquals(0, allProjectes.size());
  }
}
