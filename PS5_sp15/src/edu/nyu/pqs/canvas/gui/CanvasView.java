package edu.nyu.pqs.canvas.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.nyu.pqs.canvas.api.ICShape.PenShape;
import edu.nyu.pqs.canvas.api.ICanvas;
import edu.nyu.pqs.canvas.api.ICanvasListener;

/**
 * Main GUI for the Canvas Application.
 * 
 * @author cpp270
 *
 */
public class CanvasView implements ICanvasListener{
  private JFrame frame = new JFrame();
  
  private final ColorSelection[] colorChoices = new ColorSelection[] {
      new ColorSelection("Black", "0", Color.BLACK), new ColorSelection("Gray", "1", Color.GRAY),
      new ColorSelection("White", "2", Color.WHITE), new ColorSelection("Red", "3", Color.RED),
      new ColorSelection("Green", "4", Color.GREEN), new ColorSelection("Blue", "5", Color.BLUE),
      new ColorSelection("Yellow", "6", Color.YELLOW), 
      new ColorSelection("Custom", "7", null)};
  private ColorSelection selectedForegroundColorChoice = null;
  private ColorSelection selectedBackgroundColorChoice = null;
  
  private static enum ColorSelectionType {
    FOREGROUND, BACKGROUND; 
    @Override
    public String toString() {
        if (this.ordinal() == FOREGROUND.ordinal()) {
          return "Foreground Color";
        } else {
          return "Background Color";
        }
      }
  };
  
  private ColorSelectionType colorSelectionType = ColorSelectionType.FOREGROUND;
  
  
  private ICanvas canvasModel;
  SurfacePanel paintSurface;
  JSlider penSizeControl;
  JPanel colorPanel;
  JRadioButton roundShapePen;
  JRadioButton squareShapePen;
  
  /*
   * Used to turn live updates from mirror windows on and off. When off surface is painted 
   * when final update is received.
   */
  JRadioButtonMenuItem realTimeMirrorItem;
  
