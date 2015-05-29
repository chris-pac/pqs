package edu.nyu.pqs.canvas.app;

import edu.nyu.pqs.canvas.gui.CanvasView;
import edu.nyu.pqs.canvas.impl.Canvas;

/**
 * Canvas Application object responsible for setting the initial values of the canvas and
 * starting the GUI.
 * 
 * @author cpp270
 *
 */
public class CanvasApp {

  /*
   * Launches the Canvas GUI with a canvas model object.
   */
  public static void main(String[] args) {
    System.setProperty("apple.laf.useScreenMenuBar", "true");

    Canvas canvas = new Canvas.Builder().build();
    new CanvasView(canvas);
  }

}
