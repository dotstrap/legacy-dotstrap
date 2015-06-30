/**
 * FormEntryTab.java JRE v1.8.0_45
 *
 * Created by William Myers on Jun 28, 2015. Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.view.indexerframe;

import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
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

@SuppressWarnings("serial")
public class FormEntryTab extends JPanel implements BatchState.Observer {

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
  };

  private JList<Integer> records;
  private Map<Field, JTextField> fields;

  private ListSelectionListener listListener = new ListSelectionListener() {
    @SuppressWarnings("unchecked")
    @Override
    public void valueChanged(ListSelectionEvent e) {
      if (!e.getValueIsAdjusting()) {
        JList<Integer> source = (JList<Integer>) e.getSource();
        int selection = source.getSelectedIndex();
        if (selection >= 0) {
          ClientLogManager.getLogger().log(Level.FINER,
              "SELECTION: " + selection);
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
        ClientLogManager.getLogger().log(Level.FINER, field.toString());
        BatchState.setCurrentField(field);
      }
    }

    @Override
    public void focusLost(FocusEvent e) {
      Field field = textFieldToField((JTextField) e.getSource());

      if (field != null) {
        String text = ((JTextField) e.getSource()).getText();
        ClientLogManager.getLogger().log(Level.FINER,
            field.toString() + "\ntext: " + text);
        BatchState.notifyDataWasInput(text, BatchState.getCurrentRecord(),
            field);
      }
    }
  };

  private MouseAdapter mouseListener = new MouseAdapter() {
    @Override
    public void mouseReleased(MouseEvent e) {
      // super.mouseReleased(e);
      // JTextField field = (JTextField) e.getSource();
      // if (e.getButton() == MouseEvent.BUTTON3) {
      // // if (field.getBackground() == Color.RED) {
      // QualityCheckerPopupMenu popup = new QualityCheckerPopupMenu("test",
      // records.getSelectedIndex(), Integer.parseInt(field.getName()) + 1);
      // popup.show(field, e.getX(), e.getY());
      // // }
      // }
    }
  };

  public FormEntryTab() {
    initialize();
    BatchState.addObserver(this);
  }

  @Override
  public void cellWasSelected(int x, int y) {}

  @Override
  public void dataWasInput(String cellValue, int row, Field field) {
    int column = field.getColNum();
    StringBuilder consoleOutput = new StringBuilder();
    if (Facade.getRecordValues()[row][column] == null) {
      Facade.getRecordValues()[row][column] =
          new Record(field.getFieldId(), Facade.getBatch().getBatchId(),
              Facade.getBatch().getFilePath(), cellValue, row, column);
      consoleOutput.append("\n!!!!!!NULL!!!!!!");
    } else {
      consoleOutput.append("\nsetData=" + cellValue);
      Facade.getRecordValues()[row][column].setData(cellValue);
    }

    consoleOutput.append("\n" + field.toString());
    consoleOutput.append("\n" + Facade.getRecordValues()[row].toString());
    consoleOutput.append(
        "Records per batch=" + Facade.getProject().getRecordsPerBatch());
    consoleOutput.append("\n");
    ClientLogManager.getLogger().log(Level.FINER, consoleOutput.toString());

    // int row = record;
    // if (Facade.getRecords()[index] == null) {
    // Facade.getRecords()[index] = new Record();
    // }

    // ====================================================================
    // make sure all the record data is accurate
    // if (Facade.getRecords()[index].getFieldId() < 0) {
    // Facade.getRecords()[index].setFieldId(field.getFieldId());
    // }
    // if (Facade.getRecords()[index].getBatchId() < 0) {
    // Facade.getRecords()[index].setBatchId(Facade.getBatch().getBatchId());
    // }
    // if (Facade.getRecords()[index].getBatchURL() == "") {
    // Facade.getRecords()[index].setBatchURL(Facade.getBatch().getFilePath());
    // }
    // if (Facade.getRecords()[index].getData() == "") {
    // Facade.getRecords()[index].setData(value);
    // }
    // if (Facade.getRecords()[index].getRowNum() < 0) {
    // Facade.getRecords()[index].setRowNum(index);
    // }
    // if (Facade.getRecords()[index].getColNum() < 0) {
    // Facade.getRecords()[index].setColNum(field.getColNum());
    // }

    // new QualityCheckerPopupMenu(record, record);
    // BatchState.updateSpeltWrong(record, field.getColNum(), value);
  }

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
  public void fieldWasSelected(int r, Field field) {
    if (records != null && r >= 0)
      records.setSelectedIndex(r);

    if (fields.isEmpty() || fields == null || field == null)
      return;

    int column = field.getColNum();
    for (Field f : fields.keySet()) {
      String text = "";
      int row = BatchState.getCurrentRecord();
      if (row >= 0) {
        if (Facade.getRecordValues()[row] != null
            && Facade.getRecordValues()[row][column] != null
            && Facade.getRecordValues()[row][column].getData() != "") {

          text = Facade.getRecordValues()[row][column].getData();

          ClientLogManager.getLogger().log(Level.FINER,
              Facade.getRecordValues()[row].toString());
        }
      }
      fields.get(field).setText(text);
    }
    fields.get(field).requestFocusInWindow();
  }

  @Override
  public void wordWasMisspelled(String value, int record, Field field,
      List<String> suggestions) {
    // new SimilarWordSuggestor(tableEntryTable, row, column).show(tableEntryTable, e.getX(),
    // e.getY());
    // new QualityCheckerPopupMenu(null, record, record);
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
      records.addMouseListener(mouseListener);

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
