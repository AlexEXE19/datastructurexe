package com.alexexe19.datastructures.core.hierarchical;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BinaryHeapTest {

    private BinaryHeap<Integer> heap;

    @BeforeEach
    void setUp() {
        heap = new BinaryHeap<>();
    }

    @Test
    void peek_onEmptyHeap_returnsNull() {
        assertNull(heap.peek());
    }

    @Test
    void remove_onEmptyHeap_returnsNull() {
        assertNull(heap.remove());
    }

    @Test
    void add_singleElement_isThePeek() {
        heap.add(5);

        assertEquals(5, heap.peek());
        assertEquals(1, heap.size());
    }

    @Test
    void add_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> heap.add(null));
    }

    @Test
    void peek_afterAdd_doesNotRemoveElement() {
        heap.add(1);
        heap.add(2);

        heap.peek();

        assertEquals(2, heap.size());
    }

    @Test
    void add_thenRemoveRepeatedly_returnsElementsInDescendingOrder() {
        // Max-heap: repeated remove() calls should hand back elements in
        // strictly non-increasing order - that IS the heap property.
        int[] values = {5, 1, 9, 3, 7, 2, 8, 4, 6, 0};
        for (int v : values) heap.add(v);

        Integer previous = null;
        for (int i = 0; i < values.length; i++) {
            Integer current = heap.remove();
            assertNotNull(current);
            if (previous != null) {
                assertTrue(current <= previous,
                        "heap order violated: " + current + " came after " + previous);
            }
            previous = current;
        }

        assertTrue(heap.isEmpty());
    }

    @Test
    void add_beyondInitialCapacity_resizesAndKeepsHeapProperty() {
        for (int i = 0; i < 50; i++) {
            heap.add(i);
        }

        assertEquals(50, heap.size());
        assertEquals(49, heap.peek());
    }

    @Test
    void clear_emptiesHeap() {
        heap.add(1);
        heap.add(2);

        heap.clear();

        assertTrue(heap.isEmpty());
        assertEquals(0, heap.size());
        assertNull(heap.peek());
    }
}
