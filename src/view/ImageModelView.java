package view;

import controller.ViewListener;

/**
 * This interface represents operations that can be performed on an GUI view for the image
 * manipulator.
 */
public interface ImageModelView {
  /**
   * Adds a ViewListener for the GUI view that listens for and responds to emitted action events.
   *
   * @param listener the ViewListener that listens and responds to the GUI view
   */
  void registerViewListener(ViewListener listener);
}
