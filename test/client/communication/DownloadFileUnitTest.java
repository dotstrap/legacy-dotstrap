/**
 * DownloadFileUnitTest.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 23, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.communication;

import static org.junit.Assert.assertTrue;

import java.util.logging.Logger;

import org.junit.*;

import client.ClientException;
import client.ClientUnitTests;

import server.database.DatabaseException;

import shared.communication.DownloadFileRequest;
import shared.communication.DownloadFileResponse;

public class DownloadFileUnitTest {
    // @formatter:off
    /** The logger used throughout the project. */
    private static Logger logger;
    static {
        logger = Logger.getLogger(ClientUnitTests.LOG_NAME);
    }
// @formatter:on

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
    public void testDownloadFile() throws DatabaseException {
        logger.entering("shared.communication.DownloadFileUnitTest", "testDownloadFile");

        try {
            DownloadFileResponse result =
                            clientComm.downloadFile(new DownloadFileRequest(
                                            "Records/images/1890_image0.png"));
            boolean gotFile = false;
            if (result.getFileBytes().length > 0) {
                gotFile = true;
            }
            assertTrue(gotFile);
        } catch (ClientException e) {
            e.printStackTrace();
        }

        logger.exiting("shared.communication.DownloadFileUnitTest", "testDownloadFile");
    }
}
