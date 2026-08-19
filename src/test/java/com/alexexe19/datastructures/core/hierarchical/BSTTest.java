package com.alexexe19.datastructures.core.hierarchical;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BSTTest {

    private BST<Integer> bst;

    //              8
    //          3       10
    //        1   6        14
    //           4 7      13
    private static final int[] VALUES = {8, 3, 10, 1, 6, 14, 4, 7, 13};

    @BeforeEach
    void setUp() {
        bst = new BST<>();
    }

    @Test
    void insert_thenContains_findsAllInsertedValues() {
        for (int v : VALUES) bst.insert(v);

        for (int v : VALUES) {
            assertTrue(bst.contains(v), "expected tree to contain " + v);
        }
        assertEquals(VALUES.length, bst.size());
    }

    @Test
    void insertV2_recursiveVersion_behavesTheSameAsInsert() {
        for (int v : VALUES) bst.insertV2(v);

        for (int v : VALUES) {
            assertTrue(bst.contains(v));
        }
        assertEquals(VALUES.length, bst.size());
    }

    @Test
    void contains_missingValue_returnsFalse() {
        for (int v : VALUES) bst.insert(v);

        assertFalse(bst.contains(999));
    }

    @Test
    void insert_duplicateValue_isIgnored() {
        bst.insert(5);
        bst.insert(5);

        assertEquals(1, bst.size());
    }

    @Test
    void delete_leafNode_removesItWithoutAffectingSiblings() {
        for (int v : VALUES) bst.insert(v);

        bst.delete(1);

        assertFalse(bst.contains(1));
        assertTrue(bst.contains(3));
        assertTrue(bst.contains(6));
    }

    @Test
    void delete_nodeWithTwoChildren_promotesSuccessorAndKeepsTreeSearchable() {
        for (int v : VALUES) bst.insert(v);

        bst.delete(10);

        assertFalse(bst.contains(10));
        for (int v : VALUES) {
            if (v != 10) assertTrue(bst.contains(v), "lost value " + v + " after deleting 10");
        }
    }

    @Test
    void delete_valueNotPresent_returnsNullAndLeavesTreeUnchanged() {
        for (int v : VALUES) bst.insert(v);

        Integer result = bst.delete(999);

        assertNull(result);
        assertEquals(VALUES.length, bst.size());
    }

    @Test
    void deleteV2_recursiveVersion_matchesIterativeBehavior() {
        for (int v : VALUES) bst.insertV2(v);

        bst.deleteV2(10);

        assertFalse(bst.contains(10));
        for (int v : VALUES) {
            if (v != 10) assertTrue(bst.contains(v));
        }
    }

    @Test
    void clear_emptiesTree() {
        for (int v : VALUES) bst.insert(v);

        bst.clear();

        assertEquals(0, bst.size());
        for (int v : VALUES) {
            assertFalse(bst.contains(v));
        }
    }
}
