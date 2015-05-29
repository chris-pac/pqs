package addressBook;

/**
 * @author cp1425
 * 
 * A class that stores one {@Java.lang.String} object,
 * representing any text information to be associated with
 * the containing {@link addressBook.Entry}.
 * 
 * Limited to 512 characters. (Encoding issues not yet handled)
 *
 */
public class Note extends EntryField {
  private String note;
  final private int MAX_LENGTH = 512;

  /**
   * Constructor, requires a String to be passed in.
   * @param note
   */
  public Note(String note) {
	this.note = note;
  }

  @Override
  public String toString() {
	return note;
  }

  @Override
  public boolean checkIO(String... note) {
	if (note[0].length() > MAX_LENGTH){
	  return false;
	}
	return true;
  }

  @Override
  public boolean equals(Object o) {
	if (!(o instanceof Note)){
	  return false;
	}
	if (!note.equals(((Note) o).getNote())){
	  return false;
	}
	return true;
  }
  
  @Override
  public int hashCode() {
	return note.hashCode();
  }
  
  /**
   * @return Text contained in this object.
   */
  public String getNote() {
    return note;
  }

  /**
   * Sets the text information stored in this object.
   * @param note
   */
  public void setNote(String note) {
    this.note = note;
  }
}
