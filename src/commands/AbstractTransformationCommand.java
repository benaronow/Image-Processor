package commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import model.IPixel;
import model.ImageModel;
import model.ImageModelImpl;
import model.ImageModelMap;
import model.Pixel;
import model.Transformation;

/**
 * This class represents a generic transformation command that can be performed
 * on an object of an image model.
 */
public abstract class AbstractTransformationCommand extends AbstractImageCommand {

  /**
   * Constructs a generic command using a given output, map of image models, and scanner.
   *
   * @param output        the output that any prompts or error messages will be written to
   * @param imageModelMap the map from which to choose an image to run the command on
   * @param scanner       takes input from the user to perform the command as intended
   * @throws IllegalArgumentException if any given parameters are null
   */
  public AbstractTransformationCommand(Appendable output, ImageModelMap imageModelMap,
                                       Scanner scanner) throws IllegalArgumentException {
    super(output, imageModelMap, scanner);
  }

  /**
   * Creates an image model that results from transforming the red, green, and blue values
   * of each individual pixel based on the specified operation.
   *
   * @param models the first item is the image model on which the command is performed,
   *               and the second, if present, is the image model used to create the mask
   * @param type   the type of transformation being done on the image model
   * @return the resulting image model
   */
  protected ImageModel transform(ImageModel[] models, Transformation type) {
    int height = models[0].getImageHeight();
    int width = models[0].getImageWidth();

    IPixel[][] newPixels = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (models.length == 2) {
          if (fitsMask(models[1], i, j)) {
            newPixels[i][j] = transformPixel(
                    models[0].getPixelAt(i, j), transformationMatrix(type));
          } else {
            newPixels[i][j] = models[0].getPixelAt(i, j);
          }
        } else {
          newPixels[i][j] = transformPixel(
                  models[0].getPixelAt(i, j), transformationMatrix(type));
        }
      }
    }

    return new ImageModelImpl(newPixels, models[0].getImageHeight(), models[0].getImageWidth());
  }

  /**
   * Transforms a single pixel based on a transformation matrix.
   *
   * @param pixel the pixel being transformed
   * @param transformation the transformation matrix
   * @return the pixel being transformed
   */
  private IPixel transformPixel(IPixel pixel, double[][] transformation) {
    int newRed;
    int newGreen;
    int newBlue;

    if (transformation[0][0] == -1) {
      int maxValue = Math.max(pixel.getRed(), Math.max(pixel.getGreen(), pixel.getBlue()));
      return new Pixel(maxValue, maxValue, maxValue);
    } else {
      newRed = (int) ((pixel.getRed() * transformation[0][0])
              + ((pixel.getGreen() * transformation[0][1]))
              + ((pixel.getBlue() * transformation[0][2])));
      newGreen = (int) ((pixel.getRed() * transformation[1][0])
              + ((pixel.getGreen() * transformation[1][1]))
              + ((pixel.getBlue() * transformation[1][2])));
      newBlue = (int) ((pixel.getRed() * transformation[2][0])
              + ((pixel.getGreen() * transformation[2][1]))
              + ((pixel.getBlue() * transformation[2][2])));
      return new Pixel(Math.max(Math.min(255, newRed), 0),
              Math.max(Math.min(255, newGreen), 0),
              Math.max(Math.min(255, newBlue), 0));
    }
  }

  /**
   * Creates the transformation matrix based on the type of transformation taking place.
   *
   * @param type the type of transformation
   * @return the transformation matrix
   */
  private double[][] transformationMatrix(Transformation type) {
    double[][] transformation = new double[3][3];

    switch (type) {
      case RED:
        createMatrix(transformation, new ArrayList<>(
                Arrays.asList(1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0)));
        break;
      case GREEN:
        createMatrix(transformation, new ArrayList<>(
                Arrays.asList(0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0)));
        break;
      case BLUE:
        createMatrix(transformation, new ArrayList<>(
                Arrays.asList(0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0)));
        break;
      case MAXVALUE: //nonsense matrix to signify we want max value component
        createMatrix(transformation, new ArrayList<>(
                Arrays.asList(-1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)));
        break;
      case LUMA:
        createMatrix(transformation, new ArrayList<>(
                Arrays.asList(0.2126, 0.7152, 0.0722, 0.2126, 0.7152, 0.0722,
                        0.2126, 0.7152, 0.0722)));
        break;
      case INTENSITY:
        createMatrix(transformation, new ArrayList<>(
                Arrays.asList(1.0 / 3, 1.0 / 3, 1.0 / 3, 1.0 / 3, 1.0 / 3, 1.0 / 3,
                        1.0 / 3, 1.0 / 3, 1.0 / 3)));
        break;
      case SEPIA:
        createMatrix(transformation, new ArrayList<>(
                Arrays.asList(0.393, 0.769, 0.189, 0.349, 0.686, 0.168,
                        0.272, 0.534, 0.131)));
        break;
      default:
        createMatrix(transformation, new ArrayList<>(
                Arrays.asList(1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0)));
    }

    return transformation;
  }

  /**
   * Creates a 2D array of doubles based on a list of values to serve as a matrix.
   *
   * @param matrix the 2D array of doubles
   * @param values the list of values
   */
  private void createMatrix(double[][] matrix, ArrayList<Double> values) {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        matrix[i][j] = values.get(0);
        values.remove(0);
      }
    }
  }
}