/**
 * DownloadFileHandler.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 24, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package server.httphandler.indexerserverhandler;

import java.io.*;
import java.net.HttpURLConnection;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import server.facade.ServerFacade;

import shared.communication.DownloadFileRequest;
import shared.communication.DownloadFileResponse;

/**
 * The Class DownloadFileHandler.
 */
public class DownloadFileHandler implements HttpHandler {
  @Override
  public void handle(HttpExchange exchange) throws IOException {
    String url = new File("").getAbsolutePath() + exchange.getRequestURI().getPath();
    DownloadFileRequest request = new DownloadFileRequest(url);

    try {
      DownloadFileResponse result = ServerFacade.downloadFile(request);

      OutputStream response = exchange.getResponseBody();
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

      response.write(result.getFileBytes());
    } catch (Exception e) {
      throw new IOException("while handling file download: " + e);
    } finally {
      exchange.close();
    }
  }
}
