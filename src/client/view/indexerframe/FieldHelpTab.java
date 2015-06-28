package client.view.indexerframe;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import client.model.BatchState;
import client.model.Facade;
import client.util.ClientLogManager;

import shared.model.Batch;
import shared.model.Field;

@SuppressWarnings("serial")
public class FieldHelpTab extends JPanel implements BatchState.Observer {
  private Map<Field, String> helpPages;
  private JEditorPane htmlPane;

  public FieldHelpTab() {
    helpPages = new HashMap<Field, String>();

    htmlPane = new JEditorPane();
    htmlPane.setContentType("text/html");
    htmlPane.setEditable(false);

    setLayout(new BorderLayout());
    add(new JScrollPane(htmlPane), BorderLayout.CENTER);

    BatchState.addObserver(this);
  }

  private String downloadHelpPage(Field field) {
    if (helpPages.get(field) != null) {
      return helpPages.get(field);
    }

    InputStream is = Facade.downloadFile(field.getHelpURL());
    String html = "";
    try (Scanner scanner = new Scanner(is)) {
      StringBuilder builder = new StringBuilder();
      while (scanner.hasNextLine()) {
        builder.append(scanner.nextLine());
        builder.append("\n");
      }
      html = builder.toString();

      helpPages.put(field, html);
      return html;
    } catch (Exception e) {
      ClientLogManager.getLogger().log(Level.FINE, "STACKTRACE: ", e);
      return "";
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see client.model.BatchState.Observer#cellWasSelected(int, int)
   */
  @Override
  public void cellWasSelected(int x, int y) {}

  /*
   * (non-Javadoc)
   *
   * @see client.model.BatchState.Observer#fieldWasSelected(int, shared.model.Field)
   */
  @Override
  public void fieldWasSelected(int record, Field field) {
    ClientLogManager.getLogger().log(Level.FINEST, field.toString());
    if (field != null) {
      htmlPane.setText(downloadHelpPage(field));
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see client.model.BatchState.Observer#didChangeOrigin(int, int)
   */
  @Override
  public void didChangeOrigin(int x, int y) {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   *
   * @see client.model.BatchState.Observer#didToggleHighlight()
   */
  @Override
  public void didToggleHighlight() {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   *
   * @see client.model.BatchState.Observer#didHighlight()
   */
  @Override
  public void didHighlight() {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   *
   * @see client.model.BatchState.Observer#didZoom(double)
   */
  @Override
  public void didZoom(double zoomDirection) {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   *
   * @see client.model.BatchState.Observer#didToggleInvert()
   */
  @Override
  public void didToggleInvert() {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   *
   * @see client.model.BatchState.Observer#dataWasInput(java.lang.String, int, int)
   */
  @Override
  public void dataWasInput(String data, int x, int y) {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   *
   * @see client.model.BatchState.Observer#didChangeValue(int, shared.model.Field, java.lang.String)
   */
  @Override
  public void didChangeValue(int record, Field field, String value) {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   *
   * @see client.model.BatchState.Observer#didDownload(java.awt.image.BufferedImage)
   */
  @Override
  public void didDownload(BufferedImage b) {
    helpPages.clear();
    htmlPane.setText("");
  }

  /*
   * (non-Javadoc)
   *
   * @see client.model.BatchState.Observer#didSubmit(shared.model.Batch)
   */
  @Override
  public void didSubmit(Batch b) {
    // TODO Auto-generated method stub

  }

}
