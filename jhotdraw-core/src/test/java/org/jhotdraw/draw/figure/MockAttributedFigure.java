package org.jhotdraw.draw.figure;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.swing.Action;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.connector.Connector;
import org.jhotdraw.draw.handle.BoundsOutlineHandle;
import org.jhotdraw.draw.handle.Handle;
import org.jhotdraw.draw.handle.ResizeHandleKit;
import org.jhotdraw.draw.tool.Tool;

public class MockAttributedFigure extends AbstractAttributedFigure {

  private static final long serialVersionUID = 1L;
  private Rectangle2D.Double bounds = new Rectangle2D.Double(0, 0, 100, 100);
  private List<String> methodCalls = new ArrayList<>();
  private FigureChangeSupport changeSupport;

  public MockAttributedFigure() {
    super();
    this.changeSupport = new FigureChangeSupport(this);
    // Initialize default attributes for testing
    attr().set(org.jhotdraw.draw.AttributeKeys.FILL_COLOR, Color.RED);
    attr().set(org.jhotdraw.draw.AttributeKeys.STROKE_COLOR, Color.BLACK);
    attr().set(org.jhotdraw.draw.AttributeKeys.STROKE_WIDTH, 1.0);
    attr().set(org.jhotdraw.draw.AttributeKeys.TEXT_COLOR, Color.BLUE);
  }

  public boolean wasMethodCalled(String methodName) {
    return methodCalls.contains(methodName);
  }

  public FigureChangeSupport getChangeSupport() {
    return changeSupport;
  }

  public void resetMethodCalls() {
    methodCalls.clear();
  }

  public List<String> getMethodCalls() {
    return new ArrayList<>(methodCalls);
  }

  @Override
  public Rectangle2D.Double getBounds() {
    methodCalls.add("getBounds");
    return bounds;
  }

  @Override
  public void drawFill(Graphics2D g) {
    methodCalls.add("drawFill");
  }

  @Override
  public void drawStroke(Graphics2D g) {
    methodCalls.add("drawStroke");
  }

  @Override
  public void drawText(Graphics2D g) {
    methodCalls.add("drawText");
  }

  @Override
  public Rectangle2D.Double getDrawingArea() {
    methodCalls.add("getDrawingArea");
    // Add some padding to simulate stroke width
    double padding = 2.0;
    return new Rectangle2D.Double(
        bounds.x - padding,
        bounds.y - padding,
        bounds.width + 2 * padding,
        bounds.height + 2 * padding);
  }

  @Override
  public Collection<Handle> createHandles(int detailLevel) {
    methodCalls.add("createHandles");
    List<Handle> handles = new ArrayList<>();

    if (detailLevel < 0) {
      // For negative detail level, add a BoundsOutlineHandle
      handles.add(new BoundsOutlineHandle(this));
    } else if (detailLevel == 0) {
      // For detail level 0, add resize handles
      handles.add(ResizeHandleKit.northWest(this));
      handles.add(ResizeHandleKit.northEast(this));
      handles.add(ResizeHandleKit.southWest(this));
      handles.add(ResizeHandleKit.southEast(this));

      // Optionally add more handles like these:
      handles.add(ResizeHandleKit.north(this));
      handles.add(ResizeHandleKit.south(this));
      handles.add(ResizeHandleKit.east(this));
      handles.add(ResizeHandleKit.west(this));
    }

    return handles;
  }

  @Override
  public void setBounds(Point2D.Double start, Point2D.Double end) {
    methodCalls.add("setBounds");
    bounds.setFrameFromDiagonal(start, end);
  }

  @Override
  public Double getStartPoint() {
    methodCalls.add("getStartPoint");
    return new Point2D.Double(bounds.x, bounds.y);
  }

  @Override
  public Double getEndPoint() {
    methodCalls.add("getEndPoint");
    return new Point2D.Double(bounds.x + bounds.width, bounds.y + bounds.height);
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
    changeSupport.willChange();
  }

  @Override
  public void changed() {
    changeSupport.changed();
    methodCalls.add("changed");
  }

  @Override
  public void invalidate() {
    methodCalls.add("invalidate");
  }

  @Override
  public void validate() {
    methodCalls.add("validate");
  }

  @Override
  public java.awt.geom.Rectangle2D.Double getBounds(double scale) {
    methodCalls.add("getBounds with scale");
    return new Rectangle2D.Double(bounds.x, bounds.y, bounds.width, bounds.height);
  }
}
