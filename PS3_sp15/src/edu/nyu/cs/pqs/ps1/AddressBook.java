package edu.nyu.cs.pqs.ps1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * The address book is the main competent of the AddressLibrary API. It has the following features:
 * Create an empty address book.
 * Add an entry. An entry consists of a name, postal address, phone number, email address, and a note.
 * Remove an entry.
 * Search for an entry by any of the contact properties.
 * Save the address book to a file.
 * Read the address book from a file
 * 
 * @author Tareq Alghamdi taa307@nyu.edu
 *
 */
public class AddressBook {
  private List<AddressEntry> book;
  
  /**
   * This constructor creates empty address book. The data structure for the 
   * Address Book is Linked List
   */
  public AddressBook(){
    this.book = new LinkedList<AddressEntry>();
  }
  
  /**
   * Add new Entry to the address book.
   * @param Address Entry e
   * @return boolean when the item is added
   */
  public boolean addNewEntry(AddressEntry e){
    return book.add(e);
  }
  
  /**
   * Remove old Entry from the address book
   * @param AddressEntry e
   * @return boolean when the item is removed
   */
  public boolean removeOldEntry(AddressEntry e){
    return book.remove(e);
  }
  
  /**
   * Search the AddressBook for whatever is provided. The method is very long because the search
   * might be in anywhere in the AddressBook
   * @param entry
   * @return List of entries with the provided findings
   */
  public List<AddressEntry> search(Object object){
    List<AddressEntry> result = new LinkedList<AddressEntry>();
    // If the object is an Address Entry
    if(object instanceof AddressEntry){
      AddressEntry entry = (AddressEntry) object;
      for (AddressEntry e : book){
        if(e.equals(entry)){
          result.add(e);
        }
      }
    }
    // If the object is Entry Name
    if(object instanceof EntryName){
      EntryName name = (EntryName) object;
      for (AddressEntry e : book){
        if(e.getEntryName().equals(name)){
          result.add(e);
        }
      }
    }
    // If the object is EntryPhoneNumber
    if(object instanceof EntryPhoneNumber){
      EntryPhoneNumber phone = (EntryPhoneNumber) object;
      for (AddressEntry e : book){
        if(e.getEntryPhoneNumber().equals(phone)){
          result.add(e);
        }
      }
    }
    // If the object is Email
    if(object instanceof Email){
      Email email = (Email) object;
      for (AddressEntry e : book){
        if(e.getEmail().equals(email)){
          result.add(e);
        }
      }
    }
    // If the object is Address
    if(object instanceof Address){
      Address address = (Address) object;
      for (AddressEntry e : book){
        if(e.getAddress().equals(address)){
          result.add(e);
        }
      }
    }
    // If the object is EntryNote
    if(object instanceof EntryNote){
      EntryNote note = (EntryNote) object;
      for (AddressEntry e : book){
        if(e.getNote().equals(note)){
          result.add(e);
        }
      }
    }
    return result;
  }
 
  /**
   * Overriden toString()
   * to concatenate entries in the address book into a single string
   * @return String address book
   */
  @Override 
  public String toString(){
    String s = " ";
    for (AddressEntry e : book){
      s = s + e.toString();
    }
    return s;
  }
  
  /**
   * SaveAddressBookToFile it saves the address book linked list into a file and returns that file
   * @return File
   * @throws IOException 
   */
  public File saveAddressBookToFile() throws IOException{
    byte[] entryBytes = null;
    String path = System.getProperty("user.dir") + "AddressBook.txt";
    File file = new File(path);
    if(!file.exists()) {
      file.createNewFile();
    }
    FileOutputStream output = new FileOutputStream(file);
    for(AddressEntry e : book){
      entryBytes = e.toString().getBytes();
      output.write(entryBytes);
    }
    output.flush();
    output.close();
    return file;
  }
  
  /**
   * readAddressBookFromFile is to read the file contents and assigns it to an address book
   * assuming the entries are listed in line delimited base. 
   * @param file
   * @return an address book
   * @throws IOException 
   */
  public AddressBook readAddressBookFromFile(File file) throws IOException{
    AddressBook book = new AddressBook();
    BufferedReader br = new BufferedReader(new FileReader(file));
    String line;
    String firstName;
    String lastName;
    String phone;
    String email;
    String street;
    String city;
    String zip;
    String state;
    String country;
    String note;
    StringTokenizer token;
    String a;
    String p;
    String n;
    EntryName name;
    EntryPhoneNumber number;
    Email emailObj;
    Address address;
    EntryNote noteObj;
    AddressEntry e;
    while ((line = br.readLine()) != null) {
      firstName = br.readLine();
      lastName = br.readLine();
      name = new EntryName(firstName, lastName);
      phone = br.readLine();
      token = new StringTokenizer(phone, "-");
      a = token.nextToken();
      p = token.nextToken();
      n = token.nextToken();
      number = new EntryPhoneNumber(a,p,n);
      email = br.readLine();
      emailObj = new Email(email);
      street = br.readLine();
      city = br.readLine();
      zip = br.readLine();
      state = br.readLine();
      country = br.readLine();
      address = new Address(street, city, state, zip, country);
      note = br.readLine();
      noteObj = new EntryNote(note);
      e = new AddressEntry(name, number, emailObj, address, noteObj);
      book.addNewEntry(e);
      line = br.readLine();
    }
    return book;
  }
}
