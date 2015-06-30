package client.util.spell;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;

import client.util.ClientLogManager;

public class SpellCorrector implements ISpellCorrector {

  public Dictionary dictionary;

  SortedSet<String> Check1 = new TreeSet<String>();

  private char[] Alphabet =
      {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
          'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

  public SpellCorrector() {

  }

  @SuppressWarnings("serial")
  public static class NoSimilarWord extends NoSimilarWordFoundException {
    public NoSimilarWord(String message) {
      super();
    }
  }

  @Override
  public void useDictionary(String file) throws IOException {
    URL dictionaryFileName = new URL(file);

    Scanner scanner = new Scanner(dictionaryFileName.openStream());
    scanner.useDelimiter(",");
    dictionary = new Dictionary();
    while (scanner.hasNext()) {
      dictionary.add(scanner.next().toLowerCase());
    }
    scanner.close();
  }

  @Override
  public List<String> suggestSimilarWord(String inputWord) {

    if (inputWord == null || inputWord.equals(""))
      return new ArrayList<String>();

    StringBuilder consoleOutput = new StringBuilder(inputWord);
    ClientLogManager.getLogger().log(Level.FINE, consoleOutput.toString());

    SortedSet<String> SimilarWords = new TreeSet<String>();
    String gword = inputWord.toLowerCase();
    ArrayList<Dictionary.Node> nodes = new ArrayList<Dictionary.Node>();
    ArrayList<Dictionary.Node> nodes2 = new ArrayList<Dictionary.Node>();
    nodes.addAll(DeletionList(gword));
    nodes.addAll(TranspositionList(gword));
    nodes.addAll(AlterationList(gword));
    nodes.addAll(InsertionList(gword));
    for (Dictionary.Node node : nodes) {
      if (node != null && node.editDistance == 1)
        SimilarWords.add(node.Path);
    }

    nodes2.addAll(DeletionList());
    nodes2.addAll(TranspositionList());
    nodes2.addAll(AlterationList());
    nodes2.addAll(InsertionList());
    for (Dictionary.Node node : nodes2) {
      if (node != null && node.editDistance == 2)
        SimilarWords.add(node.Path);
    }
    if (SimilarWords.size() > 0) {
      return new ArrayList<String>(SimilarWords);
    }

    return new ArrayList<String>();
  }

  private List<Dictionary.Node> DeletionList(String inputWord) {
    SortedSet<String> DeletionRawOneList = new TreeSet<String>();
    for (int i = 0; i < inputWord.toCharArray().length; i++) {
      StringBuilder x = new StringBuilder();
      x.append(inputWord);
      x.deleteCharAt(i);
      DeletionRawOneList.add(x.toString());
    }
    List<Dictionary.Node> foundNodes = new ArrayList<Dictionary.Node>();
    for (String word : DeletionRawOneList) {
      Dictionary.Node node = (Dictionary.Node) dictionary.find(word);
      if (node != null) {
        node.editDistance = 1;
        foundNodes.add(node);
      }
    }
    if (foundNodes.size() > 0)
      return foundNodes;
    Check1.addAll(DeletionRawOneList);
    return foundNodes;
  }

  private List<Dictionary.Node> DeletionList() {
    SortedSet<String> DeletionRawOneList = Check1;
    SortedSet<String> DeletionRawTwoList = new TreeSet<String>();
    for (String word : DeletionRawOneList) {
      for (int i = 0; i < word.toCharArray().length; i++) {
        StringBuilder x = new StringBuilder();
        x.append(word);
        x.deleteCharAt(i);
        DeletionRawTwoList.add(x.toString());
      }
    }
    List<Dictionary.Node> foundNodes = new ArrayList<Dictionary.Node>();
    for (String word : DeletionRawTwoList) {
      Dictionary.Node node = (Dictionary.Node) dictionary.find(word);
      if (node != null) {
        node.editDistance = 2;
        foundNodes.add(node);
      }
    }
    if (foundNodes.size() > 0)
      return foundNodes;
    return foundNodes;
  }

  private List<Dictionary.Node> TranspositionList(String inputWord) {
    SortedSet<String> TranspositionListStrings = new TreeSet<String>();
    for (int i = 0; i < inputWord.toCharArray().length - 1; i++) {
      StringBuilder x = new StringBuilder();
      x.append(inputWord);
      char temp = x.charAt(i);
      x.deleteCharAt(i);
      x.insert(i + 1, temp);
      TranspositionListStrings.add(x.toString());
    }
    List<Dictionary.Node> foundNodes = new ArrayList<Dictionary.Node>();
    for (String word : TranspositionListStrings) {
      Dictionary.Node node = (Dictionary.Node) dictionary.find(word);
      if (node != null) {
        node.editDistance = 1;
        foundNodes.add(node);
      }
    }
    if (foundNodes.size() > 0)
      return foundNodes;
    Check1.addAll(TranspositionListStrings);
    return foundNodes;
  }

  private List<Dictionary.Node> TranspositionList() {
    SortedSet<String> CheckList = Check1;
    SortedSet<String> TranspositionListStrings2 = new TreeSet<String>();
    for (String word : CheckList) {
      for (int i = 0; i < word.toCharArray().length - 1; i++) {
        StringBuilder x = new StringBuilder();
        x.append(word);
        char temp = x.charAt(i);
        x.deleteCharAt(i);
        x.insert(i + 1, temp);
        TranspositionListStrings2.add(x.toString());
      }
    }
    List<Dictionary.Node> foundNodes = new ArrayList<Dictionary.Node>();
    for (String word : TranspositionListStrings2) {
      Dictionary.Node node = (Dictionary.Node) dictionary.find(word);
      if (node != null) {
        node.editDistance = 2;
        foundNodes.add(node);
      }
    }
    if (foundNodes.size() > 0)
      return foundNodes;
    return foundNodes;
  }

  private List<Dictionary.Node> AlterationList(String inputWord) {
    SortedSet<String> TempList = new TreeSet<String>();
    for (int i = 0; i < inputWord.toCharArray().length; i++) {
      for (char letter : Alphabet) {
        StringBuilder x = new StringBuilder();
        x.append(inputWord);
        if (letter != x.charAt(i)) {
          x.insert(i, letter);
          x.deleteCharAt(i + 1);
          TempList.add(x.toString());
        }
      }
    }
    List<Dictionary.Node> foundNodes = new ArrayList<Dictionary.Node>();
    for (String word : TempList) {
      Dictionary.Node node = (Dictionary.Node) dictionary.find(word);
      if (node != null) {
        node.editDistance = 1;
        foundNodes.add(node);
      }
    }
    if (foundNodes.size() > 0)
      return foundNodes;
    Check1.addAll(TempList);
    return foundNodes;
  }

  private List<Dictionary.Node> AlterationList() {
    SortedSet<String> CheckList = Check1;
    SortedSet<String> TempList2 = new TreeSet<String>();
    for (String word : CheckList) {
      for (int i = 0; i < word.toCharArray().length; i++) {
        for (char letter : Alphabet) {
          StringBuilder x = new StringBuilder();
          x.append(word);
          if (letter != x.charAt(i)) {
            x.insert(i, letter);
            x.deleteCharAt(i + 1);
            TempList2.add(x.toString());
          }
        }
      }
    }
    List<Dictionary.Node> foundNodes = new ArrayList<Dictionary.Node>();
    for (String word : TempList2) {
      Dictionary.Node node = (Dictionary.Node) dictionary.find(word);
      if (node != null) {
        node.editDistance = 2;
        foundNodes.add(node);
      }
    }
    if (foundNodes.size() > 0)
      return foundNodes;
    return foundNodes;
  }

  private List<Dictionary.Node> InsertionList(String inputWord) {
    SortedSet<String> TempList = new TreeSet<String>();
    for (int i = 0; i <= inputWord.toCharArray().length; i++) {
      for (char letter : Alphabet) {
        StringBuilder x = new StringBuilder();
        x.append(inputWord);
        if (i < inputWord.toCharArray().length) {
          if (letter != x.charAt(i)) {
            x.insert(i, letter);
            TempList.add(x.toString());
          }
        } else {
          x.append(letter);
          TempList.add(x.toString());
        }
      }
    }
    List<Dictionary.Node> foundNodes = new ArrayList<Dictionary.Node>();
    for (String word : TempList) {
      Dictionary.Node node = (Dictionary.Node) dictionary.find(word);
      if (node != null) {
        node.editDistance = 1;
        foundNodes.add(node);
      }
    }
    if (foundNodes.size() > 0)
      return foundNodes;
    Check1.addAll(TempList);
    return foundNodes;
  }

  private List<Dictionary.Node> InsertionList() {
    SortedSet<String> CheckList = Check1;
    SortedSet<String> TempList2 = new TreeSet<String>();
    for (String word : CheckList) {
      for (int i = 0; i <= word.toCharArray().length; i++) {
        for (char letter : Alphabet) {
          StringBuilder x = new StringBuilder();
          x.append(word);
          if (i < word.toCharArray().length) {
            if (letter != x.charAt(i)) {
              x.insert(i, letter);
              TempList2.add(x.toString());
            }
          } else {
            x.append(letter);
            TempList2.add(x.toString());
          }
        }
      }
    }
    List<Dictionary.Node> foundNodes = new ArrayList<Dictionary.Node>();
    for (String word : TempList2) {
      Dictionary.Node node = (Dictionary.Node) dictionary.find(word);
      if (node != null) {
        node.editDistance = 2;
        foundNodes.add(node);
      }
    }
    if (foundNodes.size() > 0)
      return foundNodes;
    return foundNodes;
  }

  public boolean wordKnown(String word) {
    ITrie.INode tester = dictionary.find(word.toLowerCase());
    if (tester == null)
      return false;
    else
      return true;
  }
}
