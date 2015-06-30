/**
 * LoginDialog.java
 * JRE v1.8.0_45
 *
 * Created by William Myers on Jun 28, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.logging.Level;

import javax.swing.AbstractAction;
import javax.swing.Action;
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

@SuppressWarnings("serial")
public class LoginDialog extends JDialog implements BatchState.Observer {

  private JTextField usernameField;
  private JPasswordField passwordField;

  private Action loginAction = new AbstractAction() {//@formatter:off
    @Override
    public void actionPerformed(ActionEvent e) {
      processLogin();
    }
  };

  private ActionListener loginListener = new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      processLogin();
    }
  };

  private ActionListener exitListener = new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      System.exit(0);
    }
  };//@formatter:on

  public LoginDialog() {
    super((Frame) null, "Login", true);

    // Initialize
    setSize(new Dimension(370, 120));
    setLocationRelativeTo(null);
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setLayout(new BorderLayout());
    setResizable(false);

    initRootPanel();
    setVisible(true);

    BatchState.addObserver(this);
  }

  @Override
  public void cellWasSelected(int x, int y) {}

  @Override
  public void dataWasInput(String value, int record, Field field) {}

  @Override
  public void wordWasMisspelled(String value, int record, Field field,
      List<String> suggestions) {}

  @Override
  public void didChangeOrigin(int x, int y) {}

  @Override
  public void didDownload(BufferedImage b) {}

  @Override
  public void didHighlight() {}

  @Override
  public void didSubmit(Batch b) {}

  @Override
  public void didToggleHighlight() {}

  @Override
  public void didToggleInvert() {}

  @Override
  public void didZoom(double zoomDirection) {}

  @Override
  public void fieldWasSelected(int record, Field field) {}

  public JPasswordField getPasswordField() {
    return this.passwordField;
  }

  public JTextField getUsernameField() {
    return this.usernameField;
  }

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

  private void processLogin() {
    // String username = usernameField.getText();
    // char[] password = passwordField.getPassword();
    // TODO: remember to not hardcode sheila!
    String username = "sheila";
    char[] password = {'p', 'a', 'r', 'k', 'e', 'r'};
    if (Facade.validateUser(username, password)) {
      // TODO: implement load data
      JOptionPane.showMessageDialog(this,
          "Welcome " + Facade.getUser().getFirst() + " "
              + Facade.getUser().getLast() + "\nYou have indexed: "
              + Facade.getUser().getRecordCount() + " records!",
          "Record Indexer", JOptionPane.PLAIN_MESSAGE);
      dispose();
      new IndexerFrame("Record Indexer");
      // BatchState.notifyDidLogin();
    } else {
      ClientLogManager.getLogger().log(Level.FINEST,
          "Incorrect credentials entered: Username: " + username + " Password: "
              + String.valueOf(password));
      JOptionPane.showMessageDialog(this,
          "Incorrect username or password.\nPlease try again.",
          "Invalid Credentials", JOptionPane.PLAIN_MESSAGE);
    }
  }
}
