package shared.model;

// TODO: Auto-generated Javadoc
/**
 * The Class UserInfo.
 */
public class UserInfo {
    
    /** The first. */
    private String first;
    
    /** The last. */
    private String last;
    
    /** The email. */
    private String email;

    /**
     * Instantiates a new user info.
     */
    public UserInfo() {

    }

    /**
     * Instantiates a new user info.
     *
     * @param f the f
     * @param l the l
     * @param e the e
     */
    public UserInfo(String f, String l, String e) {
        first = f;
        last = l;
        email = e;
    }

    /**
     * Gets the first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return first;
    }

    /**
     * Sets the first name.
     *
     * @param s the new first name
     */
    public void setFirstName(String s) {
        first = s;
    }

    /**
     * Gets the last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return last;
    }

    /**
     * Sets the last name.
     *
     * @param s the new last name
     */
    public void setLastName(String s) {
        last = s;
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param s the new email
     */
    public void setEmail(String s) {
        email = s;
    }
}
