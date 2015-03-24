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
  private static Logger logger;
  public final static String LOG_NAME = "server";

  public static void initialize() throws ServerException {
    try {
      logger = Logger.getLogger(LOG_NAME);
      Database.initDriver();
    } catch (final DatabaseException e) {
      throw new ServerException(e.getMessage(), e);
    }
  }

  /**
   * Validates a username/password combination
   *
   * @throws ServerException
   * @throws InvalidCredentialsException
   */
  public static ValidateUserResponse validateUser(ValidateUserRequest request)
      throws InvalidCredentialsException {
    final Database db = new Database();
    boolean isValid = false;
    final String username = request.getUsername();
    final String password = request.getPassword();
    User user = null;

    try {
      db.startTransaction();
      user = db.getUserDAO().read(username, password);
      // TODO: should I Perform this additional check on password...
      isValid = user.getPassword().equals(password);
    } catch (final DatabaseException e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      throw new InvalidCredentialsException(e.toString());
    } finally {
      db.endTransaction(true);
    }

    final ValidateUserResponse result = new ValidateUserResponse(user, isValid);
    return result;
  }

  /**
   * Gets all the projects in the database
   *
   * @throws InvalidCredentialsException
   */
  public static GetProjectsResponse getProjects(GetProjectsRequest request) throws ServerException,
      InvalidCredentialsException {
    final Database db = new Database();
    List<Project> projects = null;

    try {
      db.startTransaction();
      projects = db.getProjectDAO().getAll();
      db.endTransaction(true);
    } catch (final DatabaseException e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      throw new ServerException(e.toString());
    }

    final GetProjectsResponse result = new GetProjectsResponse();
    result.setProjects(projects);
    return result;
  }

  /**
   * Gets a sample batch from a project
   *
   * @throws InvalidCredentialsException
   */
  public static GetSampleBatchResponse getSampleBatch(GetSampleBatchRequest request)
      throws ServerException, InvalidCredentialsException {
    final Database db = new Database();
    Batch sampleBatch = null;

    try {
      db.startTransaction();
      sampleBatch = db.getBatchDAO().getSampleBatch(request.getProjectId());
      db.endTransaction(true);
    } catch (final DatabaseException e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      throw new ServerException(e.toString());
    }

    final GetSampleBatchResponse result = new GetSampleBatchResponse();
    result.setSampleBatch(sampleBatch);
    return result;
  }

  /**
   * Downloads an incomplete batch from a project. This includes information from batchs, projects,
   * and fields
   *
   * @throws InvalidCredentialsException
   */
  public static DownloadBatchResponse downloadBatch(DownloadBatchRequest request)
      throws ServerException, InvalidCredentialsException {
    final Database db = new Database();
    final int projectId = request.getProjectId();
    Batch batchToDownload = null;
    Project project = null;
    List<Field> fields = null;

    try {
      db.startTransaction();
      batchToDownload = db.getBatchDAO().getIncompleteBatch(projectId);
      project = db.getProjectDAO().read(projectId);
      fields = db.getFieldDAO().getAll(projectId);
      db.endTransaction(true);
    } catch (final DatabaseException e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      throw new ServerException(e.toString());
    }

    final DownloadBatchResponse result = new DownloadBatchResponse();
    result.setBatch(batchToDownload);
    result.setProject(project);
    result.setFields(fields);
    return result;
  }

  /**
   * Submits values from a batch into the database
   *
   * @throws InvalidCredentialsException
   */
  public static void submitBatch(SubmitBatchRequest request) throws ServerException,
      InvalidCredentialsException {
    final Database db = new Database();
    final int batchId = request.getBatchId();
    final List<Record> records = request.getFieldValues();
    Batch batch = null;
    String batchUrl = null;
    int projectId = 0;

    try {
      db.startTransaction();
      batch = db.getBatchDAO().read(batchId);
      batchUrl = batch.getFilePath();
      projectId = batch.getProjectId();

      for (final Record curr : records) {
        final int fieldId = db.getFieldDAO().getFieldId(projectId, curr.getColNum());
        curr.setFieldId(fieldId);
        curr.setBatchURL(batchUrl);
        db.getRecordDAO().create(curr);
      }
    } catch (final DatabaseException e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      throw new ServerException(e.toString());
    }
  }

  /**
   * Gets the fields for a project
   *
   * @throws InvalidCredentialsException
   */
  public static GetFieldsResponse getFields(GetFieldsRequest request) throws ServerException,
      InvalidCredentialsException {
    final Database db = new Database();
    final int projectId = request.getProjectId();
    List<Field> fields = null;

    try {
      db.startTransaction();
      fields = projectId > 0 ? db.getFieldDAO().getAll(projectId) : db.getFieldDAO().getAll();
      db.endTransaction(true);
    } catch (final DatabaseException e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      throw new ServerException(e.toString());
    }

    final GetFieldsResponse result = new GetFieldsResponse();
    result.setFields(fields);
    return result;
  }

  /**
   * Searches certain fields for certain values
   *
   * @throws InvalidCredentialsException
   */
  public static SearchResponse search(SearchRequest request) throws ServerException,
      InvalidCredentialsException {
    final Database db = new Database();
    List<Record> records = null;

    try {
      db.startTransaction();
      records = db.getRecordDAO().search(request.getFieldIds(), request.getSearchQueries());
      db.endTransaction(true);
    } catch (final DatabaseException e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      throw new ServerException(e.toString());
    }

    final SearchResponse result = new SearchResponse();
    result.setFoundRecords(records);
    return result;
  }

  /**
   * Downloads a file from the server
   *
   * @param request
   * @return
   * @throws ServerException
   * @throws InvalidCredentialsException
   */
  public static DownloadFileResponse downloadFile(DownloadFileRequest request)
      throws ServerException, InvalidCredentialsException {
    InputStream is;
    byte[] data = null;

    try {
      is = new FileInputStream(request.getUrl());
      data = IOUtils.toByteArray(is);
      is.close();
    } catch (final Exception e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      throw new ServerException(e.toString());
    }
    return new DownloadFileResponse(data);
  }
}
