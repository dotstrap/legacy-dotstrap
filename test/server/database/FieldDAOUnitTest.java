/**
 * FieldDAOUnitTest.java
 * JRE v1.7.0_76
 *
 * Created by William Myers on Mar 10, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package server.database;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import shared.model.Field;

// TODO: Auto-generated Javadoc
/**
 * The Class FieldDAOUnitTest.
 */
public class FieldDAOUnitTest {

    /**
     * Sets the up before class.
     *
     * @throws Exception the exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // Load database drivers
        Database.initDriver();
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

    /** The db field. */
    private FieldDAO dbField;

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
        dbField = null;
    }

    /**
     * Test add.
     *
     * @throws DatabaseException the database exception
     */
    @Test
    public void testAdd() throws DatabaseException {
        Field one = new Field("Title", "help", "known", 1, 1, 1, 1);
        Field two = new Field("Title2", "help2", "known2", 2, 2, 2, 2);

        dbField.add(one);
        dbField.add(two);

        List<Field> all = dbField.getAll();
        assertEquals(2, all.size());
    }

    /**
     * Test delete.
     *
     * @throws DatabaseException the database exception
     */
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

    /**
     * Test get all.
     *
     * @throws DatabaseException the database exception
     */
    @Test
    public void testGetAll() throws DatabaseException {
        List<Field> all = dbField.getAll();
        assertEquals(0, all.size());
    }
}
