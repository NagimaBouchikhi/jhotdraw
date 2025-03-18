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
package org.jhotdraw.draw.figure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author tw
 */
public class AbstractFigureTest {

  AbstractFigureMock mock;

  @BeforeEach
  public void init() {
    this.mock = new AbstractFigureMock();
    // this.grph = new Graphics2D();
  }

  @Test
  public void testChangedWithoutWillChange() {
    assertThrows(IllegalStateException.class, () -> mock.changed());
  }

  @Test
  public void testWillChangeChangedEvents() {
    assertEquals(mock.getChangingDepth(), 0);
    mock.willChange();
    assertEquals(mock.getChangingDepth(), 1);
    mock.willChange();
    assertEquals(mock.getChangingDepth(), 2);
    mock.changed();
    assertEquals(mock.getChangingDepth(), 1);
    mock.changed();
    assertEquals(mock.getChangingDepth(), 0);
  }
}
