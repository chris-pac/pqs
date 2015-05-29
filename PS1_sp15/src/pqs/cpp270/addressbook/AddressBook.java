package pqs.cpp270.addressbook;

import java.util.LinkedList;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

/**
 * AddressBook is a container class used to store contact objects.
 * 
 * 
 * @author Chris Pac
 * @version 1.0
 * @since 2015-03-17
 *
 */
public class AddressBook {
  private List<Contact> contacts = new LinkedList<Contact>();
  
  /**
   * This method saves all the contacts to a specified file.
   * The method takes advantage of Java serialization.
   * 
   * @param fileName the name of the file where one or more contact objects are to be stored
   * @return true if the operation was successful; false otherwise
   * @throws IOException
   * @see IOException
   */
  public boolean saveToFile(String fileName) throws IOException {
    FileOutputStream out = new FileOutputStream(fileName);
    ObjectOutputStream oos = new ObjectOutputStream(out);
    oos.writeObject(contacts);
    oos.close();
    out.close();
    return true;
  }
  
  /**
   * This method loads all the contacts from a specified file.
   * The method takes advantage of Java serialization.
   * 
   * @param fileName the name of the file where one or more contact objects are stored
   * @return true if the operation was successful; false otherwise
   * @throws IOException
   * @see IOException
   */
  @SuppressWarnings("unchecked")
  public boolean loadFromFile(String fileName) throws IOException, ClassNotFoundException {
    FileInputStream in = new FileInputStream(fileName);
    ObjectInputStream ois = new ObjectInputStream(in);
    contacts = (List<Contact>) (ois.readObject());
    ois.close();
    in.close();
    return true;
  }
  
  /**
   * This method adds a new contact to the address book.
   * The method allows duplicate contacts to be added.
   * 
   * @param contact the contact object to be added
   * @return true if the operation was successful; false otherwise
   * @throws NullPointerException On null input value
   * @see NullPointerException
   */
  public boolean addContact(Contact contact) {
    if (contact == null) {
      throw new NullPointerException("contact must not be null");
    }
    
    contacts.add(contact);
    return true;
  }
  
  /**
   * This method delete one or more contacts from an address book.
   * The method searches for all contacts that match and deletes them all.
   * 
   * @param contact the contact object to be deleted
   * @return true if the operation was successful; false otherwise
   * @throws NullPointerException On null input value
   * @see NullPointerException
   */
  public boolean deleteContact(Contact contact) {
    if (contact == null) {
      throw new NullPointerException("contact must not be null");
    }
    
    // remove all contacts that match
    while (contacts.remove(contact));
    return true;
  }
  
  /**
   * This method searches for a Contact that matches the supplied property criteria.
   * It will return the first Contact that matches.
   * 
   * @param contactProperty the property object to match
   * @return the first Contact object found or null otherwise
   * @throws NullPointerException On null input value
   * @see NullPointerException
   */
  public Contact searchByContactProperty(Object contactProperty) {
    if (contactProperty == null) {
      throw new NullPointerException("contactProperty must not be null");
    }

    for (Contact c : contacts) {
      if (c.matchByProperty(contactProperty))
        return c;
    }
    
    return null;
  }
}
