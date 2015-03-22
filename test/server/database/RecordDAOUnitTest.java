/**
 * RecordDAOUnitTest.java
 * JRE v1.8.0_40
 * 
 * Created by William Myers on Mar 22, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package server.database;

import static org.junit.Assert.*;

import java.util.List;
import java.util.logging.Logger;

import org.junit.*;

import server.database.dao.RecordDAO;

import shared.model.Record;

// TODO: Auto-generated Javadoc
/**
 * The Class RecordDAOUnitTest.
 */
public class RecordDAOUnitTest {
    /** The logger used throughout the project. */
    private static Logger logger;
    static {
        logger = Logger.getLogger("serverTest");
    }

    /**
     * Sets the up before class.
     *
     * @throws Exception
     *             the exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        logger.entering("server.database.RecordDAOUnitTest", "setUpBeforeClass");

        // Load database drivers
        Database.initDriver();

        logger.exiting("server.database.RecordDAOUnitTest", "setUpBeforeClass");
    }

    /**
     * Tear down after class.
     *
     * @throws Exception
     *             the exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        logger.entering("server.database.RecordDAOUnitTest", "tearDownAfterClass");
        logger.exiting("server.database.RecordDAOUnitTest", "tearDownAfterClass");
        return;
    }

    private Database  db;
    private RecordDAO testRecDAO;

    /**
     * Sets the database up.
     *
     * @throws Exception
     *             the exception
     */
    @Before
    public void setUp() throws Exception {
        logger.entering("server.database.RecordDAOUnitTest", "setUp");

        // Prepare database for test case
        db = new Database();
        db.startTransaction();
        db.initTables();
        testRecDAO = db.getRecordDAO();

        logger.exiting("server.database.RecordDAOUnitTest", "setUp");
    }

    /**
     * Tear down.
     *
     * @throws Exception
     *             the exception
     */
    @After
    public void tearDown() throws Exception {
        logger.entering("server.database.RecordDAOUnitTest", "tearDown");

        // Roll back this transaction so changes are undone
        db.endTransaction(false);
        db = null;
        testRecDAO = null;

        logger.exiting("server.database.RecordDAOUnitTest", "tearDown");
    }

    /**
     * Are equal.
     *
     * @param a
     *            the first record to compare
     * @param b
     *            the second record to compare
     * @param compareIDs
     *            compare by IDs?
     * @return true, if successful
     */
    private boolean areEqual(Record a, Record b, boolean compareIDs) {
        if (compareIDs) {
            if (a.getRecordID() != b.getRecordID()) {
                return false;
            }
        }
        return (safeEquals(a.getFieldID(), b.getFieldID())
                && safeEquals(a.getBatchID(), b.getBatchID())
                && safeEquals(a.getBatchURL(), b.getBatchURL())
                && safeEquals(a.getData(), b.getData())
                && safeEquals(a.getRowNum(), b.getRowNum()) && safeEquals(
                    a.getColNum(), b.getColNum()));
    }

