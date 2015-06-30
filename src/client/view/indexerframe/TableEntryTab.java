/*
 * 
 */
package client.view.indexerframe;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.logging.Level;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import client.model.BatchState;
import client.model.Facade;
import client.util.ClientLogManager;

import shared.model.Batch;
import shared.model.Field;
import shared.model.Record;

@SuppressWarnings("serial")
public class TableEntryTab extends JPanel implements BatchState.Observer {

  private JTable table;
  private JScrollPane scrollPane;

  private AbstractTableModel model = new AbstractTableModel() {

    @Override
    public int getColumnCount() {
      return Facade.getBatch() != null ? Facade.getFields().size() + 1 : 0;
    }

    @Override
    public int getRowCount() {
      return Facade.getBatch() != null
          ? Facade.getProject().getRecordsPerBatch() : 0;
    }

    @Override
    public Object getValueAt(int row, int column) {
      if (Facade.getBatch() == null)
        return null;
      if (column == 0)
        return row + 1;
      if (Facade.getRecords()[row] == null)
        return null;
      if (Facade.getRecords()[row].getColNum() != column - 1) {
        return null;
      }

      return Facade.getRecords()[row].getData();
    }

    @Override
    public void setValueAt(Object newValue, int row, int column) {
      if (column > 0) {
        String cellValue = (String) newValue;
        Field field = Facade.getFields().get(column - 1);
        fireTableCellUpdated(row, column);
        BatchState.notifyDataWasInput(cellValue, row, field);
      }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
      return (column != 0);
    }

    @Override
    public String getColumnName(int column) {
      if (column == 0) {
        return "Record #";
      } else {
        return Facade.getFields().get(column - 1).getTitle();
      }
    }

  };

  public TableEntryTab() {
    setLayout(new BorderLayout());
    initialize();
    BatchState.addObserver(this);
  }

  private MouseAdapter mouseListener = new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
      BatchState.notifyFieldWasSelected(getCurrentRecord(), getCurrentField());
    }
  };

  private KeyAdapter keyListener = new KeyAdapter() {
    @Override
    public void keyReleased(KeyEvent e) {
      int key = e.getKeyCode();
      if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT
          || key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN
          || key == KeyEvent.VK_ENTER || key == KeyEvent.VK_TAB) {
        BatchState.notifyFieldWasSelected(getCurrentRecord(),
            getCurrentField());
      }
    }
  };

  private void initialize() {
    removeAll();

    table = new JTable(model);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    table.getTableHeader().setReorderingAllowed(false);
    table.setCellSelectionEnabled(false);

    table.addMouseListener(mouseListener);
    table.addKeyListener(keyListener);

    scrollPane = new JScrollPane(table);

    add(scrollPane, BorderLayout.CENTER);
  }

  private int getCurrentRecord() {
    return rowToRecord(table.getSelectedRow());
  }

  private Field getCurrentField() {
    int column = table.getSelectedColumn();
    return column <= 0 ? columnToField(1) : columnToField(column);
  }

  private int rowToRecord(int row) {
    return row;
  }

  private int recordToRow(int record) {
    if (record < 0) {
      return 1;
    } else if (record >= table.getRowCount()) {
      return table.getRowCount();
    } else {
      return record;
    }
  }

  private Field columnToField(int column) {
    return Facade.getFields().get(column - 1);
  }

  private int fieldToColumn(Field field) {
    return field.getColNum() > 0 ? field.getColNum() + 1 : 1;
  }

  @Override
  public void cellWasSelected(int x, int y) {}

  @Override
  public void dataWasInput(String cellValue, int row, Field field) {
    StringBuilder consoleOutput = new StringBuilder();
    if (Facade.getRecords()[row] == null) {
      Facade.getRecords()[row] = new Record(field.getFieldId(),
          Facade.getBatch().getBatchId(), Facade.getBatch().getFilePath(),
          cellValue, row, field.getColNum());
      consoleOutput.append("\n!!!!!!NULL!!!!!!");
    } else if (Facade.getRecords()[row].getColNum() == field.getColNum()) {
      consoleOutput.append("\nsetData=" + cellValue);
      Facade.getRecords()[row].setData(cellValue);
    }

    consoleOutput.append("\n" + field.toString());
    consoleOutput.append("\n" + Facade.getRecords()[row].toString());
    consoleOutput.append(
        "Records per batch=" + Facade.getProject().getRecordsPerBatch());
    consoleOutput.append("\n");
    ClientLogManager.getLogger().log(Level.FINE, consoleOutput.toString());
  }

  @Override
  public void wordWasMisspelled(String value, int record, Field field,
      List<String> suggestions) {}

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
    StringBuilder consoleOutput = new StringBuilder();
    if (field != null && record >= 0) {
      int row = recordToRow(record);
      int column = fieldToColumn(field);

      table.setRowSelectionInterval(row, row);
      table.setColumnSelectionInterval(column, column);
      consoleOutput.append("\nrow=" + row);
      consoleOutput.append("\ncolumn=" + column);
      if (Facade.getRecords()[row] != null) {
        consoleOutput.append("\n" + field.toString());
        consoleOutput.append("\n" + Facade.getRecords()[row].toString());
        if (Facade.getRecords()[row].getColNum() != field.getColNum()) {
          consoleOutput.append("\nCREATED NEW RECORD");
          Facade.getRecords()[row] =
              new Record(field.getFieldId(), Facade.getBatch().getBatchId(),
                  Facade.getBatch().getFilePath(), "", row, field.getColNum());
        }
      }
    }
    // } else {
    // table.clearSelection();
    // }
    consoleOutput.append("\n");
    consoleOutput.append(
        "Records per batch=" + Facade.getProject().getRecordsPerBatch());
    ClientLogManager.getLogger().log(Level.FINE, consoleOutput.toString());
  }
}
