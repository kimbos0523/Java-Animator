import org.junit.Test;

import cs3500.animator.model.AnimationBackground;

import static org.junit.Assert.assertEquals;

/**
 * Test class for AnimationShape: unit test to ensure that AnimationShape operates right way.
 */
public class AnimationBackgroundTest {
  AnimationBackground background;

  @Test(expected = IllegalArgumentException.class)
  public void testAnimationBackgroundConstructorInvalidSize() {
    background = new AnimationBackground(0, 0, 0, -10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAnimationBackgroundConstructorNegativePosition() {
    background = new AnimationBackground(-10, -10, 100, 100);
  }

  @Test
  public void testAnimationBackgroundToString() {
    background = new AnimationBackground(0, 0, 100, 100);
    assertEquals("0 0 100 100", background.toString());
  }

  @Test
  public void testAnimationBackgroundGetDimension() {
    background = new AnimationBackground(0, 0, 100, 140);
    assertEquals(100, background.getDimension().width);
    assertEquals(140, background.getDimension().height);
  }

  @Test
  public void testAnimationBackgroundGetXAndY() {
    background = new AnimationBackground(0, 10, 100, 140);
    assertEquals(0, background.getX());
    assertEquals(10, background.getY());
  }
}