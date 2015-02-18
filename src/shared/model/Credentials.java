package shared.model;

// TODO: Auto-generated Javadoc
/**
 * The Class Credentials.
 */
public class Credentials {

    /** The username. */
    private String username;

    /** The password. */
    private String password;

    /**
     * Instantiates a new credentials.
     *
     * @param u
     *            the u
     * @param p
     *            the p
     */
    public Credentials(String u, String p) {
        username = u;
        password = p;
    }

    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param s
     *            the new username
     */
    public void setUsername(String s) {
        username = s;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param s
     *            the new password
     */
    public void setPassword(String s) {
        password = s;
    }
}
