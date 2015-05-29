package edu.nyu.pqs.canvas.impl;

import edu.nyu.pqs.canvas.api.ICShape;

/**
 * An immutable class that contains all the information needed to draw this shape on a canvas.
 * 
 * @author cpp270
 *
 */
public class CShape implements ICShape{
  private final int xCoordinate;
  private final int yCoordinate;
  private final int penSize;
  private final PenShape penShape;
  private final int color;
  
  /**
   * Creates a new shape object.
   * 
   * @param xCoordinate x coordinate for this shape
   * @param yCoordinate y coordinate for this shape
   * @param color the RGB color for this shape
   * @param penSize the pen size for this shape
   * @param penShape the pen shape for this shape
   * @throws NullPointerException when <code>penShape</code> is null
   */
  public CShape(int xCoordinate, int yCoordinate, int color, int penSize, PenShape penShape) {
    if (penShape == null) {
      throw new NullPointerException("Pen shape must be non-null");
    }
    this.xCoordinate = xCoordinate;
    this.yCoordinate = yCoordinate;
    this.penSize = penSize;
    this.penShape = penShape;
    this.color = color;
  }
  
  @Override
  public int getColor() {
    return color;
  }

  @Override
  public int getPenSize() {
    return penSize;
  }

  @Override
  public PenShape getPenShape() {
    return penShape;
  }

  @Override
  public int getX() {
    return xCoordinate;
  }

  @Override
  public int getY() {
    return yCoordinate;
  }

  /**
   * Indicates whether some object is equal to this CShape object.
   * 
   * @param o the reference object with which to compare 
   * @return true if this object is the same as the argument; false otherwise 
   */
  @Override public boolean equals(Object o) {
    if (o == this) {
      return true;
    }      
    if (!(o instanceof CShape)) {
      return false;
    }
    CShape cs = (CShape) o;
    return cs.color == color
        && cs.penSize == penSize
        && cs.penShape.equals(penShape)
        && cs.xCoordinate == xCoordinate
        && cs.yCoordinate == yCoordinate;          
  }

  /**
   * Computes and returns the hash code value for this CShape object.
   * 
   * @return a hash code value for this shape object
   */
  @Override public int hashCode() {
    int result = 17;
    result = 31 * result + color;
    result = 31 * result + penSize;
    result = 31 * result + xCoordinate;
    result = 31 * result + yCoordinate;
    result = 31 * result + penShape.hashCode();
    return result;
  }
  
  /**
   * This method returns friendly string representation of this shape object.
   * The exact representation is unspecified, subject to change, and should not be used to
   * infer the values of this shape object.
   * 
   * A typical string representation has the following format.
   * [X:val Y:val RGB Color:val Pen Size:val Pen Shape:val]
   */  
  @Override
  public String toString() {
      return "[X:" + xCoordinate + " Y:" + yCoordinate + " RGB Color:" + color + " Pen Size:" + 
          penSize + " Pen Shape:" + penShape.toString() + "]";
  }
}
