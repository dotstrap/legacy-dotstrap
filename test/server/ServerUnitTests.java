package server;

import org.junit.* ;
import static org.junit.Assert.* ;

public class ServerUnitTests 
{
	
	@Before
	public void setup() 
	{
		
	}
	
	@After
	public void teardown() 
	{
		
	}
	
	@Test
	public void test_1() 
	{
		assertEquals("OK", "OK");
		assertTrue(true);
		assertFalse(false);
	}

	public static void main(String[] args) 
	{
		String[] testClasses = new String[] 
		{
				"server.ServerUnitTests",
				"server.database.UserDAOTest",
				"server.database.BatchDAOUnitTests",
				"server.database.FieldDAOUnitTest",
				"server.database.ProjectDAOUnitTest",
				"server.database.RecordDAOUnitTest"
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}
	
}

