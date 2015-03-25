package servertester.views;

import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class TextPanel extends BasePanel {

	private JTextArea _textArea;
	
	public TextPanel(String label) {
		super();
		
		setBorder(BorderFactory.createTitledBorder(label));
		
		setLayout(new BorderLayout());
		
		_textArea = new JTextArea(10, 60);
		add(new JScrollPane(_textArea), BorderLayout.CENTER);
	}
	
	public void setText(String value) {
		_textArea.setText(value);
	}
	
	public String getText() {
		return _textArea.getText();
	}

}

