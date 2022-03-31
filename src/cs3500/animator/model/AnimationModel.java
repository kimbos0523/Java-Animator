package cs3500.animator.model;

import cs3500.animator.util.AnimationBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * A class that represents the model and implementations of an Animation, which will maintain the
 * state of the Animation.
 */
public final class AnimationModel implements AnimationOperations {

  private HashMap<String, TreeMap<Integer, AnimationShape>> timeline;
  private LinkedHashMap<String, String> existingShape;
  private TreeMap<Integer, List<String>> layers;
  private AnimationBackground background;

  /**
   * A constructor for an AnimationModel for an Animation that has no shapes to begin with. The
   * String represents the name of the AnimationShape, the Integer represents the time, or tick, in
   * the Animation. There is a map of the timeline, as well as a map of all shapes existing in the
   * Animation. There is also a background/canvase for the Animation.
   */
  public AnimationModel() {
    timeline = new HashMap<>();
    existingShape = new LinkedHashMap<>();
    background = new AnimationBackground(0, 0, 500, 500);
    layers = new TreeMap<>();
  }

  /**
   * A constructor for an AnimationModel for an Animation that has a list of shapes to begin with.
   *
   * @param shapes the shapes that the Animation will start off with
   */
  public AnimationModel(LinkedHashMap<String, String> shapes) {
    if (shapes == null) {
      throw new IllegalArgumentException("Cannot be null.");
    }
    if (shapes.containsKey(null)) {
      throw new IllegalArgumentException("Name cannot be null.");
    }
    if (shapes.containsValue(null)) {
      throw new IllegalArgumentException("Shape type cannot be null.");
    }
    if (shapes.containsKey("")) {
      throw new IllegalArgumentException("Name cannot be empty string");
    }
    if (shapes.containsValue("")) {
      throw new IllegalArgumentException("Shape type cannot be empty string");
    }

    timeline = new HashMap<>();
    existingShape = shapes;
    background = new AnimationBackground(0, 0, 500, 500);
    layers = new TreeMap<>();
  }


  /**
   * A Builder which constructs an AnimationModel, builds a model up step by step.
   */
  public static final class Builder implements AnimationBuilder<AnimationOperations> {

    private AnimationOperations model = new AnimationModel();

    @Override
    public AnimationOperations build() {
      return model;
    }

    @Override
    public AnimationBuilder<AnimationOperations> setBounds(int x, int y, int width, int height) {
      model.setBackground(x, y, width, height);
      return this;
    }

    @Override
    public AnimationBuilder<AnimationOperations> declareShape(String name, String type) {
      LinkedHashMap<String, String> shapes = model.getExistingShape();
      shapes.put(name, type);
      model.setExistingShape(shapes);
      model.setLayer(name, 0);
      return this;
    }

    @Override
    public AnimationBuilder<AnimationOperations> addMotion(String name, int t1, int x1, int y1,
                                                           int w1, int h1, int r1, int g1, int b1,
                                                           int t2, int x2, int y2, int w2, int h2,
                                                           int r2, int g2, int b2, int d1, int d2) {
      if (model.getExistingShape().containsKey(name) && !model.getTimeline().containsKey(name)) {
        AnimationShape shape = findShape(name, x1, y1, w1, h1, r1, g1, b1, d1);
        model.addShape(shape, name, t1);
      }
      if (x2 != x1 || y2 != y1) {
        model.move(name, t1, t2, x2, y2);
      }
      if (w2 != w1 || h2 != h1) {
        model.changeSize(name, t1, h2, w2, t2);
      }
      if (r2 != r1 || g2 != g1 || b2 != b1) {
        model.changeColor(name, t1, new ShapeColor(r2, g2, b2), t2);
      }
      if (d2 != d1) {
        model.changeDegree(name, t1, d2, t2);
      }
      if (x2 == x1 && y2 == y1 && w2 == w1 && h2 == h1 && r2 == r1 && g2 == g1
              && b2 == b1 && t1 != t2 && d1 == d2) {
        AnimationShape shape = findShape(name, x1, y1, w1, h1, r1, g1, b1, d1);
        model.addFrame(name, t2, shape);
      }
      return this;
    }

    @Override
    public AnimationBuilder<AnimationOperations> addKeyframe(String name, int t, int x, int y,
                                                             int w, int h, int r, int g, int b,
                                                             int d) {
      AnimationShape shape = findShape(name, x, y, w, h, r, g, b, d);

      model.addFrame(name, t, shape);
      return this;
    }

    @Override
    public AnimationBuilder<AnimationOperations> setLayer(String name, int layer) {
      model.setLayer(name, layer);
      return this;
    }

