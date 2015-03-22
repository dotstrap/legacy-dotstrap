/**
 * BasePanel.java
 * JRE v1.8.0_40
 * 
 * Created by William Myers on Mar 22, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package servertester.views;

import java.awt.*;
import javax.swing.*;
import servertester.controllers.*;

// TODO: Auto-generated Javadoc
/**
 * The Class BasePanel.
 */
@SuppressWarnings("serial")
public class BasePanel extends JPanel {

    /** The _controller. */
    private IController _controller;

    /**
     * Instantiates a new base panel.
     */
    public BasePanel() {
        super();
    }

    /**
     * Instantiates a new base panel.
     *
     * @param layout
     *            the layout
     */
    public BasePanel(LayoutManager layout) {
        super(layout);
    }

    /**
     * Instantiates a new base panel.
     *
     * @param isDoubleBuffered
     *            the is double buffered
     */
    public BasePanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    /**
     * Instantiates a new base panel.
     *
     * @param layout
     *            the layout
     * @param isDoubleBuffered
     *            the is double buffered
     * @param controller
     *            the controller
     */
    public BasePanel(LayoutManager layout, boolean isDoubleBuffered,
            IController controller) {
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
