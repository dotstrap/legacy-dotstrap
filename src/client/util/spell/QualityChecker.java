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
import java.util.StringTokenizer;
import java.util.logging.Level;

import client.model.BatchState;
import client.model.Facade;
import client.util.ClientLogManager;
import client.util.spell.ISpellCorrector.NoSimilarWordFoundException;

import shared.model.Batch;
import shared.model.Field;

public class QualityChecker implements BatchState.Observer {
  private static final String USAGE_MESSAGE =
      "Usage: java QualityChecker <dictionary> <word>";

  private Map<String, List<String>> knownData;
  List<String> suggestions;

  public QualityChecker() {
    BatchState.addObserver(this);
  }

  public List<String> initialize(String dictionaryFileName, String inputWord)
      throws NoSimilarWordFoundException {

    SpellCorrector corrector = new SpellCorrector();
    try {
      corrector.useDictionary(dictionaryFileName);
    } catch (IOException e) {
      ClientLogManager.getLogger().log(Level.SEVERE,
          "Unable to open dictionairy", e);
    }

    // ClientLogManager.getLogger().log(Level.FINEST, corrector.wordsInTrie.toString());

    return corrector.suggestSimilarWord(inputWord);
  }

  @Override
  public void cellWasSelected(int x, int y) {}

  @Override
  public void dataWasInput(String value, int record, Field field) {
    List<String> currentKnownData = new ArrayList<String>();
    currentKnownData = knownData.get(field.getTitle());

    if (currentKnownData != null) {
      if (!currentKnownData.contains(value.toLowerCase())) {
        BatchState.notifyWordWasMisspelled(value, record, field);
      }
    }
  }

  @Override
  public void wordWasMisspelled(String value, int record, Field field) {}

  @Override
  public void didChangeOrigin(int x, int y) {}

  @Override
  public void didDownload(BufferedImage b) {
    URL url = null;
    try {
      knownData = new HashMap<String, List<String>>();
      for (Field field : Facade.getFields()) {
        if (Facade.getFields() != null && field != null
            && field.getKnownData() != null
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
    ClientLogManager.getLogger().log(Level.FINER,
        new StringBuilder("tokens=").append(tokens).toString());
    return tokens;
  }

  @Override
  public void didHighlight() {}

  @Override
  public void didSubmit(Batch b) {}

  @Override
  public void didToggleHighlight() {}

  @Override
  public void didToggleInvert() {}

  @Override
  public void didZoom(double zoomDirection) {}

  @Override
  public void fieldWasSelected(int record, Field field) {}

  public static void main(String[] args) throws IOException {
    ClientLogManager.initLogs();
    String dictionaryFileName = ""; // the dictionary file name
    String inputWord = ""; // the word to correct

    if (args.length == 2) {
      try {
        dictionaryFileName = args[0];
        inputWord = args[1];

        List<String> suggestions =
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

  @Override
  public void spellPopupWasOpened(String value, int record, Field field,
      List<String> suggestions) {}

}
