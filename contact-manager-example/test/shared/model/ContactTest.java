package shared.model;

import static org.junit.Assert.*;
import org.junit.*;

public class ContactTest {

	private Contact defaultContact;
	private Contact valuesContact;
	
	@Before
	public void setUp() throws Exception {
		
		defaultContact = new Contact();
		valuesContact = new Contact(36, "Bob White", "801-999-9999", "1234 State Street", 
									"bob@white.org", "http://www.white.org/bob");

	}

	@After
	public void tearDown() throws Exception {
		
		defaultContact = null;
		valuesContact = null;
	}

	@Test
	public void testDefaultConstructor() {
		
		assertEquals(-1, defaultContact.getId());
		assertEquals("New Contact", defaultContact.getName());
		assertEquals(null, defaultContact.getPhone());
		assertEquals(null, defaultContact.getAddress());
		assertEquals(null, defaultContact.getEmail());
		assertEquals(null, defaultContact.getUrl());
	}

	@Test
	public void testValuesConstructor() {
		
		assertEquals(36, valuesContact.getId());
		assertEquals("Bob White", valuesContact.getName());
		assertEquals("801-999-9999", valuesContact.getPhone());
		assertEquals("1234 State Street", valuesContact.getAddress());
		assertEquals("bob@white.org", valuesContact.getEmail());
		assertEquals("http://www.white.org/bob", valuesContact.getUrl());
	}
	
	@Test
	public void testSetters() {
		
		assertEquals(-1, defaultContact.getId());
		defaultContact.setId(36);
		assertEquals(36, defaultContact.getId());
		
		assertEquals("New Contact", defaultContact.getName());
		defaultContact.setName("Bob White");
		assertEquals("Bob White", defaultContact.getName());
		
		assertEquals(null, defaultContact.getPhone());
		defaultContact.setPhone("801-999-9999");
		assertEquals("801-999-9999", defaultContact.getPhone());
		
		assertEquals(null, defaultContact.getAddress());
		defaultContact.setAddress("1234 State Street");
		assertEquals("1234 State Street", defaultContact.getAddress());
		
		assertEquals(null, defaultContact.getEmail());
		defaultContact.setEmail("bob@white.org");
		assertEquals("bob@white.org", defaultContact.getEmail());
		
		assertEquals(null, defaultContact.getUrl());
		defaultContact.setUrl("http://www.white.org/bob");
		assertEquals("http://www.white.org/bob", defaultContact.getUrl());		
	}

}
