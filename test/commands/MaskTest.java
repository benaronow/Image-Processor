package commands;

import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Scanner;

import model.IPixel;
import model.ImageModel;
import model.ImageModelImpl;
import model.ImageModelMapImpl;
import model.Pixel;

import static org.junit.Assert.assertEquals;

/**
 * Tests masking operations for {@link AbstractImageCommand]}
 */
public class MaskTest {
  private ImageModelMapImpl allModels;
  private ImageModel testModel;

  // Load the files necessary for testing
  @Before
  public void init() {
    allModels = new ImageModelMapImpl(new HashMap<>());
    LoadImage imageLoader = new LoadImage(System.out, allModels, new Scanner(new StringReader("")));
    testModel = imageLoader.loadImage("test/image.ppm");

    IPixel[][] testMaskPixels = new IPixel[3][3];
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (j < 2) {
          testMaskPixels[i][j] = new Pixel(0, 0, 0);
        } else {
          testMaskPixels[i][j] = new Pixel(255, 255, 255);
        }
      }
    }
    ImageModel testMask = new ImageModelImpl(testMaskPixels, 3, 3);

    Appendable output = new StringBuilder();
    allModels.add("image", testModel, output);
    allModels.add("mask", testMask, output);
  }

  // Tests that masking with a red greyscale command works as expected
  @Test
  public void redGreyscaleMaskTest() {
    // Runs the RedGreyscale command
    ImageCommand redGreyscale = new RedGreyscale(new StringBuilder(), allModels,
            new Scanner(new StringReader(" image image-redGreyscale")));
    redGreyscale.edit();
    ImageModel newImage = allModels.find("image-redGreyscale");

    // Runs the RedGreyscale command using a mask
    ImageCommand redGreyscaleMask = new RedGreyscale(new StringBuilder(), allModels,
            new Scanner(new StringReader(" image mask image-redGreyscale-mask")));
    redGreyscaleMask.edit();
    ImageModel newMaskImage = allModels.find("image-redGreyscale-mask");

    // Checks that the mask operation works as expected
    testPixelEquality(testModel, newImage, newMaskImage);
  }

  // Tests that masking with a green greyscale command works as expected
  @Test
  public void greenGreyscaleMaskTest() {
    // Runs the green greyscale command
    ImageCommand greenGreyscale = new GreenGreyscale(new StringBuilder(), allModels,
            new Scanner(new StringReader(" image image-greenGreyscale")));
    greenGreyscale.edit();
    ImageModel newImage = allModels.find("image-greenGreyscale");

    // Runs the green greyscale command using a mask
    ImageCommand greenGreyscaleMask = new GreenGreyscale(new StringBuilder(), allModels,
            new Scanner(new StringReader(" image mask image-greenGreyscale-mask")));
    greenGreyscaleMask.edit();
    ImageModel newMaskImage = allModels.find("image-greenGreyscale-mask");

    // Checks that the mask operation works as expected
    testPixelEquality(testModel, newImage, newMaskImage);
  }

  // Tests that masking with a blue greyscale command works as expected
  @Test
  public void blueGreyscaleMaskTest() {
    // Runs the blue greyscale command
    ImageCommand blueGreyscale = new BlueGreyscale(new StringBuilder(), allModels,
            new Scanner(new StringReader(" image image-blueGreyscale")));
    blueGreyscale.edit();
    ImageModel newImage = allModels.find("image-blueGreyscale");

    // Runs the blue greyscale command using a mask
    ImageCommand blueGreyscaleMask = new BlueGreyscale(new StringBuilder(), allModels,
            new Scanner(new StringReader(" image mask image-blueGreyscale-mask")));
    blueGreyscaleMask.edit();
    ImageModel newMaskImage = allModels.find("image-blueGreyscale-mask");

    // Checks that the mask operation works as expected
    testPixelEquality(testModel, newImage, newMaskImage);
  }

  // Tests that masking with a max-value greyscale command works as expected
  @Test
  public void maxvalueGreyscaleMaskTest() {
    // Runs the max-value command
    ImageCommand maxvalueGreyscale = new MaxValueGreyscale(new StringBuilder(), allModels,
            new Scanner(new StringReader(" image image-maxvalueGreyscale")));
    maxvalueGreyscale.edit();
    ImageModel newImage = allModels.find("image-maxvalueGreyscale");

    // Runs the max-value command using a mask
    ImageCommand maxvalueGreyscaleMask = new MaxValueGreyscale(new StringBuilder(), allModels,
            new Scanner(new StringReader(" image mask image-maxvalueGreyscale-mask")));
    maxvalueGreyscaleMask.edit();
    ImageModel newMaskImage = allModels.find("image-maxvalueGreyscale-mask");

    // Checks that the mask operation works as expected
    testPixelEquality(testModel, newImage, newMaskImage);
  }

  // Tests that masking with a luma greyscale command works as expected
  @Test
  public void lumaGreyscaleMaskTest() {
    // Runs the luma greyscale command using the mask
    ImageCommand lumaGreyscale = new LumaGreyscale(new StringBuilder(), allModels,
            new Scanner(new StringReader(" image image-lumaGreyscale")));
    lumaGreyscale.edit();
    ImageModel newImage = allModels.find("image-lumaGreyscale");

    // Runs the luma greyscale command using a mask
    ImageCommand lumaGreyscaleMask = new LumaGreyscale(new StringBuilder(), allModels,
            new Scanner(new StringReader(" image mask image-lumaGreyscale-mask")));
    lumaGreyscaleMask.edit();
    ImageModel newMaskImage = allModels.find("image-lumaGreyscale-mask");

    // Checks that the mask operation works as expected
    testPixelEquality(testModel, newImage, newMaskImage);
  }

  // Tests that masking with an intensity greyscale command works as expected
  @Test
  public void intensityGreyscaleMaskTest() {
    // Runs the intensity greyscale command
    ImageCommand intensityGreyscale = new IntensityGreyscale(new StringBuilder(), allModels,
            new Scanner(new StringReader(" image image-intensityGreyscale")));
    intensityGreyscale.edit();
    ImageModel newImage = allModels.find("image-intensityGreyscale");

    // Runs the intensity greyscale command using a mask
    ImageCommand intensityGreyscaleMask = new IntensityGreyscale(new StringBuilder(), allModels,
            new Scanner(new StringReader(" image mask image-intensityGreyscale-mask")));
    intensityGreyscaleMask.edit();
    ImageModel newMaskImage = allModels.find("image-intensityGreyscale-mask");

    // Checks that the mask operation works as expected
    testPixelEquality(testModel, newImage, newMaskImage);
  }

  // Tests that masking with a blur filter command works as expected
  @Test
  public void blurFilterMaskTest() {
    // Runs the blur filter command
    ImageCommand blurFilter = new BlurFilter(new StringBuilder(), allModels,
            new Scanner(new StringReader(" image image-blurFilter")));
    blurFilter.edit();
    ImageModel newImage = allModels.find("image-blurFilter");

    // Runs the blur filter command using a mask
    ImageCommand blurFilterMask = new BlurFilter(new StringBuilder(), allModels,
            new Scanner(new StringReader(" image mask image-blurFilter-mask")));
    blurFilterMask.edit();
    ImageModel newMaskImage = allModels.find("image-blurFilter-mask");

    // Checks that the mask operation works as expected
    testPixelEquality(testModel, newImage, newMaskImage);
  }

  // Tests that masking with a sharpen filter command works as expected
  @Test
  public void sharpenFilterMaskTest() {
    // Runs the sharpen filter command
    ImageCommand sharpenFilter = new SharpenFilter(new StringBuilder(), allModels,
            new Scanner(new StringReader(" image image-sharpenFilter")));
    sharpenFilter.edit();
    ImageModel newImage = allModels.find("image-sharpenFilter");

    // Runs the sharpen filter command using a mask
    ImageCommand sharpenFilterMask = new SharpenFilter(new StringBuilder(), allModels,
            new Scanner(new StringReader(" image mask image-sharpenFilter-mask")));
    sharpenFilterMask.edit();
    ImageModel newMaskImage = allModels.find("image-sharpenFilter-mask");

    // Checks that the mask operation works as expected
    testPixelEquality(testModel, newImage, newMaskImage);
  }

  // Tests that masking with a brighten command works as expected
  @Test
  public void brightenMaskTest() {
    // Runs the brighten command
    ImageCommand brighten = new Brighten(new StringBuilder(), allModels,
            new Scanner(new StringReader("100 image image-brighten")));
    brighten.edit();
    ImageModel newImage = allModels.find("image-brighten");

    // Runs the brighten command using a mask
    ImageCommand brightenMask = new Brighten(new StringBuilder(), allModels,
            new Scanner(new StringReader("100 image mask image-brighten-mask")));
    brightenMask.edit();
    ImageModel newMaskImage = allModels.find("image-brighten-mask");

    // Checks that the mask operation works as expected
    testPixelEquality(testModel, newImage, newMaskImage);
  }

  // Tests that masking with a mosaic command works as expected
  @Test
  public void mosaicMaskTest() {
    // Runs the mosaic command
    ImageCommand mosaic = new Mosaic(new StringBuilder(), allModels,
            new Scanner(new StringReader("4 image image-mosaic")), 1);
    mosaic.edit();
    ImageModel newImage = allModels.find("image-mosaic");

    // Runs the mosaic command using a mask
    ImageCommand mosaicMask = new Mosaic(new StringBuilder(), allModels,
            new Scanner(new StringReader("4 image mask image-mosaic-mask")), 1);
    mosaicMask.edit();
    ImageModel newMaskImage = allModels.find("image-mosaic-mask");

    // Checks that the mask operation works as expected
    testPixelEquality(testModel, newImage, newMaskImage);
  }

  // Checks that all the new pixels are correct based on the mask used
  private void testPixelEquality(ImageModel testModel, ImageModel newImage,
                                 ImageModel newMaskImage) {
    // Checks that the pixels over the black section of the mask are correct
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals(newMaskImage.getPixelAt(j, i).getRed(),
                newImage.getPixelAt(j, i).getRed());
        assertEquals(newMaskImage.getPixelAt(j, i).getGreen(),
                newImage.getPixelAt(j, i).getGreen());
        assertEquals(newMaskImage.getPixelAt(j, i).getBlue(),
                newImage.getPixelAt(j, i).getBlue());
      }
    }

    // Checks that the pixels over the white section of the mask are correct
    for (int i = 0; i < 3; i++) {
      assertEquals(newMaskImage.getPixelAt(i, 2).getRed(),
              testModel.getPixelAt(i, 2).getRed());
      assertEquals(newMaskImage.getPixelAt(i, 2).getGreen(),
              testModel.getPixelAt(i, 2).getGreen());
      assertEquals(newMaskImage.getPixelAt(i, 2).getBlue(),
              testModel.getPixelAt(i, 2).getBlue());
    }
  }
}
