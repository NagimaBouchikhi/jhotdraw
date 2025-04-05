package org.jhotdraw.draw.figure;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import javax.swing.Action;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.connector.Connector;
import org.jhotdraw.draw.handle.Handle;
import org.jhotdraw.draw.tool.Tool;

public class MockAttributedFigure extends AbstractAttributedFigure {

  private static final long serialVersionUID = 1L;
  private Rectangle2D.Double bounds = new Rectangle2D.Double(0, 0, 100, 100);

  @Override
  public void drawFill(Graphics2D g) {
    g.fill(bounds);
  }

  @Override
  public void drawStroke(Graphics2D g) {
    g.draw(bounds);
  }

  @Override
  public void drawText(Graphics2D g) {
    // No text by default
  }

  @Override
  public Rectangle2D.Double getBounds(double scale) {
    return (Rectangle2D.Double) bounds.clone();
  }

  @Override
  public Rectangle2D.Double getBounds() {
    return getBounds(1.0);
  }

  @Override
  public Rectangle2D.Double getDrawingArea() {
    return getDrawingArea(1.0);
  }

  @Override
  public Rectangle2D.Double getDrawingArea(double scale) {
    Rectangle2D.Double area = (Rectangle2D.Double) bounds.clone();
    area.add(bounds.getMaxX(), bounds.getMaxY());
    return area;
  }

  @Override
  public Collection<Handle> createHandles(int detailLevel) {
    return Collections.emptyList();
  }

  @Override
  public void setBounds(Double start, Double end) {
    bounds = new Rectangle2D.Double(
        Math.min(start.x, end.x),
        Math.min(start.y, end.y),
        Math.abs(end.x - start.x),
        Math.abs(end.y - start.y));
  }

  @Override
  public Double getStartPoint() {
    return new Double(bounds.x, bounds.y);
  }

  @Override
  public Double getEndPoint() {
    return new Double(bounds.x + bounds.width, bounds.y + bounds.height);
  }

  @Override
  public boolean contains(Double p, double scale) {
    return bounds.contains(p);
  }

  @Override
  public boolean contains(Double p) {
    return contains(p, 1.0);
  }

  @Override
  public Object getTransformRestoreData() {
    return bounds.clone();
  }

  @Override
  public void restoreTransformTo(Object restoreData) {
    bounds = (Rectangle2D.Double) ((Rectangle2D.Double) restoreData).clone();
  }

  @Override
  public void transform(AffineTransform tx) {
    Point2D.Double start = getStartPoint();
    Point2D.Double end = getEndPoint();

    tx.transform(start, start);
    tx.transform(end, end);

    setBounds(start, end);
  }

  @Override
  public Cursor getCursor(Double p, double scale) {
    return Cursor.getDefaultCursor();
  }

  @Override
  public Collection<Action> getActions(Double p) {
    return Collections.emptyList();
  }

  @Override
  public Tool getTool(Double p) {
    return null;
  }

  @Override
  public Connector findConnector(Double p, ConnectionFigure prototype) {
    return null;
  }

  @Override
  public Connector findCompatibleConnector(Connector c, boolean isStartConnector) {
    return null;
  }

  @Override
  public Collection<Connector> getConnectors(ConnectionFigure prototype) {
    return Collections.emptyList();
  }

  @Override
  public boolean includes(Figure figure) {
    return false;
  }

  @Override
  public Figure findFigureInside(Double p) {
    return contains(p) ? this : null;
  }

  @Override
  public void remap(Map<Figure, Figure> oldToNew, boolean disconnectIfNotInMap) {
    // no need implementation figure
  }

  @Override
  public boolean handleDrop(Double p, Collection<Figure> droppedFigures, DrawingView view) {
    return false;
  }

  @Override
  public boolean handleMouseClick(Double p, MouseEvent evt, DrawingView view) {
    return false;
  }

  @Override
  public void willChange() {
    // no need implementation
  }

  @Override
  public void changed() {
    // no need implementation
  }

  @Override
  public void invalidate() {
    // no need implementation
  }

  @Override
  public void validate() {
    // no need implementation
  }
}
