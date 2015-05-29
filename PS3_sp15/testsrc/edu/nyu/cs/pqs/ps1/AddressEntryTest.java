package edu.nyu.cs.pqs.ps1;

import static org.junit.Assert.*;

import org.junit.Test;

/*
 * @author Chris Pac
 * @version 1.0
 * @since 2015-03-31
 * 
 * Tests Assume that null values are Valid
 */

public class AddressEntryTest {
  AddressEntry nullAddEntry = new AddressEntry(null,null,null,null,null);
  
  @Test
  public void testHashCode_EqualWhenObjectsValuesAreEqual() {
    String[] n = {"FFFF", "LLLLL", "7867", "fgvkj", "alkda", "notan email @#$%^&", 
        "s11", "c22", "s33", "z44", "c55", "Hello World!"};

    AddressEntry addEntry1 = new AddressEntry(new EntryName(n[0], n[1]), 
        new EntryPhoneNumber(n[2], n[3], n[4]), new Email(n[5]), 
        new Address(n[6], n[7], n[8], n[9], n[10]), new EntryNote(n[11]));

    // changed indexes around
    AddressEntry addEntry2 = new AddressEntry(new EntryName(n[0], n[1]), 
        new EntryPhoneNumber(n[2], n[3], n[4]), new Email(n[5]), 
        new Address(n[6], n[7], n[8], n[9], n[10]), new EntryNote(n[11]));
    
    assertTrue(addEntry1.equals(addEntry2));
    
    assertEquals(addEntry1.hashCode(), addEntry2.hashCode());
  }
  
  /*
   * Test Fails due to null values.
   */  
  @Test
  public void testHashCode_EqualWhenObjectsAreEqualAndValuesAreNull() {
    AddressEntry nullAddEntry2 = new AddressEntry(null,null,null,null,null);
    assertTrue(nullAddEntry.equals(nullAddEntry2));
      
    assertEquals(nullAddEntry.hashCode(), nullAddEntry2.hashCode());
  }
  
  @Test
  public void testSimpleSettersAndGetters_ValidWhenValuesAreTheSame() {
    String[] n = {"FFFF", "LLLLL", "7867", "fgvkj", "alkda", "notan email @#$%^&", 
        "s11", "c22", "s33", "z44", "c55", "Hello World!"};

    Object[] os = {new EntryName(n[0], n[1]), 
        new EntryPhoneNumber(n[2], n[3], n[4]), new Email(n[5]), 
        new Address(n[6], n[7], n[8], n[9], n[10]), new EntryNote(n[11])};
    
    nullAddEntry.setEntryName((EntryName)os[0]);
    assertSame("EntryName not the same", os[0], nullAddEntry.getEntryName());

    nullAddEntry.setEntryPhoneNumber((EntryPhoneNumber)os[1]);
    assertSame("EntryPhoneNumber not the same", os[1], nullAddEntry.getEntryPhoneNumber());

    nullAddEntry.setEmail((Email)os[2]);
    assertSame("Email not the same", os[2], nullAddEntry.getEmail());

    nullAddEntry.setAddress((Address)os[3]);
    assertSame("Address not the same", os[3], nullAddEntry.getAddress());

    // inconsistent getter name
    nullAddEntry.setEntryNote((EntryNote)os[4]);
    assertSame("EntryName not the same", os[4], nullAddEntry.getNote());
  }  
  
  @Test
  public void testEqualsObject_EqualWhenDifferentObjectsWithSameValues() {
    String[] n = {"FFFF", "LLLLL", "7867", "fgvkj", "alkda", "notan email @#$%^&", 
        "s11", "c22", "s33", "z44", "c55", "Hello World!"};

    AddressEntry addEntry1 = new AddressEntry(new EntryName(n[0], n[1]), 
        new EntryPhoneNumber(n[2], n[3], n[4]), new Email(n[5]), 
        new Address(n[6], n[7], n[8], n[9], n[10]), new EntryNote(n[11]));

    // changed indexes around
    AddressEntry addEntry2 = new AddressEntry(new EntryName(n[0], n[1]), 
        new EntryPhoneNumber(n[2], n[3], n[4]), new Email(n[5]), 
        new Address(n[6], n[7], n[8], n[9], n[10]), new EntryNote(n[11]));
    
    assertTrue(addEntry1.equals(addEntry2));    
  }

  /*
   * Test Fails due to null values.
   */
  @Test
  public void testEqualsObject_EqualWhenAllValuesAreNull() {
    AddressEntry nullAddEntry2 = new AddressEntry(null,null,null,null,null);;
    assertTrue("all values null",nullAddEntry.equals(nullAddEntry2)); 
  }

  /*
   * Test Fails because overwritten equals does not check if the input object 
   * has the same reference.
   */
  @Test
  public void testEqualsObject_EqualWhenSameObjectIsCompared() {
    assertTrue(nullAddEntry.equals(nullAddEntry));
  }
  
  @Test
  public void testEqualsObject_NotEqualWhenDifferentInstanceObjects() {
    Object o = new Object();
    assertFalse(nullAddEntry.equals(o));
  }
  
  @Test
  public void testEqualsObject_NotEqualWhenObjectsHaveDifferentValues() {
    assertFalse("failed null test", nullAddEntry.equals(null));
    
    String[] n = {"FFFF", "LLLLL", "7867", "fgvkj", "alkda", "notan email @#$%^&", 
        "s11", "c22", "s33", "z44", "c55", "Hello World!"};

    AddressEntry addEntry1 = new AddressEntry(new EntryName(n[0], n[1]), 
        new EntryPhoneNumber(n[2], n[3], n[4]), new Email(n[5]), 
        new Address(n[6], n[7], n[8], n[9], n[10]), new EntryNote(n[11]));

    // changed indexes around
    AddressEntry addEntry2 = new AddressEntry(new EntryName(n[0], n[3]), 
        new EntryPhoneNumber(n[2], n[1], n[4]), new Email(n[5]), 
        new Address(n[6], n[7], n[8], n[9], n[11]), new EntryNote(n[10]));

    assertFalse(addEntry1.equals(addEntry2));
  }  
  
  @Test
  public void testToString() {
    // toString does not specify exact format so we can only test:
    // 1. that string is not empty or null when we set values
    // 2. Our set values do show up in toString
    String[] n = {"FFFF", "LLLLL", "7867", "fgvkj", "alkda", "notan email @#$%^&", 
        "s11", "c22", "s33", "z44", "c55", "Hello World!"};

    AddressEntry addEntry1 = new AddressEntry(new EntryName(n[0], n[1]), 
        new EntryPhoneNumber(n[2], n[3], n[4]), new Email(n[5]), 
        new Address(n[6], n[7], n[8], n[9], n[10]), new EntryNote(n[11]));
    
    assertNotEquals("empty string failed", "", addEntry1.toString());
    assertNotEquals("null failed", null, addEntry1.toString());
    
    String toS = addEntry1.toString();
    for (String s : n) {
      assertTrue("test failed for: "+s,toS.contains(s));
    }
  }
  
  /*
   * Test Fails due to null values.
   */
  @Test
  public void testToString_ValidWhenNullValuesSet() {
    // toString does not specify exact format so we can only test:
    // 1. that string is not empty or null when we set values
    // 2. Our set values do show up in toString
    assertNotEquals("empty string", "", nullAddEntry.toString());
    assertNotEquals("null string", null, nullAddEntry.toString());
  }
}
