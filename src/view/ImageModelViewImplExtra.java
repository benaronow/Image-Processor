package view;

import java.awt.Dimension;
import java.awt.Component;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.JComboBox;
import javax.swing.Box;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.ViewListener;
import model.IPixel;
import model.ImageModel;
import model.ImageModelImpl;
import model.ImageModelMap;
import model.Pixel;

/**
 * This class represents an object of a ImageModelViewImplExtra, a GUI view that the user can
 * interact with to use the image manipulator program with extra features.
 */
public class ImageModelViewImplExtra extends ImageModelViewImpl implements ChangeListener {
  private String previewName;
  private final ImagePanel previewDisplay;
  private final JScrollPane previewScrollPane;
  private final List<String> previewParameterList;
  private String displayMaskName;
  private final JComboBox<String> maskBox;

  /**
   * Constructs one object of an ImageModelViewImpl using a given ImageModelMap, which is used
   * to store the images that have been added to the program.
   *
   * @param imageModelMap the ImageModelMap used to store the added images
   */
  public ImageModelViewImplExtra(ImageModelMap imageModelMap) {
    super(imageModelMap);
    this.displayMaskName = "none";
    this.previewParameterList = new ArrayList<>();

    // Buttons that the user can click on to use the program
    JButton mosaic = createButton("Mosaic", "mosaic");
    JButton downscale = createButton("Downscale", "downscale");
    JButton previewImage = createButton("Preview Image", "preview");

    // Creates the panel storing a search box used to choose a mask
    JPanel maskPromptPanel = createViewPanel("flow");
    JLabel maskPrompt = new JLabel("Choose a mask to use:");
    maskPromptPanel.add(maskPrompt);
    JPanel maskBoxPanel = createViewPanel("flow");
    String[] starterMasks = new String[]{"none", "koala", "jellyfish", "penguins", "left_mask"};
    this.maskBox = new JComboBox<>(starterMasks);
    this.maskBox.setPreferredSize(new Dimension(180, 25));
    this.maskBox.addActionListener(this);
    this.maskBox.setActionCommand("maskChosen");
    maskBoxPanel.add(this.maskBox);
    this.searchPanel.add(Box.createVerticalGlue());
    this.searchPanel.add(maskPromptPanel);
    this.searchPanel.add(Box.createVerticalGlue());
    this.searchPanel.add(maskBoxPanel);

    // Adds the new operations to the operations panel
    JButton[] extraOperationsArray = new JButton[]{mosaic, downscale};
    for (JButton button : extraOperationsArray) {
      JPanel buttonPanel = createViewPanel("flow");
      setSizeBehavior(buttonPanel, new int[]{200, 30, 200, 30, 200, 30});
      buttonPanel.add(button);
      this.operationsPanel.add(Box.createVerticalGlue());
      this.operationsPanel.add(buttonPanel);
    }

    // Creates the panel to store the preview button
    JPanel previewButtonPanel = createViewPanel("flow");
    setSizeBehavior(previewButtonPanel, new int[]{200, 30, 200, 30, 200, 30});
    previewButtonPanel.add(previewImage);

    // Creates the panel where the preview window is stored
    JPanel previewLabelPanel = createViewPanel("flow");
    JLabel previewLabel = new JLabel("Image operation preview:");
    setSizeBehavior(previewLabelPanel, new int[]{300, 24, 300, 24, 300, 24});
    previewLabelPanel.add(previewLabel);

    // Sets the default preview image
    IPixel[][] emptyPreviewPixels = new IPixel[200][200];
    for (int i = 0; i < 200; i++) {
      for (int j = 0; j < 200; j++) {
        emptyPreviewPixels[i][j] = new Pixel(255, 255, 255);
      }
    }
    ImageModel emptyPreviewModel = new ImageModelImpl(emptyPreviewPixels, 200, 200);
    Appendable previewDummyOutput = new StringBuilder();
    this.imageModelMap.add("empty", emptyPreviewModel, previewDummyOutput);

    // Sets up the preview display window
    this.previewName = "empty";
    this.previewDisplay = new DisplayPanel(this.imageModelMap, this.previewName);
    this.previewScrollPane = new JScrollPane((Component) this.previewDisplay);
    JViewport viewport = this.previewScrollPane.getViewport();
    viewport.addChangeListener(this);
    JPanel previewPanel = createViewPanel("box-vertical");
    setSizeBehavior(previewPanel, new int[]{200, 200, 200, 200, 200, 200});
    previewPanel.add(this.previewScrollPane);
    JPanel previewFrame = new JPanel(new GridBagLayout());
    previewFrame.setBackground(Color.LIGHT_GRAY);
    setSizeBehavior(previewFrame, new int[]{266, 266, 266, 266, 266, 266});
    previewFrame.add(previewPanel);

    // Combines the three RGB histogram panels
    this.infoPanel.removeAll();
    this.infoPanel.add(Box.createRigidArea(new Dimension(300, 6)));
    this.infoPanel.add(previewButtonPanel);
    this.infoPanel.add(previewLabelPanel);
    this.infoPanel.add(previewFrame);
    ImagePanel rgbHistogramPanel = createHistogramPanel(this.imageModelMap, "rgb");
    ImagePanel intensityHistogramPanel = createHistogramPanel(this.imageModelMap, "intensity");
    JPanel[] labelPanels = new JPanel[]{createHistogramLabelPanel("RGB-component histogram:"),
            createHistogramLabelPanel("Intensity-component histogram:")};
    ImagePanel[] histogramPanels = new ImagePanel[]{rgbHistogramPanel, intensityHistogramPanel};
    for (int i = 0; i < 2; i++) {
      this.infoPanel.add(Box.createRigidArea(new Dimension(300, 12)));
      this.infoPanel.add(labelPanels[i]);
      this.infoPanel.add((Component) histogramPanels[i]);
    }
    this.infoPanel.add(Box.createRigidArea(new Dimension(300, 12)));

    // Resets the GUI view
    this.validate();
    this.repaint();
  }

