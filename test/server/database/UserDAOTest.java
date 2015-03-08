/**
 * UserDAOTest.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 8, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package server.database;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import shared.model.*;

// TODO: Auto-generated Javadoc
/**
 * The Class UserDAOTest.
 */
public class UserDAOTest {
    
    /**
     * Sets the up before class.
     *
     * @throws Exception the exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // Load database driver
        Database.initialize();
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
    
    /** The db. */
    private Database db;
    
    /** The db user. */
    private UserDAO  dbUser;
    
    /**
     * Are equal.
     *
     * @param a the a
     * @param b the b
     * @param compareIDs the compare i ds
     * @return true, if successful
     */
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
                        && safeEquals(a.getUserInfo().getEmail(), b.getUserInfo().getEmail())
                        && safeEquals(a.getRecordCount(), b.getRecordCount()) && safeEquals(
                                a.getCurrentBatch(), b.getCurrentBatch()));
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
     * Sets the up.
     *
     * @throws Exception the exception
     */
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
    
    /**
     * Tear down.
     *
     * @throws Exception the exception
     */
    @After
    public void tearDown() throws Exception {
        
        // Roll back transaction so changes to database are undone
        db.endTransaction(true);
        
        db = null;
        dbUser = null;
    }
    
    /**
     * Test add.
     *
     * @throws DatabaseException the database exception
     */
    @Test
    public void testAdd() throws DatabaseException {
        User bob = new User(new Credentials("BobWhite", "bobwhite"), new UserInfo(
                "Bob", "White", "bobwhite@gmail.com"));
        User amy = new User(new Credentials("AmyBlack", "amyblack"), new UserInfo(
                "Amy", "Black", "amyblack@gmail.com"));
        User davis = new User(
                new Credentials("davisHyer", "davishyer"), new UserInfo(
                        "Davis", "Hyer", "davishyer@gmail.com"));
        
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
    
    /**
     * Test delete.
     *
     * @throws DatabaseException the database exception
     */
    @Test
    public void testDelete() throws DatabaseException {
        
        User bob = new User(new Credentials("BobWhite", "bobwhite"), new UserInfo(
                "Bob", "White", "bobwhite@gmail.com"));
        User amy = new User(new Credentials("AmyBlack", "amyblack"), new UserInfo(
                "Amy", "Black", "amyblack@gmail.com"));
        User davis = new User(
                new Credentials("davisHyer", "davishyer"), new UserInfo(
                        "Davis", "Hyer", "davishyer@gmail.com"));
        
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
    
    /**
     * Test get all.
     *
     * @throws DatabaseException the database exception
     */
    @Test
    public void testGetAll() throws DatabaseException {
        List<User> all = dbUser.getAll();
        assertEquals(0, all.size());
    }
    
    /**
     * Test update.
     *
     * @throws DatabaseException the database exception
     */
    @Test
    public void testUpdate() throws DatabaseException {
        
        User bob = new User(new Credentials("BobWhite", "bobwhite"), new UserInfo(
                "Bob", "White", "bobwhite@gmail.com"));
        User amy = new User(new Credentials("AmyBlack", "amyblack"), new UserInfo(
                "Amy", "Black", "amyblack@gmail.com"));
        User davis = new User(
                new Credentials("davisHyer", "davishyer"), new UserInfo(
                        "Davis", "Hyer", "davishyer@gmail.com"));
        
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
}
