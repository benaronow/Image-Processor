package model;

/**
 * This interface represents operations that can be performed on an object of a pixel.
 */
public interface IPixel {
  /**
   * Gets the red component value of the pixel that the method is being performed on.
   *
   * @return the red component value of the pixel
   */
  int getRed();

  /**
   * Gets the green component value of the pixel that the method is being performed on.
   *
   * @return the green component value of the pixel
   */
  int getGreen();

  /**
   * Gets the blue component value of the pixel that the method is being performed on.
   *
   * @return the blue component value of the pixel
   */
  int getBlue();
}
