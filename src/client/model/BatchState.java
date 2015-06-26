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
    public void didHighlight(boolean hasHighlight);
    public void didZoom(double zoomAmt);
    public void didInvert(boolean isInverted);
    public void dataWasInput(String data, int x, int y);
    public void didChangeValue(int record, Field field, String value);
    public void didDownload(BufferedImage b);
    public void didSubmit(Batch b);
  }; //@formatter:on

  private static transient List<Observer> currentObservers;

  private static BufferedImage currentBatch;
  private static Field currentField;
  private static int currentRecord;

  private static int scrollPositionX;
  private static int scrollPositionY;
  private static double zoomAmt;
  private static boolean isHighlighted;
  private static boolean isBatchInverted;

  public static List<Observer> getCurrentObservers() {
    return currentObservers;
  }

  public static void setCurrentObservers(List<Observer> currentObservers) {
    BatchState.currentObservers = currentObservers;
  }

  public static void addObserver(Observer o) {
    if (currentObservers == null) {
      currentObservers = new ArrayList<Observer>();
    }
    currentObservers.add(o);
  }

  public static void removeObserver(Observer o) {
    if (currentObservers != null) {
      currentObservers.remove(o);
    }
  }

  public static void updateAllObservers() {
    if (currentObservers != null) {
      for (Observer o : currentObservers) {
        // o.batchChanged(batch);
        // o.valuesChanged(batch);
        // o.imageChanged(zoomLevel, scrollPositionX, scrollPositionY);
        // o.imageSettingsChanged(highlightsVisible, imageInverted);
        // o.positionChanged(currentRecord, currentField);
      }
    }
  }

  public static BufferedImage getCurrentBatch() {
    return currentBatch;
  }

  public static void setCurrentBatch(BufferedImage currentBatch) {
    BatchState.currentBatch = currentBatch;

    if (currentObservers != null) {
      for (Observer o : currentObservers) {
        o.didDownload(currentBatch);
      }
    }
  }

  public static Field getCurrentField() {
    return currentField;
  }

  public static void setCurrentField(Field currentField) {
    BatchState.currentField = currentField;
  }

  public static int getCurrentRecord() {
    return currentRecord;
  }

  public static void setCurrentRecord(int currentRecord) {
    BatchState.currentRecord = currentRecord;
  }

  public static int getScrollPositionX() {
    return scrollPositionX;
  }

  public static void setScrollPositionX(int scrollPositionX) {
    BatchState.scrollPositionX = scrollPositionX;
  }

  public static int getScrollPositionY() {
    return scrollPositionY;
  }

  public static void setScrollPositionY(int scrollPositionY) {
    BatchState.scrollPositionY = scrollPositionY;
  }

  public static double getZoomAmt() {
    return zoomAmt;
  }

  public static void setZoomAmt(double zoomAmt) {
    BatchState.zoomAmt = zoomAmt;
    for (Observer o : currentObservers) {
      o.didZoom(zoomAmt);
    }
  }

  public static boolean isHighlighted() {
    return isHighlighted;
  }

  public static void setHighlighted(boolean isHighlighted) {
    BatchState.isHighlighted = isHighlighted;
  }

  public static boolean isBatchInverted() {
    return isBatchInverted;
  }

  public static void setBatchInverted(boolean isBatchInverted) {
    BatchState.isBatchInverted = isBatchInverted;
  }

  // public void setBatchPosition(int positionX, int positionY) {
  // scrollPositionX = positionX;
  // scrollPositionY = positionY;

  // for (Observer o : currentObservers) {
  // o.batchChanged(zoomAmt, scrollPositionX, scrollPositionY);
  // }
  // }
}
