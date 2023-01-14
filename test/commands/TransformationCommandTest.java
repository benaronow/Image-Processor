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
 * Tests for {@link AbstractTransformationCommand}.
 */
public class TransformationCommandTest {
  private ImageModelMapImpl allModels;
  private ImageModel testModel;

  // Tests that the command constructor will throw an exception when given a null Appendable
  @Test(expected = IllegalArgumentException.class)
  public void testNullAppendable() {
    new BlueGreyscale(null, new ImageModelMapImpl(new HashMap<>()), new Scanner(System.in));
  }

  // Tests that the command constructor will throw an exception when given a null
  // Map<String, ImageModel>
  @Test(expected = IllegalArgumentException.class)
  public void testNullMap() {
    new GreenGreyscale(new StringBuilder(), null, new Scanner(System.in));
  }

  // Tests that the command constructor will throw an exception when given a null Scanner
  @Test(expected = IllegalArgumentException.class)
  public void testNullScanner() {
    new RedGreyscale(new StringBuilder(), new ImageModelMapImpl(new HashMap<>()), null);
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

  // Tests that each transformation command returns a map with the specified ImageModel
  // that has been edited in a manner matching the intended operation
  @Test
  public void testTransform() {
    // Tests the RedGreyscale command
    ImageCommand redGreyscale = new RedGreyscale(new StringBuilder(), allModels,
            new Scanner(new StringReader(" image image-redGreyscale")));
    redGreyscale.edit();
    // Checks that 'image' contains all the correct pixels
    assertEquals(allModels.find("image-redGreyscale").getPixelAt(0, 0),
            new Pixel(0, 0, 0));
    assertEquals(allModels.find("image-redGreyscale").getPixelAt(0, 1),
            new Pixel(255, 255, 255));
    assertEquals(allModels.find("image-redGreyscale").getPixelAt(0, 2),
            new Pixel(122, 122, 122));
    assertEquals(allModels.find("image-redGreyscale").getPixelAt(1, 0),
            new Pixel(100, 100, 100));
    assertEquals(allModels.find("image-redGreyscale").getPixelAt(1, 1),
            new Pixel(5, 5, 5));
    assertEquals(allModels.find("image-redGreyscale").getPixelAt(1, 2),
            new Pixel(5, 5, 5));
    assertEquals(allModels.find("image-redGreyscale").getPixelAt(2, 0),
            new Pixel(255, 255, 255));
    assertEquals(allModels.find("image-redGreyscale").getPixelAt(2, 1),
            new Pixel(5, 5, 5));
    assertEquals(allModels.find("image-redGreyscale").getPixelAt(2, 2),
            new Pixel(5, 5, 5));

    // Tests the GreenGreyscale command
    ImageCommand greenGreyscale = new GreenGreyscale(new StringBuilder(), allModels,
            new Scanner(new StringReader(" image image-greenGreyscale")));
    greenGreyscale.edit();
    // Checks that 'image' contains all the correct pixels
    assertEquals(allModels.find("image-greenGreyscale").getPixelAt(0, 0),
            new Pixel(0, 0, 0));
    assertEquals(allModels.find("image-greenGreyscale").getPixelAt(0, 1),
            new Pixel(255, 255, 255));
    assertEquals(allModels.find("image-greenGreyscale").getPixelAt(0, 2),
            new Pixel(123, 123, 123));
    assertEquals(allModels.find("image-greenGreyscale").getPixelAt(1, 0),
            new Pixel(5, 5, 5));
    assertEquals(allModels.find("image-greenGreyscale").getPixelAt(1, 1),
            new Pixel(100, 100, 100));
    assertEquals(allModels.find("image-greenGreyscale").getPixelAt(1, 2),
            new Pixel(5, 5, 5));
    assertEquals(allModels.find("image-greenGreyscale").getPixelAt(2, 0),
            new Pixel(5, 5, 5));
    assertEquals(allModels.find("image-greenGreyscale").getPixelAt(2, 1),
            new Pixel(255, 255, 255));
    assertEquals(allModels.find("image-greenGreyscale").getPixelAt(2, 2),
            new Pixel(5, 5, 5));

    // Tests the BlueGreyscale command
    ImageCommand blueGreyscale = new BlueGreyscale(new StringBuilder(), allModels,
            new Scanner(new StringReader(" image image-blueGreyscale")));
    blueGreyscale.edit();
    // Checks that 'image' contains all the correct pixels
    assertEquals(allModels.find("image-blueGreyscale").getPixelAt(0, 0),
            new Pixel(0, 0, 0));
    assertEquals(allModels.find("image-blueGreyscale").getPixelAt(0, 1),
            new Pixel(255, 255, 255));
    assertEquals(allModels.find("image-blueGreyscale").getPixelAt(0, 2),
            new Pixel(122, 122, 122));
    assertEquals(allModels.find("image-blueGreyscale").getPixelAt(1, 0),
            new Pixel(5, 5, 5));
    assertEquals(allModels.find("image-blueGreyscale").getPixelAt(1, 1),
            new Pixel(5, 5, 5));
    assertEquals(allModels.find("image-blueGreyscale").getPixelAt(1, 2),
            new Pixel(100, 100, 100));
    assertEquals(allModels.find("image-blueGreyscale").getPixelAt(2, 0),
            new Pixel(5, 5, 5));
    assertEquals(allModels.find("image-blueGreyscale").getPixelAt(2, 1),
            new Pixel(5, 5, 5));
    assertEquals(allModels.find("image-blueGreyscale").getPixelAt(2, 2),
            new Pixel(255, 255, 255));

    // Tests the MaxValueGreyscale command
    ImageCommand maxValueGreyscale = new MaxValueGreyscale(new StringBuilder(), allModels,
            new Scanner(new StringReader(" image image-maxValueGreyscale")));
    maxValueGreyscale.edit();
    // Checks that 'image' contains all the correct pixels
    assertEquals(allModels.find("image-maxValueGreyscale").getPixelAt(0, 0),
            new Pixel(0, 0, 0));
    assertEquals(allModels.find("image-maxValueGreyscale").getPixelAt(0, 1),
            new Pixel(255, 255, 255));
    assertEquals(allModels.find("image-maxValueGreyscale").getPixelAt(0, 2),
            new Pixel(123, 123, 123));
    assertEquals(allModels.find("image-maxValueGreyscale").getPixelAt(1, 0),
            new Pixel(100, 100, 100));
    assertEquals(allModels.find("image-maxValueGreyscale").getPixelAt(1, 1),
            new Pixel(100, 100, 100));
    assertEquals(allModels.find("image-maxValueGreyscale").getPixelAt(1, 2),
            new Pixel(100, 100, 100));
    assertEquals(allModels.find("image-maxValueGreyscale").getPixelAt(2, 0),
            new Pixel(255, 255, 255));
    assertEquals(allModels.find("image-maxValueGreyscale").getPixelAt(2, 1),
            new Pixel(255, 255, 255));
    assertEquals(allModels.find("image-maxValueGreyscale").getPixelAt(2, 2),
            new Pixel(255, 255, 255));

    // Tests the LumaGreyscale command
    ImageCommand lumaGreyscale = new LumaGreyscale(new StringBuilder(), allModels,
            new Scanner(new StringReader(" image image-lumaGreyscale")));
    lumaGreyscale.edit();
    // Checks that 'image' contains all the correct pixels
    int lumaValue;
    lumaValue = (int)(0.2126 * testModel.getPixelAt(0, 0).getRed()
            + 0.7152 * testModel.getPixelAt(0, 0).getGreen()
            + 0.0722 * testModel.getPixelAt(0, 0).getBlue());
    assertEquals(new Pixel(lumaValue, lumaValue, lumaValue),
            allModels.find("image-lumaGreyscale").getPixelAt(0, 0));
    lumaValue = (int)(0.2126 * testModel.getPixelAt(0, 1).getRed()
            + 0.7152 * testModel.getPixelAt(0, 1).getGreen()
            + 0.0722 * testModel.getPixelAt(0, 1).getBlue());
    assertEquals(new Pixel(lumaValue, lumaValue, lumaValue),
            allModels.find("image-lumaGreyscale").getPixelAt(0, 1));
    lumaValue = (int)(0.2126 * testModel.getPixelAt(0, 2).getRed()
            + 0.7152 * testModel.getPixelAt(0, 2).getGreen()
            + 0.0722 * testModel.getPixelAt(0, 2).getBlue());
    assertEquals(new Pixel(lumaValue, lumaValue, lumaValue),
            allModels.find("image-lumaGreyscale").getPixelAt(0, 2));
    lumaValue = (int)(0.2126 * testModel.getPixelAt(1, 0).getRed()
            + 0.7152 * testModel.getPixelAt(1, 0).getGreen()
            + 0.0722 * testModel.getPixelAt(1, 0).getBlue());
    assertEquals(new Pixel(lumaValue, lumaValue, lumaValue),
            allModels.find("image-lumaGreyscale").getPixelAt(1, 0));
    lumaValue = (int)(0.2126 * testModel.getPixelAt(1, 1).getRed()
            + 0.7152 * testModel.getPixelAt(1, 1).getGreen()
            + 0.0722 * testModel.getPixelAt(1, 1).getBlue());
    assertEquals(new Pixel(lumaValue, lumaValue, lumaValue),
            allModels.find("image-lumaGreyscale").getPixelAt(1, 1));
    lumaValue = (int)(0.2126 * testModel.getPixelAt(1, 2).getRed()
            + 0.7152 * testModel.getPixelAt(1, 2).getGreen()
            + 0.0722 * testModel.getPixelAt(1, 2).getBlue());
    assertEquals(new Pixel(lumaValue, lumaValue, lumaValue),
            allModels.find("image-lumaGreyscale").getPixelAt(1, 2));
    lumaValue = (int)(0.2126 * testModel.getPixelAt(2, 0).getRed()
            + 0.7152 * testModel.getPixelAt(2, 0).getGreen()
            + 0.0722 * testModel.getPixelAt(2, 0).getBlue());
    assertEquals(new Pixel(lumaValue, lumaValue, lumaValue),
            allModels.find("image-lumaGreyscale").getPixelAt(2, 0));
    lumaValue = (int)(0.2126 * testModel.getPixelAt(2, 1).getRed()
            + 0.7152 * testModel.getPixelAt(2, 1).getGreen()
            + 0.0722 * testModel.getPixelAt(2, 1).getBlue());
    assertEquals(new Pixel(lumaValue, lumaValue, lumaValue),
            allModels.find("image-lumaGreyscale").getPixelAt(2, 1));
    lumaValue = (int)(0.2126 * testModel.getPixelAt(2, 2).getRed()
            + 0.7152 * testModel.getPixelAt(2, 2).getGreen()
            + 0.0722 * testModel.getPixelAt(2, 2).getBlue());
    assertEquals(new Pixel(lumaValue, lumaValue, lumaValue),
            allModels.find("image-lumaGreyscale").getPixelAt(2, 2));

    // Tests the IntensityGreyscale command
    ImageCommand intensityGreyscale = new IntensityGreyscale(new StringBuilder(), allModels,
            new Scanner(new StringReader(" image image-intensityGreyscale")));
    intensityGreyscale.edit();
    // Checks that 'image' contains all the correct pixels
    int avgValue;
    avgValue = (testModel.getPixelAt(0, 0).getRed()
            + testModel.getPixelAt(0, 0).getGreen()
            + testModel.getPixelAt(0, 0).getBlue()) / 3;
    assertEquals(new Pixel(avgValue, avgValue, avgValue),
            allModels.find("image-intensityGreyscale").getPixelAt(0, 0));
    avgValue = (testModel.getPixelAt(0, 1).getRed()
            + testModel.getPixelAt(0, 1).getGreen()
            + testModel.getPixelAt(0, 1).getBlue()) / 3;
    assertEquals(new Pixel(avgValue, avgValue, avgValue),
            allModels.find("image-intensityGreyscale").getPixelAt(0, 1));
    avgValue = (testModel.getPixelAt(0, 2).getRed()
            + testModel.getPixelAt(0, 2).getGreen()
            + testModel.getPixelAt(0, 2).getBlue()) / 3;
    assertEquals(new Pixel(avgValue, avgValue, avgValue),
            allModels.find("image-intensityGreyscale").getPixelAt(0, 2));
    avgValue = (testModel.getPixelAt(1, 0).getRed()
            + testModel.getPixelAt(1, 0).getGreen()
            + testModel.getPixelAt(1, 0).getBlue()) / 3;
    assertEquals(new Pixel(avgValue, avgValue, avgValue),
            allModels.find("image-intensityGreyscale").getPixelAt(1, 0));
    avgValue = (testModel.getPixelAt(1, 1).getRed()
            + testModel.getPixelAt(1, 1).getGreen()
            + testModel.getPixelAt(1, 1).getBlue()) / 3;
    assertEquals(new Pixel(avgValue, avgValue, avgValue),
            allModels.find("image-intensityGreyscale").getPixelAt(1, 1));
    avgValue = (testModel.getPixelAt(1, 2).getRed()
            + testModel.getPixelAt(1, 2).getGreen()
            + testModel.getPixelAt(1, 2).getBlue()) / 3;
    assertEquals(new Pixel(avgValue, avgValue, avgValue),
            allModels.find("image-intensityGreyscale").getPixelAt(1, 2));
    avgValue = (testModel.getPixelAt(2, 0).getRed()
            + testModel.getPixelAt(2, 0).getGreen()
            + testModel.getPixelAt(2, 0).getBlue()) / 3;
    assertEquals(new Pixel(avgValue, avgValue, avgValue),
            allModels.find("image-intensityGreyscale").getPixelAt(2, 0));
    avgValue = (testModel.getPixelAt(2, 1).getRed()
            + testModel.getPixelAt(2, 1).getGreen()
            + testModel.getPixelAt(2, 1).getBlue()) / 3;
    assertEquals(new Pixel(avgValue, avgValue, avgValue),
            allModels.find("image-intensityGreyscale").getPixelAt(2, 1));
    avgValue = (testModel.getPixelAt(2, 2).getRed()
            + testModel.getPixelAt(2, 2).getGreen()
            + testModel.getPixelAt(2, 2).getBlue()) / 3;
    assertEquals(new Pixel(avgValue, avgValue, avgValue),
            allModels.find("image-intensityGreyscale").getPixelAt(2, 2));

    // Tests the SepiaTone command
    ImageCommand sepiaTone = new SepiaTone(new StringBuilder(), allModels,
            new Scanner(new StringReader(" image image-sepiaTone")));
    sepiaTone.edit();
    // Checks each pixel's red value after running transform
    int sepiaValueRed;
    sepiaValueRed = (int) (0.393 * testModel.getPixelAt(0, 0).getRed()
            + 0.769 * testModel.getPixelAt(0, 0).getGreen()
            + 0.189 * testModel.getPixelAt(0, 0).getBlue());
    assertEquals(sepiaValueRed,
            allModels.find("image-sepiaTone").getPixelAt(0, 0).getRed());
    sepiaValueRed = (int) (0.393 * testModel.getPixelAt(0, 1).getRed()
            + 0.769 * testModel.getPixelAt(0, 1).getGreen()
            + 0.189 * testModel.getPixelAt(0, 1).getBlue());
    assertEquals(Math.min(255, sepiaValueRed),
            allModels.find("image-sepiaTone").getPixelAt(0, 1).getRed());
    sepiaValueRed = (int) (0.393 * testModel.getPixelAt(0, 2).getRed()
            + 0.769 * testModel.getPixelAt(0, 2).getGreen()
            + 0.189 * testModel.getPixelAt(0, 2).getBlue());
    assertEquals(sepiaValueRed,
            allModels.find("image-sepiaTone").getPixelAt(0, 2).getRed());
    sepiaValueRed = (int) (0.393 * testModel.getPixelAt(1, 0).getRed()
            + 0.769 * testModel.getPixelAt(1, 0).getGreen()
            + 0.189 * testModel.getPixelAt(1, 0).getBlue());
    assertEquals(sepiaValueRed,
            allModels.find("image-sepiaTone").getPixelAt(1, 0).getRed());
    sepiaValueRed = (int) (0.393 * testModel.getPixelAt(1, 1).getRed()
            + 0.769 * testModel.getPixelAt(1, 1).getGreen()
            + 0.189 * testModel.getPixelAt(1, 1).getBlue());
    assertEquals(sepiaValueRed,
            allModels.find("image-sepiaTone").getPixelAt(1, 1).getRed());
    sepiaValueRed = (int) (0.393 * testModel.getPixelAt(1, 2).getRed()
            + 0.769 * testModel.getPixelAt(1, 2).getGreen()
            + 0.189 * testModel.getPixelAt(1, 2).getBlue());
    assertEquals(sepiaValueRed,
            allModels.find("image-sepiaTone").getPixelAt(1, 2).getRed());
    sepiaValueRed = (int) (0.393 * testModel.getPixelAt(2, 0).getRed()
            + 0.769 * testModel.getPixelAt(2, 0).getGreen()
            + 0.189 * testModel.getPixelAt(2, 0).getBlue());
    assertEquals(sepiaValueRed,
            allModels.find("image-sepiaTone").getPixelAt(2, 0).getRed());
    sepiaValueRed = (int) (0.393 * testModel.getPixelAt(2, 1).getRed()
            + 0.769 * testModel.getPixelAt(2, 1).getGreen()
            + 0.189 * testModel.getPixelAt(2, 1).getBlue());
    assertEquals(sepiaValueRed,
            allModels.find("image-sepiaTone").getPixelAt(2, 1).getRed());
    sepiaValueRed = (int) (0.393 * testModel.getPixelAt(2, 2).getRed()
            + 0.769 * testModel.getPixelAt(2, 2).getGreen()
            + 0.189 * testModel.getPixelAt(2, 2).getBlue());
    assertEquals(sepiaValueRed,
            allModels.find("image-sepiaTone").getPixelAt(2, 2).getRed());
    // Checks each pixel's green value after running transform
    int sepiaValueGreen;
    sepiaValueGreen = (int) (0.349 * testModel.getPixelAt(0, 0).getRed()
            + 0.686 * testModel.getPixelAt(0, 0).getGreen()
            + 0.168 * testModel.getPixelAt(0, 0).getBlue());
    assertEquals(sepiaValueGreen,
            allModels.find("image-sepiaTone").getPixelAt(0, 0).getGreen());
    sepiaValueGreen = (int) (0.349 * testModel.getPixelAt(0, 1).getRed()
            + 0.686 * testModel.getPixelAt(0, 1).getGreen()
            + 0.168 * testModel.getPixelAt(0, 1).getBlue());
    assertEquals(Math.min(255, sepiaValueGreen),
            allModels.find("image-sepiaTone").getPixelAt(0, 1).getGreen());
    sepiaValueGreen = (int) (0.349 * testModel.getPixelAt(0, 2).getRed()
            + 0.686 * testModel.getPixelAt(0, 2).getGreen()
            + 0.168 * testModel.getPixelAt(0, 2).getBlue());
    assertEquals(sepiaValueGreen,
            allModels.find("image-sepiaTone").getPixelAt(0, 2).getGreen());
    sepiaValueGreen = (int) (0.349 * testModel.getPixelAt(1, 0).getRed()
            + 0.686 * testModel.getPixelAt(1, 0).getGreen()
            + 0.168 * testModel.getPixelAt(1, 0).getBlue());
    assertEquals(sepiaValueGreen,
            allModels.find("image-sepiaTone").getPixelAt(1, 0).getGreen());
    sepiaValueGreen = (int) (0.349 * testModel.getPixelAt(1, 1).getRed()
            + 0.686 * testModel.getPixelAt(1, 1).getGreen()
            + 0.168 * testModel.getPixelAt(1, 1).getBlue());
    assertEquals(sepiaValueGreen,
            allModels.find("image-sepiaTone").getPixelAt(1, 1).getGreen());
    sepiaValueGreen = (int) (0.349 * testModel.getPixelAt(1, 2).getRed()
            + 0.686 * testModel.getPixelAt(1, 2).getGreen()
            + 0.168 * testModel.getPixelAt(1, 2).getBlue());
    assertEquals(sepiaValueGreen,
            allModels.find("image-sepiaTone").getPixelAt(1, 2).getGreen());
    sepiaValueGreen = (int) (0.349 * testModel.getPixelAt(2, 0).getRed()
            + 0.686 * testModel.getPixelAt(2, 0).getGreen()
            + 0.168 * testModel.getPixelAt(2, 0).getBlue());
    assertEquals(sepiaValueGreen,
            allModels.find("image-sepiaTone").getPixelAt(2, 0).getGreen());
    sepiaValueGreen = (int) (0.349 * testModel.getPixelAt(2, 1).getRed()
            + 0.686 * testModel.getPixelAt(2, 1).getGreen()
            + 0.168 * testModel.getPixelAt(2, 1).getBlue());
    assertEquals(sepiaValueGreen,
            allModels.find("image-sepiaTone").getPixelAt(2, 1).getGreen());
    sepiaValueGreen = (int) (0.349 * testModel.getPixelAt(2, 2).getRed()
            + 0.686 * testModel.getPixelAt(2, 2).getGreen()
            + 0.168 * testModel.getPixelAt(2, 2).getBlue());
    assertEquals(sepiaValueGreen,
            allModels.find("image-sepiaTone").getPixelAt(2, 2).getGreen());
    // Checks each pixel's blue value after running transform
    int sepiaValueBlue;
    sepiaValueBlue = (int) (0.272 * testModel.getPixelAt(0, 0).getRed()
            + 0.534 * testModel.getPixelAt(0, 0).getGreen()
            + 0.131 * testModel.getPixelAt(0, 0).getBlue());
    assertEquals(sepiaValueBlue,
            allModels.find("image-sepiaTone").getPixelAt(0, 0).getBlue());
    sepiaValueBlue = (int) (0.272 * testModel.getPixelAt(0, 1).getRed()
            + 0.534 * testModel.getPixelAt(0, 1).getGreen()
            + 0.131 * testModel.getPixelAt(0, 1).getBlue());
    assertEquals(sepiaValueBlue,
            allModels.find("image-sepiaTone").getPixelAt(0, 1).getBlue());
    sepiaValueBlue = (int) (0.272 * testModel.getPixelAt(0, 2).getRed()
            + 0.534 * testModel.getPixelAt(0, 2).getGreen()
            + 0.131 * testModel.getPixelAt(0, 2).getBlue());
    assertEquals(sepiaValueBlue,
            allModels.find("image-sepiaTone").getPixelAt(0, 2).getBlue());
    sepiaValueBlue = (int) (0.272 * testModel.getPixelAt(1, 0).getRed()
            + 0.534 * testModel.getPixelAt(1, 0).getGreen()
            + 0.131 * testModel.getPixelAt(1, 0).getBlue());
    assertEquals(sepiaValueBlue,
            allModels.find("image-sepiaTone").getPixelAt(1, 0).getBlue());
    sepiaValueBlue = (int) (0.272 * testModel.getPixelAt(1, 1).getRed()
            + 0.534 * testModel.getPixelAt(1, 1).getGreen()
            + 0.131 * testModel.getPixelAt(1, 1).getBlue());
    assertEquals(sepiaValueBlue,
            allModels.find("image-sepiaTone").getPixelAt(1, 1).getBlue());
    sepiaValueBlue = (int) (0.272 * testModel.getPixelAt(1, 2).getRed()
            + 0.534 * testModel.getPixelAt(1, 2).getGreen()
            + 0.131 * testModel.getPixelAt(1, 2).getBlue());
    assertEquals(sepiaValueBlue,
            allModels.find("image-sepiaTone").getPixelAt(1, 2).getBlue());
    sepiaValueBlue = (int) (0.272 * testModel.getPixelAt(2, 0).getRed()
            + 0.534 * testModel.getPixelAt(2, 0).getGreen()
            + 0.131 * testModel.getPixelAt(2, 0).getBlue());
    assertEquals(sepiaValueBlue,
            allModels.find("image-sepiaTone").getPixelAt(2, 0).getBlue());
    sepiaValueBlue = (int) (0.272 * testModel.getPixelAt(2, 1).getRed()
            + 0.534 * testModel.getPixelAt(2, 1).getGreen()
            + 0.131 * testModel.getPixelAt(2, 1).getBlue());
    assertEquals(sepiaValueBlue,
            allModels.find("image-sepiaTone").getPixelAt(2, 1).getBlue());
    sepiaValueBlue = (int) (0.272 * testModel.getPixelAt(2, 2).getRed()
            + 0.534 * testModel.getPixelAt(2, 2).getGreen()
            + 0.131 * testModel.getPixelAt(2, 2).getBlue());
    assertEquals(sepiaValueBlue,
            allModels.find("image-sepiaTone").getPixelAt(2, 2).getBlue());

  }
}
