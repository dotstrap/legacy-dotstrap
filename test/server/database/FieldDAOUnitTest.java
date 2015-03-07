package server.database;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import shared.model.*;

public class FieldDAOUnitTest {
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // Load database driver
        Database.initialize();
        Database.initializeTables();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        return;
    }

    private Database db;
    private FieldDAO dbField;

    @Before
    public void setUp() throws Exception {
        // Delete all users from the database
        db = new Database();
        db.startTransaction();

        ArrayList<Field> fields = db.getFieldDAO().getAll();

        for (Field f : fields) {
            db.getFieldDAO().delete(f);
        }

        db.endTransaction(true);

        // Prepare database for test case
        db = new Database();
        db.startTransaction();
        dbField = db.getFieldDAO();
    }

    @After
    public void tearDown() throws Exception {

        // Roll back transaction so changes to database are undone
        db.endTransaction(true);

        db = null;
        dbField = null;
    }

    @Test
    public void testGetAll() throws DatabaseException {
        List<Field> all = dbField.getAll();
        assertEquals(0, all.size());
    }

    @Test
    public void testAdd() throws DatabaseException {
        Field one = new Field("Title", "help", "known", 1, 1, 1, 1);
        Field two = new Field("Title2", "help2", "known2", 2, 2, 2, 2);

        dbField.add(one);
        dbField.add(two);

        List<Field> all = dbField.getAll();
        assertEquals(2, all.size());
    }

    @Test
    public void testDelete() throws DatabaseException {

        Field one = new Field("Title", "help", "known", 1, 1, 1, 1);
        Field two = new Field("Title2", "help2", "known2", 2, 2, 2, 2);

        dbField.add(one);
        dbField.add(two);

        List<Field> all = dbField.getAll();
        assertEquals(2, all.size());

        dbField.delete(one);
        dbField.delete(two);

        all = dbField.getAll();
        assertEquals(0, all.size());
    }

    @SuppressWarnings("unused")
    private boolean areEqual(Field a, Field b, boolean compareIDs) {
        if (compareIDs) {
            if (a.getID() != b.getID()) {
                return false;
            }
        }
        return (safeEquals(a.getHelp(), b.getHelp())
                && safeEquals(a.getKnownPath(), b.getKnownPath())
                && safeEquals(a.getNumber(), b.getNumber())
                && safeEquals(a.getProjectID(), b.getProjectID())
                && safeEquals(a.getTitle(), b.getTitle())
                && safeEquals(a.getWidth(), b.getWidth()) && safeEquals(a.getX(),
                    b.getX()));
    }

    private boolean safeEquals(Object a, Object b) {
        if (a == null || b == null) {
            return (a == null && b == null);
        } else {
            return a.equals(b);
        }
    }
}
