/**
 * Facade.java
 * JRE v1.8.0_45
 * 
 * Created by William Myers on Jun 28, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
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
import shared.communication.ValidateUserRequest;
import shared.communication.ValidateUserResponse;
import shared.model.Batch;
import shared.model.Field;
import shared.model.Project;
import shared.model.User;

public enum Facade {
  INSTANCE;

  private static ClientCommunicator clientComm;

  private static User user;
  private static Batch batch;
  private static Project project;
  private static List<Field> fields;
  private static String urlPrefix;
  private static URL batchUrl;

  public static boolean validateUser(String u, char[] p) {
    try {
      ValidateUserResponse response =
          clientComm.validateUser(new ValidateUserRequest(u, String.valueOf(p)));
      user = response.getUser();
      return response.isValidated();
    } catch (Exception e) {
      ClientLogManager.getLogger().log(Level.FINE, "STACKTRACE: ", e);
      return false;
    }
  }

  public static Project[] getProjects() {
    try {
      GetProjectsResponse response =
          clientComm.getProjects(new GetProjectsRequest(user.getUsername(), user.getPassword()));
      return response.getProjects().toArray(new Project[response.getProjects().size()]);
    } catch (Exception e) {
      ClientLogManager.getLogger().log(Level.FINE, "STACKTRACE: ", e);
      return null;
    }
  }

  public static BufferedImage getSampleBatch(int projId) {
    try {
      GetSampleBatchResponse response =
          clientComm.getSampleBatch(new GetSampleBatchRequest(user.getUsername(), user
              .getPassword(), projId));
      String batchUrl =
          response.getURL().toString() + "/" + response.getSampleBatch().getFilePath();
      return ImageIO.read(new URL(batchUrl));
    } catch (Exception e) {
      ClientLogManager.getLogger().log(Level.FINE, "STACKTRACE: ", e);
      return null;
    }
  }

  public static BufferedImage downloadBatch(int projId) {
    try {
      DownloadBatchResponse response =
          clientComm.downloadBatch(new DownloadBatchRequest(user.getUsername(), user.getPassword(),
              projId));
      Facade.urlPrefix = response.getUrlPrefix().toString() + "/";
      String batchUrl = urlPrefix + response.getBatch().getFilePath();

      URL u = new URL(batchUrl);
      Facade.batchUrl = u;
      Facade.batch = response.getBatch();
      Facade.project = response.getProject();
      Facade.fields = response.getFields();

      return ImageIO.read(u);
    } catch (Exception e) {
      ClientLogManager.getLogger().log(Level.FINE, "STACKTRACE: ", e);
      return null;
    }
  }

  public static InputStream downloadFile(String fileUrl) {
    try {
      DownloadFileResponse response =
          clientComm.downloadFile(new DownloadFileRequest(fileUrl, user.getUsername(), user
              .getPassword()));
      // Facade.fieldHelpUrl = fileUrl;
      return new ByteArrayInputStream(response.getFileBytes());
    } catch (Exception e) {
      ClientLogManager.getLogger().log(Level.FINER, fileUrl);
      ClientLogManager.getLogger().log(Level.FINE, "STACKTRACE: ", e);
      return null;
    }
  }

  public static ClientCommunicator getClientCommunicator() {
    return Facade.clientComm;
  }

  public static void setClientCommunicator(ClientCommunicator clientComm) {
    Facade.clientComm = clientComm;
  }

  public static User getUser() {
    return user;
  }

  public static void setUser(User user) {
    Facade.user = user;
  }

  public static Batch getBatch() {
    return batch;
  }

  public static void setBatch(Batch batch) {
    Facade.batch = batch;
  }

  public static Project getProject() {
    return project;
  }

  public static void setProject(Project project) {
    Facade.project = project;
  }

  public static List<Field> getFields() {
    return fields;
  }

  public static void setFields(List<Field> fields) {
    Facade.fields = fields;
  }

  public static String getUrlPrefix() {
    return urlPrefix;
  }

  public static void setUrlPrefix(String urlPrefix) {
    Facade.urlPrefix = urlPrefix;
  }

  public static URL getBatchUrl() {
    return batchUrl;
  }

  public static void setBatchUrl(URL batchUrl) {
    Facade.batchUrl = batchUrl;
  }

}
