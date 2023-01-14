package view;

import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.Objects;

import javax.swing.JPanel;

import model.ImageModel;
import model.ImageModelMap;
import model.ImageUtil;

/**
 * This class represents an object of an DisplayPanel that displays the image currently selected
 * by the user in the GUI view.
 */
public class DisplayPanel extends JPanel implements ImagePanel {
  private final ImageModelMap imageModelMap;
  private String imageToPaint;

  /**
   * Constructs one object of an DisplayPanel using a given ImageModelMap in which the relevant
   * ImageModel's information can be found, and a String corresponding to a default image that
   * should be painted when the GUI view is first opened.
   *
   * @param imageModelMap the ImageModelMap used to store the added images
   * @param defaultImageToPaint the image painted when the GUI view is first opened
   */
  public DisplayPanel(ImageModelMap imageModelMap, String defaultImageToPaint) {
    super();
    this.imageModelMap = Objects.requireNonNull(imageModelMap);
    this.imageToPaint = Objects.requireNonNull(defaultImageToPaint);
    int height = imageModelMap.find(defaultImageToPaint).getImageHeight();
    int width = imageModelMap.find(defaultImageToPaint).getImageWidth();
    this.setPreferredSize(new Dimension(width, height));
  }

  /**
   * Sets the ImageModel that the ImagePanel should display.
   *
   * @param imageToPaint the name corresponding to the relevant ImageModel
   */
  @Override
  public void setImageToPaint(String imageToPaint) {
    this.imageToPaint = Objects.requireNonNull(imageToPaint);
    int height = imageModelMap.find(this.imageToPaint).getImageHeight();
    int width = imageModelMap.find(this.imageToPaint).getImageWidth();
    this.setPreferredSize(new Dimension(width, height));
  }

  /**
   * Renders the ImageModel to the panel.
   *
   * @param g the graphics object being drawn on
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(modelToImage(), 0, 0, null);
  }

  /**
   * Converts an ImageModel to a BufferedImage that can be painted onto the panel.
   *
   * @return the BufferedImage to be painted onto the panel
   */
  private BufferedImage modelToImage() {
    ImageModel modelToPaint = this.imageModelMap.find(imageToPaint);
    int height = modelToPaint.getImageHeight();
    int width = modelToPaint.getImageWidth();
    BufferedImage image = new BufferedImage(width, height, 1);
    ImageUtil.renderBufferedImage(image, modelToPaint, width, height);
    return image;
  }
}
