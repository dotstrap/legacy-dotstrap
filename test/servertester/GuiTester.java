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
            Logger.getAnonymousLogger().severe("ERROR: unable to load logging propeties file...");
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
