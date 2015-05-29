package edu.nyu.pqs.canvas.api;

/**
 * A listener object that receives updates of the changes that have taken place on the canvas.
 * 
 * @author cpp270
 *
 */
public interface ICanvasListener {
  /**
   * Possible painting updates that have or are taking place that should be handled accordingly.
   * @author cpp270
   *
   */
  public enum Painting {START, END, INPROGRESS, CLEARED, REVERTED, UPDATED};
  
  /**
   * Notifies the listener that the drawing on the canvas has changed and it's time to repaint.
   * @param action indicates the type painting done on the canvas
   */
  public void paint(Painting action);

  /**
   * Notifies the listener about the changes to the pen.
   * 
   * @param penSize the new pen size that will be used to draw shapes on the canvas.
   * @param shape the new pen shape that will be used to draw shapes on the canvas.
   */
  public void updatePen(int penSize, ICShape.PenShape shape);
  
  /**
   * Notifies the listener about the changes to the colors.
   * 
   * @param foreground the new foreground color that will be used to draw shapes on the canvas.
   * @param background the new background color of the canvas.
   */
  public void updateColor(int foreground, int background);  
}
