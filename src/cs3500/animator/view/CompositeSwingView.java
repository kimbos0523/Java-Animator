package cs3500.animator.view;

import static java.util.Collections.max;

import cs3500.animator.controller.AnimationController;
import cs3500.animator.model.ReadOnlyAnimationModel;
import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.Timer;

/**
 * A composite view for the Swing representation of an Animation, allows for more actions, such as
 * playing, pausing, and restarting.
 */
public class CompositeSwingView extends JFrame implements AnimationView {

  private AnimationPanel animationPanel;
  private ButtonPanel buttonPanel;
  private KeyFramePanel keyFramePanel;
  private SaveLoadPanel saveLoadPanel;
  private ReadOnlyAnimationModel model;

  private int tick = 0;
  private boolean isLoop = false;
  private Timer timer;

  private JScrollBar scroller;

  /**
   * A constructor for the CompositeSwingView, which takes in a window title and a model.
   *
   * @param windowTitle the title for the window
   * @param model       the model that is to be displayed and manipulated in the Animation.
   */
  public CompositeSwingView(String windowTitle, ReadOnlyAnimationModel model) {
    super(windowTitle);
    this.model = model;

    setSize(model.getBackground().getDimension());
    setLocation(150, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    animationPanel = new AnimationPanel(model, tick);
    this.add(animationPanel);

    scroller = new JScrollBar(Adjustable.HORIZONTAL, 0, 10, 0,
            maxTick() + 10);
    this.add(scroller, BorderLayout.PAGE_END);
    buttonPanel = new ButtonPanel();
    keyFramePanel = new KeyFramePanel();
    saveLoadPanel = new SaveLoadPanel();
  }

  @Override
  public void doAction(AnimationController listener) {
    buttonPanel.addClickListener(listener);
    keyFramePanel.addClickListener(listener);
    saveLoadPanel.addClickListener(listener);
    scrollerAction(listener);
  }

  /**
   * Takes in the controller to move the animation according to where the user moves the scroller.
   *
   * @param listener the AnimationController which will perform the action
   */
  private void scrollerAction(AnimationController listener) {
    scroller.addAdjustmentListener(e -> listener.moveAnimation(e.getValue()));
  }

  @Override
  public void setTimer(Timer timer) {
    this.timer = timer;
  }

  @Override
  public Timer getTimer() {
    return this.timer;
  }

  @Override
  public void setIsLoop(boolean isLoop) {
    this.isLoop = isLoop;
  }

  @Override
  public boolean getIsLoop() {
    return isLoop;
  }

  @Override
  public int getTick() {
    return tick;
  }

  @Override
  public ReadOnlyAnimationModel getModel() {
    return this.model;
  }

  @Override
  public void setModel(ReadOnlyAnimationModel model) {
    this.model = model;
    this.setTick(0);
    this.animationPanel.setModel(model);
    this.refresh();
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void refresh() {
    animationPanel.setTick(tick);
    scroller.setMaximum(maxTick() + 10);
    scroller.setValue(tick);
    this.repaint();
    if (isLoop) {
      tick++;
      tick = tick % maxTick();
    } else {
      tick++;
    }
  }

  @Override
  public Appendable render() {
    //Does not apply to the swing/visual view, only the textual and SVG
    throw new UnsupportedOperationException("Not supported by Swing view");
  }

  /**
   * Calculates the last tick that exists in the model.
   *
   * @return the last tick
   */
  private int maxTick() {
    if (model.getTimeline().size() == 0) {
      return 0;
    }
    List<Integer> highest = new ArrayList<>();
    for (String string : model.getTimeline().keySet()) {
      int num = model.getTimeline().get(string).lastKey();
      highest.add(num);
    }
    return max(highest);
  }

  @Override
  public void setTick(int tick) {
    this.tick = tick;
  }
}