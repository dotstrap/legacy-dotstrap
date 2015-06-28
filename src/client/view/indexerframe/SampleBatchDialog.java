/**
 * SampleBatchDialog.java
 * JRE v1.8.0_45
 * 
 * Created by William Myers on Jun 28, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.view.indexerframe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class SampleBatchDialog extends JDialog {

  private JDialog parent;

  private ActionListener closeActionListener = new ActionListener() {//@formatter:off
    public void actionPerformed(ActionEvent e) {
      dispose();
    }
  };//@formatter:on

  public SampleBatchDialog(JDialog p, BufferedImage sampleBatch, String projName) {
    super(p, "Sample Batch from " + projName, true);
    parent = p;
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

  private Box initButtonBox() {
    Box buttonBox = Box.createHorizontalBox();
    JButton closeButton = new JButton("Close");
    closeButton.addActionListener(closeActionListener);
    buttonBox.add(Box.createGlue());
    buttonBox.add(closeButton);
    buttonBox.add(Box.createGlue());
    return buttonBox;
  }

  private JLabel initSampleBatch(BufferedImage sampleBatch) {
    int x = sampleBatch.getWidth() / 2;
    int y = sampleBatch.getHeight() / 2;
    ImageIcon scaledSampleBatch =
        new ImageIcon(sampleBatch.getScaledInstance(x, y, BufferedImage.SCALE_SMOOTH));
    return new JLabel(scaledSampleBatch);
  }
}
