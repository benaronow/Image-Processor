package commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import model.IPixel;
import model.ImageModel;
import model.ImageModelImpl;
import model.ImageModelMap;
import model.Pixel;

/**
 * This class represents a Mosaic command that can be performed on an object of an image
 * model.
 */
public class Mosaic extends AbstractImageCommand {
  private final String seeds;
  private final long randomSeed;

  /**
   * Constructs a Mosaic command using a given output, map of image models, and scanner, using
   * a default Random seed to generate seed lists.
   *
   * @param output        the output that any prompts or error messages will be written to
   * @param imageModelMap the map from which to choose an image to run the command on
   * @param scanner       takes input from the user to perform the command as intended
   * @throws IllegalArgumentException if any given parameters are null or if the constant
   *                                  value is not an integer
   */
  public Mosaic(Appendable output, ImageModelMap imageModelMap,
                Scanner scanner) throws IllegalArgumentException {
    this(output, imageModelMap, scanner, System.currentTimeMillis());
  }

  /**
   * Constructs a Mosaic command using a given output, map of image models, and scanner, using
   * a chosen seed to generate seed lists.
   *
   * @param output        the output that any prompts or error messages will be written to
   * @param imageModelMap the map from which to choose an image to run the command on
   * @param scanner       takes input from the user to perform the command as intended
   * @param randomSeed    the Random seed used to generate the seed list
   * @throws IllegalArgumentException if any given parameters are null or if the constant
   *                                  value is not an integer
   */
  public Mosaic(Appendable output, ImageModelMap imageModelMap,
                Scanner scanner, long randomSeed) throws IllegalArgumentException {
    super(output, imageModelMap, scanner);
    this.seeds = scanner.next();
    this.randomSeed = randomSeed;
  }

  /**
   * Creates a new image model by performing a Mosaic command on a given image model.
   *
   * @param models the first item is the image model on which the command is performed,
   *               and the second, if present, is the image model used to create the mask
   * @throws IllegalArgumentException if the seed amount number is not an integer or if it
   *                                  is not between 1 and the pixel count of the image
   * @return a new image model based on the method performed
   */
  @Override
  protected ImageModel newModel(ImageModel[] models) {
    try {
      return mosaic(models, Integer.parseInt(this.seeds));
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("<html>First parameter must be<br/>" +
              "an integer.</html>");
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  /**
   * Creates an image model that results from a mosaic effect over the image model that the
   * method is being performed on using a given number of seeds.
   *
   * @param models the first item is the image model on which the command is performed,
   *               and the second, if present, is the image model used to create the mask
   * @param seeds the number of seeds with which to create the mosaic effect
   * @throws IllegalArgumentException if the seed count is not between one and the pixel count
   *                                  of the image
   * @return the resulting image model
   */
  private ImageModel mosaic(ImageModel[] models, int seeds) {
    int height = models[0].getImageHeight();
    int width = models[0].getImageWidth();
    if (seeds < 1 || seeds > height * width) {
      throw new IllegalArgumentException("<html>Seed count must be between<br/>" +
              "1 and the image's pixel count.</html>");
    }

    ArrayList<int[]> seedList = generateSeedList(height, width, seeds);
    IPixel[][] newPixels = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (models.length == 2) {
          if (fitsMask(models[1], i, j)) {
            newPixels[i][j] = mosaicPixel(i, j, models[0], seedList);
          } else {
            newPixels[i][j] = models[0].getPixelAt(i, j);
          }
        } else {
          newPixels[i][j] = mosaicPixel(i, j, models[0], seedList);
        }
      }
    }

    return new ImageModelImpl(newPixels, height, width);
  }

  /**
   * Changes the color of a pixel to that of the seed nearest to it when creating a mosaic image.
   *
   * @param row      the row of the pixel being transformed
   * @param col      the column of the pixel being transformed
   * @param model    the image model being used to create a mosaic image
   * @param seedList the list of seeds coordinates from which to find the nearest one
   */
  private Pixel mosaicPixel(int row, int col, ImageModel model, ArrayList<int[]> seedList) {
    IPixel closestSeed = model.getPixelAt(seedList.get(0)[0], seedList.get(0)[1]);
    double shortestDistance = Math.max(model.getImageHeight(), model.getImageWidth());

    for (int[] seed : seedList) {
      int currentSeedRow = seed[0];
      int currentSeedCol = seed[1];
      if (row == currentSeedRow && col == currentSeedCol) {
        closestSeed = model.getPixelAt(currentSeedRow, currentSeedCol);
        break;
      }
      double distance = Math.sqrt(Math.pow(Math.abs(row - currentSeedRow), 2)
              + Math.pow(Math.abs(col - currentSeedCol), 2));
      if (distance < shortestDistance) {
        shortestDistance = distance;
        closestSeed = model.getPixelAt(currentSeedRow, currentSeedCol);
      }
    }

    return new Pixel(closestSeed.getRed(), closestSeed.getGreen(), closestSeed.getBlue());
  }

  /**
   * Creates and returns a list of x or y coordinates for a specified number of seeds with which
   * to create a mosaic effect for an image.
   *
   * @param imageHeight the height that the coordinate generation is constrained to
   * @param imageWidth  the width that the coordinate generation is constrained to
   * @param seeds       the number of seeds to generate coordinates for
   * @return the list of x or y coordinates for the specified number of seeds
   */
  private ArrayList<int[]> generateSeedList(int imageHeight, int imageWidth, int seeds) {
    ArrayList<int[]> seedList = new ArrayList<>();
    Random random = new Random(this.randomSeed);

    for (int i = 0; i < seeds; i++) {
      boolean moveToNext = false;
      while (!moveToNext) {
        int seedRow = random.nextInt(imageHeight);
        int seedCol = random.nextInt(imageWidth);
        int[] seed = new int[]{seedRow, seedCol};
        if (!containsSeed(seed, seedList)) {
          seedList.add(seed);
          moveToNext = true;
        }
      }
    }

    return seedList;
  }

  /**
   * Checks to see if a list of seeds already contains a certain seed
   *
   * @param seed     the seed that is being looked for
   * @param seedList the list of seeds that is being looked in
   * @return true if the list of seeds contains the seed, false otherwise
   */
  private boolean containsSeed(int[] seed, ArrayList<int[]> seedList) {
    boolean result = false;
    for (int[] testSeed : seedList) {
      result = result || Arrays.equals(seed, testSeed);
    }
    return result;
  }
}