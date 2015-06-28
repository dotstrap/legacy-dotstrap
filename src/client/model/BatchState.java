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
    public void fieldWasSelected(int record, Field field);
    public void didChangeOrigin(int x, int y);
    public void didToggleHighlight();
    public void didHighlight();
    public void didZoom(double zoomDirection);
    public void didToggleInvert();
    public void dataWasInput(String data, int x, int y);
    public void didChangeValue(int record, Field field, String value);
    public void didDownload(BufferedImage b);
    public void didSubmit(Batch b);
  }; //@formatter:on

  private static transient List<Observer> currentObservers;

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

  public static void notifyCellWasSelected(int positionX, int positionY) {
    for (Observer o : currentObservers) {
      o.cellWasSelected(positionX, positionY);
    }
  }

  public static void notifyFieldWasSelected(int record, Field field) {
    for (Observer o : currentObservers) {
      o.fieldWasSelected(record, field);
    }
  }

  public static void notifyOriginChanged(int positionX, int positionY) {
    for (Observer o : currentObservers) {
      o.didChangeOrigin(positionX, positionY);
    }
  }

  public static void notifyHighlight() {
    for (Observer o : currentObservers) {
      o.didHighlight();
    }
  }

  public static void notifyToggleHighlight() {
    for (Observer o : currentObservers) {
      o.didToggleHighlight();
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

  public static void notifyDidDownload(BufferedImage newBatch) {
    for (Observer o : currentObservers) {
      o.didDownload(newBatch);
    }
  }

}
