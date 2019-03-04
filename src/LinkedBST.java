import java.util.Optional;

public class LinkedBST<T extends Comparable<T>> implements BST<T>{
  private Node root;

  private Position find(T obj) {
    Node parent = null;
    Node current = root;
    while (current != null) {
      switch (obj.compareTo(current.obj)) {
        case -1:
          parent = current;
          current = current.left;
          break;
        case 0:
          return new Position(parent, current);
        case 1:
          parent = current;
          current = current.right;
          break;
      }
    }
    return new Position(parent, null);
  }

  private Position findMin(Position start) {
    if (start.current == null) {
      return new Position(null, null);
    }
    Node parent = start.parent;
    Node current = start.current;
    while (current.left != null) {
      parent = current;
      current = current.left;
    }
    return new Position(parent, current);
  }

  private Position findMax(Position start) {
    if (start.current == null) {
      return new Position(null, null);
    }
    Node parent = start.parent;
    Node current = start.current;
    while (current.right != null) {
      parent = current;
      current = current.right;
    }
    return new Position(parent, current);
  }

  @Override
  public boolean add(T obj) {
    Position pos = find(obj);
    if (pos.current != null) {
      return false;
    }
    Node newNode = new Node(obj);
    if (pos.parent != null) {
      switch (obj.compareTo(pos.parent.obj)) {
        case -1:
          pos.parent.left = newNode;
          break;
        case 1:
          pos.parent.right = newNode;
      }
    } else {
      root = newNode;
    }
    return true;
  }

  @Override
  public boolean contains(T obj) {
    return find(obj).foundExact();
  }

  @Override
  public boolean remove(T obj) {
    Position pos = find(obj);
    Node newCurrent;

    if (!pos.foundExact()) {
      return false;
    }

    if (pos.current.left == null) {
      newCurrent = pos.current.right;
    } else if (pos.current.right == null) {
      newCurrent = pos.current.left;
    } else {
      Position posMaxLeft = findMax(new Position(pos.current, pos.current.left));
      posMaxLeft.parent.right = null;
      newCurrent = posMaxLeft.current;
    }

    if (pos.isRoot()) {
      root = newCurrent;
    } else {
      switch (obj.compareTo(pos.parent.obj)) {
        case -1:
          pos.parent.left = newCurrent;
          break;
        case 1:
          pos.parent.right = newCurrent;
          break;
      }
    }
    return true;
  }

  @Override
  public Optional<T> getMin() {
    Position pos = findMin(new Position(null, root));
    if (!pos.foundExact()) {
      return Optional.empty();
    }
    return Optional.of(pos.current.obj);
  }

  @Override
  public Optional<T> getMax() {
    Position pos = findMax(new Position(null, root));
    if (!pos.foundExact()) {
      return Optional.empty();
    }
    return Optional.of(pos.current.obj);
  }

  private class Position {
    private final Node parent, current;
    private Position(Node parent, Node current) {
      this.parent = parent;
      this.current = current;
    }
    private boolean foundExact() {
      return current != null;
    }
    private boolean isRoot() {
      return parent == null;
    }
  }

  private class Node {
    private Node left, right;
    private final T obj;
    private Node(T obj) {
      this.obj = obj;
    }
  }
}
