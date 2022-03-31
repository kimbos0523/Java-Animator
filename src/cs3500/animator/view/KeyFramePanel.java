package cs3500.animator.view;

import cs3500.animator.controller.AnimationController;
import cs3500.animator.model.Posn2D;
import cs3500.animator.model.ShapeColor;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


/**
 * A KeyFramePanel which represents the buttons that the user can use to manipulate the Animation,
 * mainly to do with adding, removing, and modifying.
 */
public class KeyFramePanel extends JPanel {

  JButton addShapeB;
  JButton deleteShapeB;
  JButton addKeyFrameB;
  JButton deleteKeyFrameB;
  JButton modifyKeyFrameB;
  JButton setShapeLayerB;
  JButton deleteLayerB;

  JFrame addShape;
  JFrame deleteShape;
  JFrame addKeyFrame;
  JFrame deleteKeyFrame;
  JFrame modifyKeyFrame;
  JFrame setShapeLayer;
  JFrame deleteLayer;

  /**
   * A constructor for KeyFramePanel, doesn't take in anything, sets the initial buttons.
   */
  public KeyFramePanel() {
    addShapeB = new JButton("Add Shape");
    deleteShapeB = new JButton("Delete Shape");
    addKeyFrameB = new JButton("Add Keyframe");
    deleteKeyFrameB = new JButton("Delete Keyframe");
    modifyKeyFrameB = new JButton("Modify Keyframe");
    setShapeLayerB = new JButton("Set Layer");
    deleteLayerB = new JButton("Delete Layer");

    JFrame frame = new JFrame();
    Box box = Box.createVerticalBox();
    box.add(addShapeB);
    box.add(deleteShapeB);
    box.add(addKeyFrameB);
    box.add(deleteKeyFrameB);
    box.add(modifyKeyFrameB);
    box.add(setShapeLayerB);
    box.add(deleteLayerB);
    frame.add(box, BorderLayout.EAST);
    frame.setSize(200, 200);
    frame.setUndecorated(true);
    frame.setLocation(0, 400);
    frame.setVisible(true);
  }

  /**
   * Takes in the controller to do the corresponding act according to the button pressed by the
   * user.
   *
   * @param listener The controller that will handle the actions
   */
  public void addClickListener(AnimationController listener) {
    addShapeB.addActionListener(e -> getAddShapeBox(listener));
    deleteShapeB.addActionListener(e -> getDeleteShapeBox(listener));
    addKeyFrameB.addActionListener(e -> getAddKeyFrameBox(listener));
    deleteKeyFrameB.addActionListener(e -> getDeleteKeyFrameBox(listener));
    modifyKeyFrameB.addActionListener(e -> getModifyKeyFrameBox(listener));
    setShapeLayerB.addActionListener(e -> getSetShapeLayerBox(listener));
    deleteLayerB.addActionListener(e -> getDeleteLayerBox(listener));
  }

  /**
   * Gets the panel that allows user to add a shape into the Animation.
   *
   * @param listener the controller that will handle the action
   */
  private void getAddShapeBox(AnimationController listener) {
    addShape = new JFrame("Add Shape");
    JLabel name = new JLabel("Name:");
    JTextField nameField = new JTextField(10);
    name.setLabelFor(nameField);
    JLabel type = new JLabel("Type");
    JTextField typeField = new JTextField(10);
    type.setLabelFor(typeField);
    JTextArea explain = new JTextArea(
            "Given the name, type and layer of shape "
                    + "adds it into the Animation \n without properties."
                    + " If you want the shape to appear in the Animation,\n "
                    + "click 'Modify Keyframe' to add properties");
    explain.setEditable(false);
    JButton done = new JButton("Apply");

    JPanel panel = new JPanel();
    JFrame frame = new JFrame();

    panel.add(name);
    panel.add(nameField);
    panel.add(type);
    panel.add(typeField);
    panel.add(done);
    frame.add(panel, BorderLayout.WEST);
    frame.add(explain, BorderLayout.PAGE_START);
    frame.setLocation(0, 400);
    frame.setSize(450, 130);
    frame.setVisible(true);

    done.addActionListener(e -> {
      try {
        listener.addShape(nameField.getText(), typeField.getText());
      } catch (IllegalArgumentException ex) {
        JFrame error = new JFrame();
        JOptionPane.showMessageDialog(error,
                ex.getMessage(),
                "Illegal Argument",
                JOptionPane.ERROR_MESSAGE);
      }
    });
    done.addActionListener(e -> frame.setVisible(false));
    done.addActionListener(e -> frame.dispose());
  }

