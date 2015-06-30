/**
 * QualityCheckerPopupMenu.java
 * JRE v1.8.0_45
 *
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.view.indexerframe;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Set;

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

/**
 * The Class QualityCheckerPopupMenu.
 */
@SuppressWarnings("serial")
public class QualityCheckerPopupMenu extends JPopupMenu
    implements BatchState.Observer {

  private JList<String> suggestionlist;

  private JButton useButton;

  private JDialog suggestionDialog;

  private int row;

  private Field column;

  ButtonListener buttonListener;
  ListListener listListener;

  /**
   * Instantiates a new quality checker popup menu.
   *
   * @param word the word
   * @param suggestions the suggestions
   * @param record the record
   * @param field the field
   */
  public QualityCheckerPopupMenu(String word, final Set<String> suggestions,
      int record, Field field) {
    row = record;
    column = field;

    buttonListener = new ButtonListener();
    listListener = new ListListener();

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
        suggestionlist.addListSelectionListener(listListener);

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

        buttons.add(useButton);
        buttons.add(Box.createGlue());

        Box suggestionBox = Box.createHorizontalBox();
        suggestionBox.add(Box.createVerticalStrut(10));
        Box hBox = Box.createHorizontalBox();

        hBox.add(Box.createVerticalStrut(5));
        panel.add(suggestionBox);
        panel.add(buttons);
        panel.add(hBox);

        suggestionDialog.add(panel);
        suggestionDialog.setVisible(true);
      }
    };

    JMenuItem suggestionsMenu = new JMenuItem("See Suggestions");
    suggestionsMenu.addActionListener(seeSuggestionsListener);
    add(suggestionsMenu);
  };

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
  public void didSubmit(Batch b) {}

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

  public ActionListener getButtonListener() {
    return buttonListener;
  }

  public Field getColumn() {
    return column;
  }

  public ListSelectionListener getListListener() {
    return listListener;
  }

  public int getRow() {
    return row;
  }

  public JDialog getSuggestionDialog() {
    return suggestionDialog;
  }

  public JList<String> getSuggestionlist() {
    return suggestionlist;
  }

  public JButton getUseButton() {
    return useButton;
  }

  public void setColumn(Field column) {
    this.column = column;
  }

  public void setRow(int row) {
    this.row = row;
  }

  public void setSuggestionDialog(JDialog suggestionDialog) {
    this.suggestionDialog = suggestionDialog;
  }

  public void setSuggestionlist(JList<String> suggestionlist) {
    this.suggestionlist = suggestionlist;
  }

  public void setUseButton(JButton useButton) {
    this.useButton = useButton;
  }

  /*
   * (non-Javadoc)
   *
   * @see client.model.BatchState.Observer#spellPopupWasOpened(java.lang.String, int,
   * shared.model.Field, java.util.List)
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
   * The listener interface for receiving button events. The class that is interested in processing
   * a button event implements this interface, and the object created with that class is registered
   * with a component using the component's <code>addButtonListener<code> method. When the button
   * event occurs, that object's appropriate method is invoked.
   *
   * @see ButtonEvent
   */
  private class ButtonListener implements ActionListener {
    
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
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
  }

  /**
   * The listener interface for receiving list events. The class that is interested in processing a
   * list event implements this interface, and the object created with that class is registered with
   * a component using the component's <code>addListListener<code> method. When the list event
   * occurs, that object's appropriate method is invoked.
   *
   * @see ListEvent
   */
  private class ListListener implements ListSelectionListener {
    
    /* (non-Javadoc)
     * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
      if (suggestionlist.getSelectedIndex() == -1) {
        useButton.setEnabled(false);
      } else {
        useButton.setEnabled(true);
      }
    }
  }
}
