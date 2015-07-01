/*
 * 
 */
package client.view.indexerframe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import client.model.BatchState;
import client.model.Facade;
import client.util.ClientLogManager;
import client.util.spell.ISpellCorrector.NoSimilarWordFoundException;

import shared.model.Batch;
import shared.model.Field;
import shared.model.Record;

@SuppressWarnings("serial")
public class TableEntryTab extends JPanel implements BatchState.Observer {

  private JTable table;
  private JScrollPane scrollPane;
  private boolean isWordIncorrect;
  private boolean[][] incorrectWords;
  private String currentWord;
  private TableColumnModel columnModel;

  private DefaultTableCellRenderer statusColumnCellRenderer =
      new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table,
            Object value, boolean isSelected, boolean hasFocus, int row,
            int column) {

          if (!table.getColumnName(column).equals("Record #")
              && (String) value != null) {
            if (incorrectWords[row][column - 1] == true) {
              setBackground(Color.RED);
            } else {
              setBackground(table.getBackground());
            }
          } else {
            setBackground(table.getBackground());
          }

          setValue(value);

          if (column != 0)
            super.getTableCellRendererComponent(table, value, isSelected,
                hasFocus, row, column - 1);

          return this;
        }

        // @Override
        // public Component getTableCellRendererComponent(JTable table,
        // Object value, boolean isSelected, boolean hasFocus, int row,
        // int col) {
        //
        // // Cells are by default rendered as a JLabel.
        // JLabel l = (JLabel) super.getTableCellRendererComponent(table, value,
        // isSelected, hasFocus, row, col + 1);
        //
        // // Get the status for the current row.
        // // CustomTableModel tableModel = (CustomTableModel) table.getModel();
        // if (isWordIncorrect) {
        // l.setBackground(Color.RED);
        // } else {
        // l.setBackground(Color.GRAY);
        // }
        //
        // // Return the JLabel which renders the cell.
        // return l;
        // }

      };

  private AbstractTableModel recordsTableModel = new AbstractTableModel() {
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
      if (Facade.getRecordValues() == null
          // || Facade.getRecordValues().length == 0
          || Facade.getRecordValues()[row] == null
          || Facade.getRecordValues()[row][column - 1] == null)
        return null;

      // if (Facade.getRecordValues()[row].getColNum() != column - 1) {
      // return null;
      // }
      return Facade.getRecordValues()[row][column - 1].getData();
    }

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
      final int row = table.rowAtPoint(e.getPoint());
      final int column = table.columnAtPoint(e.getPoint());

      if (SwingUtilities.isLeftMouseButton(e)) {
        BatchState.notifyFieldWasSelected(getCurrentRecord(),
            getCurrentField());
      } else if (SwingUtilities.isRightMouseButton(e)) {
        try {
          List<String> suggestions = BatchState.getCorrector().initialize(
              Facade.getUrlPrefix() + getCurrentField().getKnownData(),
              currentWord);

          if (incorrectWords[row][column - 1] == true) {
            QualityCheckerPopupMenu popup =
                new QualityCheckerPopupMenu(currentWord, suggestions,
                    getCurrentRecord(), getCurrentField());
            popup.show(table, e.getX(), e.getY());

            BatchState.notifySpellPopupWasOpened(currentWord,
                getCurrentRecord(), getCurrentField(), suggestions);
          }

        } catch (NoSimilarWordFoundException ex) {
          StringBuilder msg =
              new StringBuilder("currentWord=").append(currentWord);
          ClientLogManager.getLogger().log(Level.SEVERE, msg.toString(), e);
        }
      }
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
  public void dataWasInput(String cellValue, int row, Field field,
      boolean shouldResetIsIncorrect) {
    int column = field.getColNum();

    if (shouldResetIsIncorrect == true) {
      incorrectWords[row][column] = false;
    }

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
    ClientLogManager.getLogger().log(Level.FINE, consoleOutput.toString());

  }

  @Override
  public void wordWasMisspelled(String value, int record, Field field) {
    // isWordIncorrect = true;
    incorrectWords[record][field.getColNum()] = true;
    currentWord = value;
  }

  @Override
  public void didChangeOrigin(int x, int y) {}

  @Override
  public void didDownload(BufferedImage b) {
    this.initialize();
  }

  @Override
  public void didHighlight() {}

  @Override
  public void didSubmit(Batch b) {
    this.clear();
  }

  private void clear() {
    this.removeAll();
    table.createDefaultColumnsFromModel();
  }

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
    } else {
      table.clearSelection();
    }
    consoleOutput.append("\n");
    consoleOutput.append(
        "Records per batch=" + Facade.getProject().getRecordsPerBatch());
    ClientLogManager.getLogger().log(Level.FINE, consoleOutput.toString());
  }

  @Override
  public void spellPopupWasOpened(String value, int record, Field field,
      List<String> suggestions) {}

  public boolean isWordIncorrect() {
    return isWordIncorrect;
  }

  public void setWordIncorrect(boolean wordIsIncorrect) {
    this.isWordIncorrect = wordIsIncorrect;
  }

  public String getCurrentWord() {
    return currentWord;
  }

  public void setCurrentWord(String currentWord) {
    this.currentWord = currentWord;
  }

}
