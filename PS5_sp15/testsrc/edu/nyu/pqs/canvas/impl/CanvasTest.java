package edu.nyu.pqs.canvas.impl;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

import edu.nyu.pqs.canvas.api.ICShape;
import edu.nyu.pqs.canvas.api.ICShape.PenShape;
import edu.nyu.pqs.canvas.api.ICanvasListener;
import edu.nyu.pqs.canvas.api.ICanvasListener.Painting;

public class CanvasTest {

  /*
   * Helper class used to test if listeners are notified properly
   */
  class MockListenerTest implements ICanvasListener {
    boolean updatePenFired = false;
    boolean updateColorFired = false;
    boolean updatePaintFired = false;
    
    int penSize = 0;
    PenShape penShape = null;
    
    int foregroundColor = 0;
    int backgroundColor = 0;
    
    Painting action = null;
    
    public MockListenerTest() {
      
    }

    public MockListenerTest(Canvas canvas) {
      if (canvas != null) {
        canvas.addListener(this);
      }
    }

    @Override
    public void paint(Painting action) {
      this.action = action;
      updatePaintFired = true;
    }

    @Override
    public void updatePen(int penSize, PenShape shape) {
      this.penSize = penSize;
      this.penShape = shape;
      updatePenFired = true;
    }

    @Override
    public void updateColor(int foreground, int background) {
      this.foregroundColor = foreground;
      this.backgroundColor = background;
      updateColorFired = true;      
    }
    
  };
  
  public MockListenerTest[] createListeners(int quantityToCreate, Canvas canvas) {
    MockListenerTest[] listeners = new MockListenerTest[quantityToCreate];
    for (int i = 0; i < listeners.length; i++) {
      listeners[i] = new MockListenerTest(canvas);
    }
    
    return listeners;
  }

  /*
   * Helper class to quickly call multiple draws on a canvas
   */
  class CoordPairTest {
    public int Y;
    public int X;
    public CoordPairTest(int x, int y) {
      X = x;
      Y = y;
    }
  }
  
  @Test (expected=IllegalArgumentException.class)
  public void testBuilder_IllegalArgExceptionWhenMinPenSizeIsGreaterThanMaxPenSize() {
    new Canvas.Builder().minPenSize(50).maxPenSize(25).build();
  }
  
  @Test (expected=NullPointerException.class)
  public void testBuilder_NullPtrExceptionWhenPenShapeIsNull() {
    new Canvas.Builder().penShape(null).build();
  }

  @Test (expected=IllegalArgumentException.class)
  public void testBuilder_IllegalArgExceptionWhenMinPenSizeIsLessThanOne() {
    new Canvas.Builder().minPenSize(0).build();
  }

  @Test (expected=IllegalArgumentException.class)
  public void testBuilder_IllegalArgExceptionWhenPenSizeIsLessThanMinPenSize() {
    new Canvas.Builder().minPenSize(5).build();
  }

  @Test (expected=IllegalArgumentException.class)
  public void testBuilder_IllegalArgExceptionWhenPenSizeIsGreaterThanMaxPenSize() {
    new Canvas.Builder().maxPenSize(25).penSize(26).build();
  }

  /*
   * Helper method to draw on a canvas by first doing number of begin and finish draws without
   * actually drawing anything. Followed by the actuals draws.
   */
  private Iterator<ICShape> genCanavsIteratorHelper(int numBeginFinishFirst, 
      CoordPairTest[] xyCoordPairs) {
    Canvas canvas = new Canvas.Builder().build();
    while(numBeginFinishFirst != 0) {
      numBeginFinishFirst--;
      canvas.beginDrawing();
      canvas.finishDrawing();
    }   
    
    if (xyCoordPairs != null) {
      for (CoordPairTest p : xyCoordPairs) {
        canvas.beginDrawing();
        canvas.draw(p.X, p.Y);
        canvas.finishDrawing();        
      }      
    }

    return canvas.iterator();
  }
  
