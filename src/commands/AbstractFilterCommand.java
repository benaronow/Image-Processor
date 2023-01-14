package commands;

import java.util.Scanner;

import model.Filter;
import model.IPixel;
import model.ImageModel;
import model.ImageModelImpl;
import model.ImageModelMap;
import model.Pixel;

/**
 * This class represents a generic filter command that can be performed
 * on an object of an image model.
 */
public abstract class AbstractFilterCommand extends AbstractImageCommand {

  /**
   * Constructs a generic command using a given output, map of image models, and scanner.
   *
   * @param output        the output that any prompts or error messages will be written to
   * @param imageModelMap the map from which to choose an image to run the command on
   * @param scanner       takes input from the user to perform the command as intended
   * @throws IllegalArgumentException if any given parameters are null
   */
  public AbstractFilterCommand(Appendable output, ImageModelMap imageModelMap,
                               Scanner scanner) throws IllegalArgumentException {
    super(output, imageModelMap, scanner);
  }

  /**
   * Creates an image model that results from filtering the image model that the method is
   * being performed on.
   *
   * @param models the first item is the image model on which the command is performed,
   *               and the second, if present, is the image model used to create the mask
   * @param type   the type of filtering being done on the image model
   * @return the resulting image model
   */
  protected ImageModel filter(ImageModel[] models, Filter type) {
    int height = models[0].getImageHeight();
    int width = models[0].getImageWidth();

    IPixel[][] newPixels = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (models.length == 2) {
          if (fitsMask(models[1], i, j)) {
            newPixels[i][j] = filterByType(models[0], i, j, type);
          } else {
            newPixels[i][j] = models[0].getPixelAt(i, j);
          }
        } else {
          newPixels[i][j] = filterByType(models[0], i, j, type);
        }
      }
    }

    return new ImageModelImpl(newPixels, height, width);
  }

  /**
   * Filters a pixel based on the type of filtering that is taking place.
   *
   * @param model the model from which the pixel is being filtered
   * @param row   the row of the pixel being filtered
   * @param col   the column of the pixel being filtered
   * @param type  the type of filtering taking place
   * @return the filtered pixel
   */
  private IPixel filterByType(ImageModel model, int row, int col, Filter type) {
    if (type == Filter.BLUR) {
      return filterPixel(model, row, col, 1, type,
              4, 8, 16);
    } else if (type == Filter.SHARPEN) {
      return filterPixel(model, row, col, 2, type,
              1, 4, -8);
    } else {
      return model.getPixelAt(row, col);
    }
  }

  /**
   * Creates a pixel that results from filtering the image model that the method is being
   * performed on.
   *
   * @param model      the image model that is being filtered
   * @param row        the row of the pixel that is being filtered
   * @param col        the column of the pixel that is being filtered
   * @param kernelSize the size of the kernel used to perform the filter
   * @param type       the type of filtering being performed
   * @param firstDiv   the number to divide the first set of kernel pixels by
   * @param secondDiv  the number to divide the second set of kernel pixels by
   * @param thirdDiv   the number to divide the third set of kernel pixels by
   * @return the filtered pixel
   */
  private IPixel filterPixel(ImageModel model, int row, int col, int kernelSize, Filter type,
                             int firstDiv, int secondDiv, int thirdDiv) {
    int height = model.getImageHeight();
    int width = model.getImageWidth();
    int kernelRed = 0;
    int kernelGreen = 0;
    int kernelBlue = 0;
    for (int a = row - kernelSize; a <= row + kernelSize; a++) {
      for (int b = col - kernelSize; b <= col + kernelSize; b++) {
        if (a >= 0 && a < height && b >= 0 && b < width) {
          if (a == row && b == col) {
            kernelRed += model.getPixelAt(a, b).getRed() / firstDiv;
            kernelGreen += model.getPixelAt(a, b).getGreen() / firstDiv;
            kernelBlue += model.getPixelAt(a, b).getBlue() / firstDiv;
          } else if (secondSetCond(a, row, b, col, type)) {
            kernelRed += model.getPixelAt(a, b).getRed() / secondDiv;
            kernelGreen += model.getPixelAt(a, b).getGreen() / secondDiv;
            kernelBlue += model.getPixelAt(a, b).getBlue() / secondDiv;
          } else {
            kernelRed += model.getPixelAt(a, b).getRed() / thirdDiv;
            kernelGreen += model.getPixelAt(a, b).getGreen() / thirdDiv;
            kernelBlue += model.getPixelAt(a, b).getBlue() / thirdDiv;
          }
        }
      }
    }
    return new Pixel(Math.max(Math.min(kernelRed, 255), 0),
            Math.max(Math.min(kernelGreen, 255), 0),
            Math.max(Math.min(kernelBlue, 255), 0));
  }

  /**
   * Determines whether a pixel being filtered is in the second set of a kernel.
   *
   * @param rowTest the row of the pixel that is being checked
   * @param row the row that the kernel is centered on
   * @param colTest the column of the pixel that is being checked
   * @param col the column that the kernel is centered on
   * @param type the type of filtering being performed
   * @return true if the pixel is in the second set, or false otherwise
   */
  private boolean secondSetCond(int rowTest, int row, int colTest, int col, Filter type) {
    if (type == Filter.BLUR) {
      return rowTest == row || colTest == col;
    } else if (type == Filter.SHARPEN) {
      return rowTest >= row - 1 && rowTest <= row + 1 && colTest >= col - 1 && colTest <= col + 1;
    } else {
      return false;
    }
  }
}
