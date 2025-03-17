package org.jhotdraw.draw.figure;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * Interface qui définit les comportements liés au dessin d'une figure.
 * Cette séparation permet d'isoler toutes les méthodes liées à l'aspect visuel.
 */
public interface DrawableFigure {

  /**
   * Dessine la figure complète avec tous ses attributs (remplissage, contour, texte)
   * @param g Le contexte graphique sur lequel dessiner
   */
  void draw(Graphics2D g);

  /**
   * Dessine le remplissage de la figure
   * @param g Le contexte graphique sur lequel dessiner
   */
  void drawFill(Graphics2D g);

  /**
   * Dessine le contour de la figure
   * @param g Le contexte graphique sur lequel dessiner
   */
  void drawStroke(Graphics2D g);

  /**
   * Dessine le texte associé à la figure
   * @param g Le contexte graphique sur lequel dessiner
   */
  void drawText(Graphics2D g);

  /**
   * Retourne la zone de dessin de la figure en tenant compte de l'échelle
   * @param scale Facteur d'échelle à appliquer
   * @return Rectangle représentant la zone de dessin
   */
  Rectangle2D.Double getDrawingArea(double scale);

  /**
   * Indique si la figure est visible
   * @return true si la figure est visible, false sinon
   */
  boolean isVisible();
}
