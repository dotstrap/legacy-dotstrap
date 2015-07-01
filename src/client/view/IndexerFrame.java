/**
 * IndexerFrame.java JRE v1.8.0_45
 *
 * Created by William Myers on Jun 28, 2015. Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import client.model.Facade;
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

  private ActionListener toolBarListener = new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == zoomInButton) {
        BatchState.notifyDidZoom(1.0);
      } else if (e.getSource() == zoomOutButton) {
        BatchState.notifyDidZoom(-1.0);
      } else if (e.getSource() == invertImageButton) {
        BatchState.notifyToggleInvert();
      } else if (e.getSource() == toggleHighlightsButton) {
        BatchState.notifyToggleHighlight();
      } else if (e.getSource() == saveButton) {
        // TODO
      } else if (e.getSource() == submitButton) {
        processSubmit();
      }
    }
  };

  private ActionListener menuListener = new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == downloadBatchMenuItem) {
        new DownloadBatchDialog(IndexerFrame.this, batchViewer);
      } else if (e.getSource() == logoutMenuItem) {
        dispose();
        new LoginDialog();
      } else if (e.getSource() == exitMenuItem) {
        processExit();
      }
    }
  };

  private WindowAdapter windowAdapter = new WindowAdapter() {
    @Override
    public void windowClosing(WindowEvent e) {
      processExit();
    }
  };

  public IndexerFrame(String title) {
    this.intitialize();

    BatchState.addObserver(this);
  }

  @Override
  public void cellWasSelected(int x, int y) {}

  @Override
  public void dataWasInput(String value, int record, Field field, boolean shouldResetIsIncorrect) {}

  @Override
  public void didChangeOrigin(int x, int y) {}

  @Override
  public void didDownload(BufferedImage b) {
    toolBar.setEnabled(true);
    toolBar.setRollover(true);
    for (JButton button : toolBarButtons) {
      button.setEnabled(true);
    }
    downloadBatchMenuItem.setEnabled(false);
  }

  @Override
  public void didHighlight() {}

  @Override
  public void didSubmit(Batch b) {
    this.clear();
  }

  @Override
  public void didToggleHighlight() {}

  @Override
  public void didToggleInvert() {}

  @Override
  public void didZoom(double zoomAmt) {}

  @Override
  public void fieldWasSelected(int record, Field field) {}

  public void intitialize() {
    setSize(new Dimension(1000, 800));

    setJMenuBar(initMenu());
    this.add(initToolBar(), BorderLayout.NORTH);

    JPanel rootPanel = new JPanel(new BorderLayout());
    rootPanel.add(initSplitPane());

    this.add(rootPanel);
    this.setVisible(true);

    this.addWindowListener(windowAdapter);
  }

  /*
   * (non-Javadoc)
   * 
   * @see client.model.BatchState.Observer#spellPopupWasOpened(java.lang.String, int,
   * shared.model.Field)
   */
  @Override
  public void spellPopupWasOpened(String value, int record, Field field,
      List<String> suggestions) {
    // TODO Auto-generated method stub

  }

  @Override
  public void wordWasMisspelled(String value, int record, Field field) {}

  private void clear() {
    // this.removeAll();
    // this.revalidate();
    // this.repaint();
    // batchViewer = null;
    // downloadBatchMenuItem = null;
    // logoutMenuItem = null;
    // exitMenuItem = null;
    // toolBar = null;
    // toolBarButtons = null;
    // zoomInButton = null;
    // zoomOutButton = null;
    // invertImageButton = null;
    // toggleHighlightsButton = null;
    // saveButton = null;
    // submitButton = null;
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

  private JSplitPane initSplitPane() {
    JTabbedPane entryTabs = new JTabbedPane();
    entryTabs.add("Table Entry", new TableEntryTab());
    entryTabs.add("Form Entry", new FormEntryTab());

    JTabbedPane navTabs = new JTabbedPane();
    navTabs.add("Field Help", new FieldHelpTab());
    navTabs.add("Navigation", new ImageNavigationTab());

    JSplitPane hSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    hSplit.setResizeWeight(0.5);
    hSplit.add(entryTabs);
    hSplit.add(navTabs);

    batchViewer = new BatchComponent();
    JSplitPane vSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    vSplit.setResizeWeight(0.5);
    vSplit.add(batchViewer);
    vSplit.add(hSplit);
    // make the batchViewer window 500 pixels tall
    vSplit.setDividerLocation(500 + vSplit.getInsets().bottom);

    return vSplit;
  }

  // TODO
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
      button.setEnabled(false);
    }

    // toolBar.addSeparator();
    toolBar.setFloatable(false);
    toolBar.setRollover(false);
    toolBar.setEnabled(false);

    return toolBar;
  }

  private void processExit() {
    // TODO: save work
    System.exit(0);
  }

  private void processSubmit() {
    Facade.submitBatch();
    BatchState.notifyDidSubmit(Facade.getBatch());
    Facade.setBatch(null);
  }

}
