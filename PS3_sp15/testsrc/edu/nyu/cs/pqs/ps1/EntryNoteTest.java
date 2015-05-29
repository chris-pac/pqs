package edu.nyu.cs.pqs.ps1;

/*
 * @author Chris Pac
 * @version 1.0
 * @since 2015-03-31
 * 
 * Tests Assume that null values are Valid
 */

import static org.junit.Assert.*;

import org.junit.Test;

public class EntryNoteTest {
  EntryNote nullNote = new EntryNote(null);
  
  @Test
  public void testHashCode_EqualWhenObjectsValuesAreEqual() {
    EntryNote note1 = new EntryNote("A ubiquitous, general-purpose, "
        + "programming language.");
    EntryNote note2 = new EntryNote("A ubiquitous, general-purpose, "
        + "programming language.");
    assertTrue(note1.equals(note2));
      
    assertEquals(note1.hashCode(), note2.hashCode());
    
    note1.setText("-500");
    note2.setText("-500");
    assertTrue(note1.equals(note2));
    
    assertEquals(note1.hashCode(), note2.hashCode());
  }

  /*
   * Test Fails due to null values.
   */
  @Test
  public void testHashCode_EqualWhenObjectsAreEqualAndValuesAreNull() {
    EntryNote nullNote2 = new EntryNote(null);
    assertTrue(nullNote.equals(nullNote2));
    
    assertEquals(nullNote.hashCode(), nullNote2.hashCode());
  }
  
  @Test
  public void testGetText() {
    String note = "A ubiquitous, general-purpose, "
        + "programming language.";
    nullNote.setText(note);
    assertEquals(note, nullNote.getText());
    
    nullNote.setText(null);
    assertEquals(null, nullNote.getText());

    nullNote.setText("");
    assertEquals("", nullNote.getText());
  }

  @Test
  public void testEqualsObject_EqualWhenDifferentObjectsWithSameValues() {
    EntryNote note1 = new EntryNote("A ubiquitous, general-purpose, "
        + "programming language.");
    EntryNote note2 = new EntryNote("A ubiquitous, general-purpose, "
        + "programming language.");
    assertTrue(note1.equals(note2));

    note1.setText("foo");    
    note2.setText("bar");
    note1.setText("");
    note2.setText("");
    assertTrue(note1.equals(note2));    
  }

  /*
   * Test Fails due to null values.
   */
  @Test
  public void testEqualsObject_EqualWhenAllValuesAreNull() {
    EntryNote nullNote2 = new EntryNote(null);
    assertTrue("all values null",nullNote.equals(nullNote2));
    
    nullNote.setText("50");
    nullNote2.setText("100");
    nullNote.setText(null);
    nullNote2.setText(null);
    assertTrue(nullNote.equals(nullNote2));
  }

  /*
   * Test Fails because overwritten equals does not check if the input object 
   * has the same reference.
   */
  @Test
  public void testEqualsObject_EqualWhenSameObjectIsCompared() {
    assertTrue(nullNote.equals(nullNote));
  }
  
  @Test
  public void testEqualsObject_NotEqualWhenDifferentInstanceObjects() {
    EntryNote note1 = new EntryNote("Hello");
    Object o = new Object();
    assertFalse(note1.equals(o));
  }
  
  @Test
  public void testEqualsObject_NotEqualWhenObjectsHaveDifferentValues() {
    assertFalse(nullNote.equals(null));
    
    EntryNote note1 = new EntryNote("A ubiquitous, general-purpose, "
        + "programming language.");
    EntryNote note2 = new EntryNote("A ubiquitous, gxneral-purpose, "
        + "programming language.");
    assertFalse(note1.equals(note2));
    
    note2.setText("5");
    note1.setText("");
    assertFalse(note1.equals(note2));
  }
  
  @Test
  public void testToString() {
    // toString does not specify exact format so we can only test:
    // 1. that string is not empty or null when we set values
    // 2. Our set values do show up in toString
    EntryNote note = new EntryNote("A ubiquitous, general-purpose, "
        + "programming language.");
    assertNotEquals("empty string", "", note.toString());
    assertNotEquals("null string", null, note.toString());
    
    assertTrue(note.toString().contains("general"));
  }

}
