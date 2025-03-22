package org.jhotdraw.draw.figure;

/**
 * Cette classe gère le mécanisme de notification des changements d'une figure.
 * Elle extrait la logique de willChange/changed de AbstractAttributedFigure.
 */
public class FigureChangeSupport {
  private final AbstractFigure figure;

  /**
   * This variable is used to prevent endless change loops. We increase its value on each invocation
   * of willChange() and decrease it on each invocation of changed().
   */
  protected int changingDepth = 0;

  public FigureChangeSupport(AbstractFigure figure) {
    this.figure = figure;
  }

  /**
   * Informs that a figure is about to change something that affects the contents of its display
   * box.
   */
  public void willChange() {
    if (changingDepth == 0) {
      // Notifier que la figure va changer
      figure.fireAreaInvalidated();
      invalidate();
    }
    changingDepth++;
  }

  /** Informs that a figure changed the area of its display box. */
  public void changed() {
    validateChangeState();

    if (changingDepth == 1) {
      validate();
      figure.fireFigureChanged(figure.getDrawingArea());
    }
    figure.setModified();
    changingDepth--;
  }

  protected boolean isChanging() {
    return changingDepth != 0;
  }

  protected int getChangingDepth() {
    return changingDepth;
  }

  /**
   * Validates that changed() is called properly in relation to willChange().
   * @throws IllegalStateException if changingDepth is less than 1
   */
  private void validateChangeState() {
    if (changingDepth < 1) {
      throw new IllegalStateException(
          "changed was called without a prior call to willChange. " + changingDepth);
    }
  }

  /**
   * Invalidates cached data of the Figure. This method must execute fast, because it can be called
   * very often.
   */
  protected void invalidate() {
    // À surcharger par les sous-classes si nécessaire
  }

  protected void validate() {
    // À surcharger par les sous-classes si nécessaire
  }
}
