/**
 * ServerException.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 15, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package server;

// TODO: Auto-generated Javadoc
/**
 * The Class ServerException.
 */
@SuppressWarnings("serial")
public class ServerException extends Exception {

    /**
     * Instantiates a new server exception.
     */
    public ServerException() {
        return;
    }

    /**
     * Instantiates a new server exception.
     *
     * @param message
     *            the message
     */
    public ServerException(String message) {
        super(message);
    }

    /**
     * Instantiates a new server exception.
     *
     * @param message
     *            the message
     * @param throwable
     *            the throwable
     */
    public ServerException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Instantiates a new server exception.
     *
     * @param throwable
     *            the throwable
     */
    public ServerException(Throwable throwable) {
        super(throwable);
    }

}