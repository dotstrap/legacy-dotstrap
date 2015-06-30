/**
 * Trie.java
 * JRE v1.8.0_45
 * 
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.util.spell;

import java.util.Arrays;

/**
 * The Class Trie.
 */
public class Trie {

  private int wordCount;

  private int nodeCount;

  private Node root;

  /**
   * Instantiates a new trie.
   */
  public Trie() {
    wordCount = 0;
    nodeCount = 1;
    root = new Node();
  }

  /**
   * Adds the.
   *
   * @param word the word
   */
  public void add(String word) {
    root.add(word.toLowerCase());
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Trie other = (Trie) obj;
    if (nodeCount != other.nodeCount) {
      return false;
    }
    if (root == null) {
      if (other.root != null) {
        return false;
      }
    } else if (!root.equals(other.root)) {
      return false;
    }
    if (wordCount != other.wordCount) {
      return false;
    }
    return true;
  }

  /**
   * Find.
   *
   * @param word the word
   * @return the node
   */
  public Node find(String word) {
    return root.find(word.toLowerCase());
  }

  public int getNodeCount() {
    return nodeCount;
  }

  public int getWordCount() {
    return wordCount;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + nodeCount;
    result = (prime * result) + ((root == null) ? 0 : root.hashCode());
    result = (prime * result) + wordCount;
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return root.toString("");
  }

  /**
   * The Class Node.
   */
  public class Node {

    private int freq;

    private Node[] letters;

    /**
     * Instantiates a new node.
     */
    public Node() {
      freq = 0;
      letters = new Node[26];
    }

    /**
     * Adds the.
     *
     * @param word the word
     */
    public void add(String word) {
      if (word != null && !word.isEmpty()) {
        StringBuilder sb = new StringBuilder(word);
        int index = sb.charAt(0) - 'a';
        if (letters[index] == null) {
          letters[index] = new Node();
          nodeCount++;
        }
        sb.deleteCharAt(0);
        letters[index].add(sb.toString());
      } else {
        if (freq == 0) {
          wordCount++;
        }
        freq++;
      }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      Node other = (Node) obj;
      if (!getOuterType().equals(other.getOuterType())) {
        return false;
      }
      if (freq != other.freq) {
        return false;
      }
      if (!Arrays.equals(letters, other.letters)) {
        return false;
      }
      return true;
    }

    /**
     * Find.
     *
     * @param word the word
     * @return the node
     */
    public Node find(String word) {
      if (!word.isEmpty()) {
        StringBuilder sb = new StringBuilder(word);
        int index = sb.charAt(0) - 'a';
        if (letters[index] != null) {
          sb.deleteCharAt(0);
          return letters[index].find(sb.toString());
        } else {
          return null;
        }
      } else {
        if (freq > 0) {
          return this;
        } else {
          return null;
        }
      }
    }

    public int getValue() {
      return freq;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = (prime * result) + getOuterType().hashCode();
      result = (prime * result) + freq;
      result = (prime * result) + Arrays.hashCode(letters);
      return result;
    }

    /**
     * To string.
     *
     * @param word the word
     * @return the string
     */
    public String toString(String word) {
      StringBuilder out = new StringBuilder();

      if (freq > 0) {
        out.append(word + "\n");
      }

      for (int i = 0; i < letters.length; i++) {
        if (letters[i] != null) {
          StringBuilder curr = new StringBuilder(word);
          curr.append(Character.toString((char) ('a' + i)));
          out.append(letters[i].toString(curr.toString()));
        }
      }
      return out.toString();
    }

    private Trie getOuterType() {
      return Trie.this;
    }

  }

}
