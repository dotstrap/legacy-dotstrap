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
   * Initialize the database driver.
   *
   * @throws ServerException the server exception
   */
  public static void initialize() throws ServerException {
    try {
      Database.initDriver();
    } catch (DatabaseException e) {
      throw new ServerException("ERROR while initializing database: " + e);
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
    System.out.println("=========BEFORE db read in validate user in facade\n");
    System.out.println("USERNAME: " + request.getUsername() + "PASS: " + request.getPassword());
    try {
      db.startTransaction();
      user = db.getUserDAO().read(request.getUsername(), request.getPassword());
      db.endTransaction(true);
    } catch (DatabaseException e) {
      db.endTransaction(false);
      throw new ServerException("while reading from database to validate username: "
          + request.getUsername(), e);
    }

    System.out.println("=========AFTER db read in validate user in facade\n");
    if (user != null) {
      System.out.println(user.toString());
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
      db.endTransaction(false);
      throw new ServerException("while attempting to get all projects ", e);
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
      db.endTransaction(false);
      throw new ServerException("while attempting to get a sample batch ", e);
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
      throws ServerException, DatabaseException { // TODO: clean this method up
    Database db = new Database();

    try {
      db.startTransaction();

      DownloadBatchResponse result = new DownloadBatchResponse();

      User currUser = db.getUserDAO().read(request.getUsername(), request.getPassword());
      if (currUser.getCurrBatch() <= 0) {
        int projectId = request.getProjectId();
        Batch batchToDownload = db.getBatchDAO().getIncompleteBatch(projectId);
        int currBatchId = batchToDownload.getBatchId();

        // update the user and batch models
        int currUserId = currUser.getUserId();
        currUser.setCurrBatch(currBatchId);
        batchToDownload.setCurrUserId(currUserId);

        // update the user and batch in the db
        db.getUserDAO().updateCurrentBatchId(currUserId, currBatchId);
        db.getBatchDAO().assignBatchToUser(currBatchId, currUserId);

        Project currProject = db.getProjectDAO().read(projectId);
        List<Field> fields = db.getFieldDAO().getAll(projectId);

        System.out.println("PRINTING FIELDS IN SUBMIT BATCH: \n");
        for (Field f : fields) {
          System.out.println(f.toString());
        }

        result.setBatch(batchToDownload);
        result.setProject(currProject);
        result.setFields(fields);

        db.endTransaction(true);
        return result;
      } else {
        db.endTransaction(false);
        logger.log(Level.WARNING, "user already has a batch checked out...");
        return result; // should have all null values
      }
    } catch (DatabaseException e) {
      db.endTransaction(false);
      throw new ServerException("while attempting to download batch ", e);
    }
  }

  private static ArrayList<Integer> getFieldIDs(Batch batch, Database db) throws DatabaseException {
    ArrayList<Integer> fields = new ArrayList<Integer>();
    ArrayList<Field> allFields = db.getFieldDAO().getAll();
    for (Field f : allFields) {
      if (f.getProjectId() == batch.getProjectId()) {
        fields.add(f.getFieldId());
      }
    }
    return fields;
  }

  private static void addRecords(String input, Project project, Batch batch, Database db,
      ArrayList<Integer> fields) throws DatabaseException {
    List<String> rows = Arrays.asList(input.split(";| ;", 0));
    int row = 1;
    for (String s : rows) {
      int i = 0;
      List<String> values = Arrays.asList(s.split(",| ,", 0));
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
   * @throws ServerException
   */
  public static SubmitBatchResponse submitBatch(SubmitBatchRequest request)
      throws DatabaseException, ServerException {
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
        if (fields.size() > 0) {

          addRecords(input, project, batch, db, fields);

          user.setCurrBatch(-1);
          int count = (user.getRecordCount() + project.getRecordsPerBatch());
          user.setRecordCount(count);
          db.getUserDAO().update(user);

          result.setSuccess(true);
          db.endTransaction(true);
          return result;
        }
      }
    } catch (Exception e) {
      db.endTransaction(false);
      throw new ServerException("while attempting to submit batch ", e);
    }
    result.setSuccess(false);
    db.endTransaction(false);
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
      db.endTransaction(false);
      throw new ServerException("while attempting to read all fields from the database...", e);
    }

    // if (fields.isEmpty()) {
    // throw new ServerException("Requested projectId (" + projectId + ") does not exist...");
    // }

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
    SearchResponse result = new SearchResponse();
    ArrayList<Record> records = new ArrayList<Record>();
    ArrayList<String> links = new ArrayList<String>();

    try {
      db.startTransaction();

      for (Integer i : request.getFieldIds()) {
        for (String s : request.getSearchQueries()) {
          records.addAll(db.getRecordDAO().search(i, s));
        }
      }
      for (Record r : records) {
        links.add(db.getBatchDAO().read(r.getBatchId()).getFilePath());
      }
      result.setUrls(links);
      result.setFoundRecords(records);
    } catch (DatabaseException e) {
      db.endTransaction(false);
      throw new ServerException("while attempting to search database ", e);
    }

    db.endTransaction(true);
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
      throw new ServerException("while attempting to download file ", e);
    }
    return new DownloadFileResponse(data);
  }
}
