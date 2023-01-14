package model;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * This class represents a map in which each image model is a value where the key is the name
 * by which the image model is referred to.
 */
public class ImageModelMapImpl implements ImageModelMap {
  private final Map<String, ImageModel> imageModelMap;

  public ImageModelMapImpl(Map<String, ImageModel> imageModelMap) {
    this.imageModelMap = Objects.requireNonNull(imageModelMap);
  }

  /**
   * Finds and returns an image model from the map which corresponds to the given name.
   *
   * @param modelName the name of the model to find
   * @return the model that was found
   */
  @Override
  public ImageModel find(String modelName) {
    return this.imageModelMap.get(modelName);
  }

  /**
   * Adds a new key value pair of an image model and its corresponding name into the map.
   *
   * @param newName  the name of the new image model added to the map
   * @param newModel the new model added to the map
   * @param output   the output to write progress messages to
   */
  @Override
  public void add(String newName, ImageModel newModel, Appendable output) {
    if (this.imageModelMap.containsKey(newName)) {
      try {
        output.append("Overwriting image.\n");
      } catch (IOException e) {
        throw new IllegalStateException("Writing to output stream failed.");
      }
      this.imageModelMap.put(newName, newModel);
    } else {
      this.imageModelMap.put(newName, newModel);
    }
  }
}
