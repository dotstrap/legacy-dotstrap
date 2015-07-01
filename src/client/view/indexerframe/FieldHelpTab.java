/**
 * FieldHelpTab.java
 * JRE v1.8.0_45
 * 
 * Created by William Myers on Jun 28, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.view.indexerframe;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.InputStream;
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
    this.initialize();
    BatchState.addObserver(this);
  }

  @Override
  public void cellWasSelected(int x, int y) {}

  @Override
  public void dataWasInput(String value, int record, Field field, boolean shouldResetIsIncorrect) {}

  @Override
  public void didChangeOrigin(int x, int y) {}

  @Override
  public void didDownload(BufferedImage b) {
    helpPages.clear();
    htmlPane.setText("");
  }

  @Override
  public void didHighlight() {}

  @Override
  public void didSubmit(Batch b) {
    this.clear();
  }

  @Override
  public void didToggleHighlight() {}

  @Override
  public void didToggleInvert() {}

  @Override
  public void didZoom(double zoomDirection) {}

  @Override
  public void fieldWasSelected(int record, Field field) {
    if (field != null) {
      htmlPane.setText(downloadHelpPage(field));
      ClientLogManager.getLogger().log(Level.FINEST, field.toString());
    }
  }

  private void clear() {
    helpPages.clear();
    htmlPane.setText("");;
    this.removeAll();
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
      ClientLogManager.getLogger().log(Level.SEVERE, "STACKTRACE: ", e);
      return "";
    }
  }

  private void initialize() {
    helpPages = new HashMap<Field, String>();

    htmlPane = new JEditorPane();
    htmlPane.setContentType("text/html");
    htmlPane.setEditable(false);

    setLayout(new BorderLayout());
    add(new JScrollPane(htmlPane), BorderLayout.CENTER);
  }

  @Override
  public void wordWasMisspelled(String value, int record, Field field) {}

  @Override
  public void spellPopupWasOpened(String value, int record, Field field, List<String> suggestions) {}

}
