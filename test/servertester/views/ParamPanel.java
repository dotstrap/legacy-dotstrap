package servertester.views;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.util.List;

@SuppressWarnings("serial")
public class ParamPanel extends BasePanel {

	private String[] _paramNames;
	private ArrayList<JTextField> _textFields;
	
	public ParamPanel() {
		super();
		
		setBorder(BorderFactory.createTitledBorder("Parameters"));
		
		setLayout(new GridBagLayout());
	}
	
	public void setParameterNames(String[] value) {
		_paramNames = value;
		_textFields = new ArrayList<JTextField>();
		
		this.removeAll();
		
		GridBagConstraints gbc = new GridBagConstraints();

		int row = 0;
		for (String paramName : _paramNames) {
			boolean isLast = (paramName == _paramNames[_paramNames.length - 1]);
			
			JLabel label = new JLabel(paramName + ":");
			
			gbc.gridx = 0;
			gbc.gridy = row;
			gbc.weightx = 0;
			gbc.weighty = 0;
			gbc.fill = GridBagConstraints.NONE;
			gbc.insets = new Insets(5, 5, (isLast ? 5 : 0), 0);
			add(label, gbc);
	
			JTextField textField = new JTextField(40);
			textField.setMinimumSize(textField.getPreferredSize());
			_textFields.add(textField);
			
			gbc.gridx = 1;
			gbc.gridy = row;
			gbc.weightx = 0;
			gbc.weighty = 0;
			gbc.fill = GridBagConstraints.NONE;
			gbc.insets = new Insets(5, 5, (isLast ? 5 : 0), 0);
			add(textField, gbc);
			
			++row;
		}
		
		Dimension prefSize = getPreferredSize();
		Dimension maxSize = getMaximumSize();
		setMaximumSize(new Dimension((int)maxSize.getWidth(), (int)prefSize.getHeight()));
		
		revalidate();
	}
	
	public String[] getParameterNames() {
		return _paramNames;
	}
	
	public void setParameterValues(String[] value) {
		for (int i = 0; i < value.length; ++i) {
			_textFields.get(i).setText(value[i]);
		}
	}
	
	public String[] getParameterValues() {
		List<String> result = new ArrayList<String>();	
		for (int i = 0; i < _textFields.size(); ++i) {
			result.add(_textFields.get(i).getText());
		}	
		return result.toArray(new String[result.size()]);
	}
	

}

