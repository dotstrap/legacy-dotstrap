/**
 * QualityChecker.java JRE v1.8.0_45
 *
 * Created by William Myers on Jun 27, 2015. Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.view.indexerframe;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import client.model.BatchState;

import shared.model.Batch;
import shared.model.Field;

@SuppressWarnings("serial")
public class QualityCheckerPopupMenu extends JPopupMenu
    implements BatchState.Observer {
  JList<String> suggestionlist;
  JButton useButton;

  public QualityCheckerPopupMenu(String word) {
    ActionListener seeSuggestionsListener = new ActionListener() {
      JDialog suggestionDialog;

      @Override
      public void actionPerformed(ActionEvent e) {
        List<String> suggestions = Arrays.asList("EJB", "JPA", "GlassFish");;
        // BatchState.getCorrector(column - 1).suggestSimilarWord(
        // (String) table.getValueAt(row, column));
        suggestionDialog = new JDialog();
        suggestionDialog.setTitle("Suggestions");
        suggestionDialog.setModal(true);
        suggestionDialog.setLocationRelativeTo(null);
        suggestionDialog.setSize(230, 230);
        suggestionDialog.setResizable(false);
        suggestionDialog
            .setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        suggestionlist =
            new JList<String>(suggestions.toArray(new String[] {}));
        // suggestionlist.setMinimumSize(new Dimension(200,0));
        Box space = Box.createHorizontalBox();
        space.add(Box.createVerticalStrut(5));
        Box box = Box.createHorizontalBox();
        box.add(Box.createHorizontalStrut(10));
        box.add(new JScrollPane(suggestionlist));
        box.add(Box.createHorizontalStrut(10));
        box.setMaximumSize(new Dimension(200, 200));
        panel.add(space);
        panel.add(box);
        Box buttons = Box.createHorizontalBox();
        buttons.setMaximumSize(new Dimension(250, 50));
        JButton cancelButton = new JButton("Cancel");
        ActionListener listener = new ActionListener() {

          @Override
          public void actionPerformed(ActionEvent e) {
            if (((JButton) e.getSource()).getText().equals("Cancel")) {
              suggestionDialog.dispose();
            } else if (((JButton) e.getSource()) == useButton) {
              int selected = suggestionlist.getSelectedIndex();
              if (selected != -1) {
                // table.setValueAt(suggestionlist.getSelectedValue(), row, column);
                suggestionDialog.dispose();
              }
            }

          }
        };
        cancelButton.addActionListener(listener);
        buttons.add(Box.createGlue());
        buttons.add(cancelButton);
        buttons.add(Box.createHorizontalStrut(5));
        useButton = new JButton("Use Suggestion");
        useButton.setEnabled(false);
        useButton.addActionListener(listener);
        suggestionlist.addListSelectionListener(new ListSelectionListener() {

          @Override
          public void valueChanged(ListSelectionEvent e) {
            if (suggestionlist.getSelectedIndex() == -1) {
              useButton.setEnabled(false);
            } else {
              useButton.setEnabled(true);
            }
          }
        });
        buttons.add(useButton);
        buttons.add(Box.createGlue());
        Box space1 = Box.createHorizontalBox();
        space1.add(Box.createVerticalStrut(10));
        Box space2 = Box.createHorizontalBox();
        space2.add(Box.createVerticalStrut(5));
        panel.add(space1);
        panel.add(buttons);
        panel.add(space2);
        suggestionDialog.add(panel);
        suggestionDialog.setVisible(true);
      }
    };
    JMenuItem anItem = new JMenuItem("See Suggestions");
    anItem.addActionListener(seeSuggestionsListener);
    add(anItem);
  }

  @Override
  public void cellWasSelected(int x, int y) {
    // TODO Auto-generated method stub

  }

  @Override
  public void dataWasInput(String value, int record, Field field) {
    // TODO Auto-generated method stub

  }

  @Override
  public void wordWasMisspelled(String value, int record, Field field,
      List<String> suggestions) {
    // TODO Auto-generated method stub

  }

  @Override
  public void didChangeOrigin(int x, int y) {
    // TODO Auto-generated method stub

  }

  @Override
  public void didDownload(BufferedImage b) {
    // TODO Auto-generated method stub

  }

  @Override
  public void didHighlight() {
    // TODO Auto-generated method stub

  }

  @Override
  public void didSubmit(Batch b) {
    // TODO Auto-generated method stub

  }

  @Override
  public void didToggleHighlight() {
    // TODO Auto-generated method stub

  }

  @Override
  public void didToggleInvert() {
    // TODO Auto-generated method stub

  }

  @Override
  public void didZoom(double zoomDirection) {
    // TODO Auto-generated method stub

  }

  @Override
  public void fieldWasSelected(int record, Field field) {
    // TODO Auto-generated method stub

  }

}
