package view;

import java.awt.Dimension;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JFileChooser;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.filechooser.FileSystemView;

import controller.ViewListener;
import model.ImageModelMap;

/**
 * This class represents an object of a ImageModelViewImpl, a GUI view that the user can interact
 * with to use the image manipulator program.
 */
public class ImageModelViewImpl extends JFrame implements ImageModelView, ActionListener {
  protected final ImageModelMap imageModelMap;
  private final ImagePanel imageDisplay;
  private final JScrollPane imageScrollPane;
  protected final JPanel searchPanel;
  protected final JPanel operationsPanel;
  protected final JPanel parametersPanel;
  protected final JPanel editPanel;
  protected final JPanel imagePanel;
  protected final JPanel infoPanel;
  protected final JPanel finalDisplay;
  private final ImagePanel redHistogramPanel;
  private final ImagePanel greenHistogramPanel;
  private final ImagePanel blueHistogramPanel;
  private final ImagePanel intensityHistogramPanel;
  protected String displayModelName;
  protected final List<String> displayModelList;
  protected final List<String> parameterList;
  protected final List<ViewListener> listenerList;
  protected final JComboBox<String> searchBox;
  protected final JLabel userPrompt;
  protected final JTextField userInput;

  /**
   * Constructs one object of an ImageModelViewImpl using a given ImageModelMap, which is used
   * to store the images that have been added to the program.
   *
   * @param imageModelMap the ImageModelMap used to store the added images
   */
  public ImageModelViewImpl(ImageModelMap imageModelMap) {
    super();
    this.imageModelMap = Objects.requireNonNull(imageModelMap);
    this.displayModelName = "koala";
    this.displayModelList = new ArrayList<>(Arrays.asList("koala", "jellyfish", "penguins",
            "left_mask"));
    this.parameterList = new ArrayList<>();
    this.listenerList = new ArrayList<>();

    // Buttons that the user can click on to use the program
    JButton loadImage = createButton("Load Image", "load");
    JButton redGreyscale = createButton("Red Greyscale", "red-component");
    JButton greenGreyscale = createButton("Green Greyscale", "green-component");
    JButton blueGreyscale = createButton("Blue Greyscale", "blue-component");
    JButton maxvalueGreyscale = createButton("Max-Value Greyscale", "maxvalue-component");
    JButton lumaGreyscale = createButton("Luma Greyscale", "luma-component");
    JButton intensityGreyscale = createButton("Intensity Greyscale", "intensity-component");
    JButton sepiaTone = createButton("Sepia Tone", "sepia-tone");
    JButton brighten = createButton("Brighten", "brighten");
    JButton horizontalFlip = createButton("Horizontal Flip", "horizontal-flip");
    JButton verticalFlip = createButton("Vertical Flip", "vertical-flip");
    JButton blurFilter = createButton("Blur Filter", "blur");
    JButton sharpenFilter = createButton("Sharpen Filter", "sharpen");
    JButton saveImage = createButton("Save Image", "save");
    JButton confirmAction = createButton("Confirm", "confirm");

    // Allows the user to choose an image to edit
    this.searchPanel = createViewPanel("box-vertical");
    JPanel searchPromptPanel = createViewPanel("flow");
    JLabel searchPrompt = new JLabel("Choose an image to edit:");
    searchPromptPanel.add(searchPrompt);
    JPanel searchBoxPanel = createViewPanel("flow");
    String[] starterNames = new String[]{"koala", "jellyfish", "penguins", "left_mask"};
    this.searchBox = new JComboBox<>(starterNames);
    this.searchBox.setPreferredSize(new Dimension(180, 25));
    this.searchBox.addActionListener(this);
    this.searchBox.setActionCommand("imageChosen");
    searchBoxPanel.add(searchBox);
    this.searchPanel.add(searchPromptPanel);
    this.searchPanel.add(Box.createVerticalGlue());
    this.searchPanel.add(searchBoxPanel);

    // Allows user to choose an operation to perform on the selected image model
    this.operationsPanel = createViewPanel("box-vertical");
    JButton[] operationsArray = new JButton[]{redGreyscale, greenGreyscale,
            blueGreyscale, maxvalueGreyscale, lumaGreyscale,
            intensityGreyscale, sepiaTone, brighten, horizontalFlip,
            verticalFlip, blurFilter, sharpenFilter};
    for (JButton button : operationsArray) {
      JPanel buttonPanel = createViewPanel("flow");
      setSizeBehavior(buttonPanel, new int[]{200, 30, 200, 30, 200, 30});
      buttonPanel.add(button);
      this.operationsPanel.add(Box.createVerticalGlue());
      this.operationsPanel.add(buttonPanel);
    }

    // Allows the user to enter additional parameters for the operation being performed
    this.parametersPanel = createViewPanel("box-vertical");
    JPanel userPromptPanel = createViewPanel("flow");
    this.userPrompt = new JLabel("<html>Enter relevant operation<br/>parameters below.</html>");
    userPromptPanel.add(this.userPrompt);
    JPanel userInputPanel = createViewPanel("flow");
    this.userInput = new JTextField();
    this.userInput.setPreferredSize(new Dimension(180, 30));
    userInputPanel.add(userInput);
    JPanel confirmActionPanel = createViewPanel("flow");
    confirmActionPanel.add(confirmAction);
    this.parametersPanel.add(userPromptPanel);
    this.parametersPanel.add(Box.createVerticalGlue());
    this.parametersPanel.add(userInputPanel);
    this.parametersPanel.add(Box.createVerticalGlue());
    this.parametersPanel.add(confirmActionPanel);

    // Formats the search, operation, and parameter panels
    this.editPanel = createViewPanel("box-vertical");
    setSizeBehavior(editPanel, new int[]{200, 700, 200, 800, 200, 700});
    this.editPanel.add(searchPanel);
    this.editPanel.add(this.operationsPanel);
    this.editPanel.add(parametersPanel);

    // Allows the user to see the image that they are editing
    this.imageDisplay = new DisplayPanel(this.imageModelMap, this.displayModelName);
    this.imageScrollPane = new JScrollPane((Component) this.imageDisplay);

    // Formats the load and save buttons as well as the image display
    this.imagePanel = createViewPanel("box-vertical");
    setSizeBehavior(this.imagePanel, new int[]{500, 700, 900, 800, 500, 700});
    JPanel loadImagePanel = createViewPanel("grid");
    setSizeBehavior(loadImagePanel, new int[]{500, 50, 900, 50, 500, 50});
    loadImagePanel.add(loadImage);
    JPanel saveImagePanel = createViewPanel("grid");
    setSizeBehavior(saveImagePanel, new int[]{500, 50, 900, 50, 500, 50});
    saveImagePanel.add(saveImage);
    this.imagePanel.add(loadImagePanel);
    this.imagePanel.add(this.imageScrollPane);
    this.imagePanel.add(saveImagePanel);

    this.redHistogramPanel = createHistogramPanel(this.imageModelMap, "red");
    this.greenHistogramPanel = createHistogramPanel(this.imageModelMap, "green");
    this.blueHistogramPanel = createHistogramPanel(this.imageModelMap, "blue");
    this.intensityHistogramPanel = createHistogramPanel(this.imageModelMap, "intensity");

    this.infoPanel = createViewPanel("box-vertical");
    setSizeBehavior(this.infoPanel, new int[]{300, 700, 300, 800, 300, 700});
    JPanel[] labelPanels = new JPanel[]{createHistogramLabelPanel("Red-component histogram:"),
            createHistogramLabelPanel("Green-component histogram:"),
            createHistogramLabelPanel("Blue-component histogram:"),
            createHistogramLabelPanel("Intensity-component histogram:")};
    ImagePanel[] histogramPanels = new ImagePanel[]{redHistogramPanel, greenHistogramPanel,
            blueHistogramPanel, intensityHistogramPanel};
    for (int i = 0; i < 4; i++) {
      this.infoPanel.add(labelPanels[i]);
      this.infoPanel.add((Component) histogramPanels[i]);
    }
    this.infoPanel.add(Box.createRigidArea(new Dimension(300, 12)));

    // Combines all panels into one display that the user can see
    this.finalDisplay = createViewPanel("box-horizontal");
    this.finalDisplay.add(this.editPanel);
    this.finalDisplay.add(this.imagePanel);
    this.finalDisplay.add(this.infoPanel);

    setSizeBehavior(this, new int[]{1000, 700, 1400, 800, 1000, 700});
    this.setTitle("Image Processor");
    this.add(finalDisplay);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.pack();
    this.setVisible(true);
  }

