package shared.communication;

// TODO: Auto-generated Javadoc
/**
 * The Class DownloadFileParameters.
 */
public class DownloadFileParameters {
    private String url;
    private String username;
    private String password;

   /**
     *
     */
    public DownloadFileParameters() {
    }

    /**
     * @param url
     * @param username
     * @param password
     */
    public DownloadFileParameters(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

  }
