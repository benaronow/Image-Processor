package commands;

import java.util.Scanner;

import model.Flip;
import model.IPixel;
import model.ImageModel;
import model.ImageModelImpl;
import model.ImageModelMap;
import model.Pixel;

/**
 * This class represents a generic flip command that can be performed
 * on an object of an image model.
 */
public abstract class AbstractFlipCommand extends AbstractImageCommand {

  /**
   * Constructs a generic command using a given output, map of image models, and scanner.
   *
   * @param output        the output that any prompts or error messages will be written to
   * @param imageModelMap the map from which to choose an image to run the command on
   * @param scanner       takes input from the user to perform the command as intended
   * @throws IllegalArgumentException if any given parameters are null
   */
  public AbstractFlipCommand(Appendable output, ImageModelMap imageModelMap,
                             Scanner scanner) throws IllegalArgumentException {
    super(output, imageModelMap, scanner);
  }

  /**
   * Creates an image model that results from flipping the image model that the method is
   * being performed on.
   *
   * @param models    the first item is the image model on which the command is performed,
   *                  and the second, if present, is the image model used to create the mask
   * @param direction the direction that the image model is being flipped in
   * @return the resulting image model
   */
  public ImageModel flip(ImageModel[] models, Flip direction) {
    int height = models[0].getImageHeight();
    int width = models[0].getImageWidth();

    IPixel[][] newPixels = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (models.length == 2) {
          if (fitsMask(models[1], i, j)) {
            flipByDirection(models[0], newPixels, i, j, direction);
          } else {
            newPixels[i][j] = models[0].getPixelAt(i, j);
          }
        } else {
          flipByDirection(models[0], newPixels, i, j, direction);
        }
      }
    }

    return new ImageModelImpl(newPixels, height, width);
  }

  /**
   * Flips a given pixel in a given direction.
   *
   * @param model     the model from which the pixel is being flipped
   * @param newPixels the list of pixels the pixel is being flipped to
   * @param row       the row of the pixel being flipped
   * @param col       the column of the pixel being flipped
   * @param direction the direction that the pixel is being flipped in
   */
  private void flipByDirection(ImageModel model, IPixel[][] newPixels,
                               int row, int col, Flip direction) {
    if (direction == Flip.HORIZONTAL) {
      newPixels[row][model.getImageWidth() - 1 - col] = model.getPixelAt(row, col);
    } else if (direction == Flip.VERTICAL) {
      newPixels[model.getImageHeight() - 1 - row][col] = model.getPixelAt(row, col);
    }
  }
}