/**
 * SettingsPanel.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 8, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package servertester.views;

import static servertester.views.Constants.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

// TODO: Auto-generated Javadoc
/**
 * The Class SettingsPanel.
 */
@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
public class SettingsPanel extends BasePanel {
    
    /** The _execute button. */
    private JButton    _executeButton;
    
    /** The _host text field. */
    private JTextField _hostTextField;
    
    /** The _op combo box. */
    private JComboBox  _opComboBox;
    
    /** The _port text field. */
    private JTextField _portTextField;
    
    /**
     * Instantiates a new settings panel.
     */
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
        
        // //
        
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
    
    /**
     * 
     *
     * 
     */
    public String getHost() {
        return _hostTextField.getText();
    }
    
    /**
     * 
     *
     * 
     */
    public ServerOp getOperation() {
        return (ServerOp) _opComboBox.getSelectedItem();
    }
    
    /**
     * 
     *
     * 
     */
    public String getPort() {
        return _portTextField.getText();
    }
    
    /**
     * DELETETHISSETTER.
     */
    public void setHost(String value) {
        _hostTextField.setText(value);
    }
    
    /**
     * DELETETHISSETTER.
     */
    public void setOperation(ServerOp value) {
        _opComboBox.setSelectedItem(value);
    }
    
    /**
     * DELETETHISSETTER.
     */
    public void setPort(String value) {
        _portTextField.setText(value);
    }
    
}
