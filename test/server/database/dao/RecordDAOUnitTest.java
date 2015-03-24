/**
 * RecordDAOUnitTest.java
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

import shared.model.Record;

// TODO: Auto-generated Javadoc
/**
 * The Class RecordDAOUnitTest.
 */
public class RecordDAOUnitTest {
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

  private Database db;
  private RecordDAO testRecDAO;

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
    db.initTables();
    testRecDAO = db.getRecordDAO();
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
    testRecDAO = null;
  }

  /**
   * Are equal.
   *
   * @param a the first record to compare
   * @param b the second record to compare
   * @param compareIds compare by Ids?
   * @return true, if successful
   */
  private boolean areEqual(Record a, Record b, boolean compareIds) {
    if (compareIds) {
      if (a.getRecordId() != b.getRecordId()) {
        return false;
      }
    }
    return (safeEquals(a.getFieldId(), b.getFieldId())
        && safeEquals(a.getBatchId(), b.getBatchId())
        && safeEquals(a.getBatchURL(), b.getBatchURL()) && safeEquals(a.getData(), b.getData())
        && safeEquals(a.getRowNum(), b.getRowNum()) && safeEquals(a.getColNum(), b.getColNum()));
  }

  /**
   * Safe equals.
   *
   * @param a the first record to compare
   * @param b the second record to compare
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
   * Test get allRecords.
   *
   * @throws DatabaseException the database exception
   */
  @Test
  public void testGetAll() throws DatabaseException {

    final List<Record> allRecords = testRecDAO.getAll();
    assertEquals(0, allRecords.size());
  }

  // FIXME: separate this into different methods without too much code
  // duplication
  /*
   * TestCRUD throws the kitchen sink at the records table
   *
   * @throws DatabaseException the database exception
   */
  @Test
  public void testCreate() throws DatabaseException {

    final int fieldId1 = 1;
    final int fieldId2 = 2;
    final int fieldId3 = 3;
    final int fieldId4 = 4;

    final int batchId1 = 1;
    final String batchURL1 = "batch1/img";
    final Record recordTest1 = new Record(fieldId1, batchId1, batchURL1, "A", 1, 1);
    final Record recordTest2 = new Record(fieldId2, batchId1, batchURL1, "B", 1, 2);
    final Record recordTest3 = new Record(fieldId2, batchId1, batchURL1, "C", 2, 1);
    final Record recordTest4 = new Record(fieldId2, batchId1, batchURL1, "A", 2, 2);

    final int batchId2 = 2;
    final String batchURL2 = "batch2/img";
    final Record recordTest5 = new Record(fieldId1, batchId2, batchURL2, "A", 1, 1);
    final Record recordTest6 = new Record(fieldId2, batchId2, batchURL2, "A", 1, 2);
    final Record recordTest7 = new Record(fieldId1, batchId2, batchURL2, "B", 2, 1);
    final Record recordTest8 = new Record(fieldId2, batchId2, batchURL2, "C", 2, 2);

    final int batchId3 = 3;
    final String batchURL3 = "batch3/img";
    final Record recordTest9 = new Record(fieldId3, batchId3, batchURL3, "A", 1, 1);
    final Record recordTest10 = new Record(fieldId4, batchId3, batchURL3, "C", 1, 2);
    final Record recordTest11 = new Record(fieldId3, batchId3, batchURL3, "A", 2, 1);
    final Record recordTest12 = new Record(fieldId4, batchId3, batchURL3, "B", 2, 2);

    // Add new records to database
    final int testRecordId1 = testRecDAO.create(recordTest1);
    recordTest1.setRecordId(testRecordId1);
    final int testRecordId2 = testRecDAO.create(recordTest2);
    recordTest2.setRecordId(testRecordId2);
    final int testRecordId3 = testRecDAO.create(recordTest3);
    recordTest3.setRecordId(testRecordId3);
    final int testRecordId4 = testRecDAO.create(recordTest4);
    recordTest4.setRecordId(testRecordId4);

    final int testRecordId5 = testRecDAO.create(recordTest5);
    recordTest5.setRecordId(testRecordId5);
    final int testRecordId6 = testRecDAO.create(recordTest6);
    recordTest6.setRecordId(testRecordId6);
    final int testRecordId7 = testRecDAO.create(recordTest7);
    recordTest7.setRecordId(testRecordId7);
    final int testRecordId8 = testRecDAO.create(recordTest8);
    recordTest8.setRecordId(testRecordId8);

    final int testRecordId9 = testRecDAO.create(recordTest9);
    recordTest9.setRecordId(testRecordId9);
    final int testRecordId10 = testRecDAO.create(recordTest10);
    recordTest10.setRecordId(testRecordId10);
    final int testRecordId11 = testRecDAO.create(recordTest11);
    recordTest1.setRecordId(testRecordId11);
    final int testRecordId12 = testRecDAO.create(recordTest12);
    recordTest2.setRecordId(testRecordId12);

    // Quick tests to verify safe equals works for different data types
    // assertTrue(safeEquals(1, 1));
    // assertFalse(safeEquals(1, 2));
    // assertTrue(safeEquals("a", "a"));
    // assertFalse(safeEquals("a", "b"));
    // assertTrue(safeEquals(testRecordId1, testRecordId1));
    // assertTrue(safeEquals(recordTest1, recordTest1));

    // Ensure database contains all the new records by testing read
    assertTrue(areEqual(recordTest1, testRecDAO.read(testRecordId1), false));
    assertTrue(areEqual(recordTest2, testRecDAO.read(testRecordId2), false));
    assertTrue(areEqual(recordTest3, testRecDAO.read(testRecordId3), false));
    assertTrue(areEqual(recordTest4, testRecDAO.read(testRecordId4), false));

    assertTrue(areEqual(recordTest5, testRecDAO.read(testRecordId5), false));
    assertTrue(areEqual(recordTest6, testRecDAO.read(testRecordId6), false));
    assertTrue(areEqual(recordTest7, testRecDAO.read(testRecordId7), false));
    assertTrue(areEqual(recordTest8, testRecDAO.read(testRecordId8), false));

    assertTrue(areEqual(recordTest9, testRecDAO.read(testRecordId9), false));
    assertTrue(areEqual(recordTest10, testRecDAO.read(testRecordId10), false));
    assertTrue(areEqual(recordTest11, testRecDAO.read(testRecordId11), false));
    assertTrue(areEqual(recordTest12, testRecDAO.read(testRecordId12), false));

    // Ensure table contains 12 records
    final List<Record> allRecords = testRecDAO.getAll();
    assertEquals(12, allRecords.size());

    boolean hasFoundRecord1 = false;
    boolean hasFoundRecord2 = false;
    boolean hasFoundRecord3 = false;
    boolean hasFoundRecord4 = false;
    for (final Record curr : allRecords) {
      assertFalse(curr.getRecordId() == -1);
      if (!hasFoundRecord1) {
        hasFoundRecord1 = areEqual(curr, recordTest1, false);
      }
      if (!hasFoundRecord2) {
        hasFoundRecord2 = areEqual(curr, recordTest2, false);
      }
      if (!hasFoundRecord3) {
        hasFoundRecord3 = areEqual(curr, recordTest3, false);
      }
      if (!hasFoundRecord4) {
        hasFoundRecord4 = areEqual(curr, recordTest4, false);
      }
    }
    assertTrue(hasFoundRecord1 && hasFoundRecord2 && hasFoundRecord3 && hasFoundRecord4);
  }

  /**
   * Test delete.
   *
   * @throws DatabaseException the database exception
   */
  @Test
  public void testDelete() throws DatabaseException {

    final int batchId1 = 1;
    final int fieldId1 = 1;
    final int fieldId2 = 2;

    final String batchURL1 = "batch1/img";
    final Record recordTest1 = new Record(fieldId1, batchId1, batchURL1, "A", 1, 1);
    final Record recordTest2 = new Record(fieldId2, batchId1, batchURL1, "B", 1, 2);
    final Record recordTest3 = new Record(fieldId2, batchId1, batchURL1, "C", 2, 1);
    final Record recordTest4 = new Record(fieldId2, batchId1, batchURL1, "A", 2, 2);

    testRecDAO.create(recordTest1);
    testRecDAO.create(recordTest2);
    testRecDAO.create(recordTest3);
    testRecDAO.create(recordTest4);

    List<Record> allRecords = testRecDAO.getAll();
    int i = 3;
    for (final Record curr : allRecords) {
      testRecDAO.delete(curr);
      allRecords = testRecDAO.getAll();
      assertEquals(i, allRecords.size());
      --i;
    }
  }
}
