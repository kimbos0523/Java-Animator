import org.junit.Test;

import java.util.HashMap;
import java.util.TreeMap;

import cs3500.animator.controller.AnimationController;
import cs3500.animator.controller.AnimationControllerImpl;
import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.AnimationOperations;
import cs3500.animator.model.AnimationShape;
import cs3500.animator.model.Posn2D;
import cs3500.animator.model.RectangleShape;
import cs3500.animator.model.ShapeColor;
import cs3500.animator.view.AnimationView;
import cs3500.animator.view.CompositeSwingView;

import static org.junit.Assert.assertEquals;

/**
 * Test class for AnimationControllerImpl: unit test to ensure that AnimationControllerImpl operates
 * right way.
 */
public class AnimationControllerImplTest {
  AnimationOperations model;
  AnimationView view;
  AnimationController controller;

  private void initTest() {
    model = new AnimationModel();
    model.setBackground(0, 0, 600, 600);
    model.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(255, 0, 0), 50, 50);
    model.addShape(rec, "rec", 0);
    model.changeColor("rec", 0, new ShapeColor(0, 255, 0), 20);
    model.changeColor("rec", 20, new ShapeColor(0, 0, 255), 40);
    view = new CompositeSwingView("Animator", model);
    controller = new AnimationControllerImpl(view, 5);
    controller.playAnimation();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullView() {
    model = new AnimationModel();
    view = null;
    controller = new AnimationControllerImpl(view, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorZeroSpeed() {
    model = new AnimationModel();
    view = new CompositeSwingView("Animator", model);
    controller = new AnimationControllerImpl(view, 0);
  }

  @Test
  public void testPlayAnimation() {
    initTest();
    controller.playAnimation();
    assertEquals(200, view.getTimer().getDelay());
  }

  @Test
  public void testDeleteShape() {
    initTest();
    controller.deleteShape("rec");
    HashMap<String, TreeMap<Integer, AnimationShape>> test = new HashMap<>();
    assertEquals(test, model.getTimeline());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDeleteShapeNameNull() {
    initTest();
    controller.deleteShape(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDeleteShapeNameEmptyString() {
    initTest();
    controller.deleteShape("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDeleteShapeNameNotExist() {
    initTest();
    controller.deleteShape("hi");
  }

  @Test
  public void testDeleteFrame() {
    initTest();
    controller.deleteFrame("rec", 20);
    TreeMap<Integer, AnimationShape> test = new TreeMap<>();
    AnimationShape rec1 = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(255, 0, 0), 50, 50);
    AnimationShape rec2 = new RectangleShape(new Posn2D(0, 10),
            new ShapeColor(0, 0, 255), 50, 50);
    test.put(0, rec1);
    test.put(40, rec2);
    HashMap<String, TreeMap<Integer, AnimationShape>> time = new HashMap<>();
    time.put("rec", test);
    assertEquals(time, model.getTimeline());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDeleteFrameNameNull() {
    initTest();
    controller.deleteFrame(null, 20);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDeleteFrameNameEmptyString() {
    initTest();
    controller.deleteFrame("", 20);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDeleteFrameNameDoesNotExist() {
    initTest();
    controller.deleteFrame("oval", 20);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDeleteFrameTimeNegative() {
    initTest();
    controller.deleteFrame("rec", -5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDeleteFrameTimeDoesNotFrame() {
    initTest();
    controller.deleteFrame("rec", 5);
  }

  @Test
  public void testAddShape() {
    initTest();
    controller.addShape("ell", "ellipse");
    assertEquals(true, model.getExistingShape().containsKey("ell"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddShapeNameNull() {
    initTest();
    controller.addShape(null, "ellipse");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddShapeNameEmptyString() {
    initTest();
    controller.addShape("", "ellipse");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddShapeTypeNull() {
    initTest();
    controller.addShape("ell", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddShapeTypeEmptyString() {
    initTest();
    controller.addShape("ell", "");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddShapeTypeInvalid() {
    initTest();
    controller.addShape("ell", "Star");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddShapeNameAlreadyExist() {
    initTest();
    controller.addShape("rec", "rectangle");
  }

  @Test
  public void testAddFrame() {
    initTest();
    controller.addFrame("rec", 30, new Posn2D(20, 20),
            new ShapeColor(0, 0, 0), 30, 30, 0);
    assertEquals(true, model.getTimeline().get("rec").containsKey(30));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddFrameNameNull() {
    initTest();
    controller.addFrame(null, 30, new Posn2D(20, 20),
            new ShapeColor(0, 0, 0), 30, 30, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddFrameNameEmptyString() {
    initTest();
    controller.addFrame("", 30, new Posn2D(20, 20),
            new ShapeColor(0, 0, 0), 30, 30, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddFrameNameDoesNotExist() {
    initTest();
    controller.addFrame("oval", 30, new Posn2D(20, 20),
            new ShapeColor(0, 0, 0), 30, 30, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddFrameTimeNegative() {
    initTest();
    controller.addFrame("rec", -5, new Posn2D(20, 20),
            new ShapeColor(0, 0, 0), 30, 30, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddFrameTimeAlreadyExist() {
    initTest();
    controller.addFrame("rec", 20, new Posn2D(20, 20),
            new ShapeColor(0, 0, 0), 30, 30, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddFramePosNull() {
    initTest();
    controller.addFrame("rec", 30, null,
            new ShapeColor(0, 0, 0), 30, 30, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddFrameColorNull() {
    initTest();
    controller.addFrame("rec", 30, new Posn2D(20, 20),
            null, 30, 30, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddFrameSizeInvalid() {
    initTest();
    controller.addFrame("rec", 30, new Posn2D(20, 20),
            new ShapeColor(0, 0, 0), 0, 0, 0);
  }

  @Test
  public void modifyFrame() {
    initTest();
    controller.modifyFrame("rec", 20, new Posn2D(0, 0),
            new ShapeColor(255, 255, 255), 100, 100, 90);
    assertEquals(0, model.getTimeline().get("rec").get(20).getPosition().getX());
    assertEquals(0, model.getTimeline().get("rec").get(20).getPosition().getY());
    assertEquals(255, model.getTimeline().get("rec").get(20).getShapeColor().getR());
    assertEquals(255, model.getTimeline().get("rec").get(20).getShapeColor().getG());
    assertEquals(255, model.getTimeline().get("rec").get(20).getShapeColor().getB());
    assertEquals(100, model.getTimeline().get("rec").get(20).getHeight());
    assertEquals(100, model.getTimeline().get("rec").get(20).getWidth());
    assertEquals(90, model.getTimeline().get("rec").get(20).getDegree());
  }

  @Test(expected = IllegalArgumentException.class)
  public void modifyFrameNameNull() {
    initTest();
    controller.modifyFrame(null, 20, new Posn2D(0, 0),
            new ShapeColor(255, 255, 255), 100, 100, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void modifyFrameNameEmptyString() {
    initTest();
    controller.modifyFrame("", 20, new Posn2D(0, 0),
            new ShapeColor(255, 255, 255), 100, 100, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void modifyFrameNameDoesNotExist() {
    initTest();
    controller.modifyFrame("e1", 20, new Posn2D(0, 0),
            new ShapeColor(255, 255, 255), 100, 100, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void modifyFrameTimeNegative() {
    initTest();
    controller.modifyFrame("rec", -5, new Posn2D(0, 0),
            new ShapeColor(255, 255, 255), 100, 100, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void modifyFrameTimeOutOfFrame() {
    initTest();
    controller.modifyFrame("rec", 70, new Posn2D(0, 0),
            new ShapeColor(255, 255, 255), 100, 100, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void modifyFramePosnNull() {
    initTest();
    controller.modifyFrame("rec", 20, null,
            new ShapeColor(255, 255, 255), 100, 100, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void modifyFrameColorNull() {
    initTest();
    controller.modifyFrame("rec", 20, new Posn2D(0, 0),
            null, 100, 100, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void modifyFrameSizeInvalid() {
    initTest();
    controller.modifyFrame("rec", 20, new Posn2D(0, 0),
            new ShapeColor(255, 255, 255), 0, 0, 0);
  }

  @Test
  public void testFaster() {
    initTest();
    controller.faster();
    assertEquals(166, view.getTimer().getDelay());
  }

  @Test
  public void testSlower() {
    initTest();
    controller.slower();
    assertEquals(250, view.getTimer().getDelay());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSlowerSpeedZero() {
    initTest();
    controller.slower(); // speed: 4
    controller.slower(); // speed: 3
    controller.slower(); // speed: 2
    controller.slower(); // speed: 1
    controller.slower(); // speed: 0
  }

  @Test
  public void testRestart() {
    initTest();
    controller.restart();
    assertEquals(0, view.getTick());
  }

  @Test
  public void testLoop() {
    initTest();
    controller.loop();
    assertEquals(true, view.getIsLoop());
    controller.loop();
    assertEquals(false, view.getIsLoop());
  }

  @Test
  public void testPause() {
    initTest();
    controller.pause();
    assertEquals(false, view.getTimer().isRunning());
  }

  @Test
  public void testResume() {
    initTest();
    controller.resume();
    assertEquals(true, view.getTimer().isRunning());
  }

  @Test
  public void testSave() {
    initTest();
    controller.save("sample50.txt");
    controller.load("sample50.txt");
    assertEquals("canvas 0 0 600 600\n"
            + "shape rec rectangle\n"
            + "motion rec 0 0 10 50 50 255 0 0 20 0 10 50 50 0 255 0 0 0\n"
            + "motion rec 20 0 10 50 50 0 255 0 40 0 10 50 50 0 0 255 0 0", model.getAnimation());
  }

  @Test
  public void testMoveAnimation() {
    initTest();
    controller.moveAnimation(5);
    assertEquals(5, view.getTick());
  }

  @Test
  public void testSetLayer() {
    initTest();
    assertEquals(true, model.getLayers().get(0).contains("rec"));
    assertEquals(false, model.getLayers().containsKey(2));
    controller.setLayer("rec", 2);
    assertEquals(true, model.getLayers().get(2).contains("rec"));
  }
}