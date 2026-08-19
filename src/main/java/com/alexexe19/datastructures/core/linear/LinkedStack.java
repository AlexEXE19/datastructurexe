package com.alexexe19.datastructures.core.linear;

import java.util.NoSuchElementException;

public class LinkedStack<E> {
    private final SinglyLinkedList<E> singlyLinkedList;

    public LinkedStack() {
        this.singlyLinkedList = new SinglyLinkedList<E>();
    }

    public void push(E value) {
        this.singlyLinkedList.addFirst(value);
    }

    public E pop() {
        return this.singlyLinkedList.removeFirst();
    }

    public E peek() {
        // peek() used to call singlyLinkedList.get(0) directly, which throws
        // IndexOutOfBoundsException on an empty stack - inconsistent with
        // pop(), which throws NoSuchElementException in the same situation.
        // Both now report the same exception type for "empty".
        if (this.singlyLinkedList.isEmpty()) throw new NoSuchElementException("Cannot peek from an empty stack");
        return this.singlyLinkedList.get(0);
    }
}
