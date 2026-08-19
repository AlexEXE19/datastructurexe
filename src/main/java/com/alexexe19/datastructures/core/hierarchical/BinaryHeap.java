package com.alexexe19.datastructures.core.hierarchical;

public class BinaryHeap<E extends Comparable<E>> {
    private Object[] elements;
//    private int capacity;
    private int size;

    public BinaryHeap() {
//        this.capacity = 10;
        this.elements = new Object[10];
    }

    private void heapifyUp() {
        int newElementIndex = this.size - 1;
        int parentIndex;


        parentIndex = (newElementIndex - 1) / 2;

        E currentNode = (E) this.elements[newElementIndex];
        E currentParentNode = (E) this.elements[parentIndex];
        Object temp;

        while (true) {
            if (currentNode.compareTo(currentParentNode) > 0) {

                temp = this.elements[newElementIndex];
                this.elements[newElementIndex] = this.elements[parentIndex];
                this.elements[parentIndex] = temp;

                newElementIndex = parentIndex;
                if (newElementIndex == 0) break;

                currentNode = (E) this.elements[newElementIndex];
                parentIndex = (newElementIndex - 1) / 2;
                currentParentNode = (E) this.elements[parentIndex];

            } else {
                break;
            }
        }
    }

    private void heapifyDown() {
        int currentIndex = 0;
        int leftChildIndex;
        int rightChildIndex;

        int currentChildToCompareIndex;

        E currentNode;
        E currentChildLeftNode;
        E currentChildRightNode;
        E currentChildToCompareNode;

        Object temp;

        while (true) {
            leftChildIndex = 2 * currentIndex + 1;
            rightChildIndex = 2 * currentIndex + 2;

            if (leftChildIndex >= this.size) break;

            currentNode = (E) this.elements[currentIndex];

            if (rightChildIndex >= this.size) currentChildToCompareIndex = leftChildIndex;

            else {
                currentChildLeftNode = (E) this.elements[leftChildIndex];
                currentChildRightNode = (E) this.elements[rightChildIndex];

                if (currentChildLeftNode.compareTo(currentChildRightNode) > 0) {
                    currentChildToCompareIndex = leftChildIndex;

                } else {
                    currentChildToCompareIndex = rightChildIndex;
                }
            }

            currentChildToCompareNode = (E) this.elements[currentChildToCompareIndex];

            if (currentNode.compareTo(currentChildToCompareNode) < 0) {
                temp = this.elements[currentChildToCompareIndex];
                this.elements[currentChildToCompareIndex] = this.elements[currentIndex];
                this.elements[currentIndex] = temp;

                currentIndex = currentChildToCompareIndex;

            } else break;

        }
    }

    private void resize() {
        Object[] temp = this.elements;

        this.elements = new Object[this.elements.length * 2];
        System.arraycopy(temp, 0, this.elements, 0, this.size);
    }

    public void add(E value) {
        if (value == null) throw new NullPointerException("Cannot add a null value");
        if (this.size == this.elements.length) this.resize();

        this.elements[size] = value;
        this.size++;

        this.heapifyUp();

    }

    public E peek() {
        if (this.size == 0) return null;

        return (E) this.elements[0];
    }

    public E remove() {
        if (this.size == 0) return null;

        E valueToReturn = (E) this.elements[0];

        this.elements[0] = this.elements[this.size - 1];
        this.elements[this.size - 1] = null;

        this.size--;

        this.heapifyDown();

        return valueToReturn;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public void clear() {
        this.elements = new Object[this.elements.length];
        this.size = 0;
    }
}
