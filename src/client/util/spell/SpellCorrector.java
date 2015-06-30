/**
 * SpellCorrector.java
 * JRE v1.8.0_45
 * 
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.util.spell;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * The Class SpellCorrector.
 */
public class SpellCorrector {

  private Trie dict;

  /**
   * Instantiates a new spell corrector.
   */
  public SpellCorrector() {
    dict = new Trie();
  }

  /**
   * Suggest similar word.
   *
   * @param inputWord the input word
   * @return the string
   * @throws NoSimilarWordFoundException the no similar word found exception
   */
  public Set<String> suggestSimilarWord(String inputWord)
      throws NoSimilarWordFoundException {

    if (inputWord == null || inputWord.equals("")) {
      return new HashSet<String>();
    }

    if (dict.find(inputWord) != null) {
      Set<String> hSet = new HashSet<String>();
      hSet.add(inputWord);
      return hSet;
    }

    Set<String> oneEditDistances = processOneEditDistances(inputWord);

    SortedSet<String> twoEditDistances = new TreeSet<String>();
    for (String curr : oneEditDistances) {
      for (String newWord : processOneEditDistances(curr)) {
        twoEditDistances.add(newWord);
      }
    }

    if (twoEditDistances.isEmpty()) {
      throw new NoSimilarWordFoundException();
    }

    return twoEditDistances;
  }

  /**
   * Use dictionary.
   *
   * @param dictionaryFileName the dictionary file name
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public void useDictionary(String dictionaryFileName) throws IOException {
    Scanner scanner = new Scanner(new URL(dictionaryFileName).openStream());
    dict = new Trie();
    while (scanner.hasNext()) {
      dict.add(scanner.next().toLowerCase());
    }
    scanner.close();

  }

  /**
   * Process alteration.
   *
   * @param inputWord the input word
   * @return the string[]
   */
  private String[] processAlteration(String inputWord) {
    String[] similarWords = new String[inputWord.length() * 25];
    for (int i = 0; i < inputWord.length(); i++) {
      String currLetter = Character.toString(inputWord.charAt(i));
      int j = 0;
      for (Alphabet l : Alphabet.values()) {
        String curr = l.toString();
        if (currLetter.equalsIgnoreCase(curr)) {
          continue;
        }
        similarWords[(i * 25) + j++] =
            inputWord.substring(0, i) + curr + inputWord.substring(i + 1);
      }

    }
    return similarWords;
  }

  /**
   * Process deletion.
   *
   * @param inputWord the input word
   * @return the string[]
   */
  private String[] processDeletion(String inputWord) {
    String[] similarWords = new String[inputWord.length()];
    for (int i = 0; i < similarWords.length; i++) {
      similarWords[i] = inputWord.substring(0, i) + inputWord.substring(i + 1);
    }
    return similarWords;
  }

  /**
   * Process insertion.
   *
   * @param inputWord the input word
   * @return the string[]
   */
  private String[] processInsertion(String inputWord) {
    String[] similarWords = new String[(inputWord.length() + 1) * 26];
    for (int i = 0; i <= inputWord.length(); i++) {
      int j = 0;
      for (Alphabet l : Alphabet.values()) {
        String curr = l.toString();
        similarWords[(i * 26) + j++] =
            inputWord.substring(0, i) + curr + inputWord.substring(i);
      }

    }
    return similarWords;
  }

  /**
   * Process one edit distances.
   *
   * @param inputWord the input word
   * @return the sets the
   */
  private Set<String> processOneEditDistances(String inputWord) {
    Set<String> similarWords = new HashSet<String>();
    if (inputWord != null) {
      for (String curr : processDeletion(inputWord)) {
        similarWords.add(curr);
      }
      for (String curr : processTransposition(inputWord)) {
        similarWords.add(curr);
      }
      for (String curr : processAlteration(inputWord)) {
        similarWords.add(curr);
      }
      for (String curr : processInsertion(inputWord)) {
        similarWords.add(curr);
      }
      return similarWords;
    } else {
      return null;
    }

  }

  /**
   * Process transposition.
   *
   * @param inputWord the input word
   * @return the string[]
   */
  private String[] processTransposition(String inputWord) {
    String[] similarWords = new String[inputWord.length() - 1];
    for (int i = 0; i < similarWords.length; i++) {
      similarWords[i] = inputWord.substring(0, i) + inputWord.charAt(i + 1)
          + inputWord.charAt(i) + inputWord.substring(i + 2);
    }
    return similarWords;
  }

  /**
   * The Enum Alphabet.
   */
  public enum Alphabet {

    a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z;
  }

  /**
   * The Class NoSimilarWordFoundException.
   */
  @SuppressWarnings("serial")
  public static class NoSimilarWordFoundException extends Exception {
  }

}
