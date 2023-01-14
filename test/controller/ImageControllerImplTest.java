package controller;

import org.junit.Test;

import java.io.StringReader;
import java.util.HashMap;

import model.ImageModelMapImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests for {@link ImageControllerImpl}.
 */
public class ImageControllerImplTest {
  // Tests that the controller constructor throws an exception when given a null Appendable
  @Test(expected = IllegalArgumentException.class)
  public void testNullAppendable() {
    new ImageControllerImpl(null, new ImageModelMapImpl(new HashMap<>()));
  }

  // Tests that the controller constructor throws an exception when given a null ImageModelMap
  @Test(expected = IllegalArgumentException.class)
  public void testNullImageModelMap() {
    new ImageControllerImpl(new StringBuilder(), null);
  }

  // Tests that the start message is displayed as intended
  @Test
  public void testStartMessage() {
    Readable testInput = new StringReader("");
    Appendable testOutput = new StringBuilder();
    ImageModelMapImpl imageModelMap = new ImageModelMapImpl(new HashMap<>());
    ImageController controller = new ImageControllerImpl(testOutput, imageModelMap);
    controller.run(testInput, true);

    assertEquals("" +
            "Welcome to the Image Processor! Supported operations include:\n" +
            "1. Image Loading (load from-filepath name)\n" +
            "2. Color Transformations (operation name new-name)\n" +
            "   These operations include red-component, green-component, blue-component,\n" +
            "   maxvalue-component, luma-component, intensity-component, and sepia-tone.\n" +
            "3. Image Flipping (operation name new-name)\n" +
            "   These operations include horizontal-flip and vertical-flip.\n" +
            "4. Image Filtering (operation name new-name)\n" +
            "   These operations include blur-filter and sharpen-filter.\n" +
            "5. Image Saving (save to-filepath name)\n",
            testOutput.toString());
  }

  // Tests that quit works as intended as the first user input
  @Test
  public void testQuitRightAway() {
    assertEquals("test", new StringBuilder("test").toString()); //FOR STYLE CHECKER!
    runHelper("q", "Thank you for using the Image Processor!");
  }

  // Tests that load works as intended when given a valid PPM filepath
  @Test
  public void testLoadValidPPMFilepath() {
    assertEquals("test", new StringBuilder("test").toString()); //FOR STYLE CHECKER!
    runHelper("load test/image.ppm image",
            "Loading completed.\n");
  }

  // Tests that load works as intended when given a valid non-PPM filepath
  @Test
  public void testLoadValidNONPPMFilepath() {
    assertEquals("test", new StringBuilder("test").toString()); //FOR STYLE CHECKER!
    runHelper("load test/image.jpg image",
            "Loading completed.\n");
  }

  // Tests that the output stream displays the correct error message when attempting to load
  // an image model from an invalid filepath
  @Test
  public void testLoadInvalidFilepath() {
    assertEquals("test", new StringBuilder("test").toString()); //FOR STYLE CHECKER!
    runHelper("load SavedPictures/image.ppm image",
            "File not found: SavedPictures/image.ppm.\n");
  }

  // Tests that the output stream displays the correct error message when attempting to load
  // an image model from an invalid file type
  @Test
  public void testLoadInvalidFileType() {
    assertEquals("test", new StringBuilder("test").toString()); //FOR STYLE CHECKER!
    runHelper("load test/image.heic image",
            "Invalid image file type to load from.\n");
  }

  // Tests that the output stream displays the correct error message when attempting to create
  // a red greyscale image from a not-yet-loaded image model
  @Test
  public void testRedGreyscaleBeforeLoad() {
    assertEquals("test", new StringBuilder("test").toString()); //FOR STYLE CHECKER!
    runHelper("red-component image red-image",
            "Please ensure all images are loaded before using them to perform an operation.\n");
  }

