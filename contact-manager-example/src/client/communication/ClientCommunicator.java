package client.communication;

import java.io.*;
import java.net.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import shared.communication.*;
import client.*;

public class ClientCommunicator {

	private static final String SERVER_HOST = "localhost";
	private static final int SERVER_PORT = 8080;
	private static final String URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
	private static final String HTTP_GET = "GET";
	private static final String HTTP_POST = "POST";

	private XStream xmlStream;

	public ClientCommunicator() {
		xmlStream = new XStream(new DomDriver());
	}

	public GetAllContacts_Result getAllContacts() throws ClientException {
		return (GetAllContacts_Result)doGet("/GetAllContacts");
	}
	
	public void addContact(AddContact_Params params) throws ClientException {
		doPost("/AddContact", params);
	}
	
	public void updateContact(UpdateContact_Params params) throws ClientException {
		doPost("/UpdateContact", params);
	}
	
	public void deleteContact(DeleteContact_Params params) throws ClientException {
		doPost("/DeleteContact", params);
	}
	
	private Object doGet(String urlPath) throws ClientException {
		try {
			URL url = new URL(URL_PREFIX + urlPath);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod(HTTP_GET);
			connection.connect();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				Object result = xmlStream.fromXML(connection.getInputStream());
				return result;
			}
			else {
				throw new ClientException(String.format("doGet failed: %s (http code %d)",
											urlPath, connection.getResponseCode()));
			}
		}
		catch (IOException e) {
			throw new ClientException(String.format("doGet failed: %s", e.getMessage()), e);
		}
	}
	
	private void doPost(String urlPath, Object postData) throws ClientException {
		try {
			URL url = new URL(URL_PREFIX + urlPath);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod(HTTP_POST);
			connection.setDoOutput(true);
			connection.connect();
			xmlStream.toXML(postData, connection.getOutputStream());
			connection.getOutputStream().close();
			if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new ClientException(String.format("doPost failed: %s (http code %d)",
						urlPath, connection.getResponseCode()));
			}
		}
		catch (IOException e) {
			throw new ClientException(String.format("doPost failed: %s", e.getMessage()), e);
		}
	}

}
