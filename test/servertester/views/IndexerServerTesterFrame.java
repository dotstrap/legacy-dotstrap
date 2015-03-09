package servertester.views;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import servertester.controllers.*;
import static servertester.views.Constants.*;

@SuppressWarnings("serial")
public class IndexerServerTesterFrame extends JFrame implements IView {
	
	private IController _controller;
	private SettingsPanel _settingsPanel;
	private ParamPanel _paramPanel;
	private TextPanel _requestPanel;
	private TextPanel _responsePanel;
	
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

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
												_requestPanel, _responsePanel);
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
	
	public IController getController() {
		return _controller;
	}
	
	public void setController(IController value) {
		_controller = value;
		_settingsPanel.setController(value);
		_paramPanel.setController(value);
		_requestPanel.setController(value);
		_responsePanel.setController(value);
	}

	// IView methods
	//
	
	@Override
	public void setHost(String value) {
		_settingsPanel.setHost(value);
	}

	@Override
	public String getHost() {
		return _settingsPanel.getHost();
	}

	@Override
	public void setPort(String value) {
		_settingsPanel.setPort(value);
	}

	@Override
	public String getPort() {
		return _settingsPanel.getPort();
	}

	@Override
	public void setOperation(ServerOp value) {
		_settingsPanel.setOperation(value);
	}

	@Override
	public ServerOp getOperation() {
		return _settingsPanel.getOperation();
	}

	@Override
	public void setParameterNames(String[] value) {
		_paramPanel.setParameterNames(value);
	}
	
	@Override
	public String[] getParameterNames() {
		return _paramPanel.getParameterNames();
	}
	
	@Override
	public void setParameterValues(String[] value) {
		_paramPanel.setParameterValues(value);
	}

	@Override
	public String[] getParameterValues() {
		return _paramPanel.getParameterValues();
	}

	@Override
	public void setRequest(String value) {
		_requestPanel.setText(value);
	}

	@Override
	public String getRequest() {
		return _requestPanel.getText();
	}

	@Override
	public void setResponse(String value) {
		_responsePanel.setText(value);
	}

	@Override
	public String getResponse() {
		return _responsePanel.getText();
	}

}

