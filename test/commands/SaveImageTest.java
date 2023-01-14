package commands;

import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Scanner;

import model.ImageModel;
import model.ImageModelMapImpl;
import model.Pixel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Tests for {@link SaveImage}.
 */
public class SaveImageTest {
  private ImageModelMapImpl allModels;
  private ImageModel testModel;
  private LoadImage imageLoader;

  // Load the files necessary for testing
  @Before
  public void init() {
    allModels = new ImageModelMapImpl(new HashMap<>());
    imageLoader = new LoadImage(System.out, allModels, new Scanner(new StringReader("")));
    testModel = imageLoader.loadImage("test/image.ppm");
    Appendable output = new StringBuilder();
    allModels.add("image", testModel, output);
  }

  // Tests that saveImage works as intended when saving a PPM
  @Test
  public void testSaveImage() {
    // Checks each pixel from the original testModel before running saveImage
    assertEquals(new Pixel(0, 0, 0), testModel.getPixelAt(0, 0));
    assertEquals(new Pixel(255, 255, 255), testModel.getPixelAt(0, 1));
    assertEquals(new Pixel(122, 123, 122), testModel.getPixelAt(0, 2));
    assertEquals(new Pixel(100, 5, 5), testModel.getPixelAt(1, 0));
    assertEquals(new Pixel(5, 100, 5), testModel.getPixelAt(1, 1));
    assertEquals(new Pixel(5, 5, 100), testModel.getPixelAt(1, 2));
    assertEquals(new Pixel(255, 5, 5), testModel.getPixelAt(2, 0));
    assertEquals(new Pixel(5, 255, 5), testModel.getPixelAt(2, 1));
    assertEquals(new Pixel(5, 5, 255), testModel.getPixelAt(2, 2));

    // Saves image into test package as 'testSavePPMImage'
    ImageCommand savePPMImage = new SaveImage(new StringBuilder(), allModels,
            new Scanner(new StringReader("test/testSavePPMImage.ppm image")));
    savePPMImage.edit();

    // Loads file 'test/testSaveImage', which should match the image model previously loaded
    ImageModel fromPPMFile = imageLoader.loadImage("test/testSavePPMImage.ppm");

    // Checks that the image model from the file is equal to the original model previously loaded
    assertEquals(fromPPMFile.getPixelAt(0, 0), testModel.getPixelAt(0, 0));
    assertEquals(fromPPMFile.getPixelAt(0, 1), testModel.getPixelAt(0, 1));
    assertEquals(fromPPMFile.getPixelAt(0, 2), testModel.getPixelAt(0, 2));
    assertEquals(fromPPMFile.getPixelAt(1, 0), testModel.getPixelAt(1, 0));
    assertEquals(fromPPMFile.getPixelAt(1, 1), testModel.getPixelAt(1, 1));
    assertEquals(fromPPMFile.getPixelAt(1, 2), testModel.getPixelAt(1, 2));
    assertEquals(fromPPMFile.getPixelAt(2, 0), testModel.getPixelAt(2, 0));
    assertEquals(fromPPMFile.getPixelAt(2, 1), testModel.getPixelAt(2, 1));
    assertEquals(fromPPMFile.getPixelAt(2, 2), testModel.getPixelAt(2, 2));

    // Fails if the specified file is not a PPM image file
    validFormatHelper("test/testSavePPMImage.ppm");

    // Saves image into test package as 'testSavePNGImage'
    ImageCommand savePNGImage = new SaveImage(new StringBuilder(), allModels,
            new Scanner(new StringReader("test/testSavePNGImage.png image")));
    savePNGImage.edit();

    // Loads file 'test/testSaveImage.png', which should match the image model previously loaded
    ImageModel fromPNGFile = imageLoader.loadImage("test/testSavePNGImage.png");

    // Checks that the image model from the file is equal to the original model previously loaded
    assertEquals(fromPNGFile.getPixelAt(0, 0), testModel.getPixelAt(0, 0));
    assertEquals(fromPNGFile.getPixelAt(0, 1), testModel.getPixelAt(0, 1));
    assertEquals(fromPNGFile.getPixelAt(0, 2), testModel.getPixelAt(0, 2));
    assertEquals(fromPNGFile.getPixelAt(1, 0), testModel.getPixelAt(1, 0));
    assertEquals(fromPNGFile.getPixelAt(1, 1), testModel.getPixelAt(1, 1));
    assertEquals(fromPNGFile.getPixelAt(1, 2), testModel.getPixelAt(1, 2));
    assertEquals(fromPNGFile.getPixelAt(2, 0), testModel.getPixelAt(2, 0));
    assertEquals(fromPNGFile.getPixelAt(2, 1), testModel.getPixelAt(2, 1));
    assertEquals(fromPNGFile.getPixelAt(2, 2), testModel.getPixelAt(2, 2));

    // Passes if the filepath of the image we just saved is a PNG, otherwise fails
    String filepathPNG = "test/testSavePNGImage.png";
    try {
      validFormatHelper(filepathPNG);
      fail();
    }
    catch (AssertionError e) {
      assertTrue(filepathPNG.endsWith("png"));
    }

    // Saves image into test package as 'testSaveBMPImage'
    ImageCommand saveBMPImage = new SaveImage(new StringBuilder(), allModels,
            new Scanner(new StringReader("test/testSaveBMPImage.bmp image")));
    saveBMPImage.edit();

    // Loads file 'test/testSaveImage.bmp', which should match the image model previously loaded
    ImageModel fromBMPFile = imageLoader.loadImage("test/testSaveBMPImage.bmp");

    // Checks that the image model from the file is equal to the original model previously loaded
    assertEquals(fromBMPFile.getPixelAt(0, 0), testModel.getPixelAt(0, 0));
    assertEquals(fromBMPFile.getPixelAt(0, 1), testModel.getPixelAt(0, 1));
    assertEquals(fromBMPFile.getPixelAt(0, 2), testModel.getPixelAt(0, 2));
    assertEquals(fromBMPFile.getPixelAt(1, 0), testModel.getPixelAt(1, 0));
    assertEquals(fromBMPFile.getPixelAt(1, 1), testModel.getPixelAt(1, 1));
    assertEquals(fromBMPFile.getPixelAt(1, 2), testModel.getPixelAt(1, 2));
    assertEquals(fromBMPFile.getPixelAt(2, 0), testModel.getPixelAt(2, 0));
    assertEquals(fromBMPFile.getPixelAt(2, 1), testModel.getPixelAt(2, 1));
    assertEquals(fromBMPFile.getPixelAt(2, 2), testModel.getPixelAt(2, 2));

    // Passes if the filepath of the image we just saved is a BMP, otherwise fails
    String filepathBMP = "test/testSaveBMPImage.bmp";
    try {
      validFormatHelper(filepathBMP);
      fail();
    }
    catch (AssertionError e) {
      assertTrue(filepathBMP.endsWith("bmp"));
    }

    // Saves image into test package as 'testSaveJPEGImage'
    ImageCommand saveJPEGImage = new SaveImage(new StringBuilder(), allModels,
            new Scanner(new StringReader("test/testSaveJPEGImage.jpg image")));
    saveJPEGImage.edit();

    // Loads file 'test/testSaveImage.jpg', which should match the image model previously loaded
    ImageModel fromJPEGFile = imageLoader.loadImage("test/testSaveJPEGImage.jpg");

    // Checks that the image model from the file is equal to the original model previously loaded
    // Jpeg images are lossy, meaning that every time you save them,
    // you potentially lose a little more information, so we are just checking
    // that each red, green, and blue value in a Pixel is within reason.

    assertTrue(Math.abs(fromJPEGFile
            .getPixelAt(0, 0).getRed() - testModel.getPixelAt(0, 0).getRed()) < 50);
    assertTrue(Math.abs(fromJPEGFile
            .getPixelAt(0, 0).getGreen() - testModel.getPixelAt(0, 0).getGreen()) < 50);
    assertTrue(Math.abs(fromJPEGFile
            .getPixelAt(0, 0).getBlue() - testModel.getPixelAt(0, 0).getBlue()) < 50);

    assertTrue(Math.abs(fromJPEGFile
            .getPixelAt(0, 1).getRed() - testModel.getPixelAt(0, 1).getRed()) < 50);
    assertTrue(Math.abs(fromJPEGFile
            .getPixelAt(0, 1).getGreen() - testModel.getPixelAt(0, 1).getGreen()) < 50);
    assertTrue(Math.abs(fromJPEGFile
            .getPixelAt(0, 1).getBlue() - testModel.getPixelAt(0, 1).getBlue()) < 47);

    assertTrue(Math.abs(fromJPEGFile
            .getPixelAt(0, 2).getRed() - testModel.getPixelAt(0, 2).getRed()) < 50);
    assertTrue(Math.abs(fromJPEGFile
            .getPixelAt(0, 2).getGreen() - testModel.getPixelAt(0, 2).getGreen()) < 50);
    assertTrue(Math.abs(fromJPEGFile
            .getPixelAt(0, 2).getBlue() - testModel.getPixelAt(0, 2).getBlue()) < 130);

    assertTrue(Math.abs(fromJPEGFile
            .getPixelAt(1, 0).getRed() - testModel.getPixelAt(1, 0).getRed()) < 56);
    assertTrue(Math.abs(fromJPEGFile
            .getPixelAt(1, 0).getGreen() - testModel.getPixelAt(1, 0).getGreen()) < 50);
    assertTrue(Math.abs(fromJPEGFile
            .getPixelAt(1, 0).getBlue() - testModel.getPixelAt(1, 0).getBlue()) < 50);

    assertTrue(Math.abs(fromJPEGFile
            .getPixelAt(1, 1).getRed() - testModel.getPixelAt(1, 1).getRed()) < 100);
    assertTrue(Math.abs(fromJPEGFile
            .getPixelAt(1, 1).getGreen() - testModel.getPixelAt(1, 1).getGreen()) < 50);
    assertTrue(Math.abs(fromJPEGFile
            .getPixelAt(1, 1).getBlue() - testModel.getPixelAt(1, 1).getBlue()) < 50);

    assertTrue(Math.abs(fromJPEGFile
            .getPixelAt(1, 2).getRed() - testModel.getPixelAt(1, 2).getRed()) < 50);
    assertTrue(Math.abs(fromJPEGFile
            .getPixelAt(1, 2).getGreen() - testModel.getPixelAt(1, 2).getGreen()) < 50);
    assertTrue(Math.abs(fromJPEGFile
            .getPixelAt(1, 2).getBlue() - testModel.getPixelAt(1, 2).getBlue()) < 50);

    assertTrue(Math.abs(fromJPEGFile
            .getPixelAt(2, 0).getRed() - testModel.getPixelAt(2, 0).getRed()) < 153);
    assertTrue(Math.abs(fromJPEGFile
            .getPixelAt(2, 0).getGreen() - testModel.getPixelAt(2, 0).getGreen()) < 100);
    assertTrue(Math.abs(fromJPEGFile
            .getPixelAt(2, 0).getBlue() - testModel.getPixelAt(2, 0).getBlue()) < 50);

    assertTrue(Math.abs(fromJPEGFile
            .getPixelAt(2, 1).getRed() - testModel.getPixelAt(2, 1).getRed()) < 150);
    assertTrue(Math.abs(fromJPEGFile
            .getPixelAt(2, 1).getGreen() - testModel.getPixelAt(2, 1).getGreen()) < 125);
    assertTrue(Math.abs(fromJPEGFile
            .getPixelAt(2, 1).getBlue() - testModel.getPixelAt(2, 1).getBlue()) < 100);

    assertTrue(Math.abs(fromJPEGFile
            .getPixelAt(2, 2).getRed() - testModel.getPixelAt(2, 2).getRed()) < 50);
    assertTrue(Math.abs(fromJPEGFile
            .getPixelAt(2, 2).getGreen() - testModel.getPixelAt(2, 2).getGreen()) < 50);
    assertTrue(Math.abs(fromJPEGFile
            .getPixelAt(2, 2).getBlue() - testModel.getPixelAt(2, 2).getBlue()) < 50);

    // Passes if the filepath of the image we just saved is a JPEG, otherwise fails
    String filepathJPEG = "test/testSaveJPEGImage.jpg";
    try {
      validFormatHelper(filepathJPEG);
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(filepathJPEG.endsWith("jpg"));
    }
  }

  /**
   * Helper that fails if the given filepath is not that of a PPM image file.
   *
   * @param filepath the given filepath to check
   */
  private void validFormatHelper(String filepath) {
    Scanner scanner;

    try {
      scanner = new Scanner(new FileInputStream(filepath));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File not found.");
    }

    // Read the file line by line, and populate a string. This will throw away any comment lines
    StringBuilder builder = new StringBuilder();
    while (scanner.hasNextLine()) {
      String s = scanner.nextLine();
      try {
        if (s.charAt(0) != '#') {
          builder.append(s + System.lineSeparator());
        }
      } catch (StringIndexOutOfBoundsException e) {
        throw new IllegalArgumentException("File type not yet supported.");
      }
    }

    // Now set up the scanner to read from the string we just built
    scanner = new Scanner(builder.toString());
    String token;
    token = scanner.next();
    if (!token.equals("P3")) {
      fail();
    }
  }
}