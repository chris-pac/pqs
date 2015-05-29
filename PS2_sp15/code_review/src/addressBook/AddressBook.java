package addressBook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.ArrayList;

/**
 * 
 * An address book that holds {@link addressBook.Entry} objects.
 * The main data structure is a {@link java.util.TreeSet}, and the client
 * can add, remove, search for entries and also save/load from disk.
 * @author cp1425 
 */

public class AddressBook implements Comparator<Entry>, Serializable {
  private static final long serialVersionUID = 1L;
  private TreeSet<Entry> addressBook;

  /**
   * These should be used with the 
   * {@link #searchEntries(EntryFieldType type, Entry queryEntry)} method. 
   *
   */
  public enum EntryFieldType {
	Name, PostalAddress, EmailAddress, PhoneNumber, Note;
  }

  
  /**
   * Constructor, initializes {@link TreeSet} that holds the entries.
   */
  public AddressBook() {
	addressBook = new TreeSet<Entry>();
  }

  /**
   * Adds an Entry to the address book. An entry must consist of the 5 fields,
   * and they must be not null (future iterations will enable partial entries).
   * 
   * @param e an {@link Entry} 
   */
  public void addEntry(Entry e) {
	addressBook.add(e);
  }

  /**
   * Removes an Entry from the address book. It is up to the client to ensure
   * the right entry is passed in (and then removed).
   * 
   * @param an {@link Entry}
   */
  public void removeEntry(Entry e) {
	addressBook.remove(e);
  }

  
  /**
   * Should be given the entry field to search by, and an Entry object.
   * 
   * @param {@link EntryFieldType} type
   * @param {@link Entry} queryEntry
   * @return a list of entry objects where the given field of both the entry
   * query and the found entry objects is of equal value. If no such object
   * is found, null is returned. 
   */
  public ArrayList<Entry> searchEntries(EntryFieldType type, Entry queryEntry) {
	ArrayList<Entry> list = new ArrayList<Entry>();
	for (Entry e : addressBook) {
	  switch (type) {
	  case Name:
		if (e.getName().equals(queryEntry.getName())) {
		  list.add(e);
		}
		break;

	  case EmailAddress:
		if (e.getEmailAddress().equals(queryEntry.getEmailAddress())) {
		  list.add(e);
		}
		break;

	  case PostalAddress:
		if (e.getPostalAddress().equals(queryEntry.getPostalAddress())) {
		  list.add(e);
		}
		break;

	  case PhoneNumber:
		if (e.getPhoneNumber().equals(queryEntry.getPhoneNumber())) {
		  list.add(e);
		}
		break;

	  case Note:
		if (e.getNote().equals(queryEntry.getNote())) {
		  list.add(e);
		}
		break;
	  }
	}
	if (!list.isEmpty())
	  return list;
	return null;
  }

  /**
   * Saves the address book object to a file on disk.
   * 
   * @param path a {@link String} where the file should be.
   */
  public void saveToFile(String path) {
	if (addressBook.isEmpty()) {
	  return;
	}
	try {
	  FileOutputStream fileOut = new FileOutputStream(path);
	  ObjectOutputStream out = new ObjectOutputStream(fileOut);
	  out.writeObject(this);
	  out.close();
	  fileOut.close();
	} catch (IOException e) {
	  e.printStackTrace();
	}
  }

  /**
   * If the file specified is found, and it contains a serialized version of an
   * address book object, the object will be loaded into memory.
   * 
   * @param path a {@link String} where the file should be.
   */
  public void readFromFile(String path) {
	TreeSet<Entry> ab = null;
	try {
	  FileInputStream fileIn = new FileInputStream(path);
	  ObjectInputStream in = new ObjectInputStream(fileIn);
	  if (ab instanceof TreeSet<?>) {
		if (ab.first() instanceof Entry)
		  ab = (TreeSet<Entry>) in.readObject();
	  }
	  in.close();
	  fileIn.close();
	} catch (IOException e) {
	  e.printStackTrace();
	  return;
	} catch (ClassNotFoundException c) {
	  System.out.println("Entry class not found");
	  c.printStackTrace();
	  return;
	}
	addressBook = ab;
  }

  /**
   * Compares two Entry objects by their Name. Ordering is alphabetical,
   * with accordance to the {@link addressBook.Name} class definitions at runtime.
   * 
   * @param o1 The first {@link Entry} object to be compared.
   * @param o2 The second {@link Entry} object to be compared. 
   */
  @Override
  public int compare(Entry o1, Entry o2) {
	return o1.getName().compareTo(o2.getName());
  }

}
