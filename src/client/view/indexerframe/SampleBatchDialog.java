package client.view.indexerframe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import client.util.ClientLogManager;

@SuppressWarnings("serial")
public class SampleBatchDialog extends JDialog {
  private JDialog              parent;

  private static BufferedImage NULL_IMAGE = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);

  /**
   * Instantiates a new SampleBatchDialog.
   *
   */
  public SampleBatchDialog(JDialog p, URL batchUrl, String projName) {
    // Initialize
    super(p, "Sample Batch from " + projName, true);
    parent = p;
    setSize(new Dimension(500, 400));
    setLocationRelativeTo(parent);
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setLayout(new BorderLayout());
    setResizable(false);
    setModal(true);

    add(initSampleBatch(batchUrl), BorderLayout.CENTER);

    add(initButtonBox(), BorderLayout.SOUTH);

    pack();
    setVisible(true);
  }

  /**
   * Load and then scale an image from specified URL
   *
   * @param batchUrl the URL to load the image from
   * @return
   */
  private JLabel initSampleBatch(URL batchUrl) {
    BufferedImage sampleBatch = loadImage(batchUrl);
    int x = sampleBatch.getWidth() / 2;
    int y = sampleBatch.getHeight() / 2;
    ImageIcon scaledSampleBatch =
        new ImageIcon(sampleBatch.getScaledInstance(x, y, BufferedImage.SCALE_SMOOTH));
    ClientLogManager.getLogger().log(Level.FINEST, "loaded sample batch: " + batchUrl.toString());
   return new JLabel(scaledSampleBatch);
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

  /**
   * Loads an image from the given URL
   *
   * @param batchUrl
   * @return the unscaled BufferedImage | an empty image if we encounter an error
   */
  private BufferedImage loadImage(URL batchUrl) {
    try {
      return ImageIO.read(batchUrl);
    } catch (IOException e) {
      ClientLogManager.getLogger().log(Level.FINE, "STACKTRACE: ", e);
      return NULL_IMAGE;
    }
  }

  private ActionListener closeActionListener = new ActionListener() {//@formatter:off
    public void actionPerformed(ActionEvent e) {
      dispose();
    }
  };//@formatter:on
}
