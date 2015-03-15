/**
 * GuiTester.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 14, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package servertester;

import java.awt.*;

import servertester.controllers.*;
import servertester.views.*;

// TODO: Auto-generated Javadoc
/**
 * The Class GuiTester.
 */
public class GuiTester {

    /**
     * The main method.
     *
     * @param args
     *            the arguments
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
