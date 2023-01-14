package view;

/**
 * This interface represents operations that can be performed on an ImagePanel to alter
 * its appearance.
 */
public interface ImagePanel {
  /**
   * Sets the ImageModel that the ImagePanel should display.
   *
   * @param imageToPaint the name corresponding to the relevant ImageModel
   */
  void setImageToPaint(String imageToPaint);
}
