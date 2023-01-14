import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Scanner;

import commands.LoadImage;
import controller.ImageController;
import controller.ImageControllerImplExtra;
import model.IPixel;
import model.ImageModel;
import model.ImageModelImpl;
import model.ImageModelMap;
import model.ImageModelMapImpl;
import model.Pixel;
import view.ImageModelView;
import view.ImageModelViewImplExtra;

/**
 * This class runs the image processor program including extra features with optional initial
 * argument inputs.
 */
public class ImageProgramExtra extends ImageProgram {
  /**
   * Starts the image processor program including extra features in a manner based on user input.
   *
   * @param args the input to specify a file that inputs can be read from
   */
  public static void main(String[] args){
    ImageModelMap imageModelMap = new ImageModelMapImpl(new HashMap<>());
    if (args.length > 0) {
      switch (args[0]) {
        case "-text":
          ImageController controller = new ImageControllerImplExtra(imageModelMap);
          controller.run(new InputStreamReader(System.in), true);
          break;
        case "-file":
          try {
            FileReader fileReader = new FileReader(args[1]);
            controller = new ImageControllerImplExtra(imageModelMap);
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

      IPixel[][] leftMaskPixels = new IPixel[768][1024];
      for (int i = 0; i < 768; i++) {
        for (int j = 0; j < 1024; j++) {
          if (j <= 512) {
            leftMaskPixels[i][j] = new Pixel(0, 0, 0);
          } else {
            leftMaskPixels[i][j] = new Pixel(255, 255, 255);
          }
        }
      }
      ImageModel leftMask = new ImageModelImpl(leftMaskPixels, 768, 1024);

      LoadImage imageLoader = new LoadImage(System.out, imageModelMap, scanner);
      imageModelMap.add("koala",
              imageLoader.loadImage("StarterImages/koala.jpg"), System.out);
      imageModelMap.add("jellyfish",
              imageLoader.loadImage("StarterImages/jellyfish.jpg"), System.out);
      imageModelMap.add("penguins",
              imageLoader.loadImage("StarterImages/penguins.jpg"), System.out);
      imageModelMap.add("left_mask", leftMask, System.out);

      ImageModelView view = new ImageModelViewImplExtra(imageModelMap);
      ImageController controller = new ImageControllerImplExtra(imageModelMap, view);
    }
  }
}