package model;

import java.util.Objects;

/**
 * This class represents an object of a pixel.
 */
public class Pixel implements IPixel {
  private final int red;
  private final int green;
  private final int blue;

  /**
   * Constructs a pixel with given pixel color values.
   *
   * @param red   the red component value of the pixel
   * @param green the green component value of the pixel
   * @param blue  the blue component value of the pixel
   * @throws IllegalArgumentException if any of the given pixel color values are invalid
   */
  public Pixel(int red, int green, int blue) throws IllegalArgumentException {
    this(red, green, blue, 255);
  }

  /**
   * Constructs a pixel with given pixel color values and a given maximum color value.
   *
   * @param red   the red component value of the pixel
   * @param green the green component value of the pixel
   * @param blue  the blue component value of the pixel
   * @param maxValue the maximum color value that may be assigned to a pixel
   * @throws IllegalArgumentException if any of the given pixel color values are invalid
   */
  public Pixel(int red, int green, int blue, int maxValue) throws IllegalArgumentException {
    if (red < 0 || red > maxValue || green < 0 || green > maxValue
            || blue < 0 || blue > maxValue) {
      throw new IllegalArgumentException("Must be valid pixel color measurements.");
    }
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  /**
   * Determines and returns whether one pixel is equal to another pixel.
   *
   * @param o the object to which the pixel is being compared to for equality
   * @return true if the pixels are equal to each other, and false otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    } else if (!(o instanceof Pixel)) {
      return false;
    }
    Pixel that = (Pixel) o;
    return this.red == that.getRed()
            && this.green == that.getGreen()
            && this.blue == that.getBlue();
  }

  /**
   * Assigns a hashcode to the pixel that the method is being performed on.
   *
   * @return the hashcode assigned to the pixel
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.red, this.green, this.blue);
  }

  /**
   * Gets the red component value of the pixel that the method is being performed on.
   *
   * @return the red component value of the pixel
   */
  public int getRed() {
    return this.red;
  }

  /**
   * Gets the green component value of the pixel that the method is being performed on.
   *
   * @return the green component value of the pixel
   */
  public int getGreen() {
    return this.green;
  }

  /**
   * Gets the blue component value of the pixel that the method is being performed on.
   *
   * @return the blue component value of the pixel
   */
  public int getBlue() {
    return this.blue;
  }
}