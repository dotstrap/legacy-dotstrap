/**
 * ValidateUserUnitTest.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 22, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.*;

import client.communication.ClientCommunicator;

import server.database.Database;
import server.database.dao.ProjectDAO;
import server.database.dao.UserDAO;

import shared.SharedUnitTests;
import shared.model.User;

public class ValidateUserUnitTest {

    /** The logger used throughout the project. */
    private static Logger logger;
    static {
        logger = Logger.getLogger(ClientUnitTests.LOG_NAME);
    }

 // @formatter:off
    static private ClientCommunicator clientComm;
    static private UserDAO    testUserDAO;
    static private ProjectDAO testProjectDAO;

    static private Database db;

    static private User    testUser1    = null;
    static private User    testUser2    = null;
    static private User    testUser3    = null;
// @formatter:on

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        logger.entering("shared.communication.ValidateUserUnitTest", "setUpBeforeClass");

        // Load database driver
        Database.initDriver();

        // create these once per class for speed
        db = new Database();
        db.startTransaction();

        db = new Database();
        db.startTransaction();

        // Prepare database for test case
        testUserDAO = db.getUserDAO();
        clientComm = new ClientCommunicator();

        testUserDAO.initTable();

        testUser1 = new User("userTest1", "pass1", "first1", "last1", "email1", 1, 1);
        testUser2 = new User("userTest2", "pass2", "first2", "last2", "email2", 2, 2);
        testUser3 = new User("userTest3", "pass3", "first3", "last3", "email3", 3, 3);

        testUserDAO.create(testUser1);
        testUserDAO.create(testUser2);
        testUserDAO.create(testUser3);

        List<User> allUseres = testUserDAO.getAll();
        assertEquals(3, allUseres.size());

        logger.exiting("shared.communication.ValidateUserUnitTest", "tearDownAfterClass");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        logger.entering("shared.communication.ValidateUserUnitTest", "tearDownAfterClass");
        // Roll back this transaction so changes are undone
        db.endTransaction(false);
        db = null;
        testProjectDAO = null;

        testUser1 = null;
        testUser2 = null;
        testUser3 = null;

        logger.exiting("shared.communication.ValidateUserUnitTest", "tearDownAfterClass");
        return;;
    }

    @Before
    public void setUp() throws Exception {
        // quick check to ensure size hasnt changed for some reason
        List<User> allUseres = testUserDAO.getAll();
        assertEquals(3, allUseres.size());
    }

    @After
    public void tearDown() throws Exception {
        // quick check to ensure size hasnt changed for some reason
        List<User> allUseres = testUserDAO.getAll();
        assertEquals(3, allUseres.size());
    }

    @Test void invalidPasswordTest() {
        boolean isValidPassword = false;
        try {
            clientComm.validateUser(new ValidateUserRequest("userTest2", "INVALID"));
            isValidPassword = true;
        } catch (Exception e) {
            isValidPassword = false;
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
        }
        assertEquals(false, isValidPassword);
    }

    @Test void misMatchedPasswordTest() {
        boolean isValidPassword = false;
        try {
            clientComm.validateUser(new ValidateUserRequest("userTest2", "pass3"));
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
            clientComm.validateUser(new ValidateUserRequest("pass3", "userTest3"));
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
            clientComm.validateUser(new ValidateUserRequest("userTest2", "userTest2"));
            isValidCreds = true;
        } catch (Exception e) {
            isValidCreds = false;
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
        }
        assertEquals(false, isValidCreds);
    }
}
