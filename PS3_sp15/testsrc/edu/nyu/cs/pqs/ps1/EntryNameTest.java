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

public class EntryNameTest {
  EntryName nullNames = new EntryName(null, null);
  
  @Test
  public void testHashCode_EqualWhenNamesAreEqualAndNotEmptyOrNull() {
    EntryName name1 = new EntryName("#$%^&*", "");
    EntryName name2 = new EntryName("#$%^&*", "");
    
    name1.setLastName("-500");
    name2.setLastName("-500");
    assertTrue(name1.equals(name2));
    
    assertEquals(name1.hashCode(), name2.hashCode());
  }
  
  @Test
  public void testHashCode_EqualWhenAllValuesAreEmptyStrings() {
    EntryName name1 = new EntryName("", "");
    EntryName name2 = new EntryName("", "");
    assertTrue(name1.equals(name2));
    
    assertEquals(name1.hashCode(), name2.hashCode());
  }  
  
  @Test
  public void testHashCode_EqualWhenFirstNamesAreEqualAndLastNamesAreNull() {
    EntryName name1 = new EntryName("");
    EntryName name2 = new EntryName("");
    assertTrue(name1.equals(name2));
    
    assertEquals(name1.hashCode(), name2.hashCode());
  }
  
  @Test
  public void testHashCode_EqualWhenAllValuesAreNull() {
    EntryName name = new EntryName(null);
    if (name.equals(nullNames)) {
      assertEquals(name.hashCode(), nullNames.hashCode());
    }
  }
  
  @Test
  public void testGetFirstName() {
    assertNull(nullNames.getFirstName());
    
    String firstName = "!@#$%^&*()";
    EntryName nameFirst = new EntryName(firstName);
    nameFirst.setFirstName(firstName);
    assertEquals(firstName, nameFirst.getFirstName());
    
    nameFirst.setFirstName("");
    nameFirst.setLastName("");
    assertEquals(nameFirst.getFirstName(), nameFirst.getLastName());
  }

  @Test
  public void testGetLastName() {
    assertNull(nullNames.getLastName());

    String lastName = "!@#$%^&*()!@#$%^&*()";
    nullNames.setLastName(lastName);
    assertEquals(lastName, nullNames.getLastName());
    
    nullNames.setFirstName("");
    nullNames.setLastName("");
    assertEquals(nullNames.getFirstName(), nullNames.getLastName());
  }

  /*
   * Test Fails because overwritten equals does not check if the input object 
   * has the same reference.
   */
  @Test
  public void testEqualsObject_EqualWhenSameObjectIsCompared() {
    assertTrue(nullNames.equals(nullNames));
  }
  
  @Test
  public void testEqualsObject_EqualWhenDifferentObjectsWithSameValues() {
    EntryName name1 = new EntryName("John", "Smith");
    EntryName name2 = new EntryName("John", "Smith");
    assertTrue(name1.equals(name2));
  }
  
  /*
   * Test Fails due to null values.
   */
  @Test
  public void testEqualsObject_EqualWhenAllValuesAreNull() {
    assertTrue(nullNames.equals(new EntryName(null, null)));
  }

  @Test
  public void testEqualsObject_EqualWhenFirstNameIsNull() {    
    EntryName name1 = new EntryName(null, "Smith");
    EntryName name2 = new EntryName(null, "Smith");
    assertTrue(name1.equals(name2));     
    assertTrue(name2.equals(name1)); 
  }

  @Test
  public void testEqualsObject_EqualWhenLastNameIsNull() {    
    EntryName name1 = new EntryName("John", null);
    EntryName name2 = new EntryName("John", null);
    assertTrue(name1.equals(name2));
    assertTrue(name2.equals(name1));
  }

  @Test
  public void testEqualsObject_NotEqualWhenObjectsHaveDifferentValues() {
    EntryName name = new EntryName("John", null);
    assertFalse(name.equals(new EntryName("Jane", null)));
        
    name.setFirstName(" ");
    name.setLastName(" ");
    assertFalse(name.equals(new EntryName("", "")));
    
    EntryName name1 = new EntryName(null, "Smith");
    EntryName name2 = new EntryName(null, "FooBar");
    assertFalse(name1.equals(name2));         
  }

  @Test
  public void testEqualsObject_NotEqualWhenDifferentInstanceObjects() {
    EntryName name = new EntryName(null);
    Object o = new Object();
    assertFalse(name.equals(o));
  }

  @Test
  public void testToString() {
    // toString does not specify exact format so we can only test:
    // 1. that string is not empty or null when we set values
    // 2. Our set values do show up in toString
    EntryName name = new EntryName("John", "Smith");
    assertNotEquals("empty string", "", name.toString());
    assertNotEquals("null string", null, name.toString());
    
    assertTrue(name.toString().contains("John"));
    assertTrue(name.toString().contains("Smith"));
  }
}
