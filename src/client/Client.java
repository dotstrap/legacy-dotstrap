package client;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import client.view.LoginDialog;

public class Client {
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        LoginDialog loginDialog = new LoginDialog("Record Indexer");
      }
    });
  }
}
