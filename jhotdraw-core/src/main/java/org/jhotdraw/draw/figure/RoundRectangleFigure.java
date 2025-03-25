/*
 * @(#)RoundRectangleFigure.java
 *
 * Copyright (c) 1996-2010 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.draw.figure;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Collection;
import java.util.Map;
import javax.swing.Action;
import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.connector.ChopRoundRectangleConnector;
import org.jhotdraw.draw.connector.Connector;
import org.jhotdraw.draw.handle.Handle;
import org.jhotdraw.draw.handle.RoundRectangleRadiusHandle;
import org.jhotdraw.draw.tool.Tool;
import org.jhotdraw.utils.geom.Geom;

/**
 * A {@link Figure} with a rounded rectangular shape.
 *
 * <p>This figure has two JavaBeans properties {@code arcWidth} and {@code arcHeight} which specify
 * the corner radius.
 *
 * <p>This figure creates a {@link RoundRectangleRadiusHandle} which allows to interactively change
 * the corner radius.
 */
public class RoundRectangleFigure extends AbstractAttributedFigure {

  private static final long serialVersionUID = 1L;

  /** Identifies the {@code arcWidth} JavaBeans property. */
  public static final String ARC_WIDTH_PROPERTY = "arcWidth";

  /** Identifies the {@code arcHeight} JavaBeans property. */
  public static final String ARC_HEIGHT_PROPERTY = "arcHeight";

  protected RoundRectangle2D.Double roundrect;
  public static final double DEFAULT_ARC = 20;

  public RoundRectangleFigure() {
    this(0, 0, 0, 0);
  }

  public RoundRectangleFigure(double x, double y, double width, double height) {
    roundrect = new RoundRectangle2D.Double(x, y, width, height, DEFAULT_ARC, DEFAULT_ARC);
  }

  // DRAWING
  @Override
  public void drawFill(Graphics2D g) {
    RoundRectangle2D.Double r = (RoundRectangle2D.Double) roundrect.clone();
    double grow =
        AttributeKeys.getPerpendicularFillGrowth(this, AttributeKeys.getScaleFactorFromGraphics(g));
    r.x -= grow;
    r.y -= grow;
    r.width += grow * 2;
    r.height += grow * 2;
    r.arcwidth += grow * 2;
    r.archeight += grow * 2;
    if (r.width > 0 && r.height > 0) {
      g.fill(r);
    }
  }

  @Override
  public void drawStroke(Graphics2D g) {
    RoundRectangle2D.Double r = (RoundRectangle2D.Double) roundrect.clone();
    double grow =
        AttributeKeys.getPerpendicularDrawGrowth(this, AttributeKeys.getScaleFactorFromGraphics(g));
    r.x -= grow;
    r.y -= grow;
    r.width += grow * 2;
    r.height += grow * 2;
    r.arcwidth += grow * 2;
    r.archeight += grow * 2;
    if (r.width > 0 && r.height > 0) {
      g.draw(r);
    }
  }

  // SHAPE AND BOUNDS
  @Override
  public Rectangle2D.Double getBounds(double scale) {
    return (Rectangle2D.Double) roundrect.getBounds2D();
  }

  @Override
  public Rectangle2D.Double getDrawingArea(double scale) {
    Rectangle2D.Double r = (Rectangle2D.Double) roundrect.getBounds2D();
    double grow = AttributeKeys.getPerpendicularHitGrowth(this, scale) + 1;
    Geom.grow(r, grow, grow);
    return r;
  }

  /** Gets the arc width. */
  public double getArcWidth() {
    return roundrect.arcwidth;
  }

  /** Gets the arc height. */
  public double getArcHeight() {
    return roundrect.archeight;
  }

  /** Sets the arc width. */
  public void setArcWidth(double newValue) {
    double oldValue = roundrect.arcwidth;
    roundrect.arcwidth = newValue;
  }

  /** Sets the arc height. */
  public void setArcHeight(double newValue) {
    double oldValue = roundrect.archeight;
    roundrect.archeight = newValue;
  }

  /** Convenience method for setting both the arc width and the arc height. */
  public void setArc(double width, double height) {
    setArcWidth(width);
    setArcHeight(height);
  }

  /** Checks if a Point2D.Double is inside the figure. */
  @Override
  public boolean contains(Point2D.Double p, double scaleDenominator) {
    RoundRectangle2D.Double r = (RoundRectangle2D.Double) roundrect.clone();
    double grow = AttributeKeys.getPerpendicularHitGrowth(this, scaleDenominator);
    r.x -= grow;
    r.y -= grow;
    r.width += grow * 2;
    r.height += grow * 2;
    r.arcwidth += grow * 2;
    r.archeight += grow * 2;
    return r.contains(p);
  }

  @Override
  public void setBounds(Point2D.Double anchor, Point2D.Double lead) {
    roundrect.x = Math.min(anchor.x, lead.x);
    roundrect.y = Math.min(anchor.y, lead.y);
    roundrect.width = Math.max(0.1, Math.abs(lead.x - anchor.x));
    roundrect.height = Math.max(0.1, Math.abs(lead.y - anchor.y));
  }

  /**
   * Transforms the figure.
   *
   * @param tx The transformation.
   */
  @Override
  public void transform(AffineTransform tx) {
    Point2D.Double anchor = getStartPoint();
    Point2D.Double lead = getEndPoint();
    setBounds(
        (Point2D.Double) tx.transform(anchor, anchor), (Point2D.Double) tx.transform(lead, lead));
  }

  // EDITING
  @Override
  public Collection<Handle> createHandles(int detailLevel) {
    Collection<Handle> handles = super.createHandles(detailLevel);
    handles.add(new RoundRectangleRadiusHandle(this));
    return handles;
  }

  @Override
  public void restoreTransformTo(Object geometry) {
    RoundRectangle2D.Double r = (RoundRectangle2D.Double) geometry;
    roundrect.x = r.x;
    roundrect.y = r.y;
    roundrect.width = r.width;
    roundrect.height = r.height;
  }

  @Override
  public Object getTransformRestoreData() {
    return roundrect.clone();
  }

  // CONNECTING
  @Override
  public Connector findConnector(Point2D.Double p, ConnectionFigure prototype) {
    return new ChopRoundRectangleConnector(this);
  }

  @Override
  public Connector findCompatibleConnector(Connector c, boolean isStartConnector) {
    return new ChopRoundRectangleConnector(this);
  }

  // COMPOSITE FIGURES
  // CLONING
  @Override
  public RoundRectangleFigure clone() {
    RoundRectangleFigure that = (RoundRectangleFigure) super.clone();
    that.roundrect = (RoundRectangle2D.Double) this.roundrect.clone();
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

  // EVENT HANDLING
}