  @Test
  public void 
  testIterator_OneItemPresentWhenDrawCalledAfterThreeBeginAndFinishInvocationWithoutDraw() {
    CoordPairTest pair = new CoordPairTest(5, 8);

    Iterator<ICShape> it = genCanavsIteratorHelper(3, new CoordPairTest[] {pair});
    assertTrue(it.hasNext());
    
    ICShape cs = it.next();
    assertNotEquals(cs, null);
    assertEquals(cs.getX(), pair.X);
    assertEquals(cs.getY(), pair.Y);
    
    assertFalse(it.hasNext());
  }

  @Test
  public void testIterator_ItemsPresentWhenAddedOnSingleBeginFinishDrawing() {
    CoordPairTest pair = new CoordPairTest(5, 8);

    Iterator<ICShape> it = genCanavsIteratorHelper(0, new CoordPairTest[] {pair, pair, pair});
    assertTrue(it.hasNext());
    assertNotEquals(it.next(), null);

    assertTrue(it.hasNext());
    assertNotEquals(it.next(), null);

    assertTrue(it.hasNext());
    assertNotEquals(it.next(), null);

    assertFalse(it.hasNext());
  }

  @Test
  public void testIterator_NextValidWhenItemsAddedButHasNextNotCalled() {
    CoordPairTest[] pairs = {new CoordPairTest(9, 15), new CoordPairTest(9, 15), 
        new CoordPairTest(200, 20), new CoordPairTest(12, 12), new CoordPairTest(9000, 9001)};
    Iterator<ICShape> it = genCanavsIteratorHelper(7,pairs);
    
    for (CoordPairTest p : pairs) {
      ICShape cs  = it.next();
      assertEquals(cs.getX(), p.X);
      assertEquals(cs.getY(), p.Y);
    }
  }
  
  @Test (expected=NoSuchElementException.class)
  public void testIterator_NoSuchElementExceptionWhenNoItemsDrawn() {
    Iterator<ICShape> it =  new Canvas.Builder().build().iterator();
    it.next();
  }

  @Test (expected=NoSuchElementException.class)
  public void testIterator_NoSuchElementExceptionWhenNoItemsDrawnButBeginAndFinishCalled() {
    Iterator<ICShape> it =  genCanavsIteratorHelper(4,null);
    it.next();
  }

  @Test (expected=UnsupportedOperationException.class)
  public void testIterator_UnsupportedOperationExceptionWhenRemovedCalled() {
    new Canvas.Builder().build().iterator().remove();
  }

  @Test
  public void testGetBackgroundColor() {
    Canvas canvas = new Canvas.Builder().backgroundColor(255).build();
    assertEquals(canvas.getBackgroundColor(), 255);
  }

  @Test
  public void testGetForegroundColor() {
    Canvas canvas = new Canvas.Builder().foregroundColor(-300).build();
    assertEquals(canvas.getForegroundColor(), -300);
  }

  @Test
  public void testGetPenSize() {
    Canvas canvas = new Canvas.Builder().penSize(20).build();
    assertEquals(canvas.getPenSize(), 20);
  }

  @Test
  public void testGetPenShape() {
    for (PenShape s : PenShape.values()) {
      Canvas canvas = new Canvas.Builder().penShape(s).build();
      assertEquals(s.toString(), canvas.getPenShape(), s);
    }
  }

