package org.jhotdraw.draw.figure;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public interface TransformableFigure {

  /**
   * Indique si la figure pe transfo
   * @return true si la figure est transformable
   */
  boolean isTransformable();

  /**
   * definit si la figure pe transfo
   * @param newValue nvx valeur
   */
  void setTransformable(boolean newValue);

  /**
   * Définit les limit de la figure a partir de deux point
   * @param anchor Point d'ancrage
   * @param lead Point de destination
   */
  void setBounds(Point2D.Double anchor, Point2D.Double lead);

  /**
   * Applique une transformation affine à la figure
   * @param tx Transformation a appliquer
   */
  void transform(AffineTransform tx);

  /**
   * Retourne les limit de la figure selon lechelle
   * @param scale Facteur d echelle
   * @return Rectangle representant les limits
   */
  Rectangle2D.Double getBounds(double scale);

  /**
   * Retourne le point de depart de la figure
   * @return Point de depart
   */
  Point2D.Double getStartPoint();

  /**
   * Retourne le point final de la figure
   * @return Point final
   */
  Point2D.Double getEndPoint();
}
