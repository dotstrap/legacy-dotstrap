/**
 * RecordDAOUnitTest.java
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

import shared.model.Record;

// TODO: Auto-generated Javadoc
/**
 * The Class RecordDAOUnitTest.
 */
public class RecordDAOUnitTest {
    
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
    private Database  db;
    
    /** The db record. */
    private RecordDAO dbRecord;
    
    /**
     * Are equal.
     *
     * @param a the a
     * @param b the b
     * @param compareIDs the compare i ds
     * @return true, if successful
     */
    private boolean areEqual(Record a, Record b, boolean compareIDs) {
        if (compareIDs) {
            if (a.getID() != b.getID()) {
                return false;
            }
        }
        return (safeEquals(a.getBatchID(), b.getBatchID())
                && safeEquals(a.getData(), b.getData())
                && safeEquals(a.getFieldID(), b.getFieldID()) && safeEquals(
                        a.getRecordNumber(), b.getRecordNumber()));
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
        ArrayList<Record> records = db.getRecordDAO().getAll();
        
        for (Record r : records) {
            db.getRecordDAO().delete(r);
        }
        
        db.endTransaction(true);
        
        // Prepare database for test case
        db = new Database();
        db.startTransaction();
        dbRecord = db.getRecordDAO();
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
        dbRecord = null;
    }
    
    /**
     * Test add.
     *
     * @throws DatabaseException the database exception
     */
    @Test
    public void testAdd() throws DatabaseException {
        Record one = new Record(1, 1, "one", 1);
        Record two = new Record(2, 2, "two", 2);
        
        dbRecord.add(one);
        dbRecord.add(two);
        
        List<Record> all = dbRecord.getAll();
        assertEquals(2, all.size());
        
        boolean foundOne = false;
        boolean foundTwo = false;
        
        for (Record r : all) {
            
            assertFalse(r.getID() == -1);
            
            if (!foundOne) {
                foundOne = areEqual(r, one, false);
            }
            if (!foundTwo) {
                foundTwo = areEqual(r, two, false);
            }
        }
        assertTrue(foundOne && foundTwo);
    }
    
    /*
     * @Test public void testUpdate() throws DatabaseException { User bob = new
     * User(new Credentials("BobWhite", "bobwhite"), new UserInfo("Bob",
     * "White", "bobwhite@gmail.com")); User amy = new User(new
     * Credentials("AmyBlack", "amyblack"), new UserInfo("Amy", "Black",
     * "amyblack@gmail.com")); User davis = new User(new
     * Credentials("davisHyer", "davishyer"), new UserInfo("Davis", "Hyer",
     * "davishyer@gmail.com")); dbUser.add(bob); dbUser.add(amy);
     * dbUser.add(davis); bob.getUserInfo().setFirstName("Robert");
     * amy.getUserInfo().setLastName("White"); dbUser.update(bob);
     * dbUser.update(amy); List<User> all = dbUser.getAll(); assertEquals(3,
     * all.size()); boolean foundBob = false; boolean foundAmy = false; boolean
     * foundDavis = false; for (User u : all) { assertFalse(u.getID() == -1); if
     * (!foundBob) { foundBob = areEqual(u, bob, false); } if (!foundAmy) {
     * foundAmy = areEqual(u, amy, false); } if (!foundDavis) { foundDavis =
     * areEqual(u, davis, false); } } assertTrue(foundBob && foundAmy &&
     * foundDavis); }
     */
    
    /**
     * Test delete.
     *
     * @throws DatabaseException the database exception
     */
    @Test
    public void testDelete() throws DatabaseException {
        
        Record one = new Record(1, 1, "one", 1);
        Record two = new Record(2, 2, "two", 2);
        
        dbRecord.add(one);
        dbRecord.add(two);
        
        List<Record> all = dbRecord.getAll();
        assertEquals(2, all.size());
        
        dbRecord.delete(one);
        dbRecord.delete(two);
        
        all = dbRecord.getAll();
        assertEquals(0, all.size());
    }
    
    /*
     * @Test(expected=DatabaseException.class) public void testInvalidAdd()
     * throws DatabaseException { Contact invalidContact = new Contact(-1, null,
     * null, null, null, null); dbContacts.add(invalidContact); }
     * @Test(expected=DatabaseException.class) public void testInvalidUpdate()
     * throws DatabaseException { Contact invalidContact = new Contact(-1, null,
     * null, null, null, null); dbContacts.update(invalidContact); }
     * @Test(expected=DatabaseException.class) public void testInvalidDelete()
     * throws DatabaseException { Contact invalidContact = new Contact(-1, null,
     * null, null, null, null); dbContacts.delete(invalidContact); }
     */
    
    /**
     * Test get all.
     *
     * @throws DatabaseException the database exception
     */
    @Test
    public void testGetAll() throws DatabaseException {
        List<Record> all = dbRecord.getAll();
        assertEquals(0, all.size());
    }
    
    /**
     * Test search.
     *
     * @throws DatabaseException the database exception
     */
    @Test
    public void testSearch() throws DatabaseException {
        Record one = new Record(1, 1, "one", 1);
        Record two = new Record(2, 2, "two", 2);
        
        dbRecord.add(one);
        dbRecord.add(two);
        
        ArrayList<Record> search = dbRecord.search(1, "one");
        assertEquals(1, search.size());
    }
}
