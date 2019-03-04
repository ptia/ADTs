import java.util.Optional;

public class LinkedBST<T extends Comparable<T>> implements BST<T>{
  private Node root;

  private Position find(T obj) {
    Node parent = null;
    Node current = root;
    Side side = Side.ROOT;
    while (current != null) {
      switch (obj.compareTo(current.obj)) {
        case -1:
          parent = current;
          current = current.left;
          side = Side.LEFT;
          break;
        case 0:
          return new Position(parent, current, side);
        case 1:
          parent = current;
          current = current.right;
          side = Side.RIGHT;
          break;
      }
    }
    return new Position(parent, null, side);
  }

  private Position findMin(Position start) {
    if (start.current == null) {
      return new Position(null, null, Side.ROOT);
    }
    Node parent = start.parent;
    Node current = start.current;
    Side side = start.side;
    while (current.left != null) {
      parent = current;
      current = current.left;
      side = Side.LEFT;
    }
    return new Position(parent, current, side);
  }

  private Position findMax(Position start) {
    if (start.current == null) {
      return new Position(null, null, Side.ROOT);
    }
    Node parent = start.parent;
    Node current = start.current;
    Side side = start.side;
    while (current.right != null) {
      parent = current;
      current = current.right;
      side = Side.RIGHT;
    }
    return new Position(parent, current, side);
  }

  @Override
  public synchronized boolean add(T obj) {
    Position pos = find(obj);
    if (pos.current != null) {
      return false;
    }
    Node newNode = new Node(obj);
    switch (pos.side) {
      case ROOT:
        root = newNode;
        break;
      case LEFT:
        pos.parent.left = newNode;
        break;
      case RIGHT:
        pos.parent.right = newNode;
        break;
    }
    return true;
  }

  @Override
  public synchronized boolean contains(T obj) {
    return find(obj).foundExact();
  }

  @Override
  public synchronized boolean remove(T obj) {
    Position pos = find(obj);
    if (!pos.foundExact()) {
      return false;
    }
    remove(pos);
    return true;
  }

  private void remove(Position pos) {
    assert pos.foundExact();

    Node newCurrent;
    if (pos.current.left == null) {
      newCurrent = pos.current.right;
    } else if (pos.current.right == null) {
      newCurrent = pos.current.left;
    } else {
      Position posMaxLeft =
          findMax(new Position(pos.current, pos.current.left, Side.LEFT));
      remove(posMaxLeft);
      newCurrent =
          new Node(posMaxLeft.current.obj, pos.current.left, pos.current.right);
    }

    switch (pos.side) {
      case ROOT:
        root = newCurrent;
        break;
      case LEFT:
        pos.parent.left = newCurrent;
        break;
      case RIGHT:
        pos.parent.right = newCurrent;
        break;
    }
  }

  @Override
  public synchronized Optional<T> getMin() {
    Position pos = findMin(new Position(null, root, Side.ROOT));
    if (!pos.foundExact()) {
      return Optional.empty();
    }
    return Optional.of(pos.current.obj);
  }

  @Override
  public synchronized Optional<T> getMax() {
    Position pos = findMax(new Position(null, root, Side.ROOT));
    if (!pos.foundExact()) {
      return Optional.empty();
    }
    return Optional.of(pos.current.obj);
  }

  @Override
  public synchronized boolean isEmpty() {
    return root == null;
  }

  @Override
  public synchronized String toString() {
    if (isEmpty()) {
      return "<empty>\n";
    }
    StringBuilder sb = new StringBuilder();
    toStringHelper(sb, root, 0);
    return sb.toString();
  }

  private void toStringHelper(StringBuilder sb, Node node, int depth) {
    if (node.right != null) {
      toStringHelper(sb, node.right, depth + 1);
    }
    sb.append("\t".repeat(depth));
    sb.append(node.obj);
    sb.append('\n');
    if (node.left != null) {
      toStringHelper(sb, node.left, depth + 1);
    }
  }

  private class Position {
    private final Node parent, current;
    private final Side side;
    private Position(Node parent, Node current, Side side) {
      assert (parent == null) == (side == Side.ROOT);
      this.parent = parent;
      this.current = current;
      this.side = side;
    }
    private boolean foundExact() {
      return current != null;
    }
    private boolean isRoot() {
      return side == Side.ROOT;
    }
  }

  private enum Side {
    LEFT, RIGHT, ROOT
  }

  private class Node {
    private Node left, right;
    private final T obj;
    private Node(T obj) {
      this(obj, null, null);
    }
    private Node(T obj, Node left, Node right) {
      this.obj = obj;
      this.left = left;
      this.right = right;
    }
  }
}
