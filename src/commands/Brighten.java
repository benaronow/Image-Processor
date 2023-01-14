package commands;

import java.util.Scanner;

import model.IPixel;
import model.ImageModel;
import model.ImageModelImpl;
import model.ImageModelMap;
import model.Pixel;

/**
 * This class represents a Brighten command that can be performed on an object of an image model.
 */
public class Brighten extends AbstractImageCommand {
  private final String constant;

  /**
   * Constructs a Brighten command using a given output, map of image models, and scanner.
   *
   * @param output        the output that any prompts or error messages will be written to
   * @param imageModelMap the map from which to choose an image to run the command on
   * @param scanner       takes input from the user to perform the command as intended
   * @throws IllegalArgumentException if any given parameters are null or if the constant
   *                                  value is not an integer
   */
  public Brighten(Appendable output, ImageModelMap imageModelMap,
                  Scanner scanner) throws IllegalArgumentException {
    super(output, imageModelMap, scanner);
    this.constant = scanner.next();
  }

  /**
   * Creates a new image model by performing a Brighten command on a given image model.
   *
   * @param models the first item is the image model on which the command is performed,
   *               and the second, if present, is the image model used to create the mask
   * @throws IllegalArgumentException if the constant value is not an integer
   * @return a new image model based on the method performed
   */
  @Override
  protected ImageModel newModel(ImageModel[] models) throws IllegalArgumentException {
    try {
      return brighten(models, Integer.parseInt(this.constant));
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("<html>First parameter must be<br/>" +
              "an integer.</html>");
    }
  }

  /**
   * Creates an image model that results from brightening the image model that the method
   * is being performed on by a given constant.
   *
   * @param models   the first item is the image model on which the command is performed,
   *                 and the second, if present, is the image model used to create the mask
   * @param constant the constant by which to brighten the image model
   * @return the resulting image model
   */
  private ImageModel brighten(ImageModel[] models, int constant) {
    int height = models[0].getImageHeight();
    int width = models[0].getImageWidth();

    IPixel[][] newPixels = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (models.length == 2) {
          if (fitsMask(models[1], i, j)) {
            newPixels[i][j] = brightenPixel(models[0], i, j, constant);
          } else {
            newPixels[i][j] = models[0].getPixelAt(i, j);
          }
        } else {
          newPixels[i][j] = brightenPixel(models[0], i, j, constant);
        }
      }
    }

    return new ImageModelImpl(newPixels, height, width);
  }

  /**
   * Brightens a pixel based on a constant by which to shift its color values.
   *
   * @param model the model from which the pixel is being brightened
   * @param row the row of the pixel being brightened
   * @param col the column of the pixel being brightened
   * @param constant the constant to shift the color values by
   * @return the brightened pixel
   */
  private IPixel brightenPixel(ImageModel model, int row, int col, int constant) {
    int red;
    int green;
    int blue;
    if (constant >= 0) {
      red = Math.min(model.getPixelAt(row, col).getRed() + constant, 255);
      green = Math.min(model.getPixelAt(row, col).getGreen() + constant, 255);
      blue = Math.min(model.getPixelAt(row, col).getBlue() + constant, 255);
    }
    else {
      red = Math.max(model.getPixelAt(row, col).getRed() + constant, 0);
      green = Math.max(model.getPixelAt(row, col).getGreen() + constant, 0);
      blue = Math.max(model.getPixelAt(row, col).getBlue() + constant, 0);
    }
    return new Pixel(red, green, blue);
  }
}