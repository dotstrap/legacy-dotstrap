package client.communication;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.*;

import shared.model.*;
import shared.communication.*;
import client.ClientException;

public class ClientCommunicatorTest {

	@Test
	public void test() throws ClientException {
		
		ClientCommunicator communicator = new ClientCommunicator();
		List<Contact> contacts = null;
		DeleteContact_Params deleteParams = new DeleteContact_Params();
		AddContact_Params addParams = new AddContact_Params();
		UpdateContact_Params updateParams = new UpdateContact_Params();
		
		// Delete all existing contacts
		contacts = communicator.getAllContacts().getContacts();
		for (Contact c : contacts) {
			deleteParams.setContact(c);
			communicator.deleteContact(deleteParams);
		}

		contacts = communicator.getAllContacts().getContacts();
		assertEquals(0, contacts.size());
		
		Contact bob = new Contact(-1, "Bob White", "801-999-9999", "1234 State Street", 
									"bob@white.org", "http://www.white.org/bob");
		addParams.setContact(bob);
		communicator.addContact(addParams);

		contacts = communicator.getAllContacts().getContacts();
		assertEquals(1, contacts.size());
		
		Contact newBob = contacts.get(0);
		compareContacts(bob, newBob, false);
		
		newBob.setName("Robert White");
		newBob.setPhone("801-000-0000");
		newBob.setAddress("1234 Riverside Drive");
		newBob.setEmail("robert@white.org");
		newBob.setUrl("http://www.white.org/robert");
		
		updateParams.setContact(newBob);
		communicator.updateContact(updateParams);
		
		contacts = communicator.getAllContacts().getContacts();
		assertEquals(1, contacts.size());
		
		Contact newerBob = contacts.get(0);
		compareContacts(newBob, newerBob, true);
		
		deleteParams.setContact(newerBob);
		communicator.deleteContact(deleteParams);
		
		contacts = communicator.getAllContacts().getContacts();
		assertEquals(0, contacts.size());
	}
	
	private void compareContacts(Contact expected, Contact actual, boolean compareIDs) {

		if (compareIDs) {
			assertEquals(expected.getId(), actual.getId());
		}
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getPhone(), actual.getPhone());
		assertEquals(expected.getAddress(), actual.getAddress());
		assertEquals(expected.getEmail(), actual.getEmail());
		assertEquals(expected.getUrl(), actual.getUrl());
	}

}
