package com.alexexe19.datastructures.core.hierarchical;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AvlTreeTest {

    private AvlTree<Integer> tree;

    @BeforeEach
    void setUp() {
        tree = new AvlTree<>();
    }

    @Test
    void insert_sortedSequence_allValuesRemainFindable() {
        // Inserting an already-sorted sequence is the classic case that
        // turns a plain BST into a degenerate linked list. We can't inspect
        // the tree's internal height/balance fields from outside, but we
        // CAN verify the functional contract still holds after a
        // rebalancing-heavy insert pattern.
        for (int i = 1; i <= 100; i++) {
            tree.insert(i);
        }

        assertEquals(100, tree.size());
        for (int i = 1; i <= 100; i++) {
            assertTrue(tree.contains(i), "expected tree to contain " + i);
        }
    }

    @Test
    void insert_descendingSequence_allValuesRemainFindable() {
        for (int i = 100; i >= 1; i--) {
            tree.insert(i);
        }

        for (int i = 1; i <= 100; i++) {
            assertTrue(tree.contains(i));
        }
    }

    @Test
    void findMin_andFindMax_returnCorrectBoundaries() {
        int[] values = {50, 20, 80, 10, 30, 70, 90, 5};
        for (int v : values) tree.insert(v);

        assertEquals(5, tree.findMin());
        assertEquals(90, tree.findMax());
    }

    @Test
    void findMin_onEmptyTree_returnsNull() {
        assertNull(tree.findMin());
        assertNull(tree.findMax());
    }

    @Test
    void contains_missingValue_returnsFalse() {
        tree.insert(10);
        tree.insert(20);

        assertFalse(tree.contains(999));
    }

    @Test
    void insert_nullValue_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> tree.insert(null));
    }

    @Test
    void delete_leafAndInternalNodes_keepsRemainingValuesFindable() {
        int[] values = {50, 20, 80, 10, 30, 70, 90, 5, 15};
        for (int v : values) tree.insert(v);

        tree.delete(20); // has two children
        tree.delete(5);  // leaf

        assertFalse(tree.contains(20));
        assertFalse(tree.contains(5));
        for (int v : new int[]{50, 80, 10, 30, 70, 90, 15}) {
            assertTrue(tree.contains(v), "lost value " + v + " after deletions");
        }
        assertEquals(values.length - 2, tree.size());
    }

    @Test
    void delete_thenBulkDelete_treeStaysConsistent() {
        for (int i = 1; i <= 200; i++) tree.insert(i);
        for (int i = 2; i <= 200; i += 2) tree.delete(i);

        assertEquals(100, tree.size());
        for (int i = 1; i <= 200; i++) {
            if (i % 2 == 0) assertFalse(tree.contains(i));
            else assertTrue(tree.contains(i));
        }
    }

    @Test
    void isEmpty_beforeAndAfterClear() {
        assertTrue(tree.isEmpty());

        tree.insert(1);
        assertFalse(tree.isEmpty());

        tree.clear();
        assertTrue(tree.isEmpty());
    }
}
