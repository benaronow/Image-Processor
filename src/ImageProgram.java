import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Scanner;

import commands.LoadImage;
import controller.ImageController;
import controller.ImageControllerImpl;
import model.ImageModelMap;
import model.ImageModelMapImpl;
import view.ImageModelView;
import view.ImageModelViewImpl;

/**
 * This class runs the image processor program with optional initial argument inputs.
 */
public class ImageProgram {
  /**
   * Starts the image processor program in a manner based on user input.
   *
   * @param args the input to specify a file that inputs can be read from
   */
  public static void main(String[] args) {
    ImageModelMap imageModelMap = new ImageModelMapImpl(new HashMap<>());
    if (args.length > 0) {
      switch (args[0]) {
        case "-text":
          ImageController controller = new ImageControllerImpl(imageModelMap);
          controller.run(new InputStreamReader(System.in), true);
          break;
        case "-file":
          try {
            FileReader fileReader = new FileReader(args[1]);
            controller = new ImageControllerImpl(imageModelMap);
            controller.run(fileReader, false);
          } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Input must either be empty to use console " +
                    "or contain a valid filepath to read from.");
          } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("A file to read from must be included after " +
                    "the '-file' keyword.");
          }
          break;
        default:
          throw new IllegalArgumentException("Additional arguments must begin with either " +
                  "'-text' or '-file'.");
      }
    } else {
      Scanner scanner = new Scanner(System.in);
      LoadImage imageLoader = new LoadImage(System.out, imageModelMap, scanner);
      imageModelMap.add("koala",
              imageLoader.loadImage("StarterImages/koala.jpg"), System.out);
      imageModelMap.add("jellyfish",
              imageLoader.loadImage("StarterImages/jellyfish.jpg"), System.out);
      imageModelMap.add("penguins",
              imageLoader.loadImage("StarterImages/penguins.jpg"), System.out);

      ImageModelView view = new ImageModelViewImpl(imageModelMap);
      ImageController controller = new ImageControllerImpl(imageModelMap, view);
    }
  }
}