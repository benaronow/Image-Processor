package view;

import org.junit.Test;

import java.util.HashMap;

import model.ImageModelMapImpl;

/**
 * This class represents tests for {@link ImageModelViewImpl}.
 */
public class ImageModelViewImplTest {

  @Test(expected = NullPointerException.class)
  public void testNullImageModelMap() {
    new ImageModelViewImpl(null);
  }

  @Test(expected = NullPointerException.class)
  public void testNullViewListener() {
    new ImageModelViewImpl(new ImageModelMapImpl(new HashMap<>())).registerViewListener(null);
  }

  @Test(expected = NullPointerException.class)
  public void testNullActionListener() {
    new ImageModelViewImpl(new ImageModelMapImpl(new HashMap<>())).actionPerformed(null);
  }
}
