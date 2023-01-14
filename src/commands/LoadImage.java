package commands;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import model.ImageModel;
import model.ImageModelImpl;
import model.ImageModelMap;
import model.Pixel;

/**
 * This class represents a Load command that can be performed on an object of an image model.
 */
public class LoadImage extends AbstractImageCommand {
  /**
   * Constructs a Load command using a given output and scanner.
   *
   * @param output        the output that any prompts or error messages will be written to
   * @param imageModelMap the map to add the new image model to
   * @param scanner       takes input from the user to perform the command as intended
   * @throws IllegalArgumentException if any given parameters are null
   */
  public LoadImage(Appendable output, ImageModelMap imageModelMap, Scanner scanner)
          throws IllegalArgumentException {
    super(output, imageModelMap, scanner);
  }

  /**
   * Edits an image model and adds a map with one entry containing a name of the new model as
   * the key and a corresponding image model derived from performing an image model method as
   * the value based on the type of command run.

   * @throws IllegalStateException if writing to the output stream fails
   */
  @Override
  public void edit() throws IllegalStateException {
    try {
      String filepath = scanner.next();
      String newName = scanner.next();

      try {
        this.imageModelMap.add(newName, loadImage(filepath), this.output);
        this.output.append("Loading completed.\n");
      } catch (IllegalArgumentException e) {
        this.output.append(e.getMessage() + "\n");
      } catch (IllegalStateException e) {
        this.output.append("Reading image from file failed.\n");
      }

    } catch (IOException e) {
      throw new IllegalStateException("Writing to output stream failed.");
    }
  }

  /**
   * Creates a new image model by performing a Load command on a given image model.
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
   * Loads an image from a given filepath to an image model.
   *
   * @param filepath the filepath from which the image is loaded from
   * @return the resulting image model
   * @throws IllegalArgumentException if the filepath input is invalid
   * @throws IllegalStateException if reading the image from the file fails
   */
  public ImageModel loadImage(String filepath)
          throws IllegalArgumentException, IllegalStateException {
    if (filepath.endsWith(".ppm")) {
      return loadPPM(filepath);
    } else {
      return loadOther(filepath);
    }
  }

  /**
   * Loads a PPM image from a given filepath to an image model.
   *
   * @param filepath the filepath from which the image is loaded from
   * @return the resulting image model
   * @throws IllegalArgumentException if the filepath input is invalid
   */
  private ImageModel loadPPM(String filepath)
          throws IllegalArgumentException, IllegalStateException {
    Scanner scanner;
    try {
      scanner = new Scanner(new FileInputStream(filepath));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File not found: " + filepath + ".");
    }

    StringBuilder builder = new StringBuilder();
    while (scanner.hasNextLine()) {
      String s = scanner.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    scanner = new Scanner(builder.toString());
    String token;
    token = scanner.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file:" +
              "plain RAW file should begin with P3.");
    }

    int width = scanner.nextInt();
    int height = scanner.nextInt();
    int maxValue = scanner.nextInt();

    Pixel[][] pixels = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int red = scanner.nextInt();
        int green = scanner.nextInt();
        int blue = scanner.nextInt();
        pixels[i][j] = new Pixel(red, green, blue, maxValue);
      }
    }

    return new ImageModelImpl(pixels, height, width);
  }

  /**
   * Loads a non-PPM image from a given filepath to an image model.
   *
   * @param filepath the filepath from which the image is loaded from
   * @return the resulting image model
   * @throws IllegalArgumentException if the filepath input is invalid
   * @throws IllegalStateException if reading the image from the file fails
   */
  private ImageModel loadOther(String filepath)
          throws IllegalArgumentException, IllegalStateException {
    BufferedImage image;
    try {
      image = ImageIO.read(new File(filepath));
    } catch (IOException e) {
      throw new IllegalStateException("Reading image from file failed.");
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Filepath must not contain a null value.");
    }

    int height;
    int width;
    if (image != null) {
      height = image.getHeight();
      width = image.getWidth();
    } else {
      throw new IllegalArgumentException("Invalid image file type to load from.");
    }

    Pixel[][] pixels = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Color color = new Color(image.getRGB(j, i));
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        pixels[i][j] = new Pixel(red, green, blue, 255);
      }
    }

    return new ImageModelImpl(pixels, height, width);
  }
}
