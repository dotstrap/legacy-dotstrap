package client.view.indexerframe;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;


/////////////////
// Drawing Shape
/////////////////
interface DrawingShape {
  boolean contains(Graphics2D g2, double x, double y);
  void draw(Graphics2D g2);
  Rectangle2D getBounds(Graphics2D g2);
}

/////////////////
//Implementations
/////////////////
class DrawingRect implements DrawingShape {

  private Rectangle2D rect;
  private Color color;
  private boolean visible = true;
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

  public Rectangle2D getRect(){
    return rect;
  }

  public void setRect(Rectangle2D rect){
    this.rect = rect;
  }
  public DrawingRect invert() {
    color = new Color(0, 0, 255, 100);
    if(!visible){
      setVisible(false);
    }
    return this;
  }

  public DrawingRect setVisible(boolean visible){
    if(visible == false)
      color = new Color(color.getRed(), color.getGreen(), color.getBlue(), 0);
    if(visible == true)
      color = new Color(color.getRed(), color.getGreen(), color.getBlue(), 100);
    this.visible = visible;
    return this;
  }

  public boolean isVisible() {
    return visible;
  }
}

class DrawingImage implements DrawingShape {

  private Image image;
  private Rectangle2D rect;

  public DrawingImage(Image image, Rectangle2D rect) {
    this.image = image;
    this.rect = rect;
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

  public DrawingImage invert(){
    BufferedImage bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
      Graphics2D bGr = bimage.createGraphics();
      bGr.drawImage(image, 0, 0, null);
      bGr.dispose();
      //image = Utils.invertBufferedImage(bimage);
    return this;
  }
}
