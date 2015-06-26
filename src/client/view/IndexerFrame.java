package client.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import client.model.BatchState;
import client.view.indexerframe.BatchComponent;
import client.view.indexerframe.DownloadBatchDialog;
import client.view.indexerframe.FieldHelpTab;
import client.view.indexerframe.FormEntryTab;
import client.view.indexerframe.ImageNavigationTab;
import client.view.indexerframe.TableEntryTab;

import shared.model.Batch;
import shared.model.Field;

@SuppressWarnings("serial")
public class IndexerFrame extends JFrame implements BatchState.Observer {
  private BatchComponent batchViewer;

  private JMenuItem downloadBatchMenuItem;
  private JMenuItem logoutMenuItem;
  private JMenuItem exitMenuItem;

  private JToolBar toolBar;
  private List<JButton> toolBarButtons;
  private JButton zoomInButton;
  private JButton zoomOutButton;
  private JButton invertImageButton;
  private JButton toggleHighlightsButton;
  private JButton saveButton;
  private JButton submitButton;

  private static final double ZOOM_SCALE_FACTOR = 0.04;

  public IndexerFrame(String title) throws HeadlessException {
    // Initalize
    addWindowListener(windowAdapter);
    setSize(new Dimension(1000, 800));

    setJMenuBar(initMenu());
    this.add(initToolBar(), BorderLayout.NORTH);

    JPanel rootPanel = new JPanel(new BorderLayout());
    rootPanel.add(initSplitPane());

    this.add(rootPanel);
    this.setVisible(true);

    BatchState.addObserver(this);
  }

  private JMenuBar initMenu() {
    JMenuBar menuBar = new JMenuBar();

    JMenu menu = new JMenu("File");
    menu.setMnemonic('c');
    menuBar.add(menu);

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

  // TODO: clean this code up
  private JToolBar initToolBar() {
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

    // toolBar.addSeparator();
    toolBar.setFloatable(false);
    toolBar.setRollover(false);
    toolBar.setEnabled(false);

    return toolBar;
  }

  private JSplitPane initSplitPane() {
    // data entry components
    JTabbedPane entryTabs = new JTabbedPane();
    entryTabs.add("Table Entry", new TableEntryTab());
    entryTabs.add("Form Entry", new FormEntryTab());

    // field help and image navigation
    JTabbedPane navTabs = new JTabbedPane();
    navTabs.add("Field Help", new FieldHelpTab());
    navTabs.add("Navigation", new ImageNavigationTab());

    // horizontal split
    JSplitPane hSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    hSplit.setResizeWeight(0.5);
    hSplit.add(entryTabs);
    hSplit.add(navTabs);

    // vertical split
    batchViewer = new BatchComponent();
    JSplitPane vSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    vSplit.setResizeWeight(0.5);
    vSplit.add(batchViewer);
    vSplit.add(hSplit);

    return vSplit;
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
      if (e.getSource() == zoomInButton) {
        BatchState.notifyDidZoom(1.0);
      } else if (e.getSource() == zoomOutButton) {
        BatchState.notifyDidZoom(-1.0);
      } else if (e.getSource() == invertImageButton) {
        BatchState.notifyToggleInvert();
      }
    }
  };

  private ActionListener menuListener = new ActionListener() {
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == downloadBatchMenuItem) {
        new DownloadBatchDialog(IndexerFrame.this, batchViewer);
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

  /* (non-Javadoc)
   * @see client.model.BatchState.Observer#cellWasSelected(int, int)
   */
  @Override
  public void cellWasSelected(int x, int y) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see client.model.BatchState.Observer#didHighlight(boolean)
   */
  @Override
  public void didHighlight(boolean hasHighlight) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see client.model.BatchState.Observer#didZoom(double)
   */
  @Override
  public void didZoom(double zoomAmt) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see client.model.BatchState.Observer#didInvert(boolean)
   */
  @Override
  public void didToggleInvert() {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see client.model.BatchState.Observer#dataWasInput(java.lang.String, int, int)
   */
  @Override
  public void dataWasInput(String data, int x, int y) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see client.model.BatchState.Observer#didChangeValue(int, shared.model.Field, java.lang.String)
   */
  @Override
  public void didChangeValue(int record, Field field, String value) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see client.model.BatchState.Observer#didDownload(shared.model.Batch)
   */
  @Override
  public void didDownload(BufferedImage b) {
    toolBar.setEnabled(true);
    toolBar.setRollover(true);
    downloadBatchMenuItem.setEnabled(false);
  }

  /* (non-Javadoc)
   * @see client.model.BatchState.Observer#didSubmit(shared.model.Batch)
   */
  @Override
  public void didSubmit(Batch b) {
    downloadBatchMenuItem.setEnabled(true);
  }
}
