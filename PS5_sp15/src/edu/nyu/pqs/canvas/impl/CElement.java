package edu.nyu.pqs.canvas.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import edu.nyu.pqs.canvas.api.ICShape;

/**
 * This class associates a group of shapes that have been drawn on a canvas at the "same time".
 * 
 * @author cpp270
 *
 */
public class CElement implements Iterable<ICShape>{
  private List<ICShape> shapes = new ArrayList<ICShape>();
  
  /**
   * Add a shape to this element.
   * 
   * @param shape a new shape to be added
   * @return true if the shape was successfully added or false otherwise
   * @throws NullPointerException when the <code>shape</code> is null
   */
  public boolean addShape(ICShape shape) {
    if (shape == null) {
      throw new NullPointerException("Shape must be non-null");
    }
    return shapes.add(shape);
  }

  /**
   * Returns an iterator for the shapes that are contained in this element object.
   * The remove method is disabled and will return UnsupportedOperationException when called.
   */
  @Override
  public Iterator<ICShape> iterator() {
    return new ElementIterator(shapes);
  }

  /**
   * Private iterator class that iterates through the ICShape.
   * 
   * @author cpp270
   *
   */
  private class ElementIterator implements Iterator<ICShape> {
    private Iterator<ICShape> iterator;
    
    private ElementIterator(List<ICShape> shapes) {
      this.iterator = shapes.iterator();
    }
    
    @Override
    public boolean hasNext() {            
      return iterator!= null && iterator.hasNext();
    }

    @Override
    public ICShape next() { 
      if (iterator == null) {
        throw new NoSuchElementException();
      }
      return iterator.next();
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();      
    }
    
  }
}
