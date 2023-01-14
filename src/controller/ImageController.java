package controller;

/**
 * This interface represents operations that can be performed using an object of an
 * image controller.
 */
public interface ImageController {
  /**
   * Runs the controller using commands from a given input source.
   *
   * @param input the input source from which to read the commands for the controller
   * @param start true if the controller is being run for the first time in a given program
   *              use, false otherwise
   * @throws IllegalStateException if the controller fails to write to the output stream
   */
  void run(Readable input, Boolean start) throws IllegalStateException;
}