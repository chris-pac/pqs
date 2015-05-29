package edu.nyu.pqs.canvas.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import edu.nyu.pqs.canvas.api.ICShape;
import edu.nyu.pqs.canvas.api.ICShape.PenShape;
import edu.nyu.pqs.canvas.api.ICanvas;
import edu.nyu.pqs.canvas.api.ICanvasListener;
import edu.nyu.pqs.canvas.api.ICanvasListener.Painting;

/**
 * This class is used to position child elements within this canvas by using coordinates 
 * that are relative to the usre's surface area. Furthermore, this class allows for manipulation
 * of those elements.
 * 
 * Each element is composed of zero or more shape objects which contain the actual coordinates and
 * rendering properties.
 * 
 * The Canvas class implements the observable/model scheme that notifies the observers/listeners 
 * through fired events.
 * 
 * @author cpp270
 *
 */
public class Canvas implements ICanvas {
  private List<ICanvasListener> listeners = new LinkedList<ICanvasListener>();

  private List<CElement> elements = new ArrayList<CElement>();
  
  private CElement currentElement = null;

  private static final int DEFAULTMINPENSIZE = 1;
  private static final int DEFAULTMAXPENSIZE = 50;

  private int minPenSize = DEFAULTMINPENSIZE;
  private int maxPenSize = DEFAULTMAXPENSIZE;
  
  private int penSize = minPenSize;
  private int backgroundColor;
  private int foregroundColor;
  private PenShape penShape = PenShape.ROUND;
    
  /**
   * This Builder class is used to initialize and generate a Canvas object.
   * 
   * @author cpp270
   *
   */
  public static class Builder {
    private int minPenSize = DEFAULTMINPENSIZE;
    private int maxPenSize = DEFAULTMAXPENSIZE;
    
    private int penSize = minPenSize;
    private int backgroundColor;
    private int foregroundColor;
    private PenShape penShape = PenShape.ROUND;

    /**
     * A setter method used to initialize the foreground color in the canvas object.
     * 
     * @param val the RGB color value
     * @return the builder object
     */
    public Builder foregroundColor(int val) {
      foregroundColor = val;
      return this;
    }
    
    /**
     * A setter method used to initialize the background color in the canvas object.
     * 
     * @param val the RGB color value
     * @return the builder object
     */
    public Builder backgroundColor(int val) {
      backgroundColor = val;
      return this;
    }
    
    /**
     * A setter method used to initialize the maximum pen size in the canvas object.
     * 
     * @param val the maximum pen size to be used
     * @return the builder object
     */
    public Builder maxPenSize(int val) {
      maxPenSize = val;
      return this;
    }
    
    /**
     * A setter method used to initialize the minimum pen size in the canvas object.
     * 
     * @param val the minimum pen size to be used
     * @return the builder object
     */
    public Builder minPenSize(int val) {
      minPenSize = val;
      return this;
    }
    
    /**
     * A setter method used to initialize the pen size in the canvas object.
     * 
     * @param val the pen size value
     * @return the builder object
     * @throws IllegalArgumentException when pen size is outside the min max pen size values.
     */
    public Builder penSize(int val) {
      penSize = val;      
      return this;
    }

    /**
     * A setter method used to initialize the pen shape in the canvas object.
     * 
     * @param val the pen shape to be used
     * @return the builder object
     */
    public Builder penShape(PenShape val) {
      penShape = val;
      return this;
    }
    
    /**
     * This method validates input, and creates and returns a new Canvas object.
     * 
     * @return new Canvas object
     * @throws IllegalArgumentException when input values are invalid
     * @throws NullPointerException when input values are null
     */
    public Canvas build() {
      if (minPenSize < 1) {
        throw new IllegalArgumentException("Min pen size must be greater or equal to one");
      }
      
      if (maxPenSize <= minPenSize) {
        throw new IllegalArgumentException("Max pen size must be greater than min pen size");
      }      
      
      if (penSize < minPenSize || penSize > maxPenSize) {
        throw new IllegalArgumentException("Pen Size outsize min/max bounds." + 
          " Min " + minPenSize + " Max " + maxPenSize);
      }
      
      if (penShape == null) {
        throw new NullPointerException("Pen shape must be non-null");
      }
      
      return new Canvas(this);
    }

  };
  
  private Canvas(Builder builder) {
    this.currentElement = null;
    
    this.foregroundColor = builder.foregroundColor;
    this.backgroundColor = builder.backgroundColor;
    
    this.penSize = builder.penSize;
    this.penShape = builder.penShape;
    
    this.minPenSize = builder.minPenSize;
    this.maxPenSize = builder.maxPenSize;
  }
  
  /*
   * Fire Events notify the subscribed listeners of changes to this canvas object.
   */
  private void firePaintEvent(Painting action) {
    for (ICanvasListener l: listeners) {
      l.paint(action);
    }    
  }
  
  private void fireColorEvent() {
    for (ICanvasListener l: listeners) {
      l.updateColor(foregroundColor, backgroundColor);
    }    
  }

  private void firePenEvent() {
    for (ICanvasListener l: listeners) {
      l.updatePen(penSize, penShape);
    }    
  }

