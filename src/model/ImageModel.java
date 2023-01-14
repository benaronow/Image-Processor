package model;

/**
 * This interface represents operations that can be performed on an object of an image model.
 */
public interface ImageModel {
  /**
   * Gets the height of the image model that the method is being performed on.
   *
   * @return the height of the image model
   */
  int getImageHeight();

  /**
   * Gets the width of the image model that the method is being performed on.
   *
   * @return the width of the image model
   */
  int getImageWidth();

  /**
   * Gets the pixel at a given row and column of the image model that the method is being
   * performed on.
   *
   * @param row the row from which to get the pixel
   * @param col the column from which to get the pixel
   * @return the pixel at the given row and column
   * @throws IllegalArgumentException if the given position is beyond the dimensions of the
   *                                  image model
   */
  IPixel getPixelAt(int row, int col) throws IllegalArgumentException;
}