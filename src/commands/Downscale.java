package commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import model.IPixel;
import model.ImageModel;
import model.ImageModelImpl;
import model.ImageModelMap;
import model.Pixel;

/**
 * This class represents a Downscale command that can be performed on an object of an image
 * model.
 */
public class Downscale extends AbstractImageCommand {
  private final String heightPercentage;
  private final String widthPercentage;

  /**
   * Constructs a Downscale command using a given output, map of image models, and scanner.
   *
   * @param output        the output that any prompts or error messages will be written to
   * @param imageModelMap the map from which to choose an image to run the command on
   * @param scanner       takes input from the user to perform the command as intended
   * @throws IllegalArgumentException if any given parameters are null
   */
  public Downscale(Appendable output, ImageModelMap imageModelMap, Scanner scanner)
          throws IllegalArgumentException {
    super(output, imageModelMap, scanner);
    this.heightPercentage = scanner.next();
    this.widthPercentage = scanner.next();
  }

  /**
   * Creates a new image model by performing a Downscale command on a given image model.
   *
   * @param models the first item is the image model on which the command is performed,
   *               and the second, if present, is the image model used to create the mask
   * @throws IllegalArgumentException if the percentage is not an integer or if it is not
   *                                  between 1 and 100
   * @return a new image model based on the method performed
   */
  @Override
  protected ImageModel newModel(ImageModel[] models) {
    try {
      return downscaleImage(models[0], Integer.parseInt(this.heightPercentage),
              Integer.parseInt(this.widthPercentage));
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("<html>First parameter must be<br/>" +
              "an integer.</html>");
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  /**
   * Creates an image model that results from downscaling the image model that the method is
   * being performed on using a given number of seeds.
   *
   * @param model            the image model which is being downscaled
   * @param heightPercentage the percentage by which to downscale the height
   * @param widthPercentage  the percentage by which to downscale the width
   * @throws IllegalArgumentException if the percentage is not between 1 and 100
   * @return the resulting image model
   */
  private ImageModel downscaleImage(ImageModel model, int heightPercentage,
                                    int widthPercentage) {
    int downscaleHeight = (int) (model.getImageHeight() * ((double) heightPercentage / 100));
    int downscaleWidth = (int) (model.getImageWidth() * ((double) widthPercentage / 100));
    if (heightPercentage < 1 || heightPercentage > 100
            || widthPercentage < 1 || widthPercentage > 100) {
      throw new IllegalArgumentException("<html>Percentages must be between<br/>" +
              "1 and 100, inclusive.</html>");
    }

    IPixel[][] newPixels = new Pixel[downscaleHeight][downscaleWidth];
    for (int i = 0; i < downscaleHeight; i++) {
      for (int j = 0; j < downscaleWidth; j++) {
        double rowToMap = i / ((double) heightPercentage / 100);
        double colToMap = j / ((double) widthPercentage / 100);

        if (rowToMap == Math.floor(rowToMap) && colToMap == Math.floor(colToMap)) {
          newPixels[i][j] = model.getPixelAt((int) rowToMap, (int) colToMap);
        } else {
          ArrayList<Integer> mappedValues = mappedValues(model, rowToMap, colToMap);
          newPixels[i][j] = new Pixel(
                  mappedValues.get(0), mappedValues.get(1), mappedValues.get(2));
        }
      }
    }

    return new ImageModelImpl(newPixels, downscaleHeight, downscaleWidth);
  }

  /**
   * Returns a list of the red, green, and blue color values resulting from the average values
   * of the four pixels surrounding a floating point pixel location.
   *
   * @param model the image model to get the pixels from
   * @param rowToMap the row of the floating point pixel location
   * @param colToMap the column of the floating point pixel location
   * @return the list of red, green, and blue color values
   */
  private ArrayList<Integer> mappedValues(ImageModel model, double rowToMap, double colToMap) {
    IPixel topLeft = model.getPixelAt((int) Math.floor(rowToMap),
            (int) Math.floor(colToMap));
    IPixel topRight = model.getPixelAt((int) Math.floor(rowToMap),
            (int) Math.ceil(colToMap));
    IPixel bottomLeft = model.getPixelAt((int) Math.ceil(rowToMap),
            (int) Math.floor(colToMap));
    IPixel bottomRight = model.getPixelAt((int) Math.ceil(rowToMap),
            (int) Math.ceil(colToMap));

    return new ArrayList<>(Arrays.asList(
            averageValue(rowToMap, colToMap, topLeft.getRed(),
                    topRight.getRed(), bottomLeft.getRed(), bottomRight.getRed()),
            averageValue(rowToMap, colToMap, topLeft.getGreen(),
                    topRight.getGreen(), bottomLeft.getGreen(), bottomRight.getGreen()),
            averageValue(rowToMap, colToMap, topLeft.getGreen(),
                    topRight.getGreen(), bottomLeft.getGreen(), bottomRight.getGreen())));
  }

  /**
   * Determines the average color value from that of four surrounding pixels.
   *
   * @param rowToMap    the row of the pixel that is being mapped to a downscaled image
   * @param colToMap    the column of the pixel that is being mapped to a downscaled image
   * @param topLeft     the pixel to the top left of the one being mapped
   * @param topRight    the pixel to the top right of the one being mapped
   * @param bottomLeft  the pixel to the bottom left of the one being mapped
   * @param bottomRight the pixel to the bottom right of the one being mapped
   * @return the average color value
   */
  private int averageValue(double rowToMap, double colToMap, int topLeft,
                           int topRight, int bottomLeft, int bottomRight) {
    double m = topRight * (rowToMap - Math.floor(rowToMap))
            + topLeft * (Math.ceil(rowToMap) - rowToMap);
    double n = bottomRight * (rowToMap - Math.floor(rowToMap))
            + bottomLeft * (Math.ceil(rowToMap) - rowToMap);

    return (int) (n * (colToMap - Math.floor(colToMap)))
            + (int) (m * (Math.ceil(colToMap) - colToMap));
  }
}