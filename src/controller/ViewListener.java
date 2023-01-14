package controller;

/**
 * This interface represents operations that can be performed by a listener of the GUI view
 * for the image manipulator.
 */
public interface ViewListener {
  /**
   * Executes the command for an interaction with the GUI view that triggered an action event.
   *
   * @param parameters the parameters to be scanned by the command function object when
   *                   performing the operation on an image model.
   */
  void viewActionPerformed(String parameters);
}