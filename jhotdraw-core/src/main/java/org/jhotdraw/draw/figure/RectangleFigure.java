/*
 * @(#)RectangleFigure.java
 *
 * Copyright (c) 1996-2010 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.draw.figure;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import java.awt.geom.Point2D.Double;
import java.util.Collection;
import java.util.Map;
import javax.swing.Action;
import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.connector.Connector;
import org.jhotdraw.draw.tool.Tool;
import org.jhotdraw.utils.geom.Geom;

public class RectangleFigure extends AbstractAttributedFigure {

  private static final long serialVersionUID = 1L;
  protected Rectangle2D.Double rectangle;

  public RectangleFigure() {
    this(0, 0, 0, 0);
  }

  public RectangleFigure(double x, double y, double width, double height) {
    rectangle = new Rectangle2D.Double(x, y, width, height);
  }

  @Override
  public void drawFill(Graphics2D g) {
    Rectangle2D.Double r = (Rectangle2D.Double) rectangle.clone();
    double grow =
        AttributeKeys.getPerpendicularFillGrowth(this, AttributeKeys.getScaleFactorFromGraphics(g));
    Geom.grow(r, grow, grow);
    g.fill(r);
  }

  @Override
  public void drawStroke(Graphics2D g) {
    Rectangle2D.Double r = (Rectangle2D.Double) rectangle.clone();
    double grow =
        AttributeKeys.getPerpendicularDrawGrowth(this, AttributeKeys.getScaleFactorFromGraphics(g));
    Geom.grow(r, grow, grow);
    g.draw(r);
  }

  @Override
  public Rectangle2D.Double getBounds(double scale) {
    Rectangle2D.Double bounds = (Rectangle2D.Double) rectangle.clone();
    return bounds;
  }

  @Override
  public Rectangle2D.Double getDrawingArea(double scale) {
    Rectangle2D.Double r = (Rectangle2D.Double) rectangle.clone();
    double grow = AttributeKeys.getPerpendicularHitGrowth(this, scale) + 1d;
    Geom.grow(r, grow, grow);
    return r;
  }

  @Override
  public boolean contains(Point2D.Double p, double scaleDenominator) {
    Rectangle2D.Double r = (Rectangle2D.Double) rectangle.clone();
    double grow = AttributeKeys.getPerpendicularHitGrowth(this, scaleDenominator) + 1d;
    Geom.grow(r, grow, grow);
    return r.contains(p);
  }

  @Override
  public void setBounds(Point2D.Double anchor, Point2D.Double lead) {
    rectangle.x = Math.min(anchor.x, lead.x);
    rectangle.y = Math.min(anchor.y, lead.y);
    rectangle.width = Math.max(0.1, Math.abs(lead.x - anchor.x));
    rectangle.height = Math.max(0.1, Math.abs(lead.y - anchor.y));
  }

  @Override
  public void transform(AffineTransform tx) {
    Point2D.Double anchor = getStartPoint();
    Point2D.Double lead = getEndPoint();
    setBounds(
        (Point2D.Double) tx.transform(anchor, anchor), (Point2D.Double) tx.transform(lead, lead));
  }

  @Override
  public void restoreTransformTo(Object geometry) {
    rectangle.setRect((Rectangle2D.Double) geometry);
  }

  @Override
  public Object getTransformRestoreData() {
    return rectangle.clone();
  }

  @Override
  public RectangleFigure clone() {
    RectangleFigure that = (RectangleFigure) super.clone();
    that.rectangle = (Rectangle2D.Double) this.rectangle.clone();
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
