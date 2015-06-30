/*
 *
 */
package server.facade;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

import server.ServerException;
import server.database.Database;
import server.database.DatabaseException;

import shared.communication.DownloadBatchRequest;
import shared.communication.DownloadBatchResponse;
import shared.communication.DownloadFileRequest;
import shared.communication.DownloadFileResponse;
import shared.communication.GetFieldsRequest;
import shared.communication.GetFieldsResponse;
import shared.communication.GetProjectsRequest;
import shared.communication.GetProjectsResponse;
import shared.communication.GetSampleBatchRequest;
import shared.communication.GetSampleBatchResponse;
import shared.communication.SearchRequest;
import shared.communication.SearchResponse;
import shared.communication.SubmitBatchRequest;
import shared.communication.SubmitBatchResponse;
import shared.communication.ValidateUserRequest;
import shared.communication.ValidateUserResponse;
import shared.model.Batch;
import shared.model.Field;
import shared.model.Project;
import shared.model.Record;
import shared.model.User;

public class ServerFacade {

  private static Logger logger;

  static {
    ServerFacade.logger = Logger.getLogger("server");
  }

  public static void initialize() throws ServerException {
    try {
      Database.initDriver();
    } catch (DatabaseException e) {
      throw new ServerException("ERROR while initializing database: " + e);
    }
  }

  public static ValidateUserResponse validateUser(ValidateUserRequest request)
      throws ServerException {
    Database db = new Database();
    User user = null;
    try {
      db.startTransaction();
      user = db.getUserDAO().read(request.getUsername(), request.getPassword());
      db.endTransaction(true);
    } catch (DatabaseException e) {
      db.endTransaction(false);
      throw new ServerException(
          "while reading from database to validate username: "
              + request.getUsername(),
          e);
    }

    ValidateUserResponse result = new ValidateUserResponse(user);
    return result;
  }

  public static GetProjectsResponse getProjects(GetProjectsRequest request)
      throws ServerException {
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

  public static GetSampleBatchResponse getSampleBatch(
      GetSampleBatchRequest request) throws ServerException {
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
    ServerFacade.logger.log(Level.FINEST,
        "URL=" + result.getURL() + result.getSampleBatch().getFilePath());
    return result;
  }

  public static DownloadBatchResponse downloadBatch(
      DownloadBatchRequest request) throws ServerException, DatabaseException { // TODO
    Database db = new Database();

    StringBuilder batchLogOutput = new StringBuilder();
    DownloadBatchResponse result = new DownloadBatchResponse();
    try {
      db.startTransaction();

      User currUser =
          db.getUserDAO().read(request.getUsername(), request.getPassword());
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
        Record[][] recordValues = db.getRecordDAO().readByBatchId(currBatchId,
            currProject.getRecordsPerBatch(), fields.size());

        result.setBatch(batchToDownload);
        result.setProject(currProject);
        result.setFields(fields);
        result.setRecordValues(recordValues);

        db.endTransaction(true);

        batchLogOutput.append("batchId=").append(currBatchId);
        batchLogOutput.append("\nURL=").append(result.getUrlPrefix())
            .append(result.getBatch().getFilePath());
        ServerFacade.logger.log(Level.FINEST, batchLogOutput.toString());
        return result;
      } else {
        ServerFacade.logger.log(Level.WARNING,
            "user already has a batch checked out...");
      }
    } catch (DatabaseException e) {
      db.endTransaction(false);
      throw new ServerException("while attempting to download batch ", e);
    } catch (Exception e) {
      db.endTransaction(false);
      throw new ServerException(
          "unknown error while attempting to download batch ", e);
    }
    db.endTransaction(false);
    return result; // should have all null values if we made it to here
  }

  private static ArrayList<Integer> getFieldIDs(Batch batch, Database db)
      throws DatabaseException {
    ArrayList<Integer> fields = new ArrayList<Integer>();
    ArrayList<Field> allFields = db.getFieldDAO().getAll();
    for (Field f : allFields) {
      if (f.getProjectId() == batch.getProjectId()) {
        fields.add(f.getFieldId());
      }
    }
    return fields;
  }

  private static void addRecords(String input, Project project, Batch batch,
      Database db, ArrayList<Integer> fields) throws DatabaseException {
    List<String> rows = Arrays.asList(input.split(";| ;", 0));
    int row = 0;
    for (String s : rows) {
      int col = 0;
      List<String> allData = Arrays.asList(s.split(",| ,", 0));
      for (String currData : allData) {
        currData = currData.toUpperCase();
        Record record = new Record(fields.get(col), batch.getBatchId(),
            batch.getFilePath(), currData, row, col);
        db.getRecordDAO().create(record);
        col++;
      }
      row++;
    }
    batch.setStatus(Batch.ACTIVE);
    db.getBatchDAO().update(batch);
  }

  public static SubmitBatchResponse submitBatch(SubmitBatchRequest request)
      throws DatabaseException, ServerException {
    SubmitBatchResponse result = new SubmitBatchResponse();
    Database db = new Database();
    try {
      db.startTransaction();
      User user =
          db.getUserDAO().read(request.getUsername(), request.getPassword());

      if (user.getCurrBatch() == request.getBatchID()) {
        String input = request.getFieldValues();
        Batch batch = db.getBatchDAO().read(request.getBatchID());
        Project project = db.getProjectDAO().read(batch.getProjectId());
        ArrayList<Integer> fields = ServerFacade.getFieldIDs(batch, db);
        if (fields.size() > 0) {

          ServerFacade.addRecords(input, project, batch, db, fields);

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

  public static GetFieldsResponse getFields(GetFieldsRequest request)
      throws ServerException {
    Database db = new Database();
    int projectId = request.getProjectId();
    List<Field> fields = null;

    try {
      db.startTransaction();
      fields = projectId > 0 ? db.getFieldDAO().getAll(projectId)
          : db.getFieldDAO().getAll();
      db.endTransaction(true);
    } catch (DatabaseException e) {
      db.endTransaction(false);
      throw new ServerException(
          "while attempting to read all fields from the database...", e);
    }

    if (fields.isEmpty()) {
      throw new ServerException(
          "Requested projectId (" + projectId + ") does not exist...");
    }

    GetFieldsResponse result = new GetFieldsResponse();
    result.setFields(fields);
    return result;
  }

  public static SearchResponse search(SearchRequest request)
      throws ServerException {
    Database db = new Database();
    SearchResponse result = new SearchResponse();
    ArrayList<Record> records = new ArrayList<Record>();

    try {
      db.startTransaction();

      for (Integer i : request.getFieldIds()) {
        for (String s : request.getSearchQueries()) {
          records.addAll(db.getRecordDAO().search(i, s));
        }
      }

      result.setFoundRecords(records);
    } catch (DatabaseException e) {
      db.endTransaction(false);
      throw new ServerException("while attempting to search database ", e);
    }

    db.endTransaction(true);
    return result;
  }

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
