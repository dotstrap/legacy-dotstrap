/**
 * BatchState.java
 * JRE v1.8.0_45
 * 
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import client.util.spell.QualityChecker;
import client.view.IndexerFrame;

import shared.model.Batch;
import shared.model.Field;

/**
 * The Enum BatchState.
 */
public enum BatchState {

  /** The instance. */
  INSTANCE;

  /** The indexer frame. */
  private static IndexerFrame indexerFrame;;

  /** The corrector. */
  private static QualityChecker corrector;

  /** The current observers. */
  private static transient List<Observer> currentObservers;

  /** The current record. */
  private static int currentRecord;

  /** The current field. */
  private static Field currentField;

  /**
   * Adds the observer.
   *
   * @param o the o
   */
  public static void addObserver(Observer o) {
    if (currentObservers == null) {
      currentObservers = new ArrayList<Observer>();
    }
    currentObservers.add(o);
  }

  public static QualityChecker getCorrector() {
    return corrector;
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

  public static IndexerFrame getIndexerFrame() {
    return indexerFrame;
  }

  /**
   * Initialize indexer frame.
   */
  public static void initializeIndexerFrame() {
    // if (BatchState.indexerFrame != null) {
    // BatchState.indexerFrame.dispose();
    // }
    // BatchState.indexerFrame = new IndexerFrame("Record Indexer");
  }

  /**
   * Notifies all current Observers: cell was selected.
   *
   * @param positionX the position x
   * @param positionY the position y
   */
  public static void notifyCellWasSelected(int positionX, int positionY) {
    for (Observer o : currentObservers) {
      o.cellWasSelected(positionX, positionY);
    }
  }

  /**
   * Notifies all current Observers: data was input.
   *
   * @param value the value
   * @param record the record
   * @param field the field
   */
  public static void notifyDataWasInput(String value, int record, Field field) {
    BatchState.currentField = field;
    BatchState.currentRecord = record;
    for (Observer o : currentObservers) {
      o.dataWasInput(value, record, field);
    }

  }

  /**
   * Notifies all current Observers: did download.
   *
   * @param newBatch the new batch
   */
  public static void notifyDidDownload(BufferedImage newBatch) {
    BatchState.corrector = new QualityChecker();
    for (Observer o : currentObservers) {
      o.didDownload(newBatch);
    }
  }

  /**
   * Notifies all current Observers: did submit.
   *
   * @param b the b
   */
  public static void notifyDidSubmit(Batch b) {
    for (Observer o : currentObservers) {
      o.didSubmit(b);
    }
    // BatchState.initializeIndexerFrame();
  }

  /**
   * Notifies all current Observers: did zoom.
   *
   * @param zoomAmt the zoom amt
   */
  public static void notifyDidZoom(double zoomAmt) {
    for (Observer o : currentObservers) {
      o.didZoom(zoomAmt);
    }
  }

  /**
   * Notifies all current Observers: field was selected.
   *
   * @param record the record
   * @param field the field
   */
  public static void notifyFieldWasSelected(int record, Field field) {
    BatchState.currentField = field;
    BatchState.currentRecord = record;
    for (Observer o : currentObservers) {
      o.fieldWasSelected(record, field);
    }
  }

  /**
   * Notifies all current Observers: did highlight.
   */
  public static void notifyHighlight() {
    for (Observer o : currentObservers) {
      o.didHighlight();
    }
  }

  /**
   * Notifies all current Observers: origin changed.
   *
   * @param positionX the position x
   * @param positionY the position y
   */
  public static void notifyOriginChanged(int positionX, int positionY) {
    for (Observer o : currentObservers) {
      o.didChangeOrigin(positionX, positionY);
    }
  }

  /**
   * Notifies all current Observers: spell popup was opened.
   *
   * @param value the value
   * @param record the record
   * @param field the field
   * @param suggestions TODO
   */
  public static void notifySpellPopupWasOpened(String value, int record,
      Field field, List<String> suggestions) {
    for (Observer o : currentObservers) {
      o.spellPopupWasOpened(value, record, field, null);
    }
  }

  /**
   * Notifies all current Observers: toggle highlight.
   */
  public static void notifyToggleHighlight() {
    for (Observer o : currentObservers) {
      o.didToggleHighlight();
    }
  }

  /**
   * Notifies all current Observers: toggle invert.
   */
  public static void notifyToggleInvert() {
    for (Observer o : currentObservers) {
      o.didToggleInvert();
    }
  }

  /**
   * Notifies all current Observers: word was misspelled.
   *
   * @param value the value
   * @param record the record
   * @param field the field
   */
  public static void notifyWordWasMisspelled(String value, int record,
      Field field) {
    for (Observer o : currentObservers) {
      o.wordWasMisspelled(value, record, field);
    }
  }

  /**
   * Removes the observer.
   *
   * @param o the o
   */
  public static void removeObserver(Observer o) {
    if (currentObservers != null) {
      currentObservers.remove(o);
    }
  }

  public static void setCorrector(QualityChecker corrector) {
    BatchState.corrector = corrector;
  }

  public static void setCurrentField(Field f) {
    BatchState.currentField = f;
    // BatchState.currentRecord
    for (Observer o : currentObservers) {
      o.fieldWasSelected(currentRecord, currentField);
    }
  }

  // public static void setIndexerFrame(IndexerFrame indexerFrame) {
  // BatchState.indexerFrame = indexerFrame;
  // }

  public static void setCurrentObservers(List<Observer> currentObservers) {
    BatchState.currentObservers = currentObservers;
  }

  public static void setCurrentRecord(int currentRecord) {
    BatchState.currentRecord = currentRecord;
    for (Observer o : currentObservers) {
      o.fieldWasSelected(currentRecord, currentField);
    }
  }

  /**
   * Updates the all observers.
   */
  public static void updateAllObservers() {
    if (currentObservers != null) {
      for (Observer o : currentObservers) {
        // TODO
      }
    }
  }

  /**
   * The Interface Observer.
   * 
   * Most GUI elements implement the Observer pattern to receive updates from the BatchState about
   * various events.
   */
  public interface Observer {

    /**
     * Cell was selected.
     *
     * @param x the x
     * @param y the y
     */
    public void cellWasSelected(int x, int y);

    /**
     * Data was input.
     *
     * @param value the value
     * @param record the record
     * @param field the field
     */
    public void dataWasInput(String value, int record, Field field);

    /**
     * Did change origin.
     *
     * @param x the x
     * @param y the y
     */
    public void didChangeOrigin(int x, int y);

    /**
     * Did download.
     *
     * @param b the b
     */
    public void didDownload(BufferedImage b);

    /**
     * Did highlight.
     */
    public void didHighlight();

    /**
     * Did submit.
     *
     * @param b the b
     */
    public void didSubmit(Batch b);

    /**
     * Did toggle highlight.
     */
    public void didToggleHighlight();

    /**
     * Did toggle invert.
     */
    public void didToggleInvert();

    /**
     * Did zoom.
     *
     * @param zoomDirection the zoom direction
     */
    public void didZoom(double zoomDirection);

    /**
     * Field was selected.
     *
     * @param record the record
     * @param field the field
     */
    public void fieldWasSelected(int record, Field field);

    /**
     * Spell popup was opened.
     *
     * @param value the value
     * @param record the record
     * @param field the field
     * @param suggestions TODO
     */
    public void spellPopupWasOpened(String value, int record, Field field, List<String> suggestions);

    /**
     * Word was misspelled.
     *
     * @param value the value
     * @param record the record
     * @param field the field
     */
    public void wordWasMisspelled(String value, int record, Field field);
  }

}
