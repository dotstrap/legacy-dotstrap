/**
 * ClientException.java
 * JRE v1.8.0_40
 * 
 * Created by William Myers on Mar 22, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client;

// TODO: Auto-generated Javadoc
/**
 * The Class ClientException.
 */
@SuppressWarnings("serial")
public class ClientException extends Exception {

    /**
     * Instantiates a new client exception.
     */
    public ClientException() {
        return;
    }

    /**
     * Instantiates a new client exception.
     *
     * @param message
     *            the message
     */
    public ClientException(String message) {
        super(message);
    }

    /**
     * Instantiates a new client exception.
     *
     * @param throwable
     *            the throwable
     */
    public ClientException(Throwable throwable) {
        super(throwable);
    }

    /**
     * Instantiates a new client exception.
     *
     * @param message
     *            the message
     * @param throwable
     *            the throwable
     */
    public ClientException(String message, Throwable throwable) {
        super(message, throwable);
    }

}