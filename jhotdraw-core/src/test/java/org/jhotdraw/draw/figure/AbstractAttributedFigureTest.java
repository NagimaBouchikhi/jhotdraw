package org.jhotdraw.draw.figure;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.geom.Rectangle2D;
import org.junit.jupiter.api.Test;

class AbstractAttributedFigureTest {

  @Test
  void testDrawMethods() {
    // Crée une instance du mock
    MockAttributedFigure mockFigure = new MockAttributedFigure();

    // Vérifie les méthodes de dessin
    mockFigure.drawFill(null); // Cela devrait afficher "drawFill called"
    mockFigure.drawStroke(null); // Cela devrait afficher "drawStroke called"
    mockFigure.drawText(null); // Cela devrait afficher "drawText called"
  }

  @Test
  void testGetBounds() {
    // Crée une instance du mock
    MockAttributedFigure mockFigure = new MockAttributedFigure();

    // Vérifie les limites
    Rectangle2D.Double bounds = mockFigure.getBounds(1.0);
    assertEquals(0, bounds.x);
    assertEquals(0, bounds.y);
    assertEquals(100, bounds.width);
    assertEquals(100, bounds.height);
  }
}
