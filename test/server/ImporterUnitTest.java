package server;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.logging.Logger;

import org.junit.*;

import server.database.*;

import shared.model.*;

/**
 *
 */
public class ImporterUnitTest {
    private static Logger logger;
    static {
        logger = Logger.getLogger("server");
    }

    /**
     * Sets up before class.
     *
     * @throws Exception
     *             the exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        logger.entering("server.database.ImporterUnitTest", "setUpBeforeClass");

        // Load database driver
        Database.initDriver();

        logger.exiting("server.database.ImporterUnitTest", "setUpBeforeClass");
    }

    Database db;

    /**
     * Sets the database up.
     *
     * @throws Exception
     *             the exception
     */
    @Before
    public void setUp() throws Exception {
        logger.entering("server.database.ImporterUnitTest", "setUp");

        // Prepare database for test case
        String[] args = { "import/Records.xml" };
        Importer.main(args);

        db = new Database();
        db.startTransaction();

        logger.exiting("server.database.ImporterUnitTest", "setUp");
    }

    /**
     * Tear down.
     *
     * @throws Exception
     *             the exception
     */
    @After
    public void tearDown() throws Exception {
        logger.entering("server.ImporterUnitTest", "tearDown");

        // Roll back this transaction so changes are undone
        db.endTransaction(false);
        db = null;

        logger.exiting("server.ImporterUnitTest", "tearDown");
    }

    @Test
    public void importTest() throws Exception {
        logger.entering("server.ImporterUnitTest", "importTest");

        // Be double sure we start with empty lists...
        List<User> users = null;
        List<Project> projects = null;
        List<Batch> batches = null;
        List<Field> fields = null;
        List<Record> records = null;

        // Load the lists with the all of the data that was imported database
        users = db.getUserDAO().getAll();
        projects = db.getProjectDAO().getAll();
        fields = db.getFieldDAO().getAll();
        batches = db.getBatchDAO().getAll();
        records = db.getRecordDAO().getAll();

        assertEquals(3, users.size());
        assertEquals(3, projects.size());
        assertEquals(13, fields.size());
        assertEquals(60, batches.size());
        assertEquals(560, records.size());

        logger.exiting("server.ImporterUnitTest", "importTest");
    }
}
