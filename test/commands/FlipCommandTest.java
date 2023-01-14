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
 * Tests for {@link AbstractFlipCommand}.
 */
public class FlipCommandTest {
  private ImageModelMapImpl allModels;

  // Tests that the command constructor will throw an exception when given a null Appendable
  @Test(expected = IllegalArgumentException.class)
  public void testNullAppendable() {
    new VerticalFlip(null, new ImageModelMapImpl(new HashMap<>()), new Scanner(System.in));
  }

  // Tests that the command constructor will throw an exception when given a null
  // Map<String, ImageModel>
  @Test(expected = IllegalArgumentException.class)
  public void testNullMap() {
    new HorizontalFlip(new StringBuilder(), null, new Scanner(System.in));
  }

  // Tests that the command constructor will throw an exception when given a null Scanner
  @Test(expected = IllegalArgumentException.class)
  public void testNullScanner() {
    new VerticalFlip(new StringBuilder(), new ImageModelMapImpl(new HashMap<>()), null);
  }

  // Load the files necessary for testing
  @Before
  public void init() {
    allModels = new ImageModelMapImpl(new HashMap<>());
    LoadImage imageLoader = new LoadImage(System.out, allModels, new Scanner(new StringReader("")));
    ImageModel testModel = imageLoader.loadImage("test/image.ppm");
    Appendable output = new StringBuilder();
    allModels.add("image", testModel, output);
  }

  // Tests that each flip command returns a map with the specified ImageModel that has been edited
  // in a manner matching the intended operation
  @Test
  public void testFlipEdit() {
    // Tests the HorizontalFlip command
    ImageCommand horizontalFlip = new HorizontalFlip(new StringBuilder(), allModels,
            new Scanner(new StringReader(" image image-horizontalFlip")));
    horizontalFlip.edit();
    // Checks that 'image' contains all the correct pixels
    assertEquals(allModels.find("image-horizontalFlip").getPixelAt(0, 0),
            new Pixel(122, 123, 122));
    assertEquals(allModels.find("image-horizontalFlip").getPixelAt(0, 1),
            new Pixel(255, 255, 255));
    assertEquals(allModels.find("image-horizontalFlip").getPixelAt(0, 2),
            new Pixel(0, 0, 0));
    assertEquals(allModels.find("image-horizontalFlip").getPixelAt(1, 0),
            new Pixel(5, 5, 100));
    assertEquals(allModels.find("image-horizontalFlip").getPixelAt(1, 1),
            new Pixel(5, 100, 5));
    assertEquals(allModels.find("image-horizontalFlip").getPixelAt(1, 2),
            new Pixel(100, 5, 5));
    assertEquals(allModels.find("image-horizontalFlip").getPixelAt(2, 0),
            new Pixel(5, 5, 255));
    assertEquals(allModels.find("image-horizontalFlip").getPixelAt(2, 1),
            new Pixel(5, 255, 5));
    assertEquals(allModels.find("image-horizontalFlip").getPixelAt(2, 2),
            new Pixel(255, 5, 5));

    // Tests the VerticalFlip command
    ImageCommand verticalFlip = new VerticalFlip(new StringBuilder(), allModels,
            new Scanner(new StringReader(" image image-verticalFlip")));
    verticalFlip.edit();
    // Checks that 'image' contains all the correct pixels
    assertEquals(allModels.find("image-verticalFlip").getPixelAt(0, 0),
            new Pixel(255, 5, 5));
    assertEquals(allModels.find("image-verticalFlip").getPixelAt(0, 1),
            new Pixel(5, 255, 5));
    assertEquals(allModels.find("image-verticalFlip").getPixelAt(0, 2),
            new Pixel(5, 5, 255));
    assertEquals(allModels.find("image-verticalFlip").getPixelAt(1, 0),
            new Pixel(100, 5, 5));
    assertEquals(allModels.find("image-verticalFlip").getPixelAt(1, 1),
            new Pixel(5, 100, 5));
    assertEquals(allModels.find("image-verticalFlip").getPixelAt(1, 2),
            new Pixel(5, 5, 100));
    assertEquals(allModels.find("image-verticalFlip").getPixelAt(2, 0),
            new Pixel(0, 0, 0));
    assertEquals(allModels.find("image-verticalFlip").getPixelAt(2, 1),
            new Pixel(255, 255, 255));
    assertEquals(allModels.find("image-verticalFlip").getPixelAt(2, 2),
            new Pixel(122, 123, 122));
  }
}
