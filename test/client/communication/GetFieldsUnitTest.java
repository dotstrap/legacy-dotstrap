/**
 * GetFieldsUnitTest.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 23, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.communication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.*;

import client.ClientUnitTests;

import server.database.Database;
import server.database.dao.FieldDAO;
import server.database.dao.UserDAO;

import shared.communication.GetFieldsRequest;
import shared.communication.GetFieldsResponse;
import shared.model.Field;
import shared.model.User;

public class GetFieldsUnitTest {

    /** The logger used throughout the project. */
    static private Logger logger;
    static {
        logger = Logger.getLogger(ClientUnitTests.LOG_NAME);
    }

    // @formatter:off
    static private ClientCommunicator clientComm;
    static private UserDAO  testUserDAO;
    static private FieldDAO testFieldDAO;

    static private Database db;
    static private User     testUser1  = null;
    static private User     testUser2  = null;
    static private User     testUser3  = null;

    static private Field    fieldTest1 = null;
    static private Field    fieldTest2 = null;
    static private Field    fieldTest3 = null;
    // @formatter:on

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // Load database driver
        Database.initDriver();

        // create these once per class for speed
        db = new Database();
        db.startTransaction();

        db = new Database();
        db.startTransaction();

        // Prepare database for test case
        testUserDAO = db.getUserDAO();
        testFieldDAO = db.getFieldDAO();
        clientComm = new ClientCommunicator();

        testUserDAO.initTable();
        testFieldDAO.initTable();

        testUser1 = new User("userTest1", "pass1", "first1", "last1", "email1", 1, 1);
        testUser2 = new User("userTest2", "pass2", "first2", "last2", "email2", 2, 2);
        testUser3 = new User("userTest3", "pass3", "first3", "last3", "email3", 3, 3);

        testUserDAO.create(testUser1);
        testUserDAO.create(testUser2);
        testUserDAO.create(testUser3);

        List<User> allUseres = testUserDAO.getAll();
        assertEquals(3, allUseres.size());

        fieldTest1 = new Field(100, 111, "fieldTest1", "KnownData1", "helpURL1", 1, 1, 1);
        fieldTest2 = new Field(200, 222, "fieldTest2", "KnownData2", "helpURL2", 2, 2, 2);
        fieldTest3 = new Field(300, 333, "fieldTest3", "KnownData3", "helpURL3", 3, 3, 3);

        testFieldDAO.create(fieldTest1);
        testFieldDAO.create(fieldTest2);
        testFieldDAO.create(fieldTest3);

        List<Field> allBatches = testFieldDAO.getAll();
        assertEquals(3, allBatches.size());
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        db = null;

        testUser1 = null;
        testUser2 = null;
        testUser3 = null;

        fieldTest1 = null;
        fieldTest2 = null;
        fieldTest3 = null;

        return;
    }

    @Before
    public void setUp() throws Exception {
        // quick check to ensure size hasnt changed for some reason
        List<User> allUseres = testUserDAO.getAll();
        assertEquals(3, allUseres.size());

        List<Field> allBatches = testFieldDAO.getAll();
        assertEquals(3, allBatches.size());
    }

    @After
    public void tearDown() throws Exception {
        // quick check to ensure size hasnt changed for some reason
        List<User> allUseres = testUserDAO.getAll();
        assertEquals(3, allUseres.size());

        List<Field> allBatches = testFieldDAO.getAll();
        assertEquals(3, allBatches.size());
    }

    @Test
    public void testValidField() {
        GetFieldsResponse result1 = null;
        try {
            result1 = clientComm.getFields(new GetFieldsRequest("userTest1", "pass1", 1));
        } catch (Exception e) {
            fail("ERROR: failed vaild test...");
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
        }
        assertEquals(1, result1.getFields().size());
    }

    @Test
    public void testValidFieldWithNoProject() {
        GetFieldsResponse result2 = null;
        try {
            result2 = clientComm.getFields(new GetFieldsRequest("userTest1", "pass1"));
        } catch (Exception e) {
            fail("ERROR: failed vaild test...");
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
        }
        assertEquals(1, result2.getFields().size());
    }

    @Test
    public void invalidPasswordTest() {
        boolean isValidPassword = false;
        try {
            clientComm.getFields(new GetFieldsRequest("userTest2", "INVALID"));
            isValidPassword = true;
        } catch (Exception e) {
            isValidPassword = false;
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
        }
        assertEquals(false, isValidPassword);
    }

    @Test
    public void misMatchedPasswordTest() {
        boolean isValidPassword = false;
        try {
            clientComm.getFields(new GetFieldsRequest("userTest2", "pass3"));
            isValidPassword = true;
        } catch (Exception e) {
            isValidPassword = false;
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
        }
        assertEquals(false, isValidPassword);
    }

    @Test
    public void invalidUsernameTest() {
        boolean isValidUsername = false;
        try {
            clientComm.getFields(new GetFieldsRequest("pass3", "userTest3"));
            isValidUsername = true;
        } catch (Exception e) {
            isValidUsername = false;
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
        }
        assertEquals(false, isValidUsername);
    }

    @Test
    public void invalidCredsTest() {
        // invalid credentials test
        boolean isValidCreds = false;
        try {
            clientComm.getFields(new GetFieldsRequest("userTest2", "userTest2"));
            isValidCreds = true;
        } catch (Exception e) {
            isValidCreds = false;
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
        }
        assertEquals(false, isValidCreds);
    }

    @Test
    public void invalidUserTest() {
        boolean isValidUser = false;
        try {
            clientComm.getFields(new GetFieldsRequest("INVALID", "pass2", 1));
            isValidUser = true;
        } catch (Exception e) {
            isValidUser = false;
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
        }
        assertEquals(false, isValidUser);
    }

    @Test
    public void invalidProjectIdTest() {
        boolean isValidProject = false;
        try {
            clientComm.getFields(new GetFieldsRequest("validate", "validate", 100));
            isValidProject = true;
        } catch (Exception e) {
            isValidProject = false;
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
        }
        assertEquals(false, isValidProject);
    }
}
