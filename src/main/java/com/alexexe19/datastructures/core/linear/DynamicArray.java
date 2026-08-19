package com.alexexe19.datastructures.core.linear;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class DynamicArray<E> implements Iterable<E> {
    private E[] array;
    private int size;
    private int capacity;

    public DynamicArray() {
        this.capacity = 10;
        this.array = (E[]) new Object[capacity];
    }

    private void resize() {
        this.capacity *= 2;
        E[] tempObj = this.array;
        this.array = (E[]) new Object[capacity];
        System.arraycopy(tempObj, 0, this.array, 0, this.size);
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > this.size) throw new IllegalArgumentException("Index " + index + " out of bounds for size " + size);
    }
    private void checkElementIndex(int index) {
        if (index < 0 || index >= this.size) throw new IllegalArgumentException("Index " + index + " out of bounds for size " + size);
    }

    public E add(E value) {
        if (this.size == this.capacity) resize();

        this.array[this.size] = value;
        this.size++;

        return value;
    }

    public E add(int index, E value) {
        checkPositionIndex(index);

        if (this.size == this.capacity) resize();

        System.arraycopy(this.array, index, this.array, index + 1, this.size - index);

        this.array[index] = value;
        this.size++;


        return value;
    }

    public E get(int index) {
        checkElementIndex(index);

        return this.array[index];

    }

    public E set(int index, E value) {
        checkElementIndex(index);

        this.array[index] = value;
        return value;
    }

    public E remove(int index) {
        checkElementIndex(index);

        E valueToReturn = this.array[index];

        System.arraycopy(this.array, index + 1, this.array, index, this.size - index - 1);
        this.array[this.size - 1] = null;

        this.size--;

        return valueToReturn;
    }

    public boolean contains(E value) {
        for (int i = 0; i < this.size; i++) if (Objects.equals(this.array[i], value)) return true;
        return false;
    }

    public int indexOf(E value) {
        for (int i = 0; i < this.size; i++) if (Objects.equals(this.array[i], value)) return i;

        return -1;
    }

    public void clear() {
        this.array = (E[]) new Object[this.capacity];
        this.size = 0;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[");

        for (int i = 0; i < this.size; i++) {

            stringBuilder.append(this.array[i]);
            if (i != this.size - 1) stringBuilder.append(", ");
        }

        stringBuilder.append("]");

        return stringBuilder.toString();
    }

    // Returns a copy trimmed to `size`, as a plain Object[] rather than E[].
    // Two things worth knowing:
    //   1. It's trimmed to size, not capacity - the backing array can be
    //      larger than size, with unused/null trailing slots, so returning
    //      it directly would leak nulls to anyone iterating the result.
    //   2. It's declared to return Object[], not E[], on purpose. Because of
    //      Java's generic type erasure, `this.array` is only ever actually
    //      an Object[] at runtime, no matter what E is (that's what the
    //      `(E[]) new Object[capacity]` cast in the constructor really
    //      means - it's a compile-time-only promise). If this method
    //      claimed to return E[], the compiler would insert an implicit
    //      cast at every call site (e.g. `String[] a = list.toArray();`),
    //      and that cast throws ClassCastException at runtime, because the
    //      array genuinely isn't a String[] - it just impersonates one at
    //      compile time. Returning Object[] is the honest version. For
    //      typed iteration, use the Iterable<E> interface below instead
    //      (`for (E e : myDynamicArray)`), which reads one element at a
    //      time and never hits this problem.
    public Object[] toArray() {
        return Arrays.copyOf(this.array, this.size);
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public E next() {
                if (!hasNext()) throw new NoSuchElementException();
                return array[index++];
            }
        };
    }
}