  // Tests that a red greyscale image is created when given a valid loaded image model
  @Test
  public void testRedGreyscaleAfterLoad() {
    assertEquals("test", new StringBuilder("test").toString()); //FOR STYLE CHECKER!
    runHelper("load test/image.ppm image " +
                    "red-component image red-image",
            "Loading completed.\n" +
                    "Edit completed.\n");
  }

  // Tests that the output stream displays the correct error message when attempting to create
  // a green greyscale image from a not-yet-loaded image model
  @Test
  public void testGreenGreyscaleBeforeLoad() {
    assertEquals("test", new StringBuilder("test").toString()); //FOR STYLE CHECKER!
    runHelper("green-component image green-image",
            "Please ensure all images are loaded before using them to perform an operation.\n");
  }

  // Tests that a green greyscale image is created when given a valid loaded image model
  @Test
  public void testGreenGreyscaleAfterLoad() {
    assertEquals("test", new StringBuilder("test").toString()); //FOR STYLE CHECKER!
    runHelper("load test/image.ppm image " +
                    "green-component image green-image",
            "Loading completed.\n" +
                    "Edit completed.\n");
  }

  // Tests that the output stream displays the correct error message when attempting to create
  // a blue greyscale image from a not-yet-loaded image model
  @Test
  public void testBlueGreyscaleBeforeLoad() {
    assertEquals("test", new StringBuilder("test").toString()); //FOR STYLE CHECKER!
    runHelper("blue-component image blue-image",
            "Please ensure all images are loaded before using them to perform an operation.\n");
  }

  // Tests that a blue greyscale image is created when given a valid loaded image model
  @Test
  public void testBlueGreyscaleAfterLoad() {
    assertEquals("test", new StringBuilder("test").toString()); //FOR STYLE CHECKER!
    runHelper("load test/image.ppm image " +
                    "blue-component image blue-image",
            "Loading completed.\n" +
                    "Edit completed.\n");
  }

  // Tests that the output stream displays the correct error message when attempting to create
  // a max-value greyscale image from a not-yet-loaded image model
  @Test
  public void testValeGreyscaleBeforeLoad() {
    assertEquals("test", new StringBuilder("test").toString()); //FOR STYLE CHECKER!
    runHelper("maxvalue-component image maxvalue-image",
            "Please ensure all images are loaded before using them to perform an operation.\n");
  }

  // Tests that a max-value greyscale image is created when given a valid loaded image model
  @Test
  public void testValueGreyscaleAfterLoad() {
    assertEquals("test", new StringBuilder("test").toString()); //FOR STYLE CHECKER!
    runHelper("load test/image.ppm image " +
                    "maxvalue-component image maxvalue-image",
            "Loading completed.\n" +
                    "Edit completed.\n");
  }

  // Tests that the output stream displays the correct error message when attempting to create
  // a luma greyscale image from a not-yet-loaded image model
  @Test
  public void testLumaGreyscaleBeforeLoad() {
    assertEquals("test", new StringBuilder("test").toString()); //FOR STYLE CHECKER!
    runHelper("luma-component image luma-image",
            "Please ensure all images are loaded before using them to perform an operation.\n");
  }

  // Tests that a luma greyscale image is created when given a valid loaded image model
  @Test
  public void testLumaGreyscaleAfterLoad() {
    assertEquals("test", new StringBuilder("test").toString()); //FOR STYLE CHECKER!
    runHelper("load test/image.ppm image " +
                    "luma-component image luma-image",
            "Loading completed.\n" +
                    "Edit completed.\n");
  }

  // Tests that the output stream displays the correct error message when attempting to create
  // an intensity greyscale image from a not-yet-loaded image model
  @Test
  public void testIntensityGreyscaleBeforeLoad() {
    assertEquals("test", new StringBuilder("test").toString()); //FOR STYLE CHECKER!
    runHelper("intensity-component image intensity-image",
            "Please ensure all images are loaded before using them to perform an operation.\n");
  }

