package edu.nyu.cs.pqs.ps1;

/**
 * EntryName class is to hold names in each entry in the address book. My address 
 * book accept names as first name and last name. 
 *  
 * @author Tareq Alghamdi taa307@nyu.edu
 */
public class EntryName {
	// Names are composed of First Name and Last Name
	private String firstName;
	private String lastName;
	
	/**
	 * Constructor for Name providing the first name
	 * @param firstName
	 */
	public EntryName(String firstName){
	  this.firstName = firstName;
	}
	
	/**
	 * Constructor for Name providing first and last names
	 * @param firstName
	 * @param lastName
	 */
	public EntryName(String firstName, String lastName){
	  this.firstName = firstName;
	  this.lastName = lastName;
	}
		
	/**
	 * Setter for first name
	 * @param firstName
	 */
	public void setFirstName(String firstName){
	  this.firstName = firstName;
	}
	
	/**
	 * Getter for first name
	 * @return
	 */
	public String getFirstName(){
	  return firstName;
	}
	
	/**
	 * Setter for last name
	 * @param lastName
	 */
	public void setLastName(String lastName){
	  this.lastName = lastName;
	}
	
	/**
	 * Getter for last name
	 * @return last name
	 */
	public String getLastName(){
	  return lastName;
	}

	/**
	 * Overridden equals()
	 * to check the equality of two entry names
	 * @param Object supposed to be entry name
	 * @return true if the names are equal, false if they are not
	 */
	@Override
	public boolean equals(Object o){
	  if (! (o instanceof EntryName)){
	    return false;
	  }
	  EntryName en = (EntryName)o;
	  if (en.firstName == null && en.lastName == null){
	    return false;
	  }
	  else if (en.firstName == null){
	    return en.lastName == lastName;
	  }
	  else if (en.lastName == null){
	    return en.firstName == firstName;
	  }
	  else{
	    return en.firstName == firstName && en.lastName == lastName;
	  }
	}
	
	/**
	 * Overridden hashCode()
	 * This method is adopted from hashCode() method in the course text book
     * Effective Java 2nd edition 
     * to return the new hash code for my EntryName class
	 * @return int for the hashCode of the first and last names
	 */
	@Override
	public int hashCode(){
	  int hash = 17;
	  hash = 31 * hash + firstName.hashCode(); 
	  hash = 31 * hash + lastName.hashCode();
	  return hash;
	}
	
	/**
	 * Overridden toString()
     * to return the string of the EntryName class
     * @return String for the first and last names
	 */
	@Override
	public String toString(){
	  return firstName + " " + lastName;
	}
}
