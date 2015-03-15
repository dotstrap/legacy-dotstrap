/**
 * BatchDAOUnitTest.java
 * JRE v1.7.0_76
 *
 * Created by William Myers on Mar 14, 2015.
 * Copyright (c) 2015 William Myers. allBatches Rights reserved.
 */
package server.database;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import shared.model.Batch;

// TODO: Auto-generated Javadoc
/**
 * The Class BatchDAOUnitTest.
 */
public class BatchDAOUnitTest {

    /**
     * Sets the up before class.
     *
     * @throws Exception
     *             the exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // Load database drivers
        Database.initDriver();
    }

    /**
     * Tear down after class.
     *
     * @throws Exception
     *             the exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        return;
    }

    private Database db;
    private BatchDAO dbBatchTest;

    /**
     * Sets the database up.
     *
     * @throws Exception
     *             the exception
     */
    @Before
    public void setUp() throws Exception {
        // Delete allBatches users from the database
        db = new Database();
        //db.initDBTables();
        db.startTransaction();

        ArrayList<Batch> batches = db.getBatchDAO().getAll();
        for (Batch b : batches) {
            db.getBatchDAO().delete(b);
        }
        db.endTransaction(true);

        // Prepare database for test case
        db = new Database();
        db.startTransaction();
        dbBatchTest = db.getBatchDAO();
    }

    /**
     * Tear down.
     *
     * @throws Exception
     *             the exception
     */
    @After
    public void tearDown() throws Exception {
        // Roll back this transaction so changes are undone
        db.endTransaction(true);
        db = null;
        dbBatchTest = null;
    }

    /**
     * Are equal.
     *
     * @param a
     *            the a
     * @param b
     *            the b
     * @param compareIDs
     *            the compare i ds
     * @return true, if successful
     */
    private boolean areEqual(Batch a, Batch b, boolean compareIDs) {
        if (compareIDs) {
            if (a.getID() != b.getID()) {
                return false;
            }
        }
        return (safeEquals(a.getFilePath(), b.getFilePath())
                && safeEquals(a.getProjectID(), b.getProjectID()) && safeEquals(
                    a.getStatus(), b.getStatus()));
    }

    /**
     * Safe equals.
     *
     * @param a
     *            the a
     * @param b
     *            the b
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
     * Test get allBatches.
     *
     * @throws DatabaseException
     *             the database exception
     */
    @Test
    public void testGetAll() throws DatabaseException {
        List<Batch> allBatches = dbBatchTest.getAll();
        assertEquals(0, allBatches.size());
    }

    /**
     * Test create.
     *
     * @throws DatabaseException
     *             the database exception
     */
    @Test
    public void testCreate() throws DatabaseException {
        Batch testBatch1 = new Batch("batchTestCreate1", 10, 10);
        Batch testBatch2 = new Batch("batchTestCreate2", 10, 10);
        Batch testBatch3 = new Batch("batchTestCreate3", 10, 10);

        dbBatchTest.create(testBatch1);
        dbBatchTest.create(testBatch2);
        dbBatchTest.create(testBatch3);

        List<Batch> allBatches = dbBatchTest.getAll();
        assertEquals(3, allBatches.size());

        boolean hasFoundBatch1 = false;
        boolean hasFoundBatch2 = false;
        boolean hasFoundBatch3 = false;
        for (Batch b : allBatches) {
            assertFalse(b.getID() == -1);
            if (!hasFoundBatch1) {
                hasFoundBatch1 = areEqual(b, testBatch1, false);
            }
            if (!hasFoundBatch2) {
                hasFoundBatch2 = areEqual(b, testBatch2, false);
            }
            if (!hasFoundBatch3) {
                hasFoundBatch3 = areEqual(b, testBatch3, false);
            }
        }
        assertTrue(hasFoundBatch1 && hasFoundBatch2 && hasFoundBatch3);
    }

    /**
     * Test update.
     *
     * @throws DatabaseException
     *             the database exception
     */
    @Test
    public void testUpdate() throws DatabaseException {
        Batch testBatch1 = new Batch("batchUdateTest1", 10, 10);
        Batch testBatch2 = new Batch("batchUdateTest2", 1, 1);
        Batch testBatch3 = new Batch("batchUdateTest3", 15, 15);

        dbBatchTest.create(testBatch1);
        dbBatchTest.create(testBatch2);
        dbBatchTest.create(testBatch3);

        testBatch1.setStatus(0);
        testBatch2.setStatus(1);
        testBatch3.setStatus(2);

        dbBatchTest.update(testBatch1);
        dbBatchTest.update(testBatch2);
        dbBatchTest.update(testBatch3);

        List<Batch> allBatches = dbBatchTest.getAll();
        assertEquals(3, allBatches.size());

        boolean hasFoundBatch1 = false;
        boolean hasFoundBatch2 = false;
        boolean hasFoundBatch3 = false;
        for (Batch b : allBatches) {
            assertFalse(b.getID() == -1);
            if (!hasFoundBatch1) {
                hasFoundBatch1 = areEqual(b, testBatch1, false);
            }
            if (!hasFoundBatch2) {
                hasFoundBatch2 = areEqual(b, testBatch2, false);
            }
            if (!hasFoundBatch3) {
                hasFoundBatch3 = areEqual(b, testBatch3, false);
            }
        }
        assertTrue(hasFoundBatch1 && hasFoundBatch2 && hasFoundBatch3);
    }

    /**
     * Test delete.
     *
     * @throws DatabaseException
     *             the database exception
     */
    @Test
    public void testDelete() throws DatabaseException {
        Batch testBatch1 = new Batch("batchDeleteTest1", 10, 10);
        Batch testBatch2 = new Batch("batchDeleteTest2", 5, 5);
        Batch testBatch3 = new Batch("batchDeleteTest3", 1, 0);

        dbBatchTest.create(testBatch1);
        dbBatchTest.create(testBatch2);
        dbBatchTest.create(testBatch3);

        List<Batch> allBatches = dbBatchTest.getAll();
        assertEquals(3, allBatches.size());

        dbBatchTest.delete(testBatch1);
        allBatches = dbBatchTest.getAll();
        assertEquals(2, allBatches.size());

        dbBatchTest.delete(testBatch2);
        allBatches = dbBatchTest.getAll();
        assertEquals(1, allBatches.size());

        dbBatchTest.delete(testBatch3);
        allBatches = dbBatchTest.getAll();
        assertEquals(0, allBatches.size());
    }
}
