package com.alexexe19.datastructures.core.linear;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class SinglyLinkedListTest {

    private SinglyLinkedList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new SinglyLinkedList<>();
    }

    @Test
    void isEmpty_onNewList_isTrue() {
        assertTrue(list.isEmpty());
        assertEquals(0, list.getSize());
    }

    @Test
    void addFirst_multipleElements_maintainsReverseOrder() {
        list.addFirst(1);
        list.addFirst(2);
        list.addFirst(3);

        assertEquals("[3, 2, 1]", list.toString());
    }

    @Test
    void addLast_multipleElements_maintainsInsertionOrder() {
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);

        assertEquals("[1, 2, 3]", list.toString());
    }

    @Test
    void get_firstMiddleAndLastIndex_returnsCorrectValues() {
        list.addLast(10);
        list.addLast(20);
        list.addLast(30);

        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
    }

    @Test
    void get_negativeOrOutOfRangeIndex_throwsIndexOutOfBoundsException() {
        list.addLast(1);

        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));
    }

    @Test
    void removeFirst_onSingleElementList_emptiesTheList() {
        list.addLast(1);

        int removed = list.removeFirst();

        assertEquals(1, removed);
        assertTrue(list.isEmpty());
    }

    @Test
    void removeFirst_onEmptyList_throwsNoSuchElementException() {
        assertThrows(NoSuchElementException.class, () -> list.removeFirst());
    }

    @Test
    void removeLast_onMultiElementList_updatesTailCorrectly() {
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);

        assertEquals(3, list.removeLast());
        list.addLast(99);

        assertEquals("[1, 2, 99]", list.toString());
    }

    @Test
    void removeIndex_middleElement_preservesRemainingOrder() {
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        list.addLast(4);

        int removed = list.removeIndex(1);

        assertEquals(2, removed);
        assertEquals("[1, 3, 4]", list.toString());
    }

    @Test
    void add_atIndexZero_insertsAtHead() {
        list.addLast(1);
        list.addLast(2);

        list.add(0, 99);

        assertEquals("[99, 1, 2]", list.toString());
    }

    @Test
    void contains_existingAndMissingValue_returnsCorrectBoolean() {
        list.addLast(1);
        list.addLast(2);

        assertTrue(list.contains(2));
        assertFalse(list.contains(42));
    }

    @Test
    void clear_resetsListToEmpty() {
        list.addLast(1);
        list.addLast(2);

        list.clear();

        assertTrue(list.isEmpty());
        assertEquals(0, list.getSize());
    }
}