  /**
   * Gets the panel that allows user to delete a shape from the Animation.
   *
   * @param listener the controller that will handle the action
   */
  private void getDeleteShapeBox(AnimationController listener) {
    deleteShape = new JFrame("Delete Shape");
    JLabel name = new JLabel("Name:");
    JTextField nameField = new JTextField(20);
    name.setLabelFor(nameField);
    JTextArea explain = new JTextArea(
            "Given the name of the shape, deletes it from the Animation entirely.");
    explain.setEditable(false);
    JButton done = new JButton("Apply");

    JPanel panel = new JPanel();
    JFrame frame = new JFrame();

    panel.add(name);
    panel.add(nameField);
    panel.add(done);
    frame.add(panel, BorderLayout.WEST);
    frame.add(explain, BorderLayout.PAGE_START);
    frame.setLocation(0, 400);
    frame.setSize(430, 80);
    frame.setVisible(true);

    done.addActionListener(e -> {
      try {
        listener.deleteShape(nameField.getText());
      } catch (IllegalArgumentException ex) {
        JFrame error = new JFrame();
        JOptionPane.showMessageDialog(error,
                ex.getMessage(),
                "Illegal Argument",
                JOptionPane.ERROR_MESSAGE);
      }
    });
    done.addActionListener(e -> frame.setVisible(false));
    done.addActionListener(e -> frame.dispose());

  }

  /**
   * Gets the panel that allows user to add a keyframe to the Animation.
   *
   * @param listener the controller that will handle the action
   */
  private void getAddKeyFrameBox(AnimationController listener) {
    addKeyFrame = new JFrame("Add Keyframe");
    JLabel name = new JLabel("Name:");
    JTextField nameField = new JTextField(10);
    name.setLabelFor(nameField);
    JLabel time = new JLabel("Time:");
    JTextField timeField = new JTextField(10);
    time.setLabelFor(timeField);
    JLabel positionX = new JLabel("Position X-coordinate:");
    JLabel positionY = new JLabel("Position Y-coordinate:");
    JTextField positionXField = new JTextField(10);
    JTextField positionYField = new JTextField(10);
    positionX.setLabelFor(positionXField);
    positionY.setLabelFor(positionYField);
    JLabel colorR = new JLabel("Red Shade:");
    JLabel colorG = new JLabel("Green Shade:");
    JLabel colorB = new JLabel("Blue Shade:");
    JTextField colorRField = new JTextField(10);
    JTextField colorGField = new JTextField(10);
    JTextField colorBField = new JTextField(10);
    colorR.setLabelFor(colorRField);
    colorG.setLabelFor(colorGField);
    colorB.setLabelFor(colorBField);
    JLabel height = new JLabel("Height:");
    JTextField heightField = new JTextField(10);
    height.setLabelFor(heightField);
    JLabel width = new JLabel("Width:");
    JTextField widthField = new JTextField(10);
    width.setLabelFor(widthField);
    JLabel degree = new JLabel("Degree:");
    JTextField degreeField = new JTextField(10);
    degree.setLabelFor(degreeField);
    JTextArea explain = new JTextArea(
            "Given the name of a shape that exists in the Animation,\n"
                    + " provide desired time and the wanted properties \n for the shape, "
                    + "can be any time.");
    explain.setEditable(false);
    JButton done = new JButton("Apply");

    JPanel panel = new JPanel();
    JFrame frame = new JFrame();

    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.add(name);
    panel.add(nameField);
    panel.add(time);
    panel.add(timeField);
    panel.add(positionX);
    panel.add(positionXField);
    panel.add(positionY);
    panel.add(positionYField);
    panel.add(colorR);
    panel.add(colorRField);
    panel.add(colorG);
    panel.add(colorGField);
    panel.add(colorB);
    panel.add(colorBField);
    panel.add(height);
    panel.add(heightField);
    panel.add(width);
    panel.add(widthField);
    panel.add(degree);
    panel.add(degreeField);
    panel.add(done);
    frame.add(panel, BorderLayout.CENTER);
    frame.add(explain, BorderLayout.PAGE_START);
    frame.setLocation(0, 400);
    frame.setSize(360, 550);
    frame.setVisible(true);

    done.addActionListener(e -> {
      try {
        listener.addFrame(nameField.getText(), Integer.parseInt(timeField.getText()),
                new Posn2D(Integer.parseInt(positionXField.getText()),
                        Integer.parseInt(positionYField.getText())),
                new ShapeColor(Integer.parseInt(colorRField.getText()),
                        Integer.parseInt(colorGField.getText()),
                        Integer.parseInt(colorBField.getText())),
                Integer.parseInt(heightField.getText()), Integer.parseInt(widthField.getText()),
                Integer.parseInt(degreeField.getText()));
      } catch (IllegalArgumentException ex) {
        JFrame error = new JFrame();
        JOptionPane.showMessageDialog(error,
                ex.getMessage(),
                "Illegal Argument",
                JOptionPane.ERROR_MESSAGE);
      }
    });
    done.addActionListener(e -> frame.setVisible(false));
    done.addActionListener(e -> frame.dispose());
  }