  public CanvasView(ICanvas canvasModel) {
    this.canvasModel = canvasModel;
    this.canvasModel.addListener(this);
    
    // create surface to draw on
    paintSurface = new SurfacePanel(canvasModel);
    
    JPanel selectionPanel = new JPanel(new GridLayout(1, 3));
    selectionPanel.add(createToolPanel(), BorderLayout.EAST);
    selectionPanel.add(colorPanel = createColorPanel(), BorderLayout.WEST);
    selectionPanel.add(createActionPanel(), BorderLayout.WEST);
    
    frame.getContentPane().setLayout(new BorderLayout());
    frame.getContentPane().add(selectionPanel, BorderLayout.NORTH);
    frame.getContentPane().add(paintSurface, BorderLayout.CENTER);
    
    frame.setJMenuBar(createMenuBar());

    // initialize control values
    updatePen(canvasModel.getPenSize(), canvasModel.getPenShape());
    updateColor(canvasModel.getForegroundColor(), canvasModel.getBackgroundColor());
    
    frame.pack();
    frame.setResizable(false);
    frame.setVisible(true);    //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  
  /*
   * If this is a mirror window that does not have focus then real-time paint updates will be
   * ignored based on the user's selection.
   * 
   * (non-Javadoc)
   * @see 
   * edu.nyu.pqs.canvas.api.ICanvasListener#paint(edu.nyu.pqs.canvas.api.ICanvasListener.Painting)
   */
  @Override
  public void paint(Painting action) {
    if (action == Painting.INPROGRESS && !realTimeMirrorItem.isSelected() && !frame.isFocused()) {
      return;
    }    
    
    paintSurface.repaint();
  }

  @Override
  public void updatePen(int penSize, PenShape shape) {
    penSizeControl.setValue(penSize);
    
    roundShapePen.setSelected(shape == PenShape.ROUND);
    squareShapePen.setSelected(shape == PenShape.SQUARE);
  }

  @Override
  public void updateColor(int foreground, int background) {
    ColorSelection cs = getColorSelection(foreground);
    if (cs != null) {
      selectedForegroundColorChoice = cs;
    }

    cs = getColorSelection(background);
    if (cs != null) {
      selectedBackgroundColorChoice = cs;
    }

    if (colorSelectionType == ColorSelectionType.FOREGROUND) {
        selectedForegroundColorChoice.getControl().setSelected(true);
    } else if (colorSelectionType == ColorSelectionType.BACKGROUND) {
        selectedBackgroundColorChoice.getControl().setSelected(true);
    }        
  }
  
  /*
   * Action Listeners
   * 
   */
  
  /*
   * Notifies the model to undo what's has been drawn last.
   */
  private ActionListener undoActionListener = new ActionListener() {
    public void actionPerformed(ActionEvent e) {
      canvasModel.undo();
    }
  };

  /*
   * Displays an About dialog box. 
   */
  private ActionListener aboutActionListener = new ActionListener() {
    public void actionPerformed(ActionEvent e) {
      JLabel title = new JLabel("CanvasApp", JLabel.CENTER);
      title.setFont(new Font("Serif", Font.BOLD, 20));

      JLabel version = new JLabel("Version 0.9", JLabel.CENTER);
      version.setFont(new Font("Serif", Font.PLAIN, 14));

      JLabel copyright = new JLabel("Copyright © 2015 cpp270", JLabel.CENTER);
      copyright.setFont(new Font("Serif", Font.PLAIN, 16));
      
      JDialog about = new JDialog(frame, true);
      
      about.add(title, BorderLayout.NORTH);
      about.add(version, BorderLayout.CENTER);
      about.add(copyright, BorderLayout.SOUTH);

      about.setLocationRelativeTo(frame);
      about.setAlwaysOnTop(true);
      about.pack();
      about.setVisible(true);
    }
  };
  
  /*
   * Closes this GUI and unsubscribes from further notifications by the model.
   */
  private ActionListener closeActionListener = new ActionListener() {
    public void actionPerformed(ActionEvent e) {
      canvasModel.removeListener(CanvasView.this);
      frame.dispose();
    }
  };

  /*
   * Closes all GUI windows.
   */
  private ActionListener closeAllActionListener = new ActionListener() {
    public void actionPerformed(ActionEvent e) {
      System.exit(0);
    }
  };

  /*
   * Notifies the model to clear all its content.
   */
  private ActionListener clearActionListener = new ActionListener() {
    public void actionPerformed(ActionEvent e) {
      canvasModel.clearAll();
    }
  };

  /*
   * Starts a new mirror GUI window.
   */
  private ActionListener newMirrorWindowActionListener = new ActionListener() {
    public void actionPerformed(ActionEvent e) {
      new CanvasView(canvasModel);
    }
  };

  /*
   * Starts a new fresh GUI window.
   */
  private ActionListener newFreshWindowActionListener = new ActionListener() {
    public void actionPerformed(ActionEvent e) {
      new CanvasView(canvasModel.newInstance());
    }
  };

  /*
   * Notifies the model about a change to the background or foreground color.
   */
  private ActionListener colorSelectionActionListener = new ActionListener() {
    public void actionPerformed(ActionEvent e) {
      int index = Integer.parseInt(e.getActionCommand());
      ColorSelection cs = colorChoices[index];
      Color newColor = null;
      if (cs.getTitle().equals("Custom")) {
        newColor = JColorChooser.showDialog(frame, "Choose Color", null);
      } else {
        newColor = cs.getColor();
      }
      
      if (colorSelectionType == ColorSelectionType.FOREGROUND) {
        if (newColor != null) {
          selectedForegroundColorChoice = cs;
          canvasModel.setForegroundColor(newColor.getRGB());
        } else if (selectedForegroundColorChoice != null) {
          // reset to old control
          selectedForegroundColorChoice.getControl().setSelected(true);
        }        
      } else if (colorSelectionType == ColorSelectionType.BACKGROUND) {
        if (newColor != null) {
          selectedBackgroundColorChoice = cs;
          canvasModel.setBackgroundColor(newColor.getRGB());
        } else if (selectedBackgroundColorChoice != null) {
          // reset to old control
          selectedBackgroundColorChoice.getControl().setSelected(true);
        }        
      }      
    }
  };
  
  /*
   * Changes the GUI so the user can modify background or foreground colors using the color panel.
   */
  private ActionListener colorTypeActionListener = new ActionListener() {
    public void actionPerformed(ActionEvent e) {
      if (ColorSelectionType.FOREGROUND.toString().equals(e.getActionCommand())) {
        colorSelectionType = ColorSelectionType.FOREGROUND;
      } else if (ColorSelectionType.BACKGROUND.toString().equals(e.getActionCommand())) {
        colorSelectionType = ColorSelectionType.BACKGROUND;        
      } else {
        throw new IllegalStateException("invalid color selection type");
      }
      
      if (colorSelectionType == ColorSelectionType.FOREGROUND) {
        selectedForegroundColorChoice.getControl().setSelected(true);     
      } else {
        selectedBackgroundColorChoice.getControl().setSelected(true);      
      }
      
      colorPanel.setBorder(new TitledBorder(colorSelectionType.toString()));
    }
  };
  
  /*
   * Notifies the model about a new pen size.
   */
  private ChangeListener penSizeChangeListener = new ChangeListener() {
    public void stateChanged(ChangeEvent e) {
      JSlider source = (JSlider)e.getSource();
      if (!source.getValueIsAdjusting()) {
        canvasModel.setPenSize(source.getValue());
      }
    }
  };

  /*
   * Notifies the model about a new pen shape.
   */
  private ActionListener penShapeActionListener = new ActionListener() {
    public void actionPerformed(ActionEvent e) {
      if (e.getSource().equals(roundShapePen)) {
        canvasModel.setPenShape(PenShape.ROUND);
      } else if (e.getSource().equals(squareShapePen)) {
        canvasModel.setPenShape(PenShape.SQUARE);
      }
    }
  };

  /*
   * Creates all the controls that will be part of the menu bar.
   * 
   * @return menu bar
   */
  private JMenuBar createMenuBar() {
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");

    // new window submenu of file menu
    JMenu newWindows = new JMenu("New Window");
    
    JMenuItem newMirrorWindow = new JMenuItem("Mirror Window");
    newMirrorWindow.addActionListener(newMirrorWindowActionListener);
    newWindows.add(newMirrorWindow);
    
    newWindows.add(new JSeparator());
    
    JMenuItem newFreshWindow = new JMenuItem("Fresh Window");
    newFreshWindow.addActionListener(newFreshWindowActionListener);
    newWindows.add(newFreshWindow);

    fileMenu.add(newWindows);
    fileMenu.add(new JSeparator());
    
    // close all submenu of file menu
    JMenuItem closeItem = new JMenuItem("Close All");
    closeItem.addActionListener(closeAllActionListener);
    fileMenu.add(closeItem);
 
    // Options main menu
    JMenu optionsMenu = new JMenu("Options");
    JRadioButtonMenuItem foregroundColorItem = 
        new JRadioButtonMenuItem(ColorSelectionType.FOREGROUND.toString(), 
        colorSelectionType == ColorSelectionType.FOREGROUND);
    foregroundColorItem.addActionListener(colorTypeActionListener);

    JRadioButtonMenuItem backgroundColorItem = 
        new JRadioButtonMenuItem(ColorSelectionType.BACKGROUND.toString(), 
        colorSelectionType == ColorSelectionType.BACKGROUND);
    backgroundColorItem.addActionListener(colorTypeActionListener);
    
    optionsMenu.add(foregroundColorItem);
    optionsMenu.add(backgroundColorItem);
    
    optionsMenu.add(new JSeparator());
    
    realTimeMirrorItem = new JRadioButtonMenuItem("Real-Time Mirror", true);
    optionsMenu.add(realTimeMirrorItem);
        
    ButtonGroup colorGroup = new ButtonGroup();
    colorGroup.add(foregroundColorItem);
    colorGroup.add(backgroundColorItem);
    
    menuBar.add(fileMenu);
    menuBar.add(optionsMenu);
    
    return menuBar;
  }

  /*
   * Creates all the controls that will be part of the tool panel.
   * 
   * @return tool panel
   */
  private JPanel createToolPanel() {    
    // create the pen size panel    
    JPanel sizePanel = new JPanel();

    JLabel sliderLabel = new JLabel("Pen Size",JLabel.CENTER);

    penSizeControl = new JSlider(JSlider.HORIZONTAL, 
        canvasModel.getMinPenSize(), canvasModel.getMaxPenSize(), canvasModel.getPenSize());
    penSizeControl.setMinorTickSpacing(1);
    penSizeControl.setMajorTickSpacing(10);
    penSizeControl.setPaintTicks(true);
    penSizeControl.setSnapToTicks(true);
    penSizeControl.addChangeListener(penSizeChangeListener);
    
    sizePanel.setLayout(new BorderLayout());
    sizePanel.add(sliderLabel, BorderLayout.NORTH);        
    sizePanel.add(penSizeControl, BorderLayout.SOUTH);
    
    // create the pen shape panel
    JPanel shapePanel = new JPanel(new GridLayout(2, 1));
    ButtonGroup shapeGroup = new ButtonGroup();
    roundShapePen = new JRadioButton(PenShape.ROUND.toString());
    roundShapePen.addActionListener(penShapeActionListener);
    shapePanel.add(roundShapePen);
    shapeGroup.add(roundShapePen);
    
    squareShapePen = new JRadioButton(PenShape.SQUARE.toString());
    squareShapePen.addActionListener(penShapeActionListener);
    shapePanel.add(squareShapePen);
    shapeGroup.add(squareShapePen);

    JPanel toolPanel = new JPanel(new BorderLayout());
    TitledBorder toolTitle = new TitledBorder("Tool");
    
    toolPanel.setBorder(toolTitle);
    toolPanel.add(shapePanel, BorderLayout.WEST);
    toolPanel.add(sizePanel, BorderLayout.CENTER);
       
    return toolPanel;
  }
  
  /*
   * Creates all the controls that will be part of the color panel.
   * 
   * @return color panel
   */
  private JPanel createColorPanel() {
    JPanel colorPanel = new JPanel(new GridLayout(2, 3));
    TitledBorder colorTitle = new TitledBorder(colorSelectionType.toString());
    colorPanel.setBorder(colorTitle);
    
    ButtonGroup colorGroup = new ButtonGroup();
    for (ColorSelection cs : colorChoices) {
      JRadioButton button = new JRadioButton(cs.getTitle());
      button.setActionCommand(cs.getAction());
      button.addActionListener(colorSelectionActionListener);
      colorGroup.add(button);
      colorPanel.add(button);
      cs.setControl(button);
    }
    
    return colorPanel;
  }
  
  /*
   * Creates all the controls that will be part of the action panel.
   * 
   * @return action panel
   */
  private JPanel createActionPanel() {
    JPanel actionPanel = new JPanel(new GridLayout(2, 2));
    TitledBorder actionTitle = new TitledBorder("Action");
    actionPanel.setBorder(actionTitle);

    JButton undoButton = new JButton("Undo");
    undoButton.addActionListener(undoActionListener);
    actionPanel.add(undoButton);

    JButton clearButton = new JButton("Clear");
    clearButton.addActionListener(clearActionListener);
    actionPanel.add(clearButton);    
    
    JButton closeButton = new JButton("Close");
    actionPanel.add(closeButton);
    closeButton.addActionListener(closeActionListener);

    JButton aboutButton = new JButton("About");
    actionPanel.add(aboutButton);
    aboutButton.addActionListener(aboutActionListener);

    return actionPanel;
  }
  
  /*
   * Helper method that returns the color selection based on an RGB color. If the specified color
   * is not found then the custom control is returned. 
   * 
   * @return null if a color selection for the specified color does not exist and there is
   * no custom color selection.
   */
  private ColorSelection getColorSelection(int color) {
    Color c = new Color(color);
    ColorSelection custom = null;
    
    for (ColorSelection cs : colorChoices) {
      if (cs.getColor() != null && cs.getColor().equals(c)) {
        return cs;
      }
      if (cs.getTitle() != null && cs.getTitle().equals("Custom")) {
        custom = cs;
      }
    }    
    
    return custom;
  }
  
  /*
   * Helper class for color selection control that specifies each item in the control.
   * @author cpp270
   *
   */
  private class ColorSelection {
    private Color color;
    private String title;
    private String action;
    private JRadioButton control;
    
    private ColorSelection(String title, String action, Color color) {
      if (title == null || action == null) {
        throw new NullPointerException("title and action must be non-null");
      }
      this.color = color;
      this.title = title;
      this.action = action;
    }
    
    private ColorSelection(String title, Color color) {
      if (title == null) {
        throw new NullPointerException("title must be non-null");
      }
      this.color = color;
      this.title = title;
      this.action = title;
    }
 
    public Color getColor() {
      return color;
    }
    
    @SuppressWarnings("unused")
    public void setColor(Color color) {
      if (color == null) {
        throw new NullPointerException("color must be non-null");
      }
      this.color = color;
    }
    
    public String getTitle() {
      return title;
    }
    
    public String getAction() {
      return action;
    }    
    
    public void setControl(JRadioButton o) {
      if (o == null) {
        throw new NullPointerException("control must be non-null");
      }
      control = o;
    }
    
    public JRadioButton getControl() {
      return control;
    }
  }
}
