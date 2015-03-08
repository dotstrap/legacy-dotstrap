/**
 * IndexerServerTesterFrame.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 8, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package servertester.views;

import static servertester.views.Constants.DOUBLE_VSPACE;

import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import servertester.controllers.IController;

// TODO: Auto-generated Javadoc
/**
 * The Class IndexerServerTesterFrame.
 */
@SuppressWarnings("serial")
public class IndexerServerTesterFrame extends JFrame implements IView {
    
    /** The _controller. */
    private IController   _controller;
    
    /** The _param panel. */
    private ParamPanel    _paramPanel;
    
    /** The _request panel. */
    private TextPanel     _requestPanel;
    
    /** The _response panel. */
    private TextPanel     _responsePanel;
    
    /** The _settings panel. */
    private SettingsPanel _settingsPanel;
    
    /**
     * Instantiates a new indexer server tester frame.
     */
    public IndexerServerTesterFrame() {
        super();
        
        setTitle("Record Indexer - Server Tester");
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        
        add(Box.createRigidArea(DOUBLE_VSPACE));
        
        _settingsPanel = new SettingsPanel();
        add(_settingsPanel);
        
        add(Box.createRigidArea(DOUBLE_VSPACE));
        
        _paramPanel = new ParamPanel();
        add(_paramPanel);
        
        add(Box.createRigidArea(DOUBLE_VSPACE));
        
        _requestPanel = new TextPanel("Request");
        
        _responsePanel = new TextPanel("Response");
        
        JSplitPane splitPane = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT, _requestPanel, _responsePanel);
        splitPane.setResizeWeight(0.5);
        splitPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(splitPane);
        
        add(Box.createRigidArea(DOUBLE_VSPACE));
        
        addWindowListener(new WindowAdapter() {
            
            @Override
            public void windowClosed(WindowEvent arg0) {
                // TODO Auto-generated method stub
                
            }
        });
        
        pack();
        
        setMinimumSize(getPreferredSize());
    }
    
    /**
     * 
     *
     * 
     */
    public IController getController() {
        return _controller;
    }
    
    /**
     * (non-Javadoc)
     * 
     * @see servertester.views.IView#getHost()
     */
    @Override
    public String getHost() {
        return _settingsPanel.getHost();
    }
    
    // IView methods
    //
    
    /**
     * (non-Javadoc)
     * 
     * @see servertester.views.IView#getOperation()
     */
    @Override
    public ServerOp getOperation() {
        return _settingsPanel.getOperation();
    }
    
    /**
     * (non-Javadoc)
     * 
     * @see servertester.views.IView#getParameterNames()
     */
    @Override
    public String[] getParameterNames() {
        return _paramPanel.getParameterNames();
    }
    
    /**
     * (non-Javadoc)
     * 
     * @see servertester.views.IView#getParameterValues()
     */
    @Override
    public String[] getParameterValues() {
        return _paramPanel.getParameterValues();
    }
    
    /**
     * (non-Javadoc)
     * 
     * @see servertester.views.IView#getPort()
     */
    @Override
    public String getPort() {
        return _settingsPanel.getPort();
    }
    
    /**
     * (non-Javadoc)
     * 
     * @see servertester.views.IView#getRequest()
     */
    @Override
    public String getRequest() {
        return _requestPanel.getText();
    }
    
    /**
     * (non-Javadoc)
     * 
     * @see servertester.views.IView#getResponse()
     */
    @Override
    public String getResponse() {
        return _responsePanel.getText();
    }
    
    /**
     * DELETETHISSETTER.
     */
    public void setController(IController value) {
        _controller = value;
        _settingsPanel.setController(value);
        _paramPanel.setController(value);
        _requestPanel.setController(value);
        _responsePanel.setController(value);
    }
    
    /**
     * (non-Javadoc)
     * 
     * @see servertester.views.IView#setHost(java.lang.String)
     */
    @Override
    public void setHost(String value) {
        _settingsPanel.setHost(value);
    }
    
    /**
     * (non-Javadoc)
     * 
     * @see servertester.views.IView#setOperation(servertester.views.ServerOp)
     */
    @Override
    public void setOperation(ServerOp value) {
        _settingsPanel.setOperation(value);
    }
    
    /**
     * (non-Javadoc)
     * 
     * @see servertester.views.IView#setParameterNames(java.lang.String[])
     */
    @Override
    public void setParameterNames(String[] value) {
        _paramPanel.setParameterNames(value);
    }
    
    /**
     * (non-Javadoc)
     * 
     * @see servertester.views.IView#setParameterValues(java.lang.String[])
     */
    @Override
    public void setParameterValues(String[] value) {
        _paramPanel.setParameterValues(value);
    }
    
    /**
     * (non-Javadoc)
     * 
     * @see servertester.views.IView#setPort(java.lang.String)
     */
    @Override
    public void setPort(String value) {
        _settingsPanel.setPort(value);
    }
    
    /**
     * (non-Javadoc)
     * 
     * @see servertester.views.IView#setRequest(java.lang.String)
     */
    @Override
    public void setRequest(String value) {
        _requestPanel.setText(value);
    }
    
    /**
     * (non-Javadoc)
     * 
     * @see servertester.views.IView#setResponse(java.lang.String)
     */
    @Override
    public void setResponse(String value) {
        _responsePanel.setText(value);
    }
    
}
