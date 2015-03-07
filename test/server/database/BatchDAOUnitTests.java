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

import shared.model.Batch;

public class BatchDAOUnitTests {
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
    private BatchDAO dbBatch;

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

    @After
    public void tearDown() throws Exception {

        // Roll back transaction so changes to database are undone
        db.endTransaction(true);

        db = null;
        dbBatch = null;
    }

    @Test
    public void testGetAll() throws DatabaseException {

        List<Batch> all = dbBatch.getAll();
        assertEquals(0, all.size());
    }

    @Test
    public void testAdd() throws DatabaseException {

        Batch one = new Batch("temporary.file.path", 10, 10);
        Batch two = new Batch("temporary.file.path.two", 10, 10);

        dbBatch.add(one);
        dbBatch.add(two);

        List<Batch> all = dbBatch.getAll();
        assertEquals(2, all.size());

        boolean foundOne = false;
        boolean foundTwo = false;

        for (Batch b : all) {

            assertFalse(b.getID() == -1);

            if (!foundOne) {
                foundOne = areEqual(b, one, false);
            }
            if (!foundTwo) {
                foundTwo = areEqual(b, two, false);
            }
        }

        assertTrue(foundOne && foundTwo);
    }

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

    @Test
    public void testDelete() throws DatabaseException {

        Batch one = new Batch("temporary", 10, 10);
        Batch two = new Batch("temporaryAgain", 5, 5);
        Batch three = new Batch("temporaryAgainAgain", 1, 0);

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

    private boolean safeEquals(Object a, Object b) {
        if (a == null || b == null) {
            return (a == null && b == null);
        } else {
            return a.equals(b);
        }
    }
}
