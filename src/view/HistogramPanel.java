package view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Objects;

import javax.swing.JPanel;

import model.ImageModel;
import model.ImageModelMap;

/**
 * This class represents an object of a HistogramPanel which displays a line-based histogram
 * for visualizing one component of the current image's pixel values.
 */
public class HistogramPanel extends JPanel implements ImagePanel {
  private final ImageModelMap imageModelMap;
  private String imageToPaint;
  private final String greyscaleType;

  /**
   * Constructs one object of a HistogramPanel which visualizes an image's greyscale component
   * using a given ImageModelMap in which the relevant ImageModel's information can be found,
   * and a String corresponding to a default image that should be painted when the GUI view is
   * first opened.
   *
   * @param imageModelMap the ImageModelMap used to store the added images
   * @param defaultImageToPaint the image painted when the GUI view is first opened
   * @param greyscaleType the greyscale component that the histogram should visualize
   */
  public HistogramPanel(ImageModelMap imageModelMap, String defaultImageToPaint,
                        String greyscaleType) {
    super();
    this.imageModelMap = Objects.requireNonNull(imageModelMap);
    this.imageToPaint = Objects.requireNonNull(defaultImageToPaint);
    this.greyscaleType = Objects.requireNonNull(greyscaleType);
  }

  /**
   * Sets the ImageModel that the ImagePanel should display.
   *
   * @param imageToPaint the name corresponding to the relevant ImageModel
   */
  @Override
  public void setImageToPaint(String imageToPaint) {
    this.imageToPaint = Objects.requireNonNull(imageToPaint);
  }

  /**
   * Renders the histogram to the panel.
   *
   * @param g the graphics object being drawn on
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    drawHistogram(g);
  }

  /**
   * Draws the line-based histogram onto the graphics object to be displayed in the panel.
   *
   * @param g the graphics object being drawn on
   */
  private void drawHistogram(Graphics g) {
    int x = 5;
    int prevX = x;

    g.setColor(Color.BLACK);
    g.drawLine(prevX, this.getHeight() - 5, 261, this.getHeight() - 5);
    g.drawLine(prevX, 5, prevX, this.getHeight() - 5);

    if (this.greyscaleType.equals("rgb")) {
      float maxY = getMaxY(getYCoords("red"));
      float prevY = getYCoords("red").get(0);
      for (int y : getYCoords("red")) {
        g.setColor(Color.RED);
        drawLine(g, prevX, prevY, maxY, x, y);
        prevX = x;
        prevY = y;
        x = x + 1;
      }

      x = 5;
      prevX = x;
      maxY = getMaxY(getYCoords("green"));
      prevY = getYCoords("green").get(0);
      for (int y : getYCoords("green")) {
        g.setColor(Color.GREEN);
        drawLine(g, prevX, prevY, maxY, x, y);
        prevX = x;
        prevY = y;
        x = x + 1;
      }

      x = 5;
      prevX = x;
      maxY = getMaxY(getYCoords("blue"));
      prevY = getYCoords("blue").get(0);
      for (int y : getYCoords("blue")) {
        g.setColor(Color.BLUE);
        drawLine(g, prevX, prevY, maxY, x, y);
        prevX = x;
        prevY = y;
        x = x + 1;
      }

    } else {
      switch (this.greyscaleType) {
        case "red":
          g.setColor(Color.RED);
          break;
        case "green":
          g.setColor(Color.GREEN);
          break;
        case "blue":
          g.setColor(Color.BLUE);
          break;
        case "intensity":
          g.setColor(Color.DARK_GRAY);
          break;
        default:
          break;
      }

      float maxY = getMaxY(getYCoords(this.greyscaleType));
      float prevY = getYCoords(this.greyscaleType).get(0);
      for (int y : getYCoords(this.greyscaleType)) {
        drawLine(g, prevX, prevY, maxY, x, y);
        prevX = x;
        prevY = y;
        x = x + 1;
      }
    }
  }

  /**
   * Draws a line on a graphics object.
   *
   * @param g the graphics object being drawn on
   * @param prevX the starting x-coordinate of the line
   * @param prevY the starting y-coordinate of the line
   * @param maxY the maximum y-coordinate of any possible line
   * @param x the ending x-coordinate of the line
   * @param y the ending y-coordinate of the line
   */
  private void drawLine(Graphics g, int prevX, float prevY, float maxY, int x, int y) {
    g.drawLine(prevX, (int)(this.getHeight() - 5 - (prevY / maxY * (getHeight() - 10))),
            x, (int)(this.getHeight() - 5 - (y / maxY * (getHeight() - 10))));
  }

  /**
   * Gets a list of 256 y-coordinates corresponding to the number of pixels containing a red,
   * green, blue, or intensity value in the image being displayed.
   *
   * @return the list of y-coordinates
   */
  private ArrayList<Integer> getYCoords(String greyscaleType) {
    ImageModel model = this.imageModelMap.find(this.imageToPaint);

    ArrayList<Integer> yCoords = new ArrayList<>();
    for (int i = 0; i < 256; i++) {
      yCoords.add(0);
    }

    for (int i = 0; i < model.getImageHeight(); i++) {
      for (int j = 0; j < model.getImageWidth(); j++) {
        switch (greyscaleType) {
          case "red":
            int currentValue = yCoords.get(model.getPixelAt(i, j).getRed());
            yCoords.set(model.getPixelAt(i, j).getRed(), currentValue + 1);
            break;
          case "green":
            currentValue = yCoords.get(model.getPixelAt(i, j).getGreen());
            yCoords.set(model.getPixelAt(i, j).getGreen(), currentValue + 1);
            break;
          case "blue":
            currentValue = yCoords.get(model.getPixelAt(i, j).getBlue());
            yCoords.set(model.getPixelAt(i, j).getBlue(), currentValue + 1);
            break;
          case "intensity":
            int intensityValue = (model.getPixelAt(i, j).getRed()
                    + model.getPixelAt(i, j).getGreen()
                    + model.getPixelAt(i, j).getBlue()) / 3;
            currentValue = yCoords.get(intensityValue);
            yCoords.set(intensityValue, currentValue + 1);
            break;
          default:
            break;
        }
      }
    }
    return yCoords;
  }

  /**
   * Gets the highest y-value of the y-coordinates used to make the line chart.
   *
   * @param yCoords the y-coordinates used to make the line chart
   * @return the highest y-value among the coordinates
   */
  private int getMaxY(ArrayList<Integer> yCoords) {
    int maxValue = 0;
    for (int yCoord : yCoords) {
      if (yCoord > maxValue) {
        maxValue = yCoord;
      }
    }
    return maxValue;
  }
}
