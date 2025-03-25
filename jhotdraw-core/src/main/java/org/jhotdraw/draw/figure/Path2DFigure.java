/*
 * @(#)LineFigure.java
 *
 * Copyright (c) 1996-2010 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.draw.figure;

import static org.jhotdraw.draw.AttributeKeys.STROKE_MITER_LIMIT;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.geom.Point2D.Double;
import java.util.*;
import javax.swing.Action;
import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.connector.Connector;
import org.jhotdraw.draw.handle.Handle;
import org.jhotdraw.draw.handle.TrackingHandle;
import org.jhotdraw.draw.tool.Tool;
import org.jhotdraw.utils.geom.GrowStroke;
import org.jhotdraw.utils.geom.path.MutablePath2D;

public class Path2DFigure extends AbstractAttributedFigure {

  private static final long serialVersionUID = 1L;
  private final MutablePath2D path = new MutablePath2D();

  public Path2DFigure() {
    setConnectable(false);
  }

  @Override
  public boolean contains(Point2D.Double p, double scaleDenominator) {
    return path.contains(p);
  }

  @Override
  public Collection<Handle> createHandles(int detailLevel) {
    if (detailLevel == -1 || detailLevel == 0) {
      return super.createHandles(detailLevel);
    } else {
      List<Handle> handles = new ArrayList<>();
      switch (detailLevel) {
        case 1:
          for (int i = 0; i < path.size(); i++) {
            int idx = i;
            handles.add(new TrackingHandle(
                this,
                () -> path.getNodePoint(idx),
                p -> path.changeNode(idx, node -> node.withPoint(p.x, p.y))));
          }
          break;
      }
      return handles;
    }
  }

  @Override
  public Rectangle2D.Double getBounds(double scale) {
    return (Rectangle2D.Double) path.getBounds2D();
  }

  @Override
  public Object getTransformRestoreData() {
    throw new UnsupportedOperationException("Not supported yet."); // Generated from
    // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
  }

  @Override
  public boolean handleMouseClick(Point2D.Double p, MouseEvent evt, DrawingView view) {
    //    if (evt.getClickCount() == 2 && view.getHandleDetailLevel() == 0) {
    //      willChange();
    //      final int index = splitSegment(p, (float) (5f / view.getScaleFactor()));
    //      if (index != -1) {
    //        final BezierPath.Node newNode = getNode(index);
    //        fireUndoableEditHappened(
    //            new AbstractUndoableEdit() {
    //              private static final long serialVersionUID = 1L;
    //
    //              @Override
    //              public void redo() throws CannotRedoException {
    //                super.redo();
    //                willChange();
    //                addNode(index, newNode);
    //                changed();
    //              }
    //
    //              @Override
    //              public void undo() throws CannotUndoException {
    //                super.undo();
    //                willChange();
    //                removeNode(index);
    //                changed();
    //              }
    //            });
    //        changed();
    //        return true;
    //      }
    //    }
    return false;
  }

  @Override
  public void restoreTransformTo(Object restoreData) {
    throw new UnsupportedOperationException("Not supported yet."); // Generated from
    // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
  }

  @Override
  public void transform(AffineTransform tx) {
    throw new UnsupportedOperationException("Not supported yet."); // Generated from
    // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
  }

  @Override
  public void drawStroke(Graphics2D g) {
    // if (isClosed()) {
    double grow =
        AttributeKeys.getPerpendicularDrawGrowth(this, AttributeKeys.getScaleFactorFromGraphics(g));
    if (grow == 0d) {
      g.draw(path);
    } else {
      GrowStroke gs = new GrowStroke(
          grow,
          AttributeKeys.getStrokeTotalWidth(this, AttributeKeys.getScaleFactorFromGraphics(g))
              * attr().get(STROKE_MITER_LIMIT));
      g.draw(gs.createStrokedShape(path));
    }
    //    } else {
    //      g.draw(getCappedPath());
    //    }
    //    drawCaps(g);
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
