package client;

import java.awt.EventQueue;

import client.util.ClientLogManager;
import client.view.LoginDialog;
import client.view.IndexerFrame;

public class Client {

  /**
   * Entry point for the Indexer Server program.
   *
   * @param args the port to run the indexer server on
   */
  public static void main(String[] args) {
    ClientLogManager.initLogs();

    EventQueue.invokeLater(new Runnable() {
      public void run() {
        new LoginDialog("Record Indexer");
      }
    });
  }
}