  /**
   * Adds a ViewListener for the GUI view that listens for and responds to emitted action events.
   *
   * @param listener the ViewListener that listens and responds to the GUI view
   */
  @Override
  public void registerViewListener(ViewListener listener) {
    this.listenerList.add(Objects.requireNonNull(listener));
  }

  /**
   * Handles an action event triggered by a user interacting with the GUI view by either changing
   * or confirming the operation to be performed.
   * @param e the event to be processed
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("confirm")) {
      if (this.parameterList.size() > 0) {
        emitConfirmAction();
      }
    } else if (e.getActionCommand().equals("load")) {
      fileChooserInteraction(e, new String[]{},
              "<html>Enter the name to load the<br/>new image to.</html>");
    } else if (e.getActionCommand().equals("save")) {
      fileChooserInteraction(e, new String[]{this.displayModelName}, this.userPrompt.getText());
      emitSaveAction();
    } else if (e.getActionCommand().equals("brighten")) {
      replaceOperation(new String[]{e.getActionCommand()},
              "<html>Enter an integer to brighten by<br/>and the new image's name.</html>");
    } else if (e.getActionCommand().equals("imageChosen")) {
      refresh(Objects.requireNonNull(this.searchBox.getSelectedItem()).toString());
    } else {
      replaceOperation(new String[]{e.getActionCommand()},
              "<html>Enter a name for the new<br/>" + e.getActionCommand() + " image.</html>");
    }
  }

  /**
   * Handles an action event by passing the parameters to the controller for the operation
   * to be performed if the user has inputted the parameters necessary for such an operation
   * to take place.
   */
  protected void emitConfirmAction() {
    try {
      if (userInput.getText().length() == 0) {
        throw new IllegalStateException("No parameters");
      }
      String[] otherParamList = userInput.getText().split("\\s+");
      if (this.parameterList.contains("load")) {
        this.parameterList.add(otherParamList[0]);
      } else if (this.parameterList.contains("brighten")) {
        this.parameterList.addAll(Arrays.asList(otherParamList[0], this.displayModelName,
                otherParamList[1]));
      } else {
        this.parameterList.addAll(Arrays.asList(this.displayModelName, otherParamList[0]));
      }
      executeAndReset();
    } catch (IllegalArgumentException e) {
      this.userPrompt.setText(e.getMessage());
    } catch (IllegalStateException ignored) {

    }
  }

