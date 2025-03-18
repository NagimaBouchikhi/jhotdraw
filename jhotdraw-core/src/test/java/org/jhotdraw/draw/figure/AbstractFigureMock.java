package org.jhotdraw.draw.figure;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**Mock defini pour donner des comportements par default a cette classe abstraite */
public class AbstractFigureMock extends AbstractAttributedFigure {

  @Override
  public void draw(Graphics2D g) {}

  @Override
  public Rectangle2D.Double getBounds(double scale) {
    return null;
  }

  @Override
  public boolean contains(Point2D.Double p) {
    return true;
  }

  @Override
  public Object getTransformRestoreData() {
    return null;
  }

  @Override
  public void restoreTransformTo(Object restoreData) {}

  @Override
  public void transform(AffineTransform tx) {}

  @Override
  public Rectangle2D.Double getDrawingArea(double factor) {
    return null;
  }

  @Override
  public Attributes attr() {
    return null;
  }

  @Override
  public void drawFill(Graphics2D g) {}

  @Override
  public void drawStroke(Graphics2D g) {}

  @Override
  public boolean contains(Point2D.Double p, double scaleDenominator) {
    return false;
  }

  @Override
  public void setDraggable() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'setDraggable'");
  }
}
