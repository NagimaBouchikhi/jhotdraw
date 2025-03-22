package org.jhotdraw.draw.figure;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.event.FigureListener;
import org.jhotdraw.utils.geom.Dimension2DDouble;

/**
 * Base class for all figures in the drawing framework.
 * Contains the basic functionality shared by all figures.
 */
public abstract class AbstractFigure implements Figure {

  private static final long serialVersionUID = 1L;
  // Event
  protected FigureEventDispatcher eventDispatcher;
  // Etat basique
  private Drawing drawing;
  private boolean isSelectable = true;
  private boolean isRemovable = true;
  private boolean isVisible = true;
  private boolean isConnectable = true;
  private boolean isDraggable = true;
  private boolean isTransformable = true;
  private boolean modified = false;

  public AbstractFigure() {
    this.eventDispatcher = new FigureEventDispatcher(this);
  }

  // Methode de base pour la gestion du dessin parent
  @Override
  public void addNotify(Drawing d) {
    this.drawing = d;
    this.eventDispatcher.fireFigureAdded();
  }

  @Override
  public void removeNotify(Drawing d) {
    this.eventDispatcher.fireFigureAdded();
    this.drawing = null;
  }

  public Drawing getDrawing() {
    return drawing;
  }

  // Getteur and setteur pour les propriéte de base
  @Override
  public boolean isSelectable() {
    return isSelectable;
  }

  public void setSelectable(boolean newValue) {
    isSelectable = newValue;
  }

  @Override
  public boolean isRemovable() {
    return isRemovable;
  }

  public void setRemovable(boolean newValue) {
    isRemovable = newValue;
  }

  @Override
  public boolean isVisible() {
    return isVisible;
  }

  public void setVisible(boolean newValue) {
    if (newValue != isVisible) {
      willChange();
      isVisible = newValue;
      changed();
    }
  }

  @Override
  public boolean isConnectable() {
    return isConnectable;
  }

  public void setConnectable(boolean newValue) {
    isConnectable = newValue;
  }

  @Override
  public boolean isDraggable() {
    return isDraggable;
  }

  public void setDraggable(boolean isDraggable) {
    this.isDraggable = isDraggable;
  }

  @Override
  public boolean isTransformable() {
    return isTransformable;
  }

  public void setTransformable(boolean newValue) {
    isTransformable = newValue;
  }

  // Gestion des listeners
  @Override
  public void addFigureListener(FigureListener l) {
    this.eventDispatcher.addFigureListener(l);
  }

  @Override
  public void removeFigureListener(FigureListener l) {
    eventDispatcher.removeFigureListener(l);
  }

  // Gestion de l'état modified
  @Override
  public final boolean isModified() {
    return modified;
  }

  public void setModified() {
    modified = true;
  }

  public void resetModified() {
    modified = false;
  }

  // Gestion du changement d'état
  // Ces méthodes seront implémentées dans FigureChangeSupport
  public abstract void willChange();

  public abstract void changed();

  // Méthodes abstraites à implémenter par les sous-classes
  @Override
  public abstract Rectangle2D.Double getBounds();

  @Override
  public abstract Rectangle2D.Double getDrawingArea();

  @Override
  public abstract boolean contains(Point2D.Double p);

  @Override
  public AbstractFigure clone() {
    try {
      AbstractFigure that = (AbstractFigure) super.clone();
      // Copie des propriétés de base
      that.eventDispatcher = new FigureEventDispatcher(that);
      that.drawing = null; // Le clone ne doit pas être attaché au même dessin
      that.isSelectable = this.isSelectable;
      that.isRemovable = this.isRemovable;
      that.isVisible = this.isVisible;
      that.isConnectable = this.isConnectable;
      that.isDraggable = this.isDraggable;
      that.isTransformable = this.isTransformable;
      that.modified = this.modified;
      return that;
    } catch (CloneNotSupportedException ex) {
      throw new InternalError("clone failed", ex);
    }
  }

  // Autres méthodes communes implémentées avec des valeurs par défaut
  @Override
  public void requestRemove() {
    eventDispatcher.fireFigureRequestRemove();
  }

  @Override
  public int getLayer() {
    return 0;
  }

  protected void validate() {}

  /**
   * Invalidates cached data of the Figure. This method must execute fast, because it can be called
   * very often.
   */
  protected void invalidate() {}

  @Override
  public Dimension2DDouble getPreferredSize(double scale) {
    Rectangle2D.Double r = getBounds(scale);
    return new Dimension2DDouble(r.width, r.height);
  }

  @Override
  public String getToolTipText(Point2D.Double p) {
    return null;
  }

  /**
   * Notify all listeners that the figure's area has been invalidated.
   */
  protected void fireAreaInvalidated() {
    eventDispatcher.fireAreaInvalidated();
  }

  /**
   * Notify all listeners that the figure has changed.
   * @param changedArea The area that has changed.
   */
  protected void fireFigureChanged(Rectangle2D.Double changedArea) {
    eventDispatcher.fireFigureChanged(changedArea);
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    buf.append(getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1));
    buf.append('@');
    buf.append(hashCode());
    return buf.toString();
  }
}
