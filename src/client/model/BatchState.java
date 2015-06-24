package client.model;

import java.util.List;

import client.view.IndexerFrame;

import shared.model.Batch;
import shared.model.Field;

public class BatchState {

  public interface Observer {//@formatter:off
    public void cellWasSelected(int x, int y);
    public void didHighlight(boolean hasHighlight);
    public void didZoom(boolean hasZoom);
    public void dataWasInput(String data, int x, int y);
    public void didDownload(Batch b);
    public void didSubmit(Batch b);
  }; //@formatter:on

  private transient List<Observer> listeners;//@formatter:off
  private Batch        batch;
  private IndexerFrame mainframe;

  private int          currentRecord;
  private Field        currentField;

  private double       zoomLevel;
  private int          scrollPositionX;
  private int          scrollPositionY;
  private boolean      isHighlighted;
  private boolean      batchIsInverted;//@formatter:on

  public IndexerFrame getMainframe() {
    return this.mainframe;
  }

  public void setMainframe(IndexerFrame mainframe) {
    this.mainframe = mainframe;
  }
}
