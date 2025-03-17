package org.jhotdraw.draw.figure;

import java.awt.Cursor;
import java.awt.geom.Point2D;
import java.util.Collection;
import org.jhotdraw.draw.handle.Handle;

public interface SelectableFigure {

  /**
   * Indique si la figure peut être selectionne
   * @return true si la figure est selectionnable
   */
  boolean isSelectable();

  /**
   * Définit si la figure peut être selectionne
   * @param newValue new value
   */
  void setSelectable(boolean newValue);

  /**
   * Indique si la figure peut être déplacée
   * @return true si la figure est déplaçable
   */
  boolean isDraggable();

  /**
   * Définit si la figure peut etre deplacer
   * @param newValue new
   */
  void setDraggable();

  /**
   * Crée les poignées de manipulation pour la figure
   * @param detailLevel niveau de détail des poignées
   * @return Collection de poignées
   */
  Collection<Handle> createHandles(int detailLevel);

  /**
   * Retourne le curseur approprie pour un point donne
   * @param p Point ou se trouve le curseur
   * @param scaleDenominator facteur d echelle
   * @return Le curseur a afficher
   */
  Cursor getCursor(Point2D.Double p, double scaleDenominator);

  /**
   * Verifie si un point est contenu dans la figure
   * @param p Point a tester
   * @param scaleDenominator Facteur dechelle
   * @return true si le point est dans la figure
   */
  boolean contains(Point2D.Double p, double scaleDenominator);
}
