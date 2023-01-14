package commands;

import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import model.ImageModel;
import model.ImageModelMapImpl;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link Mosaic}.
 */
public class MosaicCommandTest {
  private ImageModelMapImpl allModels;
  private ImageModel testModel;

  // Tests that the command constructor will throw an exception when given a null Appendable
  @Test(expected = IllegalArgumentException.class)
  public void testNullAppendable() {
    new Mosaic(null, new ImageModelMapImpl(new HashMap<>()), new Scanner(System.in));
  }

  // Tests that the command constructor will throw an exception when given a null
  // Map<String, ImageModel>
  @Test(expected = IllegalArgumentException.class)
  public void testNullMap() {
    new Mosaic(new StringBuilder(), null, new Scanner(System.in));
  }

  // Tests that the command constructor will throw an exception when given a null Scanner
  @Test(expected = IllegalArgumentException.class)
  public void testNullScanner() {
    new Mosaic(new StringBuilder(), new ImageModelMapImpl(new HashMap<>()), null);
  }

  // Load the files necessary for testing
  @Before
  public void init() {
    allModels = new ImageModelMapImpl(new HashMap<>());
    LoadImage imageLoader = new LoadImage(System.out, allModels, new Scanner(new StringReader("")));
    testModel = imageLoader.loadImage("test/image.ppm");
    Appendable output = new StringBuilder();
    allModels.add("image", testModel, output);
  }

  // Tests that an exception is thrown when a number of seeds less than 1 is entered
  @Test(expected = IllegalArgumentException.class)
  public void testNotEnoughSeeds() {
    // Runs a mosaic command with -1 seed
    Mosaic mosaicNegativeSeeds = new Mosaic(new StringBuilder(), allModels,
            new Scanner(new StringReader("-1 image image-mosaic")));
    mosaicNegativeSeeds.edit();
  }

  // Tests that an exception is thrown when the number of seeds entered is greater than
  // the number of pixels in the image
  @Test(expected = IllegalArgumentException.class)
  public void testTooManySeeds() {
    // Runs a mosaic command with 10 seeds on an image containing only 9 pixels
    Mosaic mosaicTooManySeeds = new Mosaic(new StringBuilder(), allModels,
            new Scanner(new StringReader("10 image image-mosaic")));
    mosaicTooManySeeds.edit();
  }

  // Tests that the number of colors present in a mosaicked image where all pixels are
  // different colors matches the number of seeds used
  @Test
  public void testSeedNumberMatchesColorCount() {
    // Runs a mosaic commands with 1 through 9 seeds as there are 9 pixels in the image
    for (int s = 1; s < 9; s++) {

      // Runs a mosaic command with a desired number of seeds
      Mosaic testMosaic = new Mosaic(new StringBuilder(), allModels,
              new Scanner(new StringReader(s + " image image-mosaic")));
      testMosaic.edit();
      ImageModel newImage = allModels.find("image-mosaic");

      // Adds every unique color in the mosaicked image to a list of colors
      ArrayList<Color> colorList = new ArrayList<>();
      for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
          Color testColor = new Color(newImage.getPixelAt(i, j).getRed(),
                  newImage.getPixelAt(i, j).getGreen(),
                  newImage.getPixelAt(i, j).getBlue());
          if (!colorList.contains(testColor)) {
            colorList.add(testColor);
          }
        }
      }

