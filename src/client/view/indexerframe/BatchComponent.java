package client.view.indexerframe;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import client.util.ClientLogManager;

@SuppressWarnings("serial")
public class BatchComponent extends JComponent {

  private static final Color HIGHLIGHT_COLOR = new Color(0, 100, 255, 90);
  private static final double ZOOM_SCALE_FACTOR = 0.02;

  private static BufferedImage NULL_IMAGE = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);

  private BufferedImage batch;
  private Rectangle2D rectangle;

  boolean isHighlighted;
  boolean isInverted;

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

  /**
   * Instantiates a new BatchComponent.
   *
   * @param batchUrl
   * @param indexerFrame
   *
   */
  public BatchComponent() throws HeadlessException {
    // super(p, true);
  }

  @Override
  protected void paintComponent(Graphics g)
  {
    super.paintComponent(g);

    dCenterX = getWidth() / 2;    dCenterY = getHeight() / 2;

    Graphics2D g2 = (Graphics2D) g;

    // draw background
    g2.setColor(getBackground());
    g2.fillRect(0,  0, getWidth(), getHeight());

    // draw image and highlights
    if (batch != null)
    {
      // draw image
      Image scaledBatch = batch.getScaledInstance((int)(scale*batch.getWidth()), -1, Image.SCALE_SMOOTH);

      dOriginX = dCenterX - (int)(scale * wCenterX);
      dOriginY = dCenterY - (int)(scale * wCenterY);

      g2.drawImage(scaledBatch, dOriginX, dOriginY, null);

      //// draw highlights
      //if (highlight)
      //{
        //if (batchState.getCurrentField() != null && batchState.getCurrentRecord() >= 0)
        //{
          //// get position in image coordinates
          //int i_x1 = batchState.getCurrentField().getxCoord();
          //int i_x2 = i_x1 + batchState.getCurrentField().getWidth();
          //int i_y1 = batchState.getBatch().getProject().getFirstYCoord()
              //+ batchState.getCurrentRecord() * batchState.getBatch().getProject().getRecordHeight();
          //int i_y2 = i_y1 + batchState.getBatch().getProject().getRecordHeight();

          //Point2D i_point1 = new Point2D.Double(i_x1, i_y1);
          //Point2D i_point2 = new Point2D.Double(i_x2, i_y2);

          //// transform to device coordinates
          //Point2D d_point1 = getDevicePoint(i_point1);
          //Point2D d_point2 = getDevicePoint(i_point2);

          //rectangle.setFrameFromDiagonal(d_point1, d_point2);

          //// draw rectangle
          //g2.setColor(HIGHLIGHT_COLOR);
          //g2.fill(rectangle);
        //}
      //}
    }
  }

  /**
   * Loads an image from the given URL
   *
   * @param batchUrl
   * @return the unscaled BufferedImage | an empty image if we encounter an error
   */
  private BufferedImage loadImage(URL batchUrl) {
    try {
      return ImageIO.read(batchUrl);
    } catch (IOException e) {
      ClientLogManager.getLogger().log(Level.FINE, "STACKTRACE: ", e);
      return NULL_IMAGE;
    }
  }

  private void initDrag() {
    isDragging = false;
    wDragStartX = 0;
    wDragStartY = 0;
    wDragStartOriginX = 0;
    wDragStartOriginY = 0;
  }
}
