package client.view;

import java.awt.Dimension;
import java.awt.HeadlessException;

import javax.swing.JFrame;

import client.model.BatchState;
import client.model.Facade;
public class IndexerFrame extends JFrame {
  private static final long serialVersionUID = -597878704594774809L;
  //private RecordViewer recordViewer;
  private Facade facade;
  private BatchState bs;

  public IndexerFrame(String title, Facade facade) throws HeadlessException {
    super(title);
    this.facade = facade;
    //facade.setMainFrame(this);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(new Dimension(1024,840));
    setLocationRelativeTo(null);
    //this.add(createMainPanel());
    //this.setJMenuBar(new IndexerFrameMenuBar(facade));
  }

	//public static IndexerFrame getInstance(){
		//return Client.frame;
	//}

}
