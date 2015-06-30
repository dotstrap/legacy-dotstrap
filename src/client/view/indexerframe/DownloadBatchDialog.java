/**
 * DownloadBatchDialog.java
 * JRE v1.8.0_45
 * 
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.view.indexerframe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.logging.Level;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import client.model.BatchState;
import client.model.Facade;
import client.util.ClientLogManager;
import client.view.IndexerFrame;

import shared.model.Project;

/**
 * The Class DownloadBatchDialog.
 */
@SuppressWarnings("serial")
public class DownloadBatchDialog extends JDialog {
  private IndexerFrame indexerFrame;

  private Project selectedProject;

  private JComboBox<Project> projectList;
  private JButton cancelButton;
  private JButton downloadButton;
  private JButton sampleButton;

  private DownloadBatchButtonListener downloadBatchButtonListener;

  /**
   * Instantiates a new download batch dialog.
   *
   * @param p the p
   * @param b the b
   */
  public DownloadBatchDialog(IndexerFrame p, BatchComponent b)

  {
    super(p, "Download Batch", true);
    indexerFrame = p;
    downloadBatchButtonListener = new DownloadBatchButtonListener();

    setSize(new Dimension(400, 110));
    setLocationRelativeTo(indexerFrame);
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setLayout(new BorderLayout());
    setResizable(false);

    initRootPanel();

    setVisible(true);
  }

  public Project getSelectedProject() {
    return selectedProject;
  }

  public void setSelectedProject(Project selectedProject) {
    this.selectedProject = selectedProject;
  }

  /**
   * Initializes the button box.
   *
   * @return the box
   */
  private Box initButtonBox() {
    Box buttonBox = Box.createHorizontalBox();

    cancelButton = new JButton("Cancel");
    cancelButton.addActionListener(downloadBatchButtonListener);
    downloadButton = new JButton("Download");
    downloadButton.addActionListener(downloadBatchButtonListener);

    buttonBox.add(Box.createGlue());
    buttonBox.add(cancelButton);
    buttonBox.add(Box.createRigidArea(new Dimension(5, 5)));
    buttonBox.add(downloadButton);
    buttonBox.add(Box.createGlue());

    return buttonBox;
  }

  /**
   * Initializes the project panel.
   *
   * @return the j panel
   */
  private JPanel initProjectPanel() {
    projectList = new JComboBox<Project>(Facade.getProjects());
    projectList.addActionListener(downloadBatchButtonListener);

    selectedProject = (Project) projectList.getSelectedItem();

    JPanel projectPanel = new JPanel();

    projectPanel.setLayout(new BoxLayout(projectPanel, BoxLayout.X_AXIS));

    projectPanel.add(Box.createHorizontalGlue());
    projectPanel.add(new JLabel("Project:"));
    projectPanel.add(Box.createRigidArea(new Dimension(5, 0)));
    projectPanel.add(projectList);
    projectPanel.add(Box.createRigidArea(new Dimension(5, 0)));

    sampleButton = new JButton("View Sample");
    sampleButton.addActionListener(downloadBatchButtonListener);

    projectPanel.add(sampleButton);
    projectPanel.add(Box.createHorizontalGlue());

    return projectPanel;
  }

  /**
   * Initializes the root panel.
   */
  private void initRootPanel() {
    JPanel rootPanel = new JPanel();

    rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.PAGE_AXIS));
    rootPanel.add(Box.createRigidArea(new Dimension(0, 5)));
    rootPanel.add(initProjectPanel());
    rootPanel.add(initButtonBox());

    this.add(rootPanel);
  }

  /**
   * Process download batch.
   *
   * @param p the p
   */
  private void processDownloadBatch(Project p) {
    BufferedImage batch = Facade.downloadBatch(p.getProjectId());
    if (batch != null) {
      dispose();
      BatchState.notifyDidDownload(batch);
    } else {
      JOptionPane.showMessageDialog(this,
          "A Batch could not be downloaded. Please try again.",
          "Unable to Download Batch", JOptionPane.WARNING_MESSAGE);
    }
  }

  /**
   * Process view sample batch.
   *
   * @param p the p
   */
  private void processViewSampleBatch(Project p) {
    BufferedImage sampleBatch = Facade.getSampleBatch(p.getProjectId());

    if (sampleBatch != null) {
      new SampleBatchDialog(this, sampleBatch, p.getTitle());
    } else {
      JOptionPane.showMessageDialog(this, "Error downloading sample batch.",
          "Unable to Download Sample Batch", JOptionPane.WARNING_MESSAGE);

      ClientLogManager.getLogger().log(Level.WARNING,
          "received null image from Facade");

    }
  }

  /**
   * The listener interface for receiving downloadBatchButton events. The class that is interested
   * in processing a downloadBatchButton event implements this interface, and the object created
   * with that class is registered with a component using the component's
   * <code>addDownloadBatchButtonListener<code> method. When the downloadBatchButton event occurs,
   * that object's appropriate method is invoked.
   *
   * @see DownloadBatchButtonEvent
   */
  class DownloadBatchButtonListener implements ActionListener {
    
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == sampleButton) {
        processViewSampleBatch((Project) projectList.getSelectedItem());
      } else if (e.getSource() == cancelButton) {
        dispose();
      } else if (e.getSource() == downloadButton) {
        processDownloadBatch((Project) projectList.getSelectedItem());
      }
    }
  }
}
