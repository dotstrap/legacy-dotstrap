package shared.communication;

import java.util.*;

import shared.model.*;

public class GetAllContacts_Result {

	private List<Contact> contacts;
	
	public GetAllContacts_Result() {
		contacts = null;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}
	
}
