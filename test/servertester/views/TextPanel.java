/**
 * TextPanel.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 8, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package servertester.views;

import java.awt.BorderLayout;

import javax.swing.*;

// TODO: Auto-generated Javadoc
/**
 * The Class TextPanel.
 */
@SuppressWarnings("serial")
public class TextPanel extends BasePanel {
    
    /** The _text area. */
    private JTextArea _textArea;
    
    /**
     * Instantiates a new text panel.
     *
     * @param label the label
     */
    public TextPanel(String label) {
        super();
        
        setBorder(BorderFactory.createTitledBorder(label));
        
        setLayout(new BorderLayout());
        
        _textArea = new JTextArea(10, 60);
        add(new JScrollPane(_textArea), BorderLayout.CENTER);
    }
    
    /**
     * 
     *
     * 
     */
    public String getText() {
        return _textArea.getText();
    }
    
    /**
     * DELETETHISSETTER.
     */
    public void setText(String value) {
        _textArea.setText(value);
    }
    
}
