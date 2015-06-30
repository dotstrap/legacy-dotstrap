/**
 * TextPanel.java
 * JRE v1.8.0_45
 * 
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package servertester.views;

import java.awt.*;
import javax.swing.*;

/**
 * The Class TextPanel.
 */
@SuppressWarnings("serial")
public class TextPanel extends BasePanel {

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

  public void setText(String value) {
    _textArea.setText(value);
  }

  public String getText() {
    return _textArea.getText();
  }

}
