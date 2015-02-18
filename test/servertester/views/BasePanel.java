package servertester.views;

import java.awt.*;
import javax.swing.*;
import servertester.controllers.*;

@SuppressWarnings("serial")
public class BasePanel extends JPanel {

	private IController _controller;
	
	public BasePanel() {
		super();
	}

	public BasePanel(LayoutManager layout) {
		super(layout);
	}

	public BasePanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}

	public BasePanel(LayoutManager layout, boolean isDoubleBuffered, IController controller) {
		super(layout, isDoubleBuffered);
		setController(controller);
	}
	
	public IController getController() {
		return _controller;
	}
	
	public void setController(IController value) {
		_controller = value;
	}

}

