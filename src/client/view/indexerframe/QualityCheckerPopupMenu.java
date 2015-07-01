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

  private JList<String> suggestionlist;
  private JButton useButton;
  private JDialog suggestionDialog;
  private int row;
  private Field column;

  public QualityCheckerPopupMenu(String word, final List<String> suggestions,
      int record, Field field) {
    this.row = record;
    this.column = field;

    ActionListener seeSuggestionsListener = new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
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
        cancelButton.addActionListener(buttonListener);
        buttons.add(Box.createGlue());
        buttons.add(cancelButton);
        buttons.add(Box.createHorizontalStrut(5));
        useButton = new JButton("Use Suggestion");
        useButton.setEnabled(false);
        useButton.addActionListener(buttonListener);
        suggestionlist.addListSelectionListener(listListener);
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

    JMenuItem suggestionsMenu = new JMenuItem("See Suggestions");
    suggestionsMenu.addActionListener(seeSuggestionsListener);
    add(suggestionsMenu);
  }

  @Override
  public void cellWasSelected(int x, int y) {}

  @Override
  public void dataWasInput(String value, int record, Field field,
      boolean shouldResetIsIncorrect) {}

  @Override
  public void wordWasMisspelled(String value, int record, Field field) {}

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

  @Override
  public void spellPopupWasOpened(String value, int record, Field field,
      List<String> suggestions) {}

  ActionListener buttonListener = new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      if (((JButton) e.getSource()).getText().equals("Cancel")) {
        suggestionDialog.dispose();
      } else if (((JButton) e.getSource()) == useButton) {
        int selected = suggestionlist.getSelectedIndex();
        if (selected != -1) {
          BatchState.notifyDataWasInput(suggestionlist.getSelectedValue(),
              getRow(), getColumn(), true);
          suggestionDialog.dispose();
        }
      }

    }
  };

  ListSelectionListener listListener = new ListSelectionListener() {
    @Override
    public void valueChanged(ListSelectionEvent e) {
      if (suggestionlist.getSelectedIndex() == -1) {
        useButton.setEnabled(false);
      } else {
        useButton.setEnabled(true);
      }
    }
  };

  public JList<String> getSuggestionlist() {
    return this.suggestionlist;
  }

  public void setSuggestionlist(JList<String> suggestionlist) {
    this.suggestionlist = suggestionlist;
  }

  public JButton getUseButton() {
    return this.useButton;
  }

  public void setUseButton(JButton useButton) {
    this.useButton = useButton;
  }

  public JDialog getSuggestionDialog() {
    return this.suggestionDialog;
  }

  public void setSuggestionDialog(JDialog suggestionDialog) {
    this.suggestionDialog = suggestionDialog;
  }

  public int getRow() {
    return this.row;
  }

  public void setRow(int row) {
    this.row = row;
  }

  public Field getColumn() {
    return this.column;
  }

  public void setColumn(Field column) {
    this.column = column;
  }

  public ActionListener getButtonListener() {
    return this.buttonListener;
  }

  public void setButtonListener(ActionListener buttonListener) {
    this.buttonListener = buttonListener;
  }

  public ListSelectionListener getListListener() {
    return this.listListener;
  }

  public void setListListener(ListSelectionListener listListener) {
    this.listListener = listListener;
  }
}
