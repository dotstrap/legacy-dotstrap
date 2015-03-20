package client;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.*;

import server.database.*;
import shared.communication.*;
import shared.model.*;
import client.communication.ClientCommunicator;

public class GetSampleImageUnitTest
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
		
		ArrayList<Batch> batches = db.getBatchDAO().getAll();
		
		for (Batch b : batches) 
		{
			db.getBatchDAO().delete(b);
		}
		
		db.endTransaction(true);

		// Prepare database for test case	
		db = new Database();
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
		dbUser.add(validate);
		Batch batch = new Batch("TempFilePath", 1, 0);
		dbBatch.add(batch);
		db.endTransaction(true);
		//valid user
		GetSampleImageResult result = cCom.getSampleImage(new GetSampleImageParameters("validate", "validate", 1));
		assertEquals(result.getLink(), "TempFilePath");
		//invalid user
		GetSampleImageResult result2 = cCom.getSampleImage(new GetSampleImageParameters("invalid", "validate", 1));
		assertEquals(false, result2.isValidUser());
		//invalid project
		GetSampleImageResult result3 = cCom.getSampleImage(new GetSampleImageParameters("validate", "validate", 100));
		assertEquals(null, result3.getLink());
	}
}