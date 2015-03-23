/**
 * FieldDAOUnitTest.java
 * JRE v1.8.0_40
 * 
 * Created by William Myers on Mar 22, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package server.database.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;
import java.util.logging.Logger;

import org.junit.*;

import server.ServerUnitTests;
import server.database.Database;
import server.database.DatabaseException;

import shared.model.Field;

/**
 * The Class FieldDAOUnitTest.
 */
public class FieldDAOUnitTest {
    /** The logger used throughout the project. */
    private static Logger logger;
    static {
        logger = Logger.getLogger(ServerUnitTests.LOG_NAME);
    }

    /**
     * Sets up before class.
     *
     * @throws Exception the exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        logger.entering("server.database.FieldDAOUnitTest", "setUpBeforeClass");

        // Load database driver
        Database.initDriver();

        logger.exiting("server.database.FieldDAOUnitTest", "setUpBeforeClass");
    }

    /**
     * Tear down after class.
     *
     * @throws Exception the exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        logger.entering("server.database.FieldDAOUnitTest", "tearDownAfterClass");
        logger.exiting("server.database.FieldDAOUnitTest", "tearDownAfterClass");
        return;
    }

    private Database db;
    private FieldDAO testFieldDAO;

    /**
     * Sets the database up.
     *
     * @throws Exception the exception
     */
    @Before
    public void setUp() throws Exception {
        logger.entering("server.database.FieldDAOUnitTest", "setUp");

        // Prepare database for test case
        db = new Database();
        db.startTransaction();
        testFieldDAO = db.getFieldDAO();
        testFieldDAO.initTable();

        logger.exiting("server.database.FieldDAOUnitTest", "setUp");
    }

    /**
     * Tear down.
     *
     * @throws Exception the exception
     */
    @After
    public void tearDown() throws Exception {
        logger.entering("server.database.FieldDAOUnitTest", "tearDown");

        // Roll back this transaction so changes are undone
        db.endTransaction(false);
        db = null;
        testFieldDAO = null;

        logger.exiting("server.database.FieldDAOUnitTest", "tearDown");
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
                && safeEquals(a.getxCoord(), b.getxCoord()) && safeEquals(a.getWidth(),
                    b.getWidth()));
    }

    /**
     * Test get all.
     *
     * @throws DatabaseException the database exception
     */
    @Test
    public void testGetAll() throws DatabaseException {
        logger.entering("server.database.FieldDAOUnitTest", "testGetAll");

        List<Field> allFields = testFieldDAO.getAll();
        assertEquals(0, allFields.size());

        logger.exiting("server.database.FieldDAOUnitTest", "testGetAll");
    }

    /**
     * Test create.
     *
     * @throws DatabaseException the database exception
     */
    @Test
    public void testCreate() throws DatabaseException {
        logger.entering("server.database.FieldDAOUnitTest", "testCreate");

        Field fieldTest1 =
                new Field(100, 111, "fieldTestDelete1", "KnownData1", "helpURL1", 1, 1, 1);
        Field fieldTest2 =
                new Field(200, 222, "fieldTestDelete2", "KnownData2", "helpURL2", 2, 2, 2);
        Field fieldTest3 =
                new Field(300, 333, "fieldTestDelete3", "KnownData3", "helpURL3", 3, 3, 3);

        testFieldDAO.create(fieldTest1);
        testFieldDAO.create(fieldTest2);
        testFieldDAO.create(fieldTest3);

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
        logger.entering("server.database.FieldDAOUnitTest", "testDelete");

        Field fieldTest1 =
                new Field(100, 111, "fieldTestDelete1", "KnownData1", "helpURL1", 1, 1, 1);
        Field fieldTest2 =
                new Field(200, 222, "fieldTestDelete2", "KnownData2", "helpURL2", 2, 2, 2);
        Field fieldTest3 =
                new Field(300, 333, "fieldTestDelete3", "KnownData3", "helpURL3", 3, 3, 3);

        testFieldDAO.create(fieldTest1);
        testFieldDAO.create(fieldTest2);
        testFieldDAO.create(fieldTest3);

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

        logger.exiting("server.database.FieldDAOUnitTest", "testDelete");
    }

    // @Test
    // public void testValidateField() throws DatabaseException {
    // Field fieldTest1 = new Field("fieldTestCreate1", "ProjectId1",
    // "KnownData1", "helpURL1",
    // 1, 1);
    // Field fieldTest2 = new Field("fieldTestCreate2", "ProjectId2",
    // "KnownData2", "helpURL2",
    // 2, 2);
    // Field fieldTest3 = new Field("fieldTestCreate3", "ProjectId3",
    // "KnownData3", "helpURL3",
    // 3, 3);

    // dbfieldTest.create(fieldTest1);
    // dbfieldTest.create(fieldTest2);
    // dbfieldTest.create(fieldTest3);

    // List<Field> all = dbfieldTest.getAll();

    // assertEquals(3, all.size());
    // assertTrue(dbfieldTest.validateField(fieldTest1) &&
    // dbfieldTest.validateField(fieldTest2));
    // }
}
