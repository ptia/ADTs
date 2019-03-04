import org.junit.Test;

import static org.junit.Assert.*;

public class LinkedBSTTest {

  LinkedBST<Integer> bst;

  @Test
  public void buildBST() {
    bst = new LinkedBST<>();
    assertFalse(bst.getMax().isPresent());
    assertFalse(bst.remove(3));

    assertTrue(bst.add(5));
    assertTrue(bst.add(4));
    bst.add(6);
    bst.add(2);
    bst.add(7);
    bst.add(3);

    assertFalse(bst.add(2));
    assertFalse(bst.add(7));
  }

  @Test
  public void getMaxGetMin() {
    buildBST();
    assertEquals(Integer.valueOf(7), bst.getMax().get());
    assertEquals(Integer.valueOf(2), bst.getMin().get());
  }

  @Test
  public void remove() {
    buildBST();
    assertFalse(bst.remove(0));
    assertTrue(bst.contains(5));
    assertTrue(bst.remove(5));
    assertFalse(bst.contains(5));
    assertTrue(bst.contains(2));
    assertTrue(bst.remove(4));
    assertTrue(bst.remove(6));
    assertTrue(bst.remove(3));
    assertTrue(bst.remove(2));
    assertTrue(bst.remove(7));
    assertTrue(bst.isEmpty());
  }
}