  @Test (expected=IllegalStateException.class)
  public void testDraw_IllegalStateExceptionWhenDrawCalledWithoutBeginDrawFirst() {
    Canvas canvas = new Canvas.Builder().build();
    canvas.draw(0, 0);
  }

  
  @Test
  public void testDraw_EventFiredWhenItemsDrawn() {
    int numLs = 3;
    Canvas canvas = new Canvas.Builder().build();
    MockListenerTest[] lsSet1  = createListeners(numLs, canvas);
    canvas.beginDrawing();
    canvas.draw(5, 20);
    canvas.draw(500, 0);
    
    
    for (MockListenerTest l : lsSet1) {
      assertTrue(l.updatePaintFired);
      assertEquals(l.action, Painting.INPROGRESS);
      //reset
      l.updatePaintFired = false;
      l.action = null;
    }
    
    // remove all but the last one
    for (int i = 0; i < numLs - 1; i++) {
      canvas.removeListener(lsSet1[i]);
    }
 
    // add a new one
    MockListenerTest ls = new MockListenerTest(canvas);

    //draw
    canvas.draw(5, 20);
    assertTrue(lsSet1[numLs - 1].updatePaintFired);
    assertEquals(lsSet1[numLs - 1].action, Painting.INPROGRESS);

    assertTrue(ls.updatePaintFired);
    assertEquals(ls.action, Painting.INPROGRESS);

    canvas.finishDrawing();
  }

  @Test (expected=IllegalStateException.class)
  public void testBeginDrawing_IllegalStateExceptionWhenCalledAgainWithoutCallingFinishDrawing() {
    Canvas canvas = new Canvas.Builder().build();
    canvas.beginDrawing();
    canvas.beginDrawing();
  }

  @Test (expected=IllegalStateException.class)
  public void testBeginDrawing_IllegalStateExceptionWhenCalledTwiceInARow() {
    Canvas canvas = new Canvas.Builder().build();
    canvas.beginDrawing();
    canvas.finishDrawing();
    canvas.beginDrawing();
    canvas.beginDrawing();
  }

  @Test (expected=IllegalStateException.class)
  public void testFinishDrawing_IllegalStateExceptionWhenCalledAgainWithoutCallingBeginDrawing() {
    Canvas canvas = new Canvas.Builder().build();
    canvas.finishDrawing();
    canvas.finishDrawing();
  }

  @Test (expected=IllegalStateException.class)
  public void testFinishDrawing_IllegalStateExceptionWhenCalledTwiceInARow() {
    Canvas canvas = new Canvas.Builder().build();
    canvas.beginDrawing();
    canvas.finishDrawing();
    canvas.finishDrawing();
  }

  @Test
  public void testUndo_EventFiredWhenItemsRemoved() {
    Canvas canvas = new Canvas.Builder().build();
    MockListenerTest[] ls = createListeners(7, canvas);
    canvas.beginDrawing();
    canvas.draw(5, 20);
    canvas.draw(500, 0);
    canvas.finishDrawing();

    canvas.beginDrawing();
    canvas.draw(5, 20);
    canvas.draw(500, 0);
    canvas.finishDrawing();

    canvas.undo();
    for (MockListenerTest l : ls) {
      assertTrue(l.updatePaintFired);
      assertEquals(l.action, Painting.REVERTED);
    }

    // reset
    for (MockListenerTest l : ls) {
      l.updatePaintFired = false;
      l.action = null;
    }
    
    canvas.undo();
    for (MockListenerTest l : ls) {
      assertTrue(l.updatePaintFired);
      assertEquals(l.action, Painting.REVERTED);
    }
  }

  @Test
  public void testUndo_FalseWhenNoItemsToUndo() {
    Canvas canvas = new Canvas.Builder().build();
    assertFalse("1st",canvas.undo());
    assertFalse("2nd",canvas.undo());
  }

  @Test
  public void testUndo_ValidWhenNoItemsToUndoAfterAnotherUndoThatUndidAllChanges() {
    Canvas canvas = new Canvas.Builder().build();
    canvas.beginDrawing();
    canvas.draw(5, 20);
    canvas.draw(500, 0);
    canvas.finishDrawing();
    
    assertTrue(canvas.undo());
    assertFalse(canvas.undo());
  }

