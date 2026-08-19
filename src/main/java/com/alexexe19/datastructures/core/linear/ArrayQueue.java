package com.alexexe19.datastructures.core.linear;

import java.util.NoSuchElementException;

public class ArrayQueue<E> {
    private Object[] elements;
    private int capacity;
    private int head;
    private int tail;
    private int size;


    public ArrayQueue() {
        this.capacity = 100;
        this.elements = new Object[this.capacity];
    }

    public void enqueue(E value) {
        this.elements[tail] = value;

        this.tail = (this.tail + 1) % elements.length;
        this.size++;

        if (this.size == this.capacity) {
            this.resize();
        }
    }

    public E dequeue() {
        if (size == 0) throw new NoSuchElementException("Cannot dequeue from an empty queue");

        E valueToReturn = (E) this.elements[this.head];
        this.elements[this.head] = null;

        this.head = (this.head + 1) % this.elements.length;
        this.size--;
        return valueToReturn;

    }

    private void resize() {
        int oldCapacity = this.capacity;
        this.capacity *= 2;

        Object[] tempElements = this.elements;

        this.elements = new Object[this.capacity];

        for (int i = 0; i < this.size; i++) this.elements[i] = tempElements[(this.head + i) % oldCapacity];

        this.head = 0;
        this.tail = this.size;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public void clear() {
        this.elements = new Object[this.capacity];
        this.size = 0;
        this.head = 0;
        this.tail = 0;
    }
}
