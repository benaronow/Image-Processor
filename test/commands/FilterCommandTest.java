package commands;

import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Scanner;

import model.ImageModel;
import model.ImageModelMapImpl;
import model.Pixel;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link AbstractFilterCommand}.
 */
public class FilterCommandTest {
  private ImageModelMapImpl allModels;
  private ImageModel testModel;

  // Tests that the command constructor will throw an exception when given a null Appendable
  @Test(expected = IllegalArgumentException.class)
  public void testNullAppendable() {
    new BlurFilter(null, new ImageModelMapImpl(new HashMap<>()), new Scanner(System.in));
  }

  // Tests that the command constructor will throw an exception when given a null
  // Map<String, ImageModel>
  @Test(expected = IllegalArgumentException.class)
  public void testNullMap() {
    new SharpenFilter(new StringBuilder(), null, new Scanner(System.in));
  }

  // Tests that the command constructor will throw an exception when given a null Scanner
  @Test(expected = IllegalArgumentException.class)
  public void testNullScanner() {
    new BlurFilter(new StringBuilder(), new ImageModelMapImpl(new HashMap<>()), null);
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

  // Tests that each filter command returns a map with the specified ImageModel that has been edited
  // in a manner matching the intended operation
  @Test
  public void testFilterEdit() {
    // Tests the BlurFilter command
    ImageCommand blurFilter = new BlurFilter(new StringBuilder(), allModels,
            new Scanner(new StringReader(" image image-blurFilter")));
    blurFilter.edit();
    // Checks that 'image' contains all the correct pixels
    int blurKernelRed;
    int blurKernelGreen;
    int blurKernelBlue;
    // Top left
    blurKernelRed = (testModel.getPixelAt(0, 0).getRed() / 4
            + testModel.getPixelAt(0, 1).getRed() / 8
            + testModel.getPixelAt(1, 0).getRed() / 8
            + testModel.getPixelAt(1, 1).getRed() / 16);
    blurKernelGreen = (testModel.getPixelAt(0, 0).getGreen() / 4
            + testModel.getPixelAt(0, 1).getGreen() / 8
            + testModel.getPixelAt(1, 0).getGreen() / 8
            + testModel.getPixelAt(1, 1).getGreen() / 16);
    blurKernelBlue = (testModel.getPixelAt(0, 0).getBlue() / 4
            + testModel.getPixelAt(0, 1).getBlue() / 8
            + testModel.getPixelAt(1, 0).getBlue() / 8
            + testModel.getPixelAt(1, 1).getBlue() / 16);
    assertEquals(new Pixel(Math.max(Math.min(blurKernelRed, 255), 0),
                    Math.max(Math.min(blurKernelGreen, 255), 0),
                    Math.max(Math.min(blurKernelBlue, 255), 0)),
            allModels.find("image-blurFilter").getPixelAt(0, 0));
    // Top center
    blurKernelRed = (testModel.getPixelAt(0, 1).getRed() / 4
            + testModel.getPixelAt(0, 0).getRed() / 8
            + testModel.getPixelAt(0, 2).getRed() / 8
            + testModel.getPixelAt(1, 1).getRed() / 8
            + testModel.getPixelAt(1, 0).getRed() / 16
            + testModel.getPixelAt(1, 2).getRed() / 16);
    blurKernelGreen = (testModel.getPixelAt(0, 1).getGreen() / 4
            + testModel.getPixelAt(0, 0).getGreen() / 8
            + testModel.getPixelAt(0, 2).getGreen() / 8
            + testModel.getPixelAt(1, 1).getGreen() / 8
            + testModel.getPixelAt(1, 0).getGreen() / 16
            + testModel.getPixelAt(1, 2).getGreen() / 16);
    blurKernelBlue = (testModel.getPixelAt(0, 1).getBlue() / 4
            + testModel.getPixelAt(0, 0).getBlue() / 8
            + testModel.getPixelAt(0, 2).getBlue() / 8
            + testModel.getPixelAt(1, 1).getBlue() / 8
            + testModel.getPixelAt(1, 0).getBlue() / 16
            + testModel.getPixelAt(1, 2).getBlue() / 16);
    assertEquals(new Pixel(Math.max(Math.min(blurKernelRed, 255), 0),
                    Math.max(Math.min(blurKernelGreen, 255), 0),
                    Math.max(Math.min(blurKernelBlue, 255), 0)),
            allModels.find("image-blurFilter").getPixelAt(0, 1));
    // Top right
    blurKernelRed = (testModel.getPixelAt(0, 2).getRed() / 4
            + testModel.getPixelAt(0, 1).getRed() / 8
            + testModel.getPixelAt(1, 2).getRed() / 8
            + testModel.getPixelAt(1, 1).getRed() / 16);
    blurKernelGreen = (testModel.getPixelAt(0, 2).getGreen() / 4
            + testModel.getPixelAt(0, 1).getGreen() / 8
            + testModel.getPixelAt(1, 2).getGreen() / 8
            + testModel.getPixelAt(1, 1).getGreen() / 16);
    blurKernelBlue = (testModel.getPixelAt(0, 2).getBlue() / 4
            + testModel.getPixelAt(0, 1).getBlue() / 8
            + testModel.getPixelAt(1, 2).getBlue() / 8
            + testModel.getPixelAt(1, 1).getBlue() / 16);
    assertEquals(new Pixel(Math.max(Math.min(blurKernelRed, 255), 0),
                    Math.max(Math.min(blurKernelGreen, 255), 0),
                    Math.max(Math.min(blurKernelBlue, 255), 0)),
            allModels.find("image-blurFilter").getPixelAt(0, 2));
    // Center left
    blurKernelRed = (testModel.getPixelAt(1, 0).getRed() / 4
            + testModel.getPixelAt(0, 0).getRed() / 8
            + testModel.getPixelAt(2, 0).getRed() / 8
            + testModel.getPixelAt(1, 1).getRed() / 8
            + testModel.getPixelAt(0, 1).getRed() / 16
            + testModel.getPixelAt(2, 1).getRed() / 16);
    blurKernelGreen = (testModel.getPixelAt(1, 0).getGreen() / 4
            + testModel.getPixelAt(0, 0).getGreen() / 8
            + testModel.getPixelAt(2, 0).getGreen() / 8
            + testModel.getPixelAt(1, 1).getGreen() / 8
            + testModel.getPixelAt(0, 1).getGreen() / 16
            + testModel.getPixelAt(2, 1).getGreen() / 16);
    blurKernelBlue = (testModel.getPixelAt(1, 0).getBlue() / 4
            + testModel.getPixelAt(0, 0).getBlue() / 8
            + testModel.getPixelAt(2, 0).getBlue() / 8
            + testModel.getPixelAt(1, 1).getBlue() / 8
            + testModel.getPixelAt(0, 1).getBlue() / 16
            + testModel.getPixelAt(2, 1).getBlue() / 16);
    assertEquals(new Pixel(Math.max(Math.min(blurKernelRed, 255), 0),
                    Math.max(Math.min(blurKernelGreen, 255), 0),
                    Math.max(Math.min(blurKernelBlue, 255), 0)),
            allModels.find("image-blurFilter").getPixelAt(1, 0));
    // Center
    blurKernelRed = (testModel.getPixelAt(1, 1).getRed() / 4
            + testModel.getPixelAt(0, 1).getRed() / 8
            + testModel.getPixelAt(1, 0).getRed() / 8
            + testModel.getPixelAt(1, 2).getRed() / 8
            + testModel.getPixelAt(2, 1).getRed() / 8
            + testModel.getPixelAt(0, 0).getRed() / 16
            + testModel.getPixelAt(0, 2).getRed() / 16
            + testModel.getPixelAt(2, 0).getRed() / 16
            + testModel.getPixelAt(2, 2).getRed() / 16);
    blurKernelGreen = (testModel.getPixelAt(1, 1).getGreen() / 4
            + testModel.getPixelAt(0, 1).getGreen() / 8
            + testModel.getPixelAt(1, 0).getGreen() / 8
            + testModel.getPixelAt(1, 2).getGreen() / 8
            + testModel.getPixelAt(2, 1).getGreen() / 8
            + testModel.getPixelAt(0, 0).getGreen() / 16
            + testModel.getPixelAt(0, 2).getGreen() / 16
            + testModel.getPixelAt(2, 0).getGreen() / 16
            + testModel.getPixelAt(2, 2).getGreen() / 16);
    blurKernelBlue = (testModel.getPixelAt(1, 1).getBlue() / 4
            + testModel.getPixelAt(0, 1).getBlue() / 8
            + testModel.getPixelAt(1, 0).getBlue() / 8
            + testModel.getPixelAt(1, 2).getBlue() / 8
            + testModel.getPixelAt(2, 1).getBlue() / 8
            + testModel.getPixelAt(0, 0).getBlue() / 16
            + testModel.getPixelAt(0, 2).getBlue() / 16
            + testModel.getPixelAt(2, 0).getBlue() / 16
            + testModel.getPixelAt(2, 2).getBlue() / 16);
    assertEquals(new Pixel(Math.max(Math.min(blurKernelRed, 255), 0),
                    Math.max(Math.min(blurKernelGreen, 255), 0),
                    Math.max(Math.min(blurKernelBlue, 255), 0)),
            allModels.find("image-blurFilter").getPixelAt(1, 1));
    // Center right
    blurKernelRed = (testModel.getPixelAt(1, 2).getRed() / 4
            + testModel.getPixelAt(0, 2).getRed() / 8
            + testModel.getPixelAt(2, 2).getRed() / 8
            + testModel.getPixelAt(1, 1).getRed() / 8
            + testModel.getPixelAt(0, 1).getRed() / 16
            + testModel.getPixelAt(2, 1).getRed() / 16);
    blurKernelGreen = (testModel.getPixelAt(1, 2).getGreen() / 4
            + testModel.getPixelAt(0, 2).getGreen() / 8
            + testModel.getPixelAt(2, 2).getGreen() / 8
            + testModel.getPixelAt(1, 1).getGreen() / 8
            + testModel.getPixelAt(0, 1).getGreen() / 16
            + testModel.getPixelAt(2, 1).getGreen() / 16);
    blurKernelBlue = (testModel.getPixelAt(1, 2).getBlue() / 4
            + testModel.getPixelAt(0, 2).getBlue() / 8
            + testModel.getPixelAt(2, 2).getBlue() / 8
            + testModel.getPixelAt(1, 1).getBlue() / 8
            + testModel.getPixelAt(0, 1).getBlue() / 16
            + testModel.getPixelAt(2, 1).getBlue() / 16);
    assertEquals(new Pixel(Math.max(Math.min(blurKernelRed, 255), 0),
                    Math.max(Math.min(blurKernelGreen, 255), 0),
                    Math.max(Math.min(blurKernelBlue, 255), 0)),
            allModels.find("image-blurFilter").getPixelAt(1, 2));
    // Bottom left
    blurKernelRed = (testModel.getPixelAt(2, 0).getRed() / 4
            + testModel.getPixelAt(2, 1).getRed() / 8
            + testModel.getPixelAt(1, 0).getRed() / 8
            + testModel.getPixelAt(1, 1).getRed() / 16);
    blurKernelGreen = (testModel.getPixelAt(2, 0).getGreen() / 4
            + testModel.getPixelAt(2, 1).getGreen() / 8
            + testModel.getPixelAt(1, 0).getGreen() / 8
            + testModel.getPixelAt(1, 1).getGreen() / 16);
    blurKernelBlue = (testModel.getPixelAt(2, 0).getBlue() / 4
            + testModel.getPixelAt(2, 1).getBlue() / 8
            + testModel.getPixelAt(1, 0).getBlue() / 8
            + testModel.getPixelAt(1, 1).getBlue() / 16);
    assertEquals(new Pixel(Math.max(Math.min(blurKernelRed, 255), 0),
                    Math.max(Math.min(blurKernelGreen, 255), 0),
                    Math.max(Math.min(blurKernelBlue, 255), 0)),
            allModels.find("image-blurFilter").getPixelAt(2, 0));
    // Bottom center
    blurKernelRed = (testModel.getPixelAt(2, 1).getRed() / 4
            + testModel.getPixelAt(2, 0).getRed() / 8
            + testModel.getPixelAt(2, 2).getRed() / 8
            + testModel.getPixelAt(1, 1).getRed() / 8
            + testModel.getPixelAt(1, 0).getRed() / 16
            + testModel.getPixelAt(1, 2).getRed() / 16);
    blurKernelGreen = (testModel.getPixelAt(2, 1).getGreen() / 4
            + testModel.getPixelAt(2, 0).getGreen() / 8
            + testModel.getPixelAt(2, 2).getGreen() / 8
            + testModel.getPixelAt(1, 1).getGreen() / 8
            + testModel.getPixelAt(1, 0).getGreen() / 16
            + testModel.getPixelAt(1, 2).getGreen() / 16);
    blurKernelBlue = (testModel.getPixelAt(2, 1).getBlue() / 4
            + testModel.getPixelAt(2, 0).getBlue() / 8
            + testModel.getPixelAt(2, 2).getBlue() / 8
            + testModel.getPixelAt(1, 1).getBlue() / 8
            + testModel.getPixelAt(1, 0).getBlue() / 16
            + testModel.getPixelAt(1, 2).getBlue() / 16);
    assertEquals(new Pixel(Math.max(Math.min(blurKernelRed, 255), 0),
                    Math.max(Math.min(blurKernelGreen, 255), 0),
                    Math.max(Math.min(blurKernelBlue, 255), 0)),
            allModels.find("image-blurFilter").getPixelAt(2, 1));
    // Bottom right
    blurKernelRed = (testModel.getPixelAt(2, 2).getRed() / 4
            + testModel.getPixelAt(2, 1).getRed() / 8
            + testModel.getPixelAt(1, 2).getRed() / 8
            + testModel.getPixelAt(1, 1).getRed() / 16);
    blurKernelGreen = (testModel.getPixelAt(2, 2).getGreen() / 4
            + testModel.getPixelAt(2, 1).getGreen() / 8
            + testModel.getPixelAt(1, 2).getGreen() / 8
            + testModel.getPixelAt(1, 1).getGreen() / 16);
    blurKernelBlue = (testModel.getPixelAt(2, 2).getBlue() / 4
            + testModel.getPixelAt(2, 1).getBlue() / 8
            + testModel.getPixelAt(1, 2).getBlue() / 8
            + testModel.getPixelAt(1, 1).getBlue() / 16);
    assertEquals(new Pixel(Math.max(Math.min(blurKernelRed, 255), 0),
                    Math.max(Math.min(blurKernelGreen, 255), 0),
                    Math.max(Math.min(blurKernelBlue, 255), 0)),
            allModels.find("image-blurFilter").getPixelAt(2, 2));

    // Tests the SharpenFilter command
    ImageCommand sharpenFilter = new SharpenFilter(new StringBuilder(), allModels,
            new Scanner(new StringReader(" image image-sharpenFilter")));
    sharpenFilter.edit();
    // Checks that 'image' contains all the correct pixels
    int sharpenKernelRed;
    int sharpenKernelGreen;
    int sharpenKernelBlue;
    // Top left
    sharpenKernelRed = (testModel.getPixelAt(0, 0).getRed()
            + testModel.getPixelAt(0, 1).getRed() / 4
            + testModel.getPixelAt(1, 0).getRed() / 4
            + testModel.getPixelAt(1, 1).getRed() / 4
            - testModel.getPixelAt(0, 2).getRed() / 8
            - testModel.getPixelAt(1, 2).getRed() / 8
            - testModel.getPixelAt(2, 0).getRed() / 8
            - testModel.getPixelAt(2, 1).getRed() / 8
            - testModel.getPixelAt(2, 2).getRed() / 8);
    sharpenKernelGreen = (testModel.getPixelAt(0, 0).getGreen()
            + testModel.getPixelAt(0, 1).getGreen() / 4
            + testModel.getPixelAt(1, 0).getGreen() / 4
            + testModel.getPixelAt(1, 1).getGreen() / 4
            - testModel.getPixelAt(0, 2).getGreen() / 8
            - testModel.getPixelAt(1, 2).getGreen() / 8
            - testModel.getPixelAt(2, 0).getGreen() / 8
            - testModel.getPixelAt(2, 1).getGreen() / 8
            - testModel.getPixelAt(2, 2).getGreen() / 8);
    sharpenKernelBlue = (testModel.getPixelAt(0, 0).getBlue()
            + testModel.getPixelAt(0, 1).getBlue() / 4
            + testModel.getPixelAt(1, 0).getBlue() / 4
            + testModel.getPixelAt(1, 1).getBlue() / 4
            - testModel.getPixelAt(0, 2).getBlue() / 8
            - testModel.getPixelAt(1, 2).getBlue() / 8
            - testModel.getPixelAt(2, 0).getBlue() / 8
            - testModel.getPixelAt(2, 1).getBlue() / 8
            - testModel.getPixelAt(2, 2).getBlue() / 8);
    assertEquals(new Pixel(Math.max(Math.min(sharpenKernelRed, 255), 0),
                    Math.max(Math.min(sharpenKernelGreen, 255), 0),
                    Math.max(Math.min(sharpenKernelBlue, 255), 0)),
            allModels.find("image-sharpenFilter").getPixelAt(0, 0));
    // Top center
    sharpenKernelRed = (testModel.getPixelAt(0, 1).getRed()
            + testModel.getPixelAt(0, 0).getRed() / 4
            + testModel.getPixelAt(0, 2).getRed() / 4
            + testModel.getPixelAt(1, 0).getRed() / 4
            + testModel.getPixelAt(1, 1).getRed() / 4
            + testModel.getPixelAt(1, 2).getRed() / 4
            - testModel.getPixelAt(2, 0).getRed() / 8
            - testModel.getPixelAt(2, 1).getRed() / 8
            - testModel.getPixelAt(2, 2).getRed() / 8);
    sharpenKernelGreen = (testModel.getPixelAt(0, 1).getGreen()
            + testModel.getPixelAt(0, 0).getGreen() / 4
            + testModel.getPixelAt(0, 2).getGreen() / 4
            + testModel.getPixelAt(1, 0).getGreen() / 4
            + testModel.getPixelAt(1, 1).getGreen() / 4
            + testModel.getPixelAt(1, 2).getGreen() / 4
            - testModel.getPixelAt(2, 0).getGreen() / 8
            - testModel.getPixelAt(2, 1).getGreen() / 8
            - testModel.getPixelAt(2, 2).getGreen() / 8);
    sharpenKernelBlue = (testModel.getPixelAt(0, 1).getBlue()
            + testModel.getPixelAt(0, 0).getBlue() / 4
            + testModel.getPixelAt(0, 2).getBlue() / 4
            + testModel.getPixelAt(1, 0).getBlue() / 4
            + testModel.getPixelAt(1, 1).getBlue() / 4
            + testModel.getPixelAt(1, 2).getBlue() / 4
            - testModel.getPixelAt(2, 0).getBlue() / 8
            - testModel.getPixelAt(2, 1).getBlue() / 8
            - testModel.getPixelAt(2, 2).getBlue() / 8);
    assertEquals(new Pixel(Math.max(Math.min(sharpenKernelRed, 255), 0),
                    Math.max(Math.min(sharpenKernelGreen, 255), 0),
                    Math.max(Math.min(sharpenKernelBlue, 255), 0)),
            allModels.find("image-sharpenFilter").getPixelAt(0, 1));
    // Top right
    sharpenKernelRed = (testModel.getPixelAt(0, 2).getRed()
            + testModel.getPixelAt(0, 1).getRed() / 4
            + testModel.getPixelAt(1, 2).getRed() / 4
            + testModel.getPixelAt(1, 1).getRed() / 4
            - testModel.getPixelAt(0, 0).getRed() / 8
            - testModel.getPixelAt(1, 0).getRed() / 8
            - testModel.getPixelAt(2, 0).getRed() / 8
            - testModel.getPixelAt(2, 1).getRed() / 8
            - testModel.getPixelAt(2, 2).getRed() / 8);
    sharpenKernelGreen = (testModel.getPixelAt(0, 2).getGreen()
            + testModel.getPixelAt(0, 1).getGreen() / 4
            + testModel.getPixelAt(1, 2).getGreen() / 4
            + testModel.getPixelAt(1, 1).getGreen() / 4
            - testModel.getPixelAt(0, 0).getGreen() / 8
            - testModel.getPixelAt(1, 0).getGreen() / 8
            - testModel.getPixelAt(2, 0).getGreen() / 8
            - testModel.getPixelAt(2, 1).getGreen() / 8
            - testModel.getPixelAt(2, 2).getGreen() / 8);
    sharpenKernelBlue = (testModel.getPixelAt(0, 2).getBlue()
            + testModel.getPixelAt(0, 1).getBlue() / 4
            + testModel.getPixelAt(1, 2).getBlue() / 4
            + testModel.getPixelAt(1, 1).getBlue() / 4
            - testModel.getPixelAt(0, 0).getBlue() / 8
            - testModel.getPixelAt(1, 0).getBlue() / 8
            - testModel.getPixelAt(2, 0).getBlue() / 8
            - testModel.getPixelAt(2, 1).getBlue() / 8
            - testModel.getPixelAt(2, 2).getBlue() / 8);
    assertEquals(new Pixel(Math.max(Math.min(sharpenKernelRed, 255), 0),
                    Math.max(Math.min(sharpenKernelGreen, 255), 0),
                    Math.max(Math.min(sharpenKernelBlue, 255), 0)),
            allModels.find("image-sharpenFilter").getPixelAt(0, 2));
    // Center left
    sharpenKernelRed = (testModel.getPixelAt(1, 0).getRed()
            + testModel.getPixelAt(0, 0).getRed() / 4
            + testModel.getPixelAt(2, 0).getRed() / 4
            + testModel.getPixelAt(0, 1).getRed() / 4
            + testModel.getPixelAt(1, 1).getRed() / 4
            + testModel.getPixelAt(2, 1).getRed() / 4
            - testModel.getPixelAt(0, 2).getRed() / 8
            - testModel.getPixelAt(1, 2).getRed() / 8
            - testModel.getPixelAt(2, 2).getRed() / 8);
    sharpenKernelGreen = (testModel.getPixelAt(1, 0).getGreen()
            + testModel.getPixelAt(0, 0).getGreen() / 4
            + testModel.getPixelAt(2, 0).getGreen() / 4
            + testModel.getPixelAt(0, 1).getGreen() / 4
            + testModel.getPixelAt(1, 1).getGreen() / 4
            + testModel.getPixelAt(2, 1).getGreen() / 4
            - testModel.getPixelAt(0, 2).getGreen() / 8
            - testModel.getPixelAt(1, 2).getGreen() / 8
            - testModel.getPixelAt(2, 2).getGreen() / 8);
    sharpenKernelBlue = (testModel.getPixelAt(1, 0).getBlue()
            + testModel.getPixelAt(0, 0).getBlue() / 4
            + testModel.getPixelAt(2, 0).getBlue() / 4
            + testModel.getPixelAt(0, 1).getBlue() / 4
            + testModel.getPixelAt(1, 1).getBlue() / 4
            + testModel.getPixelAt(2, 1).getBlue() / 4
            - testModel.getPixelAt(0, 2).getBlue() / 8
            - testModel.getPixelAt(1, 2).getBlue() / 8
            - testModel.getPixelAt(2, 2).getBlue() / 8);
    assertEquals(new Pixel(Math.max(Math.min(sharpenKernelRed, 255), 0),
                    Math.max(Math.min(sharpenKernelGreen, 255), 0),
                    Math.max(Math.min(sharpenKernelBlue, 255), 0)),
            allModels.find("image-sharpenFilter").getPixelAt(1, 0));
    // Center
    sharpenKernelRed = (testModel.getPixelAt(1, 1).getRed()
            + testModel.getPixelAt(0, 1).getRed() / 4
            + testModel.getPixelAt(1, 0).getRed() / 4
            + testModel.getPixelAt(1, 2).getRed() / 4
            + testModel.getPixelAt(2, 1).getRed() / 4
            + testModel.getPixelAt(0, 0).getRed() / 4
            + testModel.getPixelAt(0, 2).getRed() / 4
            + testModel.getPixelAt(2, 0).getRed() / 4
            + testModel.getPixelAt(2, 2).getRed() / 4);
    sharpenKernelGreen = (testModel.getPixelAt(1, 1).getGreen()
            + testModel.getPixelAt(0, 1).getGreen() / 4
            + testModel.getPixelAt(1, 0).getGreen() / 4
            + testModel.getPixelAt(1, 2).getGreen() / 4
            + testModel.getPixelAt(2, 1).getGreen() / 4
            + testModel.getPixelAt(0, 0).getGreen() / 4
            + testModel.getPixelAt(0, 2).getGreen() / 4
            + testModel.getPixelAt(2, 0).getGreen() / 4
            + testModel.getPixelAt(2, 2).getGreen() / 4);
    sharpenKernelBlue = (testModel.getPixelAt(1, 1).getBlue()
            + testModel.getPixelAt(0, 1).getBlue() / 4
            + testModel.getPixelAt(1, 0).getBlue() / 4
            + testModel.getPixelAt(1, 2).getBlue() / 4
            + testModel.getPixelAt(2, 1).getBlue() / 4
            + testModel.getPixelAt(0, 0).getBlue() / 4
            + testModel.getPixelAt(0, 2).getBlue() / 4
            + testModel.getPixelAt(2, 0).getBlue() / 4
            + testModel.getPixelAt(2, 2).getBlue() / 4);
    assertEquals(new Pixel(Math.max(Math.min(sharpenKernelRed, 255), 0),
                    Math.max(Math.min(sharpenKernelGreen, 255), 0),
                    Math.max(Math.min(sharpenKernelBlue, 255), 0)),
            allModels.find("image-sharpenFilter").getPixelAt(1, 1));
    // Center right
    sharpenKernelRed = (testModel.getPixelAt(1, 2).getRed()
            + testModel.getPixelAt(0, 2).getRed() / 4
            + testModel.getPixelAt(2, 2).getRed() / 4
            + testModel.getPixelAt(0, 1).getRed() / 4
            + testModel.getPixelAt(1, 1).getRed() / 4
            + testModel.getPixelAt(2, 1).getRed() / 4
            - testModel.getPixelAt(0, 0).getRed() / 8
            - testModel.getPixelAt(1, 0).getRed() / 8
            - testModel.getPixelAt(2, 0).getRed() / 8);
    sharpenKernelGreen = (testModel.getPixelAt(1, 2).getGreen()
            + testModel.getPixelAt(0, 2).getGreen() / 4
            + testModel.getPixelAt(2, 2).getGreen() / 4
            + testModel.getPixelAt(0, 1).getGreen() / 4
            + testModel.getPixelAt(1, 1).getGreen() / 4
            + testModel.getPixelAt(2, 1).getGreen() / 4
            - testModel.getPixelAt(0, 0).getGreen() / 8
            - testModel.getPixelAt(1, 0).getGreen() / 8
            - testModel.getPixelAt(2, 0).getGreen() / 8);
    sharpenKernelBlue = (testModel.getPixelAt(1, 2).getBlue()
            + testModel.getPixelAt(0, 2).getBlue() / 4
            + testModel.getPixelAt(2, 2).getBlue() / 4
            + testModel.getPixelAt(0, 1).getBlue() / 4
            + testModel.getPixelAt(1, 1).getBlue() / 4
            + testModel.getPixelAt(2, 1).getBlue() / 4
            - testModel.getPixelAt(0, 0).getBlue() / 8
            - testModel.getPixelAt(1, 0).getBlue() / 8
            - testModel.getPixelAt(2, 0).getBlue() / 8);
    assertEquals(new Pixel(Math.max(Math.min(sharpenKernelRed, 255), 0),
                    Math.max(Math.min(sharpenKernelGreen, 255), 0),
                    Math.max(Math.min(sharpenKernelBlue, 255), 0)),
            allModels.find("image-sharpenFilter").getPixelAt(1, 2));
    // Bottom left
    sharpenKernelRed = (testModel.getPixelAt(2, 0).getRed()
            + testModel.getPixelAt(2, 1).getRed() / 4
            + testModel.getPixelAt(1, 0).getRed() / 4
            + testModel.getPixelAt(1, 1).getRed() / 4
            - testModel.getPixelAt(0, 2).getRed() / 8
            - testModel.getPixelAt(1, 2).getRed() / 8
            - testModel.getPixelAt(0, 0).getRed() / 8
            - testModel.getPixelAt(0, 1).getRed() / 8
            - testModel.getPixelAt(2, 2).getRed() / 8);
    sharpenKernelGreen = (testModel.getPixelAt(2, 0).getGreen()
            + testModel.getPixelAt(2, 1).getGreen() / 4
            + testModel.getPixelAt(1, 0).getGreen() / 4
            + testModel.getPixelAt(1, 1).getGreen() / 4
            - testModel.getPixelAt(0, 2).getGreen() / 8
            - testModel.getPixelAt(1, 2).getGreen() / 8
            - testModel.getPixelAt(0, 0).getGreen() / 8
            - testModel.getPixelAt(0, 1).getGreen() / 8
            - testModel.getPixelAt(2, 2).getGreen() / 8);
    sharpenKernelBlue = (testModel.getPixelAt(2, 0).getBlue()
            + testModel.getPixelAt(2, 1).getBlue() / 4
            + testModel.getPixelAt(1, 0).getBlue() / 4
            + testModel.getPixelAt(1, 1).getBlue() / 4
            - testModel.getPixelAt(0, 2).getBlue() / 8
            - testModel.getPixelAt(1, 2).getBlue() / 8
            - testModel.getPixelAt(0, 0).getBlue() / 8
            - testModel.getPixelAt(0, 1).getBlue() / 8
            - testModel.getPixelAt(2, 2).getBlue() / 8);
    assertEquals(new Pixel(Math.max(Math.min(sharpenKernelRed, 255), 0),
                    Math.max(Math.min(sharpenKernelGreen, 255), 0),
                    Math.max(Math.min(sharpenKernelBlue, 255), 0)),
            allModels.find("image-sharpenFilter").getPixelAt(2, 0));
    // Bottom center
    sharpenKernelRed = (testModel.getPixelAt(2, 1).getRed()
            + testModel.getPixelAt(2, 0).getRed() / 4
            + testModel.getPixelAt(2, 2).getRed() / 4
            + testModel.getPixelAt(1, 0).getRed() / 4
            + testModel.getPixelAt(1, 1).getRed() / 4
            + testModel.getPixelAt(1, 2).getRed() / 4
            - testModel.getPixelAt(0, 0).getRed() / 8
            - testModel.getPixelAt(0, 1).getRed() / 8
            - testModel.getPixelAt(0, 2).getRed() / 8);
    sharpenKernelGreen = (testModel.getPixelAt(2, 1).getGreen()
            + testModel.getPixelAt(2, 0).getGreen() / 4
            + testModel.getPixelAt(2, 2).getGreen() / 4
            + testModel.getPixelAt(1, 0).getGreen() / 4
            + testModel.getPixelAt(1, 1).getGreen() / 4
            + testModel.getPixelAt(1, 2).getGreen() / 4
            - testModel.getPixelAt(0, 0).getGreen() / 8
            - testModel.getPixelAt(0, 1).getGreen() / 8
            - testModel.getPixelAt(0, 2).getGreen() / 8);
    sharpenKernelBlue = (testModel.getPixelAt(2, 1).getBlue()
            + testModel.getPixelAt(2, 0).getBlue() / 4
            + testModel.getPixelAt(2, 2).getBlue() / 4
            + testModel.getPixelAt(1, 0).getBlue() / 4
            + testModel.getPixelAt(1, 1).getBlue() / 4
            + testModel.getPixelAt(1, 2).getBlue() / 4
            - testModel.getPixelAt(0, 0).getBlue() / 8
            - testModel.getPixelAt(0, 1).getBlue() / 8
            - testModel.getPixelAt(0, 2).getBlue() / 8);
    assertEquals(new Pixel(Math.max(Math.min(sharpenKernelRed, 255), 0),
                    Math.max(Math.min(sharpenKernelGreen, 255), 0),
                    Math.max(Math.min(sharpenKernelBlue, 255), 0)),
            allModels.find("image-sharpenFilter").getPixelAt(2, 1));
    // Bottom right
    sharpenKernelRed = (testModel.getPixelAt(2, 2).getRed()
            + testModel.getPixelAt(2, 1).getRed() / 4
            + testModel.getPixelAt(1, 2).getRed() / 4
            + testModel.getPixelAt(1, 1).getRed() / 4
            - testModel.getPixelAt(0, 0).getRed() / 8
            - testModel.getPixelAt(1, 0).getRed() / 8
            - testModel.getPixelAt(2, 0).getRed() / 8
            - testModel.getPixelAt(0, 1).getRed() / 8
            - testModel.getPixelAt(0, 2).getRed() / 8);
    sharpenKernelGreen = (testModel.getPixelAt(2, 2).getGreen()
            + testModel.getPixelAt(2, 1).getGreen() / 4
            + testModel.getPixelAt(1, 2).getGreen() / 4
            + testModel.getPixelAt(1, 1).getGreen() / 4
            - testModel.getPixelAt(0, 0).getGreen() / 8
            - testModel.getPixelAt(1, 0).getGreen() / 8
            - testModel.getPixelAt(2, 0).getGreen() / 8
            - testModel.getPixelAt(0, 1).getGreen() / 8
            - testModel.getPixelAt(0, 2).getGreen() / 8);
    sharpenKernelBlue = (testModel.getPixelAt(2, 2).getBlue()
            + testModel.getPixelAt(2, 1).getBlue() / 4
            + testModel.getPixelAt(1, 2).getBlue() / 4
            + testModel.getPixelAt(1, 1).getBlue() / 4
            - testModel.getPixelAt(0, 0).getBlue() / 8
            - testModel.getPixelAt(1, 0).getBlue() / 8
            - testModel.getPixelAt(2, 0).getBlue() / 8
            - testModel.getPixelAt(0, 1).getBlue() / 8
            - testModel.getPixelAt(0, 2).getBlue() / 8);
    assertEquals(new Pixel(Math.max(Math.min(sharpenKernelRed, 255), 0),
                    Math.max(Math.min(sharpenKernelGreen, 255), 0),
                    Math.max(Math.min(sharpenKernelBlue, 255), 0)),
            allModels.find("image-sharpenFilter").getPixelAt(2, 2));
  }
}
