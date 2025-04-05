package org.jhotdraw.draw.figure;

/*
 * Copyright (C) 2015 JHotDraw.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
import static org.junit.jupiter.api.Assertions.*;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import org.jhotdraw.draw.DefaultDrawing;
import org.jhotdraw.draw.Drawing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author tw
 */
public class AbstractFigureTest {

  MockAbstractFigure mock;
  FigureChangeSupport changeListener;
  FigureEventDispatcher eventDispatcher;
  Drawing draw;

  @BeforeEach
  public void init() {
    this.mock = new MockAbstractFigure();
    this.eventDispatcher = new FigureEventDispatcher(mock);
    this.draw = new DefaultDrawing();
    this.changeListener = mock.getChangeSupport();

    // Réinitialisez changingDepth
    while (changeListener.getChangingDepth() > 0) {
      changeListener.changed();
    }
  }

  @Test
  void testGetBounds() {
    Rectangle2D.Double bounds = mock.getBounds();
    assertEquals(0, bounds.x);
    assertEquals(0, bounds.y);
    assertEquals(100, bounds.width);
    assertEquals(100, bounds.height);
  }

  @Test
  void testContains() {
    Point2D.Double insidePoint = new Point2D.Double(50, 50);
    Point2D.Double outsidePoint = new Point2D.Double(150, 150);

    assertTrue(mock.contains(insidePoint));
    assertFalse(mock.contains(outsidePoint));
  }

  @Test
  void testSetAndGetProperties() {
    mock.setSelectable(false);
    assertFalse(mock.isSelectable());

    mock.setRemovable(false);
    assertFalse(mock.isRemovable());

    mock.setVisible(false);
    assertFalse(mock.isVisible());

    mock.setConnectable(false);
    assertFalse(mock.isConnectable());

    mock.setDraggable(false);
    assertFalse(mock.isDraggable());

    mock.setTransformable(false);
    assertFalse(mock.isTransformable());
  }

  @Test
  void testModifiedState() {
    assertFalse(mock.isModified());
    mock.setModified();
    assertTrue(mock.isModified());
    mock.resetModified();
    assertFalse(mock.isModified());
  }

  @Test
  void testClone() {
    MockAbstractFigure clone = (MockAbstractFigure) mock.clone();
    assertNotSame(mock, clone);
    assertEquals(mock.getBounds(), clone.getBounds());
    assertEquals(mock.isSelectable(), clone.isSelectable());
    assertEquals(mock.isRemovable(), clone.isRemovable());
    assertEquals(mock.isVisible(), clone.isVisible());
  }

  @Test
  void testFireAreaInvalidated() {
    // Vérifie que fireAreaInvalidated peut être appelé sans erreur
    mock.fireAreaInvalidated();
  }

  @Test
  void testFireFigureChanged() {
    // Vérifie que fireFigureChanged peut être appelé sans erreur
    mock.fireFigureChanged(mock.getBounds());
  }

  @Test
  void testWillChange() {
    // Vérifie que la profondeur de changement (changingDepth) augmente
    assertEquals(0, changeListener.getChangingDepth());
    mock.willChange();
    assertEquals(1, changeListener.getChangingDepth());
    mock.willChange();
    assertEquals(2, changeListener.getChangingDepth());
  }

  @Test
  public void testWillChangeChangedEvents() {
    assertEquals(changeListener.getChangingDepth(), 0);
    mock.willChange();
    assertEquals(changeListener.getChangingDepth(), 1);
    mock.willChange();
    assertEquals(changeListener.getChangingDepth(), 2);
    mock.changed();
    assertEquals(changeListener.getChangingDepth(), 1);
    mock.changed();
    assertEquals(changeListener.getChangingDepth(), 0);
  }

  // add test pour les notification
  @Test
  public void addNotifyTest() {
    // Initial state
    assertNull(mock.getDrawing());

    // Add the figure to drawing
    this.mock.addNotify(draw);

    // Verify drawing was set
    assertEquals(draw, mock.getDrawing());
  }
}