  /**
   * Handles an action event triggered by a user interacting with the GUI view by either changing
   * or confirming the operation to be performed.
   *
   * @param e the event to be processed
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("preview")) {
      emitPreviewAction();
    } else if (e.getActionCommand().equals("mosaic")) {
      replaceOperation(new String[]{e.getActionCommand()},
              "<html>Enter a number of seeds<br/>" +
                      "and the new image's name.</html>");
    } else if (e.getActionCommand().equals("downscale")) {
      replaceOperation(new String[]{e.getActionCommand()},
              "<html>Enter percents to downscale<br/>" +
                      "by and the new image's name.</html>");
    } else if (e.getActionCommand().equals("maskChosen")) {
      try {
        this.displayMaskName = Objects.requireNonNull(this.maskBox.getSelectedItem()).toString();
      } catch (NullPointerException e2) {
        this.displayMaskName = "none";
      }
    } else {
      super.actionPerformed(e);
    }
    if (!e.getActionCommand().equals("preview")) {
      this.previewName = "empty";
      if (this.displayMaskName.equals("newMask")) {
        this.displayMaskName = "none";
      }
      this.previewParameterList.clear();
      this.previewDisplay.setImageToPaint(this.previewName);
      this.previewScrollPane.repaint();
      this.previewScrollPane.revalidate();
    }
  }

  /**
   * Invoked when the target of the listener has changed its state.
   *
   * @param e a ChangeEvent object
   */
  @Override
  public void stateChanged(ChangeEvent e) {
    if (!this.previewName.equals("empty")) {
      int height = this.imageModelMap.find(this.previewName).getImageHeight();
      int width = this.imageModelMap.find(this.previewName).getImageWidth();
      int row = this.previewScrollPane.getVerticalScrollBar().getValue();
      int col = this.previewScrollPane.getHorizontalScrollBar().getValue();

      createNewMask(height, width, row, col);
      this.displayPreview(row, col);
    }
  }

  /**
   * Handles a Confirm event by passing the parameters to the controller for the operation
   * to be performed if the user has inputted the parameters necessary for such an operation
   * to take place.
   */
  @Override
  protected void emitConfirmAction(){
    try {
      if (this.parameterList.contains("downscale")) {
        updateParameters(this.parameterList, 2);
        executeAndReset();
      } else if (this.parameterList.contains("mosaic")
              || this.parameterList.contains("brighten")) {
        updateParameters(this.parameterList, 1);
        executeAndReset();
      } else if (!this.parameterList.contains("load")) {
        updateParameters(this.parameterList, 0);
        executeAndReset();
      } else {
        super.emitConfirmAction();
      }
    } catch (IllegalArgumentException e) {
      this.userPrompt.setText(e.getMessage());
      this.userInput.setText("");
    } catch (IllegalStateException ignored) {

    }
  }

  /**
   * Handles a Preview event by passing the parameters to the controller for the operation
   * to be performed if the user has inputted the parameters necessary for such an operation
   * to take place.
   */
  private void emitPreviewAction() {
    this.previewName = this.displayModelName;
    int height = this.imageModelMap.find(this.previewName).getImageHeight();
    int width = this.imageModelMap.find(this.previewName).getImageWidth();
    createNewMask(height, width, 0, 0);
    this.displayMaskName = "newMask";

    try {
      if (this.parameterList.contains("downscale")) {
        updateParameters(this.previewParameterList, 2);
        this.previewParameterList.add(0, this.parameterList.get(0));
        displayPreview(0, 0);
      } else if (this.parameterList.contains("mosaic")
              || this.parameterList.contains("brighten")) {
        updateParameters(this.previewParameterList, 1);
        this.previewParameterList.add(0, this.parameterList.get(0));
        displayPreview(0, 0);
      } else if (!this.parameterList.contains("load")) {
        updateParameters(this.previewParameterList, 0);
        this.previewParameterList.add(0, this.parameterList.get(0));
        displayPreview(0, 0);
      }
    } catch (IllegalArgumentException e) {
      this.userPrompt.setText(e.getMessage());
      this.userInput.setText("");
    } catch (IllegalStateException ignored) {

    }
  }

