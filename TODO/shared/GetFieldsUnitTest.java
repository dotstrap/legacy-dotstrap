package client;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.database.Database;
import server.database.DatabaseException;
import server.database.FieldDAO;
import server.database.UserDAO;
import shared.communication.GetFieldsParameters;
import shared.communication.GetFieldsResult;
import shared.model.Credentials;
import shared.model.Field;
import shared.model.User;
import shared.model.UserInfo;
import client.communication.ClientCommunicator;

public class GetFieldsUnitTest 
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
	private FieldDAO dbField;
	private ClientCommunicator cCom;

	@Before
	public void setUp() throws Exception 
	{	
		db = new Database();		
		db.startTransaction();
		
		ArrayList<Field> fields = db.getFieldDAO().getAll();
		
		for (Field f : fields) 
		{
			db.getFieldDAO().delete(f);
		}
		
		db.endTransaction(true);

		// Prepare database for test case	
		db = new Database();
		dbUser = db.getUserDAO();
		dbField = db.getFieldDAO();
		cCom = new ClientCommunicator();
	}

	@After
	public void tearDown() throws Exception 
	{
		db = null;
		dbUser = null;
	}
	
	@Test
	public void testFields() throws DatabaseException 
	{
		db.startTransaction();
		User validate = new User(new Credentials("validate", "validate"), new UserInfo("Vali", "Date", "validate@validate.com"));
		dbUser.add(validate);
		Field field = new Field("Title", "help", "known", 1, 1, 1, 1);
		dbField.add(field);
		db.endTransaction(true);
		//valid test
		GetFieldsResult result = cCom.getFields(new GetFieldsParameters("validate", "validate", 1));
		assertEquals(true, result.isValidUser());
		//invalid user
		GetFieldsResult result2 = cCom.getFields(new GetFieldsParameters("invalid", "validate", 1));
		assertEquals(false, result2.isValidUser());
		//invalid projectID
		GetFieldsResult result3 = cCom.getFields(new GetFieldsParameters("validate", "validate", 100));
		assertEquals(0, result3.getFields().size());
	}
}
