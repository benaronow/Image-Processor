package view;

import org.junit.Test;

import java.util.HashMap;

import model.ImageModelMapImpl;

/**
 * This class represents tests for {@link ImageModelViewImplExtra}.
 */
public class ImageModelViewImplExtraTest {
  @Test(expected = NullPointerException.class)
  public void testNullImageModelMap() {
    new ImageModelViewImplExtra(null);
  }

  @Test(expected = NullPointerException.class)
  public void testNullViewListener() {
    new ImageModelViewImplExtra(new ImageModelMapImpl(new HashMap<>())).registerViewListener(null);
  }

  @Test(expected = NullPointerException.class)
  public void testNullActionListener() {
    new ImageModelViewImplExtra(new ImageModelMapImpl(new HashMap<>())).actionPerformed(null);
  }
}
