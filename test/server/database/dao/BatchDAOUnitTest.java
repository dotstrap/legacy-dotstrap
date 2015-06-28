
package server.database.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.*;

import server.database.Database;
import server.database.DatabaseException;

import shared.model.Batch;


public class BatchDAOUnitTest {
  
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    // Load database drivers
    Database.initDriver();
  }

  
  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    // "tearDownAfterClass");
    return;
  }

  private Database db;
  private BatchDAO testBatchDAO;
  Batch batchTest1;
  Batch batchTest2;
  Batch batchTest3;

  
  @Before
  public void setUp() throws Exception {
    db = new Database();
    db.startTransaction();
    testBatchDAO = db.getBatchDAO();
    testBatchDAO.initTable();

    batchTest1 = new Batch("batchTest1", 10, 10);
    batchTest2 = new Batch("batchTest2", 1, 1);
    batchTest3 = new Batch("batchTest3", 15, 15);

    testBatchDAO.create(batchTest1);
    testBatchDAO.create(batchTest2);
    testBatchDAO.create(batchTest3);

    List<Batch> allBatches = testBatchDAO.getAll();
    assertEquals(3, allBatches.size());

  }

  
  @After
  public void tearDown() throws Exception {
    db.endTransaction(false);

    batchTest1 = null;
    batchTest2 = null;
    batchTest3 = null;
    testBatchDAO = null;
    db = null;

  }

  
  private boolean areEqual(Batch a, Batch b, boolean compareIds) {
    if (compareIds) {
      if (a.getBatchId() != b.getBatchId()) {
        return false;
      }
    }
    return (safeEquals(a.getFilePath(), b.getFilePath())
        && safeEquals(a.getProjectId(), b.getProjectId()) && safeEquals(a.getStatus(),
          b.getStatus()));
  }

  
  private boolean safeEquals(Object a, Object b) {
    if ((a == null) || (b == null)) {
      return ((a == null) && (b == null));
    } else {
      return a.equals(b);
    }
  }

  
  @Test
  public void testGetAll() throws DatabaseException {
    List<Batch> allBatches = testBatchDAO.getAll();
    assertEquals(3, allBatches.size());
  }

  
  @Test
  public void testCreate() throws DatabaseException {
    List<Batch> allBatches = testBatchDAO.getAll();
    assertEquals(3, allBatches.size());

    boolean hasFoundBatch1 = false;
    boolean hasFoundBatch2 = false;
    boolean hasFoundBatch3 = false;
    for (Batch b : allBatches) {
      assertFalse(b.getBatchId() == -1);
      if (!hasFoundBatch1) {
        hasFoundBatch1 = areEqual(b, batchTest1, false);
      }
      if (!hasFoundBatch2) {
        hasFoundBatch2 = areEqual(b, batchTest2, false);
      }
      if (!hasFoundBatch3) {
        hasFoundBatch3 = areEqual(b, batchTest3, false);
      }
    }
    assertTrue(hasFoundBatch1 && hasFoundBatch2 && hasFoundBatch3);
  }

  
  @Test
  public void testUpdate() throws DatabaseException {
    batchTest1.setStatus(Batch.INCOMPLETE);
    batchTest2.setStatus(Batch.ACTIVE);
    batchTest3.setStatus(Batch.COMPLETE);

    testBatchDAO.update(batchTest1);
    testBatchDAO.update(batchTest2);
    testBatchDAO.update(batchTest3);

    List<Batch> allBatches = testBatchDAO.getAll();
    assertEquals(3, allBatches.size());

    boolean hasFoundBatch1 = false;
    boolean hasFoundBatch2 = false;
    boolean hasFoundBatch3 = false;
    for (Batch b : allBatches) {
      assertFalse(b.getBatchId() == -1);
      if (!hasFoundBatch1) {
        hasFoundBatch1 = areEqual(b, batchTest1, false);
      }
      if (!hasFoundBatch2) {
        hasFoundBatch2 = areEqual(b, batchTest2, false);
      }
      if (!hasFoundBatch3) {
        hasFoundBatch3 = areEqual(b, batchTest3, false);
      }
    }
    assertTrue(hasFoundBatch1 && hasFoundBatch2 && hasFoundBatch3);
  }

  
  @Test
  public void testDelete() throws DatabaseException {
    List<Batch> allBatches = testBatchDAO.getAll();
    assertEquals(3, allBatches.size());

    testBatchDAO.delete(batchTest1);
    allBatches = testBatchDAO.getAll();
    assertEquals(2, allBatches.size());

    testBatchDAO.delete(batchTest2);
    allBatches = testBatchDAO.getAll();
    assertEquals(1, allBatches.size());

    testBatchDAO.delete(batchTest3);
    allBatches = testBatchDAO.getAll();
    assertEquals(0, allBatches.size());
  }
}
