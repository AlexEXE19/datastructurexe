package com.alexexe19.datastructures.core.hierarchical;

public class AvlTree<E extends Comparable<E>> {

    private Node<E> root;
    private int size;

    private static class Node<E> {
        E value;
        Node<E> left;
        Node<E> right;
        int height;

        public Node(E value) {
            this.value = value;
            this.height = 1;
        }
    }

    public boolean contains(E value) {
        if (value == null)
            throw new NullPointerException("Cannot look for a null value");

        return contains(this.root, value) != null;
    }

    private Node<E> contains(Node<E> node, E value) {
        if (node == null) return null;

        int cmp = node.value.compareTo(value);

        if (cmp > 0) {
            return contains(node.left, value);

        } else if (cmp < 0) {
            return contains(node.right, value);
        }

        // if it is found return the node
        return node;
    }

    public void insert(E value) {
        if (value == null)
            throw new NullPointerException("Cannot insert a null value");

        if (this.root == null) {
            this.root = new Node<>(value);
            this.size++;
            return;
        }

        this.root = insert(this.root, value);
    }

    private Node<E> insert(Node<E> node, E value) {
        if (node == null) {
            this.size++;
            return new Node<>(value);
        }

        int cmp = node.value.compareTo(value);

        if (cmp > 0)
            node.left = insert(node.left, value);
        else if (cmp < 0)
            node.right = insert(node.right, value);

        updateHeight(node);
        return rebalance(node);

    }

    public E delete(E value) {
        if (value == null)
            throw new NullPointerException("Cannot delete a null value");

        if (this.size == 0) return null;

        int tempSize = this.size;

        this.root = delete(this.root, value);

        if (this.size < tempSize)
            return value;
        else return null;
    }

    private Node<E> delete(Node<E> node, E value) {
        if (node == null) return null;

        int cmp = node.value.compareTo(value);

        if (cmp == 0) {

            // leaf node
            if (node.left == null && node.right == null) {
                this.size--;
                return null;
            }
            // two children
            else if (node.left != null && node.right != null) {
                Node<E> successor = findMin(node.right);
                node.value = successor.value;
                node.right = delete(node.right, successor.value);

                updateHeight(node);
                return rebalance(node);
            }
            // one child
            else {
                this.size--;


                if (node.left == null)
                    return node.right;

                else
                    return node.left;
            }
        }

        if (cmp > 0)
            node.left = delete(node.left, value);
        else if (cmp < 0)
            node.right = delete(node.right, value);
        else return node;

        updateHeight(node);
        return rebalance(node);
    }

    public E findMin() {
        return this.root == null ? null : findMin(this.root).value;
    }

    public E findMax() {
        return this.root == null ? null : findMax(this.root).value;
    }

    private Node<E> findMin(Node<E> node) {
        if (node.left == null) return node;
        return findMin(node.left);
    }

    private Node<E> findMax(Node<E> node) {
        if (node.right == null) return node;
        return findMax(node.right);
    }

    private int height(Node<E> node) {
        return node == null ? 0 : node.height;
    }

    private void updateHeight(Node<E> node) {
        node.height = 1 + Math.max(height(node.left), height(node.right));
    }

    private int balanceFactor(Node<E> node) {
        if (node == null) return 0;
        return height(node.left) - height(node.right);
    }

    private Node<E> rotateLeft(Node<E> node) {
        Node<E> tempNode = node;
        node = node.right;
        tempNode.right = node.left;
        node.left = tempNode;

        updateHeight(node.left);
        updateHeight(node);

        return node;
    }

    private Node<E> rotateRight(Node<E> node) {
        Node<E> tempNode = node;
        node = node.left;
        tempNode.left = node.right;
        node.right = tempNode;

        updateHeight(node.right);
        updateHeight(node);

        return node;
    }

    private Node<E> rebalance(Node<E> node) {
        int balance = balanceFactor(node);
        int balanceChildLeft = balanceFactor(node.left);
        int balanceChildRight = balanceFactor(node.right);

        if (balance > 1 && balanceChildLeft >= 0) {
            return rotateRight(node);
        } else if (balance < -1 && balanceChildRight <= 0) {
            return rotateLeft(node);
        } else if (balance > 1 && balanceChildLeft < 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        } else if (balance < -1 && balanceChildRight > 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    public void clear() {
        this.root = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

}
