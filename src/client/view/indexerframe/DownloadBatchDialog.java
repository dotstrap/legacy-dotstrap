/**
 * DownloadBatchDialog.java JRE v1.8.0_45
 *
 * Created by William Myers on Jun 28, 2015. Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.view.indexerframe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.logging.Level;

import javax.swing.*;

import client.model.BatchState;
import client.model.Facade;
import client.util.ClientLogManager;
import client.view.IndexerFrame;
import shared.model.Project;

@SuppressWarnings("serial")
public class DownloadBatchDialog extends JDialog {
  private IndexerFrame indexerFrame;

  private JComboBox<Project> projectList;
  private JButton cancelButton;
  private JButton downloadButton;
  private JButton sampleButton;
  private Project selectedProject;

  private ActionListener downloadBatchButtonListener = new ActionListener() {//@formatter:off
    @Override
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == sampleButton) {
        processViewSampleBatch((Project) projectList.getSelectedItem());
      } else if (e.getSource() == cancelButton) {
          //batch = null;
          dispose();
      } else if (e.getSource() == downloadButton) {
          processDownloadBatch((Project) projectList.getSelectedItem());
      }
    }
  };//@formatter:on

  public DownloadBatchDialog(IndexerFrame p, BatchComponent b) {
    super(p, "Download Batch", true);

    this.indexerFrame = p;

    // Initialize
    setSize(new Dimension(400, 110));
    setLocationRelativeTo(indexerFrame);
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setLayout(new BorderLayout());
    setResizable(false);

    initRootPanel();

    setVisible(true);
  }

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

  private JPanel initProjectPanel() {
    JPanel projectPanel = new JPanel();

    projectList = new JComboBox<Project>(Facade.getProjects());
    projectList.addActionListener(downloadBatchButtonListener);
    selectedProject = (Project) projectList.getSelectedItem();
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

  private void initRootPanel() {
    JPanel rootPanel = new JPanel();
    rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.PAGE_AXIS));
    rootPanel.add(Box.createRigidArea(new Dimension(0, 5)));
    rootPanel.add(initProjectPanel());
    rootPanel.add(initButtonBox());
    this.add(rootPanel);
  }

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

  private void processViewSampleBatch(Project p) {
    BufferedImage sampleBatch = Facade.getSampleBatch(p.getProjectId());
    if (sampleBatch != null) {
      new SampleBatchDialog(this, sampleBatch, p.getTitle());
    } else {
      JOptionPane.showMessageDialog(this, "Error downloading sample batch.",
          "Unable to Download Sample Batch", JOptionPane.WARNING_MESSAGE);
      ClientLogManager.getLogger().log(Level.FINE,
          "received null image from Facade");

    }
  }
}
