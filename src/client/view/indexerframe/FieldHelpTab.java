/**
 * FieldHelpTab.java
 * JRE v1.8.0_45
 * 
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.view.indexerframe;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import client.model.BatchState;
import client.model.Facade;
import client.util.ClientLogManager;

import shared.model.Batch;
import shared.model.Field;

/**
 * The Class FieldHelpTab.
 */
@SuppressWarnings("serial")
public class FieldHelpTab extends JPanel implements BatchState.Observer {
  private Map<Field, String> helpPages;
  private JEditorPane htmlPane;

  /**
   * Instantiates a new field help tab.
   */
  public FieldHelpTab() {
    initialize();
    BatchState.addObserver(this);
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
   * @see client.model.BatchState.Observer#dataWasInput(java.lang.String, int, shared.model.Field,
   * boolean)
   */
  @Override
  public void dataWasInput(String value, int record, Field field,
      boolean shouldResetIsIncorrect) {}

  /*
   * (non-Javadoc)
   * 
   * @see client.model.BatchState.Observer#didChangeOrigin(int, int)
   */
  @Override
  public void didChangeOrigin(int x, int y) {}

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
   * @see client.model.BatchState.Observer#didHighlight()
   */
  @Override
  public void didHighlight() {}

  /*
   * (non-Javadoc)
   * 
   * @see client.model.BatchState.Observer#didSubmit(shared.model.Batch)
   */
  @Override
  public void didSubmit(Batch b) {
    clear();
  }

  /*
   * (non-Javadoc)
   * 
   * @see client.model.BatchState.Observer#didToggleHighlight()
   */
  @Override
  public void didToggleHighlight() {}

  /*
   * (non-Javadoc)
   * 
   * @see client.model.BatchState.Observer#didToggleInvert()
   */
  @Override
  public void didToggleInvert() {}

  /*
   * (non-Javadoc)
   * 
   * @see client.model.BatchState.Observer#didZoom(double)
   */
  @Override
  public void didZoom(double zoomDirection) {}

  /*
   * (non-Javadoc)
   * 
   * @see client.model.BatchState.Observer#fieldWasSelected(int, shared.model.Field)
   */
  @Override
  public void fieldWasSelected(int record, Field field) {
    if (field != null) {
      htmlPane.setText(downloadHelpPage(field));
      ClientLogManager.getLogger().log(Level.FINEST, field.toString());
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see client.model.BatchState.Observer#spellPopupWasOpened(java.lang.String, int,
   * shared.model.Field, java.util.List)
   */
  @Override
  public void spellPopupWasOpened(String value, int record, Field field,
      Set<String> suggestions) {}

  /*
   * (non-Javadoc)
   * 
   * @see client.model.BatchState.Observer#wordWasMisspelled(java.lang.String, int,
   * shared.model.Field)
   */
  @Override
  public void wordWasMisspelled(String value, int record, Field field) {}

  /**
   * Clear.
   */
  private void clear() {
    helpPages.clear();
    htmlPane.setText("");;
    removeAll();
  }

  /**
   * Download help page.
   *
   * @param field the field
   * @return the string
   */
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
      ClientLogManager.getLogger().log(Level.SEVERE, "STACKTRACE: ", e);
      return "";
    }
  }

  /**
   * Initialize.
   */
  private void initialize() {
    helpPages = new HashMap<Field, String>();

    htmlPane = new JEditorPane();
    htmlPane.setContentType("text/html");
    htmlPane.setEditable(false);

    setLayout(new BorderLayout());
    add(new JScrollPane(htmlPane), BorderLayout.CENTER);
  }

}
