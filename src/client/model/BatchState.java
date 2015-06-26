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
    public void didZoom(double zoomDirection);
    public void didToggleInvert();
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
  private static boolean isHighlighted;

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

  public static void notifyDidZoom(double zoomAmt) {
    for (Observer o : currentObservers) {
      o.didZoom(zoomAmt);
    }
  }

  public static void notifyToggleInvert() {
      for (Observer o : currentObservers) {
        o.didToggleInvert();
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

   // public void setBatchPosition(int positionX, int positionY) {
  // scrollPositionX = positionX;
  // scrollPositionY = positionY;

  // for (Observer o : currentObservers) {
  // o.batchChanged(zoomAmt, scrollPositionX, scrollPositionY);
  // }
  // }
}
