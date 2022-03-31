import org.junit.Test;

import cs3500.animator.model.ShapeColor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test class for ShapeColor: unit test to ensure that ShapeColor operates right way.
 */
public class ShapeColorTest {
  ShapeColor color;

  @Test
  public void testShapeColorConstructor() {
    color = new ShapeColor(0, 0, 0);
    assertEquals(new ShapeColor(0, 0, 0), color);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testShapeColorConstructorLessThanZero() {
    color = new ShapeColor(-1, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testShapeColorConstructorGreaterThan255() {
    color = new ShapeColor(0, 256, 0);
  }

  @Test
  public void testToString() {
    color = new ShapeColor(0, 0, 0);
    assertEquals("0 0 0", color.toString());
  }

  @Test
  public void testEquals() {
    color = new ShapeColor(0, 0, 0);
    assertTrue(color.equals(new ShapeColor(0, 0, 0)));
    assertFalse(color.equals(new ShapeColor(0, 10, 0)));
  }

  @Test
  public void testGetRGB() {
    color = new ShapeColor(0, 10, 20);
    assertEquals(0, color.getR());
    assertEquals(10, color.getG());
    assertEquals(20, color.getB());
  }
}