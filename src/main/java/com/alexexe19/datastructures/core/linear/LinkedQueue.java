package com.alexexe19.datastructures.core.linear;

public class LinkedQueue<E> {
    private final SinglyLinkedList<E> singlyLinkedList;

    public LinkedQueue() {
        this.singlyLinkedList = new SinglyLinkedList<E>();
    }

    public void enqueue(E value) {
        this.singlyLinkedList.addLast(value);
    }

    public E dequeue() {
        return this.singlyLinkedList.removeFirst();
    }
}
