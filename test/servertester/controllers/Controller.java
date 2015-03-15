/**
 * Controller.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 15, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package servertester.controllers;

import java.util.*;

import servertester.views.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Controller.
 */
public class Controller implements IController {

    /** The _view. */
    private IView _view;

    /**
     * Instantiates a new controller.
     */
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

    /*
     * (non-Javadoc)
     * 
     * @see servertester.controllers.IController#initialize()
     */
    @Override
    public void initialize() {
        getView().setHost("localhost");
        getView().setPort("39640");
        operationSelected();
    }

    /*
     * (non-Javadoc)
     * 
     * @see servertester.controllers.IController#operationSelected()
     */
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
        getView().setParameterNames(
                paramNames.toArray(new String[paramNames.size()]));
    }

    /*
     * (non-Javadoc)
     * 
     * @see servertester.controllers.IController#executeOperation()
     */
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

    /**
     * Validate user.
     */
    private void validateUser() {
    }

    /**
     * Gets the projects.
     *
     * @return the projects
     */
    private void getProjects() {
    }

    /**
     * Gets the sample image.
     *
     * @return the sample image
     */
    private void getSampleImage() {
    }

    /**
     * Download batch.
     */
    private void downloadBatch() {
    }

    /**
     * Gets the fields.
     *
     * @return the fields
     */
    private void getFields() {
    }

    /**
     * Submit batch.
     */
    private void submitBatch() {
    }

    /**
     * Search.
     */
    private void search() {
    }

}
