package client.view;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.*;

import javax.swing.*;

import client.model.BatchState;

public class IndexerFrame extends JFrame {
  private static final long serialVersionUID = -597878704594774809L;
  // private RecordViewer recordViewer;
  private BatchState        bs;

  private JMenuItem         backMenuItem;
  private JMenuItem         forwardMenuItem;
  private JMenuItem         addMenuItem;
  private JMenuItem         removeMenuItem;
  private JMenuItem         exitMenuItem;
  //private UrlPanel          urlPanel;
  //private FavPanel          favPanel;
  //private HtmlPanel         htmlPanel;

  public IndexerFrame(String title) throws HeadlessException {

    addWindowListener(windowAdapter);

    JMenuBar menuBar = new JMenuBar();
    setJMenuBar(menuBar);

    JMenu menu = new JMenu("Menu");
    menu.setMnemonic('c');
    menuBar.add(menu);

    backMenuItem = new JMenuItem("Back", KeyEvent.VK_B);
    backMenuItem.addActionListener(actionListener);
    menu.add(backMenuItem);

    forwardMenuItem = new JMenuItem("Forward", KeyEvent.VK_F);
    forwardMenuItem.addActionListener(actionListener);
    menu.add(forwardMenuItem);

    addMenuItem = new JMenuItem("Add Favorite", KeyEvent.VK_A);
    addMenuItem.addActionListener(actionListener);
    menu.add(addMenuItem);

    removeMenuItem = new JMenuItem("Remove Favorite", KeyEvent.VK_R);
    removeMenuItem.addActionListener(actionListener);
    menu.add(removeMenuItem);

    exitMenuItem = new JMenuItem("Exit", KeyEvent.VK_X);
    exitMenuItem.addActionListener(actionListener);
    menu.add(exitMenuItem);

    //urlPanel = new UrlPanel(urlContext);

    //favPanel = new FavPanel(favContext);

    //htmlPanel = new HtmlPanel(htmlContext);

    //JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, favPanel, htmlPanel);

    JPanel rootPanel = new JPanel(new BorderLayout());
    //rootPanel.add(urlPanel, BorderLayout.NORTH);
    //rootPanel.add(splitPane, BorderLayout.CENTER);

    this.add(rootPanel);
    this.setVisible(true);
  }

  private WindowAdapter windowAdapter = new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
          System.exit(0);
      }
  };

  private ActionListener actionListener = new ActionListener() {

  public void actionPerformed(ActionEvent e) {

    if (e.getSource() == backMenuItem) {
      //htmlPanel.back();
    } else if (e.getSource() == forwardMenuItem) {
      //htmlPanel.forward();
    } else if (e.getSource() == addMenuItem) {
      //favPanel.addFav();
    } else if (e.getSource() == removeMenuItem) {
      //favPanel.removeFav();
    } else if (e.getSource() == exitMenuItem) {
      System.exit(0);
    }
  }
};
  // public static IndexerFrame getInstance(){
  // return Client.frame;
  // }

}
