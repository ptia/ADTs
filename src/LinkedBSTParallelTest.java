import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class LinkedBSTParallelTest {
  private LinkedBST<Integer> bst;
  private int[] elems = {5, 4, 6, 7, 3, 9, 8, -1, 15, 10, -3, -2, 12};

  @Test
  public void test1() throws InterruptedException {
    bst = new LinkedBST<>();

    //Adding
    List<Thread> ts = new ArrayList<>();
    for (int elem : elems) {
      ts.add(new Thread(() -> {
        assertTrue(bst.add(elem));
      }));
    }
    ts.forEach(Thread::start);
    for (Thread t : ts) {
      t.join();
    }
    for (int elem : elems) {
      assertTrue(bst.contains(elem));
    }
    System.out.println(bst);

    //Removing
    ts = new ArrayList<>();
    for (int elem : elems) {
      ts.add(new Thread(() -> {
        assertTrue(bst.remove(elem));
      }));
    }
    ts.forEach(Thread::start);
    for (Thread t : ts) {
      t.join();
    }
    assertTrue(bst.isEmpty());
  }
}
