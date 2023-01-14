package model;

/**
 * This interface represents operations that can be performed on a map in which each image
 * model is a value where the key is the name by which the image model is referred to.
 */
public interface ImageModelMap {
  /**
   * Finds and returns an image model from the map which corresponds to the given name.
   *
   * @param modelName the name of the model to find
   * @return the model that was found
   */
  ImageModel find(String modelName);

  /**
   * Adds a new key value pair of an image model and its corresponding name into the map.
   *
   * @param newName  the name of the new image model added to the map
   * @param newModel the new model added to the map
   * @param output   the output to write progress messages to
   */
  void add(String newName, ImageModel newModel, Appendable output);
}
