/**
 * BatchState.java
 * JRE v1.8.0_45
 * 
 * Created by William Myers on Jun 28, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import shared.model.Batch;
import shared.model.Field;

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
    BatchState.currentField = field;
    BatchState.currentRecord = record;
    for (Observer o : currentObservers) {
      o.dataWasInput(value, record, field);
    }

  }

  public static void notifyWordWasMisspelled(String value, int record,
      Field field, List<String> suggestions) {
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
    BatchState.currentField = field;
    BatchState.currentRecord = record;
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
    // BatchState.currentRecord
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

}
