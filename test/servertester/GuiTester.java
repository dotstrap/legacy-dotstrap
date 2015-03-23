/**
 * GuiTester.java
 * JRE v1.8.0_40
 * 
 * Created by William Myers on Mar 22, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package servertester;

import java.awt.*;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.*;
import servertester.controllers.*;
import servertester.views.*;

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
            final FileInputStream is = new FileInputStream("logging.properties");
            LogManager.getLogManager().readConfiguration(is);
            logger = Logger.getLogger("guiTester");
        } catch (final IOException e) {
            Logger.getAnonymousLogger().severe("ERROR: unable to load logging properties file...");
            Logger.getAnonymousLogger().severe(e.getMessage());
        }

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
