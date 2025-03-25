/*
 * @(#)GroupFigure.java
 *
 * Copyright (c) 1996-2010 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.draw.figure;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.Map;
import javax.swing.Action;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.connector.Connector;
import org.jhotdraw.draw.tool.Tool;
import org.jhotdraw.utils.geom.Geom;

/** A {@link org.jhotdraw.draw.figure.Figure} which groups a collection of figures. */
public class GroupFigure extends AbstractAttributedCompositeFigure {

  private static final long serialVersionUID = 1L;

  public GroupFigure() {
    setConnectable(false);
  }

  /**
   * This is a default implementation that chops the point at the rectangle returned by getBounds()
   * of the figure.
   *
   * <p>Figures which have a non-rectangular shape need to override this method.
   *
   * <p>FIXME Invoke chop on each child and return the closest point.
   */
  public Point2D.Double chop(Point2D.Double from) {
    Rectangle2D.Double r = getBounds();
    return Geom.angleToPoint(r, Geom.pointToAngle(r, from));
  }

  /** Returns true if all children of the group are transformable. */
  @Override
  public boolean isTransformable() {
    for (Figure f : children) {
      if (!f.isTransformable()) {
        return false;
      }
    }
    return true;
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
  public boolean contains(Double p) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'contains'");
  }
}
