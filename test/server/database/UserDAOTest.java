/**
 * UserDAOTest.java
 * JRE v1.7.0_76
 *
 * Created by William Myers on Mar 10, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package server.database;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.junit.*;

import shared.model.User;
/**
 * The Class UserDAOTest.
 */
public class UserDAOTest {

    private static Logger logger;

    /**
     * Sets the up before class.
     *
     * @throws Exception the exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        logger = Logger.getLogger("server");
        logger.entering("test.server.UserDAOTest", "setUpBeforeClass");
        // Load database driver
        Database.initDriver();
        logger.exiting("test.server.UserDAOTest", "setUpBeforeClass");
    }

    /**
     * Tear down after class.
     *
     * @throws Exception the exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        logger.entering("test.server.UserDAOTest", "tearDownAfterClass");
        logger.exiting("test.server.UserDAOTest", "tearDownAfterClass");
        return;
    }

    /** The db. */
    private Database db;

    /** The db user. */
    private UserDAO  dbUser;

    /**
     * Sets the database up.
     *
     * @throws Exception the exception
     */
    @Before
    public void setUp() throws Exception {
        logger.entering("test.server.UserDAOTest", "setUp");

        // Delete all users from the database
        db = new Database();
        db.initDBTables();
        db.startTransaction();

        ArrayList<User> users = db.getUserDAO().getAll();

        for (User u : users) {
            db.getUserDAO().delete(u);
        }
        db.endTransaction(true);

        // Prepare database for test case
        db = new Database();
        db.startTransaction();
        dbUser = db.getUserDAO();

        logger.exiting("test.server.UserDAOTest", "setUp");
    }

    /**
     * Tear down.
     *
     * @throws Exception the exception
     */
    @After
    public void tearDown() throws Exception {
        logger.entering("test.server.UserDAOTest", "tearDown");

        // Roll back this transaction so changes are undone
        db.endTransaction(true);
        db = null;
        dbUser = null;

        logger.exiting("test.server.UserDAOTest", "tearDown");
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

    private boolean areEqual(User a, User b, boolean shouldCompareIDs) {
        if (shouldCompareIDs) {
            if (a.getID() != b.getID()) {
                return false;
            }
        }
        return (safeEquals(a.getFirst(), b.getFirst())
                && safeEquals(a.getLast(), b.getLast())
                && safeEquals(a.getEmail(), b.getEmail())
                && safeEquals(a.getRecordCount(), b.getRecordCount())
				&& safeEquals(a.getCurrBatch(), b.getCurrBatch()));
    }

    /**
     * Test get all.
     *
     * @throws DatabaseException the database exception
     */
    @Test
    public void testGetAll() throws DatabaseException {
        logger.entering("test.server.UserDAOTest", "testGetAll");

        List<User> all = dbUser.getAll();
        assertEquals(0, all.size());

        logger.exiting("test.server.UserDAOTest", "testGetAll");
    }

    /**
     * Test create.
     *
     * @throws DatabaseException the database exception
     */
    @Test
    public void testCreate() throws DatabaseException {
        logger.entering("test.server.UserDAOTest", "testCreate");

        User firstTest = new User(
                "UserTestCreate1", "pass1", "first1", "last1", "email1", 1, 1);
        User secondTest = new User(
                "UserTestCreate2", "pass2", "first2", "last2", "email2", 2, 2);
        User thirdTest = new User(
                "UserTestCreate3", "pass3", "first3", "last3", "email3", 3, 3);

        dbUser.create(firstTest);
        dbUser.create(secondTest);
        dbUser.create(thirdTest);

        List<User> all = dbUser.getAll();
        assertEquals(3, all.size());

        boolean hasFoundOne = false;
        boolean hasFoundTwo = false;
        boolean hasFoundThree = false;

        for (User b : all) {

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

        logger.exiting("test.server.UserDAOTest", "testCreate");
    }

    /**
     * Test update.
     *
     * @throws DatabaseException the database exception
     */
    @Test
    public void testUpdate() throws DatabaseException {
        logger.entering("test.server.UserDAOTest", "testUpdate");

        User firstTest = new User(
                "UserTestCreate1", "pass1", "first1", "last1", "email1", 1, 1);
        User secondTest = new User(
                "UserTestCreate2", "pass2", "first2", "last2", "email2", 2, 2);
        User thirdTest = new User(
                "UserTestCreate3", "pass3", "first3", "last3", "email3", 3, 3);

        dbUser.create(firstTest);
        dbUser.create(secondTest);
        dbUser.create(thirdTest);

        firstTest.setFirst("first-001");
        secondTest.setFirst("first-002");
        thirdTest.setFirst("first-003");

        firstTest.setLast("last-001");
        secondTest.setLast("last-002");
        thirdTest.setLast("last-003");

        firstTest.setEmail("email-001");
        secondTest.setEmail("email-002");
        thirdTest.setEmail("email-003");

        dbUser.update(firstTest);
        dbUser.update(secondTest);
        dbUser.update(thirdTest);

        List<User> all = dbUser.getAll();
        assertEquals(3, all.size());

        boolean hasFoundOne = false;
        boolean hasFoundTwo = false;
        boolean hasFoundThree = false;

        for (User b : all) {
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

        logger.exiting("test.server.UserDAOTest", "testUpdate");
    }

    /**
     * Test delete.
     *
     * @throws DatabaseException the database exception
     */
    @Test
    public void testDelete() throws DatabaseException {
        logger.entering("test.server.UserDAOTest", "testDelete");

        User firstTest = new User(
                "UserTestCreate1", "pass1", "first1", "last1", "email1", 1, 1);
        User secondTest = new User(
                "UserTestCreate2", "pass2", "first2", "last2", "email2", 2, 2);
        User thirdTest = new User(
                "UserTestCreate3", "pass3", "first3", "last3", "email3", 3, 3);

        dbUser.create(firstTest);
        dbUser.create(secondTest);
        dbUser.create(thirdTest);

        List<User> allUseres = dbUser.getAll();
        assertEquals(3, allUseres.size());

        dbUser.delete(firstTest);
        allUseres = dbUser.getAll();
        assertEquals(2, allUseres.size());

        dbUser.delete(thirdTest);
        allUseres = dbUser.getAll();
        assertEquals(1, allUseres.size());

        dbUser.delete(secondTest);
        allUseres = dbUser.getAll();
        assertEquals(0, allUseres.size());

        logger.exiting("test.server.UserDAOTest", "testDelete");
    }
}
