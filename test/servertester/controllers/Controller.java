package servertester.controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.logging.*;

import client.communication.ClientCommunicator;

import servertester.views.IView;

import shared.communication.*;
public class Controller implements IController {

    private IView _view;

    /** The logger used throughout the project. */
    private static Logger logger; // @formatter:off

    /** The log name. */
    public static String LOG_NAME = "client";
    static {
      try {
        FileInputStream is = new FileInputStream("logging.properties");
        LogManager.getLogManager().readConfiguration(is);
        logger = Logger.getLogger(LOG_NAME);
      } catch (IOException e) {


      }
      logger.info("===================Initialized " + LOG_NAME + " log===================");
    }

    public Controller() {
        return;
    }

    public IView getView() {
        return _view;
    }

    public void setView(IView value) {
        _view = value;
    }

    // IController methods
    //

    @Override
    public void initialize() {
        getView().setHost("localhost");
        getView().setPort("39640");
        operationSelected();
    }

    @Override
    public void operationSelected() {
        ArrayList<String> paramNames = new ArrayList<String>();
        paramNames.add("User");
        paramNames.add("Password");

        switch (getView().getOperation()) {
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
    public void executeOperation() {
        switch (getView().getOperation()) {
        case VALIDATE_USER:
            validateUser();
            break;
        case GET_PROJECTS:
            getProjects();
            break;
        case GET_SAMPLE_IMAGE:
            getSampleBatch();
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

private void validateUser() {
    String[] args = getView().getParameterValues();
    String port = getView().getPort();
    String host = getView().getHost();

    ValidateUserRequest params = null;
    try {

      ClientCommunicator clientComm = new ClientCommunicator(port, host);

      params = new ValidateUserRequest(args[0], args[1]);
      ValidateUserResponse result = clientComm.validateUser(params);

      getView().setResponse(result.toString());
    } catch (Exception e) {
       logger.log(Level.SEVERE, "STACKTRACE: ", e);
       System.out.println("SOOOOOOOOOOOOOOOOOOOOOOOOOO TIRED");
      getView().setResponse("FAILED\n" + e.toString());
    } finally {
      getView().setRequest(params.toString());
    }
  }

  private void getProjects() {
    String[] args = getView().getParameterValues();
    String port = getView().getPort();
    GetProjectsRequest params = null;
    try {
      String host = getView().getHost();

      ClientCommunicator clientComm = new ClientCommunicator(port, host);
      params = new GetProjectsRequest(args[0], args[1]);
      GetProjectsResponse result = clientComm.getProjects(params);

      getView().setRequest(params.toString());
      getView().setResponse(result.toString());
    } catch (Exception e) {
      // logger.log(Level.SEVERE, "STACKTRACE: ", e);
      getView().setResponse("FAILED\n");
    } finally {
      getView().setRequest(params.toString());
    }
  }

  private void getSampleBatch() {
    String[] args = getView().getParameterValues();
    String port = getView().getPort();
    try {
      String host = getView().getHost();

      ClientCommunicator clientComm = new ClientCommunicator(port, host);
      GetSampleBatchRequest params =
          new GetSampleBatchRequest(args[0], args[1], Integer.parseInt(args[2]));
      GetSampleBatchResponse result = clientComm.getSampleBatch(params);

      getView().setRequest(params.toString());
      getView().setResponse(result.toString());
    } catch (Exception e) {
      // logger.log(Level.SEVERE, "STACKTRACE: ", e);
      getView().setResponse("FAILED\n");
    }
  }

  private void downloadBatch() {
    String[] args = getView().getParameterValues();
    String port = getView().getPort();
    DownloadBatchRequest params = null;
    try {
      String host = getView().getHost();

      ClientCommunicator clientComm = new ClientCommunicator(port, host);
      params = new DownloadBatchRequest(args[0], args[1], Integer.parseInt(args[2]));
      DownloadBatchResponse result = clientComm.downloadBatch(params);

      getView().setResponse(result.toString());
    } catch (Exception e) {
      // logger.log(Level.SEVERE, "STACKTRACE: ", e);
      getView().setResponse("FAILED\n");
    } finally {
      getView().setRequest(params.toString());
    }
  }

  private void getFields() {
    String[] args = getView().getParameterValues();
    int projectId = 0;
    GetFieldsRequest params = null;
    try {
      if (args[2].length() != 0) {
        projectId = Integer.parseInt(args[2]);
        if (projectId < 0) {
          getView().setResponse("FAILED\n");
          return;
        }
      }

      String port = getView().getPort();
      String host = getView().getHost();

      ClientCommunicator clientComm = new ClientCommunicator(port, host);
      params = new GetFieldsRequest(args[0], args[1], projectId);
      GetFieldsResponse result = clientComm.getFields(params);

      getView().setRequest(params.toString());
      getView().setResponse(result.toString());
    } catch (Exception e) {
      // logger.log(Level.SEVERE, "STACKTRACE: ", e);
      getView().setResponse("FAILED\n");
    } finally {
      getView().setRequest(params.toString());
    }
  }

  private void submitBatch() {
    String[] args = getView().getParameterValues();
    String port = getView().getPort();
    SubmitBatchRequest params = null;
    try {
      String host = getView().getHost();

      ClientCommunicator clientComm = new ClientCommunicator(port, host);
      params = new SubmitBatchRequest(args[0], args[1], Integer.parseInt(args[2]), args[3]);
      SubmitBatchResponse result = clientComm.submitBatch(params);
      getView().setResponse(result.toString()); // FIXME: there is no output upon success....
    } catch (Exception e) {
      // getView().setResponse(result.toString());
      getView().setResponse("FAILED\n");
    } finally {
      getView().setRequest(params.toString());
    }
  }

  private void search() {
    String[] args = getView().getParameterValues();
    ArrayList<Integer> fieldList = new ArrayList<Integer>();
    ArrayList<String> searchList = new ArrayList<String>();
    SearchRequest params = null;
    String fieldId = args[2];
    try {
      // FIXME: fields wont parse if 1,2,3 and it returns a server error 500 if just one digit
      List<String> tmpFieldIds = Arrays.asList(fieldId.split(",", -1));
      for (String s : tmpFieldIds) {
        if (!fieldList.contains(Integer.parseInt(s))) {
          fieldList.add(Integer.parseInt(s));
        }
      }

      String search = args[3];
      List<String> searchQuery = Arrays.asList(search.split(",", -1));
      for (String s : searchQuery) {
        s = s.toUpperCase();
        if (!searchList.contains(s)) {
          searchList.add(s);
        }
      }

      String port = getView().getPort();
      String host = getView().getHost();

      ClientCommunicator clientComm = new ClientCommunicator(port, host);
      params = new SearchRequest(args[0], args[1], fieldList, searchList);
      SearchResponse result = clientComm.search(params);

      getView().setResponse(result.toString());
    } catch (Exception e) {
      // logger.log(Level.SEVERE, "STACKTRACE: ", e);
      getView().setResponse("FAILED\n");
    } finally {
      getView().setRequest(params.toString());
    }
  }

}

