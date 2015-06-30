/**
 * SampleBatchDialog.java
 * JRE v1.8.0_45
 * 
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.view.indexerframe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

/**
 * The Class SampleBatchDialog.
 */
@SuppressWarnings("serial")
public class SampleBatchDialog extends JDialog {

  private JDialog parent;
  CloseActionListener closeActionListener;

  /**
   * Instantiates a new sample batch dialog.
   *
   * @param p the p
   * @param sampleBatch the sample batch
   * @param projName the proj name
   */
  public SampleBatchDialog(JDialog p, BufferedImage sampleBatch,
      String projName) {
    super(p, "Sample Batch from " + projName, true);
    parent = p;

    closeActionListener = new CloseActionListener();

    setSize(new Dimension(500, 400));
    setLocationRelativeTo(parent);
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setLayout(new BorderLayout());
    setResizable(false);
    setModal(true);

    add(initSampleBatch(sampleBatch), BorderLayout.CENTER);
    add(initButtonBox(), BorderLayout.SOUTH);

    pack();
    setVisible(true);
  }

  /**
   * Initializes the button box.
   *
   * @return the box
   */
  private Box initButtonBox() {
    Box buttonBox = Box.createHorizontalBox();

    JButton closeButton = new JButton("Close");
    closeButton.addActionListener(closeActionListener);
    buttonBox.add(Box.createGlue());
    buttonBox.add(closeButton);
    buttonBox.add(Box.createGlue());

    return buttonBox;
  }

  /**
   * Initializes the sample batch.
   *
   * @param sampleBatch the sample batch
   * @return the j label
   */
  private JLabel initSampleBatch(BufferedImage sampleBatch) {
    int x = sampleBatch.getWidth() / 2;
    int y = sampleBatch.getHeight() / 2;

    ImageIcon scaledSampleBatch =
        new ImageIcon(sampleBatch.getScaledInstance(x, y, Image.SCALE_SMOOTH));

    return new JLabel(scaledSampleBatch);
  }

  /**
   * The listener interface for receiving closeAction events. The class that is interested in
   * processing a closeAction event implements this interface, and the object created with that
   * class is registered with a component using the component's <code>addCloseActionListener
   * <code> method. When the closeAction event occurs, that object's appropriate method is invoked.
   *
   * @see CloseActionEvent
   */
  private class CloseActionListener implements ActionListener {
    
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      dispose();
    }
  }
}
