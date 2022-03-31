package cs3500.animator;

import cs3500.animator.controller.AnimationController;
import cs3500.animator.controller.AnimationControllerImpl;
import cs3500.animator.model.ReadOnlyAnimationModel;
import cs3500.animator.view.AnimationSVGView;
import cs3500.animator.view.AnimationSwingView;
import cs3500.animator.view.AnimationTextView;
import cs3500.animator.view.AnimationView;
import cs3500.animator.view.CompositeSwingView;
import java.awt.Frame;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import cs3500.animator.model.AnimationModel;
import static cs3500.animator.util.AnimationReader.parseFile;

/**
 * Represents a class that holds the main method which handles inputs from the user and determines
 * the correct outputs for the Animation.
 */
public final class Excellence {

  private static Frame frame = new Frame();

  /**
   * Handles the user input arguments and determines the correct outputs.
   *
   * @param args the input arguments that give specific instructions to the main and Animation
   */
  public static void main(String[] args) {
    AnimationModel.Builder builder = new AnimationModel.Builder();
    ReadOnlyAnimationModel roModel = findIn(args, builder);
    AnimationView view = findView(args, roModel);
    String fileName = findOut(args, view);
    AnimationController controller;

    if (args.length % 2 == 1) {
      JOptionPane.showMessageDialog(frame,
          "Give the proper input (Input cannot be odd number).",
          "Not enough input",
          JOptionPane.ERROR_MESSAGE);
    }

    if (view instanceof AnimationTextView) {
      if (fileName == null) {
        System.out.println(((AnimationTextView) view).render().toString());
      } else {
        writeFile(args, view);
      }
    } else if (view instanceof AnimationSVGView) {
      if (fileName == null) {
        System.out.println(((AnimationSVGView) view).render().toString());
      } else {
        writeFile(args, view);
      }
    } else if (view instanceof AnimationSwingView) {
      view.makeVisible();
      view.refresh();
    } else if (view instanceof CompositeSwingView) {
      view.makeVisible();
      view.refresh();
      controller = new AnimationControllerImpl((CompositeSwingView) view, findSpeed(args));
      controller.playAnimation();
    }
  }


  /**
   * Write the output file when the view is text view or svg view.
   *
   * @param args the argument of the main method
   * @param view the animation view
   */
  private static void writeFile(String[] args, AnimationView view) {
    String fileName;
    fileName = findOut(args, view);
    try {
      FileWriter fileWriter = new FileWriter(fileName);
      String result = "";
      if (view instanceof AnimationTextView) {
        result = ((AnimationTextView) view).render().toString();
      } else {
        result = ((AnimationSVGView) view).render().toString();
      }
      fileWriter.write(result);
      fileWriter.flush();
      fileWriter.close();
    } catch (IOException e) {
      JOptionPane.showMessageDialog(frame,
          "Problem in writer.",
          "IOException",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * Determines the file that is being passed in and passes it to a builder-model.
   *
   * @param args    the argument that is being passed in
   * @param builder the builder inside an animation model, which will be built up according to the
   *                input
   * @return the read-only animation model after the builder has completely built up a model
   */
  private static ReadOnlyAnimationModel findIn(String[] args, AnimationModel.Builder builder) {
    ReadOnlyAnimationModel model = null;
    for (int i = 0; i < (args.length / 2); i++) {
      if (args[i * 2].equals("-in")) {
        if (model != null) {
          JOptionPane.showMessageDialog(frame,
              "Please give only one -in command.",
              "Multiple same command",
              JOptionPane.ERROR_MESSAGE);
        }
        try {
          model = parseFile(new FileReader(args[i * 2 + 1]), builder);
        } catch (FileNotFoundException e) {
          JOptionPane.showMessageDialog(frame,
              "File does not exist.",
              "FileNotFoundException",
              JOptionPane.ERROR_MESSAGE);
          throw new IllegalArgumentException("File does not exist");
        }
      }
    }
    if (model == null) {
      JOptionPane.showMessageDialog(frame,
          "You should give input for read file.",
          "Non -in command",
          JOptionPane.ERROR_MESSAGE);
    }
    return model;
  }

  /**
   * Determines what the correct view output is.
   *
   * @param args  the argument that is being passed in
   * @param model the read-only model that is to be viewed
   * @return the correct view output according to the arguments
   */
  private static AnimationView findView(String[] args, ReadOnlyAnimationModel model) {
    AnimationView view = null;
    for (int i = 0; i < (args.length / 2); i++) {
      if (args[i * 2].equals("-view")) {
        if (view != null) {
          JOptionPane.showMessageDialog(frame,
              "Please give only one -view command.",
              "Multiple same command",
              JOptionPane.ERROR_MESSAGE);
        }
        switch (args[i * 2 + 1]) {
          case "text":
            view = new AnimationTextView(model, new StringBuilder());
            break;
          case "svg":
            view = new AnimationSVGView(model, new StringBuilder(), findSpeed(args));
            break;
          case "visual":
            view = new AnimationSwingView("Animation", model, findSpeed(args));
            break;
          case "edit":
            view = new CompositeSwingView("Animation", model);
            break;
          default:
            JOptionPane.showMessageDialog(frame,
                "Invalid view type.",
                "Invalid type",
                JOptionPane.ERROR_MESSAGE);
        }
      }
    }
    if (view == null) {
      JOptionPane.showMessageDialog(frame,
          "You should give input for view type.",
          "Non -view command",
          JOptionPane.ERROR_MESSAGE);
    }
    return view;
  }

  /**
   * Determines the correct output file name.
   *
   * @param args the arguments that is passed in
   * @param view the type of animation view
   * @return the name of the output file according to the arguments
   */
  private static String findOut(String[] args, AnimationView view) {
    String fileName = null;
    for (int i = 0; i < (args.length / 2); i++) {
      if (args[i * 2].equals("-out")) {
        String temp = args[i * 2 + 1];
        String extension = temp.substring(temp.length() - 4);
        if (fileName != null) {
          JOptionPane.showMessageDialog(frame,
              "Please give one -out command.",
              "Multiple same command",
              JOptionPane.ERROR_MESSAGE);
        } else if (extension.equals(".svg") && view instanceof AnimationSVGView) {
          fileName = temp;
        } else if (extension.equals(".txt") && view instanceof AnimationTextView) {
          fileName = temp;
        } else {
          JOptionPane.showMessageDialog(frame,
              "Extension name does not match to view",
              "Wrong extension name",
              JOptionPane.ERROR_MESSAGE);
        }
      }
    }
    return fileName;
  }

  /**
   * Determines the speed of the Animation.
   *
   * @param args the arguments that is passed in
   * @return the speed of the Animation according to the Animation
   */
  private static int findSpeed(String[] args) {
    int sp = 1000;
    for (int i = 0; i < (args.length / 2); i++) {
      if (args[i * 2].equals("-speed")) {
        if (sp != 1000) {
          JOptionPane.showMessageDialog(frame,
              "Please give one -speed command.",
              "Multiple same command",
              JOptionPane.ERROR_MESSAGE);
        } else {
          try {
            return sp = Integer.parseInt(args[i * 2 + 1]);
          } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(frame,
                "Speed should be integer.",
                "Invalid format",
                JOptionPane.ERROR_MESSAGE);
          }
        }
      }
    }
    return sp;
  }
}



