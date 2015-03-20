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
import server.database.ProjectDAO;
import server.database.UserDAO;
import shared.communication.GetProjectsParameters;
import shared.communication.GetProjectsResult;
import shared.model.Credentials;
import shared.model.Project;
import shared.model.ProjectInfo;
import shared.model.User;
import shared.model.UserInfo;
import client.communication.ClientCommunicator;

public class GetProjectsUnitTest
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
	private ProjectDAO dbProject;
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
		
		ArrayList<Project> projects = db.getProjectDAO().getAll();
		
		for (Project p : projects) 
		{
			db.getProjectDAO().delete(p);
		}
		
		db.endTransaction(true);

		// Prepare database for test case	
		db = new Database();
		dbUser = db.getUserDAO();
		dbProject = db.getProjectDAO();
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
		Project project = new Project(new ProjectInfo("Title"), 1, 1, 1);
		dbProject.add(project);
		db.endTransaction(true);
		//valid user
		GetProjectsResult result = cCom.getProjects(new GetProjectsParameters("validate", "validate"));
		assertEquals(result.getProjects().size(), 1);
		//invalid user
		GetProjectsResult result2 = cCom.getProjects(new GetProjectsParameters("invalid", "validate"));
		assertEquals(false, result2.isValidUser());
	}
}
