/**
 * IView.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 14, 2015.
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
