package commands;

/**
 * This interface represents operations that can be performed on a command object of an
 * image model.
 */
public interface ImageCommand {
  /**
   * Edits an image model and adds a map with one entry containing a name of the new model as
   * the key and a corresponding image model derived from performing an image model method as
   * the value based on the type of command run.
   *
   * @throws IllegalStateException if writing to the output stream fails
   */
  void edit() throws IllegalStateException;
}