package com.alexexe19.datastructures.core.linear;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class DoublyLinkedListTest {

    private DoublyLinkedList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new DoublyLinkedList<>();
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
    void get_indexInFirstHalf_returnsCorrectValue() {
        for (int i = 0; i < 10; i++) list.addLast(i);

        // index 2 is in the first half (< size/2 == 5) - get() should
        // traverse from the head.
        assertEquals(2, list.get(2));
    }

    @Test
    void get_indexInSecondHalf_returnsCorrectValue() {
        for (int i = 0; i < 10; i++) list.addLast(i);

        // index 8 is in the second half (>= size/2 == 5) - get() should
        // traverse from the tail instead. If that branch had an off-by-one,
        // this is the case that would catch it.
        assertEquals(8, list.get(8));
    }

    @Test
    void removeIndex_inFirstHalf_preservesRemainingOrder() {
        for (int i = 0; i < 10; i++) list.addLast(i);

        int removed = list.removeIndex(2);

        assertEquals(2, removed);
        assertEquals(9, list.getSize());
        assertEquals(3, list.get(2)); // what used to be at index 3 shifted down
    }

    @Test
    void removeIndex_inSecondHalf_preservesRemainingOrder() {
        for (int i = 0; i < 10; i++) list.addLast(i);

        int removed = list.removeIndex(8);

        assertEquals(8, removed);
        assertEquals(9, list.getSize());
        assertEquals(9, list.get(8)); // what used to be at index 9 shifted down
    }

    @Test
    void removeFirst_onEmptyList_throwsNoSuchElementException() {
        assertThrows(NoSuchElementException.class, () -> list.removeFirst());
    }

    @Test
    void removeLast_onEmptyList_throwsNoSuchElementException() {
        assertThrows(NoSuchElementException.class, () -> list.removeLast());
    }

    @Test
    void get_invalidIndex_throwsIndexOutOfBoundsException() {
        list.addLast(1);

        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(5));
    }

    @Test
    void contains_existingAndMissingValue() {
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
