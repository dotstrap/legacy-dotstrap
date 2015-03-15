/**
 * UserDAOUnitTest.java
 * JRE v1.7.0_76
 *
 * Created by William Myers on Mar 14, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package server.database;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import shared.model.User;

/**
 * The Class UserDAOUnitTest.
 */
public class UserDAOUnitTest {

    /**
     * Sets up before class.
     *
     * @throws Exception
     *             the exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // Load database driver
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
    private UserDAO  dbUserTest;

    /**
     * Sets the database up.
     *
     * @throws Exception
     *             the exception
     */
    @Before
    public void setUp() throws Exception {
        // Delete all users from the database
        db = new Database();
        // db.initDBTables();
        db.startTransaction();

        ArrayList<User> users = db.getUserDAO().getAll();
        for (User u : users) {
            db.getUserDAO().delete(u);
        }
        db.endTransaction(true);

        // Prepare database for test case
        db = new Database();
        db.startTransaction();
        dbUserTest = db.getUserDAO();

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
        db.endTransaction(false);
        db = null;
        dbUserTest = null;
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

    private boolean areEqual(User a, User b, boolean shouldCompareIDs) {
        if (shouldCompareIDs) {
            if (a.getID() != b.getID()) {
                return false;
            }
        }
        return (safeEquals(a.getFirst(), b.getFirst())
                && safeEquals(a.getLast(), b.getLast())
                && safeEquals(a.getEmail(), b.getEmail())
                && safeEquals(a.getRecordCount(), b.getRecordCount()) && safeEquals(
                    a.getCurrBatch(), b.getCurrBatch()));
    }

    /**
     * Test get all.
     *
     * @throws DatabaseException
     *             the database exception
     */
    @Test
    public void testGetAll() throws DatabaseException {
        List<User> allUsers = dbUserTest.getAll();
        assertEquals(0, allUsers.size());
    }

    /**
     * Test create.
     *
     * @throws DatabaseException
     *             the database exception
     */
    @Test
    public void testCreate() throws DatabaseException {
        User testUser1 = new User("UserTestCreate1", "pass1", "first1", "last1",
                "email1", 1, 1);
        User testUser2 = new User("UserTestCreate2", "pass2", "first2", "last2",
                "email2", 2, 2);
        User testUser3 = new User("UserTestCreate3", "pass3", "first3", "last3",
                "email3", 3, 3);

        dbUserTest.create(testUser1);
        dbUserTest.create(testUser2);
        dbUserTest.create(testUser3);

        List<User> all = dbUserTest.getAll();
        assertEquals(3, all.size());

        boolean hasFoundUser1 = false;
        boolean hasFoundUser2 = false;
        boolean hasFoundUser3 = false;
        for (User curr : all) {
            assertFalse(curr.getID() == -1);
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
    }

    /**
     * Test update.
     *
     * @throws DatabaseException
     *             the database exception
     */
    @Test
    public void testUpdate() throws DatabaseException {
        User testUser1 = new User("UserTestUpdate1", "pass1", "first1", "last1",
                "email1", 1, 1);
        User testUser2 = new User("UserTestUpdate2", "pass2", "first2", "last2",
                "email2", 2, 2);
        User testUser3 = new User("UserTestUpdate3", "pass3", "first3", "last3",
                "email3", 3, 3);

        dbUserTest.create(testUser1);
        dbUserTest.create(testUser2);
        dbUserTest.create(testUser3);

        testUser1.setFirst("first-001");
        testUser2.setFirst("first-002");
        testUser3.setFirst("first-003");

        testUser1.setLast("last-001");
        testUser2.setLast("last-002");
        testUser3.setLast("last-003");

        testUser1.setEmail("email-001");
        testUser2.setEmail("email-002");
        testUser3.setEmail("email-003");

        dbUserTest.update(testUser1);
        dbUserTest.update(testUser2);
        dbUserTest.update(testUser3);

        List<User> all = dbUserTest.getAll();
        assertEquals(3, all.size());

        boolean hasFoundUser1 = false;
        boolean hasFoundUser2 = false;
        boolean hasFoundUser3 = false;
        for (User curr : all) {
            assertFalse(curr.getID() == -1);
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
    }

    /**
     * Test delete.
     *
     * @throws DatabaseException
     *             the database exception
     */
    @Test
    public void testDelete() throws DatabaseException {
        User testUser1 = new User("UserTestDelete1", "pass1", "first1", "last1",
                "email1", 1, 1);
        User testUser2 = new User("UserTestDelete2", "pass2", "first2", "last2",
                "email2", 2, 2);
        User testUser3 = new User("UserTestDelete3", "pass3", "first3", "last3",
                "email3", 3, 3);

        dbUserTest.create(testUser1);
        dbUserTest.create(testUser2);
        dbUserTest.create(testUser3);

        List<User> allUseres = dbUserTest.getAll();
        assertEquals(3, allUseres.size());

        dbUserTest.delete(testUser1);
        allUseres = dbUserTest.getAll();
        assertEquals(2, allUseres.size());

        dbUserTest.delete(testUser3);
        allUseres = dbUserTest.getAll();
        assertEquals(1, allUseres.size());

        dbUserTest.delete(testUser2);
        allUseres = dbUserTest.getAll();
        assertEquals(0, allUseres.size());
    }

    //@Test
    //public void testValidateUser() throws DatabaseException {
        //User testUser1 = new User("UserTestCreate1", "pass1", "first1", "last1",
                //"email1", 1, 1);
        //User testUser2 = new User("UserTestCreate2", "pass2", "first2", "last2",
                //"email2", 2, 2);
        //User testUser3 = new User("UserTestCreate3", "pass3", "first3", "last3",
                //"email3", 3, 3);

        //dbUserTest.create(testUser1);
        //dbUserTest.create(testUser2);
        //dbUserTest.create(testUser3);

        //List<User> all = dbUserTest.getAll();

        //assertEquals(3, all.size());
        //assertTrue(dbUserTest.validateUser(testUser1) && dbUserTest.validateUser(testUser2));
    //}
}
