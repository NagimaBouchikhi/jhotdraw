/*
 * @(#)StraightLineFigure.java
 *
 * Copyright (c) 1996-2010 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.samples.mini;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import java.awt.geom.Point2D.Double;
import java.util.Collection;
import java.util.Map;
import javax.swing.Action;
import org.jhotdraw.draw.*;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.connector.Connector;
import org.jhotdraw.draw.figure.AbstractAttributedFigure;
import org.jhotdraw.draw.figure.ConnectionFigure;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.draw.tool.Tool;
import org.jhotdraw.utils.geom.Geom;

/**
 * Example showing the minimal amount of code needed to implement a {@code Figure} by extending
 * {@code AbstractAttributedFigure}.
 */
public class StraightLineFigure extends AbstractAttributedFigure {

  private static final long serialVersionUID = 1L;
  private Line2D.Double line;

  public StraightLineFigure() {
    line = new Line2D.Double();
  }

  @Override
  public void drawFill(Graphics2D g) {}

  @Override
  public void drawStroke(Graphics2D g) {
    g.draw(line);
  }

  @Override
  public void transform(AffineTransform ty) {
    Point2D.Double p1 = (Point2D.Double) line.getP1();
    Point2D.Double p2 = (Point2D.Double) line.getP2();
    line.setLine(ty.transform(p1, p1), ty.transform(p2, p2));
  }

  @Override
  public void setBounds(Point2D.Double start, Point2D.Double end) {
    line.setLine(start, end);
  }

  @Override
  public Rectangle2D.Double getBounds(double scale) {
    return (Rectangle2D.Double) line.getBounds2D();
  }

  @Override
  public Object getTransformRestoreData() {
    return line.clone();
  }

  @Override
  public void restoreTransformTo(Object restoreData) {
    line = (Line2D.Double) ((Line2D.Double) restoreData).clone();
  }

  @Override
  public boolean contains(Point2D.Double p, double scaleDenominator) {
    return Geom.lineContainsPoint(
        line.x1,
        line.y1,
        line.x2,
        line.y2,
        p.x,
        p.y,
        AttributeKeys.getStrokeTotalWidth(this, scaleDenominator));
  }

  @Override
  public StraightLineFigure clone() {
    StraightLineFigure that = (StraightLineFigure) super.clone();
    that.line = (Line2D.Double) this.line.clone();
    return that;
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

  @Override
  public boolean contains(Double p) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'contains'");
  }
}
