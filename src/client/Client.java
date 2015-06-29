/**
 * Client.java JRE v1.8.0_45
 *
 * Created by William Myers on Jun 28, 2015. Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

import client.communication.ClientCommunicator;
import client.model.Facade;
import client.util.ClientLogManager;
import client.view.LoginDialog;

public class Client {
  private static final String USAGE_MESSAGE =
      "Usage: java client.Client <host> <port>";

  public static void main(String[] args) {
    ClientLogManager.initLogs();

    if (args.length == 2) {
      try {
        final String host = args[0];
        final int port = Integer.parseInt(args[1]);

        Facade.setClientCommunicator(new ClientCommunicator(host, port));
        // UIManager.put("Table.gridColor", new ColorUIResource(Color.LIGHT_GRAY));
        EventQueue.invokeLater(new Runnable() {
          @Override
          public void run() {
            UIManager.put("Table.gridColor",
                new ColorUIResource(Color.LIGHT_GRAY));
            new LoginDialog();
          }
        });
      } catch (NumberFormatException ex) {
        System.out.println(Client.USAGE_MESSAGE);
      }
    } else {
      System.out.println(Client.USAGE_MESSAGE);
    }
  }
}
