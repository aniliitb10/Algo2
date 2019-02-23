import java.util.LinkedList;

class MyTriesSet
{
  private final CharSet charSet;
  private Node root;

  class Node
  {
    boolean isKey = false;
    Node[] next = new Node[charSet.size()];

    Node(){}

    Node(final Node node)
    {
      this.isKey = node.isKey;
      System.arraycopy(node.next, 0, this.next, 0, this.next.length);
    }
  }

  MyTriesSet(final CharSet charSet)
  {
    this.charSet = charSet;
    this.root = new Node();
  }

  boolean contains(final String key)
  {
    Node node = contains(root, key, 0);
    return (node != null && node.isKey);
  }

  void add(final String s)
  {
    root = add(this.root, s, 0);
  }

  Iterable<String> keysWithPrefix(final String prefix)
  {
    LinkedList<String> queue = new LinkedList<>();
    keysWithPrefix(contains(root, prefix, 0), new StringBuilder(prefix), queue);
    return queue;
  }

  Iterable<String> keysThatMatch(final String key)
  {
    LinkedList<String> queue = new LinkedList<>();
    keysThatMatch(root, new StringBuilder(), queue, key, 0);
    return queue;
  }

  Node getNode(String key)
  {
    Node node = contains(root, key, 0);
    return (node == null) ? null : new Node(node);
  }

  private Node contains(Node node, final String key, int depth)
  {
    if (node == null)          return null;
    if (depth == key.length()) return node;

    final int index = this.charSet.getIndex(key.charAt(depth));
    return contains(node.next[index], key, depth + 1);
  }

  private Node add(Node node, final String key, final int depth)
  {
    if (node == null)          node =  new Node();
    if (depth == key.length()) { node.isKey = true; return node; }

    final int index = charSet.getIndex(key.charAt(depth));

    node.next[index] = add(node.next[index], key, depth + 1);

    return node;
  }

  private void keysWithPrefix(final Node node, final StringBuilder key, final LinkedList<String> queue)
  {
    if (node == null) return;
    if (node.isKey) queue.add(key.toString());

    for (int index = 0; index < this.charSet.size(); ++index)
    {
      keysWithPrefix(node.next[index], key.append(this.charSet.getChar(index)), queue);
      key.deleteCharAt(key.length()-1);
    }
  }

  private void keysThatMatch(final Node node, final StringBuilder key, final LinkedList<String> queue,
                             final String oKey, final int depth)
  {
    if (node == null) return;
    if (depth == oKey.length())
    {
      if (node.isKey) queue.add(key.toString());
      return;
    }

    final char nextCharFromOKey = oKey.charAt(depth);

    for (int index = 0; index < this.charSet.size(); ++index)
    {
      char nextCharToInspect = this.charSet.getChar(index);
      if (nextCharFromOKey == '.' || nextCharFromOKey == nextCharToInspect)
      {
        keysThatMatch(node.next[index], key.append(nextCharToInspect), queue, oKey, depth + 1);
        key.deleteCharAt(key.length()-1);
      }
    }
  }

  public static void main(String[] args)
  {
    MyTriesSet triesSet = new MyTriesSet(new LowerCaseCharSet());
    triesSet.add("ail");
    triesSet.add("kumar");
    triesSet.add("kumara");
    triesSet.add("kumarb");
    triesSet.add("kumarc");
    triesSet.add("kumard");
    triesSet.add("anild");
    triesSet.add("anils");

    for(String key : triesSet.keysThatMatch(".u...."))
      System.out.println(key);

    triesSet = new MyTriesSet(new UpperCaseCharSet());
    triesSet.add("ANIL");
    triesSet.add("ANILK");
    triesSet.add("ANILU");
    triesSet.add("ANILS");

    System.out.println("contains: " + triesSet.contains("ANIL"));
    for(String key : triesSet.keysThatMatch(".N..."))
    {
      System.out.println(key);
    }
  }
}
