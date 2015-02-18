package servertester.views;

import java.awt.event.*;

import javax.swing.*;

import static servertester.views.Constants.*;

@SuppressWarnings({"serial", "rawtypes", "unchecked"})
public class SettingsPanel extends BasePanel {
	
	private JTextField _hostTextField;
	private JTextField _portTextField;
	private JComboBox _opComboBox;
	private JButton _executeButton;
	
	public SettingsPanel() {
		super();
		
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		add(Box.createRigidArea(DOUBLE_HSPACE));
		add(new JLabel("HOST:"));
		add(Box.createRigidArea(SINGLE_HSPACE));
		
		_hostTextField = new JTextField(30);
		_hostTextField.setMinimumSize(_hostTextField.getPreferredSize());
		add(_hostTextField);
		add(Box.createRigidArea(TRIPLE_HSPACE));
		
		add(new JLabel("PORT:"));
		add(Box.createRigidArea(SINGLE_HSPACE));
		
		_portTextField = new JTextField(10);
		_portTextField.setMinimumSize(_portTextField.getPreferredSize());
		add(_portTextField);
		add(Box.createRigidArea(TRIPLE_HSPACE));
		
		add(new JLabel("OPERATION:"));
		add(Box.createRigidArea(SINGLE_HSPACE));
		
		_opComboBox = new JComboBox();
		_opComboBox.addItem(ServerOp.VALIDATE_USER);
		_opComboBox.addItem(ServerOp.GET_PROJECTS);
		_opComboBox.addItem(ServerOp.GET_SAMPLE_IMAGE);
		_opComboBox.addItem(ServerOp.DOWNLOAD_BATCH);
		_opComboBox.addItem(ServerOp.SUBMIT_BATCH);
		_opComboBox.addItem(ServerOp.GET_FIELDS);
		_opComboBox.addItem(ServerOp.SEARCH);
		_opComboBox.setSelectedItem(ServerOp.VALIDATE_USER);
		_opComboBox.setMinimumSize(_opComboBox.getPreferredSize());
		add(_opComboBox);
		add(Box.createRigidArea(TRIPLE_HSPACE));

		_executeButton = new JButton("Execute");
		add(_executeButton);	
		add(Box.createRigidArea(DOUBLE_HSPACE));
		
		setMaximumSize(getPreferredSize());
		
		////
		
		_opComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getController().operationSelected();
			}
		});
		
		_executeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getController().executeOperation();
			}
		});
	}
	
	public void setHost(String value) {
		_hostTextField.setText(value);
	}

	public String getHost() {
		return _hostTextField.getText();
	}

	public void setPort(String value) {
		_portTextField.setText(value);
	}

	public String getPort() {
		return _portTextField.getText();
	}

	public void setOperation(ServerOp value) {
		_opComboBox.setSelectedItem(value);
	}

	public ServerOp getOperation() {
		return (ServerOp)_opComboBox.getSelectedItem();
	}

}

