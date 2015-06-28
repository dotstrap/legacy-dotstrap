package client.view.indexerframe;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;


interface DrawingShape {
  boolean contains(Graphics2D g2, double x, double y);
  void draw(Graphics2D g2);
  Rectangle2D getBounds(Graphics2D g2);
}

class DrawingRect implements DrawingShape {

  private Rectangle2D rect;
  private Color color;
  private boolean isVisible = true;

  public DrawingRect(Rectangle2D rect, Color color) {
    this.rect = rect;
    this.color = color;
  }

  public Rectangle2D getRect(){
    return rect;
  }

  public void setRect(Rectangle2D rect){
    this.rect = rect;
  }

  public Color getColor() {
    return this.color;
  }

  public DrawingRect setColor(Color invertColor) {
    color = invertColor;
    if(!isVisible){
      setVisible(false);
    }
    return this;
  }

  public boolean isVisible() {
    return isVisible;
  }

  public DrawingRect setVisible(boolean isVisible){
    if(isVisible == false)
      color = new Color(color.getRed(), color.getGreen(), color.getBlue(), 0);
    if(isVisible == true)
      color = new Color(color.getRed(), color.getGreen(), color.getBlue(), 100);
    this.isVisible = isVisible;
    return this;
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

}

class DrawingImage implements DrawingShape {

  private BufferedImage image;
  private Rectangle2D rect;

  public DrawingImage(BufferedImage image, Rectangle2D rect) {
    this.image = image;
    this.rect = rect;
  }

  public BufferedImage getImage() {
    return this.image;
  }

  public DrawingImage setImage(BufferedImage img) {
    image = img;
    return this;
  }

  public Rectangle2D getRect() {
    return this.rect;
  }

  public void setRect(Rectangle2D rect) {
    this.rect = rect;
  }

  public DrawingImage invert(BufferedImage img) {
    image = new RescaleOp(-1.0f, 255f, null).filter(img, null);
    return this;
  }

  @Override
  public boolean contains(Graphics2D g2, double x, double y) {
    return rect.contains(x, y);
  }

  @Override
  public void draw(Graphics2D g2) {
    g2.drawImage(image, (int)rect.getMinX(), (int)rect.getMinY(), (int)rect.getMaxX(), (int)rect.getMaxY(),
          0, 0, image.getWidth(null), image.getHeight(null), null);
  }

  @Override
    public Rectangle2D getBounds(Graphics2D g2) {
    return rect.getBounds2D();
  }
}
