package servertester.controllers;

import java.util.*;

import client.communication.ClientCommunicator;
import servertester.views.*;
import shared.communication.*;

public class Controller implements IController 
{

	private IView _view;
	
	public Controller() 
	{
		return;
	}
	
	public IView getView() 
	{
		return _view;
	}
	
	public void setView(IView value)
	{
		_view = value;
	}
	
	// IController methods
	
	@Override
	public void initialize() 
	{
		getView().setHost("localhost");
		getView().setPort("50080");
		operationSelected();
	}

	@Override
	public void operationSelected() 
	{
		ArrayList<String> paramNames = new ArrayList<String>();
		paramNames.add("User");
		paramNames.add("Password");
		
		switch (getView().getOperation()) 
		{
		case VALIDATE_USER:
			break;
		case GET_PROJECTS:
			break;
		case GET_SAMPLE_IMAGE:
			paramNames.add("Project");
			break;
		case DOWNLOAD_BATCH:
			paramNames.add("Project");
			break;
		case GET_FIELDS:
			paramNames.add("Project");
			break;
		case SUBMIT_BATCH:
			paramNames.add("Batch");
			paramNames.add("Record Values");
			break;
		case SEARCH:
			paramNames.add("Fields");
			paramNames.add("Search Values");
			break;
		default:
			assert false;
			break;
		}
		
		getView().setRequest("");
		getView().setResponse("");
		getView().setParameterNames(paramNames.toArray(new String[paramNames.size()]));
	}

	@Override
	public void executeOperation() 
	{
		switch (getView().getOperation()) 
		{
		case VALIDATE_USER:
			validateUser();
			break;
		case GET_PROJECTS:
			getProjects();
			break;
		case GET_SAMPLE_IMAGE:
			getSampleImage();
			break;
		case DOWNLOAD_BATCH:
			downloadBatch();
			break;
		case GET_FIELDS:
			getFields();
			break;
		case SUBMIT_BATCH:
			submitBatch();
			break;
		case SEARCH:
			search();
			break;
		default:
			assert false;
			break;
		}
	}
	
	private void validateUser()
	{
		String[] args = getView().getParameterValues();
		String port = getView().getPort();
		try
		{
			String host = getView().getHost();
			
			ClientCommunicator client = new ClientCommunicator(port, host);
			ValidateUserCredentials creds = new ValidateUserCredentials(args[0], args[1]);
			ValidateUserResult result = client.validateUser(creds);
			getView().setResponse(result.toString());
		}
		catch(Exception e)
		{
			getView().setResponse("FAILED\n");
		}
	}
	
	private void getProjects()
	{
		String[] args = getView().getParameterValues();
		String port = getView().getPort();
		try
		{
			String host = getView().getHost();
			
			ClientCommunicator client = new ClientCommunicator(port, host);
			GetProjectsParameters params = new GetProjectsParameters(args[0], args[1]);
			GetProjectsResult result = client.getProjects(params);
			getView().setResponse(result.toString());
		}
		catch(Exception e)
		{
			getView().setResponse("FAILED\n");
		}
	}
	
	private void getSampleImage()
	{
		String[] args = getView().getParameterValues();
		String port = getView().getPort();
		try
		{
			String host = getView().getHost();
			
			ClientCommunicator client = new ClientCommunicator(port, host);
			GetSampleImageParameters params = new GetSampleImageParameters(args[0], args[1], Integer.parseInt(args[2]));
			GetSampleImageResult result = client.getSampleImage(params);
			getView().setResponse(result.toString());
		}
		catch(Exception e)
		{
			getView().setResponse("FAILED\n");
		}
	}
	
	private void downloadBatch()
	{
		String[] args = getView().getParameterValues();
		String port = getView().getPort();
		try
		{
			String host = getView().getHost();
			
			ClientCommunicator client = new ClientCommunicator(port, host);
			DownloadBatchParameters creds = new DownloadBatchParameters(args[0], args[1], Integer.parseInt(args[2]));
			DownloadBatchResult result = client.downloadBatch(creds);
			getView().setResponse(result.toString());
		}
		catch(Exception e)
		{
			getView().setResponse("FAILED\n");
		}
	}
	
	private void getFields()
	{
		String[] args = getView().getParameterValues();
		int proj = -1;
		try
		{
			if(args[2].length() != 0)
			{
				proj = Integer.parseInt(args[2]);
				if(proj == -1)
				{
					getView().setResponse("FAILED\n");
					return;
				}
			}

			String port = getView().getPort();
			String host = getView().getHost();
			
			ClientCommunicator client = new ClientCommunicator(port, host);
			GetFieldsParameters params = new GetFieldsParameters(args[0], args[1], proj);
			GetFieldsResult result = client.getFields(params);
			getView().setResponse(result.toString());
		}
		catch(Exception e)
		{
			getView().setResponse("FAILED\n");
		}
	}
	
	private void submitBatch()
	{
		String[] args = getView().getParameterValues();
		String port = getView().getPort();
		try
		{
			String host = getView().getHost();
			
			ClientCommunicator client = new ClientCommunicator(port, host);
			SubmitBatchParameters params = new SubmitBatchParameters(args[0], args[1], Integer.parseInt(args[2]), args[3]);
			SubmitBatchResult result = client.submitBatch(params);
			getView().setResponse(result.toString());
		}
		catch(Exception e)
		{
			getView().setResponse("FAILED\n");
		}
	}
	
	private void search()
	{
		String[] args = getView().getParameterValues();
		String fieldID = args[2];
		ArrayList<Integer> fieldList = new ArrayList<Integer>();
		ArrayList<String> searchList = new ArrayList<String>();

		try
		{	
			List<String> holder = Arrays.asList(fieldID.split(",",-1));
			for(String s : holder)
			{
				if(!fieldList.contains(Integer.parseInt(s)))
				{
					fieldList.add(Integer.parseInt(s));
				}
			}
			String search = args[3];
			List<String> holder2 = Arrays.asList(search.split(",",-1));
			for(String s : holder2)
			{
				s = s.toUpperCase();
				if(!searchList.contains(s))
				{
					searchList.add(s); 
				}
			}
			String port = getView().getPort();
			String host = getView().getHost();
		
			ClientCommunicator client = new ClientCommunicator(port, host);
			SearchParameters params = new SearchParameters(args[0], args[1], fieldList, searchList);
			SearchResult result = client.search(params);
			getView().setResponse(result.toString());
		}
		catch(Exception e)
		{
			getView().setResponse("FAILED\n");
		}
	}

}