  /**
   * Executes a command specified by the parameters entered by the user and resets the GUI view
   * so that it can take in new user input.
   *
   * @throws IllegalArgumentException if the command has any erroneous parameters
   */
  protected void executeAndReset() {
    for (ViewListener listener : this.listenerList) {
      StringBuilder parameters = new StringBuilder();
      for (String parameter : this.parameterList) {
        parameters.append(parameter + " ");
      }
      try {
        listener.viewActionPerformed(parameters.toString());
        String newImageModel = this.parameterList.get(this.parameterList.size() - 1);
        if (!this.displayModelList.contains(newImageModel)) {
          this.searchBox.addItem(newImageModel);
          this.displayModelList.add(newImageModel);
        }
        refresh(newImageModel);
        this.parameterList.clear();
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException(e.getMessage());
      }
    }
    this.userPrompt.setText("<html>Enter relevant operation<br/>parameters below.</html>");
    this.userInput.setText("");
  }

  /**
   * Refreshes the GUI view when a different ImageModel is to be displayed.
   *
   * @param modelName the name in the ImageModelMap corresponding to the ImageModel to be
   *                  displayed
   */
  protected void refresh(String modelName) {
    this.displayModelName = modelName;
    ImagePanel[] refreshedPanels = new ImagePanel[]{this.imageDisplay, this.redHistogramPanel,
            this.greenHistogramPanel, this.blueHistogramPanel, this.intensityHistogramPanel};
    for (ImagePanel panel : refreshedPanels) {
      panel.setImageToPaint(this.displayModelName);
    }
    this.searchBox.setSelectedItem(this.displayModelName);
    this.repaint();
    this.imageScrollPane.revalidate();
  }

  /**
   * Handles a save action event by passing the parameters to the controller for the save
   * to be performed.
   */
  private void emitSaveAction() {
    for (ViewListener listener : listenerList) {
      StringBuilder parameters = new StringBuilder();
      for (String parameter : this.parameterList) {
        parameters.append(parameter + " ");
      }
      listener.viewActionPerformed(parameters.toString());
    }
    this.userPrompt.setText("<html>Enter relevant operation<br/>parameters below.</html>");
    this.userInput.setText("");
  }

  /**
   * Handles the user's interaction with a JFileChooser when loading and saving an image.
   *
   * @param e the action event that triggered the interaction
   * @param otherParameters the currently known parameters for the operation that is now expected
   * @param newPrompt the prompt to display to the user so that they enter the other necessary
   *                  parameters correctly
   */
  private void fileChooserInteraction(ActionEvent e, String[] otherParameters, String newPrompt) {
    JFileChooser fileChooser =
            new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
    int result;
    if (e.getActionCommand().equals("load")) {
      result = fileChooser.showOpenDialog(null);
    } else {
      result = fileChooser.showSaveDialog(null);
    }
    if (result == JFileChooser.APPROVE_OPTION) {
      String[] parameters =
              new String[]{e.getActionCommand(), fileChooser.getSelectedFile().getAbsolutePath()};
      String[] newParameters =
              Arrays.copyOf(parameters, parameters.length + otherParameters.length);
      System.arraycopy(otherParameters, 0, newParameters,
              parameters.length, otherParameters.length);
      replaceOperation(newParameters, newPrompt);
    }
  }

