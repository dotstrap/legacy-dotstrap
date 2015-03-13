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

        Batch firstTest = new Batch("batchtestCreate1", 10, 10);
        Batch secondTest = new Batch("batchtestCreate2", 10, 10);
        Batch thirdTest = new Batch("batchTestCreate3", 10, 10);

        dbBatch.create(firstTest);
        dbBatch.create(secondTest);
        dbBatch.create(thirdTest);

        List<Batch> all = dbBatch.getAll();
        assertEquals(2, all.size());

        boolean hasFoundOne = false;
        boolean hasFoundTwo = false;
        boolean hasFoundThree = false;

        for (Batch b : all) {

            assertFalse(b.getID() == -1);

            if (!hasFoundOne) {
                hasFoundOne = areEqual(b, firstTest, false);
            }
            if (!hasFoundTwo) {
                hasFoundTwo = areEqual(b, secondTest, false);
            }
            if (!hasFoundThree) {
                hasFoundThree = areEqual(b, thirdTest, false);
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

        Batch firstTest = new Batch("batchUdateTest1", 10, 10);
        Batch secondTest = new Batch("batchUdateTest2", 1, 1);
        Batch thirdTest = new Batch("batchUdateTest3", 15, 15);

        dbBatch.create(firstTest);
        dbBatch.create(secondTest);
        dbBatch.create(thirdTest);

        firstTest.setState(0);
        secondTest.setState(1);
        thirdTest.setState(2);

        dbBatch.update(firstTest);
        dbBatch.update(secondTest);
        dbBatch.update(thirdTest);

        List<Batch> all = dbBatch.getAll();
        assertEquals(3, all.size());

        boolean hasFoundOne = false;
        boolean hasFoundTwo = false;
        boolean hasFoundThree = false;

        for (Batch b : all) {
            assertFalse(b.getID() == -1);

            if (!hasFoundOne) {
                hasFoundOne = areEqual(b, firstTest, false);
            }
            if (!hasFoundTwo) {
                hasFoundTwo = areEqual(b, secondTest, false);
            }
            if (!hasFoundThree) {
                hasFoundThree = areEqual(b, thirdTest, false);
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
        Batch firstTest = new Batch("batchDeleteTest1", 10, 10);
        Batch secondTest = new Batch("batchDeleteTest2", 5, 5);
        Batch thirdTest = new Batch("batchDeleteTest3", 1, 0);

        dbBatch.create(firstTest);
        dbBatch.create(secondTest);
        dbBatch.create(thirdTest);

        List<Batch> allBatches = dbBatch.getAll();
        assertEquals(3, allBatches.size());

        dbBatch.delete(firstTest);
        dbBatch.delete(secondTest);

        allBatches = dbBatch.getAll();
        assertEquals(2, allBatches.size());

        dbBatch.delete(thirdTest);

        allBatches = dbBatch.getAll();
        assertEquals(0, allBatches.size());
    }
}
