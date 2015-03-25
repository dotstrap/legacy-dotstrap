/**
 * UserDAOUnitTest.java
 * JRE v1.8.0_40
 * 
 * Created by William Myers on Mar 24, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package server.database.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.*;

import server.database.Database;
import server.database.DatabaseException;

import shared.model.User;

// TODO: Auto-generated Javadoc
/**
 * The Class UserDAOUnitTest.
 */
public class UserDAOUnitTest {
  /**
   * Sets up before class.
   *
   * @throws Exception the exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {

    // Load database driver
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

  private Database db;
  private UserDAO  testUserDAO;

  /** The user test1. */
  User             userTest1;

  /** The user test2. */
  User             userTest2;

  /** The user test3. */
  User             userTest3;

  /**
   * Sets the database up.
   *
   * @throws Exception the exception
   */
  @Before
  public void setUp() throws Exception {

    // Prepare database for test case
    db = new Database();
    db.startTransaction();
    testUserDAO = db.getUserDAO();
    testUserDAO.initTable();

    userTest1 = new User("Update1", "pass1", "first1", "last1", "email1", 1, 1);
    userTest2 = new User("Update2", "pass2", "first2", "last2", "email2", 2, 2);
    userTest3 = new User("Update3", "pass3", "first3", "last3", "email3", 3, 3);

    testUserDAO.create(userTest1);
    testUserDAO.create(userTest2);
    testUserDAO.create(userTest3);

    List<User> all = testUserDAO.getAll();
    assertEquals(3, all.size());
  }

  /**
   * Tear down.
   *
   * @throws Exception the exception
   */
  @After
  public void tearDown() throws Exception {

    // Roll back this transaction so changes are undone
    db.endTransaction(false);
    db = null;
    testUserDAO = null;
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

  private boolean areEqual(User a, User b, boolean shouldCompareIds) {
    if (shouldCompareIds) {
      if (a.getUserId() != b.getUserId()) {
        return false;
      }
    }
    return (safeEquals(a.getFirst(), b.getFirst()) && safeEquals(a.getLast(), b.getLast())
        && safeEquals(a.getEmail(), b.getEmail())
        && safeEquals(a.getRecordCount(), b.getRecordCount()) && safeEquals(a.getCurrBatch(),
          b.getCurrBatch()));
  }

  /**
   * Test create.
   *
   * @throws DatabaseException the database exception
   */
  @Test
  public void testCreate() throws DatabaseException {

    List<User> all = testUserDAO.getAll();
    assertEquals(3, all.size());

    boolean hasFoundUser1 = false;
    boolean hasFoundUser2 = false;
    boolean hasFoundUser3 = false;
    for (User curr : all) {
      assertFalse(curr.getUserId() == -1);
      if (!hasFoundUser1) {
        hasFoundUser1 = areEqual(curr, userTest1, false);
      }
      if (!hasFoundUser2) {
        hasFoundUser2 = areEqual(curr, userTest2, false);
      }
      if (!hasFoundUser3) {
        hasFoundUser3 = areEqual(curr, userTest3, false);
      }
    }
    assertTrue(hasFoundUser1 && hasFoundUser2 && hasFoundUser3);
  }

  /**
   * Test update.
   *
   * @throws DatabaseException the database exception
   */
  @Test
  public void testUpdate() throws DatabaseException {
    userTest1.setFirst("first-001");
    userTest2.setFirst("first-002");
    userTest3.setFirst("first-003");

    userTest1.setLast("last-001");
    userTest2.setLast("last-002");
    userTest3.setLast("last-003");

    userTest1.setEmail("email-001");
    userTest2.setEmail("email-002");
    userTest3.setEmail("email-003");

    testUserDAO.update(userTest1);
    testUserDAO.update(userTest2);
    testUserDAO.update(userTest3);

    List<User> all = testUserDAO.getAll();
    assertEquals(3, all.size());

    boolean hasFoundUser1 = false;
    boolean hasFoundUser2 = false;
    boolean hasFoundUser3 = false;
    for (User curr : all) {
      assertFalse(curr.getUserId() == -1);
      if (!hasFoundUser1) {
        hasFoundUser1 = areEqual(curr, userTest1, false);
      }
      if (!hasFoundUser2) {
        hasFoundUser2 = areEqual(curr, userTest2, false);
      }
      if (!hasFoundUser3) {
        hasFoundUser3 = areEqual(curr, userTest3, false);
      }
    }
    assertTrue(hasFoundUser1 && hasFoundUser2 && hasFoundUser3);
  }

  /**
   * Test delete.
   *
   * @throws DatabaseException the database exception
   */
  @Test
  public void testDelete() throws DatabaseException {
    List<User> allUseres = testUserDAO.getAll();
    assertEquals(3, allUseres.size());

    testUserDAO.delete(userTest1);
    allUseres = testUserDAO.getAll();
    assertEquals(2, allUseres.size());

    testUserDAO.delete(userTest3);
    allUseres = testUserDAO.getAll();
    assertEquals(1, allUseres.size());

    testUserDAO.delete(userTest2);
    allUseres = testUserDAO.getAll();
    assertEquals(0, allUseres.size());
  }

}
