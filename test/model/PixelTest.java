package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests for {@link Pixel}.
 */
public class PixelTest {
  IPixel red = new Pixel(255, 0, 0);
  IPixel green = new Pixel(0, 255, 0);
  IPixel blue = new Pixel(0, 0, 255);

  // Tests that the pixel constructor throws an exception when given a negative red color value
  @Test(expected = IllegalArgumentException.class)
  public void testRedUnderMinValue() {
    new Pixel(-10, 100, 100);
  }

  // Tests that the pixel constructor throws an exception when given a red color value over the
  // default max value
  @Test(expected = IllegalArgumentException.class)
  public void testRedOverDefaultMaxValue() {
    new Pixel(256, 100, 100);
  }

  // Tests that the pixel constructor throws an exception when given a red color value over the
  // given max value
  @Test(expected = IllegalArgumentException.class)
  public void testRedOverGivenMaxValue() {
    new Pixel(300, 100, 34, 255);
  }

  // Tests that the pixel constructor throws an exception when given a negative green color value
  @Test(expected = IllegalArgumentException.class)
  public void testGreenUnderMinValue() {
    new Pixel(254, -50, 100);
  }

  // Tests that the pixel constructor throws an exception when given a green color value over the
  // default max value
  @Test(expected = IllegalArgumentException.class)
  public void testGreenOverDefaultMaxValue() {
    new Pixel(100, 300, 100);
  }

  // Tests that the pixel constructor throws an exception when given a green color value over the
  // given max value
  @Test(expected = IllegalArgumentException.class)
  public void testGreenOverGivenMaxValue() {
    new Pixel(12, 256, 34, 255);
  }

  // Tests that the pixel constructor throws an exception when given a negative blue color value
  @Test(expected = IllegalArgumentException.class)
  public void testBlueUnderMinValue() {
    new Pixel(125, 100, -1);
  }

  // Tests that the pixel constructor throws an exception when given a blue color value over the
  // default max value
  @Test(expected = IllegalArgumentException.class)
  public void testBlueOverDefaultMaxValue() {
    new Pixel(124, 100, 500);
  }

  // Tests that the pixel constructor throws an exception when given a blue color value over the
  // given max value
  @Test(expected = IllegalArgumentException.class)
  public void testBlueOverGivenMaxValue() {
    new Pixel(12, 100, 750, 500);
  }

  // Tests that getRed works as intended
  @Test
  public void testGetRed() {
    assertEquals(255, red.getRed());
    assertNotEquals(0, red.getRed());
  }

  // Tests that getGreen works as intended
  @Test
  public void testGetGreen() {
    assertEquals(255, green.getGreen());
    assertNotEquals(0, green.getGreen());
  }

  // Tests that getBlue works as intended
  @Test
  public void testGetBlue() {
    assertEquals(255, blue.getBlue());
    assertNotEquals(0, blue.getBlue());
  }

  // Tests that equals works as intended
  @Test
  public void testEquals() {
    assertNotEquals(blue, red);
    assertNotEquals(red, green);
    assertNotEquals(green, new Pixel(0, 0, 255));

    assertEquals(blue, blue);
    assertEquals(blue, new Pixel(0, 0, 255));
    assertEquals(green, green);
    assertEquals(green, new Pixel(0, 255, 0));
    assertEquals(red, red);
    assertEquals(red, new Pixel(255, 0, 0));
  }

  // Tests that hashCode works as intended
  @Test
  public void testHashCode() {
    assertNotEquals(blue.hashCode(), red.hashCode());
    assertNotEquals(red.hashCode(), green.hashCode());
    assertNotEquals(green.hashCode(), new Pixel(0, 0, 255).hashCode());

    assertEquals(blue.hashCode(), blue.hashCode());
    assertEquals(blue.hashCode(), new Pixel(0, 0, 255).hashCode());
    assertEquals(green.hashCode(), green.hashCode());
    assertEquals(green.hashCode(), new Pixel(0, 255, 0).hashCode());
    assertEquals(red.hashCode(), red.hashCode());
    assertEquals(red.hashCode(), new Pixel(255, 0, 0).hashCode());
  }
}
