/**
 * ImageNavigationTab.java
 * JRE v1.8.0_45
 * 
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.view.indexerframe;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JComponent;

import client.model.BatchState;

import shared.model.Batch;
import shared.model.Field;

/**
 * The Class ImageNavigationTab.
 */
@SuppressWarnings("serial")
public class ImageNavigationTab extends JComponent
    implements BatchState.Observer {
  private static final Color HIGHLIGHT_COLOR = new Color(0, 255, 245, 80);
  private static final double ZOOM_SCALE_FACTOR = 1;

  private BufferedImage batch;
  private BatchComponent batchViewer;
  private ArrayList<DrawingShape> shapes;
  private Rectangle2D rectangle;

  private double scale;
  private int dCenterX;
  private int dCenterY;
  private int wOriginX;
  private int wOriginY;

  ImgNavMouseAdapter mouseAdapter;

  /**
   * Instantiates a new image navigation tab.
   *
   * @param batchViewer the batch viewer
   */
  public ImageNavigationTab(BatchComponent batchViewer) {
    this.batchViewer = batchViewer;
    batch = null;

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
  public void dataWasInput(String value, int record, Field field,
      boolean shouldResetIsIncorrect) {}

  /*
   * (non-Javadoc)
   * 
   * @see client.model.BatchState.Observer#didChangeOrigin(int, int)
   */
  @Override
  public void didChangeOrigin(int x, int y) {
    setOrigin(x, y);
  }

  /*
   * (non-Javadoc)
   * 
   * @see client.model.BatchState.Observer#didDownload(java.awt.image.BufferedImage)
   */
  @Override
  public void didDownload(BufferedImage b) {
    initBatch(b);
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
  public void didSubmit(Batch b) {}

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
  public void didZoom(double zoomDirection) {
    zoomDirection *= ImageNavigationTab.ZOOM_SCALE_FACTOR;
    scale *= (1 + zoomDirection);

    setScale(scale);
  }

  /**
   * Display batch.
   */
  public void displayBatch() {
    this.repaint();
  }

  /*
   * (non-Javadoc)
   * 
   * @see client.model.BatchState.Observer#fieldWasSelected(int, shared.model.Field)
   */
  @Override
  public void fieldWasSelected(int record, Field field) {}

  public BufferedImage getBatch() {
    return batch;
  }

  public Rectangle2D getRectangle() {
    return rectangle;
  }

  public double getScale() {
    return scale;
  }

  /**
   * Sets the origin.
   *
   * @param x the x
   * @param y the y
   */
  public void setOrigin(int x, int y) {
    wOriginX = x;
    wOriginY = y;
    this.repaint();
  }

  public void setRectangle(Rectangle2D rectangle) {
    this.rectangle = rectangle;
  }

  public void setScale(double newScale) {
    scale = newScale;
    this.repaint();
  }

  /*
   * (non-Javadoc)
   *
   * @see client.model.BatchState.Observer#spellPopupWasOpened(java.lang.String, int,
   * shared.model.Field)
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
  public void wordWasMisspelled(String value, int record, Field field) {}

  /*
   * (non-Javadoc)
   * 
   * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2 = (Graphics2D) g;
    drawBackground(g2);
    g2.translate(getWidth() / 2.0, getHeight() / 2.0);

    drawShapes(g2);
  }

  /**
   * Draw background.
   *
   * @param g2 the g2
   */
  private void drawBackground(Graphics2D g2) {
    g2.setColor(getBackground());
    g2.fillRect(0, 0, getWidth(), getHeight());
  }

  /**
   * Draw shapes.
   *
   * @param g2 the g2
   */
  private void drawShapes(Graphics2D g2) {
    if (shapes.size() >= 1) {
      DrawingImage image = (DrawingImage) shapes.get(0);
      Rectangle2D imagerect = image.getBounds(g2);

      double ratio = 0;
      double imgHeight = getHeight() / imagerect.getHeight();
      double imgWidth = getWidth() / imagerect.getWidth();
      scale =
          (getWidth() > getHeight()) ? (ratio = imgHeight) : (ratio = imgWidth);

      g2.scale(ratio, ratio);
      image.draw(g2);
      g2.translate(wOriginX, wOriginY);

      if (batchViewer != null) {
        double boxH = batchViewer.getHeight();
        double boxW = batchViewer.getWidth();
        double scale = batchViewer.getScale();

        Rectangle2D rect = new Rectangle2D.Double(-boxW / 2, -boxH / 2,
            boxW, boxH);
        DrawingRect dRect =
            new DrawingRect(rect, ImageNavigationTab.HIGHLIGHT_COLOR);

        g2.scale(1 / scale, 1 / scale);
        dRect.draw(g2);
      }
    }
  }

  /**
   * Initializes the batch.
   *
   * @param batch the batch
   */
  private void initBatch(BufferedImage batch) {
    if (batch == null) {
      return;
    }

    this.batch = batch;

    wOriginX = 0;
    wOriginY = 0;
    scale = 1.0;

    dCenterX = batch.getWidth(null) / 2;
    dCenterY = batch.getHeight(null) / 2;

    setPreferredSize(new Dimension(750, 700));
    setMinimumSize(new Dimension(150, 100));
    setMaximumSize(new Dimension(950, 900));

    setRectangle(new Rectangle2D.Double());
    shapes = new ArrayList<DrawingShape>();

    shapes.add(new DrawingImage(batch, new Rectangle2D.Double(-dCenterX,
        -dCenterY, batch.getWidth(null), batch.getHeight(null))));

    addMouseListener(mouseAdapter);
    addMouseMotionListener(mouseAdapter);
    addMouseWheelListener(mouseAdapter);

    this.repaint();
  }

  /**
   * The Class ImgNavMouseAdapter.
   */
  private class ImgNavMouseAdapter extends MouseAdapter {

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseAdapter#mouseDragged(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseDragged(MouseEvent e) {
      int dX = e.getX() - (getWidth() / 2);
      int dY = e.getY() - (getHeight() / 2);

      AffineTransform transform = new AffineTransform();
      transform.translate(getWidth() / 2.0, getHeight() / 2.0);
      transform.scale(scale, scale);
      transform.translate(-wOriginX, -wOriginY);

      Point2D dPt = new Point2D.Double(dX, dY);
      Point2D wPt = new Point2D.Double();
      try {
        transform.inverseTransform(dPt, wPt);
      } catch (NoninvertibleTransformException ex) {
        return;
      }

      int wX = (int) dPt.getX();
      int wY = (int) dPt.getY();
      wOriginX = wX;
      wOriginY = wY;

      repaint();
      BatchState.notifyOriginChanged(wOriginX, wOriginY);
    }

    /* (non-Javadoc)
     * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
     */
    @Override
    public void mousePressed(MouseEvent e) {
      int dX = e.getX() - (getWidth() / 2);
      int dY = e.getY() - (getHeight() / 2);

      AffineTransform transform = new AffineTransform();
      transform.translate(getWidth() / 2.0, getHeight() / 2.0);
      transform.scale(20000, 20000);
      transform.translate(-wOriginX, -wOriginY);

      Point2D dPt = new Point2D.Double(dX, dY);
      Point2D wPt = new Point2D.Double();
      try {
        transform.inverseTransform(dPt, wPt);
      } catch (NoninvertibleTransformException ex) {
        return;
      }

      int wX = (int) dPt.getX();
      int wY = (int) dPt.getY();
      wOriginX = wX;
      wOriginX = wY;
    }

    /* (non-Javadoc)
     * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseReleased(MouseEvent e) {}

  }
}
