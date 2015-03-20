package client;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.*;

import server.database.*;
import shared.communication.*;
import shared.model.*;
import client.communication.ClientCommunicator;

public class DownloadFileUnitTest
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
		cCom = new ClientCommunicator();
	}

	@After
	public void tearDown() throws Exception 
	{
		db = null;
	}
	
	@Test
	public void testDownloadFile() throws DatabaseException 
	{
		try 
		{
			DownloadFileResult result = cCom.downloadFile(new DownloadFileParameters("Records/images/1890_image0.png"));
			boolean gotFile = false;
			if(result.getFileBytes().length > 0)
			{
				gotFile = true;
			}
			assertTrue(gotFile);
		} 
		catch (ClientException e) 
		{
			e.printStackTrace();
		}
	}
}