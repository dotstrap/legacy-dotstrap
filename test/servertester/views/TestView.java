/**
 * TestView.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 8, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package servertester.views;

import servertester.controllers.IController;

// TODO: Auto-generated Javadoc
/**
 * The Class TestView.
 */
public class TestView implements IView {
    
    /** The _host. */
    private String   _host;
    
    /** The _op. */
    private ServerOp _op;
    
    /** The _param names. */
    private String[] _paramNames;
    
    /** The _param values. */
    private String[] _paramValues;
    
    /** The _port. */
    private String   _port;
    
    /** The _request. */
    private String   _request;
    
    /** The _response. */
    private String   _response;
    
    /**
     * Instantiates a new test view.
     */
    public TestView() {
        _host = "";
        _port = "";
        _op = ServerOp.VALIDATE_USER;
        _paramNames = new String[0];
        _paramValues = new String[0];
        _request = "";
        _response = "";
    }
    
    /**
     * (non-Javadoc)
     * 
     * @see servertester.views.IView#getHost()
     */
    @Override
    public String getHost() {
        return _host;
    }
    
    //
    // IView
    //
    
    /**
     * (non-Javadoc)
     * 
     * @see servertester.views.IView#getOperation()
     */
    @Override
    public ServerOp getOperation() {
        return _op;
    }
    
    /**
     * (non-Javadoc)
     * 
     * @see servertester.views.IView#getParameterNames()
     */
    @Override
    public String[] getParameterNames() {
        return _paramNames;
    }
    
    /**
     * (non-Javadoc)
     * 
     * @see servertester.views.IView#getParameterValues()
     */
    @Override
    public String[] getParameterValues() {
        return _paramValues;
    }
    
    /**
     * (non-Javadoc)
     * 
     * @see servertester.views.IView#getPort()
     */
    @Override
    public String getPort() {
        return _port;
    }
    
    /**
     * (non-Javadoc)
     * 
     * @see servertester.views.IView#getRequest()
     */
    @Override
    public String getRequest() {
        return _request;
    }
    
    /**
     * (non-Javadoc)
     * 
     * @see servertester.views.IView#getResponse()
     */
    @Override
    public String getResponse() {
        return _response;
    }
    
    /**
     * DELETETHISSETTER.
     */
    public void setController(IController value) {
    }
    
    /**
     * (non-Javadoc)
     * 
     * @see servertester.views.IView#setHost(java.lang.String)
     */
    @Override
    public void setHost(String value) {
        _host = value;
    }
    
    /**
     * (non-Javadoc)
     * 
     * @see servertester.views.IView#setOperation(servertester.views.ServerOp)
     */
    @Override
    public void setOperation(ServerOp value) {
        _op = value;
    }
    
    /**
     * (non-Javadoc)
     * 
     * @see servertester.views.IView#setParameterNames(java.lang.String[])
     */
    @Override
    public void setParameterNames(String[] value) {
        _paramNames = value;
    }
    
    /**
     * (non-Javadoc)
     * 
     * @see servertester.views.IView#setParameterValues(java.lang.String[])
     */
    @Override
    public void setParameterValues(String[] value) {
        _paramValues = value;
    }
    
    /**
     * (non-Javadoc)
     * 
     * @see servertester.views.IView#setPort(java.lang.String)
     */
    @Override
    public void setPort(String value) {
        _port = value;
    }
    
    /**
     * (non-Javadoc)
     * 
     * @see servertester.views.IView#setRequest(java.lang.String)
     */
    @Override
    public void setRequest(String value) {
        _request = value;
    }
    
    /**
     * (non-Javadoc)
     * 
     * @see servertester.views.IView#setResponse(java.lang.String)
     */
    @Override
    public void setResponse(String value) {
        _response = value;
    }
    
}
