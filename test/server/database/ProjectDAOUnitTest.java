/**
 * ProjectDAOUnitTest.java
 * JRE v1.8.0_40
 * 
 * Created by William Myers on Mar 22, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package server.database;

import static org.junit.Assert.*;

import java.util.List;
import java.util.logging.Logger;

import org.junit.*;

import server.database.dao.ProjectDAO;

import shared.model.Project;

// TODO: Auto-generated Javadoc
/**
 * The Class ProjectDAOUnitTest.
 */
public class ProjectDAOUnitTest {
    /** The logger used throughout the project. */
    private static Logger logger;
    static {
        logger = Logger.getLogger("serverTest");
    }

    /**
     * Sets the up before class.
     *
     * @throws Exception
     *             the exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        logger.entering("server.database.ProjectDAOUnitTest", "setUpBeforeClass");

        // Load database drivers
        Database.initDriver();

        logger.exiting("server.database.ProjectDAOUnitTest", "setUpBeforeClass");
    }

    /**
     * Tear down after class.
     *
     * @throws Exception
     *             the exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        logger.entering("server.database.ProjectDAOUnitTest",
                "tearDownAfterClass");
        logger.exiting("server.database.ProjectDAOUnitTest", "tearDownAfterClass");
        return;
    }

    private Database   db;
    private ProjectDAO testProjectDAO;

    /**
     * Sets the database up.
     *
     * @throws Exception
     *             the exception
     */
    @Before
    public void setUp() throws Exception {
        logger.entering("server.database.ProjectDAOUnitTest", "setUp");

        // Prepare database for test case
        db = new Database();
        db.startTransaction();
        testProjectDAO = db.getProjectDAO();
        testProjectDAO.initTable();

        logger.exiting("server.database.ProjectDAOUnitTest", "setUp");
    }

    /**
     * Tear down.
     *
     * @throws Exception
     *             the exception
     */
    @After
    public void tearDown() throws Exception {
        logger.entering("server.database.ProjectDAOUnitTest", "tearDown");

        // Roll back this transaction so changes are undone
        db.endTransaction(false);
        db = null;
        testProjectDAO = null;

        logger.exiting("server.database.ProjectDAOUnitTest", "tearDown");
    }

    /**
     * Are equal.
     *
     * @param a
     *            the first project to compare
     * @param b
     *            the second project to compare
     * @param compareIDs
     *            compare by IDs?
     * @return true, if successful
     */
    private boolean areEqual(Project a, Project b, boolean compareIDs) {
        if (compareIDs) {
            if (a.getProjectID() != b.getProjectID()) {
                return false;
            }
        }
        return (safeEquals(a.getFirstYCoord(), b.getFirstYCoord())
                && safeEquals(a.getTitle(), b.getTitle())
                && safeEquals(a.getRecordHeight(), b.getRecordHeight()) && safeEquals(
                    a.getRecordsPerBatch(), b.getRecordsPerBatch()));
    }

    /**
     * Safe equals.
     *
     * @param a
     *            the first project to compare
     * @param b
     *            the second project to compare
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
     * Test get allProjects.
     *
     * @throws DatabaseException
     *             the database exception
     */
    @Test
    public void testGetAll() throws DatabaseException {
        logger.entering("server.database.ProjectDAOUnitTest", "testGetAll");

        List<Project> allProjectes = testProjectDAO.getAll();
        assertEquals(0, allProjectes.size());

        logger.exiting("server.database.ProjectDAOUnitTest", "testGetAll");
    }

