package com.alexexe19.datastructures.core.linear;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class LinkedQueueTest {

    private LinkedQueue<Integer> queue;

    @BeforeEach
    void setUp() {
        queue = new LinkedQueue<>();
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
    void dequeue_onEmptyQueue_throwsNoSuchElementException() {
        // LinkedQueue.dequeue() delegates to SinglyLinkedList.removeFirst(),
        // which throws NoSuchElementException on an empty list.
        assertThrows(NoSuchElementException.class, () -> queue.dequeue());
    }
}
