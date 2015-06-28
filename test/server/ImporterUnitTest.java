
package server;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.*;

import server.database.Database;
import server.importer.Importer;

import shared.model.*;


public class ImporterUnitTest {
  
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    // Load database driver
    Database.initDriver();
  }

  
  Database db;

  
  @Before
  public void setUp() throws Exception {
    // Prepare database for test case(s)
    String[] args = {"Records/Records.xml"};
    Importer.main(args);

    db = new Database();
    db.startTransaction();
  }

  
  @After
  public void tearDown() throws Exception {
    // Roll back this transaction so changes are undone
    db.endTransaction(false);
    db = null;
  }

  
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
