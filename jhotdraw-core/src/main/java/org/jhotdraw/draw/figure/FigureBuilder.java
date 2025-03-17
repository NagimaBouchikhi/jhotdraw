package org.jhotdraw.draw.figure;

import static org.jhotdraw.draw.AttributeKeys.*;

import java.awt.Color;
import java.awt.geom.Point2D;
import org.jhotdraw.draw.AttributeKeys;

/**
 * Builder pour faciliter la création et la configuration des figures
 */
public class FigureBuilder {
  private final Figure figure;

  private FigureBuilder(Figure figure) {
    this.figure = figure;
  }

  /**
   *Create a new builder for the specified figure
   * @Param figures the figure to configure
   * @RETURN A NEW FIGUREBUILDER
   */
  public static FigureBuilder create(Figure figure) {
    return new FigureBuilder(figure);
  }

  /**Defines the filling color of the figure. */
  public FigureBuilder setFillColor(Color color) {
    figure.attr().set(AttributeKeys.FILL_COLOR, color);
    return this;
  }

  /**Defines the outline color of the figure.*/
  public FigureBuilder setStrokeColor(Color color) {
    figure.attr().set(AttributeKeys.STROKE_COLOR, color);
    return this;
  }

  /**Defines the thickness of the outline of the figure. */
  public FigureBuilder setStrokeWidth(double width) {
    figure.attr().set(AttributeKeys.STROKE_WIDTH, width);
    return this;
  }

  /**
   *Configure the style of the line
   */
  public FigureBuilder strokeType(AttributeKeys.StrokeType type) {
    figure.attr().set(AttributeKeys.STROKE_TYPE, type);
    return this;
  }

  /**
   * Defines the position and size of the figure
   * */
  public FigureBuilder setBounds(double x, double y, double width, double height) {
    figure.setBounds(new Point2D.Double(x, y), new Point2D.Double(x + width, y + height));
    return this;
  }

  /** Built and returns the configured figure.*/
  public Figure build() {
    return figure;
  }
}
