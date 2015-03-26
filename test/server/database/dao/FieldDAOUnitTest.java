/**
 * FieldDAOUnitTest.java
 * JRE v1.8.0_40
 * 
 * Created by William Myers on Mar 24, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package server.database.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.*;

import server.database.Database;
import server.database.DatabaseException;

import shared.model.Field;


/**
 * The Class FieldDAOUnitTest.
 */
public class FieldDAOUnitTest {
  /**
   * Sets up before class.
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
    // "tearDownAfterClass");
    return;
  }

  private Database db;
  private FieldDAO testFieldDAO;
  private Field    fieldTest1 = null;
  private Field    fieldTest2 = null;
  private Field    fieldTest3 = null;

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
    testFieldDAO = db.getFieldDAO();
    testFieldDAO.initTable();

    fieldTest1 = new Field(100, 111, "fieldTest1", "KnownData1", "helpURL1", 1, 1, 1);
    fieldTest2 = new Field(200, 222, "fieldTest2", "KnownData2", "helpURL2", 2, 2, 2);
    fieldTest3 = new Field(300, 333, "fieldTest3", "KnownData3", "helpURL3", 3, 3, 3);

    testFieldDAO.create(fieldTest1);
    testFieldDAO.create(fieldTest2);
    testFieldDAO.create(fieldTest3);

    List<Field> allFieldes = testFieldDAO.getAll();
    assertEquals(3, allFieldes.size());
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

    testFieldDAO = null;
    fieldTest1 = null;
    fieldTest2 = null;
    fieldTest3 = null;
  }

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

  // @formatter:off
  private boolean areEqual(Field a, Field b, boolean shouldCompareIds) {
    if (shouldCompareIds) {
      if (a.getFieldId() != b.getFieldId()) {
        return false;
      }
    }
    return (safeEquals(a.getProjectId(), b.getProjectId())
        && safeEquals(a.getTitle(), b.getTitle())
        && safeEquals(a.getKnownData(), b.getKnownData())
        && safeEquals(a.getHelpURL(), b.getHelpURL())
        && safeEquals(a.getXCoord(), b.getXCoord())
        && safeEquals(a.getWidth(), b.getWidth()));
  }
  // @formatter:on

  /**
   * Test get all.
   *
   * @throws DatabaseException the database exception
   */
  @Test
  public void testGetAll() throws DatabaseException {

    List<Field> allFields = testFieldDAO.getAll();
    assertEquals(3, allFields.size());
  }

  /**
   * Test create.
   *
   * @throws DatabaseException the database exception
   */
  @Test
  public void testCreate() throws DatabaseException {

    List<Field> all = testFieldDAO.getAll();
    assertEquals(3, all.size());

    boolean hasFoundField1 = false;
    boolean hasFoundField2 = false;
    boolean hasFoundField3 = false;
    for (Field curr : all) {
      assertFalse(curr.getFieldId() == -1);
      if (!hasFoundField1) {
        hasFoundField1 = areEqual(curr, fieldTest1, false);
      }
      if (!hasFoundField2) {
        hasFoundField2 = areEqual(curr, fieldTest2, false);
      }
      if (!hasFoundField3) {
        hasFoundField3 = areEqual(curr, fieldTest3, false);
      }
    }
  }

  /**
   * Test delete.
   *
   * @throws DatabaseException the database exception
   */
  @Test
  public void testDelete() throws DatabaseException {
    List<Field> allFieldes = testFieldDAO.getAll();
    assertEquals(3, allFieldes.size());

    testFieldDAO.delete(fieldTest1);
    allFieldes = testFieldDAO.getAll();
    assertEquals(2, allFieldes.size());

    testFieldDAO.delete(fieldTest3);
    allFieldes = testFieldDAO.getAll();
    assertEquals(1, allFieldes.size());

    testFieldDAO.delete(fieldTest2);
    allFieldes = testFieldDAO.getAll();
    assertEquals(0, allFieldes.size());
  }
}
