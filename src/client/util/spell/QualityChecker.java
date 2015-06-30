/**
 * QualityChecker.java
 * JRE v1.8.0_45
 *
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.util.spell;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;

import client.model.BatchState;
import client.model.Facade;
import client.util.ClientLogManager;
import client.util.spell.SpellCorrector.NoSimilarWordFoundException;

import shared.model.Batch;
import shared.model.Field;

/**
 * The Class QualityChecker.
 */
public class QualityChecker implements BatchState.Observer {

  private static final String USAGE_MESSAGE =
      "Usage: java QualityChecker <dictionary> <word>";

  /**
   * The main method.
   *
   * @param args the arguments
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static void main(String[] args) throws IOException {
    ClientLogManager.initLogs();
    String dictionaryFileName = ""; // the dictionary file name
    String inputWord = ""; // the word to correct

    if (args.length == 2) {
      try {
        dictionaryFileName = args[0];
        inputWord = args[1];

        Set<String> suggestions =
            new QualityChecker().initialize(dictionaryFileName, inputWord);

        System.out.println("Input: [" + inputWord + "]" + "\nFile: ["
            + dictionaryFileName + "]" + "\nSuggestion [" + suggestions + "]");

      } catch (NumberFormatException ex) {
        System.out.println(USAGE_MESSAGE);
      } catch (NoSimilarWordFoundException e) {

        System.out.println("ERROR\nInput: [" + inputWord
            + "] NOT FOUND\nFile: [" + dictionaryFileName + "]");
      }
    } else {
      System.out.println(USAGE_MESSAGE);
    }
  }

  private Map<String, List<String>> knownData;

  List<String> suggestions;

  /**
   * Instantiates a new quality checker.
   */
  public QualityChecker() {
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
      boolean shouldResetIsIncorrect) {
    List<String> currentKnownData = new ArrayList<String>();
    currentKnownData = knownData.get(field.getTitle());

    if (currentKnownData != null) {
      if (!currentKnownData.contains(value.toLowerCase())) {
        BatchState.notifyWordWasMisspelled(value, record, field);
      }
    }
  }

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
    URL url = null;
    try {
      knownData = new HashMap<String, List<String>>();

      for (Field field : Facade.getFields()) {
        if ((Facade.getFields() != null) && (field != null)
            && (field.getKnownData() != null)
            && !field.getKnownData().isEmpty()) {

          url = new URL(Facade.getUrlPrefix() + field.getKnownData());

          knownData.put(field.getTitle(), parseFile(url, ","));
        }

      }
    } catch (Exception e) {
      ClientLogManager.getLogger().log(Level.SEVERE,
          new StringBuilder("ERROR processing: ").append(url).toString(), e);
    }

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
  public void didSubmit(Batch b) {}

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
  public void fieldWasSelected(int record, Field field) {}

  /**
   * Initialize.
   *
   * @param dictionaryFileName the dictionary file name
   * @param inputWord the input word
   * @return the list
   * @throws NoSimilarWordFoundException the no similar word found exception
   */
  public Set<String> initialize(String dictionaryFileName, String inputWord)
      throws NoSimilarWordFoundException {

    SpellCorrector corrector = new SpellCorrector();
    try {
      corrector.useDictionary(dictionaryFileName);
    } catch (IOException e) {
      ClientLogManager.getLogger().log(Level.SEVERE,
          "Unable to open dictionairy", e);
    }
    return corrector.suggestSimilarWord(inputWord);
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
   * Parses the file.
   *
   * @param url the url
   * @param delimiter the delimiter
   * @return the array list
   * @throws Exception the exception
   */
  private ArrayList<String> parseFile(URL url, String delimiter)
      throws Exception {
    ArrayList<String> tokens = new ArrayList<String>();
    BufferedReader br =
        new BufferedReader(new InputStreamReader(url.openStream()));
    String line = "";
    StringTokenizer tok = null;

    while ((line = br.readLine()) != null) {
      tok = new StringTokenizer(line, delimiter);
      while (tok.hasMoreTokens()) {
        tokens.add(tok.nextToken().toLowerCase());
      }
    }

    ClientLogManager.getLogger().log(Level.FINE,
        new StringBuilder("SUCCESS: ").append(url).toString());
    ClientLogManager.getLogger().log(Level.FINEST,
        new StringBuilder("tokens=").append(tokens).toString());
    return tokens;
  }

}
