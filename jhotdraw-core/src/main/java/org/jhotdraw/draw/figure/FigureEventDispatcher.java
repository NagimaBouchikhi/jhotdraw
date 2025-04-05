package org.jhotdraw.draw.figure;

import java.awt.geom.Rectangle2D;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import javax.swing.event.EventListenerList;
import javax.swing.undo.UndoableEdit;
import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.event.FigureEvent;
import org.jhotdraw.draw.event.FigureListener;

/**
 * Cette classe gère la diffusion des événements liés aux figures.
 * Elle extrait la gestion des événements de AbstractAttributedFigure.
 */
public class FigureEventDispatcher {
  /**
   * Cette classe gère la diffusion des événements liés aux figures.
   * Elle extrait la gestion des événements de AbstractAttributedFigure.
   */
  private final Figure source;

  protected EventListenerList listenerList = new EventListenerList();

  public FigureEventDispatcher(Figure source) {
    this.source = source;
  }

  public void addFigureListener(FigureListener l) {
    boolean alreadyAdded = false;
    for (FigureListener listener : listenerList.getListeners(FigureListener.class)) {
      if (listener.equals(l)) {
        alreadyAdded = true;
        break;
      }
    }
    if (!alreadyAdded) {
      listenerList.add(FigureListener.class, l);
    }
  }

  public void removeFigureListener(FigureListener l) {
    listenerList.remove(FigureListener.class, l);
  }

  /** Tool method to process a listener and create its event object lazily.*/
  protected void fireFigureEvent(
      BiConsumer<FigureListener, FigureEvent> listenerConsumer,
      Supplier<FigureEvent> eventSupplier) {
    FigureEvent event = null;
    if (listenerList.getListenerCount() == 0) {
      return;
    }
    for (FigureListener listener : listenerList.getListeners(FigureListener.class)) {
      if (event == null) {
        event = eventSupplier.get();
      }
      listenerConsumer.accept(listener, event);
    }
  }

  public void fireFigureChanged(FigureEvent event) {
    fireFigureEvent((listener, e) -> listener.figureChanged(e), () -> event);
  }

  /** Notify all listeners that have registered interest for notification on this event type. */
  public void fireAreaInvalidated() {
    fireAreaInvalidated(source.getDrawingArea());
  }

  /** Notify all listeners that have registered interest for notification on this event type. */
  public void fireAreaInvalidated(Rectangle2D.Double invalidatedArea) {
    fireFigureEvent(
        (listener, event) -> listener.areaInvalidated(event),
        () -> new FigureEvent(source, invalidatedArea));
  }

  /** Notify all listeners that have registered interest for notification on this event type. */
  public void fireFigureRequestRemove() {
    fireFigureEvent(
        (listener, event) -> listener.figureRequestRemove(event),
        () -> new FigureEvent(source, source.getBounds(AttributeKeys.scaleFromContext(source))));
  }

  /** Notify all listeners that have registered interest for notification on this event type. */
  public void fireFigureAdded() {
    fireFigureEvent(
        (listener, event) -> listener.figureAdded(event),
        () -> new FigureEvent(source, source.getBounds(AttributeKeys.scaleFromContext(source))));
  }

  /** Notify all listeners that have registered interest for notification on this event type. */
  public void fireFigureRemoved() {
    fireFigureEvent(
        (listener, event) -> listener.figureRemoved(event),
        () -> new FigureEvent(source, source.getBounds(AttributeKeys.scaleFromContext(source))));
  }

  public void fireFigureChanged() {
    fireFigureChanged(source.getDrawingArea());
  }

  /** Notify all listeners that have registered interest for notification on this event type. */
  public void fireFigureChanged(Rectangle2D.Double changedArea) {
    fireFigureEvent(
        (listener, event) -> listener.figureChanged(event),
        () -> new FigureEvent(source, changedArea));
  }

  /** Notify all listeners that have registered interest for notification on this event type. */
  public <T> void fireAttributeChanged(AttributeKey<T> attribute, T oldValue, T newValue) {
    fireFigureEvent(
        (listener, event) -> listener.attributeChanged(event),
        () -> new FigureEvent(source, attribute, oldValue, newValue));
  }

  /** Notify all listeners that have registered interest for notification on this event type. */
  public void fireFigureHandlesChanged() {
    fireFigureEvent(
        (listener, event) -> listener.figureHandlesChanged(event),
        () -> new FigureEvent(source, source.getDrawingArea()));
  }

  /**
   * Notify all UndoableEditListener of the Drawing, to which this Figure has been added to.
   * If this Figure is not part of a Drawing, the event is lost.
   */
  public void fireUndoableEditHappened(UndoableEdit edit) {
    if (source.getDrawing() != null) {
      source.getDrawing().fireUndoableEditHappened(edit);
    }
  }
}
