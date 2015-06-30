/**
 * Facade.java
 * JRE v1.8.0_45
 * 
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import client.communication.ClientCommunicator;
import client.util.ClientLogManager;

import shared.communication.DownloadBatchRequest;
import shared.communication.DownloadBatchResponse;
import shared.communication.DownloadFileRequest;
import shared.communication.DownloadFileResponse;
import shared.communication.GetProjectsRequest;
import shared.communication.GetProjectsResponse;
import shared.communication.GetSampleBatchRequest;
import shared.communication.GetSampleBatchResponse;
import shared.communication.SubmitBatchRequest;
import shared.communication.SubmitBatchResponse;
import shared.communication.ValidateUserRequest;
import shared.communication.ValidateUserResponse;
import shared.model.Batch;
import shared.model.Field;
import shared.model.Project;
import shared.model.Record;
import shared.model.User;

/**
 * The Enum Facade.
 */
public enum Facade {

  INSTANCE;

  private static ClientCommunicator clientComm;

  private static User user;

  private static Batch batch;

  private static Project project;

  private static List<Field> fields;

  private static Record[][] recordValues;

  private static String urlPrefix;

  private static URL batchUrl;

  /**
   * Download batch.
   *
   * @param projId the proj id
   * @return the buffered image
   */
  public static BufferedImage downloadBatch(int projId) {
    String batchUrl = "";
    try {
      DownloadBatchResponse response =
          Facade.clientComm.downloadBatch(new DownloadBatchRequest(
              Facade.user.getUsername(), Facade.user.getPassword(), projId));
      Facade.urlPrefix = response.getUrlPrefix().toString() + "/";
      batchUrl = Facade.urlPrefix + response.getBatch().getFilePath();
      URL url = new URL(batchUrl);

      Facade.batchUrl = url;
      Facade.batch = response.getBatch();

      Facade.project = response.getProject();

      Facade.fields = new ArrayList<Field>();
      Facade.fields = response.getFields();

      Facade.recordValues = response.getRecordValues();

      ClientLogManager.getLogger().log(Level.FINEST, "SUCESS: " + batchUrl);
      return ImageIO.read(url);
    } catch (Exception e) {
      StringBuilder stack = new StringBuilder("URL: ").append(batchUrl);
      stack.append("\nSTACKTRACE: ").append(e);
      ClientLogManager.getLogger().log(Level.SEVERE, stack.toString());
      return null;
    }
  }

  /**
   * Download file.
   *
   * @param fileUrl the file url
   * @return the input stream
   */
  public static InputStream downloadFile(String fileUrl) {
    try {
      DownloadFileResponse response =
          Facade.clientComm.downloadFile(new DownloadFileRequest(fileUrl,
              Facade.user.getUsername(), Facade.user.getPassword()));

      ClientLogManager.getLogger().log(Level.FINEST, "SUCESS: " + fileUrl);
      return new ByteArrayInputStream(response.getFileBytes());
    } catch (Exception e) {
      StringBuilder msg = new StringBuilder("URL: ").append(fileUrl);
      ClientLogManager.getLogger().log(Level.SEVERE, msg.toString(), e);
      return null;
    }
  }

  public static Batch getBatch() {
    return Facade.batch;
  }

  public static URL getBatchUrl() {
    return Facade.batchUrl;
  }

  public static ClientCommunicator getClientCommunicator() {
    return Facade.clientComm;
  }

  public static List<Field> getFields() {
    return Facade.fields;
  }

  public static Project getProject() {
    return Facade.project;
  }

  public static Project[] getProjects() {
    try {
      GetProjectsResponse response =
          Facade.clientComm.getProjects(new GetProjectsRequest(
              Facade.user.getUsername(), Facade.user.getPassword()));

      ClientLogManager.getLogger().log(Level.FINEST,
          "SUCESS: " + response.getProjects().toString());
      return response.getProjects()
          .toArray(new Project[response.getProjects().size()]);
    } catch (Exception e) {
      ClientLogManager.getLogger().log(Level.SEVERE, "STACKTRACE: ", e);
      return null;
    }
  }