    /**
     * Test create.
     *
     * @throws DatabaseException
     *             the database exception
     */
    @Test
    public void testCreate() throws DatabaseException {
        logger.entering("server.database.ProjectDAOUnitTest", "testCreate");

        Project testProject1 = new Project("projectTestCreate1", 10, 11, 12);
        Project testProject2 = new Project("projectTestCreate2", 20, 21, 22);
        Project testProject3 = new Project("projectTestCreate3", 30, 31, 32);

        testProjectDAO.create(testProject1);
        testProjectDAO.create(testProject2);
        testProjectDAO.create(testProject3);

        List<Project> allProjectes = testProjectDAO.getAll();
        assertEquals(3, allProjectes.size());

        boolean hasFoundProject1 = false;
        boolean hasFoundProject2 = false;
        boolean hasFoundProject3 = false;
        for (Project b : allProjectes) {
            assertFalse(b.getProjectID() == -1);
            if (!hasFoundProject1) {
                hasFoundProject1 = areEqual(b, testProject1, false);
            }
            if (!hasFoundProject2) {
                hasFoundProject2 = areEqual(b, testProject2, false);
            }
            if (!hasFoundProject3) {
                hasFoundProject3 = areEqual(b, testProject3, false);
            }
        }
        assertTrue(hasFoundProject1 && hasFoundProject2 && hasFoundProject3);

        logger.exiting("server.database.ProjectDAOUnitTest", "testCreate");
    }

    /**
     * Test update.
     *
     * @throws DatabaseException
     *             the database exception
     */
    // @Test
    // public void testUpdate() throws DatabaseException {
    // logger.entering("server.database.ProjectDAOUnitTest", "testUpdate");

    // Project testProject1 = new Project("projectUdateTest1", 10, 10);
    // Project testProject2 = new Project("projectUdateTest2", 1, 1);
    // Project testProject3 = new Project("projectUdateTest3", 15, 15);

    // dbProjectTest.create(testProject1);
    // dbProjectTest.create(testProject2);
    // dbProjectTest.create(testProject3);

    // testProject1.setStatus(0);
    // testProject2.setStatus(1);
    // testProject3.setStatus(2);

    // dbProjectTest.update(testProject1);
    // dbProjectTest.update(testProject2);
    // dbProjectTest.update(testProject3);

    // List<Project> allProjectes = dbProjectTest.getAll();
    // assertEquals(3, allProjectes.size());

    // boolean hasFoundProject1 = false;
    // boolean hasFoundProject2 = false;
    // boolean hasFoundProject3 = false;
    // for (Project b : allProjectes) {
    // assertFalse(b.getprojectID() == -1);
    // if (!hasFoundProject1) {
    // hasFoundProject1 = areEqual(b, testProject1 ,false);
    // }
    // if (!hasFoundProject2) {
    // hasFoundProject2 = areEqual(b, testProject2 ,false);
    // }
    // if (!hasFoundProject3) {
    // hasFoundProject3 = areEqual(b, testProject3 ,false);
    // }
    // }
    // assertTrue(hasFoundProject1 && hasFoundProject2 && hasFoundProject3);

    // logger.exiting("server.database.ProjectDAOUnitTest", "testUpdate");
    // }

    /**
     * Test delete.
     *
     * @throws DatabaseException
     *             the database exception
     */
    @Test
    public void testDelete() throws DatabaseException {
        logger.entering("server.database.ProjectDAOUnitTest", "testDelete");

        Project testProject1 = new Project("projectTestCreate1", 10, 11, 12);
        Project testProject2 = new Project("projectTestCreate2", 20, 21, 22);
        Project testProject3 = new Project("projectTestCreate3", 30, 31, 32);

        testProjectDAO.create(testProject1);
        testProjectDAO.create(testProject2);
        testProjectDAO.create(testProject3);

        List<Project> allProjectes = testProjectDAO.getAll();
        assertEquals(3, allProjectes.size());

        testProjectDAO.delete(testProject1);
        allProjectes = testProjectDAO.getAll();
        assertEquals(2, allProjectes.size());

        testProjectDAO.delete(testProject2);
        allProjectes = testProjectDAO.getAll();
        assertEquals(1, allProjectes.size());

        testProjectDAO.delete(testProject3);
        allProjectes = testProjectDAO.getAll();
        assertEquals(0, allProjectes.size());

        logger.exiting("server.database.ProjectDAOUnitTest", "testDelete");
    }
}
