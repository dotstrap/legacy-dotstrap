/**
 * BatchDAOUnitTests.java
 * JRE v1.7.0_76
 *
 * Created by William Myers on Mar 10, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package server.database;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import shared.model.Batch;

// TODO: Auto-generated Javadoc
/**
 * The Class BatchDAOUnitTests.
 */
public class BatchDAOUnitTests {

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
        return;
    }

    /** The database. */
    private Database db;

    /** The database batch. */
    private BatchDAO dbBatch;

    /**
     * Sets the database up.
     *
     * @throws Exception the exception
     */
    @Before
    public void setUp() throws Exception {

        // Delete all users from the database
        db = new Database();
        db.initDBTables();
        db.startTransaction();

        ArrayList<Batch> batches = db.getBatchDAO().getAll();

        for (Batch b : batches) {
            db.getBatchDAO().delete(b);
        }
        db.endTransaction(true);

        // Prepare database for test case
        db = new Database();
        db.startTransaction();
        dbBatch = db.getBatchDAO();
    }

    /**
     * Tear down.
     *
     * @throws Exception the exception
     */
    @After
    public void tearDown() throws Exception {

        // Roll back this transaction so changes are undone
         db.endTransaction(true);

        db = null;
        dbBatch = null;
    }

    /**
     * Are equal.
     *
     * @param a the a
     * @param b the b
     * @param compareIDs the compare i ds
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
                    a.getState(), b.getState()));
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

    /**
     * Test get all.
     *
     * @throws DatabaseException the database exception
     */
    @Test
    public void testGetAll() throws DatabaseException {
        List<Batch> all = dbBatch.getAll();
        assertEquals(0, all.size());
    }

    /**
     * Test create.
     *
     * @throws DatabaseException the database exception
     */
    @Test
    public void testCreate() throws DatabaseException {

        Batch one = new Batch("batchtestcreate1", 10, 10);
        Batch two = new Batch("batchtestcreate2", 10, 10);
        Batch three = new Batch("batchTestCreate3", 10, 10);

        dbBatch.create(one);
        dbBatch.create(two);
        dbBatch.create(three);

        List<Batch> all = dbBatch.getAll();
        assertEquals(2, all.size());

        boolean hasFoundOne = false;
        boolean hasFoundTwo = false;
        boolean hasFoundThree = false;

        for (Batch b : all) {

            assertFalse(b.getID() == -1);

            if (!hasFoundOne) {
                hasFoundOne = areEqual(b, one, false);
            }
            if (!hasFoundTwo) {
                hasFoundTwo = areEqual(b, two, false);
            }
            if (!hasFoundThree) {
                hasFoundThree = areEqual(b, three, false);
            }
        }
        assertTrue(hasFoundOne && hasFoundTwo && hasFoundThree);
    }

    /**
     * Test update.
     *
     * @throws DatabaseException the database exception
     */
    @Test
    public void testUpdate() throws DatabaseException {

        Batch one = new Batch("batchUdateTest1", 10, 10);
        Batch two = new Batch("batchUdateTest2", 1, 1);
        Batch three = new Batch("batchUdateTest3", 15, 15);

        dbBatch.create(one);
        dbBatch.create(two);
        dbBatch.create(three);

        one.setState(0);
        two.setState(1);
        three.setState(2);

        dbBatch.update(one);
        dbBatch.update(two);
        dbBatch.update(three);

        List<Batch> all = dbBatch.getAll();
        assertEquals(3, all.size());

        boolean hasFoundOne = false;
        boolean hasFoundTwo = false;
        boolean hasFoundThree = false;

        for (Batch b : all) {
            assertFalse(b.getID() == -1);

            if (!hasFoundOne) {
                hasFoundOne = areEqual(b, one, false);
            }
            if (!hasFoundTwo) {
                hasFoundTwo = areEqual(b, two, false);
            }
            if (!hasFoundThree) {
                hasFoundThree = areEqual(b, three, false);
            }
        }
        assertTrue(hasFoundOne && hasFoundTwo && hasFoundThree);
    }

    /**
     * Test delete.
     *
     * @throws DatabaseException the database exception
     */
    @Test
    public void testDelete() throws DatabaseException {
        Batch one = new Batch("batchDeleteTest1", 10, 10);
        Batch two = new Batch("batchDeleteTest2", 5, 5);
        Batch three = new Batch("batchDeleteTest3", 1, 0);

        dbBatch.create(one);
        dbBatch.create(two);
        dbBatch.create(three);

        List<Batch> allBatches = dbBatch.getAll();
        assertEquals(3, allBatches.size());

        dbBatch.delete(one);
        dbBatch.delete(two);

        allBatches = dbBatch.getAll();
        assertEquals(2, allBatches.size());

        dbBatch.delete(three);

        allBatches = dbBatch.getAll();
        assertEquals(0, allBatches.size());
    }
}