  /**
   * Gets the panel that allows user to delete a keyframe from the Animation.
   *
   * @param listener the controller that will handle the action
   */
  private void getDeleteKeyFrameBox(AnimationController listener) {
    deleteKeyFrame = new JFrame("Add Keyframe");
    JLabel name = new JLabel("Name:");
    JTextField nameField = new JTextField(10);
    name.setLabelFor(nameField);
    JLabel time = new JLabel("Time:");
    JTextField timeField = new JTextField(10);
    time.setLabelFor(timeField);
    JTextArea explain = new JTextArea(
            "Given the name of a shape, removes its key frame at the given time.");
    explain.setEditable(false);
    JButton done = new JButton("Apply");

    JPanel panel = new JPanel();
    JFrame frame = new JFrame();

    panel.add(name);
    panel.add(nameField);
    panel.add(time);
    panel.add(timeField);
    panel.add(done);
    frame.add(panel, BorderLayout.WEST);
    frame.add(explain, BorderLayout.PAGE_START);
    frame.setLocation(0, 400);
    frame.setSize(430, 80);
    frame.setVisible(true);

    done.addActionListener(e -> {
      try {
        listener.deleteFrame(nameField.getText(), Integer.parseInt(timeField.getText()));
      } catch (IllegalArgumentException ex) {
        JFrame error = new JFrame();
        JOptionPane.showMessageDialog(error,
                ex.getMessage(),
                "Illegal Argument",
                JOptionPane.ERROR_MESSAGE);
      }
    });
    done.addActionListener(e -> frame.setVisible(false));
    done.addActionListener(e -> frame.dispose());
  }

  /**
   * Gets the panel that allows user to modify a keyframe in the Animation.
   *
   * @param listener the controller that will handle the action
   */
  private void getModifyKeyFrameBox(AnimationController listener) {
    modifyKeyFrame = new JFrame("Modify Keyframe");
    JLabel name = new JLabel("Name:");
    JTextField nameField = new JTextField(10);
    name.setLabelFor(nameField);
    JLabel time = new JLabel("Time:");
    JTextField timeField = new JTextField(10);
    time.setLabelFor(timeField);
    JLabel positionX = new JLabel("Position X-coordinate:");
    JLabel positionY = new JLabel("Position Y-coordinate:");
    JTextField positionXField = new JTextField(10);
    JTextField positionYField = new JTextField(10);
    positionX.setLabelFor(positionXField);
    positionY.setLabelFor(positionYField);
    JLabel colorR = new JLabel("Red Shade:");
    JLabel colorG = new JLabel("Green Shade:");
    JLabel colorB = new JLabel("Blue Shade:");
    JTextField colorRField = new JTextField(10);
    JTextField colorGField = new JTextField(10);
    JTextField colorBField = new JTextField(10);
    colorR.setLabelFor(colorRField);
    colorG.setLabelFor(colorGField);
    colorB.setLabelFor(colorBField);
    JLabel height = new JLabel("Height:");
    JTextField heightField = new JTextField(10);
    height.setLabelFor(heightField);
    JLabel width = new JLabel("Width:");
    JTextField widthField = new JTextField(10);
    width.setLabelFor(widthField);
    JLabel degree = new JLabel("Degree:");
    JTextField degreeField = new JTextField(10);
    degree.setLabelFor(degreeField);
    JTextArea explain = new JTextArea(
            "Given the name of a shape that exists in the Animation\n"
                    + "and a time that exists in-between existing keyframes,\n"
                    + " modifies its properties .");
    explain.setEditable(false);
    JButton done = new JButton("Apply");

    JPanel panel = new JPanel();
    JFrame frame = new JFrame();

    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.add(name);
    panel.add(nameField);
    panel.add(time);
    panel.add(timeField);
    panel.add(positionX);
    panel.add(positionXField);
    panel.add(positionY);
    panel.add(positionYField);
    panel.add(colorR);
    panel.add(colorRField);
    panel.add(colorG);
    panel.add(colorGField);
    panel.add(colorB);
    panel.add(colorBField);
    panel.add(height);
    panel.add(heightField);
    panel.add(width);
    panel.add(widthField);
    panel.add(degree);
    panel.add(degreeField);
    panel.add(done);
    frame.add(panel, BorderLayout.CENTER);
    frame.add(explain, BorderLayout.PAGE_START);
    frame.setLocation(0, 400);
    frame.setSize(360, 550);
    frame.setVisible(true);

    done.addActionListener(e -> {
      try {
        listener.modifyFrame(nameField.getText(), Integer.parseInt(timeField.getText()),
                new Posn2D(Integer.parseInt(positionXField.getText()),
                        Integer.parseInt(positionYField.getText())),
                new ShapeColor(Integer.parseInt(colorRField.getText()),
                        Integer.parseInt(colorGField.getText()),
                        Integer.parseInt(colorBField.getText())),
                Integer.parseInt(heightField.getText()), Integer.parseInt(widthField.getText()),
                Integer.parseInt(degreeField.getText()));
      } catch (IllegalArgumentException ex) {
        JFrame error = new JFrame();
        JOptionPane.showMessageDialog(error,
                ex.getMessage(),
                "Illegal Argument",
                JOptionPane.ERROR_MESSAGE);
      }
    });
    done.addActionListener(e -> frame.setVisible(false));
    done.addActionListener(e -> frame.dispose());
  }

