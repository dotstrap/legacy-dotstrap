/**
 * InvalidCredentialsException.java
 * JRE v1.8.0_40
 * 
 * Created by William Myers on Mar 23, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared;

@SuppressWarnings("serial")
public class InvalidCredentialsException extends Exception {
    /**
     * Instantiates a new invalid user exception.
     */
    public InvalidCredentialsException() {
        return;
    }

    /**
     * Instantiates a new invalid user exception.
     *
     * @param message the message
     */
    public InvalidCredentialsException(String message) {
        super(message);
    }

    /**
     * Instantiates a new invalid user exception.
     *
     * @param message the message
     * @param throwable the throwable
     */
    public InvalidCredentialsException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Instantiates a new invalid user exception.
     *
     * @param throwable the throwable
     */
    public InvalidCredentialsException(Throwable throwable) {
        super(throwable);
    }
}
