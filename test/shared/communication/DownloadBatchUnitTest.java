package shared.communication;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.logging.Logger;

import org.junit.*;

import client.ClientException;
import client.communication.ClientCommunicator;

import server.database.*;

import shared.communication.DownloadBatchParameters;
import shared.communication.DownloadBatchResult;
import shared.model.Batch;
import shared.model.User;

public class DownloadBatchUnitTest {
    /** The logger used throughout the project. */
    private static Logger logger;
    static {
        logger = Logger.getLogger("clientTest");
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // Load database driver
        Database.initDriver();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        return;
    }

    private Database           db;
    private ClientCommunicator clientComm;
    private UserDAO            testUserDAO;
    private BatchDAO           testBatchDAO;

    //@formatter:off
    @Before
    public void setUp() throws Exception {
        db = new Database();
        db.startTransaction();

        // Prepare database for test case
        testUserDAO  = db.getUserDAO();
        testBatchDAO = db.getBatchDAO();
        clientComm   = new ClientCommunicator();

        testUserDAO.initTable();
        testBatchDAO.initTable();
    }
    //@formatter:on

    @After
    public void tearDown() throws Exception {
        db.endTransaction(false);
        db = null;
        testUserDAO = null;
    }

    @Test
    public void testDownloadBatch() throws DatabaseException {
        User testUser1 = new User("userTest1", "pass1", "first1", "last1", "email1", 1, 1);
        User testUser2 = new User("userTest2", "pass2", "first2", "last2", "email2", 2, 2);
        User testUser3 = new User("userTest3", "pass3", "first3", "last3", "email3", 3, 3);

        testUserDAO.create(testUser1);
        testUserDAO.create(testUser2);
        testUserDAO.create(testUser3);

        List<User> allUseres = testUserDAO.getAll();
        assertEquals(3, allUseres.size());

        Batch testBatch1 = new Batch("batchTest1", 10, 10);
        Batch testBatch2 = new Batch("batchTest2", 5, 5);
        Batch testBatch3 = new Batch("batchTest3", 1, 0);

        testBatchDAO.create(testBatch1);
        testBatchDAO.create(testBatch2);
        testBatchDAO.create(testBatch3);

        List<Batch> allBatches = testBatchDAO.getAll();
        assertEquals(3, allBatches.size());

        // invalid user
        boolean isValid = true;
        try {
            clientComm.downloadBatch(new DownloadBatchParameters("userTest1", "INVALID", 1));
        } catch (ClientException e) {
            isValid = false;
        }
        assertEquals(false, isValid);

        // invalid projectID
        DownloadBatchResult result2 = new DownloadBatchResult();
        try {
            result2 =
                    clientComm.downloadBatch(new DownloadBatchParameters("userTest2", "pass2",
                            100));
        } catch (ClientException e) {
            logger.finer("Caught invalid projectId test...");
        }
        assertEquals(null, result2.getFields());
    }
}
