package edu.nyu.cs.pqs.ps1;

/**
 * This class is to hold all entry variables to compose an entry instance used later in
 * the address book.
 * 
 * @author Tareq Alghamdi taa307@nyu.eud 
 *
 */
public class AddressEntry {
  // The address entry consists of name, phone, email, address and note
  // Each field belongs to its own class definition
  private EntryName name;
  private EntryPhoneNumber number;
  private Email email;
  private Address address;
  private EntryNote note;
  
  /**
   * Constructor for the entry class. 
   * @param Name n
   * @param Phone p
   * @param Email e
   * @param Address a
   * @param Note en
   */
  public AddressEntry(EntryName n, EntryPhoneNumber p, Email e, Address a, EntryNote en){
    name = n;
    number = p;
    email = e;
    address = a;
    note = en;
  }
  
  /**
   * Getter for the name field
   * @return EntryName name
   */
  public EntryName getEntryName(){
    return name;
  }
  
  /**
   * Setter for the name field of the entry
   * @param EntryName name
   */
  public void setEntryName(EntryName name){
    this.name = name;
  }
  
  /**
   * Getter for the phone number
   * @return EntryPhoneNumber
   */
  public EntryPhoneNumber getEntryPhoneNumber(){
    return number;
  }
  
  /**
   * Setter for the phone number
   * @param EntryPhoneNumber number
   */
  public void setEntryPhoneNumber(EntryPhoneNumber number){
    this.number = number;
  }
  
  /**
   * Getter for the email 
   * @return Email
   */
  public Email getEmail(){
    return email;
  }
  
  /**
   * Setter for the email
   * @param Email email
   */
  public void setEmail(Email email){
    this.email = email;
  }
  
  /**
   * Getter for the address
   * @return Address
   */
  public Address getAddress(){
    return address;
  }
  
  /**
   * Setter for the address
   * @param Address address
   */
  public void setAddress(Address address){
    this.address = address;
  }
  
  /**
   * Getter for the Entry Note
   * @return EntryNote
   */
  public EntryNote getNote(){
    return note;
  }
  
  /**
   * Setter for the Entry Note
   * @param EntryNote note
   */
  public void setEntryNote(EntryNote note){
    this.note = note;
  }
  
  /**
   * OVerridden equals()
   * It checks if both AddressEntries are equal and returns true accordingly.
   * @return boolean true if all variables of Entry are equal. If any is not equal, it 
   * returns false
   */
  @Override
  public boolean equals(Object o){
    if (! (o instanceof AddressEntry)){
      return false;
    }
    // Two address entries are equal if all the fields are equal.
    AddressEntry entry = (AddressEntry) o;
    return entry.name.equals(name) &&
        entry.number.equals(number) &&
        entry.email.equals(email) &&
        entry.address.equals(address) &&
        entry.note.equals(note);
  }
  
  /**
   * Overridden hashCode()
   * This method is adopted from hashCode() method in the course text book
   * Effective Java 2nd edition 
   * @return int the new hash code for the address entry
   */
  @Override
  public int hashCode(){
    int hash = 17;
    hash = 31 * hash + name.hashCode();
    hash = 31 * hash + number.hashCode();
    hash = 31 * hash + email.hashCode();
    hash = 31 * hash + address.hashCode();
    hash = 31 * hash + note.hashCode();
    return hash;
  }

  /**
   * Overridden toString() to return string of address entry in the form
   * Name:
   * Phone Number:
   * Address:
   * Email:
   * Note:
   * @return String Entry
   */
  @Override
  public String toString(){
    return "Name: " + name.toString() + "\n" +
        "Phone Number: " + number.toString() + "\n" +
        "Email: " + email.toString() + "\n" +
        "Address: " + address.toString() + "\n" +
        "Note:" + note.toString() + "\n" ;
  }
}
