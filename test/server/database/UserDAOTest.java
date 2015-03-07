package server.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import shared.model.Credentials;
import shared.model.User;
import shared.model.UserInfo;

public class UserDAOTest {
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // Load database driver
        Database.initialize();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        return;
    }

    private Database db;
    private UserDAO  dbUser;

    @Before
    public void setUp() throws Exception {
        // Delete all users from the database
        db = new Database();
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
    }

    @After
    public void tearDown() throws Exception {

        // Roll back transaction so changes to database are undone
        db.endTransaction(true);

        db = null;
        dbUser = null;
    }

    @Test
    public void testGetAll() throws DatabaseException {
        List<User> all = dbUser.getAll();
        assertEquals(0, all.size());
    }

    @Test
    public void testAdd() throws DatabaseException {
        User bob = new User(new Credentials("BobWhite", "bobwhite"), new UserInfo(
                "Bob", "White", "bobwhite@gmail.com"));
        User amy = new User(new Credentials("AmyBlack", "amyblack"), new UserInfo(
                "Amy", "Black", "amyblack@gmail.com"));
        User davis = new User(new Credentials("davisHyer", "davishyer"),
                new UserInfo("Davis", "Hyer", "davishyer@gmail.com"));

        dbUser.add(bob);
        dbUser.add(amy);
        dbUser.add(davis);

        Credentials c = new Credentials("davisHyer", "davishyer");
        assertEquals("davisHyer", dbUser.getUser(c).getCreds().getUsername());

        List<User> all = dbUser.getAll();
        assertEquals(3, all.size());

        boolean foundBob = false;
        boolean foundAmy = false;
        boolean foundDavis = false;

        for (User u : all) {

            assertFalse(u.getID() == -1);

            if (!foundBob) {
                foundBob = areEqual(u, bob, false);
            }
            if (!foundAmy) {
                foundAmy = areEqual(u, amy, false);
            }
            if (!foundDavis) {
                foundDavis = areEqual(u, davis, false);
            }
        }
        assertTrue(foundBob && foundAmy && foundDavis);
    }

    @Test
    public void testUpdate() throws DatabaseException {

        User bob = new User(new Credentials("BobWhite", "bobwhite"), new UserInfo(
                "Bob", "White", "bobwhite@gmail.com"));
        User amy = new User(new Credentials("AmyBlack", "amyblack"), new UserInfo(
                "Amy", "Black", "amyblack@gmail.com"));
        User davis = new User(new Credentials("davisHyer", "davishyer"),
                new UserInfo("Davis", "Hyer", "davishyer@gmail.com"));

        dbUser.add(bob);
        dbUser.add(amy);
        dbUser.add(davis);

        bob.getUserInfo().setFirstName("Robert");
        amy.getUserInfo().setLastName("White");

        dbUser.update(bob);
        dbUser.update(amy);

        List<User> all = dbUser.getAll();
        assertEquals(3, all.size());

        boolean foundBob = false;
        boolean foundAmy = false;
        boolean foundDavis = false;

        for (User u : all) {

            assertFalse(u.getID() == -1);

            if (!foundBob) {
                foundBob = areEqual(u, bob, false);
            }
            if (!foundAmy) {
                foundAmy = areEqual(u, amy, false);
            }
            if (!foundDavis) {
                foundDavis = areEqual(u, davis, false);
            }
        }
        assertTrue(foundBob && foundAmy && foundDavis);
        dbUser.delete(bob);
        dbUser.delete(amy);
        dbUser.delete(davis);
    }

    @Test
    public void testDelete() throws DatabaseException {

        User bob = new User(new Credentials("BobWhite", "bobwhite"), new UserInfo(
                "Bob", "White", "bobwhite@gmail.com"));
        User amy = new User(new Credentials("AmyBlack", "amyblack"), new UserInfo(
                "Amy", "Black", "amyblack@gmail.com"));
        User davis = new User(new Credentials("davisHyer", "davishyer"),
                new UserInfo("Davis", "Hyer", "davishyer@gmail.com"));

        dbUser.add(bob);
        dbUser.add(amy);
        dbUser.add(davis);

        List<User> all = dbUser.getAll();
        assertEquals(3, all.size());

        dbUser.delete(bob);
        dbUser.delete(amy);

        all = dbUser.getAll();
        assertEquals(1, all.size());
        dbUser.delete(davis);
    }

    private boolean areEqual(User a, User b, boolean compareIDs) {
        if (compareIDs) {
            if (a.getID() != b.getID()) {
                return false;
            }
        }
        return (safeEquals(a.getUserInfo().getFirstName(), b.getUserInfo()
                .getFirstName())
                && safeEquals(a.getUserInfo().getLastName(), b.getUserInfo()
                        .getLastName())
                && safeEquals(a.getUserInfo().getEmail(), b.getUserInfo()
                        .getEmail())
                && safeEquals(a.getRecordCount(), b.getRecordCount()) && safeEquals(
                    a.getCurrentBatch(), b.getCurrentBatch()));
    }

    private boolean safeEquals(Object a, Object b) {
        if (a == null || b == null) {
            return (a == null && b == null);
        } else {
            return a.equals(b);
        }
    }
}
