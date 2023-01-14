package controller;

import commands.Downscale;
import commands.Mosaic;
import model.ImageModelMap;
import view.ImageModelView;

/**
 * This class represents an object of an image controller which can take in and perform
 * operations including Mosaic and Downscale on multiple different objects of image models.
 */
public class ImageControllerImplExtra extends ImageControllerImpl {
  /**
   * Constructs a default image controller that writes to System.out as the output.
   */
  public ImageControllerImplExtra(ImageModelMap imageModelMap) {
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
  public ImageControllerImplExtra(ImageModelMap imageModelMap, ImageModelView view)
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
  public ImageControllerImplExtra(Appendable output, ImageModelMap imageModelMap)
          throws IllegalArgumentException {
    super(output, imageModelMap);
    this.commandMap.putIfAbsent("mosaic",
            s -> (new Mosaic(this.output, imageModelMap, s)));
    this.commandMap.putIfAbsent("downscale",
            s -> (new Downscale(this.output, imageModelMap, s)));
  }
}