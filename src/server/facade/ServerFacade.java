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
import java.util.*;
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
  public static String  LOG_NAME = "server";

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
  public static ValidateUserResponse validateUser(ValidateUserRequest request)
      throws InvalidCredentialsException {
    Database db = new Database();
    boolean isValid = false;
    String username = request.getUsername();
    String password = request.getPassword();
    User user = null;

    try {
      db.startTransaction();
      user = db.getUserDAO().read(username, password);
      // TODO: should I Perform this additional check on password...
      isValid = user.getPassword().equals(password);
    } catch (DatabaseException e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      throw new InvalidCredentialsException(e.toString());
    } finally {
      db.endTransaction(true);
    }

    ValidateUserResponse result = new ValidateUserResponse(user, isValid);
    return result;
  }

  /**
   * Gets all the projects in the database
   *
   * @throws InvalidCredentialsException
   */
  public static GetProjectsResponse getProjects(GetProjectsRequest request)
      throws ServerException, InvalidCredentialsException {
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
    return result;
  }

  /**
   * Gets a sample batch from a project
   *
   * @throws InvalidCredentialsException
   */
  public static GetSampleBatchResponse getSampleBatch(GetSampleBatchRequest request)
      throws ServerException, InvalidCredentialsException {
    Database db = new Database();
    Batch sampleBatch = null;

    try {
      db.startTransaction();
      sampleBatch = db.getBatchDAO().getSampleBatch(request.getProjectId());
      db.endTransaction(true);
    } catch (DatabaseException e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      throw new ServerException(e.toString());
    }

    GetSampleBatchResponse result = new GetSampleBatchResponse();
    result.setSampleBatch(sampleBatch);
    return result;
  }

  /**
   * Downloads an incomplete batch from a project. This includes information from batchs, projects,
   * and fields
   *
   * @throws InvalidCredentialsException
   * @throws DatabaseException
   */
  public static DownloadBatchResponse downloadBatch(DownloadBatchRequest request)
      throws ServerException, InvalidCredentialsException, DatabaseException {
    Database db = new Database();
    int projectId = request.getProjectId();

    Batch batchToDownload = null;
    Project project = null;
    List<Field> fields = null;
    try {
      db.startTransaction();

      // update the batch & user to reflect downloaded batch
      User currUser = db.getUserDAO().read(request.getUsername(), request.getPassword());
      int currUserId = currUser.getUserId();

      if (currUser.getCurrBatch() < 1) {
        batchToDownload = db.getBatchDAO().getIncompleteBatch(projectId);
        int currBatchId = batchToDownload.getBatchId();

        // update the user and batch models
        currUser.setCurrBatch(currBatchId);
        batchToDownload.setCurrUserId(currUserId);

        // update the user and batch in the db
        db.getUserDAO().updateCurrentBatchId(currUserId, currBatchId);
        db.getBatchDAO().assignBatchToUser(currBatchId, currUserId);

        project = db.getProjectDAO().read(projectId);
        fields = db.getFieldDAO().getAll(projectId);
      } else {
        db.endTransaction(false);
        throw new ServerException("ERROR: user already has a batch checked out...");
      }

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
    return result;
  }

  private static ArrayList<Integer> getFieldIDs(Batch batch, Database db) throws DatabaseException {
    ArrayList<Integer> fields = new ArrayList<Integer>();
    ArrayList<Field> holder = db.getFieldDAO().getAll();
    for (Field f : holder) {
      if (f.getProjectId() == batch.getProjectId()) {
        fields.add(f.getFieldId());
      }
    }
    return fields;
  }

  private static void addRecords(String input, Project project, Batch batch, Database db,
      ArrayList<Integer> fields) throws DatabaseException {
    List<String> rows = Arrays.asList(input.split(";", -1));
    int row = 1;
    for (String s : rows) {
      int i = 0;
      List<String> values = Arrays.asList(s.split(",", -1));
      for (String currVal : values) {
        currVal = currVal.toUpperCase();
        Record record =
            new Record(row, fields.get(i), batch.getBatchId(), batch.getFilePath(), currVal);
        db.getRecordDAO().create(record);
        i++;
      }
      row++;
    }
    batch.setStatus(Batch.ACTIVE);
    db.getBatchDAO().update(batch);
  }

  /**
   * Submits values from a batch into the database
   *
   * @return
   *
   * @throws InvalidCredentialsException
   */
  public static SubmitBatchResponse submitBatch(SubmitBatchRequest request) {
    SubmitBatchResponse result = new SubmitBatchResponse();
    Database db = new Database();
    try {
      db.startTransaction();
      User user = db.getUserDAO().read(request.getUsername(), request.getPassword());

      if (user.getCurrBatch() == request.getBatchID()) {
        String input = request.getFieldValues();
        Batch batch = db.getBatchDAO().read(request.getBatchID());
        Project project = db.getProjectDAO().read(batch.getProjectId());
        ArrayList<Integer> fields = getFieldIDs(batch, db);
        int size = fields.size();
        if (size > 0) {

          addRecords(input, project, batch, db, fields);

          user.setCurrBatch(0);
          int count = (user.getRecordCount() + project.getRecordsPerBatch());
          user.setRecordCount(count);
          db.getUserDAO().update(user);
          result.setSuccess(true);
        } else {
          result.setSuccess(false);
          db.endTransaction(false);
          return result;
        }
      } else {
        result.setSuccess(false);
        db.endTransaction(false);
        return result;
      }
    } catch (Exception e) {
      db.endTransaction(false);
      e.printStackTrace();
    }
    db.endTransaction(true);
    return result;
  }

  /**
   * Gets the fields for a project
   *
   * @throws InvalidCredentialsException
   */
  public static GetFieldsResponse getFields(GetFieldsRequest request) throws ServerException,
      InvalidCredentialsException {
    Database db = new Database();
    int projectId = request.getProjectId();
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
    return result;
  }

  /**
   * Searches certain fields for certain values
   *
   * @throws InvalidCredentialsException
   */
  public static SearchResponse search(SearchRequest request) throws ServerException,
      InvalidCredentialsException {
    Database db = new Database();
    List<Record> records = null;

    try {
      db.startTransaction();
      records = db.getRecordDAO().search(request.getFieldIds(), request.getSearchQueries());
      db.endTransaction(true);
    } catch (DatabaseException e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      throw new ServerException(e.toString());
    }

    SearchResponse result = new SearchResponse();
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
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      throw new ServerException(e.toString());
    }
    return new DownloadFileResponse(data);
  }
}
