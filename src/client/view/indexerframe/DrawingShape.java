/**
 * DrawingShape.java
 * JRE v1.8.0_45
 * 
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.view.indexerframe;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

class DrawingImage implements DrawingShape {
  private BufferedImage image;
  private Rectangle2D rect;

  public DrawingImage(BufferedImage image, Rectangle2D rect) {
    this.image = image;
    this.rect = rect;
  }

  @Override
  public boolean contains(Graphics2D g2, double x, double y) {
    return rect.contains(x, y);
  }

  @Override
  public void draw(Graphics2D g2) {
    g2.drawImage(image, (int) rect.getMinX(), (int) rect.getMinY(),
        (int) rect.getMaxX(), (int) rect.getMaxY(), 0, 0, image.getWidth(null),
        image.getHeight(null), null);
  }

  @Override
  public Rectangle2D getBounds(Graphics2D g2) {
    return rect.getBounds2D();
  }

  public BufferedImage getImage() {
    return image;
  }

  public Rectangle2D getRect() {
    return rect;
  }

  public DrawingImage invert(BufferedImage img) {
    image = new RescaleOp(-1.0f, 255f, null).filter(img, null);
    return this;
  }

  public DrawingImage setImage(BufferedImage img) {
    image = img;
    return this;
  }

  public void setRect(Rectangle2D rect) {
    this.rect = rect;
  }
}


class DrawingRect implements DrawingShape {
  private Rectangle2D rect;
  private Color color;
  private boolean isVisible = true;

  public DrawingRect(Rectangle2D rect, Color color) {
    this.rect = rect;
    this.color = color;
  }

  @Override
  public boolean contains(Graphics2D g2, double x, double y) {
    return rect.contains(x, y);
  }

  @Override
  public void draw(Graphics2D g2) {
    g2.setColor(color);
    g2.fill(rect);
  }

  @Override
  public Rectangle2D getBounds(Graphics2D g2) {
    return rect.getBounds2D();
  }

  public Color getColor() {
    return color;
  }

  public Rectangle2D getRect() {
    return rect;
  }

  public boolean isVisible() {
    return isVisible;
  }

  public DrawingRect setColor(Color invertColor) {
    color = invertColor;
    if (!isVisible) {
      setVisible(false);
    }
    return this;
  }

  public void setRect(Rectangle2D rect) {
    this.rect = rect;
  }

  public DrawingRect setVisible(boolean isVisible) {
    color = (isVisible == true)
        ? new Color(color.getRed(), color.getGreen(), color.getBlue(), 100)
        : new Color(color.getRed(), color.getGreen(), color.getBlue(), 0);

    this.isVisible = isVisible;
    return this;
  }
}


/**
 * The Interface DrawingShape.
 */
interface DrawingShape {

  /**
   * Contains.
   *
   * @param g2 the g2
   * @param x the x
   * @param y the y
   * @return true, if successful
   */
  boolean contains(Graphics2D g2, double x, double y);

  /**
   * Draw.
   *
   * @param g2 the g2
   */
  void draw(Graphics2D g2);

  /**
   * Gets the bounds.
   *
   * @param g2 the g2
   * @return the bounds
   */
  Rectangle2D getBounds(Graphics2D g2);
}
