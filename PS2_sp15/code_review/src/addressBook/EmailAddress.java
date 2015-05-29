package addressBook;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author cp1425
 * A class that stores and handles email addresses.
 */
public class EmailAddress extends EntryField {
  private String ea = null;
  final private int MAX_NUM_EMAILS = 1;

  
  /**
   * This constructor takes a string, and if the string
   * is a valid email, it is stored. Otherwise, the value
   * of the email address is null. 
   * In future iterations, there will be an option of
   * storing multiple emails. 
   * 
   * @param ea
   */
  public EmailAddress(String ea) {
	String[] checkString = new String[MAX_NUM_EMAILS];
	checkString[0] = ea;
	if (checkIO(checkString)) {
	  this.ea = ea;
	}
  }

  /**
   * Returns the email address stored in this object,
   * or null if there is none.
   * 
   * @return
   */
  public String getAddress() {
	return ea;
  }

  /**
   * Takes a {java.lang.String} and stores if it is a valid
   * email address.
   * @param ea
   */
  public void setAddress(String ea) {
	String[] checkEa = new String[1];
	checkEa[0] = ea;
	if (checkIO(checkEa)){
	  this.ea = ea;
	}
  }

  /**
   * Returns the email address stored in this
   * object as a String.
   */
  @Override
  public String toString() {
	return ea;
  }

  /**
   * Checks validity of a String array of emails.
   * Uses the regex - "^.+@.+\\..+$" to determine validity.
   */
  @Override
  protected boolean checkIO(String... emails) {
	for (String e : emails){
	  Pattern pattern = Pattern.compile("^.+@.+\\..+$");
	  Matcher matcher = pattern.matcher(e);
	  if (!matcher.matches()){
		return false;
	  }
	}
	return true;
  }
  
  /**
   * Comparison by value of two EmailAddress objects.
   * It is based on comparing the stored email String
   * in this object and the passed in object.
   */
  @Override
  public boolean equals(Object o) {
	if (!(o instanceof EmailAddress)){
	  return false;
	}
	if (!ea.equals(((EmailAddress) o).getAddress())){
	  return false;
	}
	return true;
  }
  
  @Override
  public int hashCode(){
	return ea.hashCode();
  }
}
