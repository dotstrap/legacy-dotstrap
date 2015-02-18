package main;

public class UnitTestDriver {

	public static void main(String[] args) {

		String[] testClasses = new String[] {
				"shared.model.ContactTest",
				"server.database.DatabaseTest",
				"server.database.ContactsDAOTest",
				"client.communication.ClientCommunicatorTest"
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}

}
