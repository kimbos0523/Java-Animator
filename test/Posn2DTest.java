import org.junit.Test;

import cs3500.animator.model.Posn2D;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class for Posn2D: unit test to ensure that Posn2D operates right way.
 */
public class Posn2DTest {
  Posn2D pos;

  @Test
  public void testConstructor() {
    pos = new Posn2D(250, 250);
    assertEquals(250, pos.getX());
    assertEquals(250, pos.getY());
  }

  @Test
  public void testToString() {
    pos = new Posn2D(250, 250);
    assertEquals("250 250", pos.toString());
  }

  @Test
  public void tesEquals() {
    pos = new Posn2D(250, 250);
    assertTrue(pos.equals(new Posn2D(250, 250)));
  }

  @Test
  public void testGetXY() {
    pos = new Posn2D(250, 250);
    assertEquals(250, pos.getX());
    assertEquals(250, pos.getY());
  }
}