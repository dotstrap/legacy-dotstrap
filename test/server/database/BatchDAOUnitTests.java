/**
 * BatchDAOUnitTests.java
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

import shared.model.Batch;

// TODO: Auto-generated Javadoc
/**
 * The Class BatchDAOUnitTests.
 */
public class BatchDAOUnitTests {
    
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
    
    /** The db batch. */
    private BatchDAO dbBatch;
    
    /**
     * Are equal.
     *
     * @param a the a
     * @param b the b
     * @param compareIDs the compare i ds
     * @return true, if successful
     */
    private boolean areEqual(Batch a, Batch b, boolean compareIDs) {
        if (compareIDs) {
            if (a.getID() != b.getID()) {
                return false;
            }
        }
        return (safeEquals(a.getFilePath(), b.getFilePath())
                && safeEquals(a.getProjectID(), b.getProjectID()) && safeEquals(
                        a.getState(), b.getState()));
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
        
        ArrayList<Batch> batches = db.getBatchDAO().getAll();
        
        for (Batch b : batches) {
            db.getBatchDAO().delete(b);
        }
        
        db.endTransaction(true);
        
        // Prepare database for test case
        db = new Database();
        db.startTransaction();
        dbBatch = db.getBatchDAO();
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
        dbBatch = null;
    }
    
    /**
     * Test add.
     *
     * @throws DatabaseException the database exception
     */
    @Test
    public void testAdd() throws DatabaseException {
        
        Batch one = new Batch("temporary.file.path", 10, 10);
        Batch two = new Batch("temporary.file.path.two", 10, 10);
        
        dbBatch.add(one);
        dbBatch.add(two);
        
        List<Batch> all = dbBatch.getAll();
        assertEquals(2, all.size());
        
        boolean hasFoundOne = false;
        boolean hasFoundTwo = false;
        
        for (Batch b : all) {
            
            assertFalse(b.getID() == -1);
            
            if (!hasFoundOne) {
                hasFoundOne = areEqual(b, one, false);
            }
            if (!hasFoundTwo) {
                hasFoundTwo = areEqual(b, two, false);
            }
        }
        
        assertTrue(hasFoundOne && hasFoundTwo);
    }
    
    /**
     * Test delete.
     *
     * @throws DatabaseException the database exception
     */
    @Test
    public void testDelete() throws DatabaseException {
        
        Batch one = new Batch("deleteMe1", 10, 10);
        Batch two = new Batch("deleteMe2", 5, 5);
        Batch three = new Batch("deleteMe3", 1, 0);
        
        dbBatch.add(one);
        dbBatch.add(two);
        dbBatch.add(three);
        
        List<Batch> all = dbBatch.getAll();
        assertEquals(3, all.size());
        
        dbBatch.delete(one);
        dbBatch.delete(two);
        
        all = dbBatch.getAll();
        assertEquals(1, all.size());
        
        dbBatch.delete(three);
    }
    
    /**
     * Test get all.
     *
     * @throws DatabaseException the database exception
     */
    @Test
    public void testGetAll() throws DatabaseException {
        
        List<Batch> all = dbBatch.getAll();
        assertEquals(0, all.size());
    }
    
    /**
     * Test update.
     *
     * @throws DatabaseException the database exception
     */
    @Test
    public void testUpdate() throws DatabaseException {
        
        Batch one = new Batch("one", 10, 10);
        Batch two = new Batch("two", 1, 1);
        Batch three = new Batch("three", 15, 15);
        
        dbBatch.add(one);
        dbBatch.add(two);
        dbBatch.add(three);
        
        one.setState(0);
        two.setState(1);
        
        dbBatch.update(one);
        dbBatch.update(two);
        
        List<Batch> all = dbBatch.getAll();
        assertEquals(3, all.size());
        
        boolean foundOne = false;
        boolean foundTwo = false;
        boolean foundThree = false;
        
        for (Batch b : all) {
            
            assertFalse(b.getID() == -1);
            
            if (!foundOne) {
                foundOne = areEqual(b, one, false);
            }
            if (!foundTwo) {
                foundTwo = areEqual(b, two, false);
            }
            if (!foundThree) {
                foundThree = areEqual(b, three, false);
            }
        }
        assertTrue(foundOne && foundTwo && foundThree);
    }
}