  /**
   * Changes the current operation expected by the program when a new operation button is pressed.
   *
   * @param newParameters currently known parameters for the operation that is now expected
   * @param newPrompt the prompt to display to the user so that they enter the other necessary
   *                  parameters correctly
   */
  protected void replaceOperation(String[] newParameters, String newPrompt) {
    this.parameterList.clear();
    this.parameterList.addAll(Arrays.asList(newParameters));
    this.userPrompt.setText(newPrompt);
    this.userInput.setText("");
  }

  /**
   * Creates a button that allows the user to perform an operation supported by the image
   * manipulator program.
   *
   * @param buttonName the name of the button, corresponding to its respective operation
   * @param buttonAction the action command emitted by the button to signify the operation
   *                     to be performed
   * @return a button that allows users to perform operations with the image manipulator
   */
  protected JButton createButton(String buttonName, String buttonAction) {
    JButton button = new JButton(buttonName);
    button.setPreferredSize(new Dimension(180, 25));
    button.addActionListener(this);
    button.setActionCommand(buttonAction);
    return button;
  }

  /**
   * Creates a panel with a given layout type to hold components of the GUI view which the user
   * can interact with.
   *
   * @param layoutType the corresponds to a LayoutManager that handle's the panel's layout
   * @return a panel to hold components of the GUI
   */
  protected JPanel createViewPanel(String layoutType) {
    JPanel grayFlowPanel = new JPanel();
    switch (layoutType) {
      case "flow":
        grayFlowPanel.setLayout(new FlowLayout());
        break;
      case "grid":
        grayFlowPanel.setLayout(new GridBagLayout());
        break;
      case "box-vertical":
        grayFlowPanel.setLayout(new BoxLayout(grayFlowPanel, BoxLayout.Y_AXIS));
        break;
      case "box-horizontal":
        grayFlowPanel.setLayout(new BoxLayout(grayFlowPanel, BoxLayout.X_AXIS));
        break;
      default:
        throw new IllegalStateException("Unknown layout type");
    }
    grayFlowPanel.setBackground(Color.GRAY);
    return grayFlowPanel;
  }

  /**
   * Creates a panel to hold a descriptive label for each histogram visualizing the pixel's
   * values.
   *
   * @param labelText the text contained in the label being held
   * @return a panel which holds the descriptive label
   */
  protected JPanel createHistogramLabelPanel(String labelText) {
    JPanel histogramLabelPanel = new JPanel(new GridBagLayout());
    histogramLabelPanel.setBackground(Color.GRAY);
    setSizeBehavior(histogramLabelPanel, new int[]{300, 24, 300, 24, 300, 24});
    JLabel histogramLabel = new JLabel(labelText);
    histogramLabelPanel.add(histogramLabel);
    return histogramLabelPanel;
  }

  /**
   * Creates an ImagePanel for the image currently being displayed to visualize the components
   * of the pixel's respective red, green, blue, and intensity values.
   *
   * @param imageModelMap the ImageModelMap in which the image being displayed is contained
   * @param greyscaleType the component of the pixel's values that is being visualized
   * @return an ImagePanel displaying the histogram
   */
  protected ImagePanel createHistogramPanel(ImageModelMap imageModelMap, String greyscaleType) {
    ImagePanel histogramPanel = new HistogramPanel(imageModelMap,
            this.displayModelName, greyscaleType);
    setSizeBehavior((Component) histogramPanel, new int[]{266, 136, 266, 165, 266, 136});
    return histogramPanel;
  }

  /**
   * Sets the preferred, maximum, and minimum size for a component using given parameters.
   *
   * @param component the component for which the size constraints are being set
   * @param sizeParameters the parameters to determine the constraints
   */
  protected void setSizeBehavior(Component component, int[] sizeParameters) {
    component.setPreferredSize(new Dimension(sizeParameters[0], sizeParameters[1]));
    component.setMaximumSize(new Dimension(sizeParameters[2], sizeParameters[3]));
    component.setMinimumSize(new Dimension(sizeParameters[4], sizeParameters[5]));
  }
}