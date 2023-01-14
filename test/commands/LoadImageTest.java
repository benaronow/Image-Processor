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
import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link LoadImage}.
 */
public class LoadImageTest {
  private ImageModelMapImpl allModels;

  // Load the files necessary for testing
  @Before
  public void init() {
    allModels = new ImageModelMapImpl(new HashMap<>());
    LoadImage imageLoader = new LoadImage(System.out, allModels, new Scanner(new StringReader("")));
    ImageModel testModel = imageLoader.loadImage("test/image.ppm");
    Appendable output = new StringBuilder();
    allModels.add("image", testModel, output);
  }

  // Tests that loadImage works as expected
  @Test
  public void testLoadImage() {
    // Tests the LoadImage command with a PPM file
    ImageCommand loadPPMImage = new LoadImage(new StringBuilder(), allModels,
            new Scanner(new StringReader("test/image.ppm testLoadPPMImage")));
    loadPPMImage.edit();

    // Checks that 'testLoadPPMImage' contains all the correct pixels
    assertEquals(allModels.find("testLoadPPMImage").getPixelAt(0, 0),
            new Pixel(0, 0, 0));
    assertEquals(allModels.find("testLoadPPMImage").getPixelAt(0, 1),
            new Pixel(255, 255, 255));
    assertEquals(allModels.find("testLoadPPMImage").getPixelAt(0, 2),
            new Pixel(122, 123, 122));
    assertEquals(allModels.find("testLoadPPMImage").getPixelAt(1, 0),
            new Pixel(100, 5, 5));
    assertEquals(allModels.find("testLoadPPMImage").getPixelAt(1, 1),
            new Pixel(5, 100, 5));
    assertEquals(allModels.find("testLoadPPMImage").getPixelAt(1, 2),
            new Pixel(5, 5, 100));
    assertEquals(allModels.find("testLoadPPMImage").getPixelAt(2, 0),
            new Pixel(255, 5, 5));
    assertEquals(allModels.find("testLoadPPMImage").getPixelAt(2, 1),
            new Pixel(5, 255, 5));
    assertEquals(allModels.find("testLoadPPMImage").getPixelAt(2, 2),
            new Pixel(5, 5, 255));

    // Tests the LoadImage command with a PNG file
    ImageCommand loadPNGImage = new LoadImage(new StringBuilder(), allModels,
            new Scanner(new StringReader("test/image.png testLoadPNGImage")));
    loadPNGImage.edit();

    // Checks that 'testLoadPNGImage' contains all the correct pixels
    assertEquals(allModels.find("testLoadPNGImage").getPixelAt(0, 0),
            new Pixel(0, 0, 0));
    assertEquals(allModels.find("testLoadPNGImage").getPixelAt(0, 1),
            new Pixel(255, 255, 255));
    assertEquals(allModels.find("testLoadPNGImage").getPixelAt(0, 2),
            new Pixel(122, 123, 122));
    assertEquals(allModels.find("testLoadPNGImage").getPixelAt(1, 0),
            new Pixel(100, 5, 5));
    assertEquals(allModels.find("testLoadPNGImage").getPixelAt(1, 1),
            new Pixel(5, 100, 5));
    assertEquals(allModels.find("testLoadPNGImage").getPixelAt(1, 2),
            new Pixel(5, 5, 100));
    assertEquals(allModels.find("testLoadPNGImage").getPixelAt(2, 0),
            new Pixel(255, 5, 5));
    assertEquals(allModels.find("testLoadPNGImage").getPixelAt(2, 1),
            new Pixel(5, 255, 5));
    assertEquals(allModels.find("testLoadPNGImage").getPixelAt(2, 2),
            new Pixel(5, 5, 255));

    // Tests the LoadImage command with a BMP file
    ImageCommand loadBMPImage = new LoadImage(new StringBuilder(), allModels,
            new Scanner(new StringReader("test/image.bmp testLoadBMPImage")));
    loadBMPImage.edit();


    // Checks that 'testLoadBMPImage' contains all the correct pixels
    assertEquals(allModels.find("testLoadBMPImage").getPixelAt(0, 0),
            new Pixel(0, 0, 0));
    assertEquals(allModels.find("testLoadBMPImage").getPixelAt(0, 1),
            new Pixel(255, 255, 255));
    assertEquals(allModels.find("testLoadBMPImage").getPixelAt(0, 2),
            new Pixel(122, 123, 122));
    assertEquals(allModels.find("testLoadBMPImage").getPixelAt(1, 0),
            new Pixel(100, 5, 5));
    assertEquals(allModels.find("testLoadBMPImage").getPixelAt(1, 1),
            new Pixel(5, 100, 5));
    assertEquals(allModels.find("testLoadBMPImage").getPixelAt(1, 2),
            new Pixel(5, 5, 100));
    assertEquals(allModels.find("testLoadBMPImage").getPixelAt(2, 0),
            new Pixel(255, 5, 5));
    assertEquals(allModels.find("testLoadBMPImage").getPixelAt(2, 1),
            new Pixel(5, 255, 5));
    assertEquals(allModels.find("testLoadBMPImage").getPixelAt(2, 2),
            new Pixel(5, 5, 255));

    // Tests the LoadImage command with a JPEG file
    ImageCommand loadJPEGImage = new LoadImage(new StringBuilder(), allModels,
            new Scanner(new StringReader("test/image.jpg testLoadJPEGImage")));
    loadJPEGImage.edit();

    // Checks that 'testLoadJPEGImage' contains all the correct pixels
    // Jpeg images are lossy, meaning that every time you save them,
    // you potentially lose a little more information, so we are just checking
    // that each red, green, and blue value in a Pixel is within one.
    assertTrue(Math.abs(allModels.find("testLoadJPEGImage")
            .getPixelAt(0, 0).getRed() - new Pixel(0, 0, 0).getRed()) < 2);
    assertTrue(Math.abs(allModels.find("testLoadJPEGImage")
            .getPixelAt(0, 0).getGreen() - new Pixel(0, 0, 0).getGreen()) < 2);
    assertTrue(Math.abs(allModels.find("testLoadJPEGImage")
            .getPixelAt(0, 0).getBlue() - new Pixel(0, 0, 0).getBlue()) < 2);

    assertTrue(Math.abs(allModels.find("testLoadJPEGImage")
            .getPixelAt(0, 1).getRed() - new Pixel(255, 255, 255).getRed()) < 2);
    assertTrue(Math.abs(allModels.find("testLoadJPEGImage")
            .getPixelAt(0, 1).getGreen() - new Pixel(255, 255, 255).getGreen()) < 2);
    assertTrue(Math.abs(allModels.find("testLoadJPEGImage")
            .getPixelAt(0, 1).getBlue() - new Pixel(255, 255, 255).getBlue()) < 2);

    assertTrue(Math.abs(allModels.find("testLoadJPEGImage")
            .getPixelAt(0, 2).getRed() - new Pixel(122, 123, 122).getRed()) < 2);
    assertTrue(Math.abs(allModels.find("testLoadJPEGImage")
            .getPixelAt(0, 2).getGreen() - new Pixel(122, 123, 122).getGreen()) < 2);
    assertTrue(Math.abs(allModels.find("testLoadJPEGImage")
            .getPixelAt(0, 2).getBlue() - new Pixel(122, 123, 122).getBlue()) < 2);

    assertTrue(Math.abs(allModels.find("testLoadJPEGImage")
            .getPixelAt(1, 0).getRed() - new Pixel(100, 5, 5).getRed()) < 2);
    assertTrue(Math.abs(allModels.find("testLoadJPEGImage")
            .getPixelAt(1, 0).getGreen() - new Pixel(100, 5, 5).getGreen()) < 2);
    assertTrue(Math.abs(allModels.find("testLoadJPEGImage")
            .getPixelAt(1, 0).getBlue() - new Pixel(100, 5, 5).getBlue()) < 2);

    assertTrue(Math.abs(allModels.find("testLoadJPEGImage")
            .getPixelAt(1, 1).getRed() - new Pixel(5, 100, 5).getRed()) < 2);
    assertTrue(Math.abs(allModels.find("testLoadJPEGImage")
            .getPixelAt(1, 1).getGreen() - new Pixel(5, 100, 5).getGreen()) < 2);
    assertTrue(Math.abs(allModels.find("testLoadJPEGImage")
            .getPixelAt(1, 1).getBlue() - new Pixel(5, 100, 5).getBlue()) < 2);

    assertTrue(Math.abs(allModels.find("testLoadJPEGImage")
            .getPixelAt(1, 2).getRed() - new Pixel(5, 5, 100).getRed()) < 2);
    assertTrue(Math.abs(allModels.find("testLoadJPEGImage")
            .getPixelAt(1, 2).getGreen() - new Pixel(5, 5, 100).getGreen()) < 2);
    assertTrue(Math.abs(allModels.find("testLoadJPEGImage")
            .getPixelAt(1, 2).getBlue() - new Pixel(5, 5, 100).getBlue()) < 2);

    assertTrue(Math.abs(allModels.find("testLoadJPEGImage")
            .getPixelAt(2, 0).getRed() - new Pixel(255, 5, 5).getRed()) < 2);
    assertTrue(Math.abs(allModels.find("testLoadJPEGImage")
            .getPixelAt(2, 0).getGreen() - new Pixel(255, 5, 5).getGreen()) < 2);
    assertTrue(Math.abs(allModels.find("testLoadJPEGImage")
            .getPixelAt(2, 0).getBlue() - new Pixel(255, 5, 5).getBlue()) < 2);

    assertTrue(Math.abs(allModels.find("testLoadJPEGImage")
            .getPixelAt(2, 1).getRed() - new Pixel(5, 255, 5).getRed()) < 2);
    assertTrue(Math.abs(allModels.find("testLoadJPEGImage")
            .getPixelAt(2, 1).getGreen() - new Pixel(5, 255, 5).getGreen()) < 2);
    assertTrue(Math.abs(allModels.find("testLoadJPEGImage")
            .getPixelAt(2, 1).getBlue() - new Pixel(5, 255, 5).getBlue()) < 2);

    assertTrue(Math.abs(allModels.find("testLoadJPEGImage")
            .getPixelAt(2, 2).getRed() - new Pixel(5, 5, 255).getRed()) < 2);
    assertTrue(Math.abs(allModels.find("testLoadJPEGImage")
            .getPixelAt(2, 2).getGreen() - new Pixel(5, 5, 255).getGreen()) < 2);
    assertTrue(Math.abs(allModels.find("testLoadJPEGImage")
            .getPixelAt(2, 2).getBlue() - new Pixel(5, 5, 255).getBlue()) < 2);
  }
}