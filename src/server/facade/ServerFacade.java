/**
 * ServerFacade.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 23, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package server.facade;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

import server.ServerException;
import server.database.Database;
import server.database.DatabaseException;

import shared.InvalidCredentialsException;
import shared.communication.*;
import shared.model.*;

/**
 */
public class ServerFacade {
    /** The logger used throughout the project. */
    private static Logger      logger;
    public final static String LOG_NAME = "server";

    public static void initialize() throws ServerException {
        try {
            logger = Logger.getLogger(LOG_NAME);
            Database.initDriver();
        } catch (DatabaseException e) {
            throw new ServerException(e.getMessage(), e);
        }
    }

    /**
     * Validates a username/password combination
     *
     * @throws ServerException
     * @throws InvalidCredentialsException
     */
    public static ValidateUserResponse validateUser(ValidateUserRequest params)
                    throws InvalidCredentialsException {
        logger.entering("server.facade.ServerFacade", "validateUser");

        Database db = new Database();
        boolean isValid = false;
        String username = params.getUsername();
        String password = params.getPassword();
        User user = null;
        try {
            db.startTransaction();
            user = db.getUserDAO().read(username, password);
            // TODO: should I Preform an additional check on password...
            isValid = user.getPassword().equals(password);
        } catch (DatabaseException e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new InvalidCredentialsException(e.toString());
        } finally {
            db.endTransaction(true);
        }

        ValidateUserResponse result = new ValidateUserResponse(user, isValid);

        logger.exiting("server.facade.ServerFacade", "validateUser");
        return result;
    }

    /**
     * Gets all the projects in the database
     *
     * @throws InvalidCredentialsException
     */
    public static GetProjectsResponse getProjects(GetProjectsRequest params)
                    throws ServerException, InvalidCredentialsException {
        logger.entering("server.facade.ServerFacade", "getProjects");

        Database db = new Database();
        List<Project> projects = null;
        try {
            db.startTransaction();
            projects = db.getProjectDAO().getAll();
            db.endTransaction(true);
        } catch (DatabaseException e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new ServerException(e.toString());
        }

        GetProjectsResponse result = new GetProjectsResponse();
        result.setProjects(projects);

        logger.exiting("server.facade.ServerFacade", "getProjects");
        return result;
    }

    /**
     * Gets a sample batch from a project
     *
     * @throws InvalidCredentialsException
     */
    public static GetSampleBatchResponse getSampleBatch(GetSampleBatchRequest params)
                    throws ServerException, InvalidCredentialsException {
        logger.entering("server.facade.ServerFacade", "getSampleBatch");

        Database db = new Database();
        Batch sampleBatch = null;

        try {
            db.startTransaction();
            sampleBatch = db.getBatchDAO().getSampleBatch(params.getProjectId());
            db.endTransaction(true);
        } catch (DatabaseException e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new ServerException(e.toString());
        }

        GetSampleBatchResponse result = new GetSampleBatchResponse();
        result.setSampleBatch(sampleBatch);

        logger.exiting("server.facade.ServerFacade", "getSampleBatch");
        return result;
    }

    /**
     * Downloads an incomplete batch from a project. This includes information from batchs,
     * projects, and fields
     *
     * @throws InvalidCredentialsException
     */
    public static DownloadBatchResponse downloadBatch(DownloadBatchRequest params)
                    throws ServerException, InvalidCredentialsException {
        logger.entering("server.facade.ServerFacade", "downloadBatch");

        Database db = new Database();
        int projectId = params.getProjectId();
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

        DownloadBatchResponse result = new DownloadBatchResponse();
        result.setBatch(batchToDownload);
        result.setProject(project);
        result.setFields(fields);

        logger.exiting("server.facade.ServerFacade", "downloadBatch");
        return result;
    }

    /**
     * Submits values from a batch into the database
     *
     * @throws InvalidCredentialsException
     */
    public static void submitBatch(SubmitBatchRequest params) throws ServerException,
                    InvalidCredentialsException {
        logger.entering("server.facade.ServerFacade", "submitBatch");

        Database db = new Database();
        int batchId = params.getBatchId();
        List<Record> records = params.getFieldValues();
        Batch batch = null;
        String batchUrl = null;
        int projectId = 0;

        try {
            db.startTransaction();
            batch = db.getBatchDAO().read(batchId);
            batchUrl = batch.getFilePath();
            projectId = batch.getProjectId();

            for (Record curr : records) {
                int fieldId = db.getFieldDAO().getFieldId(projectId, curr.getColNum());
                curr.setFieldId(fieldId);
                curr.setBatchURL(batchUrl);
                db.getRecordDAO().create(curr);
            }
        } catch (DatabaseException e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new ServerException(e.toString());
        }

        logger.exiting("server.facade.ServerFacade", "getFields");
    }

    /**
     * Gets the fields for a project
     *
     * @throws InvalidCredentialsException
     */
    public static GetFieldsResponse getFields(GetFieldsRequest params) throws ServerException,
                    InvalidCredentialsException {
        logger.entering("server.facade.ServerFacade", "getFields");

        Database db = new Database();
        int projectId = params.getProjectId();
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

        GetFieldsResponse result = new GetFieldsResponse();
        result.setFields(fields);

        logger.exiting("server.facade.ServerFacade", "getFields");
        return result;
    }

    /**
     * Searches certain fields for certain values
     *
     * @throws InvalidCredentialsException
     */
    public static SearchResponse search(SearchRequest params) throws ServerException,
                    InvalidCredentialsException {
        logger.entering("server.facade.ServerFacade", "search");

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

        SearchResponse result = new SearchResponse();
        result.setFoundRecords(records);

        logger.exiting("server.facade.ServerFacade", "search");
        return result;
    }

    /**
     * Downloads a file from the server
     *
     * @param params
     * @return
     * @throws ServerException
     * @throws InvalidCredentialsException
     */
    public static DownloadFileResponse downloadFile(DownloadFileRequest params)
                    throws ServerException, InvalidCredentialsException {
        logger.entering("server.facade.ServerFacade", "downloadFile");

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

        logger.exiting("server.facade.ServerFacade", "downloadFile");
        return new DownloadFileResponse(data);
    }
}
