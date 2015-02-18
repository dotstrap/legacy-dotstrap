package server.database;

import static org.junit.Assert.*;

import java.util.List;
import org.junit.*;

import shared.model.*;

public class DatabaseTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		// Load database driver		
		Database.initialize();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		return;
	}

	@Before
	public void setUp() throws Exception {

		// Delete all contacts from the database
		Database db = new Database();		
		db.startTransaction();
		
		List<Contact> contacts = db.getContactsDAO().getAll();		
		for (Contact c : contacts) {
			db.getContactsDAO().delete(c);
		}
		
		db.endTransaction(true);		
	}

	@After
	public void tearDown() throws Exception {
		return;
	}

	@Test
	public void testCommit() throws DatabaseException {
		
		// Add two contacts
		addTwoContacts(true);
		
		// Start new transaction
		Database db = new Database();
		ContactsDAO dbContacts = db.getContactsDAO();
		db.startTransaction();
		
		// Make sure the previous transaction was properly committed
		List<Contact> all = dbContacts.getAll();
		assertEquals(2, all.size());
		
		db.endTransaction(true);
	}

	@Test
	public void testRollback() throws DatabaseException {
		
		// Add two contacts
		addTwoContacts(false);
		
		// Start new transaction
		Database db = new Database();
		ContactsDAO dbContacts = db.getContactsDAO();
		db.startTransaction();
		
		// Make sure the previous transaction was properly rolled back
		List<Contact> all = dbContacts.getAll();
		assertEquals(0, all.size());
		
		db.endTransaction(true);
	}
	
	private void addTwoContacts(boolean commit) throws DatabaseException {
		
		// Start transaction	
		Database db = new Database();
		ContactsDAO dbContacts = db.getContactsDAO();
		db.startTransaction();
		
		List<Contact> all = dbContacts.getAll();
		assertEquals(0, all.size());
		
		// Add two contacts
		Contact bob = new Contact(-1, "Bob White", "801-999-9999", "1234 State Street", 
									"bob@white.org", "http://www.white.org/bob");
		Contact amy = new Contact(-1, "Amy White", "777-777-7777", "77 Industrial Boulevard",
									"amy@white.org", "http://www.white.org/amy");
		
		dbContacts.add(bob);
		dbContacts.add(amy);
		
		all = dbContacts.getAll();
		assertEquals(2, all.size());

		// End transaction	
		db.endTransaction(commit);	
	}

}
