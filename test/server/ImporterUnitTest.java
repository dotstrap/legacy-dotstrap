/**
 * ImporterUnitTest.java
 * JRE v1.8.0_40
 * 
 * Created by William Myers on Mar 24, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package server;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.*;

import server.database.Database;
import server.importer.Importer;

import shared.model.*;

/**
 * The Class ImporterUnitTest.
 */
public class ImporterUnitTest {
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

  /** The database. */
  Database db;

  /**
   * Sets the database up.
   *
   * @throws Exception the exception
   */
  @Before
  public void setUp() throws Exception {
    // Prepare database for test case(s)
    String[] args = {"Records/Records.xml"};
    Importer.main(args);

    db = new Database();
    db.startTransaction();
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
  }

  /**
   * Import test.
   *
   * @throws Exception the exception
   */
  @Test
  public void importTest() throws Exception {
    // Extra precaution that lists are empty to start with...
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
  }
}
