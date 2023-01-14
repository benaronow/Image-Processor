package commands;

import java.util.Scanner;

import model.Filter;
import model.ImageModel;
import model.ImageModelMap;

/**
 * This class represents a Sharpen Filter command that can be performed on an object
 * of an image model.
 */
public class SharpenFilter extends AbstractFilterCommand {
  /**
   * Constructs a Sharpen Filter command using a given output, map of image models, and scanner.
   *
   * @param output        the output that any prompts or error messages will be written to
   * @param imageModelMap the map from which to choose an image to run the command on
   * @param scanner       takes input from the user to perform the command as intended
   * @throws IllegalArgumentException if any given parameters are null
   */
  public SharpenFilter(Appendable output, ImageModelMap imageModelMap,
                    Scanner scanner) throws IllegalArgumentException {
    super(output, imageModelMap, scanner);
  }

  /**
   * Creates a new image model by performing a Sharpen Filter command on a given image model.
   *
   * @param models the first item is the image model on which the command is performed,
   *               and the second, if present, is the image model used to create the mask
   * @return a new image model based on the method performed
   */
  @Override
  protected ImageModel newModel(ImageModel[] models) {
    return filter(models, Filter.SHARPEN);
  }
}