  // Tests that an intensity greyscale image is created when given a valid loaded image model
  @Test
  public void testIntensityGreyscaleAfterLoad() {
    assertEquals("test", new StringBuilder("test").toString()); //FOR STYLE CHECKER!
    runHelper("load test/image.ppm image " +
                    "intensity-component image intensity-image",
            "Loading completed.\n" +
                    "Edit completed.\n");
  }

  // Tests that the output stream displays the correct error message when attempting to create
  // a sepia tone image from a not-yet-loaded image model
  @Test
  public void testSepiaToneBeforeLoad() {
    assertEquals("test", new StringBuilder("test").toString()); //FOR STYLE CHECKER!
    runHelper("sepia-tone image sepia-image",
            "Please ensure all images are loaded before using them to perform an operation.\n");
  }

  // Tests that a sepia tone image is created when given a valid loaded image model
  @Test
  public void testSepiaToneAfterLoad() {
    assertEquals("test", new StringBuilder("test").toString()); //FOR STYLE CHECKER!
    runHelper("load test/image.ppm image " +
                    "sepia-tone image sepia-image",
            "Loading completed.\n" +
                    "Edit completed.\n");
  }

  // Tests that the output stream displays the correct error message when attempting to create
  // a brightened image from a not-yet-loaded image model
  @Test
  public void testBrightenBeforeLoad() {
    assertEquals("test", new StringBuilder("test").toString()); //FOR STYLE CHECKER!
    runHelper("brighten 10 image bright-image",
            "Please ensure all images are loaded before using them to perform an operation.\n");
  }

  // Tests that a brightened image is created when given a valid loaded image model
  @Test
  public void testBrightenValidConstantAfterLoad() {
    assertEquals("test", new StringBuilder("test").toString()); //FOR STYLE CHECKER!
    runHelper("load test/image.ppm image " +
                    "brighten 10 image bright-image",
            "Loading completed.\n" +
                    "Edit completed.\n");
  }

  // Tests that the output stream displays the correct error message when attempting to create
  // a brightened image from a valid loaded image model with an invalid constant
  @Test
  public void testBrightenInvalidConstantAfterLoad() {
    assertEquals("test", new StringBuilder("test").toString()); //FOR STYLE CHECKER!
    runHelper("load test/image.ppm image " +
                    "brighten ten image bright-image",
            "Loading completed.\n" +
                    "Please ensure that integer parameters are inputted in integer" +
                    " form and within their designated boundaries.\n");
  }

  // Tests that the output stream displays the correct error message when attempting to create
  // a horizontally flipped image from a not-yet-loaded image model
  @Test
  public void testHorizontalFlipBeforeLoad() {
    assertEquals("test", new StringBuilder("test").toString()); //FOR STYLE CHECKER!
    runHelper("horizontal-flip image horizontal-image",
            "Please ensure all images are loaded before using them to perform an operation.\n");
  }

  // Tests that a horizontally flipped image is created when given a valid loaded image model
  @Test
  public void testHorizontalFlipAfterLoad() {
    assertEquals("test", new StringBuilder("test").toString()); //FOR STYLE CHECKER!
    runHelper("load test/image.ppm image " +
                    "horizontal-flip image horizontal-image",
            "Loading completed.\n" +
                    "Edit completed.\n");
  }

  // Tests that the output stream displays the correct error message when attempting to create
  // a vertically flipped image from a not-yet-loaded image
  @Test
  public void testVerticalFlipBeforeLoad() {
    assertEquals("test", new StringBuilder("test").toString()); //FOR STYLE CHECKER!
    runHelper("vertical-flip image vertical-image",
            "Please ensure all images are loaded before using them to perform an operation.\n");
  }

  // Tests that a vertically flipped image is created when given a valid loaded image model
  @Test
  public void testVerticalFlipAfterLoad() {
    assertEquals("test", new StringBuilder("test").toString()); //FOR STYLE CHECKER!
    runHelper("load test/image.ppm image " +
                    "vertical-flip image vertical-image",
            "Loading completed.\n" +
                    "Edit completed.\n");
  }

