package view;

import org.junit.Test;

import java.util.HashMap;

import model.ImageModelMapImpl;

/**
 * This class represents tests for the display panel.
 */
public class DisplayPanelTest {

  @Test(expected = NullPointerException.class)
  public void testNullImageModelMap() {
    new DisplayPanel(null, "hello");
  }

  @Test(expected = NullPointerException.class)
  public void testNullString() {
    new DisplayPanel(new ImageModelMapImpl(new HashMap<>()), null);
  }

  @Test(expected = NullPointerException.class)
  public void testNullStringMethod() {
    new DisplayPanel(new ImageModelMapImpl(new HashMap<>()), "name").setImageToPaint(null);
  }
}
