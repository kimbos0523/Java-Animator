import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;

import cs3500.animator.model.AnimationBackground;
import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.AnimationOperations;
import cs3500.animator.model.AnimationShape;
import cs3500.animator.model.EllipseShape;
import cs3500.animator.model.Posn2D;
import cs3500.animator.model.RectangleShape;
import cs3500.animator.model.ShapeColor;
import cs3500.animator.util.AnimationBuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class for AnimationModel: unit test to ensure that AnimationModel operates right way.
 */
public class AnimationModelTest {
  AnimationOperations animationModel;

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNull() {
    animationModel = new AnimationModel(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorShapeNull() {
    LinkedHashMap<String, String> test = new LinkedHashMap<>();
    test.put("rec", null);
    animationModel = new AnimationModel(test);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNameNull() {
    LinkedHashMap<String, String> test = new LinkedHashMap<>();
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    test.put(null, "rectangle");
    animationModel = new AnimationModel(test);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNameEmptyString() {
    LinkedHashMap<String, String> test = new LinkedHashMap<>();
    test.put("", "rectangle");
    animationModel = new AnimationModel(test);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorTypeNull() {
    LinkedHashMap<String, String> test = new LinkedHashMap<>();
    test.put("rec", null);
    animationModel = new AnimationModel(test);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorTypeEmptyString() {
    LinkedHashMap<String, String> test = new LinkedHashMap<>();
    test.put("rec", "");
    animationModel = new AnimationModel(test);
  }

  @Test
  public void testConstructor() {
    LinkedHashMap<String, String> test = new LinkedHashMap<>();
    test.put("rec", "rectangle");
    test.put("ellipse", "ellipse");
    animationModel = new AnimationModel(test);
    assertEquals(test, animationModel.getExistingShape());
  }

  @Test
  public void testDeclareShape() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    animationModel.declareShape("oval", "ellipse");
    HashMap<String, String> shape = new HashMap<>();
    shape.put("rec", "rectangle");
    shape.put("oval", "ellipse");
    assertEquals(shape, animationModel.getExistingShape());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDeclareShapeNameNull() {
    animationModel = new AnimationModel();
    animationModel.declareShape(null, "rectangle");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDeclareShapeNameEmptyString() {
    animationModel = new AnimationModel();
    animationModel.declareShape("", "rectangle");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDeclareShapeNameAlreadyExist() {
    animationModel = new AnimationModel();
    animationModel.declareShape("shape", "rectangle");
    animationModel.declareShape("shape", "oval");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDeclareShapeTypeNull() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDeclareShapeTypeEmptyString() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDeclareShapeTypeWrong() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "start");
  }


  @Test
  public void testAddShapeOne() {
    animationModel = new AnimationModel();
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.declareShape("rec", "rectangle");
    animationModel.addShape(rec, "rec", 5);
    assertEquals(0, animationModel.getShape(5, "rec").getPosition().getX());
    assertEquals(0, animationModel.getShape(5, "rec").getPosition().getY());
    assertEquals(0, animationModel.getShape(5, "rec").getShapeColor().getR());
    assertEquals(0, animationModel.getShape(5, "rec").getShapeColor().getG());
    assertEquals(0, animationModel.getShape(5, "rec").getShapeColor().getB());
    assertEquals(10, animationModel.getShape(5, "rec").getHeight());
    assertEquals(10, animationModel.getShape(5, "rec").getWidth());
  }

  @Test
  public void testAddShapeTwo() {
    animationModel = new AnimationModel();
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.declareShape("rec", "rectangle");
    animationModel.addShape(rec, "rec", 5);
    AnimationShape oval = new EllipseShape(new Posn2D(50, 50),
            new ShapeColor(255, 255, 255), 50, 50);
    animationModel.declareShape("oval", "ellipse");
    animationModel.addShape(oval, "oval", 10);

    assertEquals(0, animationModel.getShape(5, "rec").getPosition().getX());
    assertEquals(0, animationModel.getShape(5, "rec").getPosition().getY());
    assertEquals(0, animationModel.getShape(5, "rec").getShapeColor().getR());
    assertEquals(0, animationModel.getShape(5, "rec").getShapeColor().getG());
    assertEquals(0, animationModel.getShape(5, "rec").getShapeColor().getB());
    assertEquals(10, animationModel.getShape(5, "rec").getHeight());
    assertEquals(10, animationModel.getShape(5, "rec").getWidth());

    assertEquals(50, animationModel.getShape(10, "oval").getPosition().getX());
    assertEquals(50, animationModel.getShape(10, "oval").getPosition().getY());
    assertEquals(255, animationModel.getShape(10, "oval").getShapeColor().getR());
    assertEquals(255, animationModel.getShape(10, "oval").getShapeColor().getG());
    assertEquals(255, animationModel.getShape(10, "oval").getShapeColor().getB());
    assertEquals(50, animationModel.getShape(10, "oval").getHeight());
    assertEquals(50, animationModel.getShape(10, "oval").getWidth());
  }

  @Test
  public void testAddShapeAtSameTime() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    animationModel.declareShape("oval", "ellipse");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.addShape(rec, "rec", 5);
    AnimationShape oval = new EllipseShape(new Posn2D(50, 50),
            new ShapeColor(255, 255, 255), 50, 50);
    animationModel.addShape(oval, "oval", 5);

    assertEquals(0, animationModel.getShape(5, "rec").getPosition().getX());
    assertEquals(0, animationModel.getShape(5, "rec").getPosition().getY());
    assertEquals(0, animationModel.getShape(5, "rec").getShapeColor().getR());
    assertEquals(0, animationModel.getShape(5, "rec").getShapeColor().getG());
    assertEquals(0, animationModel.getShape(5, "rec").getShapeColor().getB());
    assertEquals(10, animationModel.getShape(5, "rec").getHeight());
    assertEquals(10, animationModel.getShape(5, "rec").getWidth());

    assertEquals(50, animationModel.getShape(5, "oval").getPosition().getX());
    assertEquals(50, animationModel.getShape(5, "oval").getPosition().getY());
    assertEquals(255, animationModel.getShape(5, "oval").getShapeColor().getR());
    assertEquals(255, animationModel.getShape(5, "oval").getShapeColor().getG());
    assertEquals(255, animationModel.getShape(5, "oval").getShapeColor().getB());
    assertEquals(50, animationModel.getShape(5, "oval").getHeight());
    assertEquals(50, animationModel.getShape(5, "oval").getWidth());
  }


  @Test(expected = IllegalArgumentException.class)
  public void testAddShapeNullShape() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    animationModel.declareShape("oval", "ellipse");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.addShape(null, "rec", 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddShapeEmptyStringName() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    animationModel.declareShape("oval", "ellipse");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.addShape(rec, "", 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddShapeNullName() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    animationModel.declareShape("oval", "ellipse");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.addShape(rec, null, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddShapeNegativeTime() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.addShape(rec, "rec", -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddShapeAlreadyExistedName() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    AnimationShape rec1 = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    AnimationShape rec2 = new RectangleShape(new Posn2D(5, 5), new ShapeColor(0, 0, 0),
            50, 50);
    animationModel.addShape(rec1, "rec", 5);
    animationModel.addShape(rec2, "rec", 8);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddShapeInvalidColorNegative() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(-1, 0, 0),
            10, 10);
    animationModel.addShape(rec, "rec", 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddShapeInvalidColorGreater255() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 256, 0),
            10, 10);
    animationModel.addShape(rec, "rec", 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddShapeInvalidSize() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            0, 0);
    animationModel.addShape(rec, "rec", 5);
  }


  @Test
  public void testRemoveShapeOnlyDeclareShape() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    animationModel.removeShape("rec");

    assertEquals(false, animationModel.getExistingShape().containsKey("rec"));
    assertEquals(false, animationModel.getTimeline().containsKey("rec"));
  }

  @Test
  public void testRemoveShapeOne() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.addShape(rec, "rec", 5);

    assertEquals(0, animationModel.getShape(5, "rec").getPosition().getX());
    assertEquals(0, animationModel.getShape(5, "rec").getPosition().getY());
    assertEquals(0, animationModel.getShape(5, "rec").getShapeColor().getR());
    assertEquals(0, animationModel.getShape(5, "rec").getShapeColor().getG());
    assertEquals(0, animationModel.getShape(5, "rec").getShapeColor().getB());
    assertEquals(10, animationModel.getShape(5, "rec").getHeight());
    assertEquals(10, animationModel.getShape(5, "rec").getWidth());

    animationModel.removeShape("rec");
    assertEquals(false, animationModel.getExistingShape().containsKey("rec"));
    assertEquals(false, animationModel.getTimeline().containsKey("rec"));
  }

  @Test
  public void testRemoveShapeTwoShapeAtDiffTime() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    animationModel.declareShape("oval", "ellipse");

    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    AnimationShape oval = new EllipseShape(new Posn2D(50, 50),
            new ShapeColor(255, 255, 255), 50, 50);
    animationModel.addShape(rec, "rec", 5);
    animationModel.addShape(oval, "oval", 10);

    animationModel.removeShape("rec");
    assertEquals(false, animationModel.getExistingShape().containsKey("rec"));
    assertEquals(false, animationModel.getTimeline().containsKey("rec"));
    assertEquals(true, animationModel.getExistingShape().containsKey("oval"));
    assertEquals(true, animationModel.getTimeline().containsKey("oval"));

    animationModel.removeShape("oval");
    assertEquals(false, animationModel.getExistingShape().containsKey("rec"));
    assertEquals(false, animationModel.getTimeline().containsKey("rec"));
    assertEquals(false, animationModel.getExistingShape().containsKey("oval"));
    assertEquals(false, animationModel.getTimeline().containsKey("oval"));
  }

  @Test
  public void testRemoveShapeTwoShapeAtSameTime() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    animationModel.declareShape("oval", "ellipse");

    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.addShape(rec, "rec", 5);
    AnimationShape oval = new EllipseShape(new Posn2D(50, 50),
            new ShapeColor(255, 255, 255), 50, 50);
    animationModel.addShape(oval, "oval", 5);

    assertEquals(50, animationModel.getShape(5, "oval").getPosition().getX());
    assertEquals(50, animationModel.getShape(5, "oval").getPosition().getY());
    assertEquals(255, animationModel.getShape(5, "oval").getShapeColor().getR());
    assertEquals(255, animationModel.getShape(5, "oval").getShapeColor().getG());
    assertEquals(255, animationModel.getShape(5, "oval").getShapeColor().getB());
    assertEquals(50, animationModel.getShape(5, "oval").getHeight());
    assertEquals(50, animationModel.getShape(5, "oval").getWidth());

    animationModel.removeShape("oval");

    assertEquals(false, animationModel.getExistingShape().containsKey("oval"));
    assertEquals(false, animationModel.getTimeline().containsKey("oval"));
    assertEquals(true, animationModel.getExistingShape().containsKey("rec"));
    assertEquals(true, animationModel.getTimeline().containsKey("rec"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveShapeNameNull() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");

    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.addShape(rec, "rec", 5);
    animationModel.removeShape(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveShapeNameEmptyString() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");

    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.addShape(rec, "rec", 5);
    animationModel.removeShape("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveShapeNameNotExist() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");

    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.addShape(rec, "rec", 5);
    animationModel.removeShape("oval");
  }


  @Test
  public void testGetAnimationNoShape() {
    animationModel = new AnimationModel();
    assertEquals("", animationModel.getAnimation());
  }

  @Test
  public void testGetAnimationAddShapeNoAction() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");

    assertEquals("canvas 0 0 500 500\n"
            + "shape rec rectangle", animationModel.getAnimation());
  }

  @Test
  public void testGetAnimationOneShapeMove() {
    animationModel = new AnimationModel();
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.declareShape("rec", "rectangle");
    animationModel.addShape(rec, "rec", 5);
    animationModel.move("rec", 5, 10, 50, 50);
    animationModel.move("rec", 15, 20, 100, 0);

    assertEquals("canvas 0 0 500 500\n"
                    + "shape rec rectangle\n"
                    + "motion rec 5 0 0 10 10 0 0 0 10 50 50 10 10 0 0 0 0 0\n"
                    + "motion rec 10 50 50 10 10 0 0 0 15 50 50 10 10 0 0 0 0 0\n"
                    + "motion rec 15 50 50 10 10 0 0 0 20 100 0 10 10 0 0 0 0 0",
            animationModel.getAnimation());
  }

  @Test
  public void testGetAnimationOneShapeChangeColor() {
    animationModel = new AnimationModel();
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.declareShape("rec", "rectangle");
    animationModel.addShape(rec, "rec", 5);
    animationModel.changeColor("rec", 5,
            new ShapeColor(100, 100, 100), 10);
    animationModel.changeColor("rec", 10,
            new ShapeColor(231, 153, 92), 20);

    assertEquals("canvas 0 0 500 500\n"
                    + "shape rec rectangle\n"
                    + "motion rec 5 0 0 10 10 0 0 0 10 0 0 10 10 100 100 100 0 0\n"
                    + "motion rec 10 0 0 10 10 100 100 100 20 0 0 10 10 231 153 92 0 0",
            animationModel.getAnimation());
  }

  @Test
  public void testGetAnimationOneShapeChangeSize() {
    animationModel = new AnimationModel();
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.declareShape("rec", "rectangle");
    animationModel.addShape(rec, "rec", 5);
    animationModel.changeSize("rec", 5, 50, 50, 10);
    animationModel.changeSize("rec", 10, 20, 20, 20);

    assertEquals("canvas 0 0 500 500\n"
                    + "shape rec rectangle\n"
                    + "motion rec 5 0 0 10 10 0 0 0 10 0 0 50 50 0 0 0 0 0\n"
                    + "motion rec 10 0 0 50 50 0 0 0 20 0 0 20 20 0 0 0 0 0",
            animationModel.getAnimation());
  }

  @Test
  public void testGetAnimationMultipleShapeMultipleAction() {
    animationModel = new AnimationModel();
    AnimationShape rec1 = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    AnimationShape rec2 = new RectangleShape(new Posn2D(50, 30), new ShapeColor(35, 86, 222),
            90, 250);
    AnimationShape oval = new EllipseShape(new Posn2D(50, 50),
            new ShapeColor(255, 255, 255), 50, 50);
    animationModel.declareShape("rec1", "rectangle");
    animationModel.declareShape("rec2", "rectangle");
    animationModel.declareShape("oval", "ellipse");
    animationModel.addShape(rec1, "rec1", 5);
    animationModel.addShape(rec2, "rec2", 10);
    animationModel.addShape(oval, "oval", 5);

    animationModel.changeColor("rec1", 5, new ShapeColor(30, 30, 30), 8);
    animationModel.changeSize("rec1", 5, 50, 50, 9);
    animationModel.move("rec1", 5, 10, 300, 300);
    animationModel.move("rec1", 11, 18, 100, 250);
    animationModel.changeColor("rec1", 11, new ShapeColor(25, 210, 10), 18);

    animationModel.changeSize("rec2", 10, 50, 50, 17);
    animationModel.changeColor("rec2", 10, new ShapeColor(30, 30, 30), 19);
    animationModel.move("rec2", 10, 20, 300, 300);
    animationModel.move("rec2", 21, 28, 100, 250);
    animationModel.changeColor("rec2", 21, new ShapeColor(25, 210, 10), 28);

    animationModel.changeColor("oval", 5, new ShapeColor(30, 30, 30), 8);
    animationModel.changeSize("oval", 5, 50, 50, 9);
    animationModel.move("oval", 5, 10, 300, 300);
    animationModel.changeColor("oval", 11, new ShapeColor(25, 210, 10), 18);
    animationModel.move("oval", 10, 20, 100, 250);

    assertEquals("canvas 0 0 500 500\n"
                    + "shape rec1 rectangle\n"
                    + "motion rec1 5 0 0 10 10 0 0 0 8 180 180 40 40 30 30 30 0 0\n"
                    + "motion rec1 8 180 180 40 40 30 30 30 9 240 240 50 50 30 30 30 0 0\n"
                    + "motion rec1 9 240 240 50 50 30 30 30 10 300 300 50 50 30 30 30 0 0\n"
                    + "motion rec1 10 300 300 50 50 30 30 30 11 300 300 50 50 30 30 30 0 0\n"
                    + "motion rec1 11 300 300 50 50 30 30 30 18 100 250 50 50 25 210 10 0 0\n"
                    + "shape rec2 rectangle\n"
                    + "motion rec2 10 50 30 250 90 35 86 222 17 225 219 50 50 35 44 75 0 0\n"
                    + "motion rec2 17 225 219 50 50 35 44 75 19 275 273 50 50 30 30 30 0 0\n"
                    + "motion rec2 19 275 273 50 50 30 30 30 20 300 300 50 50 30 30 30 0 0\n"
                    + "motion rec2 20 300 300 50 50 30 30 30 21 300 300 50 50 30 30 30 0 0\n"
                    + "motion rec2 21 300 300 50 50 30 30 30 28 100 250 50 50 25 210 10 0 0\n"
                    + "shape oval ellipse\n"
                    + "motion oval 5 50 50 50 50 255 255 255 8 200 200 50 50 30 30 30 0 0\n"
                    + "motion oval 8 200 200 50 50 30 30 30 9 250 250 50 50 30 30 30 0 0\n"
                    + "motion oval 9 250 250 50 50 30 30 30 10 300 300 50 50 30 30 30 0 0\n"
                    + "motion oval 10 300 300 50 50 30 30 30 11 280 295 50 50 30 30 30 0 0\n"
                    + "motion oval 11 280 295 50 50 30 30 30 18 140 260 50 50 25 210 10 0 0\n"
                    + "motion oval 18 140 260 50 50 25 210 10 20 100 250 50 50 25 210 10 0 0",
            animationModel.getAnimation());
  }


  @Test
  public void testGetShape() {
    animationModel = new AnimationModel();
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 50, 100),
            20, 10);
    animationModel.declareShape("rec", "rectangle");
    animationModel.addShape(rec, "rec", 5);

    assertEquals(new Posn2D(0, 0),
            animationModel.getShape(5, "rec").getPosition());
    assertEquals(0, animationModel.getShape(5, "rec").getShapeColor().getR());
    assertEquals(50, animationModel.getShape(5, "rec").getShapeColor().getG());
    assertEquals(100, animationModel.getShape(5, "rec").getShapeColor().getB());
    assertEquals(20, animationModel.getShape(5, "rec").getHeight());
    assertEquals(10, animationModel.getShape(5, "rec").getWidth());
  }

  @Test
  public void testGetShapeDuringAction() {
    animationModel = new AnimationModel();
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            100, 100);
    animationModel.declareShape("rec", "rectangle");
    animationModel.addShape(rec, "rec", 5);

    animationModel.move("rec", 5, 10, 100, 100);
    animationModel.changeColor("rec", 5, new ShapeColor(255, 255, 255), 10);
    animationModel.changeSize("rec", 5, 200, 200, 10);

    assertEquals(new Posn2D(0, 0),
            animationModel.getShape(5, "rec").getPosition());
    assertEquals(0, animationModel.getShape(5, "rec").getShapeColor().getR());
    assertEquals(0, animationModel.getShape(5, "rec").getShapeColor().getG());
    assertEquals(0, animationModel.getShape(5, "rec").getShapeColor().getB());
    assertEquals(100, animationModel.getShape(5, "rec").getHeight());
    assertEquals(100, animationModel.getShape(5, "rec").getWidth());

    assertEquals(new Posn2D(60, 60),
            animationModel.getShape(8, "rec").getPosition());
    assertEquals(153, animationModel.getShape(8, "rec").getShapeColor().getR());
    assertEquals(153, animationModel.getShape(8, "rec").getShapeColor().getG());
    assertEquals(153, animationModel.getShape(8, "rec").getShapeColor().getB());
    assertEquals(160, animationModel.getShape(8, "rec").getHeight());
    assertEquals(160, animationModel.getShape(8, "rec").getWidth());

    assertEquals(new Posn2D(100, 100),
            animationModel.getShape(10, "rec").getPosition());
    assertEquals(255, animationModel.getShape(10, "rec").getShapeColor().getR());
    assertEquals(255, animationModel.getShape(10, "rec").getShapeColor().getG());
    assertEquals(255, animationModel.getShape(10, "rec").getShapeColor().getB());
    assertEquals(200, animationModel.getShape(10, "rec").getHeight());
    assertEquals(200, animationModel.getShape(10, "rec").getWidth());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetShapeNullName() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 50, 100),
            20, 10);
    animationModel.addShape(rec, "rec", 5);

    animationModel.getShape(5, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetShapeEmptyStringName() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 50, 100),
            20, 10);
    animationModel.addShape(rec, "rec", 5);

