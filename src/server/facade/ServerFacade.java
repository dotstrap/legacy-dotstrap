/**
 * ServerFacade.java
 * JRE v1.8.0_40
 * 
 * Created by William Myers on Mar 22, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package server.facade;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import server.ServerException;
import server.database.*;

import shared.model.*;
import shared.communication.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

/**
 */
public class ServerFacade {
    /** The logger used throughout the project. */
    private static Logger logger;

    public static void initialize() throws ServerException {
        try {
            logger = Logger.getLogger("server");
            Database.initDriver();
        } catch (DatabaseException e) {
            throw new ServerException(e.getMessage(), e);
        }
    }

    /**
     * Validates a username/password combination
     *
     * @throws ServerException
     */
    public static ValidateUserResult validateUser(ValidateUserParameters params)
            throws ServerException {
        Database db = new Database();
        boolean isValid = false;
        String username = params.getUsername();
        String password = params.getPassword();
        User user = null;
        try {
            db.startTransaction();
            user = db.getUserDAO().read(username, password);
            // Preform an additional check on password...
            isValid = user.getPassword().equals(password);
        } catch (DatabaseException e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new ServerException(e.toString());
        } finally {
            db.endTransaction(true);
        }
         //TODO: should I handle it this way?
        if (!isValid) {
            user = new User();
        }

        ValidateUserResult result = new ValidateUserResult(user, isValid);

        return result;
    }

    /**
     * Gets all the projects in the database
     */
    public static GetProjectsResult getProjects(GetProjectsParameters params)
            throws ServerException {
        ValidateUserParameters validate = new ValidateUserParameters();
        validate.setUsername(params.getUsername());
        validate.setPassword(params.getPassword());
        boolean isValid = validateUser(validate).isValid();
        if (!isValid) {
            throw new ServerException("Invalid credentials...");
        }

        Database db = new Database();
        List<Project> projects = null;
        try {
            db.startTransaction();
            projects = db.getProjectDAO().getAll();
            db.endTransaction(true);
        } catch (DatabaseException e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new ServerException("FAILED\n");
        }

        GetProjectsResult result = new GetProjectsResult();
        result.setProjects(projects);

        return result;
    }

    /**
     * Gets a sample batch from a project
     */
    public static GetSampleBatchResult getSampleBatch(GetSampleBatchParameters params)
            throws ServerException {
        ValidateUserParameters validate = new ValidateUserParameters();
        validate.setUsername(params.getUsername());
        validate.setPassword(params.getPassword());
        boolean isValid = validateUser(validate).isValid();
        if (!isValid) {
            throw new ServerException("Invalid credentials...");
        }

        Database db = new Database();
        Batch sampleBatch = null;

        try {
            db.startTransaction();
            sampleBatch = db.getBatchDAO().getSampleBatch(params.getProjectID());
            db.endTransaction(true);
        } catch (DatabaseException e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new ServerException(e.toString());
        }

        GetSampleBatchResult result = new GetSampleBatchResult();
        result.setSampleBatch(sampleBatch);

        return result;
    }

    /**
     * Downloads an incomplete batch from a project. This includes information from batchs,
     * projects, and fields
     */
    public static DownloadBatchResult downloadBatch(DownloadBatchParameters params)
            throws ServerException {
        ValidateUserParameters validate = new ValidateUserParameters();
        validate.setUsername(params.getUsername());
        validate.setPassword(params.getPassword());
        boolean isValid = validateUser(validate).isValid();
        if (!isValid) {
            throw new ServerException("Invalid credentials...");
        }

        Database db = new Database();
        int projectId = params.getProjectID();
        Batch batchToDownload = null;
        Project project = null;
        List<Field> fields = null;
        try {
            db.startTransaction();
            batchToDownload = db.getBatchDAO().getIncompleteBatch(projectId);
            project = db.getProjectDAO().read(projectId);
            fields = db.getFieldDAO().getAll(projectId);
            db.endTransaction(true);
        } catch (DatabaseException e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new ServerException(e.toString());
        }

        DownloadBatchResult result = new DownloadBatchResult();
        result.setBatch(batchToDownload);
        result.setProject(project);
        result.setFields(fields);

        return result;
    }

    /**
     * Submits values from a batch into the database
     */
    public static void submitBatch(SubmitBatchParameters params) throws ServerException {
        ValidateUserParameters validate = new ValidateUserParameters();
        validate.setUsername(params.getUsername());
        validate.setPassword(params.getPassword());
        boolean isValid = validateUser(validate).isValid();
        if (!isValid) {
            throw new ServerException("Invalid credentials...");
        }

        Database db = new Database();
        int batchId = params.getBatchID();
        List<Record> records = params.getFieldValues();
        Batch batch = null;
        String batchUrl = null;
        int projectId = 0;

        try {
            db.startTransaction();
            batch = db.getBatchDAO().read(batchId);
            batchUrl = batch.getFilePath();
            projectId = batch.getProjectID();

            for (Record curr : records) {
                int fieldId = db.getFieldDAO().getFieldID(projectId, curr.getColNum());
                curr.setFieldID(fieldId);
                curr.setBatchURL(batchUrl);
                db.getRecordDAO().create(curr);
            }
        } catch (DatabaseException e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new ServerException(e.toString());
        }
    }

    /**
     * Gets the fields for a project
     */
    public static GetFieldsResult getFields(GetFieldsParameters params) throws ServerException {
        ValidateUserParameters validate = new ValidateUserParameters();
        validate.setUsername(params.getUsername());
        validate.setPassword(params.getPassword());
        boolean isValid = validateUser(validate).isValid();
        if (!isValid) {
            throw new ServerException("Invalid credentials...");
        }

        Database db = new Database();
        int projectId = params.getProjectID();
        List<Field> fields = null;

        try {
            db.startTransaction();
            fields = projectId > 0 ? db.getFieldDAO().getAll(projectId) : db.getFieldDAO().getAll();
            db.endTransaction(true);
        } catch (DatabaseException e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new ServerException(e.toString());
        }

        GetFieldsResult result = new GetFieldsResult();
        result.setFields(fields);
        return result;
    }

    /**
     * Searches certain fields for certain values
     */
    public static SearchResult search(SearchParameters params) throws ServerException {
        ValidateUserParameters validate = new ValidateUserParameters();
        validate.setUsername(params.getUsername());
        validate.setPassword(params.getPassword());
        boolean isValid = validateUser(validate).isValid();
        if (!isValid) {
            throw new ServerException("Invalid credentials...");
        }

        Database db = new Database();
        List<Record> records = null;
        try {
            db.startTransaction();
            records = db.getRecordDAO().search(params.getFieldIds(), params.getSearchQueries());
            db.endTransaction(true);
        } catch (DatabaseException e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new ServerException(e.toString());
        }

        SearchResult result = new SearchResult();
        result.setFoundRecords(records);

        return result;
    }

    /**
     * Downloads a file from the server
     *
     * @param params
     * @return
     * @throws ServerException
     */
    public static DownloadFileResult downloadFile(DownloadFileParameters params)
            throws ServerException {
        ValidateUserParameters validate = new ValidateUserParameters();
        validate.setUsername(params.getUsername());
        validate.setPassword(params.getPassword());
        boolean isValid = validateUser(validate).isValid();
        if (!isValid) {
            throw new ServerException("Invalid credentials...");
        }

        InputStream is;
        byte[] data = null;
        try {
            is = new FileInputStream(params.getUrl());
            data = IOUtils.toByteArray(is);
            is.close();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new ServerException(e.toString());
        }
        return new DownloadFileResult(data);
    }
}
