package org.jhotdraw.draw.figure;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.Map;
import javax.swing.Action;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.connector.Connector;
import org.jhotdraw.draw.tool.Tool;

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
    return getBounds(factor);
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
  public void setBounds(Double start, Double end) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'setBounds'");
  }

  @Override
  public Double getStartPoint() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getStartPoint'");
  }

  @Override
  public Double getEndPoint() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getEndPoint'");
  }

  @Override
  public Cursor getCursor(Double p, double scale) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getCursor'");
  }

  @Override
  public Collection<Action> getActions(Double p) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getActions'");
  }

  @Override
  public Tool getTool(Double p) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getTool'");
  }

  @Override
  public Connector findConnector(Double p, ConnectionFigure prototype) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findConnector'");
  }

  @Override
  public Connector findCompatibleConnector(Connector c, boolean isStartConnector) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findCompatibleConnector'");
  }

  @Override
  public Collection<Connector> getConnectors(ConnectionFigure prototype) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getConnectors'");
  }

  @Override
  public boolean includes(Figure figure) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'includes'");
  }

  @Override
  public Figure findFigureInside(Double p) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findFigureInside'");
  }

  @Override
  public void remap(Map<Figure, Figure> oldToNew, boolean disconnectIfNotInMap) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'remap'");
  }

  @Override
  public boolean handleDrop(Double p, Collection<Figure> droppedFigures, DrawingView view) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'handleDrop'");
  }

  @Override
  public boolean handleMouseClick(Double p, MouseEvent evt, DrawingView view) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'handleMouseClick'");
  }

  @Override
  public java.awt.geom.Rectangle2D.Double getBounds() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getBounds'");
  }

  @Override
  public java.awt.geom.Rectangle2D.Double getDrawingArea() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getDrawingArea'");
  }
}
