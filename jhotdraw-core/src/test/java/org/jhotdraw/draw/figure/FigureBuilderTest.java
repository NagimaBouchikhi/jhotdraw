package org.jhotdraw.draw.figure;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.AttributeKeys.StrokeType;
import org.junit.jupiter.api.*;

/**
 * Tests unitaires pour FigureBuilder
 */
public class FigureBuilderTest {

  private RectangleFigure rectangle;
  private FigureBuilder builder;

  @BeforeEach
  public void setUp() {
    rectangle = new RectangleFigure();
    builder = FigureBuilder.create(rectangle);
  }

  @Test
  public void testCreateBuilder() {
    assertNotNull("Le builder ne devrait pas être null");
  }

  @Test
  public void testSetFillColor() {
    Color fillColor = Color.RED;
    Figure figure = builder.setFillColor(fillColor).build();

    assertEquals(fillColor, figure.attr().get(AttributeKeys.FILL_COLOR));
  }

  @Test
  public void testSetStrokeColor() {
    Color strokeColor = Color.BLACK;
    Figure figure = builder.setStrokeColor(strokeColor).build();

    assertEquals(strokeColor, figure.attr().get(AttributeKeys.STROKE_COLOR));
  }

  @Test
  public void testSetStrokeWidth() {
    double width = 3.0;
    Figure figure = builder.setStrokeWidth(width).build();

    assertEquals(width, figure.attr().get(AttributeKeys.STROKE_WIDTH));
  }

  @Test
  public void testSetBounds() {
    double x = 100.0;
    double y = 100.0;
    double width = 200.0;
    double height = 150.0;

    Figure figure = builder.setBounds(x, y, width, height).build();
    Rectangle2D.Double bounds = figure.getBounds();

    assertEquals(x, bounds.x);
    assertEquals(y, bounds.y);
    assertEquals(width, bounds.width);
    assertEquals(height, bounds.height);
  }

  @Test
  public void testStrokeTypeBasic() {
    Figure figure = builder.strokeType(StrokeType.BASIC).build();
    assertEquals(StrokeType.BASIC, figure.attr().get(AttributeKeys.STROKE_TYPE));
  }
}