      // Checks that the number of colors present matches the number of seeds
      assertEquals(s, colorList.size());
      colorList.clear();
    }
  }

  // Tests that using the same number of seeds as pixels in an image will result in a
  // duplicate of the original image being created
  @Test
  public void testMaxSeedsSameImage() {
    // Runs a mosaic command with the same number of seeds as pixels in the image
    Mosaic mosaicMaxSeeds = new Mosaic(new StringBuilder(), allModels,
            new Scanner(new StringReader("9 image image-mosaic")));
    mosaicMaxSeeds.edit();
    ImageModel newImage = allModels.find("image-mosaic");

    // Checks that the pixels all stayed the same
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        ImageModel oldImage = allModels.find("image");
        assertEquals(oldImage.getPixelAt(i, j).getRed(), newImage.getPixelAt(i, j).getRed());
        assertEquals(oldImage.getPixelAt(i, j).getGreen(), newImage.getPixelAt(i, j).getGreen());
        assertEquals(oldImage.getPixelAt(i, j).getBlue(), newImage.getPixelAt(i, j).getBlue());
      }
    }
  }

  // Tests that mosaicPixel works as expected on a given image
  @Test
  public void testIndividualPixels() {
    // Checks that all the pixels in the original image have the correct color values
    assertEquals(new Color(0, 0, 0), new Color(
            testModel.getPixelAt(0, 0).getRed(),
            testModel.getPixelAt(0, 0).getGreen(),
            testModel.getPixelAt(0, 0).getBlue()));
    assertEquals(new Color(255, 255, 255), new Color(
            testModel.getPixelAt(0, 1).getRed(),
            testModel.getPixelAt(0, 1).getGreen(),
            testModel.getPixelAt(0, 1).getBlue()));
    assertEquals(new Color(122, 123, 122), new Color(
            testModel.getPixelAt(0, 2).getRed(),
            testModel.getPixelAt(0, 2).getGreen(),
            testModel.getPixelAt(0, 2).getBlue()));
    assertEquals(new Color(100, 5, 5), new Color(
            testModel.getPixelAt(1, 0).getRed(),
            testModel.getPixelAt(1, 0).getGreen(),
            testModel.getPixelAt(1, 0).getBlue()));
    assertEquals(new Color(5, 100, 5), new Color(
            testModel.getPixelAt(1, 1).getRed(),
            testModel.getPixelAt(1, 1).getGreen(),
            testModel.getPixelAt(1, 1).getBlue()));
    assertEquals(new Color(5, 5, 100), new Color(
            testModel.getPixelAt(1, 2).getRed(),
            testModel.getPixelAt(1, 2).getGreen(),
            testModel.getPixelAt(1, 2).getBlue()));
    assertEquals(new Color(255, 5, 5), new Color(
            testModel.getPixelAt(2, 0).getRed(),
            testModel.getPixelAt(2, 0).getGreen(),
            testModel.getPixelAt(2, 0).getBlue()));
    assertEquals(new Color(5, 255, 5), new Color(
            testModel.getPixelAt(2, 1).getRed(),
            testModel.getPixelAt(2, 1).getGreen(),
            testModel.getPixelAt(2, 1).getBlue()));
    assertEquals(new Color(5, 5, 255), new Color(
            testModel.getPixelAt(2, 2).getRed(),
            testModel.getPixelAt(2, 2).getGreen(),
            testModel.getPixelAt(2, 2).getBlue()));

    // Runs a mosaic command with 4 seeds
    // See "test/mosaic/newImage.png for reference
    Mosaic mosaicTestPixels = new Mosaic(new StringBuilder(), allModels,
            new Scanner(new StringReader("4 image image-mosaic")), 13);
    mosaicTestPixels.edit();
    ImageModel newImage = allModels.find("image-mosaic");

    // Checks that pixels directly on seeds did not change color
    assertEquals(new Color(100, 5, 5), new Color(
            newImage.getPixelAt(1, 0).getRed(),
            newImage.getPixelAt(1, 0).getGreen(),
            newImage.getPixelAt(1, 0).getBlue()));
    assertEquals(new Color(5, 100, 5), new Color(
            newImage.getPixelAt(1, 1).getRed(),
            newImage.getPixelAt(1, 1).getGreen(),
            newImage.getPixelAt(1, 1).getBlue()));
    assertEquals(new Color(5, 5, 100), new Color(
            newImage.getPixelAt(1, 2).getRed(),
            newImage.getPixelAt(1, 2).getGreen(),
            newImage.getPixelAt(1, 2).getBlue()));
    assertEquals(new Color(5, 255, 5), new Color(
            newImage.getPixelAt(2, 1).getRed(),
            newImage.getPixelAt(2, 1).getGreen(),
            newImage.getPixelAt(2, 1).getBlue()));

    // Checks that pixels with only one nearest seed behave correctly
    assertEquals(new Color(100, 5, 5), new Color(
            newImage.getPixelAt(0, 0).getRed(),
            newImage.getPixelAt(0, 0).getGreen(),
            newImage.getPixelAt(0, 0).getBlue()));
    assertEquals(new Color(5, 100, 5), new Color(
            newImage.getPixelAt(0, 1).getRed(),
            newImage.getPixelAt(0, 1).getGreen(),
            newImage.getPixelAt(0, 1).getBlue()));
    assertEquals(new Color(5, 5, 100), new Color(
            newImage.getPixelAt(0, 2).getRed(),
            newImage.getPixelAt(0, 2).getGreen(),
            newImage.getPixelAt(0, 2).getBlue()));

    // Checks that pixels equidistant to more than one seed behave correctly
    assertEquals(new Color(5, 255, 5), new Color(
            newImage.getPixelAt(2, 0).getRed(),
            newImage.getPixelAt(2, 0).getGreen(),
            newImage.getPixelAt(2, 0).getBlue()));
    assertEquals(new Color(5, 255, 5), new Color(
            newImage.getPixelAt(2, 2).getRed(),
            newImage.getPixelAt(2, 2).getGreen(),
            newImage.getPixelAt(2, 2).getBlue()));
  }
}
