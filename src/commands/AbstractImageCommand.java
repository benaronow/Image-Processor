package commands;

import java.io.IOException;
import java.util.Scanner;

import model.ImageModel;
import model.ImageModelMap;

/**
 * This class represents a generic command that can be performed on an object of an image model.
 */
public abstract class AbstractImageCommand implements ImageCommand {
  protected final Appendable output;
  protected final ImageModelMap imageModelMap;
  protected final Scanner scanner;

  /**
   * Constructs a generic command using a given output, map of image models, and scanner.
   *
   * @param output        the output that any prompts or error messages will be written to
   * @param imageModelMap the map from which to choose an image to run the command on
   * @param scanner       takes input from the user to perform the command as intended
   * @throws IllegalArgumentException if any given parameters are null
   */
  public AbstractImageCommand(Appendable output, ImageModelMap imageModelMap,
                              Scanner scanner) throws IllegalArgumentException {
    if (output == null || imageModelMap == null || scanner == null) {
      throw new IllegalArgumentException("No given parameters may contain null values.");
    }
    this.output = output;
    this.imageModelMap = imageModelMap;
    this.scanner = scanner;
  }

  /**
   * Edits an image model and adds a map with one entry containing a name of the new model as
   * the key and a corresponding image model derived from performing an image model method as
   * the value based on the type of command run.
   *
   * @throws IllegalArgumentException if a new image model cannot be created based on given input
   * @throws IllegalStateException if writing to the output stream fails
   */
  @Override
  public void edit() throws IllegalArgumentException, IllegalStateException {
    try {
      String parameterString = scanner.nextLine().substring(1);
      String[] parameters = parameterString.split(" ");

      try {
        ImageModel model = this.imageModelMap.find(parameters[0]);

        try {
          if (parameters.length == 3) {
            ImageModel maskModel = this.imageModelMap.find(parameters[1]);
            if (maskModel.getImageHeight() != model.getImageHeight()
                    && maskModel.getImageWidth() != model.getImageWidth()) {
              throw new IllegalStateException("Mask image must be the same size as the" +
                      " image operated on.\n");
            }
            String newName = parameters[2];
            this.imageModelMap.add(
                    newName, newModel(new ImageModel[]{model, maskModel}), this.output);
          } else {
            String newName = parameters[1];
            this.imageModelMap.add(newName, newModel(new ImageModel[]{model}), this.output);
          }
          this.output.append("Edit completed.\n");
        } catch (IllegalArgumentException e) {
          throw new IllegalArgumentException(e.getMessage());
        } catch (ArrayIndexOutOfBoundsException ignored) {

        }

      } catch (NullPointerException e) {
        this.output.append("Please ensure all images are loaded before using them to perform" +
                " an operation.\n");
      } catch (IllegalStateException e) {
        this.output.append(e.getMessage());
      }

    } catch (IOException e) {
      throw new IllegalStateException("Writing to output stream failed.");
    }
  }

  /**
   * Creates a new image model by performing an image command on a given image model.
   *
   * @param models the first item is the image model on which the command is performed,
   *               and the second, if present, is the image model used to create the mask
   * @return a new image model based on the method performed
   */
  protected abstract ImageModel newModel(ImageModel[] models);

  /**
   * Checks whether a given pixel should be masked when masking an image.
   *
   * @param maskModel the model being used as a mask
   * @param row       the row of the pixel that is being checked
   * @param col       the column of the pixel that is being checked
   * @return true if the pixel should be masked, false otherwise
   */
  protected boolean fitsMask(ImageModel maskModel, int row, int col) {
    return (maskModel.getPixelAt(row, col).getRed() == 0 &&
            maskModel.getPixelAt(row, col).getGreen() == 0 &&
            maskModel.getPixelAt(row, col).getBlue() == 0);
  }
}