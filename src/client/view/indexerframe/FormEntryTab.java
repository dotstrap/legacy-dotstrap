/**
 * FormEntryTab.java
 * JRE v1.8.0_45
 *
 * Created by William Myers on Jun 28, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.view.indexerframe;

import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.TreeMap;

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

import shared.model.Batch;
import shared.model.Field;

@SuppressWarnings("serial")
public class FormEntryTab extends JPanel implements BatchState.Observer {
  private JList<Integer> records;
  private Map<Field, JTextField> fields;

  private class RecordsListModel extends AbstractListModel<Integer> {
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

  private ListSelectionListener listListener = new ListSelectionListener() {
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
  };

  private FocusListener focusListener = new FocusListener() {
    @Override
    public void focusGained(FocusEvent e) {
      Field field = textFieldToField((JTextField) e.getSource());
      if (field != null) {
        if (BatchState.getCurrentRecord() < 0) {
          BatchState.setCurrentRecord(0);
        }
        BatchState.setCurrentField(field);
      }
    }

    @Override
    public void focusLost(FocusEvent e) {
      Field field = textFieldToField((JTextField) e.getSource());

      if (field != null) {
        String text = ((JTextField) e.getSource()).getText();
        BatchState.notifyDataWasInput(text, BatchState.getCurrentRecord(), field);
      }
    }
  };

  public FormEntryTab() {
    initialize();
    BatchState.addObserver(this);
  }

  @Override
  public void cellWasSelected(int x, int y) {}

  @Override
  public void dataWasInput(String value, int record, Field field) {}

  @Override
  public void didChangeOrigin(int x, int y) {}

  @Override
  public void didDownload(BufferedImage b) {
    initialize();
  }

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
  public void fieldWasSelected(int record, Field field) {
    if (records != null && record >= 0) {
      records.setSelectedIndex(record);
    }

    if (fields != null && field != null) {
      for (Field f : fields.keySet()) {
        String text = "";

        if (record >= 0) {
          if (field.getKnownData() != null) {
            text = f.getKnownData(); // FIXME: this should not be getKnownData
            // text = "FIXME :(";
          }
        }
        fields.get(field).setText(text);
      }
      fields.get(field).requestFocusInWindow();
    }

  }

  private void initialize() {
    removeAll();

    if (Facade.getBatch() != null) {
      int[] recordLabels = new int[Facade.getProject().getRecordsPerBatch()];
      for (int i = 0; i < recordLabels.length; i++) {
        recordLabels[i] = i + 1;
      }
      records = new JList<Integer>(new RecordsListModel());
      records.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      records.addListSelectionListener(listListener);

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
    this.setVisible(true);
  }

  private Field textFieldToField(JTextField textField) {
    Field result = null;

    for (Field field : fields.keySet()) {
      if (fields.get(field) == textField) {
        result = field;
      }
    }
    return result;
  }
}
