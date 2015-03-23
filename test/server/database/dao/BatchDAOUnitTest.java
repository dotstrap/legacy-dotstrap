/**
 * BatchDAOUnitTest.java
 * JRE v1.8.0_40
 * 
 * Created by William Myers on Mar 22, 2015.
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

import shared.model.Batch;

// TODO: Auto-generated Javadoc
/**
 * The Class BatchDAOUnitTest.
 */
public class BatchDAOUnitTest {
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
        logger.entering("server.database.BatchDAOUnitTest", "setUpBeforeClass");

        // Load database drivers
        Database.initDriver();

        logger.exiting("server.database.BatchDAOUnitTest", "setUpBeforeClass");
    }

    /**
     * Tear down after class.
     *
     * @throws Exception the exception
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
     * @throws Exception the exception
     */
    @Before
    public void setUp() throws Exception {
        logger.entering("server.database.BatchDAOUnitTest", "setUp");

        db = new Database();
        db.startTransaction();
        testBatchDAO = db.getBatchDAO();
        testBatchDAO.initTable();

        logger.exiting("server.database.BatchDAOUnitTest", "setUp");
    }

    /**
     * Tear down.
     *
     * @throws Exception the exception
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
     * @param a the a
     * @param b the b
     * @param compareIds the compare i ds
     * @return true, if successful
     */
    private boolean areEqual(Batch a, Batch b, boolean compareIds) {
        if (compareIds) {
            if (a.getBatchId() != b.getBatchId()) {
                return false;
            }
        }
        return (safeEquals(a.getFilePath(), b.getFilePath())
                && safeEquals(a.getProjectId(), b.getProjectId()) && safeEquals(a.getStatus(),
                    b.getStatus()));
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
     * Test get allBatches.
     *
     * @throws DatabaseException the database exception
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
     * @throws DatabaseException the database exception
     */
    @Test
    public void testCreate() throws DatabaseException {
        logger.entering("server.database.BatchDAOUnitTest", "testCreate");
        // TODO:refractor these for all unit test cases into the setup method

        Batch batchTest1 = new Batch("batchTestCreate1", 10, 10);
        Batch batchTest2 = new Batch("batchTestCreate2", 10, 10);
        Batch batchTest3 = new Batch("batchTestCreate3", 10, 10);

        testBatchDAO.create(batchTest1);
        testBatchDAO.create(batchTest2);
        testBatchDAO.create(batchTest3);

        List<Batch> allBatches = testBatchDAO.getAll();
        assertEquals(3, allBatches.size());

        boolean hasFoundBatch1 = false;
        boolean hasFoundBatch2 = false;
        boolean hasFoundBatch3 = false;
        for (Batch b : allBatches) {
            assertFalse(b.getBatchId() == -1);
            if (!hasFoundBatch1) {
                hasFoundBatch1 = areEqual(b, batchTest1, false);
            }
            if (!hasFoundBatch2) {
                hasFoundBatch2 = areEqual(b, batchTest2, false);
            }
            if (!hasFoundBatch3) {
                hasFoundBatch3 = areEqual(b, batchTest3, false);
            }
        }
        assertTrue(hasFoundBatch1 && hasFoundBatch2 && hasFoundBatch3);

        logger.exiting("server.database.BatchDAOUnitTest", "testCreate");
    }

    /**
     * Test update.
     *
     * @throws DatabaseException the database exception
     */
    @Test
    public void testUpdate() throws DatabaseException {
        logger.entering("server.database.BatchDAOUnitTest", "testUpdate");

        Batch batchTest1 = new Batch("batchUdateTest1", 10, 10);
        Batch batchTest2 = new Batch("batchUdateTest2", 1, 1);
        Batch batchTest3 = new Batch("batchUdateTest3", 15, 15);

        testBatchDAO.create(batchTest1);
        testBatchDAO.create(batchTest2);
        testBatchDAO.create(batchTest3);

        batchTest1.setStatus(0);
        batchTest2.setStatus(1);
        batchTest3.setStatus(2);

        testBatchDAO.update(batchTest1);
        testBatchDAO.update(batchTest2);
        testBatchDAO.update(batchTest3);

        List<Batch> allBatches = testBatchDAO.getAll();
        assertEquals(3, allBatches.size());

        boolean hasFoundBatch1 = false;
        boolean hasFoundBatch2 = false;
        boolean hasFoundBatch3 = false;
        for (Batch b : allBatches) {
            assertFalse(b.getBatchId() == -1);
            if (!hasFoundBatch1) {
                hasFoundBatch1 = areEqual(b, batchTest1, false);
            }
            if (!hasFoundBatch2) {
                hasFoundBatch2 = areEqual(b, batchTest2, false);
            }
            if (!hasFoundBatch3) {
                hasFoundBatch3 = areEqual(b, batchTest3, false);
            }
        }
        assertTrue(hasFoundBatch1 && hasFoundBatch2 && hasFoundBatch3);

        logger.exiting("server.database.BatchDAOUnitTest", "testUpdate");
    }

    /**
     * Test delete.
     *
     * @throws DatabaseException the database exception
     */
    @Test
    public void testDelete() throws DatabaseException {
        logger.entering("server.database.BatchDAOUnitTest", "testDelete");

        Batch batchTest1 = new Batch("batchDeleteTest1", 10, 10);
        Batch batchTest2 = new Batch("batchDeleteTest2", 5, 5);
        Batch batchTest3 = new Batch("batchDeleteTest3", 1, 0);

        testBatchDAO.create(batchTest1);
        testBatchDAO.create(batchTest2);
        testBatchDAO.create(batchTest3);

        List<Batch> allBatches = testBatchDAO.getAll();
        assertEquals(3, allBatches.size());

        testBatchDAO.delete(batchTest1);
        allBatches = testBatchDAO.getAll();
        assertEquals(2, allBatches.size());

        testBatchDAO.delete(batchTest2);
        allBatches = testBatchDAO.getAll();
        assertEquals(1, allBatches.size());

        testBatchDAO.delete(batchTest3);
        allBatches = testBatchDAO.getAll();
        assertEquals(0, allBatches.size());

        logger.exiting("server.database.BatchDAOUnitTest", "testDelete");
    }
}
