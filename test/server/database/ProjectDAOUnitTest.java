/**
 * ProjectDAOUnitTest.java
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

import shared.model.Project;
import shared.model.ProjectInfo;

// TODO: Auto-generated Javadoc
/**
 * The Class ProjectDAOUnitTest.
 */
public class ProjectDAOUnitTest {
    
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
    private Database   db;
    
    /** The db project. */
    private ProjectDAO dbProject;
    
    /**
     * Are equal.
     *
     * @param a the a
     * @param b the b
     * @param compareIDs the compare i ds
     * @return true, if successful
     */
    private boolean areEqual(Project a, Project b, boolean compareIDs) {
        if (compareIDs) {
            if (a.getProjInfo().getID() != b.getProjInfo().getID()) {
                return false;
            }
        }
        return (safeEquals(a.getFirstY(), b.getFirstY())
                && safeEquals(a.getProjInfo().getName(), b.getProjInfo().getName())
                && safeEquals(a.getRecordHeight(), b.getRecordHeight()) && safeEquals(
                        a.getRecordsPerBatch(), b.getRecordsPerBatch()));
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
        
        ArrayList<Project> projects = db.getProjectDAO().getAll();
        
        for (Project p : projects) {
            db.getProjectDAO().delete(p);
        }
        
        db.endTransaction(true);
        
        // Prepare database for test case
        db = new Database();
        db.startTransaction();
        dbProject = db.getProjectDAO();
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
        dbProject = null;
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
     * Test add.
     *
     * @throws DatabaseException the database exception
     */
    @Test
    public void testAdd() throws DatabaseException {
        Project one = new Project(new ProjectInfo("one"), 10, 10, 10);
        Project two = new Project(new ProjectInfo("two"), 20, 20, 20);
        
        dbProject.add(one);
        dbProject.add(two);
        
        List<Project> all = dbProject.getAll();
        assertEquals(2, all.size());
        
        boolean foundOne = false;
        boolean foundTwo = false;
        
        for (Project p : all) {
            
            assertFalse(p.getProjInfo().getID() == -1);
            
            if (!foundOne) {
                foundOne = areEqual(p, one, false);
            }
            if (!foundTwo) {
                foundTwo = areEqual(p, two, false);
            }
        }
        assertTrue(foundOne && foundTwo);
    }
    
    /**
     * Test delete.
     *
     * @throws DatabaseException the database exception
     */
    @Test
    public void testDelete() throws DatabaseException {
        Project one = new Project(new ProjectInfo("one"), 10, 10, 10);
        Project two = new Project(new ProjectInfo("two"), 20, 20, 20);
        
        dbProject.add(one);
        dbProject.add(two);
        
        List<Project> all = dbProject.getAll();
        assertEquals(2, all.size());
        
        dbProject.delete(one);
        dbProject.delete(two);
        
        all = dbProject.getAll();
        assertEquals(0, all.size());
    }
    
    /**
     * Test get all.
     *
     * @throws DatabaseException the database exception
     */
    @Test
    public void testGetAll() throws DatabaseException {
        List<Project> all = dbProject.getAll();
        assertEquals(0, all.size());
    }
}
