/**
 * FieldDAOUnitTest.java JRE v1.7.0_76
 *
 * Created by William Myers on Mar 15, 2015. Copyright (c) 2015 William Myers. All Rights reserved.
 */
package server.database;

import static org.junit.Assert.*;

import java.util.List;
import java.util.logging.Logger;

import org.junit.*;

import shared.model.Field;

/**
 * The Class FieldDAOUnitTest.
 */
public class FieldDAOUnitTest {
    /** The logger used throughout the project. */
    private static Logger logger;
    static {
        logger = Logger.getLogger("serverTest");
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

    private boolean areEqual(Field a, Field b, boolean shouldCompareIDs) {
        if (shouldCompareIDs) {
            if (a.getFieldID() != b.getFieldID()) {
                return false;
            }
        }
        return (safeEquals(a.getProjectID(), b.getProjectID())
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

        Field testField1 =
                new Field(100, 111, "FieldTestDelete1", "KnownData1", "helpURL1", 1, 1, 1);
        Field testField2 =
                new Field(200, 222, "FieldTestDelete2", "KnownData2", "helpURL2", 2, 2, 2);
        Field testField3 =
                new Field(300, 333, "FieldTestDelete3", "KnownData3", "helpURL3", 3, 3, 3);

        testFieldDAO.create(testField1);
        testFieldDAO.create(testField2);
        testFieldDAO.create(testField3);

        List<Field> all = testFieldDAO.getAll();
        assertEquals(3, all.size());

        boolean hasFoundField1 = false;
        boolean hasFoundField2 = false;
        boolean hasFoundField3 = false;
        for (Field curr : all) {
            assertFalse(curr.getFieldID() == -1);
            if (!hasFoundField1) {
                hasFoundField1 = areEqual(curr, testField1, false);
            }
            if (!hasFoundField2) {
                hasFoundField2 = areEqual(curr, testField2, false);
            }
            if (!hasFoundField3) {
                hasFoundField3 = areEqual(curr, testField3, false);
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

        Field testField1 =
                new Field(100, 111, "FieldTestDelete1", "KnownData1", "helpURL1", 1, 1, 1);
        Field testField2 =
                new Field(200, 222, "FieldTestDelete2", "KnownData2", "helpURL2", 2, 2, 2);
        Field testField3 =
                new Field(300, 333, "FieldTestDelete3", "KnownData3", "helpURL3", 3, 3, 3);

        testFieldDAO.create(testField1);
        testFieldDAO.create(testField2);
        testFieldDAO.create(testField3);

        List<Field> allFieldes = testFieldDAO.getAll();
        assertEquals(3, allFieldes.size());

        testFieldDAO.delete(testField1);
        allFieldes = testFieldDAO.getAll();
        assertEquals(2, allFieldes.size());

        testFieldDAO.delete(testField3);
        allFieldes = testFieldDAO.getAll();
        assertEquals(1, allFieldes.size());

        testFieldDAO.delete(testField2);
        allFieldes = testFieldDAO.getAll();
        assertEquals(0, allFieldes.size());

        logger.exiting("server.database.FieldDAOUnitTest", "testDelete");
    }

    // @Test
    // public void testValidateField() throws DatabaseException {
    // Field testField1 = new Field("FieldTestCreate1", "ProjectID1",
    // "KnownData1", "helpURL1",
    // 1, 1);
    // Field testField2 = new Field("FieldTestCreate2", "ProjectID2",
    // "KnownData2", "helpURL2",
    // 2, 2);
    // Field testField3 = new Field("FieldTestCreate3", "ProjectID3",
    // "KnownData3", "helpURL3",
    // 3, 3);

    // dbFieldTest.create(testField1);
    // dbFieldTest.create(testField2);
    // dbFieldTest.create(testField3);

    // List<Field> all = dbFieldTest.getAll();

    // assertEquals(3, all.size());
    // assertTrue(dbFieldTest.validateField(testField1) &&
    // dbFieldTest.validateField(testField2));
    // }
}
