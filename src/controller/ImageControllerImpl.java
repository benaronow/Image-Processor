package controller;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Function;

import commands.BlueGreyscale;
import commands.BlurFilter;
import commands.Brighten;
import commands.GreenGreyscale;
import commands.HorizontalFlip;
import commands.ImageCommand;
import commands.IntensityGreyscale;
import commands.LoadImage;
import commands.LumaGreyscale;
import commands.Mosaic;
import commands.RedGreyscale;
import commands.SaveImage;
import commands.MaxValueGreyscale;
import commands.SepiaTone;
import commands.SharpenFilter;
import commands.VerticalFlip;
import model.ImageModelMap;
import view.ImageModelView;

/**
 * This class represents an object of an image controller which can take in and perform
 * operations on multiple different objects of image models.
 */
public class ImageControllerImpl implements ImageController, ViewListener {
  protected final Appendable output;
  protected final Map<String, Function<Scanner, ImageCommand>> commandMap;

  /**
   * Constructs a default image controller that writes to System.out as the output.
   */
  public ImageControllerImpl(ImageModelMap imageModelMap) {
    this(System.out, imageModelMap);
  }

  /**
   * Constructs an image controller that controls a GUI view and writes to System.out
   * as the output.
   *
   * @param imageModelMap the ImageModelMap containing added ImageModels
   * @param view the GUI view that the user interacts with
   * @throws IllegalArgumentException if the ImageModelView is null
   */
  public ImageControllerImpl(ImageModelMap imageModelMap, ImageModelView view)
          throws IllegalArgumentException {
    this(imageModelMap);
    if (view == null) {
      throw new IllegalArgumentException("No parameters may contain a null value.");
    }
    view.registerViewListener(this);
  }

  /**
   * Constructs an image controller that does not control a GUI view and writes to a
   * given Appendable as the output.
   *
   * @param output the Appendable that the controller writes messages to
   * @param imageModelMap the ImageModelMap containing added ImageModels
   * @throws IllegalArgumentException if the output Appendable is null
   */
  public ImageControllerImpl(Appendable output, ImageModelMap imageModelMap)
          throws IllegalArgumentException {
    if (output == null || imageModelMap == null) {
      throw new IllegalArgumentException("No parameters may contain a null value.");
    }
    this.output = output;
    this.commandMap = new HashMap<>();
    this.commandMap.putIfAbsent("load",
        s -> (new LoadImage(this.output, imageModelMap, s)));
    this.commandMap.putIfAbsent("red-component",
        s -> (new RedGreyscale(this.output, imageModelMap, s)));
    this.commandMap.putIfAbsent("green-component",
        s -> (new GreenGreyscale(this.output, imageModelMap, s)));
    this.commandMap.putIfAbsent("blue-component",
        s -> (new BlueGreyscale(this.output, imageModelMap, s)));
    this.commandMap.putIfAbsent("maxvalue-component",
        s -> (new MaxValueGreyscale(this.output, imageModelMap, s)));
    this.commandMap.putIfAbsent("luma-component",
        s -> (new LumaGreyscale(this.output, imageModelMap, s)));
    this.commandMap.putIfAbsent("intensity-component",
        s -> (new IntensityGreyscale(this.output, imageModelMap, s)));
    this.commandMap.putIfAbsent("sepia-tone",
        s -> (new SepiaTone(this.output, imageModelMap, s)));
    this.commandMap.putIfAbsent("horizontal-flip",
        s -> (new HorizontalFlip(this.output, imageModelMap, s)));
    this.commandMap.putIfAbsent("vertical-flip",
        s -> (new VerticalFlip(this.output, imageModelMap, s)));
    this.commandMap.putIfAbsent("brighten",
        s -> (new Brighten(this.output, imageModelMap, s)));
    this.commandMap.putIfAbsent("blur",
        s -> (new BlurFilter(this.output, imageModelMap, s)));
    this.commandMap.putIfAbsent("sharpen",
        s -> (new SharpenFilter(this.output, imageModelMap, s)));
    this.commandMap.putIfAbsent("mosaic-effect",
        s -> (new Mosaic(this.output, imageModelMap, s)));
    this.commandMap.putIfAbsent("save",
        s -> (new SaveImage(this.output, imageModelMap, s)));
  }

  /**
   * Runs the controller using commands from a given input source.
   *
   * @param input the input source from which to read the commands for the controller
   * @param start true if the controller is being run for the first time in a given program
   *              use, false otherwise
   * @throws IllegalStateException if writing to the output stream fails
   */
  @Override
  public void run(Readable input, Boolean start) throws IllegalStateException {
    if (input == null || start == null) {
      throw new IllegalArgumentException("No parameters may contain a null value.");
    }
    Scanner scanner = new Scanner(input);
    try {
      if (start) {
        this.output.append("Welcome to the Image Processor! Supported operations include:\n" +
                "1. Image Loading (load from-filepath name)\n" +
                "2. Color Transformations (operation name new-name)\n" +
                "   These operations include red-component, green-component, blue-component,\n" +
                "   maxvalue-component, luma-component, intensity-component, and sepia-tone.\n" +
                "3. Image Flipping (operation name new-name)\n" +
                "   These operations include horizontal-flip and vertical-flip.\n" +
                "4. Image Filtering (operation name new-name)\n" +
                "   These operations include blur-filter and sharpen-filter.\n" +
                "5. Image Saving (save to-filepath name)\n");
      }

      while (scanner.hasNext()) {
        try {
          String stringCommand = scanner.next();

          if (stringCommand.equalsIgnoreCase("q")) {
            this.output.append("Thank you for using the Image Processor!");
            return;
          }

          executeCommand(scanner, stringCommand);
        } catch (IllegalArgumentException e) {
          this.output.append("Please ensure that integer parameters are inputted in integer" +
                  " form and within their designated boundaries.\n");
        }
      }
    } catch (IOException e) {
      throw new IllegalStateException("Writing to output stream failed.");
    }
  }

  /**
   * Executes the command for an interaction with the GUI view that triggered an action event.
   *
   * @param parameters the parameters to be scanned by the command function object when
   *                   performing the operation on an image model
   * @throws IllegalArgumentException if the command has any erroneous parameters
   */
  @Override
  public void viewActionPerformed(String parameters) {
    Readable commandParameters = new StringReader(Objects.requireNonNull(parameters));
    Scanner scanner = new Scanner(commandParameters);

    while (scanner.hasNext()) {
      String stringCommand = scanner.next();
      try {
        executeCommand(scanner, stringCommand);
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException(e.getMessage());
      }
    }
  }

  /**
   * Uses the given string command to pull the relevant command function object from the
   * command map, and uses the given scanner to read other relevant operation parameters.
   *
   * @param scanner the scanner used to read other relevant operation parameters
   * @param stringCommand the string command corresponding to the relevant function object
   * @throws IllegalArgumentException if any command parameters are incorrect
   */
  private void executeCommand(Scanner scanner, String stringCommand) {
    Function<Scanner, ImageCommand> function
            = this.commandMap.getOrDefault(stringCommand.toLowerCase(), null);
    if (function != null) {
      ImageCommand command = function.apply(scanner);
      if (command != null) {
        try {
          command.edit();
        } catch (IllegalArgumentException e) {
          throw new IllegalArgumentException(e.getMessage());
        }
      }
    }
  }
}