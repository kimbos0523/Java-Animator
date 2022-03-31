import org.junit.Test;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.AnimationOperations;
import cs3500.animator.model.AnimationShape;
import cs3500.animator.model.EllipseShape;
import cs3500.animator.model.Posn2D;
import cs3500.animator.model.RectangleShape;
import cs3500.animator.model.ShapeColor;
import cs3500.animator.view.AnimationSVGView;
import cs3500.animator.view.AnimationView;

import static org.junit.Assert.assertEquals;

/**
 * Test class for AnimationSVGView: unit test to ensure that AnimationSVGView operates right way.
 */
public class AnimationSVGViewTest {
  AnimationOperations model = new AnimationModel();
  AnimationView view;

  @Test(expected = IllegalArgumentException.class)
  public void testSVGViewConstructorModelNull() {
    view = new AnimationSVGView(null, new StringBuilder(), 1000);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSVGViewConstructorAppendableNull() {
    view = new AnimationSVGView(model, null, 1000);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSVGViewConstructorSpeedLessThanOne() {
    view = new AnimationSVGView(model, new StringBuilder(), 0);
  }

  @Test
  public void testRender() {
    model.declareShape("rec", "rectangle");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    model.addShape(rec, "rec", 5);
    view = new AnimationSVGView(model, new StringBuilder(), 1000);
    assertEquals("<svg width=\"500\" height=\"500\" version=\"1.1\"\n"
            + "\txmlns=\"http://www.w3.org/2000/svg\">\n"
            + "\n"
            + "<rect id=\"rec\" x=\"0\" y=\"0\" width=\"10\" height=\"10\" fill=\"rgb(0,0,0)\" "
            + "visibility=\"visible\" >\n"
            + "\n"
            + "\n"
            + "</rect>\n"
            + "\n"
            + "</svg>", view.render().toString());
  }

  @Test
  public void testRenderComplex() {
    model.declareShape("rec", "rectangle");
    model.declareShape("oval", "ellipse");
    AnimationShape rec = new RectangleShape(new Posn2D(0, 0),
            new ShapeColor(0, 0, 0), 10, 10);
    AnimationShape oval = new EllipseShape(new Posn2D(100, 100),
            new ShapeColor(100, 100, 100), 100, 100);
    model.addShape(rec, "rec", 5);
    model.addShape(oval, "oval", 10);

    // action to rec rectangle
    model.move("rec", 5, 10, 200, 200);
    model.move("rec", 10, 30, 200, 200);
    model.move("rec", 30, 40, 300, 300);
    model.move("rec", 40, 45, 300, 300);

    model.changeColor("rec", 5,
            new ShapeColor(0, 0, 0), 7);
    model.changeColor("rec", 7,
            new ShapeColor(100, 100, 100), 12);
    model.changeColor("rec", 12,
            new ShapeColor(100, 100, 100), 35);
    model.changeColor("rec", 35,
            new ShapeColor(255, 255, 255), 45);

    model.changeSize("rec", 5, 150, 150, 25);
    model.changeSize("rec", 25, 150, 150, 45);

    model.changeDegree("rec", 5, 90, 10);

    // action to oval oval
    model.move("oval", 10, 15, 400, 200);
    model.move("oval", 15, 28, 50, 10);
    model.move("oval", 28, 45, 300, 300);

    model.changeColor("oval", 10,
            new ShapeColor(0, 0, 0), 33);
    model.changeColor("oval", 33,
            new ShapeColor(80, 131, 12), 37);
    model.changeColor("oval", 37,
            new ShapeColor(0, 0, 255), 45);

    model.changeSize("oval", 10, 150, 150, 25);
    model.changeSize("oval", 25, 150, 150, 26);
    model.changeSize("oval", 26, 900, 20, 45);

    view = new AnimationSVGView(model, new StringBuilder(), 1);
    assertEquals("<svg width=\"500\" height=\"500\" version=\"1.1\"\n" +
            "\txmlns=\"http://www.w3.org/2000/svg\">\n" +
            "\n" +
            "<rect id=\"rec\" x=\"0\" y=\"0\" width=\"10\" height=\"10\" fill=\"" +
            "rgb(0,0,0)\" visibility=\"visible\" >\n" +
            "\n" +
            "\t<animate attributeType=\"xml\" begin=\"5000.0ms\" dur=\"2000.0ms\" " +
            "attributeName=\"x\" from=\"0\" to=\"80\" fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"5000.0ms\" dur=\"2000.0ms\" " +
            "attributeName=\"y\" from=\"0\" to=\"80\" fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"5000.0ms\" dur=\"2000.0ms\" " +
            "attributeName=\"width\" from=\"10\" to=\"24\" fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"5000.0ms\" dur=\"2000.0ms\"" +
            " attributeName=\"height\" from=\"10\" to=\"24\" fill=\"freeze\" />\n" +
            "\t<animateTransform attributeType=\"xml\" begin=\"5000.0ms\" dur=\"2000.0ms\" " +
            "attributeName=\"transform\" type=\"rotate\" from=\"0 5 5\" " +
            "to=\"36 92 92\" repeatCount=\"1\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"7000.0ms\" dur=\"3000.0ms\" " +
            "attributeName=\"x\" from=\"80\" to=\"200\" fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"7000.0ms\" dur=\"3000.0ms\" " +
            "attributeName=\"y\" from=\"80\" to=\"200\" fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"7000.0ms\" dur=\"3000.0ms\" " +
            "attributeName=\"width\" from=\"24\" to=\"45\" fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"7000.0ms\" dur=\"3000.0ms\"" +
            " attributeName=\"height\" from=\"24\" to=\"45\" fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"7000.0ms\" dur=\"3000.0ms\" " +
            "attributeName=\"fill\" from=\"rgb(0,0,0)\" to=\"rgb(60,60,60)\" fill=\"freeze\" />\n" +
            "\t<animateTransform attributeType=\"xml\" begin=\"7000.0ms\" dur=\"3000.0ms\" " +
            "attributeName=\"transform\" type=\"rotate\" from=\"36 92 92\" to=\"90 222 222\"" +
            " repeatCount=\"1\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"10000.0ms\" dur=\"2000.0ms\"" +
            " attributeName=\"width\" from=\"45\" to=\"59\" fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"10000.0ms\" dur=\"2000.0ms\"" +
            " attributeName=\"height\" from=\"45\" to=\"59\" fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"10000.0ms\" dur=\"2000.0ms\"" +
            " attributeName=\"fill\" from=\"rgb(60,60,60)\" to=\"rgb(100,100,100)\"" +
            " fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"12000.0ms\" dur=\"13000.0ms\"" +
            " attributeName=\"width\" from=\"59\" to=\"150\" fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"12000.0ms\" dur=\"13000.0ms\"" +
            " attributeName=\"height\" from=\"59\" to=\"150\" fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"25000.0ms\" dur=\"5000.0ms\"" +
            " attributeName=\"x\" from=\"200\" to=\"200\" fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"30000.0ms\" dur=\"5000.0ms\"" +
            " attributeName=\"x\" from=\"200\" to=\"250\" fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"30000.0ms\" dur=\"5000.0ms\"" +
            " attributeName=\"y\" from=\"200\" to=\"250\" fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"35000.0ms\" dur=\"5000.0ms\"" +
            " attributeName=\"x\" from=\"250\" to=\"300\" fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"35000.0ms\" dur=\"5000.0ms\"" +
            " attributeName=\"y\" from=\"250\" to=\"300\" fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"35000.0ms\" dur=\"5000.0ms\"" +
            " attributeName=\"fill\" from=\"rgb(100,100,100)\" to=\"rgb(175,175,175)\" " +
            "fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"40000.0ms\" dur=\"5000.0ms\"" +
            " attributeName=\"fill\" from=\"rgb(175,175,175)\" to=\"rgb(255,255,255)\"" +
            " fill=\"freeze\" />\n" +
            "\n" +
            "</rect>\n" +
            "\n" +
            "<ellipse id=\"oval\" cx=\"100\" cy=\"100\" rx=\"50\" ry=\"50\" " +
            "fill=\"rgb(100,100,100)\" visibility=\"visible\" >\n" +
            "\n" +
            "\t<animate attributeType=\"xml\" begin=\"10000.0ms\" dur=\"5000.0ms\" " +
            "attributeName=\"cx\" from=\"100\" to=\"400\" fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"10000.0ms\" dur=\"5000.0ms\" " +
            "attributeName=\"cy\" from=\"100\" to=\"200\" fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"10000.0ms\" dur=\"5000.0ms\"" +
            " attributeName=\"rx\" from=\"50\" to=\"57\" fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"10000.0ms\" dur=\"5000.0ms\"" +
            " attributeName=\"ry\" from=\"50\" to=\"57\" fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"10000.0ms\" dur=\"5000.0ms\"" +
            " attributeName=\"fill\" from=\"rgb(100,100,100)\" to=\"rgb(80,80,80)\" " +
            "fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"15000.0ms\" dur=\"10000.0ms\"" +
            " attributeName=\"cx\" from=\"400\" to=\"140\" fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"15000.0ms\" dur=\"10000.0ms\"" +
            " attributeName=\"cy\" from=\"200\" to=\"60\" fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"15000.0ms\" dur=\"10000.0ms\"" +
            " attributeName=\"rx\" from=\"57\" to=\"75\" fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"15000.0ms\" dur=\"10000.0ms\" " +
            "attributeName=\"ry\" from=\"57\" to=\"75\" fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"15000.0ms\" dur=\"10000.0ms\"" +
            " attributeName=\"fill\" from=\"rgb(80,80,80)\" to=\"rgb(40,40,40)\"" +
            " fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"25000.0ms\" dur=\"1000.0ms\" " +
            "attributeName=\"cx\" from=\"140\" to=\"114\" fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"25000.0ms\" dur=\"1000.0ms\" " +
            "attributeName=\"cy\" from=\"60\" to=\"46\" fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"25000.0ms\" dur=\"1000.0ms\" " +
            "attributeName=\"fill\" from=\"rgb(40,40,40)\" to=\"rgb(36,36,36)\" " +
            "fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"26000.0ms\" dur=\"2000.0ms\"" +
            " attributeName=\"cx\" from=\"114\" to=\"50\" fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"26000.0ms\" dur=\"2000.0ms\" " +
            "attributeName=\"cy\" from=\"46\" to=\"10\" fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"26000.0ms\" dur=\"2000.0ms\" " +
            "attributeName=\"rx\" from=\"75\" to=\"69\" fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"26000.0ms\" dur=\"2000.0ms\" " +
            "attributeName=\"ry\" from=\"75\" to=\"114\" fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"26000.0ms\" dur=\"2000.0ms\" " +
            "attributeName=\"fill\" from=\"rgb(36,36,36)\" to=\"rgb(28,28,28)\" " +
            "fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"28000.0ms\" dur=\"5000.0ms\" " +
            "attributeName=\"cx\" from=\"50\" to=\"120\" fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"28000.0ms\" dur=\"5000.0ms\" " +
            "attributeName=\"cy\" from=\"10\" to=\"95\" fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"28000.0ms\" dur=\"5000.0ms\" a" +
            "ttributeName=\"rx\" from=\"69\" to=\"54\" fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"28000.0ms\" dur=\"5000.0ms\" " +
            "attributeName=\"ry\" from=\"114\" to=\"211\" fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"28000.0ms\" dur=\"5000.0ms\" " +
            "attributeName=\"fill\" from=\"rgb(28,28,28)\" to=\"rgb(0,0,0)\" fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"33000.0ms\" dur=\"4000.0ms\" " +
            "attributeName=\"cx\" from=\"120\" to=\"176\" fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"33000.0ms\" dur=\"4000.0ms\" " +
            "attributeName=\"cy\" from=\"95\" to=\"163\" fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"33000.0ms\" dur=\"4000.0ms\" " +
            "attributeName=\"rx\" from=\"54\" to=\"42\" fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"33000.0ms\" dur=\"4000.0ms\" " +
            "attributeName=\"ry\" from=\"211\" to=\"289\" fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"33000.0ms\" dur=\"4000.0ms\" " +
            "attributeName=\"fill\" from=\"rgb(0,0,0)\" to=\"rgb(80,131,12)\" " +
            "fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"37000.0ms\" dur=\"8000.0ms\" " +
            "attributeName=\"cx\" from=\"176\" to=\"300\" fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"37000.0ms\" dur=\"8000.0ms\" " +
            "attributeName=\"cy\" from=\"163\" to=\"300\" fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"37000.0ms\" dur=\"8000.0ms\" " +
            "attributeName=\"rx\" from=\"42\" to=\"10\" fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"37000.0ms\" dur=\"8000.0ms\" " +
            "attributeName=\"ry\" from=\"289\" to=\"450\" fill=\"freeze\" />\n" +
            "\t<animate attributeType=\"xml\" begin=\"37000.0ms\" dur=\"8000.0ms\" " +
            "attributeName=\"fill\" from=\"rgb(80,131,12)\" to=\"rgb(0,0,255)\" " +
            "fill=\"freeze\" />\n" +
            "\n" +
            "</ellipse>\n" +
            "\n" +
            "</svg>", view.render().toString());
  }
}