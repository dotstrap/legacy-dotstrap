package server.database;

import java.util.*;
import static org.junit.Assert.*;

import org.junit.*;
import shared.model.*;


public class ContactsDAOTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		// Load database driver	
		Database.initialize();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		return;
	}
		
	private Database db;
	private ContactsDAO dbContacts;

	@Before
	public void setUp() throws Exception {

		// Delete all contacts from the database	
		db = new Database();		
		db.startTransaction();
		
		List<Contact> contacts = db.getContactsDAO().getAll();
		
		for (Contact c : contacts) {
			db.getContactsDAO().delete(c);
		}
		
		db.endTransaction(true);

		// Prepare database for test case	
		db = new Database();
		db.startTransaction();
		dbContacts = db.getContactsDAO();
	}

	@After
	public void tearDown() throws Exception {
		
		// Roll back transaction so changes to database are undone
		db.endTransaction(false);
		
		db = null;
		dbContacts = null;
	}
	
	@Test
	public void testGetAll() throws DatabaseException {
		
		List<Contact> all = dbContacts.getAll();
		assertEquals(0, all.size());
	}

	@Test
	public void testAdd() throws DatabaseException {
		
		Contact bob = new Contact(-1, "Bob White", "801-999-9999", "1234 State Street", 
									"bob@white.org", "http://www.white.org/bob");
		Contact amy = new Contact(-1, "Amy White", "777-777-7777", "77 Industrial Boulevard",
									"amy@white.org", "http://www.white.org/amy");
		
		dbContacts.add(bob);
		dbContacts.add(amy);
		
		List<Contact> all = dbContacts.getAll();
		assertEquals(2, all.size());
		
		boolean foundBob = false;
		boolean foundAmy = false;
		
		for (Contact c : all) {
			
			assertFalse(c.getId() == -1);
			
			if (!foundBob) {
				foundBob = areEqual(c, bob, false);
			}		
			if (!foundAmy) {
				foundAmy = areEqual(c, amy, false);
			}
		}
		
		assertTrue(foundBob && foundAmy);
	}

	@Test
	public void testUpdate() throws DatabaseException {
		
		Contact bob = new Contact(-1, "Bob White", "801-999-9999", "1234 State Street", 
									"bob@white.org", "http://www.white.org/bob");
		Contact amy = new Contact(-1, "Amy White", "777-777-7777", "77 Industrial Boulevard",
									"amy@white.org", "http://www.white.org/amy");
		
		dbContacts.add(bob);
		dbContacts.add(amy);
		
		bob.setName("Robert White");
		bob.setPhone("000-000-0000");
		bob.setAddress("12 N 13 W");
		bob.setEmail("robert@white.org");
		bob.setUrl("http://www.white.org/robert");
		
		amy.setName("Amy Wilson White");
		amy.setPhone("111-111-1111");
		amy.setAddress("P.O. Box 9876");
		amy.setEmail("amy.white@white.org");
		amy.setUrl("http://www.white.org/amy.white");
		
		dbContacts.update(bob);
		dbContacts.update(amy);
		
		List<Contact> all = dbContacts.getAll();
		assertEquals(2, all.size());
		
		boolean foundBob = false;
		boolean foundAmy = false;
		
		for (Contact c : all) {
			
			if (!foundBob) {
				foundBob = areEqual(c, bob, false);
			}		
			if (!foundAmy) {
				foundAmy = areEqual(c, amy, false);
			}
		}
		
		assertTrue(foundBob && foundAmy);
	}

	@Test
	public void testDelete() throws DatabaseException {
		
		Contact bob = new Contact(-1, "Bob White", "801-999-9999", "1234 State Street", 
									"bob@white.org", "http://www.white.org/bob");
		Contact amy = new Contact(-1, "Amy White", "777-777-7777", "77 Industrial Boulevard",
									"amy@white.org", "http://www.white.org/amy");
		
		dbContacts.add(bob);
		dbContacts.add(amy);
		
		List<Contact> all = dbContacts.getAll();
		assertEquals(2, all.size());
		
		dbContacts.delete(bob);
		dbContacts.delete(amy);
		
		all = dbContacts.getAll();
		assertEquals(0, all.size());
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidAdd() throws DatabaseException {
		
		Contact invalidContact = new Contact(-1, null, null, null, null, null);
		dbContacts.add(invalidContact);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidUpdate() throws DatabaseException {
		
		Contact invalidContact = new Contact(-1, null, null, null, null, null);
		dbContacts.update(invalidContact);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidDelete() throws DatabaseException {
		
		Contact invalidContact = new Contact(-1, null, null, null, null, null);
		dbContacts.delete(invalidContact);
	}
	
	private boolean areEqual(Contact a, Contact b, boolean compareIDs) {
		if (compareIDs) {
			if (a.getId() != b.getId()) {
				return false;
			}
		}	
		return (safeEquals(a.getName(), b.getName()) &&
				safeEquals(a.getPhone(), b.getPhone()) &&
				safeEquals(a.getAddress(), b.getAddress()) &&
				safeEquals(a.getEmail(), b.getEmail()) &&
				safeEquals(a.getUrl(), b.getUrl()));
	}
	
	private boolean safeEquals(Object a, Object b) {
		if (a == null || b == null) {
			return (a == null && b == null);
		}
		else {
			return a.equals(b);
		}
	}

}
