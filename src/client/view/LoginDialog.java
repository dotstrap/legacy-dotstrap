package client.view;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import client.model.Facade;
import client.util.GBC;

@SuppressWarnings("serial")
public class LoginDialog extends JDialog {

  public static LoginDialog instance;
  JTextField     UsernameField;
  JPasswordField PasswordField;
  private Facade facade;

  /**
   * Instantiates a new LoginDialog.
   *
   * @param title
   */
  public LoginDialog(String title) throws HeadlessException {
    super((Frame) null, title, true);

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
    UsernameField = new JTextField();
    UsernameField.setName("UsernameField");
    UsernameField.setPreferredSize(new Dimension(275, 20));
    UsernameField.setMinimumSize(UsernameField.getPreferredSize());
    UsernameField.setMaximumSize(UsernameField.getPreferredSize());
    userbox.add(new JLabel("Username:"));
    userbox.add(Box.createRigidArea(new Dimension(5, 5)));
    userbox.add(UsernameField);
    return userbox;
  }

  private Box createPasswordBox() {
    Box passwordbox = Box.createHorizontalBox();
    PasswordField = new JPasswordField();
    PasswordField.setName("PasswordField");
    PasswordField.setPreferredSize(new Dimension(275, 20));
    PasswordField.setMinimumSize(PasswordField.getPreferredSize());
    PasswordField.setMaximumSize(PasswordField.getPreferredSize());
    passwordbox.add(new JLabel("Password:"));
    passwordbox.add(Box.createRigidArea(new Dimension(5, 5)));
    passwordbox.add(PasswordField);
    return passwordbox;
  }

  private Box createButtonBox() {
    Box buttonbox = Box.createHorizontalBox();
    JButton LoginButton = new JButton("Login");
    LoginButton.addActionListener(new LoginListener(this, facade));
    JButton ExitButton = new JButton("Exit");
    ExitButton.addActionListener(new ExitListener());
    buttonbox.add(Box.createGlue());
    buttonbox.add(LoginButton);
    buttonbox.add(Box.createRigidArea(new Dimension(5, 5)));
    buttonbox.add(ExitButton);
    buttonbox.add(Box.createGlue());
    return buttonbox;
  }

}


// TODO: should these be in controller?
class LoginListener implements ActionListener {
  private LoginDialog frame;
  private Facade      facade;

  public LoginListener(LoginDialog frame, Facade facade) {
    super();
    this.frame = frame;
    this.facade = facade;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (facade.validateUser(frame.UsernameField.getText(), frame.PasswordField.getPassword())) {
      //IndexerFrame.getInstance().loadData();
      new WelcomeDialog(facade, LoginDialog.instance);
    } else {
      JOptionPane.showMessageDialog(LoginDialog.instance, "Wrong credentials, please try again.");
    }
  }
}


class ExitListener implements ActionListener {
  @Override
  public void actionPerformed(ActionEvent e) {
    System.exit(0);
  }
}