  /**
   * Gets the panel that allows user to set a shape in specific layer into the Animation.
   *
   * @param listener the controller that will handle the action
   */
  private void getSetShapeLayerBox(AnimationController listener) {
    setShapeLayer = new JFrame("Set Shape to Layer");
    JLabel name = new JLabel("Name:");
    JTextField nameField = new JTextField(10);
    name.setLabelFor(nameField);
    JLabel layer = new JLabel("Layer:");
    JTextField layerField = new JTextField(10);
    layer.setLabelFor(layerField);
    JTextArea explain = new JTextArea(
            "Given the name and layer of shape (default layer is 0),"
                    + "\nadds shape to the specific layer \nIf you want to delete the layer,"
                    + "set the layer as 0");
    explain.setEditable(false);
    JButton done = new JButton("Apply");

    JPanel panel = new JPanel();
    JFrame frame = new JFrame();

    panel.add(name);
    panel.add(nameField);
    panel.add(layer);
    panel.add(layerField);
    panel.add(done);
    frame.add(panel, BorderLayout.WEST);
    frame.add(explain, BorderLayout.PAGE_START);
    frame.setLocation(0, 400);
    frame.setSize(450, 130);
    frame.setVisible(true);

    done.addActionListener(e -> {
      try {
        listener.setLayer(nameField.getText(), Integer.parseInt(layerField.getText()));
      } catch (IllegalArgumentException ex) {
        JFrame error = new JFrame();
        JOptionPane.showMessageDialog(error,
                ex.getMessage(),
                "Illegal Argument",
                JOptionPane.ERROR_MESSAGE);
      }
    });
    done.addActionListener(e -> frame.setVisible(false));
    done.addActionListener(e -> frame.dispose());
  }

  /**
   * Gets the panel that allows user to delete specific layer in the Animation.
   *
   * @param listener the controller that will handle the action
   */
  private void getDeleteLayerBox(AnimationController listener) {
    setShapeLayer = new JFrame("Delete Layer");
    JLabel layer = new JLabel("Layer:");
    JTextField layerField = new JTextField(10);
    layer.setLabelFor(layerField);
    JTextArea explain = new JTextArea("Delete the Shapes in the given layer");
    explain.setEditable(false);
    JButton done = new JButton("Apply");

    JPanel panel = new JPanel();
    JFrame frame = new JFrame();

    panel.add(layer);
    panel.add(layerField);
    panel.add(done);
    frame.add(panel, BorderLayout.WEST);
    frame.add(explain, BorderLayout.PAGE_START);
    frame.setLocation(0, 400);
    frame.setSize(450, 130);
    frame.setVisible(true);

    done.addActionListener(e -> {
      try {
        listener.deleteLayer(Integer.parseInt(layerField.getText()));
      } catch (IllegalArgumentException ex) {
        JFrame error = new JFrame();
        JOptionPane.showMessageDialog(error,
                ex.getMessage(),
                "Illegal Argument",
                JOptionPane.ERROR_MESSAGE);
      }
    });
    done.addActionListener(e -> frame.setVisible(false));
    done.addActionListener(e -> frame.dispose());
  }
}
