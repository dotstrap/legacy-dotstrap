/**
 * Controller.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 23, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package servertester.controllers;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import client.communication.ClientCommunicator;

import servertester.views.IView;

import shared.communication.*;

public class Controller implements IController {
  /** The logger used throughout the project. */
  private static Logger logger;
  private IView         _view;

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

  @Override
  public void initialize() {
    logger = Logger.getLogger("client");
    getView().setHost("localhost");
    getView().setPort("50080");
    operationSelected();
  }

  @Override
  public void operationSelected() {
    final ArrayList<String> paramNames = new ArrayList<String>();
    paramNames.add("User");
    paramNames.add("Password");

    switch (getView().getOperation()) {
      case VALIdATE_USER:
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
      case VALIdATE_USER:
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
    final String[] args = getView().getParameterValues();
    final String port = getView().getPort();
    try {
      final String host = getView().getHost();

      final ClientCommunicator client = new ClientCommunicator(port, host);
      final ValidateUserRequest params = new ValidateUserRequest(args[0], args[1]);
      final ValidateUserResponse result = client.validateUser(params);

      getView().setRequest(params.toString());
      getView().setResponse(result.toString());
    } catch (final Exception e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      getView().setResponse("FAILED\n" + e.toString());
    }
  }

  private void getProjects() {
    final String[] args = getView().getParameterValues();
    final String port = getView().getPort();
    try {
      final String host = getView().getHost();

      final ClientCommunicator client = new ClientCommunicator(port, host);
      final GetProjectsRequest params = new GetProjectsRequest(args[0], args[1]);
      final GetProjectsResponse result = client.getProjects(params);

      getView().setRequest(params.toString());
      getView().setResponse(result.toString());
    } catch (final Exception e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      getView().setResponse("FAILED\n" + e.toString());
    }
  }

  private void getSampleBatch() {
    final String[] args = getView().getParameterValues();
    final String port = getView().getPort();
    try {
      final String host = getView().getHost();

      final ClientCommunicator client = new ClientCommunicator(port, host);
      final GetSampleBatchRequest params =
          new GetSampleBatchRequest(args[0], args[1], Integer.parseInt(args[2]));
      final GetSampleBatchResponse result = client.getSampleBatch(params);

      getView().setRequest(params.toString());
      getView().setResponse(result.toString());
    } catch (final Exception e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      getView().setResponse("FAILED\n" + e.toString());
    }
  }

  private void downloadBatch() {
    final String[] args = getView().getParameterValues();
    final String port = getView().getPort();
    try {
      final String host = getView().getHost();

      final ClientCommunicator client = new ClientCommunicator(port, host);
      final DownloadBatchRequest params =
          new DownloadBatchRequest(args[0], args[1], Integer.parseInt(args[2]));
      final DownloadBatchResponse result = client.downloadBatch(params);

      getView().setRequest(params.toString());
      getView().setResponse(result.toString());
    } catch (final Exception e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      getView().setResponse("FAILED\n" + e.toString());

    }
  }

  private void getFields() {
    final String[] args = getView().getParameterValues();
    int projectId = 0;
    try {
      if (args[2].length() != 0) {
        projectId = Integer.parseInt(args[2]);
        if (projectId < 0) {
          getView().setResponse("FAILED\n");
          return;
        }
      }

      final String port = getView().getPort();
      final String host = getView().getHost();

      final ClientCommunicator client = new ClientCommunicator(port, host);
      final GetFieldsRequest params = new GetFieldsRequest(args[0], args[1], projectId);
      final GetFieldsResponse result = client.getFields(params);

      getView().setRequest(params.toString());
      getView().setResponse(result.toString());
    } catch (final Exception e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      getView().setResponse("FAILED\n" + e.toString());
    }
  }

  private void submitBatch() {
    // String[] args = getView().getParameterValues();
    // String port = getView().getPort();
    // try {
    // String host = getView().getHost();

    // ClientCommunicator client = new ClientCommunicator(port, host); //
    // TODO: parse fields as
    // strings
    // SubmitBatchRequest params =
    // new SubmitBatchRequest(args[0], args[1],
    // Integer.parseInt(args[2]), args[3]);
    // SubmitBatchResponse result = client.submitBatch(params);
    // getView().setResponse(result.to String());
    // } catch (Exception e) {
    // getView().setResponse("FAILED\n");
    // }
  }

  private void search() {
    final String[] args = getView().getParameterValues();
    final ArrayList<Integer> fieldList = new ArrayList<Integer>();
    final ArrayList<String> searchList = new ArrayList<String>();

    final String fieldId = args[2];
    try {

      final List<String> tmpFieldIds = Arrays.asList(fieldId.split(",", -1));
      for (final String s : tmpFieldIds) {
        if (!fieldList.contains(Integer.parseInt(s))) {
          fieldList.add(Integer.parseInt(s));
        }
      }

      final String search = args[3];
      final List<String> searchQuery = Arrays.asList(search.split(",", -1));
      for (String s : searchQuery) {
        s = s.toUpperCase();
        if (!searchList.contains(s)) {
          searchList.add(s);
        }
      }

      final String port = getView().getPort();
      final String host = getView().getHost();

      final ClientCommunicator client = new ClientCommunicator(port, host);
      final SearchRequest params = new SearchRequest(args[0], args[1], fieldList, searchList);
      final SearchResponse result = client.search(params);

      getView().setRequest(params.toString());
      getView().setResponse(result.toString());
    } catch (final Exception e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      getView().setResponse("FAILED\n" + e.toString());
    }
  }

}
