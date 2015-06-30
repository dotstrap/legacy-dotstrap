/**
 * TableEntryTab.java
 * JRE v1.8.0_45
 * 
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.view.indexerframe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import client.model.BatchState;
import client.model.Facade;

import shared.model.Batch;
import shared.model.Field;
import shared.model.Record;

/**
 * The Class TableEntryTab.
 */
@SuppressWarnings("serial")
public class TableEntryTab extends JPanel implements BatchState.Observer {
  private JTable table;
  private JScrollPane scrollPane;

  private boolean[][] incorrectWords;
  private boolean isWordIncorrect;
  private String currentWord;

  private TableColumnModel columnModel;
  private StatusColumnCellRenderer statusColumnCellRenderer;
  private TableKeyListener keyListener;
  private RecordsTableModel recordsTableModel;
  private MouseListener mouseListener;

  /**
   * Instantiates a new table entry tab.
   */
  public TableEntryTab() {
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
   * @see client.model.BatchState.Observer#dataWasInput(java.lang.String, int, shared.model.Field,
   * boolean)
   */
  @Override
  public void dataWasInput(String cellValue, int row, Field field,
      boolean shouldResetIsIncorrect) {
    int column = field.getColNum();

    if (shouldResetIsIncorrect == true) {
      incorrectWords[row][column] = false;
    }

    if (Facade.getRecordValues()[row][column] == null) {
      Facade.getRecordValues()[row][column] =
          new Record(field.getFieldId(), Facade.getBatch().getBatchId(),
              Facade.getBatch().getFilePath(), cellValue, row, column);

    } else {
      Facade.getRecordValues()[row][column].setData(cellValue);
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
  public void fieldWasSelected(int record, Field field) {
    if ((field != null) && (record >= 0)) {
      int row = recordToRow(record);
      int column = fieldToColumn(field);

      table.setRowSelectionInterval(row, row);
      table.setColumnSelectionInterval(column, column);
    } else {
      table.clearSelection();
    }
  }

  public String getCurrentWord() {
    return currentWord;
  }

  public boolean isWordIncorrect() {
    return isWordIncorrect;
  }

  public void setCurrentWord(String currentWord) {
    this.currentWord = currentWord;
  }

  public void setWordIncorrect(boolean wordIsIncorrect) {
    isWordIncorrect = wordIsIncorrect;
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
  public void wordWasMisspelled(String value, int record, Field field) {
    // isWordIncorrect = true;
    incorrectWords[record][field.getColNum()] = true;
    currentWord = value;
  }

  /**
   * Clear.
   */
  private void clear() {
    removeAll();
    table.createDefaultColumnsFromModel();
  }

  /**
   * Column to field.
   *
   * @param column the column
   * @return the field
   */
  private Field columnToField(int column) {
    return Facade.getFields().get(column - 1);
  }

  /**
   * Field to column.
   *
   * @param field the field
   * @return the int
   */
  private int fieldToColumn(Field field) {
    return field.getColNum() > 0 ? field.getColNum() + 1 : 1;
  }

  private Field getCurrentField() {
    int column = table.getSelectedColumn();
    return column <= 0 ? columnToField(1) : columnToField(column);
  }

  private int getCurrentRecord() {
    return rowToRecord(table.getSelectedRow());
  }

  /**
   * Initialize.
   */
  private void initialize() {
    setLayout(new BorderLayout());

    removeAll();

    statusColumnCellRenderer = new StatusColumnCellRenderer();
    keyListener = new TableKeyListener();
    recordsTableModel = new RecordsTableModel();

    table = new JTable(recordsTableModel);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    table.getTableHeader().setReorderingAllowed(false);
    table.setCellSelectionEnabled(false);

    table.addMouseListener(mouseListener);
    table.addKeyListener(keyListener);

    scrollPane = new JScrollPane(table);

    table.setDefaultRenderer(String.class, statusColumnCellRenderer);
    columnModel = table.getColumnModel();
    for (int i = 1; i < table.getColumnCount(); ++i) {
      TableColumn column = columnModel.getColumn(i);
      column.setCellRenderer(statusColumnCellRenderer);
    }

    incorrectWords = new boolean[table.getRowCount()][table.getColumnCount()];

    add(scrollPane, BorderLayout.CENTER);
  }

  /**
   * Record to row.
   *
   * @param record the record
   * @return the int
   */
  private int recordToRow(int record) {
    if (record < 0) {
      return 1;
    } else if (record >= table.getRowCount()) {
      return table.getRowCount();
    } else {
      return record;
    }
  }

  /**
   * Row to record.
   *
   * @param row the row
   * @return the int
   */
  private int rowToRecord(int row) {
    return row;
  }

  /**
   * The Class RecordsTableModel.
   */
  private class RecordsTableModel extends AbstractTableModel {
    @Override
    public int getColumnCount() {
      return Facade.getBatch() != null ? Facade.getFields().size() + 1 : 0;
    }

    /* (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#getColumnName(int)
     */
    @Override
    public String getColumnName(int column) {
      if (column == 0) {
        return "Record #";
      } else {
        return Facade.getFields().get(column - 1).getTitle();
      }
    }

    @Override
    public int getRowCount() {
      return Facade.getBatch() != null
          ? Facade.getProject().getRecordsPerBatch() : 0;
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    @Override
    public Object getValueAt(int row, int column) {
      if (Facade.getBatch() == null) {
        return null;
      }
      if (column == 0) {
        return row + 1;
      }
      if ((Facade.getRecordValues() == null)
          || (Facade.getRecordValues()[row] == null)
          || (Facade.getRecordValues()[row][column - 1] == null)) {
        return null;
      }
      return Facade.getRecordValues()[row][column - 1].getData();
    }

    /* (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
     */
    @Override
    public boolean isCellEditable(int row, int column) {
      return (column != 0);
    }

    /* (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object, int, int)
     */
    @Override
    public void setValueAt(Object newValue, int row, int column) {
      if (column > 0) {
        String cellValue = (String) newValue;
        Field field = Facade.getFields().get(column - 1);

        currentWord = cellValue;
        incorrectWords[row][column] = false;

        fireTableCellUpdated(row, column);
        BatchState.notifyDataWasInput(cellValue, row, field, false);
      }
    }

  }

  /**
   * The Class StatusColumnCellRenderer.
   */
  private class StatusColumnCellRenderer extends DefaultTableCellRenderer {
    
    /* (non-Javadoc)
     * @see javax.swing.table.DefaultTableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {

      if (!table.getColumnName(column).equals("Record #")
          && ((String) value != null)) {
        if (incorrectWords[row][column - 1] == true) {
          setBackground(Color.RED);
        } else {
          setBackground(table.getBackground());
        }
      } else {
        setBackground(table.getBackground());
      }

      setValue(value);

      if (column != 0) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
            row, column - 1);
      }

      return this;
    }
  }

  /**
   * The listener interface for receiving tableKey events. The class that is interested in
   * processing a tableKey event implements this interface, and the object created with that class
   * is registered with a component using the component's <code>addTableKeyListener<code> method.
   * When the tableKey event occurs, that object's appropriate method is invoked.
   *
   * @see TableKeyEvent
   */
  private class TableKeyListener extends KeyAdapter {
    
    /* (non-Javadoc)
     * @see java.awt.event.KeyAdapter#keyReleased(java.awt.event.KeyEvent)
     */
    @Override
    public void keyReleased(KeyEvent e) {
      int key = e.getKeyCode();
      if ((key == KeyEvent.VK_LEFT) || (key == KeyEvent.VK_RIGHT)
          || (key == KeyEvent.VK_UP) || (key == KeyEvent.VK_DOWN)
          || (key == KeyEvent.VK_ENTER) || (key == KeyEvent.VK_TAB)) {
        BatchState.notifyFieldWasSelected(getCurrentRecord(),
            getCurrentField());
      }
    }
  }

}
