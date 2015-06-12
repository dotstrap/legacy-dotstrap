package client.view.indexerframe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import shared.model.Batch;
import shared.model.Project;
import client.model.Facade;
import client.util.ClientLogManager;

@SuppressWarnings("serial")
public class DownloadBatchDialog extends JDialog {
  private Frame              parent;

  private JComboBox<Project> projectList;

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

    createRootPanel();

    setVisible(true);
  }

  private void createRootPanel() {
    JPanel rootPanel = new JPanel();
    rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.PAGE_AXIS));
    rootPanel.add(Box.createRigidArea(new Dimension(0, 5)));
    rootPanel.add(createProjectPanel());
    rootPanel.add(createButtonBox());
    this.add(rootPanel);
  }

  private JPanel createProjectPanel() {
    JPanel projectPanel = new JPanel();

    projectList = new JComboBox<Project>(Facade.getProjects());
    selectedProject = (Project) projectList.getSelectedItem();
    projectPanel.setLayout(new BoxLayout(projectPanel, BoxLayout.X_AXIS));
    projectPanel.add(Box.createHorizontalGlue());
    projectPanel.add(new JLabel("Project:"));
    projectPanel.add(Box.createRigidArea(new Dimension(5, 0)));
    projectPanel.add(projectList);

    projectPanel.add(Box.createRigidArea(new Dimension(5, 0)));
    JButton sampleButton = new JButton("View Sample");
    sampleButton.addActionListener(downloadBatchButtonListener);
    projectPanel.add(sampleButton);
    projectPanel.add(Box.createHorizontalGlue());

    return projectPanel;
  }

  private Box createButtonBox() {
    Box buttonbox = Box.createHorizontalBox();
    JButton cancelButton = new JButton("Cancel");
    cancelButton.addActionListener(downloadBatchButtonListener);
    JButton downloadButton = new JButton("Download");
    downloadButton.addActionListener(downloadBatchButtonListener);
    buttonbox.add(Box.createGlue());
    buttonbox.add(cancelButton);
    buttonbox.add(Box.createRigidArea(new Dimension(5, 5)));
    buttonbox.add(downloadButton);
    buttonbox.add(Box.createGlue());
    return buttonbox;
  }

  private void processViewSample() {
     String image_path = Facade.getSampleBatch(project_id);
     new SampleImageViewer().initialize(image_path, facade);
  }

  private void processDownloadBatch() {
    // Facade.createBatchState(project_id);
    // Facade.executeLoadBatch();
    // subscriber.dispatchEvent(new WindowEvent(subscriber, WindowEvent.WINDOW_CLOSING));
    // close dialog
  }

  private ActionListener downloadBatchButtonListener = new ActionListener() {//@formatter:off
    public void actionPerformed(ActionEvent e) {
      switch(((JButton)e.getSource()).getText()){
        case "View Sample":
          processViewSample();
          break;
        case "Cancel":
          //subscriber.dispatchEvent(new WindowEvent(subscriber, WindowEvent.WINDOW_CLOSING));
          batch = null;
          setVisible(false);
          break;
        case "Download":
          processDownloadBatch();
          break;
      }
    }
  };//@formatter:on
}
