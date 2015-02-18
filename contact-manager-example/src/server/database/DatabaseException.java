package server.database;

@SuppressWarnings("serial")
public class DatabaseException extends Exception {

	public DatabaseException() {
		return;
	}

	public DatabaseException(String message) {
		super(message);
	}

	public DatabaseException(Throwable cause) {
		super(cause);

	}

	public DatabaseException(String message, Throwable cause) {
		super(message, cause);
	}

}
