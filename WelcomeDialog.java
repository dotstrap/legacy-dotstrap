package client.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import client.model.BatchState;
import client.model.Facade;

public class WelcomeDialog extends JDialog {
  private static final long serialVersionUID = 6174828846470492037L;
	private BatchState bs;

  public WelcomeDialog(Facade facade, LoginDialog dialog) {
    super();
    setResizable(false);
    setSize(new Dimension(200,100));
    setLocationRelativeTo(null);
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    addWindowListener(new WelcomeWindowListener(this, facade, dialog));
    JPanel mainpanel = new JPanel();
    mainpanel.setLayout(new BoxLayout(mainpanel,BoxLayout.PAGE_AXIS));
    Box one = Box.createHorizontalBox();
    one.add(new JLabel("Welcome "+facade.getFirstname()+" "+facade.getLastname()+"!"));
    Box two = Box.createHorizontalBox();
    two.add(new JLabel("You have "+facade.getCompletedRecordCount()+" Complete records."));
    Box three = Box.createHorizontalBox();
    JButton okbutton = new JButton("OK");
    mainpanel.add(one);
    mainpanel.add(two);
    okbutton.addActionListener(new okListener(this));
    three.add(okbutton);
    mainpanel.add(three);
    add(mainpanel);
    setVisible(true);
  }

  class WelcomeWindowListener extends WindowAdapter{
    WelcomeDialog dialog;
    final Facade facade;
    private LoginDialog frame;

    WelcomeWindowListener(WelcomeDialog wd, Facade f, LoginDialog ld){
      this.dialog = wd;
      this.facade = f;
      this.frame = ld;
    }

    @Override
    public void windowClosing(WindowEvent arg0) {
      bs.getMainframe().setVisible(true);
      //facade.getMainframe().loadGuiData();
      dialog.setVisible(false);
      frame.setVisible(false);
      frame.dispose();
      dialog.dispose();
    }
  }

  class okListener implements ActionListener {
    private WelcomeDialog dialog;
    public okListener(WelcomeDialog dialog) {
      super();
      this.dialog = dialog;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
      dialog.dispatchEvent(new WindowEvent(dialog, WindowEvent.WINDOW_CLOSING));
    }
  }
}

