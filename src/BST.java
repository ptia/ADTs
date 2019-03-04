import java.util.Optional;

public interface BST<T extends Comparable<T>> {
  boolean add(T obj);
  boolean contains(T obj);
  boolean remove(T obj);
  Optional<T> getMin();
  Optional<T> getMax();
}