    animationModel.getShape(5, "");
  }


  @Test(expected = IllegalArgumentException.class)
  public void testGetShapeTimeNegative() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 50, 100),
            20, 10);
    animationModel.addShape(rec, "rec", 0);

    animationModel.getShape(-1, "rec");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetShapeTimeBeforeAdd() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 50, 100),
            20, 10);
    animationModel.addShape(rec, "rec", 5);

    animationModel.getShape(4, "rec");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetShapeNameDoesNotExist() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 50, 100),
            20, 10);
    animationModel.addShape(rec, "rec", 5);

    animationModel.getShape(7, "rectangle");
  }


  @Test
  public void testMoveOneShape() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.addShape(rec, "rec", 5);

    assertEquals(0, animationModel.getShape(5, "rec").getPosition().getX());
    assertEquals(0, animationModel.getShape(5, "rec").getPosition().getY());

    animationModel.move("rec", 5, 10, 50, 50);

    assertEquals(0, animationModel.getShape(5, "rec").getPosition().getX());
    assertEquals(0, animationModel.getShape(5, "rec").getPosition().getY());

    assertEquals(20, animationModel.getShape(7, "rec").getPosition().getX());
    assertEquals(20, animationModel.getShape(7, "rec").getPosition().getY());

    assertEquals(50, animationModel.getShape(10, "rec").getPosition().getX());
    assertEquals(50, animationModel.getShape(10, "rec").getPosition().getY());
  }

  @Test
  public void testMoveOneShapeMoveTwoConsecutiveTimes() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.addShape(rec, "rec", 5);

    animationModel.move("rec", 5, 10, 50, 50);
    animationModel.move("rec", 10, 15, 150, 150);

    assertEquals(0, animationModel.getShape(5, "rec").getPosition().getX());
    assertEquals(0, animationModel.getShape(5, "rec").getPosition().getY());

    assertEquals(50, animationModel.getShape(10, "rec").getPosition().getX());
    assertEquals(50, animationModel.getShape(10, "rec").getPosition().getY());

    assertEquals(150, animationModel.getShape(15, "rec").getPosition().getX());
    assertEquals(150, animationModel.getShape(15, "rec").getPosition().getY());

    assertEquals(150, animationModel.getShape(20, "rec").getPosition().getX());
    assertEquals(150, animationModel.getShape(20, "rec").getPosition().getY());
  }

  @Test
  public void testMoveOneShapeMoveTwoNonConsecutiveTimes() {
    animationModel = new AnimationModel();
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.declareShape("rec", "rectangle");
    animationModel.addShape(rec, "rec", 5);

    animationModel.move("rec", 5, 10, 50, 50);
    animationModel.move("rec", 15, 20, 150, 150);

    assertEquals(0, animationModel.getShape(5, "rec").getPosition().getX());
    assertEquals(0, animationModel.getShape(5, "rec").getPosition().getY());

    assertEquals(50, animationModel.getShape(10, "rec").getPosition().getX());
    assertEquals(50, animationModel.getShape(10, "rec").getPosition().getY());

    assertEquals(50, animationModel.getShape(15, "rec").getPosition().getX());
    assertEquals(50, animationModel.getShape(15, "rec").getPosition().getY());

    assertEquals(150, animationModel.getShape(20, "rec").getPosition().getX());
    assertEquals(150, animationModel.getShape(20, "rec").getPosition().getY());

    assertEquals(150, animationModel.getShape(25, "rec").getPosition().getX());
    assertEquals(150, animationModel.getShape(25, "rec").getPosition().getY());
  }

  @Test
  public void testMoveTwoShapeMove() {
    animationModel = new AnimationModel();
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    AnimationShape oval = new EllipseShape(new Posn2D(200, 200),
            new ShapeColor(255, 255, 255), 20, 20);
    animationModel.declareShape("rec", "rectangle");
    animationModel.declareShape("oval", "ellipse");
    animationModel.addShape(rec, "rec", 5);
    animationModel.addShape(oval, "oval", 5);

    animationModel.move("rec", 5, 10, 50, 50);
    animationModel.move("rec", 15, 20, 150, 150);
    animationModel.move("oval", 10, 15, 150, 150);
    animationModel.move("oval", 15, 20, 250, 250);

    assertEquals(0, animationModel.getShape(5, "rec").getPosition().getX());
    assertEquals(0, animationModel.getShape(5, "rec").getPosition().getY());
    assertEquals(200, animationModel.getShape(5, "oval").getPosition().getX());
    assertEquals(200, animationModel.getShape(5, "oval").getPosition().getY());

    assertEquals(50, animationModel.getShape(10, "rec").getPosition().getX());
    assertEquals(50, animationModel.getShape(10, "rec").getPosition().getY());
    assertEquals(200, animationModel.getShape(10, "oval").getPosition().getX());
    assertEquals(200, animationModel.getShape(10, "oval").getPosition().getY());

    assertEquals(50, animationModel.getShape(15, "rec").getPosition().getX());
    assertEquals(50, animationModel.getShape(15, "rec").getPosition().getY());
    assertEquals(150, animationModel.getShape(15, "oval").getPosition().getX());
    assertEquals(150, animationModel.getShape(15, "oval").getPosition().getY());

    assertEquals(150, animationModel.getShape(20, "rec").getPosition().getX());
    assertEquals(150, animationModel.getShape(20, "rec").getPosition().getY());
    assertEquals(250, animationModel.getShape(20, "oval").getPosition().getX());
    assertEquals(250, animationModel.getShape(20, "oval").getPosition().getY());

    assertEquals(150, animationModel.getShape(25, "rec").getPosition().getX());
    assertEquals(150, animationModel.getShape(25, "rec").getPosition().getY());
    assertEquals(250, animationModel.getShape(25, "oval").getPosition().getX());
    assertEquals(250, animationModel.getShape(25, "oval").getPosition().getY());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveNullName() {
    animationModel = new AnimationModel();
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.declareShape("rec", "rectangle");
    animationModel.addShape(rec, "rec", 5);

    animationModel.move(null, 5, 10, 50, 50);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveEmptyStringName() {
    animationModel = new AnimationModel();
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.declareShape("rec", "rectangle");
    animationModel.addShape(rec, "rec", 5);

    animationModel.move("", 5, 10, 50, 50);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveNameDoesNotExist() {
    animationModel = new AnimationModel();
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.declareShape("rec", "rectangle");
    animationModel.addShape(rec, "rec", 5);

    animationModel.move("rectangle", 5, 10, 50, 50);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveNameCalledBeforeAdd() {
    animationModel = new AnimationModel();
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.declareShape("rec", "rectangle");
    animationModel.addShape(rec, "rec", 5);

    animationModel.move("rec", 3, 10, 50, 50);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveTimeLessThanOne() {
    animationModel = new AnimationModel();
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.declareShape("rec", "rectangle");
    animationModel.addShape(rec, "rec", 5);

    animationModel.move("rec", -5, 0, 50, 50);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testMoveStartingGreaterThanEnding() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.addShape(rec, "rec", 5);

    animationModel.move("rec", 10, 5, 50, 50);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testMoveOverlapped() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.addShape(rec, "rec", 5);

    animationModel.move("rec", 5, 10, 50, 50);
    animationModel.move("rec", 7, 12, 50, 50);
  }


  @Test
  public void testChangeColorOneShape() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.addShape(rec, "rec", 5);

    animationModel.changeColor("rec", 5,
            new ShapeColor(100, 100, 100), 10);

    assertEquals(0, animationModel.getShape(5, "rec").getShapeColor().getR());
    assertEquals(0, animationModel.getShape(5, "rec").getShapeColor().getG());
    assertEquals(0, animationModel.getShape(5, "rec").getShapeColor().getB());

    assertEquals(40, animationModel.getShape(7, "rec").getShapeColor().getR());
    assertEquals(40, animationModel.getShape(7, "rec").getShapeColor().getG());
    assertEquals(40, animationModel.getShape(7, "rec").getShapeColor().getB());

    assertEquals(100, animationModel.getShape(10, "rec").getShapeColor().getR());
    assertEquals(100, animationModel.getShape(10, "rec").getShapeColor().getG());
    assertEquals(100, animationModel.getShape(10, "rec").getShapeColor().getB());

    assertEquals(100, animationModel.getShape(15, "rec").getShapeColor().getR());
    assertEquals(100, animationModel.getShape(15, "rec").getShapeColor().getG());
    assertEquals(100, animationModel.getShape(15, "rec").getShapeColor().getB());
  }

  @Test
  public void testChangeColorOneShapeAtTwoConsecutiveTime() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.addShape(rec, "rec", 5);

    animationModel.changeColor("rec", 5,
            new ShapeColor(100, 100, 100), 10);
    animationModel.changeColor("rec", 10,
            new ShapeColor(50, 50, 50), 15);

    assertEquals(0, animationModel.getShape(5, "rec").getShapeColor().getR());
    assertEquals(0, animationModel.getShape(5, "rec").getShapeColor().getG());
    assertEquals(0, animationModel.getShape(5, "rec").getShapeColor().getB());

    assertEquals(40, animationModel.getShape(7, "rec").getShapeColor().getR());
    assertEquals(40, animationModel.getShape(7, "rec").getShapeColor().getG());
    assertEquals(40, animationModel.getShape(7, "rec").getShapeColor().getB());

    assertEquals(100, animationModel.getShape(10, "rec").getShapeColor().getR());
    assertEquals(100, animationModel.getShape(10, "rec").getShapeColor().getG());
    assertEquals(100, animationModel.getShape(10, "rec").getShapeColor().getB());

    assertEquals(50, animationModel.getShape(15, "rec").getShapeColor().getR());
    assertEquals(50, animationModel.getShape(15, "rec").getShapeColor().getG());
    assertEquals(50, animationModel.getShape(15, "rec").getShapeColor().getB());

    assertEquals(50, animationModel.getShape(20, "rec").getShapeColor().getR());
    assertEquals(50, animationModel.getShape(20, "rec").getShapeColor().getG());
    assertEquals(50, animationModel.getShape(20, "rec").getShapeColor().getB());
  }

  @Test
  public void testChangeColorOneShapeAtTwoNonConsecutiveTime() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.addShape(rec, "rec", 5);

    animationModel.changeColor("rec", 5,
            new ShapeColor(100, 100, 100), 10);
    animationModel.changeColor("rec", 15,
            new ShapeColor(50, 50, 50), 20);

    assertEquals(0, animationModel.getShape(5, "rec").getShapeColor().getR());
    assertEquals(0, animationModel.getShape(5, "rec").getShapeColor().getG());
    assertEquals(0, animationModel.getShape(5, "rec").getShapeColor().getB());

    assertEquals(100, animationModel.getShape(10, "rec").getShapeColor().getR());
    assertEquals(100, animationModel.getShape(10, "rec").getShapeColor().getG());
    assertEquals(100, animationModel.getShape(10, "rec").getShapeColor().getB());

    assertEquals(100, animationModel.getShape(15, "rec").getShapeColor().getR());
    assertEquals(100, animationModel.getShape(15, "rec").getShapeColor().getG());
    assertEquals(100, animationModel.getShape(15, "rec").getShapeColor().getB());

    assertEquals(50, animationModel.getShape(20, "rec").getShapeColor().getR());
    assertEquals(50, animationModel.getShape(20, "rec").getShapeColor().getG());
    assertEquals(50, animationModel.getShape(20, "rec").getShapeColor().getB());

    assertEquals(50, animationModel.getShape(25, "rec").getShapeColor().getR());
    assertEquals(50, animationModel.getShape(25, "rec").getShapeColor().getG());
    assertEquals(50, animationModel.getShape(25, "rec").getShapeColor().getB());
  }

  @Test
  public void testChangeColorTwoShape() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    animationModel.declareShape("oval", "ellipse");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    AnimationShape oval = new EllipseShape(new Posn2D(200, 200),
            new ShapeColor(255, 255, 255), 20, 20);
    animationModel.addShape(rec, "rec", 5);
    animationModel.addShape(oval, "oval", 5);

    animationModel.changeColor("rec", 5,
            new ShapeColor(100, 100, 100), 10);
    animationModel.changeColor("rec", 15,
            new ShapeColor(50, 50, 50), 20);
    animationModel.changeColor("oval", 5,
            new ShapeColor(200, 100, 200), 10);
    animationModel.changeColor("oval", 10,
            new ShapeColor(150, 150, 150), 15);

    assertEquals(0, animationModel.getShape(5, "rec").getShapeColor().getR());
    assertEquals(0, animationModel.getShape(5, "rec").getShapeColor().getG());
    assertEquals(0, animationModel.getShape(5, "rec").getShapeColor().getB());
    assertEquals(255, animationModel.getShape(5, "oval").getShapeColor().getR());
    assertEquals(255, animationModel.getShape(5, "oval").getShapeColor().getG());
    assertEquals(255, animationModel.getShape(5, "oval").getShapeColor().getB());

    assertEquals(100, animationModel.getShape(10, "rec").getShapeColor().getR());
    assertEquals(100, animationModel.getShape(10, "rec").getShapeColor().getG());
    assertEquals(100, animationModel.getShape(10, "rec").getShapeColor().getB());
    assertEquals(200, animationModel.getShape(10, "oval").getShapeColor().getR());
    assertEquals(100, animationModel.getShape(10, "oval").getShapeColor().getG());
    assertEquals(200, animationModel.getShape(10, "oval").getShapeColor().getB());

    assertEquals(100, animationModel.getShape(15, "rec").getShapeColor().getR());
    assertEquals(100, animationModel.getShape(15, "rec").getShapeColor().getG());
    assertEquals(100, animationModel.getShape(15, "rec").getShapeColor().getB());
    assertEquals(150, animationModel.getShape(15, "oval").getShapeColor().getR());
    assertEquals(150, animationModel.getShape(15, "oval").getShapeColor().getG());
    assertEquals(150, animationModel.getShape(15, "oval").getShapeColor().getB());

    assertEquals(50, animationModel.getShape(20, "rec").getShapeColor().getR());
    assertEquals(50, animationModel.getShape(20, "rec").getShapeColor().getG());
    assertEquals(50, animationModel.getShape(20, "rec").getShapeColor().getB());
    assertEquals(150, animationModel.getShape(20, "oval").getShapeColor().getR());
    assertEquals(150, animationModel.getShape(20, "oval").getShapeColor().getG());
    assertEquals(150, animationModel.getShape(20, "oval").getShapeColor().getB());

    assertEquals(50, animationModel.getShape(25, "rec").getShapeColor().getR());
    assertEquals(50, animationModel.getShape(25, "rec").getShapeColor().getG());
    assertEquals(50, animationModel.getShape(25, "rec").getShapeColor().getB());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testChangeColorNullName() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.addShape(rec, "rec", 5);

    animationModel.changeColor(null, 5,
            new ShapeColor(100, 100, 100), 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testChangeColorEmptyStringName() {
    animationModel = new AnimationModel();
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.addShape(rec, "rec", 5);

    animationModel.changeColor("", 5,
            new ShapeColor(100, 100, 100), 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testChangeColorNameDoesNotExist() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.addShape(rec, "rec", 5);

    animationModel.changeColor("rectangle", 5,
            new ShapeColor(100, 100, 100), 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testChangeColorNameExistTimeBeforeAdd() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.addShape(rec, "rec", 5);

    animationModel.changeColor("rec", 3,
            new ShapeColor(100, 100, 100), 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testChangeColorTimeNegative() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.addShape(rec, "rec", 5);

    animationModel.changeColor("rec", -5,
            new ShapeColor(100, 100, 100), 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testChangeColorStartingGreaterThanEnding() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.addShape(rec, "rec", 5);

    animationModel.changeColor("rectangle", 10,
            new ShapeColor(100, 100, 100), 5);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testChangeColorInvalidNewColor() {
    animationModel = new AnimationModel();
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.addShape(rec, "rec", 5);

    animationModel.changeColor("rectangle", 5,
            new ShapeColor(300, -50, 0), 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testChangeColorOverlapped() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.addShape(rec, "rec", 5);

    animationModel.changeColor("rectangle", 5,
            new ShapeColor(100, 100, 100), 10);
    animationModel.changeColor("rectangle", 7,
            new ShapeColor(50, 50, 50), 12);
  }


  @Test
  public void testChangeSize() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.addShape(rec, "rec", 5);
    animationModel.changeSize("rec", 5, 100, 100, 10);

    assertEquals(10, animationModel.getShape(5, "rec").getHeight());
    assertEquals(10, animationModel.getShape(5, "rec").getWidth());
    assertEquals(46, animationModel.getShape(7, "rec").getHeight());
    assertEquals(46, animationModel.getShape(7, "rec").getWidth());
    assertEquals(100, animationModel.getShape(10, "rec").getHeight());
    assertEquals(100, animationModel.getShape(10, "rec").getWidth());
  }

  @Test
  public void testChangeSizeAtConsecutiveTime() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.addShape(rec, "rec", 5);
    animationModel.changeSize("rec", 5, 100, 100, 10);
    animationModel.changeSize("rec", 10, 50, 50, 15);

    assertEquals(10, animationModel.getShape(5, "rec").getHeight());
    assertEquals(10, animationModel.getShape(5, "rec").getWidth());
    assertEquals(46, animationModel.getShape(7, "rec").getHeight());
    assertEquals(46, animationModel.getShape(7, "rec").getWidth());
    assertEquals(100, animationModel.getShape(10, "rec").getHeight());
    assertEquals(100, animationModel.getShape(10, "rec").getWidth());
    assertEquals(80, animationModel.getShape(12, "rec").getHeight());
    assertEquals(80, animationModel.getShape(12, "rec").getWidth());
    assertEquals(50, animationModel.getShape(15, "rec").getHeight());
    assertEquals(50, animationModel.getShape(15, "rec").getWidth());
  }

  @Test
  public void testChangeSizeAtNonConsecutiveTime() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.addShape(rec, "rec", 5);
    animationModel.changeSize("rec", 5, 100, 100, 10);
    animationModel.changeSize("rec", 15, 50, 50, 20);

    assertEquals(10, animationModel.getShape(5, "rec").getHeight());
    assertEquals(10, animationModel.getShape(5, "rec").getWidth());
    assertEquals(46, animationModel.getShape(7, "rec").getHeight());
    assertEquals(46, animationModel.getShape(7, "rec").getWidth());
    assertEquals(100, animationModel.getShape(10, "rec").getHeight());
    assertEquals(100, animationModel.getShape(10, "rec").getWidth());
    assertEquals(100, animationModel.getShape(12, "rec").getHeight());
    assertEquals(100, animationModel.getShape(12, "rec").getWidth());
    assertEquals(100, animationModel.getShape(15, "rec").getHeight());
    assertEquals(100, animationModel.getShape(15, "rec").getWidth());
    assertEquals(50, animationModel.getShape(20, "rec").getHeight());
    assertEquals(50, animationModel.getShape(20, "rec").getWidth());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testChangeSizeNullName() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.addShape(rec, "rec", 5);
    animationModel.changeSize(null, 5, 60, 60, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testChangeSizeEmptyStringName() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.addShape(rec, "rec", 5);
    animationModel.changeSize("", 5, 60, 60, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testChangeSizeNameDoesNotExist() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.addShape(rec, "rec", 5);
    animationModel.changeSize("oval", 5, 60, 60, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testChangeSizeTimeBeforeAdd() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.addShape(rec, "rec", 5);
    animationModel.changeSize("rec", 3, 60, 60, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testChangeSizeTimeNegative() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.addShape(rec, "rec", 1);
    animationModel.changeSize("rec", -2, 60, 60, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testChangeSizeStartingGreaterThanEnding() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.addShape(rec, "rec", 1);
    animationModel.changeSize("rec", 10, 60, 60, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testChangeSizeZeroSize() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.addShape(rec, "rec", 1);
    animationModel.changeSize("rec", 5, 0, 0, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testChangeSizeNegativeSize() {
    animationModel = new AnimationModel();
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);
    animationModel.addShape(rec, "rec", 1);
    animationModel.changeSize("rec", 5, -10, -10, 10);
  }

  @Test
  public void testMoveChangeColorUnOrdered() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 0, 0),
            10, 10);

    animationModel.addShape(rec, "rec", 1);
    animationModel.changeColor("rec", 4,
            new ShapeColor(70, 70, 70), 11);
    animationModel.move("rec", 5, 10, 100, 100);
    animationModel.move("rec", 10, 20, 100, 100);
    animationModel.move("rec", 20, 200, 200, 30);

    assertEquals("canvas 0 0 500 500\n"
                    + "shape rec rectangle\n"
                    + "motion rec 1 0 0 10 10 0 0 0 4 0 0 10 10 0 0 0 0 0\n"
                    + "motion rec 4 0 0 10 10 0 0 0 5 0 0 10 10 10 10 10 0 0\n"
                    + "motion rec 5 0 0 10 10 10 10 10 10 100 100 10 10 60 60 60 0 0\n"
                    + "motion rec 10 100 100 10 10 60 60 60 11 100 100 10 10 70 70 70 0 0\n"
                    + "motion rec 11 100 100 10 10 70 70 70 20 100 100 10 10 70 70 70 0 0\n"
                    + "motion rec 20 100 100 10 10 70 70 70 200 200 30 10 10 70 70 70 0 0",
            animationModel.getAnimation());
  }

  @Test
  public void testComplexAction() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    animationModel.declareShape("oval", "ellipse");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    AnimationShape oval = new EllipseShape(new Posn2D(100, 100),
            new ShapeColor(100, 100, 100), 100, 100);
    animationModel.addShape(rec, "rec", 5);
    animationModel.addShape(oval, "oval", 10);

    // action to rec rectangle
    animationModel.move("rec", 5, 10, 200, 200);
    animationModel.move("rec", 10, 30, 200, 200);
    animationModel.move("rec", 30, 40, 300, 300);
    animationModel.move("rec", 40, 45, 300, 300);

    animationModel.changeColor("rec", 5,
            new ShapeColor(0, 0, 0), 7);
    animationModel.changeColor("rec", 7,
            new ShapeColor(100, 100, 100), 12);
    animationModel.changeColor("rec", 12,
            new ShapeColor(100, 100, 100), 35);
    animationModel.changeColor("rec", 35,
            new ShapeColor(255, 255, 255), 45);

    animationModel.changeSize("rec", 5, 150, 150, 25);
    animationModel.changeSize("rec", 25, 150, 150, 45);

    // action to oval oval
    animationModel.move("oval", 10, 15, 400, 200);
    animationModel.move("oval", 15, 28, 50, 10);
    animationModel.move("oval", 28, 45, 300, 300);

    animationModel.changeColor("oval", 10,
            new ShapeColor(0, 0, 0), 33);
    animationModel.changeColor("oval", 33,
            new ShapeColor(80, 131, 12), 37);
    animationModel.changeColor("oval", 37,
            new ShapeColor(0, 0, 255), 45);

    animationModel.changeSize("oval", 10, 150, 150, 25);
    animationModel.changeSize("oval", 25, 150, 150, 26);
    animationModel.changeSize("oval", 26, 900, 20, 45);

    assertEquals("canvas 0 0 500 500\n"
                    + "shape rec rectangle\n"
                    + "motion rec 5 0 0 10 10 0 0 0 7 80 80 24 24 0 0 0 0 0\n"
                    + "motion rec 7 80 80 24 24 0 0 0 10 200 200 45 45 60 60 60 0 0\n"
                    + "motion rec 10 200 200 45 45 60 60 60 12 200 200 59 59 100 100 100 0 0\n"
                    + "motion rec 12 200 200 59 59 100 100 100 25 200 200 150 150 100 100 100 0 0\n"
                    + "motion rec 25 200 200 150 150 100 100 100 "
                    + "30 200 200 150 150 100 100 100 0 0\n"
                    + "motion rec 30 200 200 150 150 100 100 100 "
                    + "35 250 250 150 150 100 100 100 0 0\n"
                    + "motion rec 35 250 250 150 150 100 100 100 "
                    + "40 300 300 150 150 175 175 175 0 0\n"
                    + "motion rec 40 300 300 150 150 175 175 175 "
                    + "45 300 300 150 150 255 255 255 0 0\n"
                    + "shape oval ellipse\n"
                    + "motion oval 10 100 100 100 100 100 100 100 15 400 200 115 115 80 80 80 0 0\n"
                    + "motion oval 15 400 200 115 115 80 80 80 25 140 60 150 150 40 40 40 0 0\n"
                    + "motion oval 25 140 60 150 150 40 40 40 26 114 46 150 150 36 36 36 0 0\n"
                    + "motion oval 26 114 46 150 150 36 36 36 28 50 10 138 228 28 28 28 0 0\n"
                    + "motion oval 28 50 10 138 228 28 28 28 33 120 95 108 423 0 0 0 0 0\n"
                    + "motion oval 33 120 95 108 423 0 0 0 37 176 163 84 579 80 131 12 0 0\n"
                    + "motion oval 37 176 163 84 579 80 131 12 45 300 300 20 900 0 0 255 0 0",
            animationModel.getAnimation());
  }

  @Test
  public void testGetShapeTime() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    animationModel.declareShape("oval", "ellipse");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    AnimationShape oval = new EllipseShape(new Posn2D(100, 100),
            new ShapeColor(100, 100, 100), 100, 100);
    animationModel.addShape(rec, "rec", 5);
    animationModel.addShape(oval, "oval", 10);
    List<AnimationShape> test = new ArrayList<>();
    assertEquals(true, test.equals(animationModel.getShape(4)));
    test.add(rec);
    assertEquals(true, test.equals(animationModel.getShape(5)));
    test.add(oval);
    assertEquals(true, test.equals(animationModel.getShape(10)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetShapeTimeNegativeTime() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    animationModel.addShape(rec, "rec", 0);
    List<AnimationShape> test = new ArrayList<>();
    animationModel.getShape(-1);
  }

  @Test
  public void testGetBackground() {
    animationModel = new AnimationModel();
    assertEquals(new AnimationBackground(0, 0, 500, 500),
            animationModel.getBackground());
  }

  @Test
  public void testGetExistingShape() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    assertEquals(true, animationModel.getExistingShape().containsKey("rec"));
    assertEquals(true, animationModel.getExistingShape().containsValue("rectangle"));
  }

  @Test
  public void testGetTimeLine() {
    animationModel = new AnimationModel();
    animationModel.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    animationModel.addShape(rec, "rec", 0);
    assertEquals(true, animationModel.getTimeline().containsKey("rec"));
    assertEquals(true, animationModel.getTimeline().get("rec").containsKey(0));
    assertEquals(true, animationModel.getTimeline().get("rec").containsValue(rec));
  }

  @Test
  public void testSetBackground() {
    animationModel = new AnimationModel();
    animationModel.setBackground(0, 0, 100, 100);
    assertEquals(true, animationModel.getBackground()
            .equals(new AnimationBackground(0, 0, 100, 100)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetBackgroundNegativeSize() {
    animationModel = new AnimationModel();
    animationModel.setBackground(0, 0, -5, -5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetBackgroundZeroSize() {
    animationModel = new AnimationModel();
    animationModel.setBackground(0, 0, 0, 0);
  }

  @Test
  public void testSetExistingShape() {
    animationModel = new AnimationModel();
    LinkedHashMap<String, String> shapes = new LinkedHashMap<>();
    shapes.put("rec", "rectangle");
    shapes.put("ellipse", "ellipse");
    animationModel.setExistingShape(shapes);
    assertEquals(true, animationModel.getExistingShape().equals(shapes));
  }

  @Test
  public void testSetTimeline() {
    animationModel = new AnimationModel();
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    HashMap<String, TreeMap<Integer, AnimationShape>> timeline = new HashMap<>();
    TreeMap<Integer, AnimationShape> operation = new TreeMap<>();
    operation.put(5, rec);
    timeline.put("rec", operation);
    animationModel.setTimeline(timeline);
    assertEquals(true, animationModel.getTimeline().equals(timeline));
  }

  @Test
  public void testBuilderSetBounds() {
    AnimationBuilder builder = new AnimationModel.Builder();
    builder.setBounds(0, 0, 100, 100);
    AnimationOperations model2 = (AnimationOperations) builder.build();
    assertTrue(model2.getBackground()
            .equals(new AnimationBackground(0, 0, 100, 100)));
  }

  @Test
  public void testBuilderDeclareShape() {
    AnimationBuilder builder = new AnimationModel.Builder();
    builder.declareShape("rec", "rectangle");
    AnimationOperations model2 = (AnimationOperations) builder.build();
    HashMap<String, String> shape = new HashMap<>();
    shape.put("rec", "rectangle");
    assertTrue(model2.getExistingShape().equals(shape));
  }

  @Test
  public void testBuilderAddMotionNoPreviousMotion() {
    AnimationBuilder builder = new AnimationModel.Builder();
    builder.declareShape("rec", "rectangle");
    builder.addMotion("rec", 1, 5, 5, 20, 50, 255, 255,
            255, 5, 10, 5, 20, 50, 255, 255, 255, 0, 0);
    AnimationOperations model2 = (AnimationOperations) builder.build();
    assertEquals("canvas 0 0 500 500\n"
                    + "shape rec rectangle\n"
                    + "motion rec 1 5 5 20 50 255 255 255 5 10 5 20 50 255 255 255 0 0",
            model2.getAnimation());
  }

  @Test
  public void testBuilderAddMotionWithPreviousMotion() {
    AnimationBuilder builder = new AnimationModel.Builder();
    builder.declareShape("rec", "rectangle");
    builder.addMotion("rec", 1, 5, 5, 20, 50, 255, 255,
            255, 5, 10, 5, 20, 50, 255, 255, 255, 0, 0);
    builder.addMotion("rec", 6, 10, 5, 20, 50, 255, 255,
            255, 12, 10, 5, 30, 50, 255, 255, 255, 0, 0);
    AnimationOperations model2 = (AnimationOperations) builder.build();
    assertEquals("canvas 0 0 500 500\n"
            + "shape rec rectangle\n"
            + "motion rec 1 5 5 20 50 255 255 255 5 10 5 20 50 255 255 255 0 0\n"
            + "motion rec 5 10 5 20 50 255 255 255 6 10 5 20 50 255 255 255 0 0\n"
            + "motion rec 6 10 5 20 50 255 255 255 12 10 5 30 50 255 255 255 0 0",
            model2.getAnimation());
  }

  @Test
  public void testBuilderAddMotionWithMultipleMotion() {
    AnimationBuilder builder = new AnimationModel.Builder();
    builder.declareShape("rec", "rectangle");
    builder.addMotion("rec", 1, 5, 5, 20, 50, 255, 255,
            255, 5, 10, 5, 20, 50, 255, 255, 255, 0, 0);
    builder.addMotion("rec", 6, 10, 5, 20, 50, 255, 255,
            255, 12, 10, 5, 30, 50, 0, 255, 255, 0, 0);
    AnimationOperations model2 = (AnimationOperations) builder.build();
    assertEquals("canvas 0 0 500 500\n"
            + "shape rec rectangle\n"
            + "motion rec 1 5 5 20 50 255 255 255 5 10 5 20 50 255 255 255 0 0\n"
            + "motion rec 5 10 5 20 50 255 255 255 6 10 5 20 50 255 255 255 0 0\n"
            + "motion rec 6 10 5 20 50 255 255 255 12 10 5 30 50 0 255 255 0 0",
            model2.getAnimation());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBuilderAddMotion() {
    AnimationBuilder builder = new AnimationModel.Builder();
    builder.addMotion("rec", 5, 5, 5, 20, 50, 255, 255,
            255, 5, 10, 5, 20, 50, 255, 255, 255, 0, 0);
    builder.addMotion("rec", 6, 10, 5, 20, 50, 255, 255,
            255, 12, 10, 5, 30, 50, 0, 255, 255, 0, 0);
  }

  @Test
  public void testBuilderAddKeyframe() {
    AnimationBuilder builder = new AnimationModel.Builder();
    builder.declareShape("rec", "rectangle");
    builder.addKeyframe("rec", 3, 15, 15, 100, 150, 255, 0, 255, 0);
    AnimationOperations model2 = (AnimationOperations) builder.build();
    RectangleShape rec = new RectangleShape(new Posn2D(15, 15), new ShapeColor(255, 0,
            255), 150, 100);

    HashMap<String, TreeMap<Integer, AnimationShape>> tempTimeline = new HashMap<>();
    TreeMap<Integer, AnimationShape> temp = new TreeMap<>();
    temp.put(3, rec);
    tempTimeline.put("rec", temp);
    assertTrue(model2.getTimeline().equals(tempTimeline));
  }

  @Test
  public void testBuilderSetLayer() {
    AnimationBuilder builder = new AnimationModel.Builder();
    builder.declareShape("rec", "rectangle");
    AnimationOperations model2 = (AnimationOperations) builder.build();
    List<String> shape = new ArrayList<>();
    shape.add("rec");
    TreeMap<Integer, List<String>> temp = new TreeMap<>();
    temp.put(0, shape);
    assertTrue(model2.getLayers().equals(temp));
    builder.setLayer("rec", 5);
    temp.remove(0);
    temp.put(5, shape);
    assertTrue(model2.getLayers().equals(temp));
  }

  @Test
  public void testMakeShape() {
    AnimationOperations model = new AnimationModel();
    model.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    assertEquals(rec, model.makeShape("rec", new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMakeShapeNullName() {
    AnimationOperations model = new AnimationModel();
    model.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    model.makeShape(null, new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMakeShapeNameEmptyString() {
    AnimationOperations model = new AnimationModel();
    model.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    model.makeShape("", new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMakeShapeNameDoesNotExist() {
    AnimationOperations model = new AnimationModel();
    model.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    model.makeShape("hi", new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMakeShapePosNull() {
    AnimationOperations model = new AnimationModel();
    model.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    model.makeShape("rec", null, new ShapeColor(0, 0, 0),
            10, 10, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMakeShapeColorNull() {
    AnimationOperations model = new AnimationModel();
    model.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    model.makeShape("rec", new Posn2D(0, 0), null, 10, 10, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMakeShapeInvalidSize() {
    AnimationOperations model = new AnimationModel();
    model.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    model.makeShape("rec", new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 0, 0, 0);
  }

  @Test
  public void testModifyFrame() {
    AnimationOperations model = new AnimationModel();
    model.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(255, 0, 0), 50, 50);
    AnimationShape cRec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);

    model.addShape(rec, "rec", 0);
    model.changeColor("rec", 0, new ShapeColor(0, 255, 0), 20);
    model.changeColor("rec", 20, new ShapeColor(0, 0, 255), 40);
    model.modifyFrame("rec", 20, cRec);
    assertEquals(cRec, model.getShape(20, "rec"));
    model.modifyFrame("rec", 5, cRec);
    assertEquals(cRec, model.getShape(5, "rec"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModifyFrameNameNull() {
    AnimationOperations model = new AnimationModel();
    model.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(255, 0, 0), 50, 50);
    AnimationShape cRec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);

    model.addShape(rec, "rec", 0);
    model.changeColor("rec", 0, new ShapeColor(0, 255, 0), 20);
    model.changeColor("rec", 20, new ShapeColor(0, 0, 255), 40);
    model.modifyFrame(null, 20, cRec);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModifyFrameNameEmptyString() {
    AnimationOperations model = new AnimationModel();
    model.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(255, 0, 0), 50, 50);
    AnimationShape cRec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);

    model.addShape(rec, "rec", 0);
    model.changeColor("rec", 0, new ShapeColor(0, 255, 0), 20);
    model.changeColor("rec", 20, new ShapeColor(0, 0, 255), 40);
    model.modifyFrame("", 20, cRec);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModifyFrameNameDoesNotExist() {
    AnimationOperations model = new AnimationModel();
    model.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(255, 0, 0), 50, 50);
    AnimationShape cRec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);

    model.addShape(rec, "rec", 0);
    model.changeColor("rec", 0, new ShapeColor(0, 255, 0), 20);
    model.changeColor("rec", 20, new ShapeColor(0, 0, 255), 40);
    model.modifyFrame("hello", 20, cRec);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModifyFrameOutOfTime() {
    AnimationOperations model = new AnimationModel();
    model.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(255, 0, 0), 50, 50);
    AnimationShape cRec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);

    model.addShape(rec, "rec", 0);
    model.changeColor("rec", 0, new ShapeColor(0, 255, 0), 20);
    model.changeColor("rec", 20, new ShapeColor(0, 0, 255), 40);
    model.modifyFrame("rec", 50, cRec);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModifyFrameNegativeTime() {
    AnimationOperations model = new AnimationModel();
    model.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(255, 0, 0), 50, 50);
    AnimationShape cRec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);

    model.addShape(rec, "rec", 0);
    model.changeColor("rec", 0, new ShapeColor(0, 255, 0), 20);
    model.changeColor("rec", 20, new ShapeColor(0, 0, 255), 40);
    model.modifyFrame("rec", -5, cRec);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModifyFrameShapeNull() {
    AnimationOperations model = new AnimationModel();
    model.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(255, 0, 0), 50, 50);
    AnimationShape cRec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);

    model.addShape(rec, "rec", 0);
    model.changeColor("rec", 0, new ShapeColor(0, 255, 0), 20);
    model.changeColor("rec", 20, new ShapeColor(0, 0, 255), 40);
    model.modifyFrame("rec", 20, null);
  }

  @Test
  public void testRemoveFrameFirst() {
    AnimationOperations model = new AnimationModel();
    model.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(255, 0, 0), 50, 50);
    AnimationShape cRec = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(0, 255, 0), 50, 50);
    model.addShape(rec, "rec", 0);
    model.changeColor("rec", 0, new ShapeColor(0, 255, 0), 20);
    model.changeColor("rec", 20, new ShapeColor(0, 0, 255), 40);
    model.removeFrame("rec", 0);
    assertEquals(false, model.getTimeline().get("rec").containsKey(0));
    assertEquals(cRec, model.getShape(20, "rec"));
  }

  @Test
  public void testRemoveFrameMid() {
    AnimationOperations model = new AnimationModel();
    model.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(255, 0, 0), 50, 50);
    AnimationShape cRec = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(0, 255, 0), 50, 50);
    model.addShape(rec, "rec", 0);
    model.changeColor("rec", 0, new ShapeColor(0, 255, 0), 20);
    model.changeColor("rec", 20, new ShapeColor(0, 0, 255), 40);
    model.removeFrame("rec", 20);
    assertEquals(false, model.getTimeline().get("rec").containsKey(20));
  }

  @Test
  public void testRemoveFrameLast() {
    AnimationOperations model = new AnimationModel();
    model.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(255, 0, 0), 50, 50);
    AnimationShape cRec = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(0, 255, 0), 50, 50);
    model.addShape(rec, "rec", 0);
    model.changeColor("rec", 0, new ShapeColor(0, 255, 0), 20);
    model.changeColor("rec", 20, new ShapeColor(0, 0, 255), 40);
    model.removeFrame("rec", 40);
    assertEquals(false, model.getTimeline().get("rec").containsKey(40));
    assertEquals(cRec, model.getShape(20, "rec"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveFrameNameNull() {
    AnimationOperations model = new AnimationModel();
    model.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(255, 0, 0), 50, 50);
    AnimationShape cRec = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(0, 255, 0), 50, 50);
    model.addShape(rec, "rec", 0);
    model.changeColor("rec", 0, new ShapeColor(0, 255, 0), 20);
    model.changeColor("rec", 20, new ShapeColor(0, 0, 255), 40);
    model.removeFrame(null, 40);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveFrameNameEmptyString() {
    AnimationOperations model = new AnimationModel();
    model.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(255, 0, 0), 50, 50);
    AnimationShape cRec = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(0, 255, 0), 50, 50);
    model.addShape(rec, "rec", 0);
    model.changeColor("rec", 0, new ShapeColor(0, 255, 0), 20);
    model.changeColor("rec", 20, new ShapeColor(0, 0, 255), 40);
    model.removeFrame("", 40);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveFrameNameDoesNotExist() {
    AnimationOperations model = new AnimationModel();
    model.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(255, 0, 0), 50, 50);
    AnimationShape cRec = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(0, 255, 0), 50, 50);
    model.addShape(rec, "rec", 0);
    model.changeColor("rec", 0, new ShapeColor(0, 255, 0), 20);
    model.changeColor("rec", 20, new ShapeColor(0, 0, 255), 40);
    model.removeFrame("hello", 40);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveFrameTimeDoesNotFrame() {
    AnimationOperations model = new AnimationModel();
    model.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(255, 0, 0), 50, 50);
    AnimationShape cRec = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(0, 255, 0), 50, 50);
    model.addShape(rec, "rec", 0);
    model.changeColor("rec", 0, new ShapeColor(0, 255, 0), 20);
    model.changeColor("rec", 20, new ShapeColor(0, 0, 255), 40);
    model.removeFrame("rec", 10);
  }

  @Test
  public void testAddFrameOutOfAction() {
    AnimationOperations model = new AnimationModel();
    model.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(255, 0, 0), 50, 50);
    AnimationShape cRec = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(0, 255, 0), 50, 50);
    model.addShape(rec, "rec", 0);
    model.changeColor("rec", 0, new ShapeColor(0, 255, 0), 20);
    model.changeColor("rec", 20, new ShapeColor(0, 0, 255), 40);
    model.addFrame("rec", 60, cRec);
    assertEquals(true, model.getTimeline().get("rec").containsKey(60));
    assertEquals(cRec, model.getShape(60, "rec"));
  }

  @Test
  public void testAddFrameDuringAction() {
    AnimationOperations model = new AnimationModel();
    model.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(255, 0, 0), 50, 50);
    AnimationShape cRec = new RectangleShape(new Posn2D(60, 10),
            new ShapeColor(0, 255, 255), 50, 50);
    model.addShape(rec, "rec", 0);
    model.changeColor("rec", 0, new ShapeColor(0, 255, 0), 20);
    model.changeColor("rec", 20, new ShapeColor(0, 0, 255), 40);
    model.addFrame("rec", 5, cRec);
    assertEquals(true, model.getTimeline().get("rec").containsKey(5));
    assertEquals(cRec, model.getShape(5, "rec"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddFrameNameNull() {
    AnimationOperations model = new AnimationModel();
    model.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(255, 0, 0), 50, 50);
    AnimationShape cRec = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(0, 255, 0), 50, 50);
    model.addShape(rec, "rec", 0);
    model.changeColor("rec", 0, new ShapeColor(0, 255, 0), 20);
    model.changeColor("rec", 20, new ShapeColor(0, 0, 255), 40);
    model.addFrame(null, 60, cRec);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddFrameNameEmptyString() {
    AnimationOperations model = new AnimationModel();
    model.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(255, 0, 0), 50, 50);
    AnimationShape cRec = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(0, 255, 0), 50, 50);
    model.addShape(rec, "rec", 0);
    model.changeColor("rec", 0, new ShapeColor(0, 255, 0), 20);
    model.changeColor("rec", 20, new ShapeColor(0, 0, 255), 40);
    model.addFrame("", 60, cRec);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddFrameNameDoesNotExist() {
    AnimationOperations model = new AnimationModel();
    model.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(255, 0, 0), 50, 50);
    AnimationShape cRec = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(0, 255, 0), 50, 50);
    model.addShape(rec, "rec", 0);
    model.changeColor("rec", 0, new ShapeColor(0, 255, 0), 20);
    model.changeColor("rec", 20, new ShapeColor(0, 0, 255), 40);
    model.addFrame("hello", 60, cRec);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddFrameTimeAtAlreadyExistedFrame() {
    AnimationOperations model = new AnimationModel();
    model.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(255, 0, 0), 50, 50);
    AnimationShape cRec = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(0, 255, 0), 50, 50);
    model.addShape(rec, "rec", 0);
    model.changeColor("rec", 0, new ShapeColor(0, 255, 0), 20);
    model.changeColor("rec", 20, new ShapeColor(0, 0, 255), 40);
    model.addFrame("rec", 20, cRec);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddFrameTimeNegative() {
    AnimationOperations model = new AnimationModel();
    model.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(255, 0, 0), 50, 50);
    AnimationShape cRec = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(0, 255, 0), 50, 50);
    model.addShape(rec, "rec", 0);
    model.changeColor("rec", 0, new ShapeColor(0, 255, 0), 20);
    model.changeColor("rec", 20, new ShapeColor(0, 0, 255), 40);
    model.addFrame("rec", -5, cRec);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddFrameShapeNull() {
    AnimationOperations model = new AnimationModel();
    model.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(255, 0, 0), 50, 50);
    AnimationShape cRec = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(0, 255, 0), 50, 50);
    model.addShape(rec, "rec", 0);
    model.changeColor("rec", 0, new ShapeColor(0, 255, 0), 20);
    model.changeColor("rec", 20, new ShapeColor(0, 0, 255), 40);
    model.addFrame("rec", 60, null);
  }

  @Test
  public void testGetLayers() {
    AnimationOperations model = new AnimationModel();
    model.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(255, 0, 0), 50, 50);
    model.addShape(rec, "rec", 0);

    List<String> layer = new ArrayList<>();
    layer.add("rec");

    TreeMap<Integer, List<String>> temp = new TreeMap<>();
    temp.put(0, layer);

    assertEquals(temp, model.getLayers());
  }

  @Test
  public void testSetLayers() {
    AnimationOperations model = new AnimationModel();
    model.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(255, 0, 0), 50, 50);
    model.addShape(rec, "rec", 0);
    model.setLayer("rec", 5);
    assertEquals(true, model.getLayers().get(5).contains("rec"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetLayersNameNull() {
    AnimationOperations model = new AnimationModel();
    model.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(255, 0, 0), 50, 50);
    model.addShape(rec, "rec", 0);
    model.setLayer(null, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetLayersNameEmptyString() {
    AnimationOperations model = new AnimationModel();
    model.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(255, 0, 0), 50, 50);
    model.addShape(rec, "rec", 0);
    model.setLayer("", 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetLayersNameDoesNotExist() {
    AnimationOperations model = new AnimationModel();
    model.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(255, 0, 0), 50, 50);
    model.addShape(rec, "rec", 0);
    model.setLayer("oval", 5);
  }

  @Test
  public void testChangeDegree() {
    AnimationOperations animationModel = new AnimationModel();
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    animationModel.declareShape("rec", "rectangle");
    animationModel.addShape(rec, "rec", 5);
    animationModel.changeDegree("rec", 5, 90, 15);
    assertEquals(45, animationModel.getShape(10, "rec").getDegree());
    assertEquals(0, animationModel.getShape(5, "rec").getDegree());
    assertEquals(90, animationModel.getShape(15, "rec").getDegree());
  }

  @Test(expected = IllegalArgumentException.class )
  public void testChangeDegreeNameNull() {
    AnimationOperations animationModel = new AnimationModel();
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    animationModel.declareShape("rec", "rectangle");
    animationModel.addShape(rec, "rec", 5);
    animationModel.changeDegree(null, 5, 90, 15);
  }

  @Test(expected = IllegalArgumentException.class )
  public void testChangeDegreeNameEmptyString() {
    AnimationOperations animationModel = new AnimationModel();
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    animationModel.declareShape("rec", "rectangle");
    animationModel.addShape(rec, "rec", 5);
    animationModel.changeDegree("", 5, 90, 15);
  }

  @Test(expected = IllegalArgumentException.class )
  public void testChangeDegreeNameDoesNotExist() {
    AnimationOperations animationModel = new AnimationModel();
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    animationModel.declareShape("rec", "rectangle");
    animationModel.addShape(rec, "rec", 5);
    animationModel.changeDegree("oval", 5, 90, 15);
  }

  @Test(expected = IllegalArgumentException.class )
  public void testChangeDegreeTimeBeforeExist() {
    AnimationOperations animationModel = new AnimationModel();
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    animationModel.declareShape("rec", "rectangle");
    animationModel.addShape(rec, "rec", 5);
    animationModel.changeDegree("rec", 4, 90, 15);
  }

  @Test(expected = IllegalArgumentException.class )
  public void testChangeDegreeTimeNegative() {
    AnimationOperations animationModel = new AnimationModel();
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    animationModel.declareShape("rec", "rectangle");
    animationModel.addShape(rec, "rec", 5);
    animationModel.changeDegree("rec", -5, 90, -15);
  }

  @Test(expected = IllegalArgumentException.class )
  public void testChangeDegreeStartingGreaterThanEnding() {
    AnimationOperations animationModel = new AnimationModel();
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    animationModel.declareShape("rec", "rectangle");
    animationModel.addShape(rec, "rec", 5);
    animationModel.changeDegree("rec", 7, 90, 6);
  }
}