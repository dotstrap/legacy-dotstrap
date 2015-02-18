package shared.communication;

import shared.model.*;

public class UpdateContact_Params {

	private Contact contact;
	
	public UpdateContact_Params() {
		contact = null;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}
	
	
}
