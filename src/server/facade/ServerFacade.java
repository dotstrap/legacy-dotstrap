/**
 * ServerFacade.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 24, 2015.
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

import shared.communication.*;
import shared.model.*;

/**
 * The Class ServerFacade.
 */
public class ServerFacade {
  /** The logger used throughout the project. */
  private static Logger logger;
  static {
    logger = Logger.getLogger("server");
  }

  /**
   * Initialize.
   *
   * @throws ServerException the server exception
   */
  public static void initialize() throws ServerException {
    try {
      Database.initDriver();
    } catch (DatabaseException e) {
      throw new ServerException(e);
    }
  }

  /**
   * Validates a username/password combination.
   *
   * @param request the request
   * @return the validate user response
   * @throws ServerException
   */
  public static ValidateUserResponse validateUser(ValidateUserRequest request)
      throws ServerException {
    Database db = new Database();
    User user = null;

    try {
      db.startTransaction();
      user = db.getUserDAO().read(request.getUsername(), request.getPassword());
    } catch (DatabaseException e) {
      logger.log(Level.SEVERE, "STACKTRACE: ", e);
      throw new ServerException("ERROR reading from database to validate username: "
          + request.getUsername());
    } finally {
      db.endTransaction(true);
    }

    ValidateUserResponse result = new ValidateUserResponse(user);
    return result;
  }

  /**
   * Gets all the projects in the database.
   *
   * @param request the request
   * @return the projects
   * @throws ServerException the server exception
   */
  public static GetProjectsResponse getProjects(GetProjectsRequest request) throws ServerException {
    Database db = new Database();
    List<Project> projects = null;

    try {
      db.startTransaction();
      projects = db.getProjectDAO().getAll();
      db.endTransaction(true);
    } catch (DatabaseException e) {
      logger.log(Level.SEVERE, "STACKTRACE: ", e);
      throw new ServerException(e);
    }

    GetProjectsResponse result = new GetProjectsResponse();
    result.setProjects(projects);
    return result;
  }

  /**
   * Gets a sample batch from a project.
   *
   * @param request the request
   * @return the sample batch
   * @throws ServerException the server exception
   */
  public static GetSampleBatchResponse getSampleBatch(GetSampleBatchRequest request)
      throws ServerException {
    Database db = new Database();
    Batch sampleBatch = null;

    try {
      db.startTransaction();
      sampleBatch = db.getBatchDAO().getSampleBatch(request.getProjectId());
      db.endTransaction(true);
    } catch (DatabaseException e) {
      throw new ServerException(e);
    }

    GetSampleBatchResponse result = new GetSampleBatchResponse();
    result.setSampleBatch(sampleBatch);
    return result;
  }

  /**
   * Downloads an incomplete batch from a project. This includes information from batchs, projects,
   * and fields
   *
   * @param request the request
   * @return the download batch response
   * @throws ServerException the server exception
   * @throws DatabaseException the database exception
   */
  public static DownloadBatchResponse downloadBatch(DownloadBatchRequest request)
      throws ServerException, DatabaseException {
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
        logger.log(Level.WARNING, "user already has a batch checked out...");
      }

      db.endTransaction(true);
    } catch (DatabaseException e) {
      throw new ServerException(e);
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
   * Submits values from a batch into the database.
   *
   * @param request the request
   * @return the submit batch response
   */
  public static SubmitBatchResponse submitBatch(SubmitBatchRequest request)
      throws DatabaseException {
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
      logger.log(Level.SEVERE, "STACKTRACE: ", e);
    }
    db.endTransaction(true);
    return result;
  }

  /**
   * Gets the fields for a project.
   *
   * @param request the request
   * @return the fields
   * @throws ServerException the server exception
   */
  public static GetFieldsResponse getFields(GetFieldsRequest request) throws ServerException {
    Database db = new Database();
    int projectId = request.getProjectId();
    List<Field> fields = null;

    try {
      db.startTransaction();
      fields = projectId > 0 ? db.getFieldDAO().getAll(projectId) : db.getFieldDAO().getAll();
      db.endTransaction(true);
    } catch (DatabaseException e) {
      throw new ServerException(e);
    }

    if (fields.isEmpty()) {
      throw new ServerException("Requested projectId (" + projectId + ") does not exist...");
    }

    GetFieldsResponse result = new GetFieldsResponse();
    result.setFields(fields);
    return result;
  }

  /**
   * Searches certain fields for certain values.
   *
   * @param request the request
   * @return the search response
   * @throws ServerException the server exception
   */
  public static SearchResponse search(SearchRequest request) throws ServerException {
    Database db = new Database();
    List<Record> records = null;

    try {
      db.startTransaction();
      records = db.getRecordDAO().search(request.getFieldIds(), request.getSearchQueries());
      db.endTransaction(true);
    } catch (DatabaseException e) {
      throw new ServerException(e);
    }

    SearchResponse result = new SearchResponse();
    result.setFoundRecords(records);
    return result;
  }

  /**
   * Downloads a file from the server.
   *
   * @param request the request
   * @return the download file response
   * @throws ServerException the server exception
   */
  public static DownloadFileResponse downloadFile(DownloadFileRequest request)
      throws ServerException {
    InputStream is;
    byte[] data = null;

    try {
      is = new FileInputStream(request.getUrl());
      data = IOUtils.toByteArray(is);
      is.close();
    } catch (Exception e) {
      throw new ServerException(e);
    }
    return new DownloadFileResponse(data);
  }
}
