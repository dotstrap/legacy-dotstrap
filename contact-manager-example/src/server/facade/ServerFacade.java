package server.facade;

import java.util.*;

import server.database.*;
import shared.model.*;
import server.*;

public class ServerFacade {

	public static void initialize() throws ServerException {		
		try {
			Database.initialize();		
		}
		catch (DatabaseException e) {
			throw new ServerException(e.getMessage(), e);
		}		
	}
	
	public static List<Contact> getAllContacts() throws ServerException {	

		Database db = new Database();
		
		try {
			db.startTransaction();
			List<Contact> contacts = db.getContactsDAO().getAll();
			db.endTransaction(true);
			return contacts;
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	public static void addContact(Contact contact) throws ServerException {

		Database db = new Database();
		
		try {
			db.startTransaction();
			db.getContactsDAO().add(contact);
			db.endTransaction(true);
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	public static void updateContact(Contact contact) throws ServerException {

		Database db = new Database();
		
		try {
			db.startTransaction();
			db.getContactsDAO().update(contact);
			db.endTransaction(true);
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	public static void deleteContact(Contact contact) throws ServerException {

		Database db = new Database();
		
		try {
			db.startTransaction();
			db.getContactsDAO().delete(contact);
			db.endTransaction(true);
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}

}
