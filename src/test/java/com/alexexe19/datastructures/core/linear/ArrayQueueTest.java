package com.alexexe19.datastructures.core.linear;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ArrayQueueTest {

    private ArrayQueue<Integer> queue;

    @BeforeEach
    void setUp() {
        queue = new ArrayQueue<>();
    }

    @Test
    void enqueue_thenDequeue_returnsElementsInFifoOrder() {
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        assertEquals(1, queue.dequeue());
        assertEquals(2, queue.dequeue());
        assertEquals(3, queue.dequeue());
    }

    @Test
    void isEmpty_onNewQueue_isTrue() {
        assertTrue(queue.isEmpty());
    }

    @Test
    void isEmpty_afterEnqueueAndDequeue_isTrueAgain() {
        queue.enqueue(1);
        queue.dequeue();

        assertTrue(queue.isEmpty());
    }

    @Test
    void dequeue_onEmptyQueue_throwsNoSuchElementException() {
        assertThrows(NoSuchElementException.class, () -> queue.dequeue());
    }

    @Test
    void size_reflectsMixOfEnqueueAndDequeueCalls() {
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.dequeue();

        assertEquals(2, queue.size());
    }

    @Test
    void enqueue_beyondInitialCapacity_stillWorksAfterResize() {
        // Initial capacity is 100 - push past it and make sure the
        // wraparound (head/tail indices modulo capacity) survives a resize.
        int elementsToEnqueue = 250;

        for (int i = 0; i < elementsToEnqueue; i++) {
            queue.enqueue(i);
        }

        assertEquals(elementsToEnqueue, queue.size());
        for (int i = 0; i < elementsToEnqueue; i++) {
            assertEquals(i, queue.dequeue());
        }
        assertTrue(queue.isEmpty());
    }

    @Test
    void enqueue_afterWraparound_stillDequeuesInOrder() {
        // Enqueue and dequeue repeatedly without ever exceeding capacity,
        // so head/tail wrap around the circular buffer multiple times -
        // this is the case a plain "append to the end" queue gets wrong if
        // the wraparound arithmetic has a bug.
        for (int i = 0; i < 50; i++) {
            queue.enqueue(i);
            queue.dequeue();
        }

        queue.enqueue(999);
        assertEquals(999, queue.dequeue());
    }

    @Test
    void clear_emptiesQueue() {
        queue.enqueue(1);
        queue.enqueue(2);

        queue.clear();

        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }
}