    /**
     * Safe equals.
     *
     * @param a
     *            the first record to compare
     * @param b
     *            the second record to compare
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
     * @throws DatabaseException
     *             the database exception
     */
    @Test
    public void testGetAll() throws DatabaseException {
        logger.entering("server.database.RecordDAOUnitTest", "testGetAll");

        List<Record> allRecords = testRecDAO.getAll();
        assertEquals(0, allRecords.size());

        logger.exiting("server.database.RecordDAOUnitTest", "testGetAll");
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
        logger.entering("server.database.RecordDAOUnitTest", "testCreate");

        int fieldID1 = 1;
        int fieldID2 = 2;
        int fieldID3 = 3;
        int fieldID4 = 4;

        int batchID1 = 1;
        String batchURL1 = "batch1/img";
        Record testRecord1 = new Record(fieldID1, batchID1, batchURL1, "A", 1, 1);
        Record testRecord2 = new Record(fieldID2, batchID1, batchURL1, "B", 1, 2);
        Record testRecord3 = new Record(fieldID2, batchID1, batchURL1, "C", 2, 1);
        Record testRecord4 = new Record(fieldID2, batchID1, batchURL1, "A", 2, 2);

        int batchID2 = 2;
        String batchURL2 = "batch2/img";
        Record testRecord5 = new Record(fieldID1, batchID2, batchURL2, "A", 1, 1);
        Record testRecord6 = new Record(fieldID2, batchID2, batchURL2, "A", 1, 2);
        Record testRecord7 = new Record(fieldID1, batchID2, batchURL2, "B", 2, 1);
        Record testRecord8 = new Record(fieldID2, batchID2, batchURL2, "C", 2, 2);

        int batchID3 = 3;
        String batchURL3 = "batch3/img";
        Record testRecord9 = new Record(fieldID3, batchID3, batchURL3, "A", 1, 1);
        Record testRecord10 = new Record(fieldID4, batchID3, batchURL3, "C", 1, 2);
        Record testRecord11 = new Record(fieldID3, batchID3, batchURL3, "A", 2, 1);
        Record testRecord12 = new Record(fieldID4, batchID3, batchURL3, "B", 2, 2);

        // Add new records to database
        int testRecordID1 = testRecDAO.create(testRecord1);
        testRecord1.setRecordID(testRecordID1);
        int testRecordID2 = testRecDAO.create(testRecord2);
        testRecord2.setRecordID(testRecordID2);
        int testRecordID3 = testRecDAO.create(testRecord3);
        testRecord3.setRecordID(testRecordID3);
        int testRecordID4 = testRecDAO.create(testRecord4);
        testRecord4.setRecordID(testRecordID4);

        int testRecordID5 = testRecDAO.create(testRecord5);
        testRecord5.setRecordID(testRecordID5);
        int testRecordID6 = testRecDAO.create(testRecord6);
        testRecord6.setRecordID(testRecordID6);
        int testRecordID7 = testRecDAO.create(testRecord7);
        testRecord7.setRecordID(testRecordID7);
        int testRecordID8 = testRecDAO.create(testRecord8);
        testRecord8.setRecordID(testRecordID8);

        int testRecordID9 = testRecDAO.create(testRecord9);
        testRecord9.setRecordID(testRecordID9);
        int testRecordID10 = testRecDAO.create(testRecord10);
        testRecord10.setRecordID(testRecordID10);
        int testRecordID11 = testRecDAO.create(testRecord11);
        testRecord11.setRecordID(testRecordID11);
        int testRecordID12 = testRecDAO.create(testRecord12);
        testRecord12.setRecordID(testRecordID12);

        // Quick tests to verify safe equals works for different data types
        // assertTrue(safeEquals(1, 1));
        // assertFalse(safeEquals(1, 2));
        // assertTrue(safeEquals("a", "a"));
        // assertFalse(safeEquals("a", "b"));
        // assertTrue(safeEquals(testRecordID1, testRecordID1));
        // assertTrue(safeEquals(testRecord1, testRecord1));

        // Ensure database contains all the new records by testing read
        assertTrue(areEqual(testRecord1, testRecDAO.read(testRecordID1), false));
        assertTrue(areEqual(testRecord2, testRecDAO.read(testRecordID2), false));
        assertTrue(areEqual(testRecord3, testRecDAO.read(testRecordID3), false));
        assertTrue(areEqual(testRecord4, testRecDAO.read(testRecordID4), false));

        assertTrue(areEqual(testRecord5, testRecDAO.read(testRecordID5), false));
        assertTrue(areEqual(testRecord6, testRecDAO.read(testRecordID6), false));
        assertTrue(areEqual(testRecord7, testRecDAO.read(testRecordID7), false));
        assertTrue(areEqual(testRecord8, testRecDAO.read(testRecordID8), false));

        assertTrue(areEqual(testRecord9, testRecDAO.read(testRecordID9), false));
        assertTrue(areEqual(testRecord10, testRecDAO.read(testRecordID10), false));
        assertTrue(areEqual(testRecord11, testRecDAO.read(testRecordID11), false));
        assertTrue(areEqual(testRecord12, testRecDAO.read(testRecordID12), false));

        // Ensure table contains 12 records
        List<Record> allRecords = testRecDAO.getAll();
        assertEquals(12, allRecords.size());

        boolean hasFoundRecord1 = false;
        boolean hasFoundRecord2 = false;
        boolean hasFoundRecord3 = false;
        boolean hasFoundRecord4 = false;
        for (Record curr : allRecords) {
            assertFalse(curr.getRecordID() == -1);
            if (!hasFoundRecord1) {
                hasFoundRecord1 = areEqual(curr, testRecord1, false);
            }
            if (!hasFoundRecord2) {
                hasFoundRecord2 = areEqual(curr, testRecord2, false);
            }
            if (!hasFoundRecord3) {
                hasFoundRecord3 = areEqual(curr, testRecord3, false);
            }
            if (!hasFoundRecord4) {
                hasFoundRecord4 = areEqual(curr, testRecord4, false);
            }
        }
        assertTrue(hasFoundRecord1 && hasFoundRecord2 && hasFoundRecord3
                && hasFoundRecord4);

        logger.exiting("server.database.RecordDAOUnitTest", "testCreate");
    }

    /**
     * Test delete.
     *
     * @throws DatabaseException
     *             the database exception
     */
    @Test
    public void testDelete() throws DatabaseException {
        logger.entering("server.database.RecordDAOUnitTest", "testDelete");

        int batchID1 = 1;
        int fieldID1 = 1;
        int fieldID2 = 2;

        String batchURL1 = "batch1/img";
        Record testRecord1 = new Record(fieldID1, batchID1, batchURL1, "A", 1, 1);
        Record testRecord2 = new Record(fieldID2, batchID1, batchURL1, "B", 1, 2);
        Record testRecord3 = new Record(fieldID2, batchID1, batchURL1, "C", 2, 1);
        Record testRecord4 = new Record(fieldID2, batchID1, batchURL1, "A", 2, 2);

        testRecDAO.create(testRecord1);
        testRecDAO.create(testRecord2);
        testRecDAO.create(testRecord3);
        testRecDAO.create(testRecord4);

        List<Record> allRecords = testRecDAO.getAll();
        int i = 3;
        for (Record curr : allRecords) {
            testRecDAO.delete(curr);
            allRecords = testRecDAO.getAll();
            assertEquals(i, allRecords.size());
            --i;
        }

        logger.exiting("server.database.RecordDAOUnitTest", "testDelete");
    }
}
