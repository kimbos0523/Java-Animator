import org.junit.Test;


import cs3500.animator.model.AnimationShape;
import cs3500.animator.model.EllipseShape;
import cs3500.animator.model.Posn2D;
import cs3500.animator.model.RectangleShape;
import cs3500.animator.model.ShapeColor;
import cs3500.animator.view.ISVGShapeVisitor;
import cs3500.animator.view.SVGShapeVisitor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test class for AnimationShape: unit test to ensure that AnimationShape operates right way.
 */
public class AnimationShapeTest {
  AnimationShape rec;
  AnimationShape oval;

  @Test
  public void testAnimationShapeConstructor() {
    rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    assertEquals(new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10), rec.showShape(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAnimationShapeConstructorPositionNull() {
    rec = new RectangleShape(null, new ShapeColor(0, 0, 0), 10, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAnimationShapeConstructorColorNull() {
    rec = new RectangleShape(new Posn2D(0, 0), null, 10, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAnimationShapeConstructorInvalidColor() {
    rec = new RectangleShape(new Posn2D(0, 0), new ShapeColor(0, 259, -10),
            10, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAnimationShapeConstructorSizeLessThanOne() {
    rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 0, -1);
  }


  @Test
  public void testShowShape() {
    rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    oval = new EllipseShape(new Posn2D(50, 50),
            new ShapeColor(200, 200, 200), 150, 150);

    assertEquals(new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10), rec.showShape(0));
    assertEquals(new EllipseShape(new Posn2D(50, 50),
            new ShapeColor(200, 200, 200), 150, 150), oval.showShape(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testShowShapeTimeNegative() {
    rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    oval = new EllipseShape(new Posn2D(50, 50),
            new ShapeColor(200, 200, 200), 150, 150);
    rec.showShape(-1);
  }

  @Test
  public void testGetPosition() {
    rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    oval = new EllipseShape(new Posn2D(50, 50),
            new ShapeColor(200, 200, 200), 150, 150);

    assertEquals(new Posn2D(0, 0), rec.getPosition());
    assertEquals(new Posn2D(50, 50), oval.getPosition());
  }

  @Test
  public void getColor() {
    rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    oval = new EllipseShape(new Posn2D(50, 50),
            new ShapeColor(200, 200, 200), 150, 150);

    assertEquals(new ShapeColor(0, 0, 0), rec.getShapeColor());
    assertEquals(new ShapeColor(200, 200, 200), oval.getShapeColor());
  }

  @Test
  public void getHeight() {
    rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    oval = new EllipseShape(new Posn2D(50, 50),
            new ShapeColor(200, 200, 200), 150, 150);

    assertEquals(10, rec.getHeight());
    assertEquals(150, oval.getHeight());
  }

  @Test
  public void getWidth() {
    rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    oval = new EllipseShape(new Posn2D(50, 50),
            new ShapeColor(200, 200, 200), 150, 150);

    assertEquals(10, rec.getWidth());
    assertEquals(150, oval.getWidth());
  }

  @Test
  public void setPosition() {
    rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    oval = new EllipseShape(new Posn2D(50, 50),
            new ShapeColor(200, 200, 200), 150, 150);
    rec.setPosition(50, 50);
    oval.setPosition(50, 50);

    assertEquals(new Posn2D(50, 50), rec.getPosition());
    assertEquals(new Posn2D(50, 50), oval.getPosition());
  }


  @Test
  public void setColor() {
    rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    oval = new EllipseShape(new Posn2D(50, 50),
            new ShapeColor(200, 200, 200), 150, 150);
    rec.setColor(5, 5, 5);
    oval.setColor(10, 10, 10);

    assertEquals(new ShapeColor(5, 5, 5), rec.getShapeColor());
    assertEquals(new ShapeColor(10, 10, 10), oval.getShapeColor());
  }

  @Test(expected = IllegalArgumentException.class)
  public void setColorInvalidColor() {
    rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    oval = new EllipseShape(new Posn2D(50, 50),
            new ShapeColor(200, 200, 200), 150, 150);
    rec.setColor(-5, 270, 5);
  }

  @Test
  public void setSize() {
    rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    oval = new EllipseShape(new Posn2D(50, 50),
            new ShapeColor(200, 200, 200), 150, 150);
    rec.setSize(20, 20);
    oval.setSize(20, 20);

    assertEquals(20, rec.getHeight());
    assertEquals(20, rec.getWidth());
    assertEquals(20, oval.getHeight());
    assertEquals(20, oval.getWidth());
  }

  @Test(expected = IllegalArgumentException.class)
  public void setSizeInvalidSize() {
    rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    oval = new EllipseShape(new Posn2D(50, 50),
            new ShapeColor(200, 200, 200), 150, 150);
    rec.setSize(0, 0);
  }

  @Test
  public void getDeltaX() {
    rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    oval = new EllipseShape(new Posn2D(50, 50),
            new ShapeColor(200, 200, 200), 150, 150);

    assertEquals(0.0f, rec.getDeltaX(), 0.001);
    assertEquals(0.0f, oval.getDeltaX(), 0.001);
  }

  @Test
  public void getDeltaY() {
    rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    oval = new EllipseShape(new Posn2D(50, 50),
            new ShapeColor(200, 200, 200), 150, 150);

    assertEquals(0.0f, rec.getDeltaY(), 0.001);
    assertEquals(0.0f, oval.getDeltaY(), 0.001);
  }

  @Test
  public void getDeltaR() {
    rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    oval = new EllipseShape(new Posn2D(50, 50),
            new ShapeColor(200, 200, 200), 150, 150);

    assertEquals(0.0f, rec.getDeltaR(), 0.001);
    assertEquals(0.0f, oval.getDeltaR(), 0.001);
  }

  @Test
  public void getDeltaG() {
    rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    oval = new EllipseShape(new Posn2D(50, 50),
            new ShapeColor(200, 200, 200), 150, 150);

    assertEquals(0.0f, rec.getDeltaG(), 0.001);
    assertEquals(0.0f, oval.getDeltaG(), 0.001);
  }

  @Test
  public void getDeltaB() {
    rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    oval = new EllipseShape(new Posn2D(50, 50),
            new ShapeColor(200, 200, 200), 150, 150);

    assertEquals(0.0f, rec.getDeltaB(), 0.001);
    assertEquals(0.0f, oval.getDeltaB(), 0.001);
  }

  @Test
  public void getDeltaHeight() {
    rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    oval = new EllipseShape(new Posn2D(50, 50),
            new ShapeColor(200, 200, 200), 150, 150);

    assertEquals(0.0f, rec.getDeltaHeight(), 0.001);
    assertEquals(0.0f, oval.getDeltaHeight(), 0.001);
  }

  @Test
  public void testGetDeltaWidth() {
    rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    oval = new EllipseShape(new Posn2D(50, 50),
            new ShapeColor(200, 200, 200), 150, 150);

    assertEquals(0.0f, rec.getDeltaWidth(), 0.001);
    assertEquals(0.0f, oval.getDeltaWidth(), 0.001);
  }

  @Test
  public void testSetDeltaPosition() {
    rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    rec.setDeltaPosition(2.56f, 0.0f);
    assertEquals(2.56f, rec.getDeltaX(), 0.001);
    assertEquals(0.0f, rec.getDeltaY(), 0.001);
  }

  @Test
  public void testSetDeltaColor() {
    rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    rec.setDeltaColor(2.56f, 0.0f, -2.56f);
    assertEquals(2.56f, rec.getDeltaR(), 0.001);
    assertEquals(0.0f, rec.getDeltaG(), 0.001);
    assertEquals(-2.56f, rec.getDeltaB(), 0.001);
  }

  @Test
  public void testSetDeltaHeight() {
    rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    rec.setDeltaHeight(-2.56f);
    assertEquals(-2.56f, rec.getDeltaHeight(), 0.001);

  }

  @Test
  public void testSetDeltaWidth() {
    rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    rec.setDeltaWidth(2.56f);
    assertEquals(2.56f, rec.getDeltaWidth(), 0.001);
  }

  @Test
  public void testToString() {
    rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    oval = new EllipseShape(new Posn2D(50, 50),
            new ShapeColor(200, 200, 200), 150, 150);

    assertEquals("0 0 10 10 0 0 0", rec.toString());
    assertEquals("50 50 150 150 200 200 200", oval.toString());
  }

  @Test
  public void testGetType() {
    rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    oval = new EllipseShape(new Posn2D(50, 50),
            new ShapeColor(200, 200, 200), 150, 150);

    assertEquals("rectangle", rec.getType());
    assertEquals("ellipse", oval.getType());
  }

  @Test
  public void testEquals() {
    rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    oval = new EllipseShape(new Posn2D(50, 50),
            new ShapeColor(200, 200, 200), 150, 150);

    assertTrue(rec.equals( new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10)));
    assertTrue(oval.equals(new EllipseShape(new Posn2D(50, 50),
            new ShapeColor(200, 200, 200), 150, 150)));
    assertFalse(rec.equals(new EllipseShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10)));
    assertFalse(rec.equals( new RectangleShape(new Posn2D(10, 0),
            new ShapeColor(0, 0, 0), 10, 10)));
    assertFalse(oval.equals(new EllipseShape(new Posn2D(50, 50),
            new ShapeColor(100, 200, 200), 150, 150)));
    assertFalse(oval.equals(new EllipseShape(new Posn2D(50, 50),
            new ShapeColor(200, 200, 200), 10, 150)));
    assertFalse(oval.equals(new EllipseShape(new Posn2D(50, 50),
            new ShapeColor(200, 200, 200), 150, 10)));
  }

  @Test
  public void testAcceptSVG() {
    rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    oval = new EllipseShape(new Posn2D(50, 50),
            new ShapeColor(200, 200, 200), 150, 150);
    ISVGShapeVisitor visitor = new SVGShapeVisitor("rec");
    ISVGShapeVisitor visitor2 = new SVGShapeVisitor("oval");

    assertEquals("\n<rect id=\"rec\" x=\"0\" y=\"0\" width=\"10\" height=\"10\" "
            + "fill=\"rgb(0,0,0)\" visibility=\"visible\" >\n", rec.acceptSVG(visitor));
    assertEquals("\n<ellipse id=\"oval\" cx=\"50\" cy=\"50\" rx=\"75\" ry=\"75\" "
            + "fill=\"rgb(200,200,200)\" visibility=\"visible\" >\n", oval.acceptSVG(visitor2));
  }

  @Test
  public void testGetSetDegree() {
    rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10, 0);
    assertEquals(0, rec.getDegree());
    rec.setDegree(45);
    assertEquals(45, rec.getDegree());
    rec.setDegree(-45);
    assertEquals(-45, rec.getDegree());
  }

  @Test
  public void testGetSetDeltaDegree() {
    rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10, 0);
    assertEquals(true, Math.abs(rec.getDeltaDegree() - 0) < 0.001);
    rec.setDeltaDegree(0.0125f);
    assertEquals(true, Math.abs(rec.getDeltaDegree() - 0.0125) < 0.001);
    rec.setDeltaDegree(-1.5f);
    assertEquals(true, Math.abs(rec.getDeltaDegree() + 1.5) < 0.001);
  }
}