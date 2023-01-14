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
 * Tests for {@link Brighten}.
 */
public class BrightenCommandTest {
  private ImageModelMapImpl allModels;

  // Tests that the command constructor will throw an exception when given a null Appendable
  @Test(expected = IllegalArgumentException.class)
  public void testNullAppendable() {
    new Brighten(null, new ImageModelMapImpl(new HashMap<>()), new Scanner(System.in));
  }

  // Tests that the command constructor will throw an exception when given a null
  // Map<String, ImageModel>
  @Test(expected = IllegalArgumentException.class)
  public void testNullMap() {
    new Brighten(new StringBuilder(), null, new Scanner(System.in));
  }

  // Tests that the command constructor will throw an exception when given a null Scanner
  @Test(expected = IllegalArgumentException.class)
  public void testNullScanner() {
    new Brighten(new StringBuilder(), new ImageModelMapImpl(new HashMap<>()), null);
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

  // Tests that each command returns a map with the specified ImageModel that has been edited
  // in a manner matching the intended operation
  @Test
  public void testEdit() {

    // Tests the Brighten command
    ImageCommand brighten = new Brighten(new StringBuilder(), allModels,
            new Scanner(new StringReader("10 image image-brighten")));
    brighten.edit();
    // Checks that 'image' contains all the correct pixels
    assertEquals(allModels.find("image-brighten").getPixelAt(0, 0),
            new Pixel(10, 10, 10));
    assertEquals(allModels.find("image-brighten").getPixelAt(0, 1),
            new Pixel(255, 255, 255));
    assertEquals(allModels.find("image-brighten").getPixelAt(0, 2),
            new Pixel(132, 133, 132));
    assertEquals(allModels.find("image-brighten").getPixelAt(1, 0),
            new Pixel(110, 15, 15));
    assertEquals(allModels.find("image-brighten").getPixelAt(1, 1),
            new Pixel(15, 110, 15));
    assertEquals(allModels.find("image-brighten").getPixelAt(1, 2),
            new Pixel(15, 15, 110));
    assertEquals(allModels.find("image-brighten").getPixelAt(2, 0),
            new Pixel(255, 15, 15));
    assertEquals(allModels.find("image-brighten").getPixelAt(2, 1),
            new Pixel(15, 255, 15));
    assertEquals(allModels.find("image-brighten").getPixelAt(2, 2),
            new Pixel(15, 15, 255));
  }
}