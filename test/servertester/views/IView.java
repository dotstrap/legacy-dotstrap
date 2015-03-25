/**
 * IView.java
 * JRE v1.8.0_40
 * 
 * Created by William Myers on Mar 24, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package servertester.views;

/**
 * The Interface IView.
 */
public interface IView {

  void setHost(String value);

  String getHost();

  void setPort(String value);

  String getPort();

  void setOperation(ServerOp value);

  ServerOp getOperation();

  void setParameterNames(String[] value);

  String[] getParameterNames();

  void setParameterValues(String[] value);

  String[] getParameterValues();

  void setRequest(String value);

  String getRequest();

  void setResponse(String value);

  String getResponse();
}
