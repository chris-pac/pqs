package edu.nyu.pqs.canvas.impl;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.nyu.pqs.canvas.api.ICShape.PenShape;

public class CShapeTest {

  @Test
  public void testHashCode_EqualWhenObjectsAreEqual() {
    CShape c1 = new CShape(50, 100, 255, 30, PenShape.ROUND);
    CShape c2 = new CShape(50, 100, 255, 30, PenShape.ROUND);
    
    assertEquals(c1.hashCode(), c2.hashCode());
    
    c1 = new CShape(0, 0, 0, 0, PenShape.SQUARE);
    c2 = new CShape(0, 0, 0, 0, PenShape.SQUARE);
    
    assertEquals(c1.hashCode(), c2.hashCode());
  }

  @Test
  public void testGetColor() {
    int color = 255;
    CShape c1 = new CShape(50, 100, color, 30, PenShape.ROUND);
    assertEquals(color, c1.getColor());

    color = 0;
    assertNotEquals(color, c1.getColor());
}

  @Test
  public void testGetPenSize() {
    int penSize = 30;
    CShape c1 = new CShape(50, 100, 255, penSize, PenShape.ROUND);
    assertEquals(penSize, c1.getPenSize());
    
    penSize = 0;
    assertNotEquals(penSize, c1.getPenSize());    
  }

  @Test
  public void testGetPenShape() {
    CShape c1 = new CShape(50, 100, 255, 30, PenShape.ROUND);
    CShape c2 = new CShape(50, 100, 255, 30, PenShape.SQUARE);
    
    assertEquals(PenShape.ROUND, c1.getPenShape());    
    assertEquals(PenShape.SQUARE, c2.getPenShape());
    
    assertNotEquals(PenShape.SQUARE, c1.getPenShape());    
    assertNotEquals(PenShape.ROUND, c2.getPenShape());
  }

  @Test (expected=NullPointerException.class)
  public void testGetPenShape_NullPointerExceptionWhenPenShapeIsNull() {
    new CShape(50, 100, 255, 30, null);
  }
  
  @Test
  public void testGetX() {
    int x = 100;
    CShape c1 = new CShape(x, 100, 255, 30, PenShape.ROUND);
    assertEquals(x, c1.getX());
    
    x=0;
    assertNotEquals(x, c1.getX());    
  }

  @Test
  public void testGetY() {
    int y = 100;
    CShape c1 = new CShape(0, y, 255, 30, PenShape.ROUND);
    assertEquals(y, c1.getY());
    
    y=0;
    assertNotEquals(y, c1.getY());    
  }

  @Test
  public void testEqualsObject_EqualWhenValuesAreTheSame() {
    CShape c1 = new CShape(50, 100, 255, 30, PenShape.ROUND);
    CShape c2 = new CShape(50, 100, 255, 30, PenShape.ROUND);

    assertTrue(c1.equals(c1));
    assertTrue(c1.equals(c2));
  }

  @Test
  public void testEqualsObject_NotEqualWhenValuesAreNotTheSame() {
    CShape c1 = new CShape(0, 100, 255, 30, PenShape.ROUND);
    CShape c2 = new CShape(50, 100, 255, 30, PenShape.ROUND);

    assertFalse(c1.equals(c2));
    
    c1 = new CShape(50, 0, 255, 30, PenShape.ROUND);
    assertFalse(c1.equals(c2));

    c1 = new CShape(50, 100, 0, 30, PenShape.ROUND);
    assertFalse(c1.equals(c2));

    c1 = new CShape(50, 100, 255, 0, PenShape.ROUND);
    assertFalse(c1.equals(c2));

    c1 = new CShape(50, 100, 255, 30, PenShape.SQUARE);
    assertFalse(c1.equals(c2));
  }

  @Test
  public void testEqualsObject_NotEqualWhenDifferentInstance() {
    CShape c1 = new CShape(0, 100, 255, 30, PenShape.ROUND);
    assertFalse(c1.equals(new Object()));
  }
  
  @Test
  public void testToString() {
    CShape c1 = new CShape(0, 0, 0, 0, PenShape.ROUND);
    
    assertNotEquals("null test",c1.toString(), null);
    
    assertFalse("empty test",c1.toString().isEmpty());
  }

}
