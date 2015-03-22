/**
 * Controller.java
 * JRE v1.8.0_40
 * 
 * Created by William Myers on Mar 22, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package servertester.controllers;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import client.communication.ClientCommunicator;

import servertester.views.*;

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
        ArrayList<String> paramNames = new ArrayList<String>();
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
        String[] args = getView().getParameterValues();
        String port = getView().getPort();
        try {
            String host = getView().getHost();

            ClientCommunicator client = new ClientCommunicator(port, host);
            ValidateUserRequest creds = new ValidateUserRequest(args[0], args[1]);
            ValidateUserResponse result = client.validateUser(creds);
            //getView().setRequest(.toString());
            getView().setResponse(result.toString());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            getView().setResponse("FAILED\n" + e.toString());
        }
    }

    private void getProjects() {
        String[] args = getView().getParameterValues();
        String port = getView().getPort();
        try {
            String host = getView().getHost();

            ClientCommunicator client = new ClientCommunicator(port, host);
            GetProjectsRequest params = new GetProjectsRequest(args[0], args[1]);
            GetProjectsResponse result = client.getProjects(params);
            getView().setResponse(result.toString());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            getView().setResponse("FAILED\n" + e.toString());
        }
    }

    private void getSampleBatch() {
        String[] args = getView().getParameterValues();
        String port = getView().getPort();
        try {
            String host = getView().getHost();

            ClientCommunicator client = new ClientCommunicator(port, host);
            GetSampleBatchRequest params =
                            new GetSampleBatchRequest(args[0], args[1],
                                            Integer.parseInt(args[2]));
            GetSampleBatchResponse result = client.getSampleBatch(params);
            getView().setResponse(result.toString());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            getView().setResponse("FAILED\n" + e.toString());
        }
    }

    private void downloadBatch() {
        String[] args = getView().getParameterValues();
        String port = getView().getPort();
        try {
            String host = getView().getHost();

            ClientCommunicator client = new ClientCommunicator(port, host);
            DownloadBatchRequest creds =
                            new DownloadBatchRequest(args[0], args[1], Integer.parseInt(args[2]));
            DownloadBatchResponse result = client.downloadBatch(creds);
            getView().setResponse(result.toString());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            getView().setResponse("FAILED\n" + e.toString());

        }
    }

    private void getFields() {
        String[] args = getView().getParameterValues();
        int projectId = -1;
        try {
            if (args[2].length() != 0) {
                projectId = Integer.parseInt(args[2]);
                if (projectId == -1) {
                    getView().setResponse("FAILED\n");
                    return;
                }
            }

            String port = getView().getPort();
            String host = getView().getHost();

            ClientCommunicator client = new ClientCommunicator(port, host);
            GetFieldsRequest params = new GetFieldsRequest(args[0], args[1], projectId);
            GetFieldsResponse result = client.getFields(params);
            getView().setResponse(result.toString());
        } catch (Exception e) {
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
        // getView().setResponse(result.toString());
        // } catch (Exception e) {
        // getView().setResponse("FAILED\n");
        // }
    }

    private void search() {
        String[] args = getView().getParameterValues();
        ArrayList<Integer> fieldList = new ArrayList<Integer>();
        ArrayList<String> searchList = new ArrayList<String>();

        String fieldId = args[2];
        try {
            List<String> tempFieldId = Arrays.asList(fieldId.split(",", -1));
            for (String s : tempFieldId) {
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

            ClientCommunicator client = new ClientCommunicator(port, host);
            SearchRequest params = new SearchRequest(args[0], args[1], fieldList, searchList);
            SearchResponse result = client.search(params);
            getView().setResponse(result.toString());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            getView().setResponse("FAILED\n" + e.toString());
        }
    }

}
