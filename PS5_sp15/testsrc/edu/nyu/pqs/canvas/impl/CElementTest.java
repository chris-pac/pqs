package edu.nyu.pqs.canvas.impl;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

import edu.nyu.pqs.canvas.api.ICShape;
import edu.nyu.pqs.canvas.api.ICShape.PenShape;

public class CElementTest {

  @Test (expected=NullPointerException.class)
  public void testAddShape_NullExceptionWhenShapeIsNull() {
    CElement e = new CElement();
    e.addShape(null);
  }

  
  @Test
  public void testIterator_EmptyWhenNoItemsAdded() {
    CElement e = new CElement();
    Iterator<ICShape> it = e.iterator();

    assertFalse(it.hasNext());
  }

  @Test
  public void testIterator_NotEmptyWhenItemAdded() {
    CElement e = new CElement();
    e.addShape(new CShape(50, 100, 255, 30, PenShape.ROUND));
    Iterator<ICShape> it = e.iterator();

    assertTrue(it.hasNext());

    e.addShape(new CShape(50, 100, 255, 30, PenShape.ROUND));
    it = e.iterator();

    assertTrue(it.hasNext());
  }

  @Test (expected=NoSuchElementException.class)
  public void testIterator_NoSuchElementExcpetionOnNextWhenNoItemsAdded() {
    CElement e = new CElement();
    Iterator<ICShape> it = e.iterator();
    it.next();    
  }  

  @Test
  public void testIterator_NextValidWhenIteratorNextComparedToAddedItems() {
    CElement e = new CElement();
    
    CShape c1 = new CShape(50, 100, 255, 30, PenShape.ROUND);
    e.addShape(c1);

    Iterator<ICShape> it = e.iterator();

    assertTrue(it.next().equals(c1));
    
    CShape c2 = new CShape(50, 100, 255, 30, PenShape.ROUND);
    e.addShape(c2);
    
    it = e.iterator();

    assertTrue(it.next().equals(c1));
    assertTrue(it.next().equals(c2));

    CShape c3 = new CShape(0, 0, 0, 0, PenShape.ROUND);
    e.addShape(c3);

    it = e.iterator();
    assertTrue(it.next().equals(c1));
    assertTrue(it.next().equals(c2));
    assertTrue(it.next().equals(c3));
  }  
  
  @Test (expected=UnsupportedOperationException.class)
  public void testIterator_UnsupportedOperationExceptionWhenRemovedCalled() {
    CElement e = new CElement();
    Iterator<ICShape> it = e.iterator();
    it.remove();
  }
}
