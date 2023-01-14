package commands;

import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Scanner;

import model.ImageModel;
import model.ImageModelMapImpl;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link Downscale}.
 */
public class DownscaleCommandTest {
  private ImageModelMapImpl allModels;
  private ImageModel testModel;

  // Tests that the command constructor will throw an exception when given a null Appendable
  @Test(expected = IllegalArgumentException.class)
  public void testNullAppendable() {
    new Downscale(null, new ImageModelMapImpl(new HashMap<>()), new Scanner(System.in));
  }

  // Tests that the command constructor will throw an exception when given a null
  // Map<String, ImageModel>
  @Test(expected = IllegalArgumentException.class)
  public void testNullMap() {
    new Downscale(new StringBuilder(), null, new Scanner(System.in));
  }

  // Tests that the command constructor will throw an exception when given a null Scanner
  @Test(expected = IllegalArgumentException.class)
  public void testNullScanner() {
    new Downscale(new StringBuilder(), new ImageModelMapImpl(new HashMap<>()), null);
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

  // Tests that an exception is thrown when a percentage less than 1 is entered
  @Test(expected = IllegalArgumentException.class)
  public void testNegativeHeight() {
    // Runs a downscale command with the height percentage below 1
    Downscale downscaleNegativePercentage = new Downscale(new StringBuilder(), allModels,
            new Scanner(new StringReader("-1 50 image image-downscale")));
    downscaleNegativePercentage.edit();
  }

  // Tests that an exception is thrown when a percentage over 100 is entered
  @Test(expected = IllegalArgumentException.class)
  public void testWidthOver100() {
    // Runs a downscale command with the width percentage over 100
    Downscale downscalePercentageOver100 = new Downscale(new StringBuilder(), allModels,
            new Scanner(new StringReader("50 101 image image-downscale")));
    downscalePercentageOver100.edit();
  }

  // Tests that downscaling only height only changes height
  @Test
  public void downscaleHeightOnlyTest() {
    // Checks the dimensions of the image before downscaling
    assertEquals(3, testModel.getImageHeight());
    assertEquals(3, testModel.getImageWidth());

    // Downscales only the image's height
    Downscale downscaleHeightOnly = new Downscale(new StringBuilder(), allModels,
            new Scanner(new StringReader("67 100 image image-downscale")));
    downscaleHeightOnly.edit();
    ImageModel newImage = allModels.find("image-downscale");

    // Checks the dimensions of the image after downscaling
    assertEquals(2, newImage.getImageHeight());
    assertEquals(3, newImage.getImageWidth());
  }

  // Tests that downscaling only width only changes width
  @Test
  public void downscaleWidthOnlyTest() {
    // Checks the dimensions of the image before downscaling
    assertEquals(3, testModel.getImageHeight());
    assertEquals(3, testModel.getImageWidth());

    // Downscales only the image's width
    Downscale downscaleWidthOnly = new Downscale(new StringBuilder(), allModels,
            new Scanner(new StringReader("100 67 image image-downscale")));
    downscaleWidthOnly.edit();
    ImageModel newImage = allModels.find("image-downscale");

    // Checks the dimensions of the image after downscaling
    assertEquals(3, newImage.getImageHeight());
    assertEquals(2, newImage.getImageWidth());
  }

  // Tests that downscaling both dimensions changes both dimensions
  @Test
  public void downscaleBothTest() {
    // Checks the dimensions of the image before downscaling
    assertEquals(3, testModel.getImageHeight());
    assertEquals(3, testModel.getImageWidth());

    // Downscales the image's height and width
    Downscale downscaleBoth = new Downscale(new StringBuilder(), allModels,
            new Scanner(new StringReader("67 67 image image-downscale")));
    downscaleBoth.edit();
    ImageModel newImage = allModels.find("image-downscale");

    // Checks the dimensions of the image after downscaling
    assertEquals(2, newImage.getImageHeight());
    assertEquals(2, newImage.getImageWidth());
  }

  // Tests that downscaling at 100 creates the same image
  @Test
  public void downscaleSameSizeTest() {
    // Checks the dimensions of the image before downscaling
    assertEquals(3, testModel.getImageHeight());
    assertEquals(3, testModel.getImageWidth());

    // Downscales the image so that it is still the same size
    Downscale downscaleSameSize = new Downscale(new StringBuilder(), allModels,
            new Scanner(new StringReader("100 100 image image-downscale")));
    downscaleSameSize.edit();
    ImageModel newImage = allModels.find("image-downscale");

    // Checks the dimensions of the image after downscaling
    assertEquals(3, newImage.getImageHeight());
    assertEquals(3, newImage.getImageWidth());
  }
}
