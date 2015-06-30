/**
 * LoginDialog.java
 * JRE v1.8.0_45
 * 
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Set;
import java.util.logging.Level;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import client.model.BatchState;
import client.model.Facade;
import client.util.ClientLogManager;

import shared.model.Batch;
import shared.model.Field;

/**
 * The Class LoginDialog.
 */
@SuppressWarnings("serial")
public class LoginDialog extends JDialog implements BatchState.Observer {
  private IndexerFrame indexerFrame;
  private JTextField usernameField;
  private JPasswordField passwordField;

  private LoginAction loginAction;
  private LoginListener loginListener;
  private ExitListener exitListener;

  /**
   * Instantiates a new login dialog.
   */
  public LoginDialog() {
    super((Frame) null, "Login", true);

    loginAction = new LoginAction();
    loginListener = new LoginListener();
    exitListener = new ExitListener();

    setSize(new Dimension(370, 120));
    setLocationRelativeTo(null);
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setLayout(new BorderLayout());
    setResizable(false);

    initRootPanel();
    setVisible(true);

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
  public void didDownload(BufferedImage b) {}

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
    // this.indexerFrame.dispose();
    // this.indexerFrame.initialize();
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
  public void didZoom(double zoomDirection) {}

  /*
   * (non-Javadoc)
   * 
   * @see client.model.BatchState.Observer#fieldWasSelected(int, shared.model.Field)
   */
  @Override
  public void fieldWasSelected(int record, Field field) {}

  public IndexerFrame getIndexerFrame() {
    return indexerFrame;
  }

  public JPasswordField getPasswordField() {
    return passwordField;
  }

  public JTextField getUsernameField() {
    return usernameField;
  }

  public void setIndexerFrame(IndexerFrame indexerFrame) {
    this.indexerFrame = indexerFrame;
  }

  /*
   * (non-Javadoc)
   *
   * @see client.model.BatchState.Observer#spellPopupWasOpened(java.lang.String, int,
   * shared.model.Field)
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
   * Initializes the button box.
   *
   * @return the box
   */
  private Box initButtonBox() {
    Box buttonbox = Box.createHorizontalBox();
    JButton LoginButton = new JButton("Login");
    LoginButton.addActionListener(loginListener);
    JButton ExitButton = new JButton("Exit");
    ExitButton.addActionListener(exitListener);
    buttonbox.add(Box.createGlue());
    buttonbox.add(LoginButton);
    buttonbox.add(Box.createRigidArea(new Dimension(5, 5)));
    buttonbox.add(ExitButton);
    buttonbox.add(Box.createGlue());
    return buttonbox;
  }

  /**
   * Initializes the password box.
   *
   * @return the box
   */
  private Box initPasswordBox() {
    Box passwordbox = Box.createHorizontalBox();
    passwordField = new JPasswordField();
    passwordField.setName("PasswordField");
    passwordField.setPreferredSize(new Dimension(275, 20));
    passwordField.setMinimumSize(passwordField.getPreferredSize());
    passwordField.setMaximumSize(passwordField.getPreferredSize());
    passwordbox.add(new JLabel("Password:"));
    passwordbox.add(Box.createRigidArea(new Dimension(5, 5)));
    passwordField.addActionListener(loginAction); // respond to enter key
    passwordbox.add(passwordField);
    return passwordbox;
  }

  /**
   * Initializes the root panel.
   */
  private void initRootPanel() {
    JPanel rootPanel = new JPanel();
    rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.PAGE_AXIS));
    rootPanel.add(Box.createRigidArea(new Dimension(0, 5)));
    rootPanel.add(initUserBox());
    rootPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    rootPanel.add(initPasswordBox());
    rootPanel.add(initButtonBox());
    this.add(rootPanel);
  }

  /**
   * Initializes the user box.
   *
   * @return the box
   */
  private Box initUserBox() {
    Box userbox = Box.createHorizontalBox();
    usernameField = new JTextField();
    usernameField.setName("UsernameField");
    usernameField.setPreferredSize(new Dimension(275, 20));
    usernameField.setMinimumSize(usernameField.getPreferredSize());
    usernameField.setMaximumSize(usernameField.getPreferredSize());
    userbox.add(new JLabel("Username:"));
    userbox.add(Box.createRigidArea(new Dimension(5, 5)));
    userbox.add(usernameField);
    return userbox;
  }

  /**
   * Process login.
   */
  private void processLogin() {
    String username = usernameField.getText();
    char[] password = passwordField.getPassword();

    if (Facade.validateUser(username, password)) {
      JOptionPane.showMessageDialog(this,
          "Welcome " + Facade.getUser().getFirst() + " "
              + Facade.getUser().getLast() + "\nYou have indexed: "
              + Facade.getUser().getRecordCount() + " records!",
          "Record Indexer", JOptionPane.PLAIN_MESSAGE);

      new IndexerFrame("Record Indexer");
      dispose();
    } else {
      ClientLogManager.getLogger().log(Level.FINEST,
          "Incorrect credentials entered: Username: " + username + " Password: "
              + String.valueOf(password));

      JOptionPane.showMessageDialog(this,
          "Incorrect username or password.\nPlease try again.",
          "Invalid Credentials", JOptionPane.PLAIN_MESSAGE);
    }
  }

  /**
   * The listener interface for receiving exit events. The class that is interested in processing a
   * exit event implements this interface, and the object created with that class is registered with
   * a component using the component's <code>addExitListener<code> method. When the exit event
   * occurs, that object's appropriate method is invoked.
   *
   * @see ExitEvent
   */
  private class ExitListener implements ActionListener {
    
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      System.exit(0);
    }
  }

  /**
   * The Class LoginAction.
   */
  private class LoginAction extends AbstractAction {
    
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      processLogin();
    }
  }

  /**
   * The listener interface for receiving login events. The class that is interested in processing a
   * login event implements this interface, and the object created with that class is registered
   * with a component using the component's <code>addLoginListener<code> method. When the login
   * event occurs, that object's appropriate method is invoked.
   *
   * @see LoginEvent
   */
  private class LoginListener implements ActionListener {
    
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      processLogin();
    }
  }
}
