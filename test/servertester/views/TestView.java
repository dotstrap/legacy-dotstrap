/**
 * TestView.java
 * JRE v1.8.0_40
 * 
 * Created by William Myers on Mar 23, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package servertester.views;

import servertester.controllers.*;

// TODO: Auto-generated Javadoc
/**
 * The Class TestView.
 */
public class TestView implements IView {

    /** The _controller. */
    private IController _controller;

    /** The _host. */
    private String      _host;

    /** The _port. */
    private String      _port;

    /** The _op. */
    private ServerOp    _op;

    /** The _param names. */
    private String[]    _paramNames;

    /** The _param values. */
    private String[]    _paramValues;

    /** The _request. */
    private String      _request;

    /** The _response. */
    private String      _response;

    /**
     * Instantiates a new test view.
     */
    public TestView() {
        _controller = null;
        _host = "";
        _port = "";
        _op = ServerOp.VALIdATE_USER;
        _paramNames = new String[0];
        _paramValues = new String[0];
        _request = "";
        _response = "";
    }

    public void setController(IController value) {
        _controller = value;
    }

    //
    // IView
    //

    @Override
    public void setHost(String value) {
        _host = value;
    }

    @Override
    public String getHost() {
        return _host;
    }

    @Override
    public void setPort(String value) {
        _port = value;
    }

    @Override
    public String getPort() {
        return _port;
    }

    @Override
    public void setOperation(ServerOp value) {
        _op = value;
    }

    @Override
    public ServerOp getOperation() {
        return _op;
    }

    @Override
    public void setParameterNames(String[] value) {
        _paramNames = value;
    }

    @Override
    public String[] getParameterNames() {
        return _paramNames;
    }

    @Override
    public void setParameterValues(String[] value) {
        _paramValues = value;
    }

    @Override
    public String[] getParameterValues() {
        return _paramValues;
    }

    @Override
    public void setRequest(String value) {
        _request = value;
    }

    @Override
    public String getRequest() {
        return _request;
    }

    @Override
    public void setResponse(String value) {
        _response = value;
    }

    @Override
    public String getResponse() {
        return _response;
    }

}
