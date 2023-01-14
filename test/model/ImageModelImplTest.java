package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link ImageModelImpl}.
 */
public class ImageModelImplTest {
  ImageModel testModel;
  Pixel[][] pixels;

  // Initialize pixels for testing
  @Before
  public void init() {
    pixels = new Pixel[3][3];
    pixels[0][0] = new Pixel(0, 0, 0);
    pixels[0][1] = new Pixel(255, 255, 255);
    pixels[0][2] = new Pixel(122, 123, 122);
    pixels[1][0] = new Pixel(100, 5, 5);
    pixels[1][1] = new Pixel(5, 100, 5);
    pixels[1][2] = new Pixel(5, 5, 100);
    pixels[2][0] = new Pixel(255, 5, 5);
    pixels[2][1] = new Pixel(5, 255, 5);
    pixels[2][2] = new Pixel(5, 5, 255);

    testModel = new ImageModelImpl(pixels, 3, 3);
  }

  // Tests that getImageHeight works as intended
  @Test
  public void testGetImageHeight() {
    assertEquals(3, testModel.getImageHeight());
    ImageModel testModelWidth1 = new ImageModelImpl(new Pixel[1][4], 1, 4);
    assertEquals(1, testModelWidth1.getImageHeight());
    ImageModel testModelWidth5 = new ImageModelImpl(new Pixel[5][1], 5, 1);
    assertEquals(5, testModelWidth5.getImageHeight());
  }

  // Tests that getImageWidth works as intended
  @Test
  public void testGetImageWidth() {
    assertEquals(3, testModel.getImageWidth());
    ImageModel testModelWidth1 = new ImageModelImpl(new Pixel[1][4], 1, 4);
    assertEquals(4, testModelWidth1.getImageWidth());
    ImageModel testModelWidth5 = new ImageModelImpl(new Pixel[5][1], 5, 1);
    assertEquals(1, testModelWidth5.getImageWidth());
  }

  // Tests that getPixelAt works as intended
  @Test
  public void testGetPixelAt() {
    assertEquals(new Pixel(0, 0, 0), testModel.getPixelAt(0, 0));
    assertEquals(new Pixel(255, 255, 255), testModel.getPixelAt(0, 1));
    assertEquals(new Pixel(122, 123, 122), testModel.getPixelAt(0, 2));
    assertEquals(new Pixel(100, 5, 5), testModel.getPixelAt(1, 0));
    assertEquals(new Pixel(5, 100, 5), testModel.getPixelAt(1, 1));
    assertEquals(new Pixel(5, 5, 100), testModel.getPixelAt(1, 2));
    assertEquals(new Pixel(255, 5, 5), testModel.getPixelAt(2, 0));
    assertEquals(new Pixel(5, 255, 5), testModel.getPixelAt(2, 1));
    assertEquals(new Pixel(5, 5, 255), testModel.getPixelAt(2, 2));
  }
}



