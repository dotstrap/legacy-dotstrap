package shared.communication;

import shared.model.*;

public class AddContact_Params {

	private Contact contact;
	
	public AddContact_Params() {
		contact = null;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}
	
	
}
