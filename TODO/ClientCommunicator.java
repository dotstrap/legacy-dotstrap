package client.communication;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.commons.io.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import client.ClientException;
import shared.communication.*;

public class ClientCommunicator 
{

	private static String SERVER_HOST = "localhost";
	private static int SERVER_PORT = 50080;
	private static String URL_PREFIX = "http://" + SERVER_HOST + ":"
			+ SERVER_PORT;
	private static final String HTTP_GET = "GET";
	private static final String HTTP_POST = "POST";


	public ClientCommunicator() 
	{
		
	}

	@SuppressWarnings("static-access")
	public ClientCommunicator(String port, String host) 
	{
		this.SERVER_PORT = Integer.parseInt(port);
		this.SERVER_HOST = host;
		this.URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
	}

	public GetProjectsResult getProjects(GetProjectsParameters creds) 
	{
		try 
		{
			GetProjectsResult result = (GetProjectsResult) doPost(
					"/GetProjects", creds);
			return result;
		} 
		catch (ClientException e) 
		{
			e.printStackTrace();
		}
		return null;
	}

	public ValidateUserResult validateUser(ValidateUserCredentials creds) 
	{
		try 
		{
			ValidateUserResult result = (ValidateUserResult) doPost(
					"/ValidateUser", creds);
			return result;
		} 
		catch (ClientException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public GetFieldsResult getFields(GetFieldsParameters params)
	{
		try 
		{
			GetFieldsResult result = (GetFieldsResult) doPost(
					"/GetFields", params);
			return result;
		} 
		catch (ClientException e) 
		{
			e.printStackTrace();
		}
		return null;
	}

	public GetSampleImageResult getSampleImage(GetSampleImageParameters params) 
	{
		try 
		{
			GetSampleImageResult result = (GetSampleImageResult) doPost(
					"/GetSampleImage", params);
			URL url = new URL(URL_PREFIX + "/" + result.getLink());
			result.setURL(url);
			return result;
		} 
		catch (ClientException e) 
		{
			e.printStackTrace();
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public SearchResult search(SearchParameters params)
	{
		try 
		{
			SearchResult result = (SearchResult) doPost(
					"/Search", params);
			
			ArrayList<URL> urls = new ArrayList<URL>();
			for(String s : result.getLinks())
			{
				URL url = new URL(URL_PREFIX + "/" + s);
				urls.add(url);
			}
			result.setUrls(urls);
			return result;
		} 
		catch (ClientException | MalformedURLException e) 
		{
			e.printStackTrace();
		} 
		return null;
	}
	
	public DownloadBatchResult downloadBatch(DownloadBatchParameters params)
	{
		try 
		{
			DownloadBatchResult result = (DownloadBatchResult) doPost(
					"/DownloadBatch", params);
			result.setUrl(URL_PREFIX);
			return result;
		} 
		catch (ClientException e) 
		{
			e.printStackTrace();
		} 
		return null;
	}
	
	public SubmitBatchResult submitBatch(SubmitBatchParameters params)
	{
		try 
		{
			SubmitBatchResult result = (SubmitBatchResult) doPost(
					"/SubmitBatch", params);
			return result;
		} 
		catch (ClientException e) 
		{
			e.printStackTrace();
		} 
		return null;
	}
	
	public DownloadFileResult downloadFile(DownloadFileParameters params) throws ClientException
	{
		return new DownloadFileResult(doGet("http://" + SERVER_HOST + ":" + SERVER_PORT + File.separator + params.getUrl()));
	}

	public Object doPost(String commandName, Object postData) throws ClientException 
	{
		assert commandName != null;
		assert postData != null;

		URL url;
		try 
		{
			url = new URL(URL_PREFIX + commandName);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod(HTTP_POST);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestProperty("Accept", "html/text");
			connection.connect();
			URL_PREFIX = "http://" + SERVER_HOST + ":"
					+ SERVER_PORT;
			XStream x = new XStream(new DomDriver());
			x.toXML(postData, connection.getOutputStream());

			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) 
			{
				Object o = (Object)x.fromXML(connection.getInputStream());
				return o;
			} 
			else 
			{
				throw new ClientException(String.format(
						"doPost failed: %s (http code %d)", commandName,
						connection.getResponseCode()));
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
		return null;
	}
	
	public byte[] doGet(String urlPath) throws ClientException
	{
		byte[] result = null;
		try
		{
			URL url = new URL(urlPath);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod(HTTP_GET);
			connection.setDoOutput(true);
			connection.connect();

			if(connection.getResponseCode() == HttpURLConnection.HTTP_OK)
			{
				InputStream response = connection.getInputStream();
				result = IOUtils.toByteArray(response);
				response.close();
			}
		}
		catch(Exception e)
		{
			throw new ClientException(e);
		}
		return result;
	}
}
