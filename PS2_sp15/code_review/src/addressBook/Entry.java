package addressBook;

/**
 * 
 * This class holds the data of each entry in the address book.
 * It can store the following: {@link addressBook.Name}, {@link addressBook.EmailAddress},
 * {@link addressBook.PhoneNumber}, {@link AddressBook.PostalAddress}, {@link addressBook.Note}
 * 
 * Currently, the possibility of altering the class's properties is not implemented.
 * The client must retrieve the properties and instantiate a new object with the modified
 * property in order to make such a change.
 * 
 * NOT SURE HOW TO DOCUMENT THE INSTANCIATION WITH Builder.
 *
 * @author cp1425
 */
public class Entry {
  private final Name name;
  private final PhoneNumber phoneNumber;
  private final PostalAddress postalAddress;
  private final EmailAddress emailAddress;
  private final Note note;

  public static class Builder {
	// Required parameters
	private final Name name;

	// Optional parameters - initialized to default values
	private PhoneNumber phoneNumber = null;
	private PostalAddress postalAddress = null;
	private EmailAddress emailAddress = null;
	private Note note = null;

	public Builder(String first, String last) {
	  this.name = new Name(first, last);
	}

	public Builder phoneNumber(String pn) throws InvalidIOException {
	  phoneNumber = new PhoneNumber(pn);
	  return this;
	}

	public Builder postalAddress(String line1, String line2, String line3) {
	  postalAddress = new PostalAddress(line1, line2, line3);
	  return this;
	}

	public Builder emailAddress(String ea) {
	  emailAddress = new EmailAddress(ea);
	  return this;
	}

	public Builder note(String n) {
	  note = new Note(n);
	  return this;
	}

	public Entry build() {
	  return new Entry(this);
	}
  }

  private Entry(Builder builder) {
	name = builder.name;
	phoneNumber = builder.phoneNumber;
	postalAddress = builder.postalAddress;
	emailAddress = builder.emailAddress;
	note = builder.note;
  }

  /**
   * @return First and Last name store in {@link Name} as {@link String}
   * 
   */
  public Name getName() {
	return name;
  }

  /**
   * @return First and Last name store in {@link Name} 
   */
  public PhoneNumber getPhoneNumber() {
	return phoneNumber;
  }

  /**
   * @return {@link PostalAddress} as one {@link String}.
   *  
   */
  public PostalAddress getPostalAddress() {
	return postalAddress;
  }

  /**
   * @return {@link EmailAddress} as {@link String} 
   */
  public EmailAddress getEmailAddress() {
	return emailAddress;
  }

  /**
   * @return {@link Note} as {@link String} 
   */
  public Note getNote() {
	return note;
  }
}
