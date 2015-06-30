/**
 * GuiTester.java
 * JRE v1.8.0_45
 * 
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package servertester;

import java.awt.*;

import servertester.controllers.*;
import servertester.views.*;

/**
 * The Class GuiTester.
 */
public class GuiTester {

  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
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
