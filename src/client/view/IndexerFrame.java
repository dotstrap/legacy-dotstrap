/**
 * IndexerFrame.java
 * JRE v1.8.0_45
 * 
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
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
import java.util.Set;

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

/**
 * The Class IndexerFrame.
 */
@SuppressWarnings("serial")
public class IndexerFrame extends JFrame implements BatchState.Observer {
  private BatchComponent batchViewer;

  private JMenuItem downloadBatchMenuItem;
  private JMenuItem exitMenuItem;
  private JButton invertImageButton;
  private JMenuItem logoutMenuItem;

  private IndexerMenuListener menuListener;
  private IndexerToolBarListener toolBarListener;
  private IndexerWindowAdapter windowAdapter;

  private JToolBar toolBar;
  private List<JButton> toolBarButtons;
  private JButton saveButton;
  private JButton submitButton;
  private JButton toggleHighlightsButton;
  private JButton zoomInButton;
  private JButton zoomOutButton;

  /**
   * Instantiates a new indexer frame.
   *
   * @param title the title
   */
  public IndexerFrame(String title) {
    intitialize();

    BatchState.addObserver(this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see client.model.BatchState.Observer#cellWasSelected(int, int)
   */
  @Override
  public void cellWasSelected(int x, int y) {}

  /*
   * (non-Javadoc)
   * 
   * @see client.model.BatchState.Observer#dataWasInput(java.lang.String, int, shared.model.Field,
   * boolean)
   */
  @Override
  public void dataWasInput(String value, int record, Field field,
      boolean shouldResetIsIncorrect) {}

  /*
   * (non-Javadoc)
   * 
   * @see client.model.BatchState.Observer#didChangeOrigin(int, int)
   */
  @Override
  public void didChangeOrigin(int x, int y) {}

  /*
   * (non-Javadoc)
   * 
   * @see client.model.BatchState.Observer#didDownload(java.awt.image.BufferedImage)
   */
  @Override
  public void didDownload(BufferedImage b) {
    toolBar.setEnabled(true);
    toolBar.setRollover(true);
    for (JButton button : toolBarButtons) {
      button.setEnabled(true);
    }
    downloadBatchMenuItem.setEnabled(false);
  }

  /*
   * (non-Javadoc)
   * 
   * @see client.model.BatchState.Observer#didHighlight()
   */
  @Override
  public void didHighlight() {}

  /*
   * (non-Javadoc)
   * 
   * @see client.model.BatchState.Observer#didSubmit(shared.model.Batch)
   */
  @Override
  public void didSubmit(Batch b) {
    clear();
  }

  /*
   * (non-Javadoc)
   * 
   * @see client.model.BatchState.Observer#didToggleHighlight()
   */
  @Override
  public void didToggleHighlight() {}

  /*
   * (non-Javadoc)
   * 
   * @see client.model.BatchState.Observer#didToggleInvert()
   */
  @Override
  public void didToggleInvert() {}

  /*
   * (non-Javadoc)
   * 
   * @see client.model.BatchState.Observer#didZoom(double)
   */
  @Override
  public void didZoom(double zoomAmt) {}

  /*
   * (non-Javadoc)
   * 
   * @see client.model.BatchState.Observer#fieldWasSelected(int, shared.model.Field)
   */
  @Override
  public void fieldWasSelected(int record, Field field) {}

  /**
   * Intitialize.
   */
  public void intitialize() {
    menuListener = new IndexerMenuListener();
    toolBarListener = new IndexerToolBarListener();
    windowAdapter = new IndexerWindowAdapter();

    setSize(new Dimension(1000, 800));

    setJMenuBar(initMenu());
    this.add(initToolBar(), BorderLayout.NORTH);

    JPanel rootPanel = new JPanel(new BorderLayout());
    rootPanel.add(initSplitPane());

    this.add(rootPanel);
    setVisible(true);

    addWindowListener(windowAdapter);
  }

  /*
   * (non-Javadoc)
   * 
   * @see client.model.BatchState.Observer#spellPopupWasOpened(java.lang.String, int,
   * shared.model.Field, java.util.List)
   */
  @Override
  public void spellPopupWasOpened(String value, int record, Field field,
      Set<String> suggestions) {}

  /*
   * (non-Javadoc)
   * 
   * @see client.model.BatchState.Observer#wordWasMisspelled(java.lang.String, int,
   * shared.model.Field)
   */
  @Override
  public void wordWasMisspelled(String value, int record, Field field) {}

  /**
   * Clear.
   */
  private void clear() {
    // TODO
  }

  /**
   * Initializes the menu.
   *
   * @return the j menu bar
   */
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

  /**
   * Initializes the split pane.
   *
   * @return the j split pane
   */
  private JSplitPane initSplitPane() {
    batchViewer = new BatchComponent();

    JTabbedPane entryTabs = new JTabbedPane();
    entryTabs.add("Table Entry", new TableEntryTab());
    entryTabs.add("Form Entry", new FormEntryTab());

    JTabbedPane navTabs = new JTabbedPane();
    navTabs.add("Field Help", new FieldHelpTab());
    navTabs.add("Navigation", new ImageNavigationTab(batchViewer));

    JSplitPane hSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    hSplit.setResizeWeight(0.5);
    hSplit.add(entryTabs);
    hSplit.add(navTabs);

    JSplitPane vSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    vSplit.setResizeWeight(0.5);
    vSplit.add(batchViewer);
    vSplit.add(hSplit);
    // make the batchViewer window 500 pixels tall
    vSplit.setDividerLocation(500 + vSplit.getInsets().bottom);

    return vSplit;
  }

  /**
   * Initializes the tool bar.
   *
   * @return the j tool bar
   */
  private JToolBar initToolBar() {
    zoomInButton = new JButton("Zoom In");
    zoomInButton.addActionListener(toolBarListener);

    zoomOutButton = new JButton("Zoom Out");
    zoomOutButton.addActionListener(toolBarListener);

    invertImageButton = new JButton("Invert Image");
    invertImageButton.addActionListener(toolBarListener);

    toggleHighlightsButton = new JButton("Toggle Highlights");
    toggleHighlightsButton.addActionListener(toolBarListener);

    saveButton = new JButton("Save");
    saveButton.addActionListener(toolBarListener);

    submitButton = new JButton("Submit");
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

    toolBar.setFloatable(false);
    toolBar.setRollover(false);
    toolBar.setEnabled(false);

    return toolBar;
  }

  /**
   * Process exit.
   */
  private void processExit() {
    // TODO: save work
    System.exit(0);
  }

  /**
   * Process submit.
   */
  private void processSubmit() {
    Facade.submitBatch();
    BatchState.notifyDidSubmit(Facade.getBatch());
    Facade.setBatch(null);
  }

  /**
   * The listener interface for receiving indexerMenu events. The class that is interested in
   * processing a indexerMenu event implements this interface, and the object created with that
   * class is registered with a component using the component's <code>addIndexerMenuListener
   * <code> method. When the indexerMenu event occurs, that object's appropriate method is invoked.
   *
   * @see IndexerMenuEvent
   */
  private class IndexerMenuListener implements ActionListener {

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
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
  }

  /**
   * The listener interface for receiving indexerToolBar events. The class that is interested in
   * processing a indexerToolBar event implements this interface, and the object created with that
   * class is registered with a component using the component's <code>addIndexerToolBarListener
   * <code> method. When the indexerToolBar event occurs, that object's appropriate method is
   * invoked.
   *
   * @see IndexerToolBarEvent
   */
  private class IndexerToolBarListener implements ActionListener {

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
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
  }

  /**
   * The Class IndexerWindowAdapter.
   */
  private class IndexerWindowAdapter extends WindowAdapter {

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
     */
    @Override
    public void windowClosing(WindowEvent e) {
      processExit();
    }
  }

}
