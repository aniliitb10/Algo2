public class MyTST<ValueType>
{
  private Node root;

  private class Node
  {
    private char nodeChar;
    private ValueType value;
    private Node left, mid, right;

    private Node(char c)
    {
      this.nodeChar = c;
    }
  }

  void insert(String key, ValueType value)
  {
    if (key.isEmpty()) throw new IllegalArgumentException("Key is empty!");
    root = insert(root, key, value, 0);
  }

  ValueType get(String key)
  {
    Node node = get(root, key, 0);
    return (node == null) ? null : node.value;
  }

  private Node insert(Node node, final String key, final ValueType value, int depth)
  {
    if (node == null) node = new Node(key.charAt(depth)); // Important: Not to assign a value here as not every node has a value

    final char c = key.charAt(depth);

    if      (c < node.nodeChar)         node.left  = insert(node.left,  key, value, depth);
    else if (c > node.nodeChar)         node.right = insert(node.right, key, value, depth);
    else if (depth < (key.length() -1)) node.mid   = insert(node.mid,   key, value, depth+1);
    else                                node.value = value;

    return node;
  }

  private Node get(Node node, final String key, int depth)
  {
    if (node == null) return null;
    if (key.isEmpty()) return null;

    final char c = key.charAt(depth);

    if      (c < node.nodeChar)          return get(node.left,  key, depth);
    else if (c > node.nodeChar)          return get(node.right, key, depth);
    else if (depth < (key.length() - 1)) return get(node.mid,   key, depth+1);
    else                                 return node;
  }


  public static void main(String[] args)
  {
    MyTST<Integer> myTST = new MyTST<>();
    myTST.insert("anil", 123);
    myTST.insert("kumar", 200);
    myTST.insert("kumarAnil", 400);
    myTST.insert("kumarAnil", 800);

    System.out.println(myTST.get("kumarAnil"));
  }


}
