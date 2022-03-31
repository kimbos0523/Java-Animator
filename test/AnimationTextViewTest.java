import static org.junit.Assert.assertEquals;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.AnimationOperations;
import cs3500.animator.model.AnimationShape;
import cs3500.animator.model.EllipseShape;
import cs3500.animator.model.Posn2D;
import cs3500.animator.model.RectangleShape;
import cs3500.animator.model.ShapeColor;
import cs3500.animator.view.AnimationTextView;
import cs3500.animator.view.AnimationView;
import org.junit.Test;

/**
 * Test class for AnimationTextView: unit test to ensure that AnimationTextView operates right way.
 */
public class AnimationTextViewTest {

  AnimationOperations model = new AnimationModel();
  AnimationView view;

  @Test
  public void testRenderDeclareShape() {
    model.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0),
        new ShapeColor(0, 0, 0), 10, 10);
    model.addShape(rec, "rec", 5);
    view = new AnimationTextView(model, new StringBuilder());
    assertEquals("canvas 0 0 500 500\n"
        + "shape rec rectangle", view.render().toString());
  }

  @Test
  public void testRenderMove() {
    model.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0),
        new ShapeColor(0, 0, 0), 10, 10);
    model.addShape(rec, "rec", 1);
    model.move("rec", 1, 5, 10, 10);
    view = new AnimationTextView(model, new StringBuilder());
    assertEquals("canvas 0 0 500 500\n"
        + "shape rec rectangle\n"
        + "motion rec 1 0 0 10 10 0 0 0 5 10 10 10 10 0 0 0 0 0", view.render().toString());
  }

  @Test
  public void testRenderChangeColor() {
    model.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0),
        new ShapeColor(0, 0, 0), 10, 10);
    model.addShape(rec, "rec", 1);
    model.changeColor("rec", 3, new ShapeColor(255, 0, 0), 10);
    view = new AnimationTextView(model, new StringBuilder());
    assertEquals("canvas 0 0 500 500\n"
        + "shape rec rectangle\n"
        + "motion rec 1 0 0 10 10 0 0 0 3 0 0 10 10 0 0 0 0 0\n"
        + "motion rec 3 0 0 10 10 0 0 0 10 0 0 10 10 255 0 0 0 0", view.render().toString());
  }

  @Test
  public void testRenderChangeSize() {
    model.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0),
        new ShapeColor(0, 0, 0), 10, 10);
    model.addShape(rec, "rec", 1);
    model.changeSize("rec", 1, 25, 10, 10);
    view = new AnimationTextView(model, new StringBuilder());
    assertEquals("canvas 0 0 500 500\n"
        + "shape rec rectangle\n"
        + "motion rec 1 0 0 10 10 0 0 0 10 0 0 10 25 0 0 0 0 0", view.render().toString());
  }

  @Test
  public void testRenderAddingMultipleShape() {
    model.declareShape("rec", "rectangle");
    model.declareShape("oval", "ellipse");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0),
        new ShapeColor(0, 0, 0), 10, 10);
    AnimationShape oval = new EllipseShape(new Posn2D(50, 50),
        new ShapeColor(255, 255, 255), 50, 50);
    model.addShape(rec, "rec", 1);
    model.addShape(oval, "oval", 2);
    view = new AnimationTextView(model, new StringBuilder());
    assertEquals("canvas 0 0 500 500\n"
        + "shape rec rectangle\n"
        + "shape oval ellipse", view.render().toString());
  }

  @Test
  public void testRenderAddingMultipleShapesAndMotion() {
    model.declareShape("rec", "rectangle");
    model.declareShape("oval", "ellipse");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0),
        new ShapeColor(0, 0, 0), 10, 10);
    AnimationShape oval = new EllipseShape(new Posn2D(50, 50),
        new ShapeColor(255, 255, 255), 50, 50);
    model.addShape(rec, "rec", 1);
    model.addShape(oval, "oval", 2);
    model.move("rec", 1, 20, 13, 14);
    model.changeColor("oval", 2, new ShapeColor(255, 15, 100), 5);
    model.changeSize("rec", 20, 28, 5, 25);
    view = new AnimationTextView(model, new StringBuilder());
    assertEquals("canvas 0 0 500 500\n"
        + "shape rec rectangle\n"
        + "motion rec 1 0 0 10 10 0 0 0 20 13 14 10 10 0 0 0 0 0\n"
        + "motion rec 20 13 14 10 10 0 0 0 25 13 14 5 28 0 0 0 0 0\n"
        + "shape oval ellipse\n"
        + "motion oval 2 50 50 50 50 255 255 255 5 50 50 50 50 255 15 100 0 0",
            view.render().toString());
  }
}