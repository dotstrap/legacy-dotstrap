package server;

@SuppressWarnings("serial")
public class ServerException extends Exception {

	public ServerException() {
		return;
	}

	public ServerException(String message) {
		super(message);
	}

	public ServerException(Throwable throwable) {
		super(throwable);
	}

	public ServerException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
