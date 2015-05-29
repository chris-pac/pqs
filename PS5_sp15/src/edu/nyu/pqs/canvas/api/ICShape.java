/**
 * 
 */
package edu.nyu.pqs.canvas.api;

/**
 * Object that contains all the necessary attributes required to render it on some surface.
 * 
 * @author cpp270
 *
 */
public interface ICShape {
  /**
   * The pen shape types that are available for drawing.
   * 
   * @author cpp270
   *
   */
  public enum PenShape {ROUND, SQUARE;
  /**
   * This method returns friendly string representation of the values of this enum object.
   * The exact representation is unspecified, subject to change, and should not be used to
   * infer enum values.
   */  
  @Override
  public String toString() {
      if (this.ordinal() == ROUND.ordinal()) {
        return "Round";
      } else {
        return "Square";
      }
    }
  };
  
  /**
   * Returns the color that was used to draw this shape on a canvas.
   * 
   * @return RGB color of this shape
   */
  public int getColor();
  
  /**
   * Returns the size of the pen that was used to draw this shape on a canvas.
   * 
   * @return pen size for this shape
   */
  public int getPenSize();
  
  /**
   * Returns the shape of the pen that was used to draw this shape on a canvas.
   * 
   * @return the pen shape for this shape
   */
  public PenShape getPenShape();
  
  /**
   * The x coordinate where this shape was drawn on a canvas.
   * @return the x coordinate for this shape
   */
  public int getX();
  
  /**
   * The y coordinate where this shape was drawn on a canvas.
   * @return the y coordinate for this shape
   */
  public int getY();
}
