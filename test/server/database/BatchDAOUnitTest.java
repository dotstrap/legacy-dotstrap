/**
 * BatchDAOUnitTest.java
 * JRE v1.7.0_76
 *
 * Created by William Myers on Mar 15, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package server.database;

import static org.junit.Assert.*;

import java.util.List;
import java.util.logging.Logger;

import org.junit.*;

import shared.model.Batch;

// TODO: Auto-generated Javadoc
/**
 * The Class BatchDAOUnitTest.
 */
public class BatchDAOUnitTest {

    /** The logger used throughout the project. */
    private static Logger logger;
    static {
        logger = Logger.getLogger("server");
    }

    /**
     * Sets the up before class.
     *
     * @throws Exception
     *             the exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        logger.entering("server.database.BatchDAOUnitTest", "setUpBeforeClass");

        // Load database drivers
        Database.initDriver();

        logger.exiting("server.database.BatchDAOUnitTest", "setUpBeforeClass");
    }

    /**
     * Tear down after class.
     *
     * @throws Exception
     *             the exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        logger.entering("server.database.BatchDAOUnitTest", "tearDownAfterClass");
        logger.exiting("server.database.BatchDAOUnitTest", "tearDownAfterClass");
        return;
    }

    private Database db;
    private BatchDAO testBatchDAO;

    /**
     * Sets the database up.
     *
     * @throws Exception
     *             the exception
     */
    @Before
    public void setUp() throws Exception {
        logger.entering("server.database.BatchDAOUnitTest", "setUp");

        // Prepare database for test case
        db = new Database();
        db.startTransaction();
        db.initTables();
        testBatchDAO = db.getBatchDAO();

        logger.exiting("server.database.BatchDAOUnitTest", "setUp");
    }

    /**
     * Tear down.
     *
     * @throws Exception
     *             the exception
     */
    @After
    public void tearDown() throws Exception {
        logger.entering("server.database.BatchDAOUnitTest", "tearDown");

        // Roll back this transaction so changes are undone
        db.endTransaction(false);
        db = null;
        testBatchDAO = null;

        logger.exiting("server.database.BatchDAOUnitTest", "tearDown");
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
            if (a.getBatchID() != b.getBatchID()) {
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
        logger.entering("server.database.BatchDAOUnitTest", "testGetAll");

        List<Batch> allBatches = testBatchDAO.getAll();
        assertEquals(0, allBatches.size());

        logger.exiting("server.database.BatchDAOUnitTest", "testGetAll");
    }

    /**
     * Test create.
     *
     * @throws DatabaseException
     *             the database exception
     */
    @Test
    public void testCreate() throws DatabaseException {
        logger.entering("server.database.BatchDAOUnitTest", "testCreate");

        Batch testBatch1 = new Batch("batchTestCreate1", 10, 10);
        Batch testBatch2 = new Batch("batchTestCreate2", 10, 10);
        Batch testBatch3 = new Batch("batchTestCreate3", 10, 10);

        testBatchDAO.create(testBatch1);
        testBatchDAO.create(testBatch2);
        testBatchDAO.create(testBatch3);

        List<Batch> allBatches = testBatchDAO.getAll();
        assertEquals(3, allBatches.size());

        boolean hasFoundBatch1 = false;
        boolean hasFoundBatch2 = false;
        boolean hasFoundBatch3 = false;
        for (Batch b : allBatches) {
            assertFalse(b.getBatchID() == -1);
            if (!hasFoundBatch1) {
                hasFoundBatch1 = areEqual(b, testBatch1 ,false);
            }
            if (!hasFoundBatch2) {
                hasFoundBatch2 = areEqual(b, testBatch2 ,false);
            }
            if (!hasFoundBatch3) {
                hasFoundBatch3 = areEqual(b, testBatch3 ,false);
            }
        }
        assertTrue(hasFoundBatch1 && hasFoundBatch2 && hasFoundBatch3);

        logger.exiting("server.database.BatchDAOUnitTest", "testCreate");
    }

    /**
     * Test update.
     *
     * @throws DatabaseException
     *             the database exception
     */
    @Test
    public void testUpdate() throws DatabaseException {
        logger.entering("server.database.BatchDAOUnitTest", "testUpdate");

        Batch testBatch1 = new Batch("batchUdateTest1", 10, 10);
        Batch testBatch2 = new Batch("batchUdateTest2", 1, 1);
        Batch testBatch3 = new Batch("batchUdateTest3", 15, 15);

        testBatchDAO.create(testBatch1);
        testBatchDAO.create(testBatch2);
        testBatchDAO.create(testBatch3);

        testBatch1.setStatus(0);
        testBatch2.setStatus(1);
        testBatch3.setStatus(2);

        testBatchDAO.update(testBatch1);
        testBatchDAO.update(testBatch2);
        testBatchDAO.update(testBatch3);

        List<Batch> allBatches = testBatchDAO.getAll();
        assertEquals(3, allBatches.size());

        boolean hasFoundBatch1 = false;
        boolean hasFoundBatch2 = false;
        boolean hasFoundBatch3 = false;
        for (Batch b : allBatches) {
            assertFalse(b.getBatchID() == -1);
            if (!hasFoundBatch1) {
                hasFoundBatch1 = areEqual(b, testBatch1 ,false);
            }
            if (!hasFoundBatch2) {
                hasFoundBatch2 = areEqual(b, testBatch2 ,false);
            }
            if (!hasFoundBatch3) {
                hasFoundBatch3 = areEqual(b, testBatch3 ,false);
            }
        }
        assertTrue(hasFoundBatch1 && hasFoundBatch2 && hasFoundBatch3);

        logger.exiting("server.database.BatchDAOUnitTest", "testUpdate");
    }

    /**
     * Test delete.
     *
     * @throws DatabaseException
     *             the database exception
     */
    @Test
    public void testDelete() throws DatabaseException {
        logger.entering("server.database.BatchDAOUnitTest", "testDelete");

        Batch testBatch1 = new Batch("batchDeleteTest1", 10, 10);
        Batch testBatch2 = new Batch("batchDeleteTest2", 5, 5);
        Batch testBatch3 = new Batch("batchDeleteTest3", 1, 0);
        testBatchDAO.create(testBatch1);
        testBatchDAO.create(testBatch2);
        testBatchDAO.create(testBatch3);

        List<Batch> allBatches = testBatchDAO.getAll();
        assertEquals(3, allBatches.size());

        testBatchDAO.delete(testBatch1);
        allBatches = testBatchDAO.getAll();
        assertEquals(2, allBatches.size());

        testBatchDAO.delete(testBatch2);
        allBatches = testBatchDAO.getAll();
        assertEquals(1, allBatches.size());

        testBatchDAO.delete(testBatch3);
        allBatches = testBatchDAO.getAll();
        assertEquals(0, allBatches.size());

        logger.exiting("server.database.BatchDAOUnitTest", "testDelete");
    }
}
