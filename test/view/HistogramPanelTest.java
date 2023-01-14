package view;

import org.junit.Test;

import java.util.HashMap;

import model.ImageModelMapImpl;

/**
 * This class represents tests for the histogram panel.
 */
public class HistogramPanelTest {

  @Test(expected = NullPointerException.class)
  public void testNullImageModelMap() {
    new HistogramPanel(null, "default", "red");
  }

  @Test(expected = NullPointerException.class)
  public void testNullDefaultImage() {
    new HistogramPanel(new ImageModelMapImpl(new HashMap<>()), null, "green");
  }

  @Test(expected = NullPointerException.class)
  public void testNullGreyscaleComponent() {
    new HistogramPanel(new ImageModelMapImpl(new HashMap<>()), "default", null);
  }

  @Test(expected = NullPointerException.class)
  public void testNullStringMethod() {
    new HistogramPanel(new ImageModelMapImpl(new HashMap<>()),
            "default", "blue").setImageToPaint(null);
  }
}