  // Tests that the output stream displays the correct error message when attempting to create
  // a blurred image from a not-yet-loaded image
  @Test
  public void testBlurBeforeLoad() {
    assertEquals("test", new StringBuilder("test").toString()); //FOR STYLE CHECKER!
    runHelper("blur image blurred-image",
            "Please ensure all images are loaded before using them to perform an operation.\n");
  }

  // Tests that a blurred image is created when given a valid loaded image model
  @Test
  public void testBlurAfterLoad() {
    assertEquals("test", new StringBuilder("test").toString()); //FOR STYLE CHECKER!
    runHelper("load test/image.ppm image " +
                    "blur image blurred-image",
            "Loading completed.\n" +
                    "Edit completed.\n");
  }

  // Tests that the output stream displays the correct error message when attempting to create
  // a sharpened image from a not-yet-loaded image
  @Test
  public void testSharpenBeforeLoad() {
    assertEquals("test", new StringBuilder("test").toString()); //FOR STYLE CHECKER!
    runHelper("sharpen image sharpened-image",
            "Please ensure all images are loaded before using them to perform an operation.\n");
  }

  // Tests that a sharpened image is created when given a valid loaded image model
  @Test
  public void testSharpenAfterLoad() {
    assertEquals("test", new StringBuilder("test").toString()); //FOR STYLE CHECKER!
    runHelper("load test/image.ppm image " +
                    "sharpen image sharpened-image",
            "Loading completed.\n" +
                    "Edit completed.\n");
  }

  // Tests that the output stream displays the correct error message when attempting to save
  // a not-yet-loaded PPM image model to a valid filepath
  @Test
  public void testSaveValidPPMFilepathBeforeLoad() {
    assertEquals("test", new StringBuilder("test").toString()); //FOR STYLE CHECKER!
    runHelper("save SavedImages/new-image.ppm image",
            "Please load image before saving it.\n");
  }

  // Tests that the output stream displays the correct error message when attempting to save
  // a not-yet-loaded non-PPM image model to a valid filepath
  @Test
  public void testSaveValidNonPPMFilepathBeforeLoad() {
    assertEquals("test", new StringBuilder("test").toString()); //FOR STYLE CHECKER!
    runHelper("save SavedImages/new-image.jpg image",
            "Please load image before saving it.\n");
  }

  // Tests that the output stream displays the correct error message when attempting to save
  // a not-yet-loaded PPM image model to an invalid filepath
  @Test
  public void testSaveInvalidPPMFilepathBeforeLoad() {
    assertEquals("test", new StringBuilder("test").toString()); //FOR STYLE CHECKER!
    runHelper("save SavedPictures/new-image.ppm image",
            "Please load image before saving it.\n");
  }

  // Tests that the output stream displays the correct error message when attempting to save
  // a not-yet-loaded non-PPM image model to an invalid filepath
  @Test
  public void testSaveInvalidNonPPMFilepathBeforeLoad() {
    assertEquals("test", new StringBuilder("test").toString()); //FOR STYLE CHECKER!
    runHelper("save SavedPictures/new-image.jpg image",
            "Please load image before saving it.\n");
  }

  // Tests that the output stream displays the correct error message when attempting to save
  // a not-yet-loaded image model to an invalid file type
  @Test
  public void testSaveInvalidFileTypeBeforeLoad() {
    assertEquals("test", new StringBuilder("test").toString()); //FOR STYLE CHECKER!
    runHelper("save SavedPictures/new-image.heic image",
            "Please load image before saving it.\n");
  }

  // Tests that save works when given a valid loaded PPM image model as well as a
  // valid filepath
  @Test
  public void testSaveValidPPMFilepathAfterLoad() {
    assertEquals("test", new StringBuilder("test").toString()); //FOR STYLE CHECKER!
    runHelper("load test/image.ppm image " +
                    "save SavedImages/new-image.ppm image",
            "Loading completed.\n" +
                    "Writing to new file: SavedImages/new-image.ppm.\n" +
                    "Saving completed.\n");
  }

