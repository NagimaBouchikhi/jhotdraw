package org.jhotdraw.draw.figure;

import static org.jhotdraw.draw.AttributeKeys.FILL_COLOR;
import static org.jhotdraw.draw.AttributeKeys.OPACITY;
import static org.jhotdraw.draw.AttributeKeys.STROKE_COLOR;
import static org.jhotdraw.draw.AttributeKeys.STROKE_WIDTH;
import static org.jhotdraw.draw.AttributeKeys.TEXT_COLOR;
import static org.jhotdraw.draw.AttributeKeys.TEXT_SHADOW_COLOR;
import static org.jhotdraw.draw.AttributeKeys.TEXT_SHADOW_OFFSET;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.utils.geom.Dimension2DDouble;

/**
 * Classe abstraite gérant les attributs visuels d'une figure.
 * Cette classe implémente la partie dessin de l'interface Figure.
 */
public abstract class AbstractAttributedFigure extends AbstractFigure implements DrawableFigure {

  private static final long serialVersionUID = 1L;
  private static final double HIT_GROWTH_FACTOR = 1.1;

  // Support de changement
  protected final FigureChangeSupport changeSupport;

  // Attributs visuels
  private Attributes attributes;

  public AbstractAttributedFigure() {
    super();
    this.changeSupport = new FigureChangeSupport(this);
    this.attributes = new Attributes(this::fireAttributeChanged);
  }

  @Override
  public Attributes attr() {
    return attributes;
  }

  @Override
  public void willChange() {
    changeSupport.willChange();
  }

  @Override
  public void changed() {
    changeSupport.changed();
  }

  /**
   * Méthode de feu d'événement déléguée au dispatcher
   */
  protected <T> void fireAttributeChanged(AttributeKey<T> attribute, T oldValue, T newValue) {
    eventDispatcher.fireAttributeChanged(attribute, oldValue, newValue);
  }

  /**
   * Méthode de dessin principale
   */
  @Override
  public void draw(Graphics2D g) {
    drawFillColor(g);
    drawStrokeColor(g);
    drawTextColor(g);
  }

  /**
   * Dessine la couleur de remplissage
   */
  public void drawFillColor(Graphics2D g) {
    if (attr().get(FILL_COLOR) != null) {
      var fillColor = attr().get(FILL_COLOR);
      Float opacity = attr().get(OPACITY);
      if (opacity < 1) {
        fillColor = new Color(fillColor.getRGB() & 0xffffff | ((int) (opacity * 256) << 24), true);
      }
      g.setColor(fillColor);
      drawFill(g);
    }
  }

  /**
   * Dessine la couleur de trait
   */
  public void drawStrokeColor(Graphics2D g) {
    if (attr().get(STROKE_COLOR) != null && attr().get(STROKE_WIDTH) >= 0d) {
      g.setStroke(AttributeKeys.getStroke(this, AttributeKeys.getScaleFactorFromGraphics(g)));
      g.setColor(attr().get(STROKE_COLOR));
      drawStroke(g);
    }
  }

  /**
   * Dessine la couleur de texte
   */
  public void drawTextColor(Graphics2D g) {
    if (attr().get(TEXT_COLOR) != null) {
      if (attr().get(TEXT_SHADOW_COLOR) != null && attr().get(TEXT_SHADOW_OFFSET) != null) {
        Dimension2DDouble d = attr().get(TEXT_SHADOW_OFFSET);
        g.translate(d.width, d.height);
        g.setColor(attr().get(TEXT_SHADOW_COLOR));
        drawText(g);
        g.translate(-d.width, -d.height);
      }
      g.setColor(attr().get(TEXT_COLOR));
      drawText(g);
    }
  }

  /**
   * Méthodes à implémenter par les sous-classes
   */
  public void drawFill(java.awt.Graphics2D g) {}

  public void drawStroke(java.awt.Graphics2D g) {}

  public void drawText(java.awt.Graphics2D g) {}

  @Override
  public AbstractAttributedFigure clone() {
    AbstractAttributedFigure that =
        (AbstractAttributedFigure) super.clone(); // Appel à super.clone()
    that.attributes = Attributes.from(attributes, that::fireAttributeChanged);
    that.eventDispatcher = new FigureEventDispatcher(that);
    return that;
  }

  protected FontRenderContext getFontRenderContext() {
    return new FontRenderContext(new AffineTransform(), true, true);
  }

  public double getStrokeMiterLimitFactor() {
    Number value = (Number) attr().get(AttributeKeys.STROKE_MITER_LIMIT);
    return (value != null) ? value.doubleValue() : 10f;
  }

  @Override
  public Rectangle2D.Double getDrawingArea(double scale) {
    // Implémentation par défaut basée sur les limites de la figure
    return getBounds(scale);
  }
}
