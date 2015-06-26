package client.view.indexerframe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

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

  // private Batch batch;

  /**
   * Instantiates a new DownloadBatchDialog.
   *
   */
  public DownloadBatchDialog(IndexerFrame p, BatchComponent b) throws HeadlessException {
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

  private void initRootPanel() {
    JPanel rootPanel = new JPanel();
    rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.PAGE_AXIS));
    rootPanel.add(Box.createRigidArea(new Dimension(0, 5)));
    rootPanel.add(initProjectPanel());
    rootPanel.add(initButtonBox());
    this.add(rootPanel);
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

  private void processViewSampleBatch(Project p) {
    BufferedImage sampleBatch = Facade.getSampleBatch(p.getProjectId());
    new SampleBatchDialog(this, sampleBatch, p.getTitle());
  }

  private void processDownloadBatch(Project p) {
    BufferedImage batch = Facade.downloadBatch(p.getProjectId());
    if (batch != null) {
      dispose();
      BatchState.notifyDidDownload(batch);
    } else {
      JOptionPane.showMessageDialog(this,
          "A Batch could not be downloaded for this project. Please try another project.",
          "Unable to Download Batch", JOptionPane.WARNING_MESSAGE);
    }
  }

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
}
