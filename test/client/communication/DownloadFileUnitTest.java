/**
 * DownloadFileUnitTest.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 24, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.communication;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.*;

import server.ServerException;
import server.database.DatabaseException;

import shared.communication.DownloadFileRequest;
import shared.communication.DownloadFileResponse;

/**
 * The Class DownloadFileUnitTest.
 */
public class DownloadFileUnitTest {
  /** The logger used throughout the project. */
  private static Logger logger;
  static {
    logger = Logger.getLogger("server");
  }

  private ClientCommunicator clientComm;

  /**
   * Sets the up.
   *
   * @throws Exception the exception
   */
  @Before
  public void setUp() throws Exception {
    clientComm = new ClientCommunicator();
  }

  /**
   * Tear down.
   *
   * @throws Exception the exception
   */
  @After
  public void tearDown() throws Exception {
    clientComm = null;
  }

  /**
   * Test download valid file1.
   *
   * @throws DatabaseException the database exception
   */
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
    } catch (ServerException e) {
      //logger.log(Level.SEVERE, "STACKTRACE: ", e);
    }
  }

  /**
   * Test download valid file2.
   *
   * @throws DatabaseException the database exception
   */
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
    } catch (ServerException e) {
      //logger.log(Level.SEVERE, "STACKTRACE: ", e);
    }
  }

  /**
   * Test download valid file3.
   *
   * @throws DatabaseException the database exception
   */
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
    } catch (ServerException e) {
      logger.log(Level.SEVERE, "STACKTRACE: ", e);
    }
  }

  /**
   * Test download invalid file.
   *
   * @throws DatabaseException the database exception
   */
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
    } catch (ServerException e) {
      //logger.log(Level.SEVERE, "STACKTRACE: ", e);
    }
  }

  /**
   * Test download null file.
   *
   * @throws DatabaseException the database exception
   */
  @Test
  public void testDownloadNullFile() throws DatabaseException {
    try {
      DownloadFileResponse result = clientComm.downloadFile(new DownloadFileRequest(""));
      boolean didDownload = false;
      if (result.getFileBytes().length > 0) {
        didDownload = true;
      }
      assertFalse(didDownload);
    } catch (ServerException e) {
      //logger.log(Level.SEVERE, "STACKTRACE: ", e);
    }
  }
}
