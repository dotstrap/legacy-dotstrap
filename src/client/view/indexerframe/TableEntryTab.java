/*
 * 
 */
package client.view.indexerframe;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import client.model.BatchState;
import client.model.Facade;
import shared.model.*;

@SuppressWarnings("serial")
public class TableEntryTab extends JPanel implements BatchState.Observer {

  private JTable table;
  private JScrollPane scrollPane;

  private AbstractTableModel model = new AbstractTableModel() {

    @Override
    public int getColumnCount() {
      if (Facade.getBatch() != null) {
        return Facade.getFields().size() + 1;
      } else {
        return 0;
      }
    }

    @Override
    public int getRowCount() {
      if (Facade.getBatch() != null) {
        return Facade.getProject().getRecordsPerBatch();
      } else {
        return 0;
      }
    }

    @Override
    public Object getValueAt(int row, int column) {
      if (Facade.getBatch() != null) {
        if (column == 0) {
          return row + 1;
        } else {
          if (Facade.getRecords()[row] != null) {
            return Facade.getRecords()[row].getData();
          } else {
            return null;
          }
        }
      } else {
        return null;
      }
    }

    @Override
    public void setValueAt(Object newValue, int row, int column) {
      if (column > 0) {
        // assert (newValue.getClass() == Value.class);
        String cellValue = (String) newValue;

        Field field = Facade.getFields().get(column - 1);
        int record = row;

        if (Facade.getRecords()[record] == null) {
          Facade.getRecords()[record] = new Record();
          // FIXME: sets the whole row...
          Facade.getRecords()[record].setData(cellValue);
        } else {
          Facade.getRecords()[record].setData(cellValue);
        }

        fireTableCellUpdated(row, column);
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
    if (column == 0) {
      column = 1;
    }

    return columnToField(column);
  }

  private int rowToRecord(int row) {
    return row;
  }

  private int recordToRow(int record) {
    assert(record >= 0 && record < table.getRowCount());
    return record;
  }

  private Field columnToField(int column) {
    return Facade.getFields().get(column - 1);
  }

  private int fieldToColumn(Field field) {
    List<Field> fields = Facade.getFields();
    assert fields.contains(field);

    for (int i = 0; i < fields.size(); i++) {
      if (field == fields.get(i)) {
        return i + 1;
      }
    }
    assert false; // should never get here
    return 0;
  }

  @Override
  public void cellWasSelected(int x, int y) {}

  @Override
  public void dataWasInput(String value, int record, Field field) {}

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
    if (field != null && record >= 0) {
      int row = recordToRow(record);
      int column = fieldToColumn(field);

      table.setRowSelectionInterval(row, row);
      table.setColumnSelectionInterval(column, column);
    } else {
      table.clearSelection();
    }
  }

}