    /**
     * Gets the correct shape in the existing shapes of the Animation, given the name of the shape
     * and its elements.
     *
     * @param name the name of the shape
     * @param x    the x-position of the shape
     * @param y    the y-position of the shape
     * @param w    the width of the shape
     * @param h    the height of the shape
     * @param r    the red shade of the color of the shape
     * @param g    the green shade of the color of the shape
     * @param b    the blue shade of the color of the shape
     * @return returns the correct AnimationShape
     */
    private AnimationShape findShape(String name, int x, int y,
                                     int w, int h, int r, int g, int b, int d) {
      AnimationShape shape;
      HashMap<String, String> shapes = model.getExistingShape();
      switch (shapes.get(name)) {
        case "rectangle":
          shape = new RectangleShape(new Posn2D(x, y), new ShapeColor(r, g, b), h, w, d);
          break;
        case "ellipse":
          shape = new EllipseShape(new Posn2D(x, y), new ShapeColor(r, g, b), h, w, d);
          break;
        default:
          throw new IllegalArgumentException("Shape doesn't exist");
      }
      return shape;
    }
  }


  @Override
  public void declareShape(String name, String type) {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null.");
    }
    if (name.equals("")) {
      throw new IllegalArgumentException("Name cannot be empty string.");
    }
    if (existingShape.containsKey(name)) {
      throw new IllegalArgumentException("Name already exist");
    }
    if (type == null) {
      throw new IllegalArgumentException("Shape type cannot be null");
    }
    if (!correctType(type)) {
      throw new IllegalArgumentException("Invalid type");
    }
    this.existingShape.put(name, type);
    this.setLayer(name, 0);
  }

  /**
   * Checks whether a given string is one of the types of shapes.
   *
   * @param type the string representing a shape
   * @return true if the string is one of the shape types
   */
  private boolean correctType(String type) {
    return type.equals("rectangle") || type.equals("ellipse");
  }


  @Override
  public void addShape(AnimationShape newShape, String name, int time) {
    invalidName(name);
    if (newShape == null) {
      throw new IllegalArgumentException("Shape cannot be null.");
    }
    if (time < 0) {
      throw new IllegalArgumentException("Time cannot be negative.");
    }
    if (timeline.containsKey(name)) {
      throw new IllegalArgumentException("The name already exist.");
    }

    existingShape.put(name, newShape.getType());
    TreeMap<Integer, AnimationShape> operation = new TreeMap<>();
    operation.put(time, newShape);
    timeline.put(name, operation);
  }

  @Override
  public void removeShape(String name) {
    invalidName(name);
    if (!existingShape.containsKey(name)) {
      throw new IllegalArgumentException("Can't find the given name in timeline.");
    }

    existingShape.remove(name);
    timeline.remove(name);
  }

  @Override
  public String getAnimation() {
    if (existingShape.size() == 0) {
      return "";
    }

    String result = "";
    result += ("canvas " + this.background.toString() + "\n");
    for (String name : existingShape.keySet()) {
      result += ("shape " + name + " " + existingShape.get(name) + "\n");

      if (!timeline.containsKey(name)) {
        continue;
      }

      Set<Integer> timeSet = timeline.get(name).keySet();
      List<Integer> time = new ArrayList<>(timeSet);
      Collections.sort(time);
      for (int i = 0; i < time.size() - 1; i++) {
        result += ("motion " + name + " " + time.get(i) + " "
                + getShape(time.get(i), name).toString() + " " + time.get(i + 1) + " "
                + getShape(time.get(i + 1), name).toString()
                + " " + getShape(time.get(i), name).getDegree()
                + " " + getShape(time.get(i + 1), name).getDegree() + "\n");
      }
    }
    result = result.substring(0, result.length() - 1);
    return result;
  }

  @Override
  public AnimationShape getShape(int time, String name) {
    invalidName(name);
    if (time < 0) {
      throw new IllegalArgumentException("Time cannot be negative.");
    }
    if (!timeline.containsKey(name)) {
      throw new IllegalArgumentException("Given name does not exist at the given time.");
    }
    if (timeline.get(name).size() == 0) {
      throw new IllegalArgumentException("Name exists but has "
              + "no operation so this cannot be occur.");
    }

    if (timeline.get(name).containsKey(time)) {
      return timeline.get(name).get(time).showShape(0);
    } else {
      if (timeline.get(name).lowerKey(time) == null) {
        throw new IllegalArgumentException("The given name shape does not exist at the given time");
      }
      int recentTime = timeline.get(name).lowerKey(time);
      return timeline.get(name).get(recentTime).showShape(time - recentTime);
    }
  }

  @Override
  public List<AnimationShape> getShape(int time) {
    if (time < 0) {
      throw new IllegalArgumentException("Time cannot be negative.");
    }

    List<AnimationShape> shapeList = new ArrayList<>();
    for (Integer layer : this.layers.keySet()) {
      for (int i = 0; i < layers.get(layer).size(); i++) {
        String name = layers.get(layer).get(i);
        if (timeline.get(name).firstKey() <= time) {
          shapeList.add(this.getShape(time, name));
        }
      }
    }
    return shapeList;
  }

  @Override
  public void move(String name, int startingTime, int endingTime, int newX, int newY) {
    invalidAction(name, startingTime, endingTime);
    isMoveOverlap(name, startingTime, endingTime);

    if (startingTime == endingTime) {
      return;
    }

    float deltaX = (newX - getShape(startingTime, name).getPosition().getX())
            / (endingTime - startingTime);
    float deltaY = (newY - getShape(startingTime, name).getPosition().getY())
            / (endingTime - startingTime);

    // change the shape at the startingTime
    AnimationShape startShape = getShape(startingTime, name);
    startShape.setDeltaPosition(deltaX, deltaY);
    addToTimeLine(startingTime, name, startShape);

    // change the shape at the time between startingTime and endingTime
    moveBetween(name, startingTime, endingTime, deltaX, deltaY);

    // change the shape at the endingTime
    AnimationShape endShape = getShape(endingTime, name);
    endShape.setPosition(newX, newY);
    endShape.setDeltaPosition(0.0f, 0.0f);
    addToTimeLine(endingTime, name, endShape);

    // change the shape at the time after endingTime and before the new move action
    moveAfterEndingTime(name, endingTime, newX, newY);
  }

  /**
   * Check whether the given name shape already moves during the given time or not. If moves throw
   * error, if not do nothing.
   *
   * @param name         the name of the shape
   * @param startingTime the startingTime of the shape when the shape moves
   * @param endingTime   the endingTime of the shape when the shape moves
   * @throws IllegalArgumentException if the given name shape already moves during the given time
   */
  private void isMoveOverlap(String name, int startingTime, int endingTime) {
    if ((Math.abs(getShape(startingTime, name).getDeltaX() - 0) > 0.001)
            || (Math.abs(getShape(startingTime, name).getDeltaY() - 0) > 0.001)
            || (Math.abs(getShape(endingTime, name).getDeltaX() - 0) > 0.001)
            || (Math.abs(getShape(endingTime, name).getDeltaY() - 0) > 0.001)) {
      throw new IllegalArgumentException("Shape already move the position "
              + "or stay during given time.");
    }
  }

  /**
   * Change the position of shape at the time between startingTime and endingTime. If there is no
   * time between the startingTime and endingTime, do nothing.
   *
   * @param name         the name of the certain shape
   * @param startingTime the startingTime of the shape when the shape moves
   * @param endingTime   the endingTime of the shape when the shape moves
   * @param deltaX       the speed of the shape during the given time in x axis
   * @param deltaY       the speed of the shape during the given time in y axis
   */
  private void moveBetween(String name, int startingTime, int endingTime,
                           float deltaX, float deltaY) {
    List<Integer> temp;
    if (timeline.get(name).subMap(startingTime, endingTime) == null) {
      return;
    }
    SortedMap<Integer, AnimationShape> between = timeline.get(name)
            .subMap(startingTime, endingTime);
    temp = new ArrayList<>(between.keySet());
    for (int i = 0; i < temp.size(); i++) {
      AnimationShape shape = getShape(temp.get(i), name);
      shape.setPosition(shape.getPosition().getX()
                      + Math.round(deltaX * (temp.get(i) - startingTime)),
              shape.getPosition().getY() + Math.round(deltaY * (temp.get(i) - startingTime)));
      shape.setDeltaPosition(deltaX, deltaY);
      TreeMap<Integer, AnimationShape> operation = timeline.get(name);
      operation.put(temp.get(i), shape);
      timeline.put(name, operation);
    }
  }

  /**
   * Change the shape at the time after ending time but before another move. If there is no time
   * that satisfy this condition, do nothing.
   *
   * @param name       the name of the certain shape
   * @param endingTime the endingTime of the shape when the shape moves
   * @param newX       the new X position of the shape
   * @param newY       the new Y position of the shape
   */
  private void moveAfterEndingTime(String name, int endingTime, int newX, int newY) {
    if (timeline.get(name).tailMap(endingTime, false) == null) {
      return;
    }
    List<Integer> temp;
    SortedMap<Integer, AnimationShape> afterEnd = timeline.get(name)
            .tailMap(endingTime, false);
    temp = new ArrayList<>(afterEnd.keySet());
    for (int i = 0; i < temp.size(); i++) {
      AnimationShape shape = getShape(temp.get(i), name);
      if ((Math.abs(shape.getDeltaX() - 0) > 0.001)
              || (Math.abs(shape.getDeltaY() - 0) > 0.001)) {
        break;
      }
      shape.setPosition(newX, newY);
      shape.setDeltaPosition(0.0f, 0.0f);
      TreeMap<Integer, AnimationShape> operation = timeline.get(name);
      operation.put(temp.get(i), shape);
      timeline.put(name, operation);
    }
  }

  @Override
  public void changeColor(String name, int startingTime, ShapeColor color, int endingTime) {
    invalidAction(name, startingTime, endingTime);
    isColorOverlap(name, startingTime, endingTime);

    if (startingTime == endingTime) {
      return;
    }

    float deltaR = (color.getR() - getShape(startingTime, name).getShapeColor().getR())
            / (endingTime - startingTime);
    float deltaG = (color.getG() - getShape(startingTime, name).getShapeColor().getG())
            / (endingTime - startingTime);
    float deltaB = (color.getB() - getShape(startingTime, name).getShapeColor().getB())
            / (endingTime - startingTime);

    // add the shape at the startingTime
    AnimationShape startShape = getShape(startingTime, name);
    startShape.setDeltaColor(deltaR, deltaG, deltaB);
    addToTimeLine(startingTime, name, startShape);

    // changes the color of the shape at the time between startingTime and endingTime
    colorBetween(name, startingTime, endingTime, deltaR, deltaG, deltaB);

    // changes the color of the shape at the endingTime
    AnimationShape endShape = getShape(endingTime, name);
    endShape.setColor(color.getR(), color.getG(), color.getB());
    endShape.setDeltaColor(0.0f, 0.0f, 0.0f);
    addToTimeLine(endingTime, name, endShape);

    // changes the color of the shape at the time after ending time but before the
    // another change color
    colorAfterEndingTime(name, endingTime, color);
  }

  /**
   * Check whether the given name shape already changes the color during the given time or not. If
   * changes color throw error, if not do nothing.
   *
   * @param name         the name of the shape
   * @param startingTime the startingTime of the shape when the shape changes the color
   * @param endingTime   the endingTime of the shape when the shape changes the color
   * @throws IllegalArgumentException if the given name shape already changes the color during the
   *                                  given time
   */
  private void isColorOverlap(String name, int startingTime, int endingTime) {
    if ((Math.abs(getShape(startingTime, name).getDeltaR() - 0) > 0.001)
            || (Math.abs(getShape(startingTime, name).getDeltaG() - 0) > 0.001)
            || (Math.abs(getShape(startingTime, name).getDeltaB() - 0) > 0.001)
            || (Math.abs(getShape(endingTime, name).getDeltaR() - 0) > 0.001)
            || (Math.abs(getShape(endingTime, name).getDeltaG() - 0) > 0.001)
            || (Math.abs(getShape(endingTime, name).getDeltaB() - 0) > 0.001)) {
      throw new IllegalArgumentException("Shape already change the color "
              + "or stay during given time.");
    }
  }

  /**
   * Change the shape color at the time between startingTime and endingTime. If there is no time
   * between the startingTime and endingTime, do nothing.
   *
   * @param name         the name of the certain shape
   * @param startingTime the startingTime of the shape when the shape changes the color
   * @param endingTime   the endingTime of the shape when the shape changes the color
   * @param deltaR       the change of the shape's red color during the given time
   * @param deltaG       the change of the shape's green color during the given time
   * @param deltaB       the change of the shape's blue color during the given time
   */
  private void colorBetween(String name, int startingTime, int endingTime,
                            float deltaR, float deltaG, float deltaB) {
    if (timeline.get(name).subMap(startingTime, endingTime) == null) {
      return;
    }
    Set<Integer> times = timeline.get(name).keySet();
    List<Integer> tempt = new ArrayList<>(times);
    for (int i = 0; i < tempt.size(); i++) {
      if (tempt.get(i) > startingTime && tempt.get(i) < endingTime) {
        AnimationShape shape = getShape(tempt.get(i), name);
        shape.setColor(shape.getShapeColor().getR()
                        + Math.round(deltaR * (tempt.get(i) - startingTime)),
                shape.getShapeColor().getG() + Math.round(deltaG * (tempt.get(i) - startingTime)),
                shape.getShapeColor().getB() + Math.round(deltaB * (tempt.get(i) - startingTime)));
        shape.setDeltaColor(deltaR, deltaG, deltaB);
        TreeMap<Integer, AnimationShape> operation = timeline.get(name);
        operation.put(tempt.get(i), shape);
        timeline.put(name, operation);
      }
    }
  }

  /**
   * Change the shape color at the time after ending time but before another change color. If there
   * is no time that satisfy this condition, do nothing.
   *
   * @param name       the name of the certain shape
   * @param endingTime the endingTime of the shape when the shape changes the color
   * @param color      the new color to be changed
   */
  private void colorAfterEndingTime(String name, int endingTime, ShapeColor color) {
    if (timeline.get(name).tailMap(endingTime, false) == null) {
      return;
    }
    List<Integer> temp;
    SortedMap<Integer, AnimationShape> afterEnd = timeline.get(name)
            .tailMap(endingTime, false);
    temp = new ArrayList<>(afterEnd.keySet());
    for (int i = 0; i < temp.size(); i++) {
      AnimationShape shape = getShape(temp.get(i), name);
      if ((Math.abs(shape.getDeltaR() - 0) > 0.001)
              || (Math.abs(shape.getDeltaG() - 0) > 0.001)
              || (Math.abs(shape.getDeltaB() - 0) > 0.001)) {
        break;
      }
      shape.setColor(color.getR(), color.getG(), color.getB());
      shape.setDeltaColor(0.0f, 0.0f, 0.0f);
      TreeMap<Integer, AnimationShape> operation = timeline.get(name);
      operation.put(temp.get(i), shape);
      timeline.put(name, operation);
    }
  }

  @Override
  public void changeSize(String name, int startingTime,
                         int newHeight, int newWidth, int endingTime) {
    invalidAction(name, startingTime, endingTime);
    isSizeOverlap(name, startingTime, newHeight, newWidth, endingTime);

    if (startingTime == endingTime) {
      return;
    }

    float deltaHeight = calculateDelta(getShape(startingTime, name).getHeight(),
            newHeight, endingTime - startingTime);
    float deltaWidth = calculateDelta(getShape(startingTime, name).getWidth(),
            newWidth, endingTime - startingTime);

    // update the size of the shape at the starting time
    AnimationShape startShape = getShape(startingTime, name);
    startShape.setDeltaHeight(deltaHeight);
    startShape.setDeltaWidth(deltaWidth);
    addToTimeLine(startingTime, name, startShape);

    // update the size of the shape at the time between startingTime and endingTime
    sizeBetween(name, startingTime, endingTime, deltaHeight, deltaWidth);

    // update the size of the shape at the ending time
    AnimationShape endShape = getShape(endingTime, name);
    endShape.setSize(newHeight, newWidth);
    endShape.setDeltaHeight(0.0f);
    endShape.setDeltaWidth(0.0f);
    addToTimeLine(endingTime, name, endShape);

    // update the size of the shape at the time after endingTime but before the another new change
    // size method
    sizeAfterEndingTime(name, endingTime, newHeight, newWidth);
  }

  /**
   * Check whether the given name shape already change the size during the given time or not. If
   * change size throw error, if not do nothing.
   *
   * @param name         the name of the shape
   * @param startingTime the startingTime of the shape when the shape changes size
   * @param endingTime   the endingTime of the shape when the shape changes size
   * @throws IllegalArgumentException if the given name shape already change size during the given
   *                                  time
   */
  private void isSizeOverlap(String name, int startingTime, int newHeight,
                             int newWidth, int endingTime) {
    if (newHeight <= 0 || newWidth <= 0) {
      throw new IllegalArgumentException("Size cannot be less or equal to zero.");
    }
    if ((Math.abs(getShape(startingTime, name).getDeltaHeight() - 0) > 0.001)
            || (Math.abs(getShape(startingTime, name).getDeltaWidth() - 0) > 0.001)
            || (Math.abs(getShape(endingTime, name).getDeltaHeight() - 0) > 0.001)
            || (Math.abs(getShape(endingTime, name).getDeltaWidth() - 0) > 0.001)) {
      throw new IllegalArgumentException("Shape already change the size "
              + "or stay during given time.");
    }
  }

  /**
   * Change the shape size at the time between startingTime and endingTime. If there is no time
   * between the startingTime and endingTime, do nothing.
   *
   * @param name         the name of the certain shape
   * @param startingTime the startingTime of the shape when the shape changes the size
   * @param endingTime   the endingTime of the shape when the shape changes the size
   * @param deltaHeight  the change of the shape's height during the given time
   * @param deltaWidth   the change of the shape's width during the given time
   */
  private void sizeBetween(String name, int startingTime, int endingTime,
                           float deltaHeight, float deltaWidth) {
    if (timeline.get(name).subMap(startingTime, endingTime) == null) {
      return;
    }
    Set<Integer> times = timeline.get(name).keySet();
    List<Integer> tempt = new ArrayList<>(times);
    for (int i = 0; i < tempt.size(); i++) {
      if (tempt.get(i) > startingTime && tempt.get(i) < endingTime) {
        AnimationShape shape = getShape(tempt.get(i), name);
        shape.setSize(shape.getHeight()
                        + Math.round(deltaHeight * (tempt.get(i) - startingTime)),
                shape.getWidth() + Math.round(deltaWidth * (tempt.get(i) - startingTime)));
        shape.setDeltaHeight(deltaHeight);
        shape.setDeltaWidth(deltaWidth);
        TreeMap<Integer, AnimationShape> operation = timeline.get(name);
        operation.put(tempt.get(i), shape);
        timeline.put(name, operation);
      }
    }
  }

  /**
   * Change the shape size at the time after ending time but before another change size. If there is
   * no time that satisfy this condition, do nothing.
   *
   * @param name       the name of the certain shape
   * @param endingTime the endingTime of the shape when the shape moves
   * @param newHeight  the new height of the shape
   * @param newWidth   the new width of the shape
   */
  private void sizeAfterEndingTime(String name, int endingTime, int newHeight, int newWidth) {
    if (timeline.get(name).tailMap(endingTime, false) == null) {
      return;
    }
    List<Integer> temp;
    SortedMap<Integer, AnimationShape> afterEnd = timeline.get(name).
            tailMap(endingTime, false);
    temp = new ArrayList<>(afterEnd.keySet());
    for (int i = 0; i < temp.size(); i++) {
      AnimationShape shape = getShape(temp.get(i), name);
      if ((Math.abs(shape.getDeltaHeight() - 0) > 0.001)
              || (Math.abs(shape.getDeltaWidth() - 0) > 0.001)) {
        break;
      }
      shape.setSize(newHeight, newWidth);
      shape.setDeltaHeight(0.0f);
      shape.setDeltaWidth(0.0f);
      TreeMap<Integer, AnimationShape> operation = timeline.get(name);
      operation.put(temp.get(i), shape);
      timeline.put(name, operation);
    }
  }

  @Override
  public void changeDegree(String name, int startingTime,
                           int newDegree, int endingTime) {
    invalidAction(name, startingTime, endingTime);
    isDegreeOverlap(name, startingTime, endingTime);
    if (startingTime == endingTime) {
      return;
    }
    float deltaDegree = calculateDelta(getShape(startingTime, name).getDegree(), newDegree,
            endingTime - startingTime);

    //update the degree of the shape at the starting time
    AnimationShape startShape = getShape(startingTime, name);
    startShape.setDeltaDegree(deltaDegree);
    addToTimeLine(startingTime, name, startShape);
    // update the degree of the shape at the time between startingTime and endingTime
    degreeBetween(name, startingTime, endingTime, deltaDegree);
    // update the degree of the shape at the ending time
    AnimationShape endShape = getShape(endingTime, name);
    endShape.setDegree(newDegree);
    endShape.setDeltaDegree(0.0f);
    addToTimeLine(endingTime, name, endShape);
    // update the degreeof the shape at the time after endingTime but before the another new change
    // degree method
    degreeAfterEndingTime(name, endingTime, newDegree);
  }

  /**
   * Checks whether the degrees of the shape is overlapping.
   *
   * @param name         the name of the shape
   * @param startingTime the starting time of changing degree
   * @param endingTime   the ending time of changing degree
   */
  private void isDegreeOverlap(String name, int startingTime, int endingTime) {
    if ((Math.abs(getShape(startingTime, name).getDeltaDegree() - 0) > 0.001)
            || (Math.abs(getShape(endingTime, name).getDeltaDegree() - 0) > 0.001)) {
      throw new IllegalArgumentException("Shape already change the size "
              + "or stay during given time.");
    }
  }

  /**
   * Checks the degree in between.
   *
   * @param name         the name of the shape
   * @param startingTime the starting time
   * @param endingTime   the ending time
   * @param deltaDegree  the change in degree
   */
  private void degreeBetween(String name, int startingTime, int endingTime,
                             float deltaDegree) {
    if (timeline.get(name).subMap(startingTime, endingTime) == null) {
      return;
    }
    Set<Integer> times = timeline.get(name).keySet();
    List<Integer> tempt = new ArrayList<>(times);
    for (int i = 0; i < tempt.size(); i++) {
      if (tempt.get(i) > startingTime && tempt.get(i) < endingTime) {
        AnimationShape shape = getShape(tempt.get(i), name);
        shape
                .setDegree(shape.getDegree() + Math.round(deltaDegree *
                        (tempt.get(i) - startingTime)));
        shape.setDeltaDegree(deltaDegree);
        TreeMap<Integer, AnimationShape> operation = timeline.get(name);
        operation.put(tempt.get(i), shape);
        timeline.put(name, operation);
      }
    }
  }

  /**
   * The degree at the end.
   *
   * @param name       name of the shape
   * @param endingTime the ending time
   * @param newDegree  the new degree
   */
  private void degreeAfterEndingTime(String name, int endingTime, int newDegree) {
    if (timeline.get(name).tailMap(endingTime, false) == null) {
      return;
    }
    List<Integer> temp;
    SortedMap<Integer, AnimationShape> afterEnd = timeline.get(name).
            tailMap(endingTime, false);
    temp = new ArrayList<>(afterEnd.keySet());
    for (int i = 0; i < temp.size(); i++) {
      AnimationShape shape = getShape(temp.get(i), name);
      if (Math.abs(shape.getDeltaDegree() - 0) > 0.001) {
        break;
      }
      shape.setDegree(newDegree);
      shape.setDeltaDegree(0.0f);
      TreeMap<Integer, AnimationShape> operation = timeline.get(name);
      operation.put(temp.get(i), shape);
      timeline.put(name, operation);
    }
  }

  @Override
  public AnimationBackground getBackground() {
    return new AnimationBackground(background.getX(), background.getY(),
            background.getDimension().width, background.getDimension().height);
  }

  @Override
  public LinkedHashMap<String, String> getExistingShape() {
    return new LinkedHashMap<>(this.existingShape);
  }

  @Override
  public HashMap<String, TreeMap<Integer, AnimationShape>> getTimeline() {
    return new HashMap<>(this.timeline);
  }

  @Override
  public TreeMap<Integer, List<String>> getLayers() {
    TreeMap<Integer, List<String>> temp = new TreeMap<>(this.layers);
    return temp;
  }

  @Override
  public void setBackground(int x, int y, int width, int height) {
    this.background = new AnimationBackground(x, y, width, height);
  }

  @Override
  public void setExistingShape(LinkedHashMap<String, String> shapes) {
    this.existingShape = shapes;
  }

  @Override
  public void setTimeline(HashMap<String, TreeMap<Integer, AnimationShape>> timeline) {
    this.timeline = timeline;
  }

  /**
   * Checks to see if a certain AnimationShape exists in the Animation before a given tick.
   *
   * @param name the name of the AnimationShape looked for
   * @param time the tick to start looking for the shape
   * @return true if the AnimationShape exists before the given tick
   */
  private boolean isExistBeforeTime(String name, int time) {
    invalidName(name);
    if (time < 0) {
      throw new IllegalArgumentException("Time cannot be negative.");
    }
    if (!timeline.containsKey(name)) {
      throw new IllegalArgumentException("The given name does not exist");
    }

    for (int t : timeline.get(name).keySet()) {
      if (t <= time && timeline.get(name).containsKey(t)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Checks to see if a certain action is valid.
   *
   * @param name         the name of the AnimationShape being checked
   * @param startingTime the starting tick of the action
   * @param endingTime   the ending tick of the action
   * @throws IllegalArgumentException when a certain AnimationShape doesn't exist before the start
   *                                  time of an action, if the startingTime or endingTime are less
   *                                  than 1, of if the endingTime is before the startingTime.
   */
  private void invalidAction(String name, int startingTime, int endingTime) {
    invalidName(name);
    if (!isExistBeforeTime(name, startingTime)) {
      throw new IllegalArgumentException("This shape is not existed before the starting time.");
    }
    if (startingTime < 0 || endingTime < 0) {
      throw new IllegalArgumentException("Time cannot be negative.");
    }
    if (startingTime > endingTime) {
      throw new IllegalArgumentException("Starting time can't be greater or equal to ending time.");
    }
  }

  /**
   * Return error whether the name is invalid (null or empty string).
   *
   * @param name the name of shape
   * @throws IllegalArgumentException if name is null or empty string
   */
  private void invalidName(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null.");
    }
    if (name.equals("")) {
      throw new IllegalArgumentException("Name cannot be empty string.");
    }
    if (!existingShape.containsKey(name)) {
      throw new IllegalArgumentException("Name does not exist.");
    }
  }

  /**
   * Puts an AnimationShape into the timeline at a certain tick given its name.
   *
   * @param time  the tick to put the shape in
   * @param name  the name of the shape
   * @param shape the AnimationShape corresponding to the name
   */
  private void addToTimeLine(int time, String name, AnimationShape shape) {
    TreeMap<Integer, AnimationShape> operation = timeline.get(name);
    operation.put(time, shape);
    timeline.put(name, operation);
  }

  /**
   * Calculate the delta value using current value, new value and time.
   *
   * @param curValue the current value
   * @param newValue the new value
   * @param time     the time from current value to new value
   * @return delta value for any values in shape properties
   */
  private float calculateDelta(int curValue, int newValue, int time) {
    return (newValue - curValue) / time;
  }

  @Override
  public void addFrame(String name, int time, AnimationShape newShape) {
    invalidName(name);
    if (time < 0) {
      throw new IllegalArgumentException("Time cannot be less than zero");
    }
    if (newShape == null) {
      throw new IllegalArgumentException("Shape cannot be null");
    }
    if (!timeline.containsKey(name)) {
      this.addShape(newShape, name, time);
    } else {
      if (timeline.get(name).containsKey(time)) {
        throw new IllegalArgumentException("The given time key frame already exist. "
                + "Instead of adding, please modify the frame.");
      }
      TreeMap<Integer, AnimationShape> temp = timeline.get(name);
      temp.put(time, newShape);
      if (timeline.get(name).higherKey(time) != null) {
        int higherTime = timeline.get(name).higherKey(time);
        AnimationShape tShape = getShape(higherTime, name);
        AnimationShape curShape = setDeltaValue(name, time, higherTime, tShape);
        temp.put(time, curShape);
      }
      if (timeline.get(name).lowerKey(time) != null) {
        int lowerTime = timeline.get(name).lowerKey(time);
        AnimationShape lowerShape = setDeltaValue(name, lowerTime, time, newShape);
        temp.put(lowerTime, lowerShape);
      }
      timeline.put(name, temp);
    }
  }


  @Override
  public void removeFrame(String name, int time) {
    invalidName(name);
    if (time < 0) {
      throw new IllegalArgumentException("Time cannot be less than zero");
    }
    if (!timeline.get(name).containsKey(time)) {
      throw new IllegalArgumentException("Given time is not existing as keyFrame.");
    }
    TreeMap<Integer, AnimationShape> temp = timeline.get(name);
    temp.remove(time);
    timeline.put(name, temp);
    if (timeline.get(name).lowerKey(time) != null) {
      if (timeline.get(name).higherKey(time) == null) {
        int lowerTime = timeline.get(name).lowerKey(time);
        AnimationShape lowerShape = timeline.get(name).get(lowerTime);
        lowerShape.setDeltaPosition(0.0f, 0.0f);
        lowerShape.setDeltaColor(0.0f, 0.0f, 0.0f);
        lowerShape.setDeltaHeight(0.0f);
        lowerShape.setDeltaWidth(0.0f);
        lowerShape.setDeltaDegree(0.0f);
        temp.put(lowerTime, lowerShape);
        timeline.put(name, temp);
      } else {
        int higherTime = timeline.get(name).higherKey(time);
        int lowerTime = timeline.get(name).lowerKey(time);
        AnimationShape tShape = getShape(higherTime, name);
        AnimationShape curShape = setDeltaValue(name, lowerTime, higherTime, tShape);
        temp.put(lowerTime, curShape);
        timeline.put(name, temp);
      }
    }
  }

  @Override
  public void modifyFrame(String name, int time, AnimationShape newShape) {
    invalidName(name);
    if (!timeline.containsKey(name) || timeline.get(name).firstKey() == null) {
      new IllegalArgumentException("You should add frame instead of modify frame.");
    }
    int firstTick = timeline.get(name).firstKey();
    int lastTick = timeline.get(name).lastKey();
    if ((firstTick == lastTick) && (firstTick != time)) {
      new IllegalArgumentException("There is only one frame in this animation."
              + " if you want to modify that frame, you should give the right frame.");
    }
    if (time < firstTick || time > lastTick) {
      throw new IllegalArgumentException("You cannot modify the time which is "
              + "out of the existed frame.");
    }
    if (newShape == null) {
      throw new IllegalArgumentException("Shape cannot be null.");
    }
    TreeMap<Integer, AnimationShape> temp = timeline.get(name);
    temp.put(time, newShape);
    if (timeline.get(name).higherKey(time) != null) {
      int higherTime = timeline.get(name).higherKey(time);
      AnimationShape tShape = getShape(higherTime, name);
      AnimationShape curShape = setDeltaValue(name, time, higherTime, tShape);
      temp.put(time, curShape);
    }
    if (timeline.get(name).lowerKey(time) != null) {
      int lowerTime = timeline.get(name).lowerKey(time);
      AnimationShape lowerShape = setDeltaValue(name, lowerTime, time, newShape);
      temp.put(lowerTime, lowerShape);
    }
    timeline.put(name, temp);
  }

  @Override
  public AnimationShape makeShape(String name, Posn2D pos, ShapeColor color,
                                  int height, int width, int degree) {
    invalidName(name);
    if (pos == null) {
      throw new IllegalArgumentException("Position cannot be null.");
    }
    if (color == null) {
      throw new IllegalArgumentException("Color cannot be null");
    }
    if (height <= 0 || width <= 0) {
      throw new IllegalArgumentException("Size cannot be less or equal to 0.");
    }
    AnimationShape shape;
    switch (existingShape.get(name)) {
      case "rectangle":
        shape = new RectangleShape(pos, color, height, width, degree);
        break;
      case "ellipse":
        shape = new EllipseShape(pos, color, height, width, degree);
        break;
      default:
        throw new IllegalArgumentException("Shape doesn't exist");
    }
    return shape;
  }

  @Override
  public void setLayer(String name, int layer) {
    invalidName(name);

    for (Integer lay : this.layers.keySet()) {
      if (this.layers.get(lay).contains(name)) {
        List<String> shapes = this.layers.get(lay);
        shapes.remove(name);
        this.layers.put(lay, shapes);
        if (this.layers.get(lay).size() == 0) {
          TreeMap<Integer, List<String>> temp = new TreeMap<>(this.layers);
          temp.remove(lay);
          this.layers = temp;
        }
      }
    }

    TreeMap<Integer, List<String>> temp = layers;
    List<String> tempList;
    if (temp.get(layer) == null) {
      tempList = new ArrayList<>();
    } else {
      tempList = temp.get(layer);
    }
    tempList.add(name);
    temp.put(layer, tempList);
    this.layers = temp;
  }

  /**
   * Set the delta value in the shape by comparing two shapes.
   *
   * @param name     the name of the shape
   * @param start    the starting time
   * @param end      the ending time
   * @param newShape the new shape to compare to the shape that is at starting time
   * @return AnimationShape the shape that has the valid delta values
   */
  private AnimationShape setDeltaValue(String name, int start, int end, AnimationShape newShape) {
    AnimationShape shape = getShape(start, name);
    float deltaX = (newShape.getPosition().getX() - shape.getPosition().getX())
            / (end - start);
    float deltaY = (newShape.getPosition().getY() - shape.getPosition().getY())
            / (end - start);
    float deltaR = (newShape.getShapeColor().getR() - shape.getShapeColor().getR())
            / (end - start);
    float deltaG = (newShape.getShapeColor().getG() - shape.getShapeColor().getG())
            / (end - start);
    float deltaB = (newShape.getShapeColor().getB() - shape.getShapeColor().getB())
            / (end - start);
    float deltaHeight = (newShape.getHeight() - shape.getHeight()) / (end - start);
    float deltaWidth = (newShape.getWidth() - shape.getWidth()) / (end - start);
    float deltaDegree = (newShape.getDegree() - shape.getDegree()) / (end - start);

    shape.setDeltaPosition(deltaX, deltaY);
    shape.setDeltaColor(deltaR, deltaG, deltaB);
    shape.setDeltaHeight(deltaHeight);
    shape.setDeltaWidth(deltaWidth);
    shape.setDeltaDegree(deltaDegree);

    return shape;
  }

  @Override
  public void deleteLayer(int layer) {
    if (!this.layers.containsKey(layer)) {
      throw new IllegalArgumentException("The given layer does not exist.");
    }

    this.layers.remove(layer);
  }
}