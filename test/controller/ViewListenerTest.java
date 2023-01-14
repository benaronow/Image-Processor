package controller;

import org.junit.Test;

import java.util.HashMap;

import model.ImageModelMapImpl;

/**
 * This class represents tests for our view listener function located in the controller.
 */
public class ViewListenerTest {

  @Test(expected = NullPointerException.class)
  public void testNullParameters() {
    new ImageControllerImpl(new ImageModelMapImpl(new HashMap<>())).viewActionPerformed(null);
  }

}
