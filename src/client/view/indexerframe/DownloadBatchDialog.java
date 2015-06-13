package client.view.indexerframe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.logging.Level;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import client.model.Facade;
import client.util.ClientLogManager;

import shared.model.Batch;
import shared.model.Project;

@SuppressWarnings("serial")
public class DownloadBatchDialog extends JDialog {
  private Frame              parent;

  private JComboBox<Project> projectList;
  private JButton            cancelButton;
  private JButton            downloadButton;
  private JButton            sampleButton;
  private Project            selectedProject;
  private Batch              batch;

  /**
   * Instantiates a new DownloadBatchDialog.
   *
   */
  public DownloadBatchDialog(Frame p) throws HeadlessException {
    super(p, "Download Batch", true);

    this.parent = p;

    // Initialize
    setSize(new Dimension(400, 110));
    setLocationRelativeTo(parent);
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

  private void processViewSample(Project p) {
    URL batchUrl = Facade.getSampleBatch(p.getProjectId());
    new SampleBatchDialog(this, batchUrl, selectedProject.getTitle());
  }

  private void processDownloadBatch() {
    // TODO: check if null- if so display dialog because user already has a batch checked out or
    // something went wrong...
    // Facade.createBatchState(project_id);
    // Facade.executeLoadBatch();
    // subscriber.dispatchEvent(new WindowEvent(subscriber, WindowEvent.WINDOW_CLOSING));
    // close dialog
  }

  private ActionListener downloadBatchButtonListener = new ActionListener() {//@formatter:off
    @Override
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == sampleButton) {
        processViewSample((Project) projectList.getSelectedItem());
      } else if (e.getSource() == cancelButton) {
          batch = null;
          dispose();
      } else if (e.getSource() == downloadButton) {
          processDownloadBatch();
      }
    }
  };//@formatter:on
}
