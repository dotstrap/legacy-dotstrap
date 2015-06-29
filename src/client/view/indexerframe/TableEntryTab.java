/**
 * TableEntryTab.java
 * JRE v1.8.0_45
 * 
 * Created by William Myers on Jun 28, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.view.indexerframe;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import client.model.BatchState;
import client.model.Facade;

import shared.model.Batch;
import shared.model.Field;

@SuppressWarnings("serial")
public class TableEntryTab extends JPanel implements BatchState.Observer {

  // ===========================================================================
  // private data members
  // ===========================================================================

  private JTable table;
  private JScrollPane scrollPane;

  @SuppressWarnings("serial")
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
            return Facade.getRecords()[row].getValues().get(Facade.getFields().get(column));
          } else {
            return null;
          }
        }
      } else {
        return null;
      }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public Class getColumnClass(int column) {
      if (column == 0) {
        return Integer.class;
      } else {
        return Value.class;
      }
    }

    @Override
    public void setValueAt(Object newValue, int row, int column) {
      if (column > 0) {
        assert (newValue.getClass() == Value.class);
        Value cellValue = (Value) newValue;

        Field field = Facade.getBatch().getProject().getFields().get(column);
        int record = row;

        if (Facade.getBatch().getRecords()[record].getValues().get(field) == null) {
          Facade.getBatch().getRecords()[record].getValues().put(field, cellValue);
        } else {
          Facade.getBatch().getRecords()[record].getValues().get(field)
              .setData(cellValue.getData());
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
        return "Record No.";
      } else {
        return Facade.getBatch().getProject().getFields().get(column).getTitle();
      }
    }
  };

  // ===========================================================================
  // constructors
  // ===========================================================================

  public TableEntryTab() {
    setLayout(new BorderLayout());
    initialize();
  }

  // ===========================================================================
  // public method overrides
  // ===========================================================================

  @Override
  public void positionChanged(int record, Field field) {
    if (field != null && record >= 0) {
      int row = recordToRow(record);
      int column = fieldToColumn(field);
      table.setRowSelectionInterval(row, row);
      table.setColumnSelectionInterval(column, column);
    } else {
      table.clearSelection();
    }
  }

  // ===========================================================================
  // listeners
  // ===========================================================================

  private MouseAdapter mouseListener = new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
      BatchState.setPosition(getCurrentRecord(), getCurrentField());
    }
  };

  private KeyAdapter keyListener = new KeyAdapter() {
    @Override
    public void keyReleased(KeyEvent e) {
      int key = e.getKeyCode();
      if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_UP
          || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_ENTER || key == KeyEvent.VK_TAB) {
        BatchState.setPosition(getCurrentRecord(), getCurrentField());
      }
    }
  };

  // ===========================================================================
  // private methods
  // ===========================================================================

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
    assert (record >= 0 && record < table.getRowCount());
    return record;
  }

  private Field columnToField(int column) {
    return Facade.getFields().get(column);
  }

  private int fieldToColumn(Field field) {
    Map<Integer, Field> fields = Facade.getBatch().getProject().getFields();
    assert fields.containsValue(field);

    for (int column : fields.keySet()) {
      if (field == fields.get(column))
        return column;
    }

    assert false; // should never get here
    return 0;
  }

  /*
   * (non-Javadoc)
   * 
   * @see client.model.Facade.Observer#cellWasSelected(int, int)
   */
  @Override
  public void cellWasSelected(int x, int y) {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @see client.model.Facade.Observer#dataWasInput(java.lang.String, int, shared.model.Field)
   */
  @Override
  public void dataWasInput(String value, int record, Field field) {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @see client.model.Facade.Observer#wordWasMisspelled(java.lang.String, int, shared.model.Field,
   * java.util.List)
   */
  @Override
  public void wordWasMisspelled(String value, int record, Field field, List<String> suggestions) {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @see client.model.Facade.Observer#didChangeOrigin(int, int)
   */
  @Override
  public void didChangeOrigin(int x, int y) {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @see client.model.Facade.Observer#didDownload(java.awt.image.BufferedImage)
   */
  @Override
  public void didDownload(BufferedImage b) {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @see client.model.Facade.Observer#didHighlight()
   */
  @Override
  public void didHighlight() {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @see client.model.Facade.Observer#didSubmit(shared.model.Batch)
   */
  @Override
  public void didSubmit(Batch b) {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @see client.model.Facade.Observer#didToggleHighlight()
   */
  @Override
  public void didToggleHighlight() {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @see client.model.Facade.Observer#didToggleInvert()
   */
  @Override
  public void didToggleInvert() {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @see client.model.Facade.Observer#didZoom(double)
   */
  @Override
  public void didZoom(double zoomDirection) {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @see client.model.Facade.Observer#fieldWasSelected(int, shared.model.Field)
   */
  @Override
  public void fieldWasSelected(int record, Field field) {
    // TODO Auto-generated method stub

  }
}
