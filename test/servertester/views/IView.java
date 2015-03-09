package servertester.views;

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

