package shared.communication;

import shared.model.*;

public class DeleteContact_Params {

	private Contact contact;
	
	public DeleteContact_Params() {
		contact = null;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}
	
	
}
