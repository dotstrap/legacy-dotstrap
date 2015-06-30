/**
 * FieldDAOUnitTest.java
 * JRE v1.8.0_45
 * 
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */

package server.database.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

  private Database db;

  private FieldDAO testFieldDAO;

  private Field fieldTest1 = null;

  private Field fieldTest2 = null;

  private Field fieldTest3 = null;

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
    testFieldDAO = db.getFieldDAO();
    testFieldDAO.initTable();

    fieldTest1 =
        new Field(100, 111, "fieldTest1", "knownData1", "helpURL1", 1, 1, 1);
    fieldTest2 =
        new Field(200, 222, "fieldTest2", "knownData2", "helpURL2", 2, 2, 2);
    fieldTest3 =
        new Field(300, 333, "fieldTest3", "knownData3", "helpURL3", 3, 3, 3);

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
    List<Field> allFields = testFieldDAO.getAll();
    assertEquals(3, allFields.size());

    boolean hasFoundField1 = false;
    boolean hasFoundField2 = false;
    boolean hasFoundField3 = false;
    for (Field curr : allFields) {
      assertFalse(curr.getFieldId() == -1);
      if (!hasFoundField1) {
        hasFoundField1 = curr.equals(fieldTest1);
      }
      if (!hasFoundField2) {
        hasFoundField2 = curr.equals(fieldTest2);
      }
      if (!hasFoundField3) {
        hasFoundField3 = curr.equals(fieldTest3);
      }
    }
    assertTrue(hasFoundField1 && hasFoundField2 && hasFoundField3);
  }

  /**
   * Test delete.
   *
   * @throws DatabaseException the database exception
   */
  @Test
  public void testDelete() throws DatabaseException {
    List<Field> allFields = testFieldDAO.getAll();
    assertEquals(3, allFields.size());

    testFieldDAO.delete(fieldTest1);
    allFields = testFieldDAO.getAll();
    assertEquals(2, allFields.size());

    testFieldDAO.delete(fieldTest2);
    allFields = testFieldDAO.getAll();
    assertEquals(1, allFields.size());

    testFieldDAO.delete(fieldTest3);
    allFields = testFieldDAO.getAll();
    assertEquals(0, allFields.size());
  }
}
