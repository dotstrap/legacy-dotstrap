/**
 * GuiTester.java
 * JRE v1.8.0_40
 * 
 * Created by William Myers on Mar 24, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package servertester;

import java.awt.EventQueue;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import servertester.controllers.Controller;
import servertester.views.IndexerServerTesterFrame;

// TODO: Auto-generated Javadoc
/**
 * The Class GuiTester.
 */
public class GuiTester {
  /** The logger. */
  private static Logger logger;

  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {
    try {
      FileInputStream is = new FileInputStream("logging.properties");
      LogManager.getLogManager().readConfiguration(is);
      logger = Logger.getLogger("guiTester");
    } catch (IOException e) {

    }

    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        IndexerServerTesterFrame frame = new IndexerServerTesterFrame();
        Controller controller = new Controller();
        frame.setController(controller);
        controller.setView(frame);
        controller.initialize();
        frame.setVisible(true);
      }
    });

  }
}
