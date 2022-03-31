package cs3500.animator.controller;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.AnimationOperations;
import cs3500.animator.model.AnimationShape;
import cs3500.animator.model.Posn2D;
import cs3500.animator.model.ShapeColor;
import cs3500.animator.view.AnimationSVGView;
import cs3500.animator.view.AnimationTextView;
import cs3500.animator.view.AnimationView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import static cs3500.animator.util.AnimationReader.parseFile;


/**
 * The implementation of the controller, which interacts with the view to manipulate the Animation
 * according the user input.
 */
public class AnimationControllerImpl implements AnimationController {

  private AnimationView view;
  private AnimationOperations model;
  private int speed;
  private boolean isLooped;

  /**
   * A constructor for the AnimationControllerImpl, which takes in a CompositeSwingView and a speed
   * for the animation.
   *
   * @param view  the CompositeSwingView that holds the model
   * @param speed the initial speed of the animation
   * @throws IllegalArgumentException if view or speed is invalid (null or less or equal to zero)
   */
  public AnimationControllerImpl(AnimationView view, int speed) {
    if (view == null) {
      throw new IllegalArgumentException("View cannot be null");
    }
    if (speed <= 0) {
      throw new IllegalArgumentException("Speed cannot be less or equal to zero.");
    }
    this.speed = speed;
    this.view = view;
    setModel();
    isLooped = false;
  }

  @Override
  public void playAnimation() {
    view.doAction(this);
    int timePerTick = 1000 / speed;
    view.setTimer(new Timer(timePerTick, e -> view.refresh()));
  }

  @Override
  public void deleteShape(String name) {
    model.removeShape(name);
  }

  @Override
  public void deleteFrame(String name, int time) {
    model.removeFrame(name, time);
  }

  @Override
  public void addShape(String name, String type) {
    model.declareShape(name, type);
  }

  @Override
  public void addFrame(String name, int time, Posn2D pos, ShapeColor color,
                       int height, int width, int degree) {
    AnimationShape shape = model.makeShape(name, pos, color, height, width, degree);
    model.addFrame(name, time, shape);
  }

  @Override
  public void modifyFrame(String name, int time, Posn2D pos, ShapeColor color,
                          int height, int width, int degree) {
    if (pos == null) {
      throw new IllegalArgumentException("Position cannot be null.");
    }
    if (color == null) {
      throw new IllegalArgumentException("Color cannot be null.");
    }
    AnimationShape shape = model.getShape(time, name);
    shape.setPosition(pos.getX(), pos.getY());
    shape.setColor(color.getR(), color.getG(), color.getB());
    shape.setSize(height, width);
    shape.setDegree(degree);
    model.modifyFrame(name, time, shape);
  }

  @Override
  public void faster() {
    this.speed += 1;
    int timePerTick = 1000 / speed;
    view.getTimer().stop();
    view.getTimer().setDelay(timePerTick);
    view.getTimer().start();
  }

  @Override
  public void slower() {
    this.speed -= 1;
    if (speed <= 0) {
      throw new IllegalArgumentException("Speed can't be less or equal to zero");
    }
    int timePerTick = 1000 / speed;
    view.getTimer().stop();
    view.getTimer().setDelay(timePerTick);
    view.getTimer().start();
  }

  @Override
  public void restart() {
    view.setTick(0);
    view.getTimer().restart();
  }

  @Override
  public void loop() {
    if (isLooped) {
      this.isLooped = false;
    } else {
      this.isLooped = true;
    }
    view.setIsLoop(isLooped);
  }

  @Override
  public void pause() {
    view.getTimer().stop();
  }

  @Override
  public void resume() {
    view.getTimer().start();
  }

  @Override
  public void save(String fileName) {
    try {
      String extension = fileName.substring(fileName.length() - 4);
      AnimationView temp;
      String result = "";
      if (extension.equals(".txt")) {
        temp = new AnimationTextView(view.getModel(), new StringBuilder());
        result = temp.render().toString();
      } else if (extension.equals(".svg")) {
        temp = new AnimationSVGView(view.getModel(), new StringBuilder(), speed);
        result = temp.render().toString();
      }
      FileWriter fileWriter = new FileWriter(fileName);
      fileWriter.write(result);
      fileWriter.flush();
      fileWriter.close();
    } catch (IOException io) {
      JFrame frame = new JFrame();
      JOptionPane.showMessageDialog(frame,
              "Problem in writer.",
              "IOException",
              JOptionPane.ERROR_MESSAGE);
    }
  }

  @Override
  public void load(String fileName) {
    try {
      AnimationModel.Builder builder = new AnimationModel.Builder();
      model = parseFile(new FileReader(fileName), builder);
      view.setModel(model);
    } catch (FileNotFoundException e) {
      JFrame frame = new JFrame();
      JOptionPane.showMessageDialog(frame,
              "File does not exist.",
              "FileNotFoundException",
              JOptionPane.ERROR_MESSAGE);
      throw new IllegalArgumentException("File does not exist");
    }
  }

  @Override
  public void moveAnimation(int time) {
    view.setTick(time);
  }

  /**
   * Sets this model to the model that the view holds in order to manipulate it.
   */
  private void setModel() {
    model = (AnimationOperations) view.getModel();
  }

  @Override
  public void setLayer(String name, int layer) {
    model.setLayer(name, layer);
  }

  @Override
  public void deleteLayer(int layer) {
    model.deleteLayer(layer);
  }
}

