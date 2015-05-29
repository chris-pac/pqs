package edu.nyu.cs.pqs.ps1;

/**
 * EntryNote class is to hold entry note in each entry in the address book. My address 
 * notes in the form of string. 
 *  
 * @author Tareq Alghamdi taa307@nyu.edu
 */

public class EntryNote {
  // The note is just a simple text
  private String text;
  
  /**
   * Constructor for the EntryNote class
   * @param text to be assigned for the object text class
   */
  public EntryNote(String text){
    this.text = text;
  }

  /**
   * Getter method for the text
   * @return String text
   */
  public String getText(){
    return text;
  }
  
  /**
   * Setter method for the text 
   * @param text
   */
  public void setText(String text){
    this.text = text;
  }
  
  /**
   * Overridden equals()
   * to check the equality of two entry notes
   * @param Object supposed to be entry note
   * @return true if the notes are equal, false if they are not
   */
  @Override
  public boolean equals(Object o){
    if (! (o instanceof EntryNote)){
      return false;
    }
    EntryNote en = (EntryNote)o;
    return en.text.equals(text);
  }
  
  /**
   * Overridden hashCode()
   * This method is adopted from hashCode() method in the course text book
   * Effective Java 2nd edition 
   * to return the new hash code for the note
   * @return int for the hashCode of the note
   */
  @Override
  public int hashCode(){
    int hash = 17;
    hash = 31 * hash + text.hashCode();
    return hash;
  }
  
  /**
   * Overridden toString()
   * to return the string of the EntryNote class
   * @return String for the text
   */
  @Override
  public String toString(){
    return text;
  }
}
