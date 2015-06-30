package client.util.spell;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Dictionary implements ITrie {
  private Node Root;
  private int WordCount = 0;
  private int NodeCount = 1;

  public Dictionary() {
    Root = new Node(null, "");
  }

  @Override
  public void add(String word) {
    word = word.trim();
    Node found = (Node) find(word);
    if (found != null) {
      found.incrementCount();
      return;
    } else {
      Node lastFound = findLastExisting(word);
      String subWord = word.substring(lastFound.Path.length());
      Node CurrentNode = lastFound;
      for (char letter : subWord.toCharArray()) {
        CurrentNode = CurrentNode.add(new Node(CurrentNode, CurrentNode.Path + letter));
        this.NodeCount++;
      }
      CurrentNode.incrementCount();
      this.WordCount++;

    }
  }

  public Dictionary.Node findLastExisting(String word) {
    String gword = word.toLowerCase();
    Node CurrentNode = Root;
    if (CurrentNode.NodeCount > 0)
      for (char letter : gword.toCharArray()) {
        Node found;
        if (letter == ' ')
          found = CurrentNode.Children[26];
        else
          found = CurrentNode.Children[letter - 'a'];
        if (found != null) {
          CurrentNode = found;
        } else
          return CurrentNode;
      }
    return CurrentNode;
  }

  @Override
  public INode find(String word) {
    if (word.equals(""))
      return null;
    String gword = word.toLowerCase();
    Node CurrentNode = findLastExisting(gword);
    if (CurrentNode.Path.equals("galloway")) {
      System.out.println("found: " + CurrentNode.Path);
    }
    if (CurrentNode.Count > 0 && CurrentNode.Path.equals(gword))
      return (INode) CurrentNode;
    return null;
  }

  @Override
  public int getWordCount() {
    return this.WordCount;
  }

  @Override
  public int getNodeCount() {
    return this.NodeCount;
  }

  /**
   * The toString specification is as follows: For each word, in alphabetical order: <word>
   * <count>\n
   */
  @Override
  public String toString() {
    return Root.toString();
  }

  public Map<String, Integer> toArray() {
    return Root.toArray();
  }

  @Override
  public int hashCode() {
    return this.NodeCount * 17 + this.WordCount * 31 + this.Root.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (o != null) {
      // System.out.println("true not null");
      if (o.getClass().equals(Dictionary.class)) {
        // System.out.println("true same class");
        if (((Dictionary) o).hashCode() == this.hashCode()) {
          // System.out.println("true same hashcode");
          if (((Dictionary) o).NodeCount == this.NodeCount) {
            // System.out.println("true same nodecount");
            if (((Dictionary) o).Root.equals(this.Root)) {
              // System.out.println("true root equal");
              return true;
            }
          }
        }
      }
    }

    return false;
  }

  // -------------------------------------------------------------------------------------------------------------------//
  /**
   * Your trie node class should implement the Trie.Node interface
   */
  public class Node implements ITrie.INode {

    public String Path;
    public char Letter;
    public int Count = 0;
    public Node Parent;
    public int NodeCount = 0;
    public int editDistance = 0;
    public Node[] Children = new Node[27];

    public Node() {}

    public Node(Node Parent, String Word) {
      this.Parent = Parent;
      this.Path = Word;
      if (Word.length() > 0)
        this.Letter = Word.toCharArray()[Word.length() - 1];
      else
        this.Letter = '0';
    }

    public void incrementCount() {
      Count++;
    }

    public Node add(Node child) {
      // int test = child.Letter-'a';
      if (child.Letter == ' ')
        this.Children[26] = child;
      else
        this.Children[child.Letter - 'a'] = child;
      this.NodeCount++;
      return child;
    }

    /**
     * Returns the frequency count for the word represented by the node
     * 
     * @return The frequency count for the word represented by the node
     */
    public int getValue() {

      return Count;
    }

    @Override
    public String toString() {
      StringBuilder output = new StringBuilder();
      if (this.Count >= 1) {
        output.append(this.Path + " " + this.Count + '\n');
      }
      for (Node child : this.Children) {
        if (child != null) {
          output.append(child.toString());
        }
      }
      return output.toString();
    }

    public Map<String, Integer> toArray() {
      Map<String, Integer> WordList = new HashMap<String, Integer>();
      if (this.Count >= 1) {
        WordList.put(this.Path, this.Count);
      }
      for (Node child : this.Children) {
        if (child != null) {
          WordList.putAll(child.toArray());
        }
      }
      return WordList;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + getOuterType().hashCode();
      result = prime * result + Arrays.hashCode(this.Children);
      result = prime * result + this.Count;
      result = prime * result + this.Letter;
      result = prime * result + this.NodeCount;
      result = prime * result + ((this.Parent == null) ? 0 : this.Parent.hashCode());
      result = prime * result + ((this.Path == null) ? 0 : this.Path.hashCode());
      result = prime * result + this.editDistance;
      return result;
    }

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
      if (!Arrays.equals(this.Children, other.Children)) {
        return false;
      }
      if (this.Count != other.Count) {
        return false;
      }
      if (this.Letter != other.Letter) {
        return false;
      }
      if (this.NodeCount != other.NodeCount) {
        return false;
      }
      if (this.Parent == null) {
        if (other.Parent != null) {
          return false;
        }
      } else if (!this.Parent.equals(other.Parent)) {
        return false;
      }
      if (this.Path == null) {
        if (other.Path != null) {
          return false;
        }
      } else if (!this.Path.equals(other.Path)) {
        return false;
      }
      if (this.editDistance != other.editDistance) {
        return false;
      }
      return true;
    }

    private Dictionary getOuterType() {
      return Dictionary.this;
    }

    // @Override
    // public int hashCode() {
    // return this.Count * 31 + this.Path.hashCode() * 17 + this.Letter;
    // }

    // @Override
    // public boolean equals(Object o) {
    // if (o != null) {
    // if (o.getClass() == Dictionary.Node.class) {
    // if (((Dictionary.Node) o).hashCode() == this.hashCode()) {
    // if (((Dictionary.Node) o).Count == this.Count) {
    // if (((Dictionary.Node) o).Letter == this.Letter) {
    // if (((Dictionary.Node) o).Path == this.Path) {
    // if (((Dictionary.Node) o).toString().equals(this.toString())) {
    // return true;
    // }
    // }
    // }
    // }
    // }
    // }
    // }
    // return false;
    // }
  }

}