  @Test
  public void testUndo_FalseWhenNoItemsClearedAll() {
    Canvas canvas = new Canvas.Builder().build();
    canvas.beginDrawing();
    canvas.draw(5, 20);
    canvas.draw(500, 0);
    canvas.finishDrawing();

    canvas.beginDrawing();
    canvas.draw(5, 20);
    canvas.draw(500, 0);
    canvas.finishDrawing();

    assertTrue(canvas.clearAll());
    assertFalse(canvas.undo());
  }
  
  @Test
  public void testClearAll_FalseWhenNoItems() {
    Canvas canvas = new Canvas.Builder().build();
    assertFalse("1st",canvas.clearAll());
    assertFalse("2nd",canvas.clearAll());
  }

  @Test
  public void testClearAll_EventFiredWhenItemsCleared() {
    Canvas canvas = new Canvas.Builder().build();
    MockListenerTest[] ls = createListeners(7, canvas);
    canvas.beginDrawing();
    canvas.draw(5, 20);
    canvas.draw(500, 0);
    canvas.finishDrawing();

    canvas.beginDrawing();
    canvas.draw(5, 20);
    canvas.draw(500, 0);
    canvas.finishDrawing();

    assertTrue(canvas.clearAll());
    for (MockListenerTest l : ls) {
      assertTrue(l.updatePaintFired);
      assertEquals(l.action, Painting.CLEARED);
    }
  }

  @Test
  public void testClearAll_EventNotFiredWhenItemsClearedSecondTimeWithoutDrawing() {
    Canvas canvas = new Canvas.Builder().build();
    MockListenerTest[] ls = createListeners(7, canvas);
    canvas.beginDrawing();
    canvas.draw(5, 20);
    canvas.draw(500, 0);
    canvas.finishDrawing();

    canvas.beginDrawing();
    canvas.draw(5, 20);
    canvas.draw(500, 0);
    canvas.finishDrawing();

    canvas.clearAll();
    for (MockListenerTest l : ls) {
      assertTrue(l.updatePaintFired);
      assertEquals(l.action, Painting.CLEARED);
    }

    // reset
    for (MockListenerTest l : ls) {
      l.updatePaintFired = false;
      l.action = null;
    }
    
    canvas.clearAll();
    for (MockListenerTest l : ls) {
      assertFalse(l.updatePaintFired);
      assertEquals(l.action, null);
    }
  }
  
  @Test
  public void testGetMaxPenSize() {
    Canvas canvas = new Canvas.Builder().maxPenSize(1000).build();
    assertEquals(canvas.getMaxPenSize(), 1000);
  }

  @Test
  public void testGetMinPenSize() {
    Canvas canvas = new Canvas.Builder().minPenSize(5).penSize(10).build();
    assertEquals(canvas.getMinPenSize(), 5);
  }

  @Test (expected=NullPointerException.class)
  public void testAddListener_NullPtrExceptionWhenNullListenerAddedToCanvas() {
    Canvas canvas = new Canvas.Builder().build();
    canvas.addListener(null);
  }

  @Test
  public void testAddListener() {
    Canvas canvas = new Canvas.Builder().build();
    for (MockListenerTest l : createListeners(5, null)) {
      assertTrue(canvas.addListener(l));
    }
  }
  
  @Test
  public void testAddListener_TrueWhenTryingToAddSameListenerTwice() {
    Canvas canvas = new Canvas.Builder().build();
    MockListenerTest l = new MockListenerTest();
    assertTrue(canvas.addListener(l));
    assertTrue(canvas.addListener(l));
  }
  
  @Test (expected=NullPointerException.class)
  public void testRemoveListener_NullPtrExceptionWhenNullListenerAddedToCanvas() {
    Canvas canvas = new Canvas.Builder().build();
    canvas.removeListener(null);
  }
  
  @Test
  public void testRemoveListener_FalseWhenTryingToRemoveListenerThatWasNotAdded() {
    Canvas canvas = new Canvas.Builder().build();
    assertFalse(canvas.removeListener(new MockListenerTest()));
  }

