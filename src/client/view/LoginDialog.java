package client.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;

import javax.swing.*;

import client.model.Facade;
import client.util.ClientLogManager;

@SuppressWarnings("serial")
public class LoginDialog extends JDialog {

  private static LoginDialog instance;
  private JTextField         usernameField;
  private JPasswordField     passwordField;

  /**
   * Instantiates a new LoginDialog.
   *
   * @param title
   */
  public LoginDialog(String title) throws HeadlessException {
    super((Frame) null, title, true);
    instance = this;

    // Initialize
    setSize(new Dimension(370, 125));
    setLocationRelativeTo(null);
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setLayout(new BorderLayout());
    setResizable(false);

    createMainPanel();

    setVisible(true);
  }

  private void createMainPanel() {
    JPanel mainpanel = new JPanel();
    mainpanel.setLayout(new BoxLayout(mainpanel, BoxLayout.PAGE_AXIS));
    mainpanel.add(Box.createRigidArea(new Dimension(0, 5)));
    mainpanel.add(createUserBox());
    mainpanel.add(Box.createRigidArea(new Dimension(0, 10)));
    mainpanel.add(createPasswordBox());
    mainpanel.add(createButtonBox());
    this.add(mainpanel);
  }

  private Box createUserBox() {
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

  private Box createPasswordBox() {
    Box passwordbox = Box.createHorizontalBox();
    passwordField = new JPasswordField();
    passwordField.setName("PasswordField");
    passwordField.setPreferredSize(new Dimension(275, 20));
    passwordField.setMinimumSize(passwordField.getPreferredSize());
    passwordField.setMaximumSize(passwordField.getPreferredSize());
    passwordbox.add(new JLabel("Password:"));
    passwordbox.add(Box.createRigidArea(new Dimension(5, 5)));
    passwordbox.add(passwordField);
    return passwordbox;
  }

  private Box createButtonBox() {
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

  public static LoginDialog getInstance() {
    return instance;
  }

  public JTextField getUsernameField() {
    return this.usernameField;
  }

  public JPasswordField getPasswordField() {
    return this.passwordField;
  }

  private ActionListener loginListener = new ActionListener() {//@formatter:off
    public void actionPerformed(ActionEvent e) {
      String username = usernameField.getText();
      char[] password = passwordField.getPassword();
      if (Facade.validateUser(username, password)) {
        // IndexerFrame.getInstance().loadData();
        JOptionPane.showMessageDialog(LoginDialog.instance, "Welcome " + Facade.getUser().getFirst()
            + "\nYou have indexed: " + Facade.getUser().getRecordCount() + " records!",
            "Record Indexer", JOptionPane.PLAIN_MESSAGE);
        instance.setVisible(false);
        //IndexerFrame mainWindow = new IndexerFrame("Record Indexer");
        new IndexerFrame("Record Indexer");
      } else {
        ClientLogManager.getLogger().log(
            Level.FINEST,
            "Incorrect credentials entered: Username: " + username + " Password: "
                + String.valueOf(password));
        JOptionPane.showMessageDialog(LoginDialog.instance,
            "Incorrect username or password.\nPlease try again.", "Invalid Credentials",
            JOptionPane.PLAIN_MESSAGE);
      }
    }
  };

  private ActionListener exitListener = new ActionListener() {
    public void actionPerformed(ActionEvent e) {
      System.exit(0);
    }
  };//@formatter:on
}
