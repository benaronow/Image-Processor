package commands;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.imageio.ImageIO;

import model.ImageModel;
import model.ImageModelMap;
import model.ImageUtil;

/**
 * This class represents a Save command that can be performed on an object of an image model.
 */
public class SaveImage extends AbstractImageCommand {
  /**
   * Constructs a generic command using a given output, map of image models, and scanner.
   *
   * @param output        the output that any prompts or error messages will be written to
   * @param imageModelMap the map from which to choose an image to run the command on
   * @param scanner       takes input from the user to perform the command as intended
   * @throws IllegalArgumentException if any given parameters are null
   */
  public SaveImage(Appendable output, ImageModelMap imageModelMap,
                   Scanner scanner) throws IllegalArgumentException {
    super(output, imageModelMap, scanner);
  }

  /**
   * Edits an image model and adds a map with one entry containing a name of the new model as
   * the key and a corresponding image model derived from performing an image model method as
   * the value based on the type of command run.
   *
   * @throws IllegalStateException if writing to the output stream fails
   */
  @Override
  public void edit() throws IllegalStateException {
    try {
      String newFilepath = this.scanner.next();
      String modelName = this.scanner.next();

      try {
        ImageModel model = this.imageModelMap.find(modelName);
        try {
          saveImage(model, this.output, newFilepath);
          this.output.append("Saving completed.\n");
        } catch (IllegalArgumentException e) {
          output.append(e.getMessage() + "\n");
        }
      } catch (NullPointerException e) {
        this.output.append("Please load " + modelName + " before saving it.\n");
      }

    } catch (IOException e) {
      throw new IllegalStateException("Writing to output stream failed.");
    }
  }

  /**
   * Creates a new image model by performing a Save command on a given image model.
   *
   * @param models the first item is the image model on which the command is performed,
   *               and the second, if present, is the image model used to create the mask
   * @return a new image model based on the method performed
   */
  @Override
  protected ImageModel newModel(ImageModel[] models) {
    return models[0];
  }

  /**
   * Saves an image model as an image file to a given filepath while writing messages to a
   * given output stream.
   *
   * @param model    the image model that is being saved
   * @param output   the output stream that the messages are written to
   * @param filepath the filepath that the image model is saved to
   * @throws IllegalStateException    if writing to the output stream fails
   * @throws IllegalArgumentException if the image model cannot be saved to the given filepath
   */
  public void saveImage(ImageModel model, Appendable output, String filepath)
          throws IllegalStateException, IllegalArgumentException {
    if (output == null || filepath == null) {
      throw new IllegalArgumentException("No parameters may contain null values.");
    }
    if (filepath.endsWith(".ppm")) {
      savePPM(model, output, filepath);
    } else {
      saveOther(model, output, filepath);
    }
  }

  /**
   * Saves an image model as a PPM image file to a given filepath while writing messages to a
   * given output stream.
   *
   * @param model    the image model that is being saved
   * @param output   the output stream that the messages are written to
   * @param filepath the filepath that the image model is saved to
   * @throws IllegalStateException    if writing to the output stream fails
   * @throws IllegalArgumentException if the image model cannot be saved to the given filepath
   */
  private void savePPM(ImageModel model, Appendable output, String filepath)
          throws IllegalStateException, IllegalArgumentException {
    int height = model.getImageHeight();
    int width = model.getImageWidth();

    try {
      PrintWriter file;
      try {
        file = new PrintWriter(filepath);
      } catch (FileNotFoundException e) {
        throw new IllegalArgumentException("Filepath cannot be created.");
      }

      output.append("Writing to new file: " + filepath + ".\n");

      file.println("P3");
      file.println("# PPM Image Output");
      file.println(width + " " + height);
      file.println(255);

      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          file.println(model.getPixelAt(i, j).getRed());
          file.println(model.getPixelAt(i, j).getGreen());
          file.println(model.getPixelAt(i, j).getBlue());
        }
      }
      file.close();
    } catch (IOException e) {
      throw new IllegalStateException("Writing to output stream failed.");
    }
  }

  /**
   * Saves an image model as a non-PPM image file to a given filepath while writing messages to a
   * given output stream.
   *
   * @param model    the image model that is being saved
   * @param output   the output stream that the messages are written to
   * @param filepath the filepath that the image model is saved to
   * @throws IllegalStateException    if writing to the output stream fails
   * @throws IllegalArgumentException if the image model cannot be saved to the given filepath
   */
  private void saveOther(ImageModel model, Appendable output, String filepath)
          throws IllegalStateException, IllegalArgumentException {
    int height = model.getImageHeight();
    int width = model.getImageWidth();

    try {
      new PrintWriter(filepath);
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("Filepath cannot be created.");
    }

    try {
      BufferedImage image = new BufferedImage(width, height, 1);
      ImageUtil.renderBufferedImage(image, model, width, height);

      try {
        boolean newImage = ImageIO.write(image,
                filepath.substring(filepath.length() - 3), new File(filepath));
        if (!newImage) {
          throw new IllegalArgumentException("Invalid image file type to save to.");
        }
        output.append("Writing to new file: " + filepath + ".\n");
      } catch (IOException e) {
        output.append("Writing to new file: " + filepath + ".\n");
        throw new IllegalArgumentException("Writing to new file failed.\n");
      }

    } catch (IOException e) {
      throw new IllegalStateException("Writing to output stream failed.");
    }
  }
}
