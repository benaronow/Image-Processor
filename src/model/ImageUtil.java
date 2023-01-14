package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * This class represents general purpose methods that would need to be performed on an image
 * by different classes.
 */
public class ImageUtil {
  /**
   * Renders pixel data from a given ImageModel to a BufferedImage.
   *
   * @param image the BufferedImage being rendered to
   * @param model the ImageModel being rendered
   * @param width the width of the BufferedImage being rendered to
   * @param height the height of the BufferedImage being rendered to
   */
  static public void renderBufferedImage(BufferedImage image, ImageModel model,
                                         int width, int height) {
    int[] rgbPixelData = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    for (int i = 0, position1D = 0; i < height ; i++) {
      for (int j = 0; j < width; j++, position1D++) {
        int red = model.getPixelAt(i, j).getRed();
        int green = model.getPixelAt(i, j).getGreen();
        int blue = model.getPixelAt(i, j).getBlue();
        Color color = new Color(red, green, blue);
        rgbPixelData[position1D] = color.getRGB();
      }
    }
  }
}
