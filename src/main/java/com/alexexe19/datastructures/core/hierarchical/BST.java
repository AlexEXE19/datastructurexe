package com.alexexe19.datastructures.core.hierarchical;

import java.util.Objects;

public class BST<E extends Comparable<E>> {

    private Node<E> root;
    private int size;

    private static class Node<E> {
        E value;
        Node<E> left;
        Node<E> right;

        public Node(E value) {
            this.value = value;
        }
    }

    // Two implementations of insert/delete are kept intentionally: the
    // original iterative version (insert/delete) and a recursive version
    // (insertV2/deleteV2). Both are correct and functionally equivalent -
    // they're kept side by side as a deliberate exercise comparing the two
    // approaches, not leftover/duplicate code.
    public void insert(E value) {
        if (this.root == null) {
            this.root = new Node<>(value);
            this.size++;
            return;
        }

        Node<E> currentNode = this.root;

        while (currentNode != null) {
            if (currentNode.value.compareTo(value) > 0) {
                if (currentNode.left == null) {
                    currentNode.left = new Node<>(value);
                    this.size++;
                    return;
                }
                currentNode = currentNode.left;
            } else if (currentNode.value.compareTo(value) < 0) {
                if (currentNode.right == null) {
                    currentNode.right = new Node<>(value);
                    this.size++;
                    return;
                }
                currentNode = currentNode.right;
            } else {
                // duplicate found, so exit
                return;
            }
        }
    }

    public void insertV2(E value) {
        if (this.root == null) {
            this.root = new Node<>(value);
            this.size++;
            return;
        }

        this.root = insertV2(this.root, value);
    }

    private Node<E> insertV2(Node<E> node, E value) {
        if (node == null) {
            this.size++;
            return new Node<>(value);
        }

        int cmp = node.value.compareTo(value);

        if (cmp > 0)
            node.left = insertV2(node.left, value);
        else if (cmp < 0)
            node.right = insertV2(node.right, value);

        return node;
    }

    public E deleteV2(E value) {
        if (this.size == 0) return null;

        int tempSize = this.size;

        this.root = deleteV2(this.root, value);

        if (this.size < tempSize)
            return value;
        else return null;
    }

    private Node<E> deleteV2(Node<E> node, E value) {
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
                node.right = deleteV2(node.right, successor.value);

                return node;
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
            node.left = deleteV2(node.left, value);
        else if (cmp < 0)
            node.right = deleteV2(node.right, value);

        return node;
    }

    private Node<E> findMin(Node<E> node) {
        if (node.left == null) return node;
        return findMin(node.left);
    }

    public boolean contains(E value) {
        Node<E> currentNode = this.root;
        int cmp;

        while (currentNode != null) {
            cmp = currentNode.value.compareTo(value);

            if (cmp > 0) {
                if (currentNode.left == null) {
                    return false;
                }
                currentNode = currentNode.left;
            } else if (cmp < 0) {
                if (currentNode.right == null) {
                    return false;
                }
                currentNode = currentNode.right;
            } else {
                return true;
            }
        }
        return false;

    }

    public E delete(E value) {
        if (this.size == 0) return null;

        Node<E> currentNode = this.root;
        Node<E> parentNode = null;
        // Current node position relative to its parent
        String currentNodePos = null;

        while (currentNode != null) {
            if (currentNode.value.compareTo(value) > 0) {
                if (currentNode.left == null) {
                    return null;
                }
                parentNode = currentNode;
                currentNode = currentNode.left;
                currentNodePos = "left";
            } else if (currentNode.value.compareTo(value) < 0) {
                if (currentNode.right == null) {
                    return null;
                }
                parentNode = currentNode;
                currentNode = currentNode.right;
                currentNodePos = "right";
            }
            // Node to be deleted is found:
            else {
                // If the node is a leaf
                if (currentNode.left == null && currentNode.right == null) {
                    if (Objects.equals(currentNodePos, "left")) parentNode.left = null;
                    else if (Objects.equals(currentNodePos, "right")) parentNode.right = null;
                        // Case where current node is the root node
                    else this.root = null;

                    this.size--;
                    return value;
                }
                // If the node has two children
                else if (currentNode.left != null && currentNode.right != null) {

                    Node<E> successorNode = currentNode.right;
                    Node<E> successorParentNode = currentNode;

                    while (successorNode.left != null) {
                        successorParentNode = successorNode;
                        successorNode = successorNode.left;
                    }

                    currentNode.value = successorNode.value;

                    if (successorNode == currentNode.right) {
                        successorParentNode.right = successorNode.right;
                    } else {
                        successorParentNode.left = successorNode.right;
                    }
                    this.size--;
                    return value;

                }
                // If the node has exactly one child
                else {

                    if (currentNode.left != null) {
                        if (Objects.equals(currentNodePos, null)) {
                            this.root = currentNode.left;
                        } else if (Objects.equals(currentNodePos, "left")) {
                            parentNode.left = currentNode.left;
                        } else {
                            parentNode.right = currentNode.left;
                        }

                    } else {
                        if (Objects.equals(currentNodePos, null)) {
                            this.root = currentNode.right;
                        } else if (Objects.equals(currentNodePos, "left")) {
                            parentNode.left = currentNode.right;
                        } else {
                            parentNode.right = currentNode.right;
                        }
                    }

                    this.size--;

                    return value;

                }
            }
        }
        return null;
    }


    public int size() {return this.size;}

    public void clear() {
        this.root = null;
        this.size = 0;
    }
}
