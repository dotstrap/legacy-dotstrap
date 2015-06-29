/**
 * BatchState.java
 * JRE v1.8.0_45
 * 
 * Created by William Myers on Jun 28, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import client.util.ClientLogManager;
import client.util.spell.SpellCorrector;

import shared.model.Batch;
import shared.model.Field;
import shared.model.Record;

public enum BatchState {
  INSTANCE;

  public interface Observer {//@formatter:off
    public void cellWasSelected(int x, int y);
    public void dataWasInput(String value, int record, Field field);
    public void wordWasMisspelled(String value, int record, Field field, List<String> suggestions);
    public void didChangeOrigin(int x, int y);
    public void didDownload(BufferedImage b);
    public void didHighlight();
    public void didSubmit(Batch b);
    public void didToggleHighlight();
    public void didToggleInvert();
    public void didZoom(double zoomDirection);
    public void fieldWasSelected(int record, Field field);
  }; //@formatter:on

  private static transient List<Observer> currentObservers;
  private static int currentRecord;
  private static Field currentField;

  public static void addObserver(Observer o) {
    if (currentObservers == null) {
      currentObservers = new ArrayList<Observer>();
    }
    currentObservers.add(o);
  }

  public static Field getCurrentField() {
    return currentField;
  }

  public static List<Observer> getCurrentObservers() {
    return currentObservers;
  }

  public static int getCurrentRecord() {
    return currentRecord;
  }

  public static void notifyCellWasSelected(int positionX, int positionY) {
    for (Observer o : currentObservers) {
      o.cellWasSelected(positionX, positionY);
    }
  }

  public static void notifyDataWasInput(String value, int record, Field field) {
    int index = record;
    if (Facade.getRecords()[record] == null) {
      Facade.getRecords()[index] =
          new Record(field.getFieldId(), Facade.getBatch().getBatchId(), Facade.getBatch()
              .getFilePath(), value, index, field.getColNum());

    } else {
      Facade.getRecords()[index].setData(value);
    }
    for (Observer o : currentObservers) {
      o.dataWasInput(value, index, field);
    }
  }

  public static void notifyWordWasMisspelled(String value, int record, Field field,
      List<String> suggestions) {
    for (Observer o : currentObservers) {
      o.wordWasMisspelled(value, record, field, suggestions);
    }
  }

  public static void notifyDidDownload(BufferedImage newBatch) {
    for (Observer o : currentObservers) {
      o.didDownload(newBatch);
    }
  }

  public static void notifyDidZoom(double zoomAmt) {
    for (Observer o : currentObservers) {
      o.didZoom(zoomAmt);
    }
  }

  public static void notifyFieldWasSelected(int record, Field field) {
    for (Observer o : currentObservers) {
      o.fieldWasSelected(record, field);
    }
  }

  public static void notifyHighlight() {
    for (Observer o : currentObservers) {
      o.didHighlight();
    }
  }

  public static void notifyOriginChanged(int positionX, int positionY) {
    for (Observer o : currentObservers) {
      o.didChangeOrigin(positionX, positionY);
    }
  }

  public static void notifyToggleHighlight() {
    for (Observer o : currentObservers) {
      o.didToggleHighlight();
    }
  }

  public static void notifyToggleInvert() {
    for (Observer o : currentObservers) {
      o.didToggleInvert();
    }
  }

  public static void removeObserver(Observer o) {
    if (currentObservers != null) {
      currentObservers.remove(o);
    }
  }

  public static void setCurrentField(Field f) {
    BatchState.currentField = f;
    for (Observer o : currentObservers) {
      o.fieldWasSelected(currentRecord, currentField);
    }
  }

  public static void setCurrentObservers(List<Observer> currentObservers) {
    BatchState.currentObservers = currentObservers;
  }

  public static void setCurrentRecord(int currentRecord) {
    BatchState.currentRecord = currentRecord;
    for (Observer o : currentObservers) {
      o.fieldWasSelected(currentRecord, currentField);
    }
  }

  public static void updateAllObservers() {
    if (currentObservers != null) {
      for (Observer o : currentObservers) {
        // TODO
      }
    }
  }

  public static int[][] speltWrong;
  private String[][] indexedData;

  public String[][] getIndexedData() {
    return indexedData;
  }

  public static boolean isKnownWord(int field, String word) {
    SpellCorrector corrector = getCorrector(field);
    if (corrector != null) {
      return corrector.wordKnown(word);
    }
    return true;
  }

  public List<String> getSuggestions(int column, String word) {
    SpellCorrector corrector = getCorrector(column - 1);
    return corrector.suggestSimilarWord(word);
  }

  public static SpellCorrector getCorrector(int field) {
    try {
      String DictionaryPath = Facade.getFields().get(field).getKnownData();

      if (DictionaryPath != null) {
        SpellCorrector corrector = new SpellCorrector();
        corrector.useDictionary(DictionaryPath);
        return corrector;
      }
    } catch (IOException e) {
      ClientLogManager.getLogger().log(Level.SEVERE, "unable to open dictionairy" + e);
    }
    return null;
  }

  public void initializeSpeltWrong() {
    int records = Facade.getProject().getRecordsPerBatch();
    int fields = Facade.getFields().size();
    String[][] data = new String[records][fields + 1];
    speltWrong = new int[records][fields];
    if (getIndexedData() != null) {
      data = getIndexedData();
      for (int record = 0; record < data.length; record++) {
        for (int column = 1; column < fields; column++) {
          speltWrong[record][column - 1] = 0;
          String value = data[record][column];
          if (value != null && !value.equals("")) {
            updateSpeltWrong(record, column - 1, value);
          }
        }
      }
    } else {
      for (int i = 0; i < records; i++) {
        Vector<String> row = new Vector<String>(fields + 1);
        row.add(0, String.valueOf(i + 1));
        data[i] = row.toArray(new String[] {});
      }
    }
  }

  public static boolean updateSpeltWrong(int record, int field, String word) {
    if (word != null && !word.equals("")) {
      speltWrong[record][field] = 0;
      if (!isKnownWord(field, word)) {
        speltWrong[record][field] = 1;
        return true;
      }
    }
    return false;
  }

  public boolean isSpeltWrong(int record, int field) {
    if (record >= 0 && field >= 0) {
      if (speltWrong[record][field] == 1)
        return true;
    }
    return false;
  }

}
