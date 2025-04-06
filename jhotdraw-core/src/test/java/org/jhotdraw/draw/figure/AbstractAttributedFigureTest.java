package org.jhotdraw.draw.figure;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Collection;
import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.DefaultDrawing;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.handle.Handle;
import org.jhotdraw.utils.geom.Dimension2DDouble;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AbstractAttributedFigureTest {

  private MockAttributedFigure figure;
  private Drawing drawing;
  FigureChangeSupport changeListener;
  private Graphics2D graphics;
  private BufferedImage image;

  @BeforeEach
  public void setUp() {
    figure = new MockAttributedFigure();
    drawing = new DefaultDrawing();
    this.changeListener = figure.getChangeSupport();

    // Create a graphics context for testing draw methods
    image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
    graphics = image.createGraphics();
  }

  @Test
  public void testInitialState() {
    // Check that the figure has been properly initialized
    assertNotNull(figure.attr());
    assertNotNull(figure.attr().get(AttributeKeys.FILL_COLOR));
    assertNotNull(figure.attr().get(AttributeKeys.STROKE_COLOR));
    assertNotNull(figure.attr().get(AttributeKeys.TEXT_COLOR));
  }

  @Test
  void testWillChange() {
    // Vérifie que la profondeur de changement (changingDepth) augmente
    assertEquals(0, changeListener.getChangingDepth());
    figure.willChange();
    assertEquals(1, changeListener.getChangingDepth());
    figure.willChange();
    assertEquals(2, changeListener.getChangingDepth());
  }

  @Test
  public void testWillChangeChangedEvents() {
    assertEquals(changeListener.getChangingDepth(), 0);
    figure.willChange();
    assertEquals(changeListener.getChangingDepth(), 1);
    figure.willChange();
    assertEquals(changeListener.getChangingDepth(), 2);
    figure.changed();
    assertEquals(changeListener.getChangingDepth(), 1);
    figure.changed();
    assertEquals(changeListener.getChangingDepth(), 0);
  }

  @Test
  public void testDraw() {
    // Reset method call tracking
    figure.resetMethodCalls();

    // Call the draw method
    figure.draw(graphics);

    // Check that the appropriate drawing methods were called
    assertTrue(figure.wasMethodCalled("drawFill"));
    assertTrue(figure.wasMethodCalled("drawStroke"));
    assertTrue(figure.wasMethodCalled("drawText"));
  }

  @Test
  public void testDrawFillColor() {
    // Set fill color to null and check that drawFill is not called
    figure.resetMethodCalls();
    figure.attr().set(AttributeKeys.FILL_COLOR, null);
    figure.drawFillColor(graphics);
    assertFalse(figure.wasMethodCalled("drawFill"));

    // Set fill color and check that drawFill is called
    figure.resetMethodCalls();
    figure.attr().set(AttributeKeys.FILL_COLOR, Color.RED);
    figure.drawFillColor(graphics);
    assertTrue(figure.wasMethodCalled("drawFill"));
  }

  @Test
  public void testDrawStrokeColor() {
    // Set stroke color to null and check that drawStroke is not called
    figure.resetMethodCalls();
    figure.attr().set(AttributeKeys.STROKE_COLOR, null);
    figure.drawStrokeColor(graphics);
    assertFalse(figure.wasMethodCalled("drawStroke"));

    // Set stroke color and width, and check that drawStroke is called
    figure.resetMethodCalls();
    figure.attr().set(AttributeKeys.STROKE_COLOR, Color.BLACK);
    figure.attr().set(AttributeKeys.STROKE_WIDTH, 1.0);
    figure.drawStrokeColor(graphics);
    assertTrue(figure.wasMethodCalled("drawStroke"));
  }

  @Test
  public void testDrawTextColor() {
    figure.resetMethodCalls();
    figure.attr().set(AttributeKeys.TEXT_COLOR, Color.RED);
    figure.drawTextColor(graphics);
    assertTrue(figure.wasMethodCalled("drawText"));
  }

  @Test
  public void testDrawTextWithShadow() {
    // Set text color and shadow properties
    figure.resetMethodCalls();
    figure.attr().set(AttributeKeys.TEXT_COLOR, Color.BLUE);
    figure.attr().set(AttributeKeys.TEXT_SHADOW_COLOR, Color.GRAY);
    figure.attr().set(AttributeKeys.TEXT_SHADOW_OFFSET, new Dimension2DDouble(2, 2));

    // Draw text and check that drawText is called twice (once for shadow, once for text)
    figure.drawTextColor(graphics);

    // Get all method calls and count drawText occurrences
    int drawTextCount = 0;
    for (String call : figure.getMethodCalls()) {
      if ("drawText".equals(call)) {
        drawTextCount++;
      }
    }

    assertEquals(2, drawTextCount);
  }

  @Test
  public void testClone() {
    // Set some attributes
    figure.attr().set(AttributeKeys.FILL_COLOR, Color.GREEN);
    figure.attr().set(AttributeKeys.STROKE_WIDTH, 2.0);

    // Clone the figure
    MockAttributedFigure clone = (MockAttributedFigure) figure.clone();

    // Check that attributes were copied
    assertEquals(Color.GREEN, clone.attr().get(AttributeKeys.FILL_COLOR));
    assertEquals(2.0, clone.attr().get(AttributeKeys.STROKE_WIDTH));

    // Check that changing the clone doesn't affect the original
    clone.attr().set(AttributeKeys.FILL_COLOR, Color.YELLOW);
    assertEquals(Color.GREEN, figure.attr().get(AttributeKeys.FILL_COLOR));
    assertEquals(Color.YELLOW, clone.attr().get(AttributeKeys.FILL_COLOR));
  }

  @Test
  public void testCreateHandles() {
    // Test handle creation at different detail levels
    Collection<Handle> detailNegative = figure.createHandles(-1);
    assertFalse(detailNegative.isEmpty());
    assertEquals(1, detailNegative.size()); // Should have one BoundsOutlineHandle

    Collection<Handle> detailZero = figure.createHandles(0);
    assertFalse(detailZero.isEmpty());
    assertTrue(detailZero.size() > 1); // Should have multiple resize handles
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
