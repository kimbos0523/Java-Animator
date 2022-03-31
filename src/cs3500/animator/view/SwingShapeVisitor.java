package cs3500.animator.view;

import java.awt.Graphics2D;
import java.awt.Color;
import cs3500.animator.model.AnimationShape;
import cs3500.animator.model.EllipseShape;
import cs3500.animator.model.RectangleShape;
import java.awt.geom.AffineTransform;

/**
 * An implementation of the ISwingShapeVisitor, which handles the visitor methods.
 */
public class SwingShapeVisitor implements ISwingShapeVisitor {

  private Graphics2D g;

  /**
   * A constructor for the SwingShapeVisitor.
   *
   * @param g the graphics2D that will draw the shapes in the visitors
   */
  public SwingShapeVisitor(Graphics2D g) {
    this.g = g;
  }

  @Override
  public void visitorRectangle(RectangleShape r) {
    AffineTransform org = g.getTransform();
    AffineTransform at = new AffineTransform();
    g.setColor(new Color(r.getShapeColor().getR(), r.getShapeColor().getG(),
        r.getShapeColor().getB()));
    int centerX = r.getPosition().getX() + r.getWidth() / 2;
    int centerY = r.getPosition().getY() + r.getHeight() / 2;
    at.rotate(Math.toRadians(r.getDegree()), centerX, centerY);
    g.transform(at);
    g.fillRect(r.getPosition().getX(), r.getPosition().getY(), r.getWidth(), r.getHeight());
    g.setTransform(org);
  }

  @Override
  public void visitorEllipse(EllipseShape e) {
    AffineTransform org = g.getTransform();
    AffineTransform at = new AffineTransform();
    g.setColor(new Color(e.getShapeColor().getR(), e.getShapeColor().getG(),
        e.getShapeColor().getB()));
    int centerX = e.getPosition().getX() + e.getWidth() / 2;
    int centerY = e.getPosition().getY() + e.getHeight() / 2;
    at.rotate(Math.toRadians(e.getDegree()), centerX, centerY);
    g.transform(at);
    g.fillOval(e.getPosition().getX(), e.getPosition().getY(), e.getWidth(), e.getHeight());
    g.setTransform(org);
  }

  @Override
  public void apply(AnimationShape s) {
    s.accept(this);
  }
}
