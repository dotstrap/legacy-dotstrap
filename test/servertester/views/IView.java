/**
 * IView.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 8, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package servertester.views;

// TODO: Auto-generated Javadoc
/**
 * The Interface IView.
 */
public interface IView {
    
    /**
     * 
     *
     * 
     */
    String getHost();
    
    /**
     * 
     *
     * 
     */
    ServerOp getOperation();
    
    /**
     * 
     *
     * 
     */
    String[] getParameterNames();
    
    /**
     * 
     *
     * 
     */
    String[] getParameterValues();
    
    /**
     * 
     *
     * 
     */
    String getPort();
    
    /**
     * 
     *
     * 
     */
    String getRequest();
    
    /**
     * 
     *
     * 
     */
    String getResponse();
    
    /**
     * DELETETHISSETTER.
     */
    void setHost(String value);
    
    /**
     * DELETETHISSETTER.
     */
    void setOperation(ServerOp value);
    
    /**
     * DELETETHISSETTER.
     */
    void setParameterNames(String[] value);
    
    /**
     * DELETETHISSETTER.
     */
    void setParameterValues(String[] value);
    
    /**
     * DELETETHISSETTER.
     */
    void setPort(String value);
    
    /**
     * DELETETHISSETTER.
     */
    void setRequest(String value);
    
    /**
     * DELETETHISSETTER.
     */
    void setResponse(String value);
}
