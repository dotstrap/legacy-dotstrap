/**
 * FormEntryTab.java JRE v1.8.0_45
 *
 * Created by William Myers on Jun 30, 2015. Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.view.indexerframe;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;

import javax.swing.AbstractListModel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import client.model.BatchState;
import client.model.Facade;
import client.util.ClientLogManager;

import shared.model.Batch;
import shared.model.Field;
import shared.model.Record;

/**
 * The Class FormEntryTab.
 */
@SuppressWarnings("serial")
public class FormEntryTab extends JPanel implements BatchState.Observer {
  private JList<Integer> records;;
  private Map<Field, JTextField> fields;

  FormListListener listListener;
  MouseListener mouseListener;
  FormFocusListener focusListener;

  /**
   * Instantiates a new form entry tab.
   */
  public FormEntryTab() {
    initialize();
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
   * @see client.model.BatchState.Observer#dataWasInput(java.lang.String, int, shared.model.Field)
   */
  @Override
  public void dataWasInput(String cellValue, int row, Field field,
      boolean shouldResetIsIncorrect) {

    int column = field.getColNum();
    if (Facade.getRecordValues()[row][column] == null) {
      Facade.getRecordValues()[row][column] =
          new Record(field.getFieldId(), Facade.getBatch().getBatchId(),
              Facade.getBatch().getFilePath(), cellValue, row, column);
    } else {
      Facade.getRecordValues()[row][column].setData(cellValue);
    }

    fields.get(field).setText(cellValue);

    if ((fields.get(field).getText() == "")
        || (fields.get(field).getText() == null)) {
      fields.get(field).requestFocusInWindow();
    }
  }

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
  public void didDownload(BufferedImage b) {
    initialize();
  }

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
    clear();
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
  public void fieldWasSelected(int r, Field field) {
    if ((records != null) && (r >= 0)) {
      records.setSelectedIndex(r);
    }

    if (fields.isEmpty() || (fields == null) || (field == null)) {
      return;
    }

    String text = "";
    int column = field.getColNum();
    for (Field f : fields.keySet()) {
      int row = BatchState.getCurrentRecord();
      if (row >= 0) {
        if ((Facade.getRecordValues()[row] != null)
            && (Facade.getRecordValues()[row][column] != null)
            && (Facade.getRecordValues()[row][column].getData() != "")) {

          text = Facade.getRecordValues()[row][column].getData();

          ClientLogManager.getLogger().log(Level.FINER,
              Facade.getRecordValues()[row].toString());
        }
        fields.get(field).setText(text);
        fields.get(field).requestFocusInWindow();
      }
    }
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

  /**
   * Word was misspelled.
   *
   * @param value the value
   * @param record the record
   * @param field the field
   */
  @Override
  public void wordWasMisspelled(String value, int record, Field field) {}

  /**
   * Clear.
   */
  private void clear() {
    records.clearSelection();;
    fields.clear();;
    removeAll();
    revalidate();
    this.repaint();
  }

  /**
   * Initialize.
   */
  private void initialize() {
    listListener = new FormListListener();
    mouseListener = new FormMouseListener();
    focusListener = new FormFocusListener();

    removeAll();

    if (Facade.getBatch() != null) {
      int[] recordLabels = new int[Facade.getProject().getRecordsPerBatch()];
      for (int i = 0; i < recordLabels.length; i++) {
        recordLabels[i] = i + 1;
      }
      records = new JList<Integer>(new RecordsListModel());
      records.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      records.addListSelectionListener(listListener);
      // records.addMouseListener(mouseListener);

      setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
      add(new JScrollPane(records));

      JPanel fieldsPanel = new JPanel();
      fieldsPanel.setLayout(new GridLayout(Facade.getFields().size(), 2));

      fields = new TreeMap<Field, JTextField>();
      for (int i = 0; i < Facade.getFields().size(); i++) {
        Field field = Facade.getFields().get(i);
        JTextField textField = new JTextField();
        textField.addFocusListener(focusListener);

        fieldsPanel.add(new JLabel(field.getTitle()));
        fieldsPanel.add(textField);

        fields.put(field, textField);
      }

      add(new JScrollPane(fieldsPanel));
    }
  }

  /**
   * Text field to field.
   *
   * @param textField the text field
   * @return the field
   */
  private Field textFieldToField(JTextField textField) {
    Field result = null;

    for (Field field : fields.keySet()) {
      if (fields.get(field) == textField) {
        result = field;
      }
    }
    return result;
  }

  /**
   * The focus listener.
   *
   * @see FormFocusEvent
   */
  private class FormFocusListener implements FocusListener {

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
     */
    @Override
    public void focusGained(FocusEvent e) {
      Field field = textFieldToField((JTextField) e.getSource());
      JTextField textField = (JTextField) e.getSource();
      if (field != null) {
        if (BatchState.getCurrentRecord() < 0) {
          BatchState.setCurrentRecord(0);
        }
        ClientLogManager.getLogger().log(Level.FINER, field.toString());
        BatchState.setCurrentField(field);
      }
      textField.setBackground(Color.RED);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
     */
    @Override
    public void focusLost(FocusEvent e) {
      Field field = textFieldToField((JTextField) e.getSource());

      if (field != null) {
        String text = ((JTextField) e.getSource()).getText();
        ClientLogManager.getLogger().log(Level.FINER,
            field.toString() + "\ntext: " + text);
        BatchState.notifyDataWasInput(text, BatchState.getCurrentRecord(),
            field, false);
      }
    }
  }

  /**
   * The list listener.
   *
   * @see FormListEvent
   */
  private class FormListListener implements ListSelectionListener {

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void valueChanged(ListSelectionEvent e) {
      if (!e.getValueIsAdjusting()) {
        JList<Integer> source = (JList<Integer>) e.getSource();
        int selection = source.getSelectedIndex();
        if (selection >= 0) {
          BatchState.setCurrentRecord(selection);
        }
      }
    }
  }

  /**
   * The mouse listener.
   *
   * @see FormMouseEvent
   */
  private class FormMouseListener extends MouseAdapter {

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseReleased(MouseEvent e) {
      // TODO
    }
  }

  /**
   * The Class RecordsListModel.
   */
  private class RecordsListModel extends AbstractListModel<Integer> {

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.ListModel#getElementAt(int)
     */
    @Override
    public Integer getElementAt(int index) {
      return index + 1;
    }

    @Override
    public int getSize() {
      if (Facade.getBatch() != null) {
        return Facade.getProject().getRecordsPerBatch();
      } else {
        return 0;
      }
    }
  }
}
