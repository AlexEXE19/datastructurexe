package com.alexexe19.datastructures.core.linear;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ArrayStackTest {

    private ArrayStack<Integer> stack;

    @BeforeEach
    void setUp() {
        stack = new ArrayStack<>();
    }

    @Test
    void push_singleElement_sizeIsOne() {
        stack.push(10);

        assertEquals(1, stack.size());
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
    void peek_afterPush_returnsTopWithoutRemoving() {
        stack.push(42);

        assertEquals(42, stack.peek());
        assertEquals(1, stack.size());
    }

    @Test
    void isEmpty_onNewStack_isTrue() {
        assertTrue(stack.isEmpty());
    }

    @Test
    void isEmpty_afterPushAndPop_isTrueAgain() {
        stack.push(1);
        stack.pop();

        assertTrue(stack.isEmpty());
    }

    @Test
    void pop_onEmptyStack_throwsNoSuchElementException() {
        assertThrows(NoSuchElementException.class, () -> stack.pop());
    }

    @Test
    void peek_onEmptyStack_throwsNoSuchElementException() {
        assertThrows(NoSuchElementException.class, () -> stack.peek());
    }

    @Test
    void push_beyondInitialCapacity_stillWorksAfterResize() {
        int elementsToPush = 250;

        for (int i = 0; i < elementsToPush; i++) {
            stack.push(i);
        }

        assertEquals(elementsToPush, stack.size());

        for (int i = elementsToPush - 1; i >= 0; i--) {
            assertEquals(i, stack.pop());
        }

        assertTrue(stack.isEmpty());
    }

    @Test
    void clear_removesAllElements() {
        stack.push(1);
        stack.push(2);

        stack.clear();

        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }
}
