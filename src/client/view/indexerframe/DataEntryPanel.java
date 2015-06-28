/**
 * DataEntryPanel.java
 * JRE v1.8.0_45
 * 
 * Created by William Myers on Jun 28, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.view.indexerframe;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class DataEntryPanel extends JPanel {

  private JButton addButton;
  private JButton removeButton;
  private JList<String> favList;
  private JPopupMenu favPopupMenu;
  private JMenuItem favAddMenuItem;
  private JMenuItem favRemoveMenuItem;

  private JSplitPane vSplit;
  private JSplitPane hSplit;

  private JTabbedPane entryTabs;
  private JTabbedPane navTabs;

  private TableEntryTab tableEntryTab;
  private FormEntryTab formEntryTab;

  private FieldHelpTab fieldHelpTab;
  private ImageNavigationTab imageNavigationTab;

  private ActionListener actionListener = new ActionListener() {//@formatter:off

    public void actionPerformed(ActionEvent e) {

      if (e.getSource() == addButton
          || e.getSource() == favAddMenuItem) {
        addFav();
      } else if (e.getSource() == removeButton
          || e.getSource() == favRemoveMenuItem) {
        removeFav();
      }
    }
  };

  private MouseAdapter mouseAdapter = new MouseAdapter() {
    @Override
    public void mouseReleased(MouseEvent e) {
      if (e.isPopupTrigger()) {
        if (e.getSource() == favList) {
          favPopupMenu.show(e.getComponent(), e.getX(),
              e.getY());
        }
      }
    }
  };

  public DataEntryPanel() {
    super();
    // data entry components
    tableEntryTab = new TableEntryTab();
    formEntryTab = new FormEntryTab();

    entryTabs = new JTabbedPane();
    entryTabs.add("Table Entry", tableEntryTab);
    entryTabs.add("Form Entry", formEntryTab);

    // field help and image navigation
    fieldHelpTab = new FieldHelpTab();
    imageNavigationTab = new ImageNavigationTab();

    navTabs = new JTabbedPane();
    navTabs.add("Field Help", fieldHelpTab);
    navTabs.add("Navigation", imageNavigationTab);

    // horizontal split
    hSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    hSplit.setResizeWeight(0.5);
    hSplit.add(entryTabs);
    hSplit.add(navTabs);

    // vertical split
    // imageViewer = new ImageViewer(this.userState.getBatchState(), this.communicator);

    vSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    vSplit.setResizeWeight(0.5);
    vSplit.add(imageNavigationTab);
    vSplit.add(hSplit);

    this.setLayout(new BorderLayout());
  }

  public void addFav() {

    //System.out.println("add favorite");

    //if (context.getCurrentUrl().equals("")) {
      //JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "You must first load a web page",
          //"Error", JOptionPane.ERROR_MESSAGE);
    //} else {
      ////FavDialog favDialog = new FavDialog((JFrame) this.getTopLevelAncestor(), true);
      //favDialog.setLocationRelativeTo(this.getTopLevelAncestor());
      //favDialog.setVisible(true);

      //if (favDialog.getStatus()) {
        //// TODO
      //}
      //String title = JOptionPane.showInputDialog("Title for this favorite");
      //if (title != null) {
        //// TODO
      //}
    //}
  }

  public void removeFav() {
    //System.out.println("remove favorite");
    // TODO
  }//@formatter:on
}
