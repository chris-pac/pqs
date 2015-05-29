package addressBook;


/**
 * @author cp1425
 * 
 * A class that stores two {@Java.lang.String} objects,
 * representing a fist name and a last name.
 *
 */
public class Name extends EntryField {
  private String firstName;
  private String lastName;

  private enum SortNameBy {
	First, Last
  }

  protected static SortNameBy sortBy = SortNameBy.Last;

  public Name(String firstName, String lastName) {
	this.firstName = firstName;
	this.lastName = lastName;
  }

  /**
   * Returns a the concatenated string, created by the firstName
   * and lastName class members.
   */
  @Override
  public String toString() {
	return firstName + " " + lastName;
  }

  @Override
  public boolean checkIO(String... strings) {
	// TODO Auto-generated method stub
	return false;
  }

  /**
   * Compares this Name object and the passed in Name object.
   * Comparison is done by first name or last name, according
   * to the value of the static class member sortBy. 
   * 
   * @param n2
   * @return
   */
  public int compareTo(Name n2) {
	switch (sortBy) {
	case Last:
	  if (n2 != null) {
		return lastName.compareTo(n2.getLastName());
	  }
	  break;
	case First:
	  if (n2 != null) {
		return firstName.compareTo(n2.getFirstName());
	  }
	}
	return -1;
  }

  @Override
  public boolean equals(Object o) {
	if (!(o instanceof Name)) {
	  return false;
	}
	if (!firstName.equals(((Name) o).getFirstName())) {
	  return false;
	}
	if (!lastName.equals(((Name) o).getLastName())) {
	  return false;
	}
	return true;
  }
  
  /**
   * Use this method to determine how name ordering works
   * within an {@link addressBook.AddressBook} at runtime.
   * 
   * @param s use "first" for ordering by first name,
   *  "last" for ordering by last name (case insensitive). 
   */
  public static void setSortBy(String s){
	if (s.toLowerCase().contains("first")){
	  sortBy = SortNameBy.First;
	}
	if (s.toLowerCase().contains("last")){
	  sortBy = SortNameBy.Last;
	}
  }

  @Override
  public int hashCode() {
	return firstName.hashCode() + lastName.hashCode();
  }

  
  /**
   * Returns the first name String member.
   * @return
   */
  public String getFirstName() {
	return firstName;
  }

  /**
   * Sets the passed in String as first name.
   *
   * @param firstName
   */
  public void setFirstName(String firstName) {
	this.firstName = firstName;
  }

  /**
   * Returns the last name String member.
   * @return
   */
  public String getLastName() {
	return lastName;
  }

  /**
   * Sets the passed in String as last name.
   *
   * @param lastName
   */
  public void setLastName(String lastName) {
	this.lastName = lastName;
  }

}
