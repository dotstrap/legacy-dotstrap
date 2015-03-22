/**
 * UserDAOUnitTest.java
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

import server.ServerUnitTests;
import server.database.dao.UserDAO;

import shared.model.User;

/**
 * The Class UserDAOUnitTest.
 */
public class UserDAOUnitTest {
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
        logger.entering("server.database.UserDAOUnitTest", "setUpBeforeClass");

        // Load database driver
        Database.initDriver();

        logger.exiting("server.database.UserDAOUnitTest", "setUpBeforeClass");
    }

    /**
     * Tear down after class.
     *
     * @throws Exception the exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        logger.entering("server.database.UserDAOUnitTest", "tearDownAfterClass");
        logger.exiting("server.database.UserDAOUnitTest", "tearDownAfterClass");
        return;
    }

    private Database db;
    private UserDAO  testUserDAO;

    /**
     * Sets the database up.
     *
     * @throws Exception the exception
     */
    @Before
    public void setUp() throws Exception {
        logger.entering("server.database.UserDAOUnitTest", "setUp");

        // Prepare database for test case
        db = new Database();
        db.startTransaction();
        testUserDAO = db.getUserDAO();
        testUserDAO.initTable();

        logger.exiting("server.database.UserDAOUnitTest", "setUp");
    }

    /**
     * Tear down.
     *
     * @throws Exception the exception
     */
    @After
    public void tearDown() throws Exception {
        logger.entering("server.database.UserDAOUnitTest", "tearDown");

        // Roll back this transaction so changes are undone
        db.endTransaction(false);
        db = null;
        testUserDAO = null;

        logger.exiting("server.database.UserDAOUnitTest", "tearDown");
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

    private boolean areEqual(User a, User b, boolean shouldCompareIds) {
        if (shouldCompareIds) {
            if (a.getUserId() != b.getUserId()) {
                return false;
            }
        }
        return (safeEquals(a.getFirst(), b.getFirst()) && safeEquals(a.getLast(), b.getLast())
                && safeEquals(a.getEmail(), b.getEmail())
                && safeEquals(a.getRecordCount(), b.getRecordCount()) && safeEquals(
                    a.getCurrBatch(), b.getCurrBatch()));
    }

    /**
     * Test get all.
     *
     * @throws DatabaseException the database exception
     */
    @Test
    public void testGetAll() throws DatabaseException {
        logger.entering("server.database.UserDAOUnitTest", "testGetAll");

        List<User> allUsers = testUserDAO.getAll();
        assertEquals(0, allUsers.size());

        logger.exiting("server.database.UserDAOUnitTest", "testGetAll");
    }

    /**
     * Test create.
     *
     * @throws DatabaseException the database exception
     */
    @Test
    public void testCreate() throws DatabaseException {
        logger.entering("server.database.UserDAOUnitTest", "testCreate");

        User testUser1 = new User("Create1", "pass1", "first1", "last1", "email1", 1, 1);
        User testUser2 = new User("Create2", "pass2", "first2", "last2", "email2", 2, 2);
        User testUser3 = new User("Create3", "pass3", "first3", "last3", "email3", 3, 3);

        testUserDAO.create(testUser1);
        testUserDAO.create(testUser2);
        testUserDAO.create(testUser3);

        List<User> all = testUserDAO.getAll();
        assertEquals(3, all.size());

        boolean hasFoundUser1 = false;
        boolean hasFoundUser2 = false;
        boolean hasFoundUser3 = false;
        for (User curr : all) {
            assertFalse(curr.getUserId() == -1);
            if (!hasFoundUser1) {
                hasFoundUser1 = areEqual(curr, testUser1, false);
            }
            if (!hasFoundUser2) {
                hasFoundUser2 = areEqual(curr, testUser2, false);
            }
            if (!hasFoundUser3) {
                hasFoundUser3 = areEqual(curr, testUser3, false);
            }
        }
        assertTrue(hasFoundUser1 && hasFoundUser2 && hasFoundUser3);

        logger.exiting("server.database.UserDAOUnitTest", "testCreate");
    }

    /**
     * Test update.
     *
     * @throws DatabaseException the database exception
     */
    @Test
    public void testUpdate() throws DatabaseException {
        logger.entering("server.database.UserDAOUnitTest", "testUpdate");

        User testUser1 = new User("Update1", "pass1", "first1", "last1", "email1", 1, 1);
        User testUser2 = new User("Update2", "pass2", "first2", "last2", "email2", 2, 2);
        User testUser3 = new User("Update3", "pass3", "first3", "last3", "email3", 3, 3);

        testUserDAO.create(testUser1);
        testUserDAO.create(testUser2);
        testUserDAO.create(testUser3);

        testUser1.setFirst("first-001");
        testUser2.setFirst("first-002");
        testUser3.setFirst("first-003");

        testUser1.setLast("last-001");
        testUser2.setLast("last-002");
        testUser3.setLast("last-003");

        testUser1.setEmail("email-001");
        testUser2.setEmail("email-002");
        testUser3.setEmail("email-003");

        testUserDAO.update(testUser1);
        testUserDAO.update(testUser2);
        testUserDAO.update(testUser3);

        List<User> all = testUserDAO.getAll();
        assertEquals(3, all.size());

        boolean hasFoundUser1 = false;
        boolean hasFoundUser2 = false;
        boolean hasFoundUser3 = false;
        for (User curr : all) {
            assertFalse(curr.getUserId() == -1);
            if (!hasFoundUser1) {
                hasFoundUser1 = areEqual(curr, testUser1, false);
            }
            if (!hasFoundUser2) {
                hasFoundUser2 = areEqual(curr, testUser2, false);
            }
            if (!hasFoundUser3) {
                hasFoundUser3 = areEqual(curr, testUser3, false);
            }
        }
        assertTrue(hasFoundUser1 && hasFoundUser2 && hasFoundUser3);

        logger.exiting("server.database.UserDAOUnitTest", "testUpdate");
    }

    /**
     * Test delete.
     *
     * @throws DatabaseException the database exception
     */
    @Test
    public void testDelete() throws DatabaseException {
        logger.entering("server.database.UserDAOUnitTest", "testDelete");

        User testUser1 = new User("Delete1", "pass1", "first1", "last1", "email1", 1, 1);
        User testUser2 = new User("Delete2", "pass2", "first2", "last2", "email2", 2, 2);
        User testUser3 = new User("Delete3", "pass3", "first3", "last3", "email3", 3, 3);

        testUserDAO.create(testUser1);
        testUserDAO.create(testUser2);
        testUserDAO.create(testUser3);

        List<User> allUseres = testUserDAO.getAll();
        assertEquals(3, allUseres.size());

        testUserDAO.delete(testUser1);
        allUseres = testUserDAO.getAll();
        assertEquals(2, allUseres.size());

        testUserDAO.delete(testUser3);
        allUseres = testUserDAO.getAll();
        assertEquals(1, allUseres.size());

        testUserDAO.delete(testUser2);
        allUseres = testUserDAO.getAll();
        assertEquals(0, allUseres.size());

        logger.exiting("server.database.UserDAOUnitTest", "testDelete");
    }

}