  public static Record[][] getRecordValues() {
    return Facade.recordValues;
  }

  /**
   * Gets the sample batch.
   *
   * @param projId the proj id
   * @return the sample batch
   */
  public static BufferedImage getSampleBatch(int projId) {
    try {
      GetSampleBatchResponse response =
          Facade.clientComm.getSampleBatch(new GetSampleBatchRequest(
              Facade.user.getUsername(), Facade.user.getPassword(), projId));

      String sampleBatchUrl = response.getURL().toString() + "/"
          + response.getSampleBatch().getFilePath();

      ClientLogManager.getLogger().log(Level.FINEST,
          "SUCESS: " + sampleBatchUrl);
      return ImageIO.read(new URL(sampleBatchUrl));
    } catch (Exception e) {
      ClientLogManager.getLogger().log(Level.SEVERE, "STACKTRACE: ", e);
      return null;
    }
  }

  public static String getUrlPrefix() {
    return Facade.urlPrefix;
  }

  public static User getUser() {
    return Facade.user;
  }

  /**
   * Records to string.
   *
   * @return the string
   */
  public static String recordsToString() {
    StringBuilder submitData = new StringBuilder();

    for (Record[] records : recordValues) {
      for (Record r : records) {
        String val = r != null ? r.getData() : "";
        submitData.append(val).append(",");
      }
      submitData.append(";");
    }
    return submitData.toString();
  }

  public static void setBatch(Batch batch) {
    Facade.batch = batch;
  }

  public static void setBatchUrl(URL batchUrl) {
    Facade.batchUrl = batchUrl;
  }

  public static void setClientCommunicator(ClientCommunicator clientComm) {
    Facade.clientComm = clientComm;
  }

  public static void setFields(ArrayList<Field> fields) {
    Facade.fields = fields;
  }

  public static void setProject(Project project) {
    Facade.project = project;
  }

  public static void setRecords(Record[][] records) {
    Facade.recordValues = records;
  }

  public static void setUrlPrefix(String urlPrefix) {
    Facade.urlPrefix = urlPrefix;
  }

  public static void setUser(User user) {
    Facade.user = user;
  }

  /**
   * Submit batch.
   *
   * @return true, if successful
   */
  public static boolean submitBatch() {
    try {
      SubmitBatchResponse response =
          Facade.clientComm.submitBatch(new SubmitBatchRequest(
              Facade.user.getUsername(), Facade.user.getPassword(),
              Facade.batch.getBatchId(), Facade.recordsToString()));

      ClientLogManager.getLogger().log(Level.FINE,
          "SUCESS: " + Facade.recordsToString());
      return response.isSuccess();
    } catch (Exception e) {
      StringBuilder msg =
          new StringBuilder("RecordValues: ").append(Facade.recordsToString());
      ClientLogManager.getLogger().log(Level.SEVERE, msg.toString(), e);
      return false;
    }
  }

  /**
   * Validate user.
   *
   * @param u the u
   * @param p the p
   * @return true, if successful
   */
  public static boolean validateUser(String u, char[] p) {
    try {
      ValidateUserResponse response = Facade.clientComm
          .validateUser(new ValidateUserRequest(u, String.valueOf(p)));
      Facade.user = response.getUser();

      ClientLogManager.getLogger().log(Level.FINEST,
          "SUCESS: " + u + " " + String.valueOf(p));
      return response.isValidated();
    } catch (Exception e) {
      ClientLogManager.getLogger().log(Level.SEVERE, "STACKTRACE: ", e);
      return false;
    }
  }

  // public String toStringOLD() {
  // StringBuilder data = new StringBuilder();
  // for (int row = 0; row < getRowCount(); row++) {
  // for (int column = 1; column < getColumnCount(); column++) {
  // String value = (String) getValueAt(row, column);
  // if (value == null)
  // value = "";
  // if (column == getColumnCount() - 1) {
  // data.append(value + ";");
  // } else
  // data.append(value + ",");
  // }
  // }
  // return data.toString();
  // }
}
