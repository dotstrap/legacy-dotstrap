package shared.model;

// TODO: Auto-generated Javadoc
/**
 * The Class User.
 */
public class User {
    
    /** The id. */
    private int         ID;
    
    /** The creds. */
    private Credentials creds;
    
    /** The info. */
    private UserInfo    info;
    
    /** The record count. */
    private int         recordCount;
    
    /** The batch. */
    private int         batch;

    /**
     * Instantiates a new user.
     */
    public User() {

    }

    /**
     * Instantiates a new user.
     *
     * @param c the c
     * @param ui the ui
     */
    public User(Credentials c, UserInfo ui) {
        creds = c;
        info = ui;
        recordCount = 0;
        batch = 0;
        ID = -1;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getID() {
        return ID;
    }

    /**
     * Sets the id.
     *
     * @param i the new id
     */
    public void setID(int i) {
        ID = i;
    }

    /**
     * Gets the creds.
     *
     * @return the creds
     */
    public Credentials getCreds() {
        return creds;
    }

    /**
     * Sets the creds.
     *
     * @param c the new creds
     */
    public void setCreds(Credentials c) {
        creds = c;
    }

    /**
     * Gets the user info.
     *
     * @return the user info
     */
    public UserInfo getUserInfo() {
        return info;
    }

    /**
     * Sets the user info.
     *
     * @param u the new user info
     */
    public void setUserInfo(UserInfo u) {
        info = u;
    }

    /**
     * Gets the record count.
     *
     * @return the record count
     */
    public int getRecordCount() {
        return recordCount;
    }

    /**
     * Sets the record count.
     *
     * @param i the new record count
     */
    public void setRecordCount(int i) {
        recordCount = i;
    }

    /**
     * Gets the current batch.
     *
     * @return the current batch
     */
    public int getCurrentBatch() {
        return batch;
    }

    /**
     * Sets the current batch.
     *
     * @param i the new current batch
     */
    public void setCurrentBatch(int i) {
        batch = i;
    }
}
