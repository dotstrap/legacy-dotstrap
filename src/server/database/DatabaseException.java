package server.database;

// TODO: Auto-generated Javadoc
/**
 * The Class DatabaseException.
 */
@SuppressWarnings("serial")
public class DatabaseException extends Exception {

    /**
     * Instantiates a new database exception.
     */
    public DatabaseException() {
        return;
    }

    /**
     * Instantiates a new database exception.
     *
     * @param message
     *            the message
     */
    public DatabaseException(String message) {
        super(message);
    }

    /**
     * Instantiates a new database exception.
     *
     * @param cause
     *            the cause
     */
    public DatabaseException(Throwable cause) {
        super(cause);

    }

    /**
     * Instantiates a new database exception.
     *
     * @param message
     *            the message
     * @param cause
     *            the cause
     */
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

}