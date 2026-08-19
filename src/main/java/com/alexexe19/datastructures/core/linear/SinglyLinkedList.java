package com.alexexe19.datastructures.core.linear;

import java.util.NoSuchElementException;

public class SinglyLinkedList<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    private static class Node<E> {
        E value;
        Node<E> next;

        public Node(E value) {
            this.value = value;
        }
    }

    public void addFirst(E value) {
        if (size == 0) {
            this.head = new Node<E>(value);
            this.tail = this.head;
        } else {
            Node<E> newNode = new Node<E>(value);
            newNode.next = this.head;
            this.head = newNode;
        }
        size++;
    }

    public void addLast(E value) {
        if (size == 0) {
            this.tail = new Node<E>(value);
            this.head = this.tail;
        } else {
            Node<E> newNode = new Node<E>(value);
            this.tail.next = newNode;
            this.tail = newNode;
        }
        size++;
    }

    public E removeFirst() {
        E valueToReturn;

        if (size == 0) {
            throw new NoSuchElementException("Cannot remove from an empty list");
        } else if (size == 1) {
            valueToReturn = this.head.value;
            this.head = null;
            this.tail = null;
        } else {
            valueToReturn = this.head.value;
            this.head = this.head.next;
        }
        size--;
        return valueToReturn;
    }

    public E removeLast() {
        E valueToReturn;

        if (size == 0) {
            throw new NoSuchElementException("Cannot remove from an empty list");

        } else if (size == 1) {
            valueToReturn = this.head.value;
            this.head = null;
            this.tail = null;
        } else {
            valueToReturn = this.tail.value;

            Node<E> currentNode = this.head;

            while (currentNode.next != tail) {
                currentNode = currentNode.next;
            }

            this.tail = currentNode;
            this.tail.next = null;
        }
        size--;
        return valueToReturn;
    }

    public E removeIndex(int index) {
        E valueToReturn;

        if (index + 1 > size || index < 0) throw new IndexOutOfBoundsException("Index " + index + " out of bounds for size " + size);

        if (index == 0) return this.removeFirst();
        if (index == size - 1) return this.removeLast();

        Node<E> currentNode = this.head;
        Node<E> previousNode = null;

        for (int i = 1; i <= index; i++) {
            previousNode = currentNode;
            currentNode = currentNode.next;
        }

        valueToReturn = currentNode.value;

        previousNode.next = currentNode.next;
        size--;

        return valueToReturn;
    }

    public boolean contains(E value) {
        Node<E> currentNode = this.head;

        for (int i = 0; i < size; i++) {
            if (java.util.Objects.equals(currentNode.value, value)) return true;
            currentNode = currentNode.next;
        }

        return false;
    }

    public void clear() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');

        if (size == 1) stringBuilder.append(this.head.value);
        else if (size > 1) {

            Node<E> currentNode = this.head;

            while (currentNode != tail) {
                stringBuilder.append(currentNode.value).append(", ");
                currentNode = currentNode.next;
            }

            stringBuilder.append(this.tail.value);
        }
        stringBuilder.append(']');

        return stringBuilder.toString();

    }

    public E get(int index) {
        if (index + 1 > size || index < 0) throw new IndexOutOfBoundsException("Index " + index + " out of bounds for size " + size);

        if (index == 0) return this.head.value;
        if (index == size - 1) return this.tail.value;

        Node<E> currentNode = this.head;

        for (int i = 1; i <= index; i++) {
            currentNode = currentNode.next;
        }

        return currentNode.value;
    }

    public void add(int index, E value) {
        if (index > size || index < 0) throw new IndexOutOfBoundsException("Index " + index + " out of bounds for size " + size);

        if (index == 0) {
            this.addFirst(value);
            return;
        }
        if (index == size) {
            this.addLast(value);
            return;
        }

        Node<E> currentNode = this.head;
        Node<E> previousNode = null;

        for (int i = 1; i <= index; i++) {
            previousNode = currentNode;
            currentNode = currentNode.next;
        }

        Node<E> newNode = new Node<E>(value);
        previousNode.next = newNode;
        newNode.next = currentNode;

        size++;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

}