  /**
   * Returns an iterator for the shapes that have been drawn on this canvas.
   * The remove method is disabled and will return UnsupportedOperationException when called.
   */
  @Override
  public Iterator<ICShape> iterator() {
    return new CanvasIterator(elements);
  }

  @Override
  public void setBackgroundColor(int color) {
    this.backgroundColor = color;
    
    fireColorEvent();
    firePaintEvent(Painting.UPDATED);
  }

  @Override
  public int getBackgroundColor() {
    return backgroundColor;
  }

  @Override
  public void setForegroundColor(int color) {
    this.foregroundColor = color;
    
    fireColorEvent();
  }

  @Override
  public void setPenSize(int size) {
    if (size < minPenSize || size > maxPenSize) {
      throw new IllegalArgumentException("Pen Size outsize min/max bounds." + 
        " Min " + minPenSize + " Max " + maxPenSize);
    }
    
    penSize = size;
    
    firePenEvent();
  }

  @Override
  public void setPenShape(PenShape shape) {
    if (shape == null) {
      throw new NullPointerException("Pen shape must be non-null");
    }
    
    this.penShape = shape;
    
    firePenEvent();
  }

  @Override
  public int getForegroundColor() {
    return foregroundColor;
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
  public void draw(int xCoordinate, int yCoordinate) {
    if (currentElement == null) {
      throw new IllegalStateException("begin drawing not called");
    }
    
    currentElement.addShape(new CShape(xCoordinate, yCoordinate, 
        foregroundColor, penSize, penShape ));
    
    firePaintEvent(Painting.INPROGRESS);
  }

  @Override
  public boolean beginDrawing() {
    if (currentElement != null) {
      throw new IllegalStateException("finish drawing not called");
    }
    
    currentElement = new CElement();
    boolean ret = elements.add(currentElement);
    
    if (ret) {
      firePaintEvent(Painting.START);
    }
    
    return ret;
  }
  
  @Override
  public boolean finishDrawing() {
    if (currentElement == null) {
      throw new IllegalStateException("begin drawing not called");      
    }
    currentElement = null;
    
    firePaintEvent(Painting.END);
    
    return true;
  }
  
  @Override
  public boolean undo() {
    if (elements.size() > 0) {
      elements.remove(elements.size()-1);
      
      firePaintEvent(Painting.REVERTED);
      return true;
    }
    
    return false;
  }

  @Override
  public boolean clearAll() {
    if (elements.size() > 0) {
      elements.clear();
      
      firePaintEvent(Painting.CLEARED);
      return true;      
    }
    return false;
  }

  @Override
  public int getMaxPenSize() {
    return maxPenSize;
  }
  
  @Override
  public int getMinPenSize() {
    return minPenSize;
  }

  @Override
  public boolean addListener(ICanvasListener listener) {
    if (listener == null) {
      throw new NullPointerException("listener is null");
    }
    
    return listeners.add(listener);
  }

  @Override
  public boolean removeListener(ICanvasListener listener) {
    if (listener == null) {
      throw new NullPointerException("listener is null");
    }
    
    return listeners.remove(listener);
  }

  @Override
  public ICanvas newInstance() {
    return new Canvas.Builder().backgroundColor(this.backgroundColor)
        .foregroundColor(this.foregroundColor).penSize(this.penSize).penShape(this.penShape)
        .build();
  }

  /**
   * This method returns friendly string representation of some of the values of this canvas 
   * object.
   * The exact representation are unspecified, subject to change, and should not be used to
   * infer the canvas settings.
   * 
   * A typical string representation will take the following format:
   * [Foreground Color: %d, Background Color: %d, Pen Size: %d, Min Pen Size: %d, Max Pen Size: %d,
   * Pen Shape: %s]
   */  
  @Override
  public String toString() {
    return String.format("[Foreground Color: %d, Background Color: %d, "
        + "Pen Size: %d, Min Pen Size: %d, Max Pen Size: %d, Pen Shape: %s]", 
        foregroundColor, backgroundColor, penSize, minPenSize, maxPenSize, penShape.toString());
  }
  
  /*
   * Private iterator class that iterates through the shape objects of the canvas.
   * The shapes are nested in element objects.
   * 
   * @author cpp270
   *
   */
  private class CanvasIterator implements Iterator<ICShape> {
    private Iterator<CElement> elementsItr;
    private Iterator<ICShape> shapeItr;
    
    private CanvasIterator(List<CElement> elements) {
      elementsItr = elements.iterator();
      if (elementsItr.hasNext()) {
        shapeItr = elementsItr.next().iterator();
      }
    }
    
    @Override
    public boolean hasNext() {
      if (shapeItr != null && shapeItr.hasNext()) {
        return true;
      } else while(elementsItr != null && elementsItr.hasNext()) {
        shapeItr = elementsItr.next().iterator();
        if (shapeItr != null && shapeItr.hasNext()) {
          return true;
        }
      }
      
      return false;
    }
    
    @Override
    public ICShape next() {
      if (shapeItr != null && shapeItr.hasNext()) {
        return shapeItr.next();
      } else while (elementsItr != null && elementsItr.hasNext()) {
        shapeItr = elementsItr.next().iterator();
        if (shapeItr != null && shapeItr.hasNext()) {
          return shapeItr.next();
        }
      }
      
      throw new NoSuchElementException();
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();      
    }
    
  }  
}