  @Test
  public void testRemoveListener_FalseWhenTryingToRemoveSameListenerTwice() {
    Canvas canvas = new Canvas.Builder().build();
    MockListenerTest l = new MockListenerTest();
    assertTrue(canvas.addListener(l));
    assertTrue(canvas.removeListener(l));
    assertFalse(canvas.removeListener(l));
  }


  @Test
  public void testRemoveListener() {
    Canvas canvas = new Canvas.Builder().build();
    MockListenerTest[] ls = createListeners(4, null);
    for (MockListenerTest l : ls) {
      assertTrue(canvas.addListener(l));
    }
    
    for (MockListenerTest l : ls) {
      assertTrue(canvas.removeListener(l));
    }
  } 

  @Test (expected=IllegalArgumentException.class)
  public void testSetPenSize_IllegalArgExceptionWhenPenSizeLessThanMinPenSize() {
    Canvas canvas = new Canvas.Builder().build();
    canvas.setPenSize(canvas.getMinPenSize() - 1);
  }
  
  @Test (expected=IllegalArgumentException.class)
  public void testSetPenSize_IllegalArgExceptionWhenPenSizeGreaterThanMaxPenSize() {
    Canvas canvas = new Canvas.Builder().build();
    canvas.setPenSize(canvas.getMaxPenSize() + 1);
  }

  @Test
  public void testSetPenSize_EventFiredWhenPenSizeChanged() {
    int val = 15;
    
    Canvas canvas = new Canvas.Builder().penSize(12).build();
    MockListenerTest[] ls = createListeners(7, canvas);
    canvas.setPenSize(val);
    for (MockListenerTest l : ls) {
      assertTrue(l.updatePenFired);
      assertEquals(l.penSize, val);
    }
  }

  @Test (expected=NullPointerException.class)
  public void testSetPenShape_NullPtrExceptionWhenNullPenShapeSet() {
    Canvas canvas = new Canvas.Builder().build();
    canvas.setPenShape(null);
  }
  
  @Test
  public void testSetPenShape_EventFiredWhenPenShapeChanged() {
    PenShape val = PenShape.ROUND;
    
    Canvas canvas = new Canvas.Builder().penShape(PenShape.SQUARE).build();
    MockListenerTest[] ls = createListeners(7, canvas);
    canvas.setPenShape(val);
    for (MockListenerTest l : ls) {
      assertTrue(l.updatePenFired);
      assertEquals(l.penShape, val);
    }
  }

  @Test
  public void testSetForegroundColor_EventFiredWhenForegroundColorChanged() {
    int val = 15;
    
    Canvas canvas = new Canvas.Builder().foregroundColor(245).build();
    MockListenerTest[] ls = createListeners(7, canvas);
    canvas.setForegroundColor(val);
    for (MockListenerTest l : ls) {
      assertTrue(l.updateColorFired);
      assertEquals(l.foregroundColor, val);
    }
  }

  @Test
  public void testSetBackgroundColor_EventFiredWhenBackgroundColorChanged() {
    int val = 15;
    
    Canvas canvas = new Canvas.Builder().backgroundColor(245).build();
    MockListenerTest[] ls = createListeners(7, canvas);
    canvas.setBackgroundColor(val);
    for (MockListenerTest l : ls) {
      assertTrue(l.updateColorFired);
      assertTrue(l.updatePaintFired);
      assertEquals(l.backgroundColor, val);
      assertEquals(l.action, Painting.UPDATED);
    }
  }
  
  @Test
  public void testNewInstance_DistinctWhenCreated() {
    Canvas canvasOriginal = new Canvas.Builder().build();
    
    Canvas distinctCanvas = (Canvas)canvasOriginal.newInstance();
    assertNotEquals(canvasOriginal, distinctCanvas);
    assertNotEquals(null, distinctCanvas);
  }

  @Test
  public void testToString_NotEmptyOrNull() {
    Canvas canvas = new Canvas.Builder().build();
    assertNotEquals(canvas.toString(), null);
    assertFalse(canvas.toString().isEmpty());
  }
}
