package client.view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import client.model.BatchState;
import client.view.indexer.BatchPanel;

public class IndexerFrame extends JFrame {
  private static final long serialVersionUID = -597878704594774809L;
  // private RecordViewer recordViewer;
  private BatchState        bs;

  private JMenuItem         downloadBatchMenuItem;
  private JMenuItem         logoutMenuItem;
  private JMenuItem         exitMenuItem;

  private JToolBar          toolBar;
  private List<JButton>     toolBarButtons;
  private JButton           zoomInButton;
  private JButton           zoomOutButton;
  private JButton           invertImageButton;
  private JButton           toggleHighlightsButton;
  private JButton           saveButton;
  private JButton           submitButton;

  private BatchPanel batchPanel;
  private JPanel bottomPanel;

  // private UrlPanel urlPanel;
  // private FavPanel favPanel;
  // private HtmlPanel htmlPanel;

  public IndexerFrame(String title) throws HeadlessException {
    // Initalize
    addWindowListener(windowAdapter);
    setSize(new Dimension(900, 700));
    // setSize(new Dimension(1024,840));

    setJMenuBar(createMenu());
    this.add(createToolBar(), BorderLayout.NORTH);

    batchPanel = new BatchPanel();
    bottomPanel = new JPanel();

    JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, batchPanel, bottomPanel);

    JPanel rootPanel = new JPanel(new BorderLayout());
    // rootPanel.add(urlPanel, BorderLayout.NORTH);
     rootPanel.add(splitPane, BorderLayout.CENTER);

    this.add(rootPanel);
    this.setVisible(true);
  }

  private JMenuBar createMenu() {
    JMenuBar menuBar = new JMenuBar();

    JMenu menu = new JMenu("File");
    menu.setMnemonic('c');
    menuBar.add(menu);

    // TODO: only show this when user is NOT currently indexing a batch
    downloadBatchMenuItem = new JMenuItem("Download Batch", KeyEvent.VK_D);
    downloadBatchMenuItem.addActionListener(menuListener);
    menu.add(downloadBatchMenuItem);

    logoutMenuItem = new JMenuItem("Logout", KeyEvent.VK_L);
    logoutMenuItem.addActionListener(menuListener);
    menu.add(logoutMenuItem);

    exitMenuItem = new JMenuItem("Exit", KeyEvent.VK_X);
    exitMenuItem.addActionListener(menuListener);
    menu.add(exitMenuItem);

    return menuBar;
  }

  private JToolBar createToolBar() {
    JToolBar toolBar = new JToolBar();

    zoomInButton = new JButton("Zoom In");
    zoomOutButton = new JButton("Zoom Out");
    invertImageButton = new JButton("Invert Image");
    toggleHighlightsButton = new JButton("Toggle Highlights");
    saveButton = new JButton("Save");
    submitButton = new JButton("Submit");

    zoomInButton.addActionListener(toolBarListener);
    zoomOutButton.addActionListener(toolBarListener);
    invertImageButton.addActionListener(toolBarListener);
    toggleHighlightsButton.addActionListener(toolBarListener);
    saveButton.addActionListener(toolBarListener);
    submitButton.addActionListener(toolBarListener);

    toolBarButtons = new ArrayList<JButton>();
    toolBarButtons.add(zoomInButton);
    toolBarButtons.add(zoomOutButton);
    toolBarButtons.add(invertImageButton);
    toolBarButtons.add(toggleHighlightsButton);
    toolBarButtons.add(saveButton);
    toolBarButtons.add(submitButton);

    toolBar = new JToolBar();
    for (JButton button : toolBarButtons) {
      toolBar.add(button);
    }

    //toolBar.addSeparator();
    toolBar.setFloatable(false);
    toolBar.setRollover(true);
    toolBar.setEnabled(false);

    return toolBar;
  }

  private void processDownloadBatch() {
    // TODO: download batch
  }

  private void processLogout() {
    // TODO: save work
  }

  private void processExit() {
    // TODO: save work
    System.exit(0);
  }

  private ActionListener toolBarListener = new ActionListener() {//@formatter:off
    // TODO: implement toolbar and make it look better...!
    public void actionPerformed(ActionEvent e) {
      //if (e.getSource() == downloadBatchMenuItem) {
        //processDownloadBatch();
      //} else if (e.getSource() == logoutMenuItem) {
        //processLogout();
      //} else if (e.getSource() == exitMenuItem) {
        //processExit();
      //}
    }
  };

  private ActionListener menuListener = new ActionListener() {//@formatter:off
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == downloadBatchMenuItem) {
        processDownloadBatch();
      } else if (e.getSource() == logoutMenuItem) {
        processLogout();
      } else if (e.getSource() == exitMenuItem) {
        processExit();
      }
    }
  };

  private WindowAdapter windowAdapter = new WindowAdapter() {
    public void windowClosing(WindowEvent e) {
        processExit();
    }
  };//@formatter:on
}
