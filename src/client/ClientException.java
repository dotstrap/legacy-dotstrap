/**
 * ClientException.java
 * JRE v1.8.0_45
 * 
 * Created by William Myers on Jun 28, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client;

@SuppressWarnings("serial")
public class ClientException extends Exception {

  public ClientException() {
    return;
  }

  public ClientException(String message) {
    super(message);
  }

  public ClientException(Throwable throwable) {
    super(throwable);
  }

  public ClientException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
