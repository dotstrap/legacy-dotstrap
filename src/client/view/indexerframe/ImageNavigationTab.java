/**
 * BatchComponent.java JRE v1.8.0_45
 *
 * Created by William Myers on Jun 28, 2015. Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.view.indexerframe;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import client.model.BatchState;
import shared.model.Batch;
import shared.model.Field;

@SuppressWarnings("serial")
public class ImageNavigationTab extends JComponent
    implements BatchState.Observer {

  private static final Color HIGHLIGHT_COLOR = new Color(0, 255, 245, 80);
  private static final Color INVERTED_HIGHLIGHT_COLOR =
      new Color(255, 0, 10, 80);
  private static final double ZOOM_SCALE_FACTOR = 1;
  private static final double MAX_ZOOM_AMT = 95.0;
  private static final double MIN_ZOOM_AMT = 0.01;

  private BufferedImage batch;
  private BatchComponent batchViewer;
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

  private Rectangle2D[][] cells;
  private Field[][] fieldLocations;
  private int[][] recordLocations;

  private MouseAdapter mouseAdapter = new MouseAdapter() {
    @Override
    public void mouseDragged(MouseEvent e) {
      // if (isDragging) {
      int dX = e.getX() - getWidth() / 2;
      int dY = e.getY() - getHeight() / 2;

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

      // }
    }

    @Override
    public void mousePressed(MouseEvent e) {
      int dX = e.getX() - getWidth() / 2;
      int dY = e.getY() - getHeight() / 2;

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

      // repaint();
      // BatchState.notifyOriginChanged(wOriginX, wOriginY);

      // BatchState.notifyCellWasSelected(wX, wY);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      // BatchState.notifyOriginChanged(wOriginX, wOriginY);
      // initDrag();
    }

    // @Override
    // public void mouseWheelMoved(MouseWheelEvent e) {
    // double scaleAmt =
    // -e.getWheelRotation() * ImageNavigationTab.ZOOM_SCALE_FACTOR;
    // BatchState.notifyDidZoom(scaleAmt);
    // }
  };

  public ImageNavigationTab(BatchComponent batchViewer) {
    this.batchViewer = batchViewer;

    batch = null;
    // scale = 1.0;



    // this.addComponentListener(componentAdapter);

    // initDrag();

    BatchState.addObserver(this);
  }

  @Override
  public void cellWasSelected(int x, int y) {
    // Point cell = null;
    // for (int row = 0; row < cells.length; row++) {
    // for (int column = 0; column < cells[row].length; column++) {
    // if (cells[row][column].contains(x, y)) {
    // cell = new Point(row, column);
    // BatchState.notifyFieldWasSelected(row, fieldLocations[row][column]);
    // }
    // }
    // }
    //
    // if (cell != null) {
    // highlightCell(cell.x, cell.y);
    // this.repaint();
    // }
  }

  @Override
  public void dataWasInput(String value, int record, Field field,
      boolean shouldResetIsIncorrect) {}

  @Override
  public void wordWasMisspelled(String value, int record, Field field) {}

  @Override
  public void didChangeOrigin(int x, int y) {
    setOrigin(x, y);
  }

  @Override
  public void didDownload(BufferedImage b) {
    initBatch(b);
  }

  @Override
  public void didHighlight() {
    // if (shapes.size() == 2) {
    // DrawingRect rect = (DrawingRect) shapes.get(1);
    // shapes.set(1, rect.setVisible(isHighlighted));
    // this.repaint();
    // }
  }

  @Override
  public void didSubmit(Batch b) {
    // this.setVisible(false);
  }

  @Override
  public void didToggleHighlight() {
    // this.isHighlighted = !this.isHighlighted;
    // if (shapes.size() == 2) {
    // DrawingRect rect = (DrawingRect) shapes.get(1);
    // shapes.set(1, rect.setVisible(isHighlighted));
    // this.repaint();
    // }
  }

  @Override
  public void didToggleInvert() {
    // if (shapes.size() < 1)
    // return;
    //
    // this.isInverted = !this.isInverted;
    //
    // if (this.isInverted) {
    // shapes.set(0, ((DrawingImage) shapes.get(0)).invert(this.batch));
    // if (shapes.size() == 2)
    // shapes.set(1, ((DrawingRect) shapes.get(1))
    // .setColor(ImageNavigationTab.INVERTED_HIGHLIGHT_COLOR));
    // } else {
    // shapes.set(0, ((DrawingImage) shapes.get(0)).setImage(this.batch));
    // if (shapes.size() == 2)
    // shapes.set(1, ((DrawingRect) shapes.get(1))
    // .setColor(ImageNavigationTab.HIGHLIGHT_COLOR));
    // }
    //
    // this.repaint();
  }

  @Override
  public void didZoom(double zoomDirection) {
    zoomDirection *= ImageNavigationTab.ZOOM_SCALE_FACTOR;
    this.scale *= (1 + zoomDirection);

    // if (this.scale > ImageNavigationTab.MAX_ZOOM_AMT)
    // this.scale = ImageNavigationTab.MAX_ZOOM_AMT;
    // if (this.scale < ImageNavigationTab.MIN_ZOOM_AMT)
    // this.scale = ImageNavigationTab.MIN_ZOOM_AMT;

    this.setScale(scale);
  }

  public void displayBatch() {
    this.repaint();
  }

  @Override
  public void fieldWasSelected(int record, Field field) {
    // if (field != null) {
    // ClientLogManager.getLogger().log(Level.FINEST,
    // "\n" + field.toString() + " @ record:" + record + "\n");
    //
    // Point cell = new Point(record, field.getColNum());
    // if (cell != null) {
    // ClientLogManager.getLogger().log(Level.FINEST,
    // "cellX=" + cell.x + " cellY=" + cell.y);
    // highlightCell(cell.x, cell.y);
    // this.repaint();
    // }
    // }

  }

  public BufferedImage getBatch() {
    return this.batch;
  }

  public double getScale() {
    return this.scale;
  }

  public void setOrigin(int x, int y) {
    wOriginX = x;
    wOriginY = y;
    this.repaint();
  }

  public void setScale(double newScale) {
    scale = newScale;
    this.repaint();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2 = (Graphics2D) g;
    drawBackground(g2);
    g2.translate(getWidth() / 2.0, getHeight() / 2.0);

    drawShapes(g2);
  }

  private void drawBackground(Graphics2D g2) {
    g2.setColor(getBackground());
    g2.fillRect(0, 0, getWidth(), getHeight());
  }

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
        double boxHeight = batchViewer.getHeight();
        double boxWidth = batchViewer.getWidth();
        double scale = batchViewer.getScale();

        Rectangle2D rect = new Rectangle2D.Double(-boxWidth / 2, -boxHeight / 2,
            boxWidth, boxHeight);
        DrawingRect dRect =
            new DrawingRect(rect, ImageNavigationTab.HIGHLIGHT_COLOR);

        g2.scale(1 / scale, 1 / scale);
        dRect.draw(g2);
      }
    }
  }

  private void generateBatchCells() {
    // List<Field> fields = Facade.getFields();
    // Project project = Facade.getProject();
    // int recordCount = project.getRecordsPerBatch();
    // int firstY = project.getFirstYCoord();
    // int recordHeight = project.getRecordHeight();
    // cells = new Rectangle2D[recordCount][fields.size()];
    // fieldLocations = new Field[recordCount][fields.size()];
    //
    // for (int record = 0; record < recordCount; record++) {
    // for (int field = 0; field < fields.size(); field++) {
    // Field fieldData = fields.get(field);
    // double x = fieldData.getXCoord() - dCenterX;
    // double y = (firstY + recordHeight * record) - dCenterY;
    // double w = fieldData.getWidth();
    // double h = recordHeight;
    //
    // Rectangle2D rect = new Rectangle2D.Double(x, y, w, h);
    // cells[record][field] = rect;
    // fieldLocations[record][field] = fieldData;
    // }
    // }
  }

  private void highlightCell(int row, int column) {
    // if (column == -1)
    // column = 0;
    // if (shapes.size() == 2) {
    // ((DrawingRect) shapes.get(1)).setRect(cells[row][column]);
    // } else {
    // isHighlighted = true;
    // shapes.add(new DrawingRect(cells[row][column],
    // ImageNavigationTab.HIGHLIGHT_COLOR));
    // }
    // this.repaint();
  }

  private void initBatch(BufferedImage batch) {
    if (batch == null)
      return;

    this.batch = batch;

    this.wOriginX = 0;
    this.wOriginY = 0;
    this.scale = 1.0;

    this.dCenterX = batch.getWidth(null) / 2;
    this.dCenterY = batch.getHeight(null) / 2;

    setPreferredSize(new Dimension(750, 700));
    setMinimumSize(new Dimension(150, 100));
    setMaximumSize(new Dimension(950, 900));

    this.rectangle = new Rectangle2D.Double();
    this.shapes = new ArrayList<DrawingShape>();

    this.shapes.add(new DrawingImage(batch, new Rectangle2D.Double(-dCenterX,
        -dCenterY, batch.getWidth(null), batch.getHeight(null))));

    this.addMouseListener(mouseAdapter);
    this.addMouseMotionListener(mouseAdapter);
    this.addMouseWheelListener(mouseAdapter);

    this.repaint();
  }

  private void initDrag() {
    // isDragging = false;
    // wDragStartX = 0;
    // wDragStartY = 0;
    // wDragStartOriginX = 0;
    // wDragStartOriginY = 0;
  }

  /*
   * (non-Javadoc)
   *
   * @see client.model.BatchState.Observer#spellPopupWasOpened(java.lang.String, int,
   * shared.model.Field)
   */
  @Override
  public void spellPopupWasOpened(String value, int record, Field field,
      List<String> suggestions) {}
}
