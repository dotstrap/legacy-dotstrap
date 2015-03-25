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

import org.junit.*;

import server.database.Database;
import server.database.DatabaseException;

import shared.model.Record;

// TODO: Auto-generated Javadoc
/**
 * The Class RecordDAOUnitTest.
 */
public class RecordDAOUnitTest {
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

  private Database  db;
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
   * Test get allRecords.
   *
   * @throws DatabaseException the database exception
   */
  @Test
  public void testGetAll() throws DatabaseException {

    List<Record> allRecords = testRecDAO.getAll();
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

    int fieldId1 = 1;
    int fieldId2 = 2;
    int fieldId3 = 3;
    int fieldId4 = 4;

    int batchId1 = 1;
    String batchURL1 = "batch1/img";
    Record recordTest1 = new Record(fieldId1, batchId1, batchURL1, "A", 1, 1);
    Record recordTest2 = new Record(fieldId2, batchId1, batchURL1, "B", 1, 2);
    Record recordTest3 = new Record(fieldId2, batchId1, batchURL1, "C", 2, 1);
    Record recordTest4 = new Record(fieldId2, batchId1, batchURL1, "A", 2, 2);

    int batchId2 = 2;
    String batchURL2 = "batch2/img";
    Record recordTest5 = new Record(fieldId1, batchId2, batchURL2, "A", 1, 1);
    Record recordTest6 = new Record(fieldId2, batchId2, batchURL2, "A", 1, 2);
    Record recordTest7 = new Record(fieldId1, batchId2, batchURL2, "B", 2, 1);
    Record recordTest8 = new Record(fieldId2, batchId2, batchURL2, "C", 2, 2);

    int batchId3 = 3;
    String batchURL3 = "batch3/img";
    Record recordTest9 = new Record(fieldId3, batchId3, batchURL3, "A", 1, 1);
    Record recordTest10 = new Record(fieldId4, batchId3, batchURL3, "C", 1, 2);
    Record recordTest11 = new Record(fieldId3, batchId3, batchURL3, "A", 2, 1);
    Record recordTest12 = new Record(fieldId4, batchId3, batchURL3, "B", 2, 2);

    // Add new records to database
    int testRecordId1 = testRecDAO.create(recordTest1);
    recordTest1.setRecordId(testRecordId1);
    int testRecordId2 = testRecDAO.create(recordTest2);
    recordTest2.setRecordId(testRecordId2);
    int testRecordId3 = testRecDAO.create(recordTest3);
    recordTest3.setRecordId(testRecordId3);
    int testRecordId4 = testRecDAO.create(recordTest4);
    recordTest4.setRecordId(testRecordId4);

    int testRecordId5 = testRecDAO.create(recordTest5);
    recordTest5.setRecordId(testRecordId5);
    int testRecordId6 = testRecDAO.create(recordTest6);
    recordTest6.setRecordId(testRecordId6);
    int testRecordId7 = testRecDAO.create(recordTest7);
    recordTest7.setRecordId(testRecordId7);
    int testRecordId8 = testRecDAO.create(recordTest8);
    recordTest8.setRecordId(testRecordId8);

    int testRecordId9 = testRecDAO.create(recordTest9);
    recordTest9.setRecordId(testRecordId9);
    int testRecordId10 = testRecDAO.create(recordTest10);
    recordTest10.setRecordId(testRecordId10);
    int testRecordId11 = testRecDAO.create(recordTest11);
    recordTest1.setRecordId(testRecordId11);
    int testRecordId12 = testRecDAO.create(recordTest12);
    recordTest2.setRecordId(testRecordId12);

    // Ensure database contains all the new records by testing read
    assertTrue(recordTest1.equals(testRecDAO.read(testRecordId1), false));
    assertTrue(recordTest2.equals(testRecDAO.read(testRecordId2), false));
    assertTrue(recordTest3.equals(testRecDAO.read(testRecordId3), false));
    assertTrue(recordTest4.equals(testRecDAO.read(testRecordId4), false));

    assertTrue(recordTest5.equals(testRecDAO.read(testRecordId5), false));
    assertTrue(recordTest6.equals(testRecDAO.read(testRecordId6), false));
    assertTrue(recordTest7.equals(testRecDAO.read(testRecordId7), false));
    assertTrue(recordTest8.equals(testRecDAO.read(testRecordId8), false));

    assertTrue(recordTest9.equals(testRecDAO.read(testRecordId9), false));
    assertTrue(recordTest10.equals(testRecDAO.read(testRecordId10), false));
    assertTrue(recordTest11.equals(testRecDAO.read(testRecordId11), false));
    assertTrue(recordTest12.equals(testRecDAO.read(testRecordId12), false));

    // Ensure table contains 12 records
    List<Record> allRecords = testRecDAO.getAll();
    assertEquals(12, allRecords.size());

    boolean hasFoundRecord1 = false;
    boolean hasFoundRecord2 = false;
    boolean hasFoundRecord3 = false;
    boolean hasFoundRecord4 = false;
    for (Record curr : allRecords) {
      assertFalse(curr.getRecordId() == -1);
      if (!hasFoundRecord1) {
        hasFoundRecord1 = curr.equals(recordTest1, false);
      }
      if (!hasFoundRecord2) {
        hasFoundRecord2 = curr.equals(recordTest2, false);
      }
      if (!hasFoundRecord3) {
        hasFoundRecord3 = curr.equals(recordTest3, false);
      }
      if (!hasFoundRecord4) {
        hasFoundRecord4 = curr.equals(recordTest4, false);
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

    int batchId1 = 1;
    int fieldId1 = 1;
    int fieldId2 = 2;

    String batchURL1 = "batch1/img";
    Record recordTest1 = new Record(fieldId1, batchId1, batchURL1, "A", 1, 1);
    Record recordTest2 = new Record(fieldId2, batchId1, batchURL1, "B", 1, 2);
    Record recordTest3 = new Record(fieldId2, batchId1, batchURL1, "C", 2, 1);
    Record recordTest4 = new Record(fieldId2, batchId1, batchURL1, "A", 2, 2);

    testRecDAO.create(recordTest1);
    testRecDAO.create(recordTest2);
    testRecDAO.create(recordTest3);
    testRecDAO.create(recordTest4);

    List<Record> allRecords = testRecDAO.getAll();
    int i = 3;
    for (Record curr : allRecords) {
      testRecDAO.delete(curr);
      allRecords = testRecDAO.getAll();
      assertEquals(i, allRecords.size());
      --i;
    }
  }
}
