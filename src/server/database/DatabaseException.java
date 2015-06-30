/**
 * DatabaseException.java
 * JRE v1.8.0_45
 * 
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package server.database;

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
   * @param message the message
   */
  public DatabaseException(String message) {
    super(message);
  }

  /**
   * Instantiates a new database exception.
   *
   * @param message the message
   * @param cause the cause
   */
  public DatabaseException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Instantiates a new database exception.
   *
   * @param cause the cause
   */
  public DatabaseException(Throwable cause) {
    super(cause);

  }

}
