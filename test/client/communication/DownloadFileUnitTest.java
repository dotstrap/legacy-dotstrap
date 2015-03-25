/**
 * DownloadFileUnitTest.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 23, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.communication;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.*;

import client.ClientException;

import server.database.DatabaseException;

import shared.communication.DownloadFileRequest;
import shared.communication.DownloadFileResponse;

public class DownloadFileUnitTest {
  /** The logger used throughout the project. */
  private static Logger      logger;
  static {
    logger = Logger.getLogger(ClientCommunicator.LOG_NAME);
  }

  private ClientCommunicator clientComm;

  @Before
  public void setUp() throws Exception {
    clientComm = new ClientCommunicator();
  }

  @After
  public void tearDown() throws Exception {
    clientComm = null;
  }

  @Test
  public void testDownloadValidFile1() throws DatabaseException {
    try {
      DownloadFileResponse result =
          clientComm.downloadFile(new DownloadFileRequest("Records/images/1890_image0.png"));
      boolean didDownload = false;
      if (result.getFileBytes().length > 0) {
        didDownload = true;
      }
      assertTrue(didDownload);
    } catch (ClientException e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
    }
  }

  @Test
  public void testDownloadValidFile2() throws DatabaseException {
    try {
      DownloadFileResponse result =
          clientComm.downloadFile(new DownloadFileRequest("Records/images/1890_image19.png"));
      boolean didDownload = false;
      if (result.getFileBytes().length > 0) {
        didDownload = true;
      }
      assertTrue(didDownload);
    } catch (ClientException e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
    }
  }

  @Test
  public void testDownloadValidFile3() throws DatabaseException {
    try {
      DownloadFileResponse result =
          clientComm.downloadFile(new DownloadFileRequest("Records/images/draft_image9.png"));
      boolean didDownload = false;
      if (result.getFileBytes().length > 0) {
        didDownload = true;
      }
      assertTrue(didDownload);
    } catch (ClientException e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
    }
  }

  @Test
  public void testDownloadInvalidFile() throws DatabaseException {
    try {
      DownloadFileResponse result =
          clientComm.downloadFile(new DownloadFileRequest("Records/images/INVALID.png"));
      boolean didDownload = false;
      if (result.getFileBytes().length > 0) {
        didDownload = true;
      }
      assertFalse(didDownload);
    } catch (ClientException e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
    }
  }

  @Test
  public void testDownloadNullFile() throws DatabaseException {
    try {
      DownloadFileResponse result =
          clientComm.downloadFile(new DownloadFileRequest(""));
      boolean didDownload = false;
      if (result.getFileBytes().length > 0) {
        didDownload = true;
      }
      assertFalse(didDownload);
    } catch (ClientException e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
    }
  }
}