  /**
   * Creates a new mask to use in creating the preview display.
   *
   * @param height the height of the mask image
   * @param width  the width of the mask image
   * @param row    the row to start the black section at
   * @param col    the column to start the black section at
   */
  private void createNewMask(int height, int width, int row, int col) {
    IPixel[][] newMaskPixels = new IPixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (i > row && i < row + 200 && j > col && j < col + 200) {
          newMaskPixels[i][j] = new Pixel(0, 0, 0);
        } else {
          newMaskPixels[i][j] = new Pixel(255, 255, 255);
        }
      }
    }
    ImageModel newMask = new ImageModelImpl(newMaskPixels, height, width);
    Appendable maskDummyOutput = new StringBuilder();
    this.imageModelMap.add("newMask", newMask, maskDummyOutput);
  }

  /**
   * Performs the designated operation to a small portion of the chosen image and displays
   * it in the preview window.
   *
   * @param initialRow the starting row of the image to display
   * @param initialCol the starting column of the image to display
   */
  private void displayPreview(int initialRow, int initialCol) {
    for (ViewListener listener : this.listenerList) {
      StringBuilder parameters = new StringBuilder();
      for (String parameter : this.previewParameterList) {
        parameters.append(parameter + " ");
      }
      try {
        listener.viewActionPerformed(parameters.toString());
        String previewImage = this.previewParameterList.get(this.previewParameterList.size() - 1);
        this.previewDisplay.setImageToPaint(previewImage);
        this.previewScrollPane.repaint();
        this.previewScrollPane.revalidate();
        this.previewScrollPane.getVerticalScrollBar().setValue(initialRow);
        this.previewScrollPane.getHorizontalScrollBar().setValue(initialCol);
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException(e.getMessage());
      }
    }
    this.displayMaskName = "none";
  }

  /**
   * Updates the parameters that are passed to the controller based on the view.
   *
   * @param constants the number of constants included in the user input
   * @throws IllegalArgumentException if no parameters have been entered
   */
  private void updateParameters(List<String> parameterList, int constants) {
    String[] otherParamList = userInput.getText().split("\\s+");
    try {
      if (!this.displayMaskName.equals("none")) {
        if (constants == 2) {
          parameterList.addAll(Arrays.asList(otherParamList[0], otherParamList[1],
                  this.displayModelName, this.displayMaskName, otherParamList[2]));
        } else if (constants == 1) {
          parameterList.addAll(Arrays.asList(otherParamList[0], this.displayModelName,
                  this.displayMaskName, otherParamList[1]));
        } else if (constants == 0) {
          parameterList.addAll(Arrays.asList(this.displayModelName, this.displayMaskName,
                  otherParamList[0]));
        }
      } else {
        if (constants == 2) {
          parameterList.addAll(Arrays.asList(otherParamList[0], otherParamList[1],
                  this.displayModelName, otherParamList[2]));
        } else if (constants == 1) {
          parameterList.addAll(Arrays.asList(otherParamList[0], this.displayModelName,
                  otherParamList[1]));
        } else if (constants == 0){
          parameterList.addAll(Arrays.asList(this.displayModelName, otherParamList[0]));
        }
      }
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new IllegalArgumentException("<html>Sufficient parameters must be<br/>" +
              " entered before confirming.<html>");
    }
  }

  /**
   * Refreshes the GUI view when a different ImageModel is to be displayed.
   *
   * @param modelName the name in the ImageModelMap corresponding to the ImageModel to be
   *                  displayed
   */
  @Override
  protected void refresh(String modelName) {
    this.maskBox.removeAllItems();
    this.maskBox.addItem("none");
    for (String otherName : this.displayModelList) {
      if (this.imageModelMap.find(otherName).getImageHeight()
              == this.imageModelMap.find(modelName).getImageHeight()
              && this.imageModelMap.find(otherName).getImageWidth()
              == this.imageModelMap.find(modelName).getImageWidth()) {
        this.maskBox.addItem(otherName);
      }
    }
    super.refresh(modelName);
  }

  /**
   * Creates an ImagePanel for the image currently being displayed to visualize the components
   * of the pixel's respective red, green, blue, and intensity values.
   *
   * @param imageModelMap the ImageModelMap in which the image being displayed is contained
   * @param greyscaleType the component of the pixel's values that is being visualized
   * @return an ImagePanel displaying the histogram
   */
  @Override
  protected ImagePanel createHistogramPanel(ImageModelMap imageModelMap, String greyscaleType) {
    ImagePanel histogramPanel = new HistogramPanel(imageModelMap,
            this.displayModelName, greyscaleType);
    setSizeBehavior((Component) histogramPanel, new int[]{266, 130, 266, 185, 266, 130});
    return histogramPanel;
  }
}