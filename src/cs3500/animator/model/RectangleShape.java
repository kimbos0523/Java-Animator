package cs3500.animator.model;

import java.util.Objects;

import cs3500.animator.view.ISVGShapeVisitor;
import cs3500.animator.view.ISwingShapeVisitor;

/**
 * A class representing an Rectangle shape, which extends the abstract class AShape.
 */
public class RectangleShape extends AShape {

  /**
   * A constructor for an RectangleShape, given its elements.
   *
   * @param position The Posn2D position of the current shape, Pos2D cannot be null and
   *                 position x and y cannot be negative
   * @param color    The ShapeColor of the current shape, ShapeColor cannot be null or
   *                 color rgb cannot be negative or greater than 255
   * @param height   The height of the current shape, cannot be less or equal to zero
   * @param width    The width of the current shape, cannot be less or equal to zero
   */
  public RectangleShape(Posn2D position, ShapeColor color, int height, int width, int degree) {
    super(position, color, height, width, degree);
  }

  public RectangleShape(Posn2D position, ShapeColor color, int height, int width) {
    super(position, color, height, width);
  }

  @Override
  public AnimationShape showShape(int time) {
    if (time < 0) {
      throw new IllegalArgumentException("Time cannot be negative.");
    }

    int tNewX = position.getX() + Math.round(deltaX * time);
    int tNewY = position.getY() + Math.round(deltaY * time);
    Posn2D tPosn = new Posn2D(tNewX, tNewY);

    int tNewR = color.getR() + Math.round(deltaR * time);
    int tNewG = color.getG() + Math.round(deltaG * time);
    int tNewB = color.getB() + Math.round(deltaB * time);
    ShapeColor tColor = new ShapeColor(tNewR, tNewG, tNewB);

    int tHeight = height + Math.round(deltaHeight * time);
    int tWidth = width + Math.round(deltaWidth * time);

    int tDegree = degree + Math.round(deltaDegree * time);

    RectangleShape tRec = new RectangleShape(tPosn, tColor, tHeight, tWidth, tDegree);
    tRec.setDeltaPosition(deltaX, deltaY);
    tRec.setDeltaColor(deltaR, deltaG, deltaB);
    tRec.setDeltaHeight(deltaHeight);
    tRec.setDeltaWidth(deltaWidth);
    tRec.setDeltaDegree(deltaDegree);

    return tRec;
  }

  @Override
  public String getType() {
    return "rectangle";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof RectangleShape)) {
      return false;
    }

    RectangleShape that = (RectangleShape) o;

    return this.position.equals(that.position) && this.color.equals(that.color)
            && this.height == that.height && this.width == that.width && this.degree == that.degree;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.position, this.color,
            this.height, this.width, this.degree);
  }

  @Override
  public void accept(ISwingShapeVisitor visitor) {
    visitor.visitorRectangle(this);
  }

  @Override
  public String acceptSVG(ISVGShapeVisitor visitor) {
    return visitor.visitorRectangle(this);
  }

}
