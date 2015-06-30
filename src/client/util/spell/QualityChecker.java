package client.util.spell;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import client.model.BatchState;
import client.util.ClientLogManager;
import client.util.spell.ISpellCorrector.NoSimilarWordFoundException;

import shared.model.Batch;
import shared.model.Field;

public class QualityChecker implements BatchState.Observer {
  private static final String USAGE_MESSAGE = "Usage: java QualityChecker <dictionary> <word>";

  public QualityChecker() {
    BatchState.addObserver(this);
  }

  public List<String> init(String dictionaryFileName, String inputWord)
      throws NoSimilarWordFoundException {

    ClientLogManager.getLogger().log(Level.FINE,
        "-------------------------------------------------");

    SpellCorrector corrector = new SpellCorrector();
    try {
      corrector.useDictionary(dictionaryFileName);
    } catch (IOException e) {
      ClientLogManager.getLogger().log(Level.FINE, "unable to open dictionairy" + e);
    }

    // ClientLogManager.getLogger().log(Level.FINEST, corrector.wordsInTrie.toString());

    return corrector.suggestSimilarWord(inputWord);
  }

  @Override
  public void cellWasSelected(int x, int y) {}

  @Override
  public void dataWasInput(String value, int record, Field field) {
    try {
      BatchState.notifyWordWasMisspelled(value, record, field, init(field.getKnownData(), value));
    } catch (NoSimilarWordFoundException e) {
      ClientLogManager.getLogger().log(Level.FINER, "no similar word found" + e);
    }
  }

  @Override
  public void wordWasMisspelled(String value, int record, Field field, List<String> suggestions) {}

  @Override
  public void didChangeOrigin(int x, int y) {}

  @Override
  public void didDownload(BufferedImage b) {}

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
        dictionaryFileName = args[0]; // the dictionary file name
        inputWord = args[1]; // the word to correct

        List<String> suggestions = new QualityChecker().init(dictionaryFileName, inputWord);

        System.out.println("Input: [" + inputWord + "]" + "\nFile: [" + dictionaryFileName + "]"
            + "\nSuggestion [" + suggestions + "]");

      } catch (NumberFormatException ex) {
        System.out.println(USAGE_MESSAGE);
      } catch (NoSimilarWordFoundException e) {
        System.out.println("ERROR\nInput: [" + inputWord + "] NOT FOUND\nFile: ["
            + dictionaryFileName + "]");
      }
    } else {
      System.out.println(USAGE_MESSAGE);
    }
  }
}
