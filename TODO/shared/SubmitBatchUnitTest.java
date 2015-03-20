package client;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.*;

import server.database.*;
import shared.communication.*;
import shared.model.*;
import client.communication.ClientCommunicator;

public class SubmitBatchUnitTest
{
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		// Load database driver	
		Database.initialize();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception 
	{
		return;
	}
		
	private Database db;
	private UserDAO dbUser;
	private BatchDAO dbBatch;
	private ClientCommunicator cCom;

	@SuppressWarnings("static-access")
	@Before
	public void setUp() throws Exception 
	{	
		db = new Database();		
		db.startTransaction();
		
		ArrayList<User> users = db.getUserDAO().getAll();
		for(User u : users)
		{
			db.getUserDAO().delete(u);
		}
		
		ArrayList<Record> records = db.getRecordDAO().getAll();
		
		for (Record r : records) 
		{
			db.getRecordDAO().delete(r);
		}
		
		ArrayList<Project> projects = db.getProjectDAO().getAll();
		
		for (Project p : projects) 
		{
			db.getProjectDAO().delete(p);
		}
		
		ArrayList<Batch> batches = db.getBatchDAO().getAll();
		
		for (Batch b : batches) 
		{
			db.getBatchDAO().delete(b);
		}
		
		db.endTransaction(true);

		// Prepare database for test case	
		db = new Database();
		db.initializeTables();
		dbUser = db.getUserDAO();
		dbBatch = db.getBatchDAO();
		cCom = new ClientCommunicator();
	}

	@After
	public void tearDown() throws Exception 
	{
		db = null;
		dbUser = null;
	}
	
	@Test
	public void testProjects() throws DatabaseException 
	{
		db.startTransaction();
		User validate = new User(new Credentials("validate", "validate"), new UserInfo("Vali", "Date", "validate@validate.com"));
		validate.setCurrentBatch(1);
		dbUser.add(validate);
		Batch batch = new Batch("TempFilePath", 1, 0);
		dbBatch.add(batch);
		Project project = new Project(new ProjectInfo("Project"), 1, 1, 1);
		db.getProjectDAO().add(project);
		db.endTransaction(true);
		//valid user, bad input
		SubmitBatchResult result = cCom.submitBatch(new SubmitBatchParameters("validate", "validate", 1, ";"));
		assertEquals(result.isSuccess(), false);
	}
}