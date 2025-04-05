package org.jhotdraw.draw.figure;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import javax.swing.Action;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.connector.Connector;
import org.jhotdraw.draw.handle.Handle;
import org.jhotdraw.draw.tool.Tool;

public class MockAbstractFigure extends AbstractFigure {

  private static final long serialVersionUID = 1L;
  private Rectangle2D.Double bounds = new Rectangle2D.Double(0, 0, 100, 100);
  private FigureChangeSupport changeSupport;

  public MockAbstractFigure() {
    this.changeSupport = new FigureChangeSupport(this);
  }

  public FigureChangeSupport getChangeSupport() {
    return changeSupport;
  }

  @Override
  public Rectangle2D.Double getBounds() {
    return bounds;
  }

  @Override
  public void willChange() {
    changeSupport.willChange();
  }

  @Override
  public void changed() {
    changeSupport.changed();
  }

  @Override
  public Rectangle2D.Double getDrawingArea() {
    return bounds;
  }

  @Override
  public boolean contains(Point2D.Double p) {
    return bounds.contains(p);
  }

  @Override
  public void draw(Graphics2D g) {
    System.out.println("draw called");
  }

  @Override
  public void setBounds(Point2D.Double start, Point2D.Double end) {
    bounds.setFrameFromDiagonal(start, end);
    System.out.println("setBounds called with start=" + start + " and end=" + end);
  }

  @Override
  public Point2D.Double getStartPoint() {
    return new Point2D.Double(bounds.x, bounds.y);
  }

  @Override
  public Point2D.Double getEndPoint() {
    return new Point2D.Double(bounds.x + bounds.width, bounds.y + bounds.height);
  }

  @Override
  public Rectangle2D.Double getBounds(double scale) {
    return new Rectangle2D.Double(
        bounds.x * scale, bounds.y * scale, bounds.width * scale, bounds.height * scale);
  }

  @Override
  public Rectangle2D.Double getDrawingArea(double scale) {
    return getBounds(scale);
  }

  @Override
  public boolean contains(Point2D.Double p, double scale) {
    Rectangle2D.Double scaledBounds = getBounds(scale);
    return scaledBounds.contains(p);
  }

  @Override
  public Object getTransformRestoreData() {
    System.out.println("getTransformRestoreData called");
    return bounds.clone();
  }

  @Override
  public void restoreTransformTo(Object restoreData) {
    if (restoreData instanceof Rectangle2D.Double) {
      bounds = (Rectangle2D.Double) restoreData;
      System.out.println("restoreTransformTo called with restoreData=" + restoreData);
    }
  }

  @Override
  public void transform(AffineTransform tx) {
    bounds = (Rectangle2D.Double) tx.createTransformedShape(bounds).getBounds2D();
    System.out.println("transform called with tx=" + tx);
  }

  @Override
  public Collection<Handle> createHandles(int detailLevel) {
    System.out.println("createHandles called with detailLevel=" + detailLevel);
    return Collections.emptyList();
  }

  @Override
  public Cursor getCursor(Point2D.Double p, double scale) {
    System.out.println("getCursor called with p=" + p + " and scale=" + scale);
    return Cursor.getDefaultCursor();
  }

  @Override
  public Collection<Action> getActions(Point2D.Double p) {
    System.out.println("getActions called with p=" + p);
    return Collections.emptyList();
  }

  @Override
  public Tool getTool(Point2D.Double p) {
    System.out.println("getTool called with p=" + p);
    return null;
  }

  @Override
  public Connector findConnector(Point2D.Double p, ConnectionFigure prototype) {
    System.out.println("findConnector called with p=" + p + " and prototype=" + prototype);
    return null;
  }

  @Override
  public Connector findCompatibleConnector(Connector c, boolean isStartConnector) {
    System.out.println(
        "findCompatibleConnector called with c=" + c + " and isStartConnector=" + isStartConnector);
    return null;
  }

  @Override
  public Collection<Connector> getConnectors(ConnectionFigure prototype) {
    System.out.println("getConnectors called with prototype=" + prototype);
    return Collections.emptyList();
  }

  @Override
  public boolean includes(Figure figure) {
    System.out.println("includes called with figure=" + figure);
    return false;
  }

  @Override
  public Figure findFigureInside(Point2D.Double p) {
    System.out.println("findFigureInside called with p=" + p);
    return null;
  }

  @Override
  public void remap(Map<Figure, Figure> oldToNew, boolean disconnectIfNotInMap) {
    System.out.println("remap called with oldToNew=" + oldToNew + " and disconnectIfNotInMap="
        + disconnectIfNotInMap);
  }

  @Override
  public boolean handleDrop(Point2D.Double p, Collection<Figure> droppedFigures, DrawingView view) {
    System.out.println("handleDrop called with p=" + p + ", droppedFigures=" + droppedFigures
        + ", and view=" + view);
    return false;
  }

  @Override
  public boolean handleMouseClick(Point2D.Double p, MouseEvent evt, DrawingView view) {
    System.out.println(
        "handleMouseClick called with p=" + p + ", evt=" + evt + ", and view=" + view);
    return false;
  }

  @Override
  public Attributes attr() {
    System.out.println("attr called");
    return null;
  }
}
