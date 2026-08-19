package com.alexexe19.datastructures.core.linear;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class LinkedStackTest {

    private LinkedStack<Integer> stack;

    @BeforeEach
    void setUp() {
        stack = new LinkedStack<>();
    }

    @Test
    void push_thenPop_returnsLastPushedElement() {
        stack.push(1);
        stack.push(2);
        stack.push(3);

        assertEquals(3, stack.pop());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
    }

    @Test
    void peek_doesNotRemoveElement() {
        stack.push(1);
        stack.push(2);

        assertEquals(2, stack.peek());
        assertEquals(2, stack.peek()); // calling it twice must not consume it
    }

    @Test
    void pop_onEmptyStack_throwsNoSuchElementException() {
        assertThrows(NoSuchElementException.class, () -> stack.pop());
    }

    @Test
    void peek_onEmptyStack_throwsNoSuchElementException() {
        // Regression test: peek() used to fall through to the underlying
        // SinglyLinkedList's get(0), which throws IndexOutOfBoundsException
        // on an empty list - a different exception type than pop() throws
        // in the exact same "it's empty" situation. Both now throw
        // NoSuchElementException.
        assertThrows(NoSuchElementException.class, () -> stack.peek());
    }
}
