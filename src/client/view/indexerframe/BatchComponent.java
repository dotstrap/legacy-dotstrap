package client.view.indexerframe;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.swing.JComponent;

import client.model.BatchState;
import client.model.BatchState.Observer;
import client.util.ClientLogManager;

import shared.model.Batch;
import shared.model.Field;

@SuppressWarnings("serial")
public class BatchComponent extends JComponent implements BatchState.Observer {

  private static final Color HIGHLIGHT_COLOR = new Color(0, 100, 255, 90);
  private static final double ZOOM_SCALE_FACTOR = 0.02;

  private static BufferedImage NULL_IMAGE = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);

  private BufferedImage batch;
  private Rectangle2D rectangle;
  private ArrayList<DrawingShape> shapes;

  private boolean isHighlighted;
  private boolean isInverted;

  private double scale;

  private int dOriginX;
  private int dOriginY;
  private int dCenterX;
  private int dCenterY;

  private int wOriginX;
  private int wOriginY;
  private int wCenterX;
  private int wCenterY;
  private boolean isDragging;
  private int wDragStartX;
  private int wDragStartY;
  private int wDragStartOriginX;
  private int wDragStartOriginY;

  private RescaleOp invertFilter;

  /**
   * Instantiates a new BatchComponent.
   */
  public BatchComponent() throws HeadlessException {
    batch = null;
    scale = 1.0;
    rectangle = new Rectangle2D.Double();
    shapes = new ArrayList<DrawingShape>();

    invertFilter = new RescaleOp(-1.0f, 255f, null);

    dCenterX = getWidth() / 2;
    dCenterY = getHeight() / 2;

    this.addMouseListener(mouseAdapter);
    this.addMouseMotionListener(mouseAdapter);
    // this.addComponentListener(componentAdapter);

    initDrag();

    BatchState.addObserver(this);
  }

  public BufferedImage getBatch() {
    return this.batch;
  }

  public void setBatch(BufferedImage batch) {
    this.batch = batch;
    if (batch != null) {
      dCenterX = batch.getWidth(null) / 2;
      dCenterY = batch.getHeight(null) / 2;
      shapes.add(new DrawingImage(batch, new Rectangle2D.Double(-dCenterX, -dCenterY, batch
          .getWidth(null), batch.getHeight(null))));
    }
    repaint();
  }

  public double getScale() {
    return this.scale;
  }

  public void setScale(double newScale) {
    scale = newScale;
    this.repaint();
  }

  public void setOrigin(int x, int y) {
    wOriginX = x;
    wOriginY = y;
    this.repaint();
  }

  public void displayBatch() {
    repaint();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2 = (Graphics2D) g;
    drawBackground(g2);

    g2.scale(scale, scale);
    g2.translate(-wOriginX, -wOriginY);

    drawShapes(g2);
  }

  private void drawBackground(Graphics2D g2) {
    g2.setColor(getBackground());
    g2.fillRect(0, 0, getWidth(), getHeight());
  }

  private void drawShapes(Graphics2D g2) {
    for (DrawingShape shape : shapes) {
      shape.draw(g2);
    }
  }

  private MouseAdapter mouseAdapter = new MouseAdapter() {
    @Override
    public void mousePressed(MouseEvent e) {
      int dX = e.getX();
      int dY = e.getY();

      AffineTransform transform = new AffineTransform();
      transform.scale(scale, scale);
      transform.translate(-wOriginX, -wOriginY);

      Point2D dPt = new Point2D.Double(dX, dY);
      Point2D wPt = new Point2D.Double();
      try {
        transform.inverseTransform(dPt, wPt);
      } catch (NoninvertibleTransformException ex) {
        return;
      }
      int wX = (int) wPt.getX();
      int wY = (int) wPt.getY();

      boolean didHitShape = false;

      Graphics2D g2 = (Graphics2D) getGraphics();
      for (DrawingShape shape : shapes) {
        if (shape.contains(g2, wX, wY)) {
          didHitShape = true;
          break;
        }
      }

      if (didHitShape) {
        isDragging = true;
        wDragStartX = wX;
        wDragStartY = wY;
        wDragStartOriginX = wOriginX;
        wDragStartOriginY = wOriginY;
      }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
      if (isDragging) {
        int dX = e.getX();
        int dY = e.getY();

        AffineTransform transform = new AffineTransform();
        transform.scale(scale, scale);
        transform.translate(-wDragStartOriginX, -wDragStartOriginY);

        Point2D dPt = new Point2D.Double(dX, dY);
        Point2D wPt = new Point2D.Double();
        try {
          transform.inverseTransform(dPt, wPt);
        } catch (NoninvertibleTransformException ex) {
          ClientLogManager.getLogger().log(Level.FINE, "STACKTRACE: ", e);
          return;
        }
        int wX = (int) wPt.getX();
        int wY = (int) wPt.getY();

        int wDeltaX = wX - wDragStartX;
        int wDeltaY = wY - wDragStartY;

        wOriginX = wDragStartOriginX - wDeltaX;
        wOriginY = wDragStartOriginY - wDeltaY;

        repaint();
      }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      // bs.setBatchPosition(wCenterX, wCenterY);
      initDrag();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
      double scaleAmt = -e.getWheelRotation() * 0.03;
      scale *= (1 + scaleAmt);
      BatchState.setZoomAmt(scale);
    }
  };

  private void initDrag() {
    isDragging = false;
    wDragStartX = 0;
    wDragStartY = 0;
    wDragStartOriginX = 0;
    wDragStartOriginY = 0;
  }

  /* (non-Javadoc)
   * @see client.model.BatchState.Observer#cellWasSelected(int, int)
   */
  @Override
  public void cellWasSelected(int x, int y) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see client.model.BatchState.Observer#didHighlight(boolean)
   */
  @Override
  public void didHighlight(boolean hasHighlight) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see client.model.BatchState.Observer#didZoom(double)
   */
  @Override
  public void didZoom(double zoomAmt) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see client.model.BatchState.Observer#didInvert(boolean)
   */
  @Override
  public void didInvert(boolean isInverted) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see client.model.BatchState.Observer#dataWasInput(java.lang.String, int, int)
   */
  @Override
  public void dataWasInput(String data, int x, int y) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see client.model.BatchState.Observer#didChangeValue(int, shared.model.Field, java.lang.String)
   */
  @Override
  public void didChangeValue(int record, Field field, String value) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see client.model.BatchState.Observer#didDownload(shared.model.Batch)
   */
  @Override
  public void didDownload(BufferedImage b) {
    setBatch(b);
    repaint();
    //generateImageCells();

  }

  /* (non-Javadoc)
   * @see client.model.BatchState.Observer#didSubmit(shared.model.Batch)
   */
  @Override
  public void didSubmit(Batch b) {
    // TODO Auto-generated method stub

  }
}
