package com.alexexe19.datastructures.core.linear;

import java.util.NoSuchElementException;

public class ArrayStack<E> {
    private Object[] elements;
    private int capacity;
    private int size;


    public ArrayStack() {
        this.capacity = 100;
        this.elements = new Object[this.capacity];
    }

    public void push(E value) {
        this.elements[size] = value;
        size++;

        if (this.size == this.capacity) {
            this.resize();
        }
    }

    public E pop() {
        if (size == 0) throw new NoSuchElementException("Cannot pop from an empty stack");

        E valueToReturn = (E) this.elements[this.size - 1];
        size--;
        return valueToReturn;
    }

    public E peek() {
        if (size == 0) throw new NoSuchElementException("Cannot peek from an empty stack");

        return (E) this.elements[this.size - 1];
    }

    private void resize() {
        this.capacity *= 2;

        Object[] tempElements = this.elements;
        this.elements = new Object[this.capacity];

        System.arraycopy(tempElements, 0, this.elements, 0, this.size);
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
    }
}