  // Tests that save works when given a valid loaded non-PPM image model as well as a
  // valid filepath
  @Test
  public void testSaveValidNonPPMFilepathAfterLoad() {
    assertEquals("test", new StringBuilder("test").toString()); //FOR STYLE CHECKER!
    runHelper("load test/image.jpg image " +
                    "save SavedImages/new-image.jpg image",
            "Loading completed.\n" +
                    "Writing to new file: SavedImages/new-image.jpg.\n" +
                    "Saving completed.\n");
  }

  // Tests that the output stream displays the correct error message when attempting to save
  // a valid loaded PPM image model to an invalid filepath
  @Test
  public void testSaveInvalidPPMFilepathAfterLoad() {
    assertEquals("test", new StringBuilder("test").toString()); //FOR STYLE CHECKER!
    runHelper("load test/image.ppm image " +
                    "save SavedPictures/new-image.ppm image",
            "Loading completed.\n" +
                    "Filepath cannot be created.\n");
  }

  // Tests that the output stream displays the correct error message when attempting to save
  // a valid loaded non-PPM image model to an invalid filepath
  @Test
  public void testSaveInvalidNonPPMFilepathAfterLoad() {
    assertEquals("test", new StringBuilder("test").toString()); //FOR STYLE CHECKER!
    runHelper("load test/image.jpg image " +
                    "save SavedPictures/new-image.jpg image",
            "Loading completed.\n" +
                    "Filepath cannot be created.\n");
  }

  // Tests that the output stream displays the correct error message when attempting to save
  // a not-yet-loaded image model to an invalid file type
  @Test
  public void testSaveInvalidFileTypeAfterLoad() {
    assertEquals("test", new StringBuilder("test").toString()); //FOR STYLE CHECKER!
    runHelper("load test/image.ppm image " +
                    "save SavedImages/new-image.heic image",
            "Loading completed.\n" +
                    "Invalid image file type to save to.\n");
  }

  // Tests that loading an already loaded image model under the same name overwrites the
  // current key-value pair in the map
  @Test
  public void testOverwriteSameImage() {
    assertEquals("test", new StringBuilder("test").toString()); //FOR STYLE CHECKER!
    runHelper("load test/image.ppm image " +
                    "load test/image.ppm image",
            "Loading completed.\n" +
                    "Overwriting image.\n" +
                    "Loading completed.\n");
  }

  // Tests that the run method catches an IOException
  @Test
  public void testRunIOException() {
    Appendable failOutput = new FailingTestOutput();
    Readable input = new StringReader("q");
    ImageModelMapImpl imageModelMap = new ImageModelMapImpl(new HashMap<>());
    ImageController controller = new ImageControllerImpl(failOutput, imageModelMap);
    try {
      controller.run(input, true);
      fail("Did not throw exception.");
    } catch (IllegalStateException e) {
      assertEquals("Writing to output stream failed.", e.getMessage());
    }
  }

  /**
   * Handles an interaction with an object of an image model using a predetermined
   * input and output.
   *
   * @param input the input for the interaction
   * @param output the output for the interaction
   */
  protected void runHelper(String input, String output) throws IllegalStateException {
    Readable testInput = new StringReader(input);
    Appendable testOutput = new StringBuilder();
    ImageModelMapImpl imageModelMap = new ImageModelMapImpl(new HashMap<>());
    ImageController controller = new ImageControllerImpl(testOutput, imageModelMap);
    controller.run(testInput, true);

    String fullOutput = testOutput.toString();
    String partialOutput = fullOutput.split("(save to-filepath name)")[1];
    String intendedOutput = partialOutput.split("\\r?\\n", 2)[1];

    assertEquals(output, intendedOutput);
  }
}