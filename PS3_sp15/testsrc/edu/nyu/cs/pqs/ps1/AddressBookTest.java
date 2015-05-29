package edu.nyu.cs.pqs.ps1;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.After;

import org.junit.Test;

/*
 * @author Chris Pac
 * @version 1.0
 * @since 2015-03-31
 * 
 * Tests Assume that null values are Valid
 * 
 * Note: 100% coverage does not seem to be doable since readAddressBookFromFile fails
 * at the beginning of the loop at line 194. Forcing a 100% by trying to fix the data would 
 * defeat the purpose of the test.
 * 
 */

public class AddressBookTest {
  String longNote = "Louis Nirenberg, a professor emeritus at New York "
      + "University’s Courant Institute of Mathematical Sciences, has been "
      + "awarded the Abel Prize in Mathematics";
  
  String filePathToCleanUp = "";
  
  private AddressBook helperSetUpAddressBookWithNoneNullValues() {
    EntryPhoneNumber phone1 = new EntryPhoneNumber("10000", "10001", "10002");    
    EntryNote note1 = new EntryNote("A ubiquitous, general-purpose, "
        + "programming language.");   
    EntryName name1 = new EntryName("#$%^&*", "");
    Email email1 = new Email("notan email @#$%^&");
    Address address1 = new Address("s11", "c22", "s33", "z44", "c55");    

    EntryPhoneNumber phone2 = new EntryPhoneNumber("11111", "222222", "33333");    
    EntryNote note2 = new EntryNote(longNote);   
    EntryName name2 = new EntryName("John", "Smith");
    Email email2 = new Email("@@@@@@@@@@@@@");
    Address address2 = new Address("s66", "c77", "s88", "z99", "c23");    

    AddressEntry addEntry1 = new AddressEntry(name1,phone1,email1,address1,note1);
    AddressEntry addEntry2 = new AddressEntry(name2,phone2,email2,address2,note2);
    
    AddressBook book = new AddressBook();
    book.addNewEntry(addEntry1);
    book.addNewEntry(addEntry1);
    book.addNewEntry(addEntry1);
    book.addNewEntry(addEntry2);
    
    return book;
  }
 
  /*
   * Test Fails due to null values. LinkedList uses equals when on object is removed. The equals
   * function fails because of missing null value handling.
   */
  @Test
  public void testRemoveOldEntry_ValidWhenAllValuesNull() {
    // Testing this is equivalent to testing Java LinkedList and it still fails due to nulls
    AddressEntry nullAddEntry1 = new AddressEntry(null,null,null,null,null);
    AddressEntry nullAddEntry2 = new AddressEntry(null,null,null,null,null);
    AddressEntry nullAddEntry3 = new AddressEntry(null,null,null,null,null);
    
    AddressBook book = new AddressBook();
    book.addNewEntry(nullAddEntry1);
    book.addNewEntry(nullAddEntry2);
    book.addNewEntry(nullAddEntry3);
    book.addNewEntry(nullAddEntry3);
    book.addNewEntry(nullAddEntry3);
    book.addNewEntry(nullAddEntry3);
    
    assertTrue("remove one out of many from list",book.removeOldEntry(nullAddEntry3));
    
    assertTrue("remove single item",book.removeOldEntry(nullAddEntry2));
    assertFalse("remove none-existing item", book.removeOldEntry(nullAddEntry2));
  }

  @Test
  public void testSearch_ValidWhenSearchingForPreviouslyAddedValues () {
    AddressBook book = helperSetUpAddressBookWithNoneNullValues();
    assertNotNull("book null", book);
    
    EntryPhoneNumber phone2 = new EntryPhoneNumber("11111", "222222", "33333");    
    EntryNote note2 = new EntryNote(longNote);   
    EntryName name2 = new EntryName("John", "Smith");
    Email email2 = new Email("@@@@@@@@@@@@@");
    Address address2 = new Address("s66", "c77", "s88", "z99", "c23");    

    AddressEntry addEntry2 = new AddressEntry(name2,phone2,email2,address2,note2);
 
    List<AddressEntry> list1;
    
    list1 = book.search(addEntry2);    
    assertNotEquals("list empty after entry search", 0, list1.size());
    assertEquals(list1.get(0), addEntry2);
    
    list1 = book.search(name2);    
    assertNotEquals("list empty after name search", 0, list1.size());
    assertEquals(list1.get(0), addEntry2);
    
    list1 = book.search(phone2);    
    assertNotEquals("list empty after phone search", 0, list1.size());
    assertEquals(list1.get(0), addEntry2);

    list1 = book.search(email2);    
    assertNotEquals("list empty after email search", 0, list1.size());
    assertEquals(list1.get(0), addEntry2);

    list1 = book.search(address2);    
    assertNotEquals("list empty after address search", 0, list1.size());
    assertEquals(list1.get(0), addEntry2);

    list1 = book.search(note2);    
    assertNotEquals("list empty after address search", 0, list1.size());
    assertEquals(list1.get(0), addEntry2);
}

  @Test
  public void testToString() {
    AddressBook book = helperSetUpAddressBookWithNoneNullValues();
    assertNotNull("book null", book);
    
    String s = book.toString();
    
    assertNotEquals("empty string", "", s);
    assertNotEquals("null string", null, s);
    
    assertTrue("contains long note", s.toString().contains(longNote));
  }

  /*
   * The function fails because nextToken on line 194 in AddressBook is given an incorrect
   * string. Instead of reading a phone on ln 191 , it looks like an email is read in.
   */ 
  @Test
  public void testReadAddressBookFromFile_ValidWhenItsDirectSaveAndLoad() {
    AddressBook book1 = helperSetUpAddressBookWithNoneNullValues();
    
    // Save
    File f1 = null;
    String filePath = "";
    try {
      f1 = book1.saveAddressBookToFile();
      assertTrue("file one missing", f1.exists());
      filePath = f1.getPath();
      filePathToCleanUp = filePath;
    } catch (IOException e) {
      fail("save book1 " + e.getMessage());
    }
    
    // Load
    AddressBook book2 = new AddressBook();
    try {
      book2.readAddressBookFromFile(new File(filePath));
      assertEquals("loaded and saved books not equal", book1, book2);
    } catch (IOException e) {
      fail("load book " + e.getMessage());
    }    
  }

  @Test
  public void testSaveAddressBookToFile_ValidWhenSavingMultiAndLoadingLastSavedFromReturnedFile(){
    AddressBook book1 = helperSetUpAddressBookWithNoneNullValues();
    try {
      File f1 = book1.saveAddressBookToFile();
      assertTrue("file one missing", f1.exists());
      filePathToCleanUp = f1.getPath();
    } catch (IOException e) {
      fail("save book1 " + e.getMessage());
    }    
    
    // save a different book second time
    longNote = "Hello World!";
    AddressBook book2 = helperSetUpAddressBookWithNoneNullValues();

    File f2 = null;
    try {
      f2 = book2.saveAddressBookToFile();
      assertTrue("file two missing", f2.exists());
      filePathToCleanUp = f2.getPath();
    } catch (IOException e) {
      fail("save book2 " + e.getMessage());
    }
    
    // load a book from what saveAddressBookToFile returned
    // this assumption is valid since one should be able to load from what save returned
    AddressBook book3 = new AddressBook();
    try {
      book3.readAddressBookFromFile(f2);
      assertEquals("loaded and saved books not equal", book2, book3);
    } catch (IOException e) {
      fail("load book " + e.getMessage());
    }
    
  }

  @After
  public void tearDown() throws Exception {
    File f = new File(filePathToCleanUp);
    if (f.exists()) {
      f.delete();
    }
  }

}
