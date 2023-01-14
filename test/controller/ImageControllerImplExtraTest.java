package controller;

import org.junit.Test;

import java.io.StringReader;
import java.util.HashMap;

import model.ImageModelMapImpl;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link ImageControllerImplExtra}.
 */
public class ImageControllerImplExtraTest extends ImageControllerImplTest{
  // Tests that the controller constructor throws an exception when given a null Appendable
  @Test(expected = IllegalArgumentException.class)
  public void testNullAppendable() {
    new ImageControllerImplExtra(null, new ImageModelMapImpl(new HashMap<>()));
  }

  // Tests that the controller constructor throws an exception when given a null ImageModelMap
  @Test(expected = IllegalArgumentException.class)
  public void testNullImageModelMap() {
    new ImageControllerImplExtra(new StringBuilder(), null);
  }

  // Tests that the output stream displays the correct error message when attempting to create
  // a mosaicked image from a not-yet-loaded image model
  @Test
  public void testMosaicBeforeLoad() {
    runHelper("mosaic 3 image mosaic-image",
            "Please ensure all images are loaded before using them to perform an operation.\n");
  }

  // Tests that a mosaicked image is created when given a valid loaded image model
  @Test
  public void testMosaicValidConstantAfterLoad() {
    runHelper("load test/image.ppm image " +
                    "mosaic 3 image mosaic-image",
            "Loading completed.\n" +
                    "Edit completed.\n");
  }

  // Tests that the output stream displays the correct error message when attempting to create
  // a mosaicked image from a valid loaded image model with an invalid constant
  @Test
  public void testMosaicInvalidConstantAfterLoad() {
    runHelper("load test/image.ppm image " +
                    "mosaic -1 image mosaic-image",
            "Loading completed.\n" +
                    "Please ensure that integer parameters are inputted in integer" +
                    " form and within their designated boundaries.\n");
  }

  // Tests that the output stream displays the correct error message when attempting to create
  // a mosaicked image from a valid loaded image model with a string constant
  @Test
  public void testMosaicStringConstantAfterLoad() {
    runHelper("load test/image.ppm image " +
                    "mosaic onehundred image mosaic-image",
            "Loading completed.\n" +
                    "Please ensure that integer parameters are inputted in integer" +
                    " form and within their designated boundaries.\n");
  }

  // Tests that the output stream displays the correct error message when attempting to create
  // a downscaled image from a not-yet-loaded image model
  @Test
  public void testDownscaleBeforeLoad() {
    runHelper("downscale 50 50 image downscale-image",
            "Please ensure all images are loaded before using them to perform an operation.\n");
  }

  // Tests that a downscaled image is created when given a valid loaded image model
  @Test
  public void testDownscaleValidConstantAfterLoad() {
    runHelper("load test/image.ppm image " +
                    "downscale 50 50 image downscale-image",
            "Loading completed.\n" +
                    "Edit completed.\n");
  }

  // Tests that the output stream displays the correct error message when attempting to create
  // a downscale image from a valid loaded image model with an invalid constant
  @Test
  public void testDownscaleInvalidConstantAfterLoad() {
    runHelper("load test/image.ppm image " +
                    "downscale -50 50 image downscale-image",
            "Loading completed.\n" +
                    "Please ensure that integer parameters are inputted in integer" +
                    " form and within their designated boundaries.\n");
  }

  // Tests that the output stream displays the correct error message when attempting to create
  // a downscale image from a valid loaded image model with a string constant
  @Test
  public void testDownscaleStringConstantAfterLoad() {
    runHelper("load test/image.ppm image " +
                    "downscale fifty 50 image downscale-image",
            "Loading completed.\n" +
                    "Please ensure that integer parameters are inputted in integer" +
                    " form and within their designated boundaries.\n");
  }

  // Tests that the output stream displays the correct error message when attempting to create
  // a masked image with a not-yet-loaded mask model
  @Test
  public void testMaskBeforeLoad() {
    runHelper("load test/image.ppm image " +
                    "red-component image mask mask-image",
            "Loading completed.\n" +
                    "Please ensure all images are loaded before using them to perform an operation.\n");
  }

  // Tests that a masked image is created when given a valid loaded mask model
  @Test
  public void testMaskAfterLoad() {
    runHelper("load test/image.ppm image " +
                    "load test/image.ppm mask " +
                    "red-component image mask mask-image",
            "Loading completed.\n" +
                    "Loading completed.\n" +
                    "Edit completed.\n");
  }

  /**
   * Handles an interaction with an object of an image model using a predetermined
   * input and output.
   *
   * @param input the input for the interaction
   * @param output the output for the interaction
   */
  @Override
  protected void runHelper(String input, String output) throws IllegalStateException {
    Readable testInput = new StringReader(input);
    Appendable testOutput = new StringBuilder();
    ImageModelMapImpl imageModelMap = new ImageModelMapImpl(new HashMap<>());
    ImageController controller = new ImageControllerImplExtra(testOutput, imageModelMap);
    controller.run(testInput, true);

    String fullOutput = testOutput.toString();
    String partialOutput = fullOutput.split("(save to-filepath name)")[1];
    String intendedOutput = partialOutput.split("\\r?\\n", 2)[1];

    assertEquals(output, intendedOutput);
  }
}