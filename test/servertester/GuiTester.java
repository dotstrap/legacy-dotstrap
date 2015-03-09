package servertester;

import java.awt.*;

import servertester.controllers.*;
import servertester.views.*;

public class GuiTester {

	public static void main(String[] args) {
		EventQueue.invokeLater(
				new Runnable() {
					public void run() {
						IndexerServerTesterFrame frame = new IndexerServerTesterFrame();			
						Controller controller = new Controller();
						frame.setController(controller);			
						controller.setView(frame);
						controller.initialize();
						frame.setVisible(true);
					}
				}
		);

	}

}

