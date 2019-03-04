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

    assertFalse(bst.add(2));
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
  }
}