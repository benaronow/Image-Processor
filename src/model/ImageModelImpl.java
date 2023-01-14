package model;

/**
 * This class represents an object of an image model.
 */
public class ImageModelImpl implements ImageModel {
  private final IPixel[][] pixels;
  private final int height;
  private final int width;

  /**
   * Constructs an image model from a given 2D array of pixels, height, and width.
   *
   * @param pixels the 2D array of pixels from which to create the image model
   * @param height the height in pixels of the image model
   * @param width  the width in pixels of the image model
   */
  public ImageModelImpl(IPixel[][] pixels, int height, int width) {
    if (pixels == null) {
      throw new IllegalArgumentException("Pixels must not contain a null value.");
    }
    this.pixels = pixels;
    this.height = height;
    this.width = width;
  }

  /**
   * Gets the height of the PPM image model that the method is being performed on.
   *
   * @return the height of the image model
   */
  @Override
  public int getImageHeight() {
    return this.height;
  }

  /**
   * Gets the width of the PPM image model that the method is being performed on.
   *
   * @return the width of the image model
   */
  @Override
  public int getImageWidth() {
    return this.width;
  }

  /**
   * Gets the pixel at a given row and column of the PPM image model that the method is being
   * performed on.
   *
   * @param row the row from which to get the pixel
   * @param col the column from which to get the pixel
   * @return the pixel at the given row and column
   * @throws IllegalArgumentException if the given position is beyond the dimensions of the
   *                                  image model
   */
  @Override
  public IPixel getPixelAt(int row, int col) throws IllegalArgumentException {
    if (row >= this.height || row < 0 || col >= this.width || col < 0) {
      throw new IllegalArgumentException("Pixel position is beyond the dimensions of the image.");
    }
    return this.pixels[row][col];
  }
}