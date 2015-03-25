package servertester.views;

import servertester.controllers.*;

public class TestView implements IView {

	private IController _controller;
	private String _host;
	private String _port;
	private ServerOp _op;
	private String[] _paramNames;
	private String[] _paramValues;
	private String _request;
	private String _response;
	
	public TestView() {
		_controller = null;
		_host = "";
		_port = "";
		_op = ServerOp.VALIDATE_USER;
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

