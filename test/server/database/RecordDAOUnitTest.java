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

import shared.model.Record;

public class RecordDAOUnitTest {
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // Load database driver
        Database.initialize();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        return;
    }

    private Database  db;
    private RecordDAO dbRecord;

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

    @After
    public void tearDown() throws Exception {

        // Roll back transaction so changes to database are undone
        db.endTransaction(true);

        db = null;
        dbRecord = null;
    }

    @Test
    public void testGetAll() throws DatabaseException {
        List<Record> all = dbRecord.getAll();
        assertEquals(0, all.size());
    }

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

    @Test
    public void testSearch() throws DatabaseException {
        Record one = new Record(1, 1, "one", 1);
        Record two = new Record(2, 2, "two", 2);

        dbRecord.add(one);
        dbRecord.add(two);

        ArrayList<Record> search = dbRecord.search(1, "one");
        assertEquals(1, search.size());
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

    private boolean safeEquals(Object a, Object b) {
        if (a == null || b == null) {
            return (a == null && b == null);
        } else {
            return a.equals(b);
        }
    }
}
