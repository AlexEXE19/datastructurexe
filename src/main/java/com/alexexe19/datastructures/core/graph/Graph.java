package com.alexexe19.datastructures.core.graph;

import com.alexexe19.datastructures.core.hash.HashMap;
import com.alexexe19.datastructures.core.hash.HashSet;
import com.alexexe19.datastructures.core.linear.ArrayQueue;
import com.alexexe19.datastructures.core.linear.ArrayStack;
import com.alexexe19.datastructures.core.linear.DynamicArray;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.NoSuchElementException;
import java.util.Objects;


public class Graph<E> {
    private HashMap<E, Vertex<E>> vertices;

    private static class Vertex<E> {
        E value;
        DynamicArray<Vertex<E>> neighbors;

        public Vertex(E value) {
            this.value = value;
            this.neighbors = new DynamicArray<>();
        }
    }

    public Graph() {
        this.vertices = new HashMap<>();
    }

    public void addVertex(E vertexValue) {
        if (vertexValue == null) throw new IllegalArgumentException("Vertex value must not be null");

        if (this.vertices.containsKey(vertexValue))
            throw new KeyAlreadyExistsException("Vertex " + vertexValue + " already exists");

        Vertex<E> newVertex = new Vertex<>(vertexValue);
        this.vertices.put(vertexValue, newVertex);
    }

    public void addEdge(E from, E to) {
        if (from == null || to == null) throw new IllegalArgumentException("from/to must not be null");

        Vertex<E> fromVertex = this.vertices.get(from);
        Vertex<E> toVertex = this.vertices.get(to);

        if (fromVertex == null || toVertex == null)
            throw new NoSuchElementException("Both vertices must exist before adding an edge");

        if (!fromVertex.neighbors.contains(toVertex))
            fromVertex.neighbors.add(toVertex);
    }

    public void removeEdge(E from, E to) {
        if (from == null || to == null) throw new IllegalArgumentException("from/to must not be null");

        Vertex<E> fromVertex = this.vertices.get(from);
        Vertex<E> toVertex = this.vertices.get(to);

        if (fromVertex == null || toVertex == null)
            throw new NoSuchElementException("Both vertices must exist before removing an edge");

        int vertexIndex = fromVertex.neighbors.indexOf(toVertex);
        if (vertexIndex == -1) throw new NoSuchElementException("No edge from " + from + " to " + to);

        fromVertex.neighbors.remove(vertexIndex);
    }

    public void removeVertex(E vertexValue) {
        if (vertexValue == null) throw new IllegalArgumentException("Vertex value must not be null");

        Vertex<E> vertexToRemove = this.vertices.remove(vertexValue);
        if (vertexToRemove == null) throw new NoSuchElementException("No such vertex: " + vertexValue);

        cleanRemovedVertex(vertexToRemove);
    }

    public void bfs(E start) {
        HashSet<E> visited = new HashSet<>();
        ArrayQueue<Vertex<E>> queue = new ArrayQueue<>();

        Vertex<E> currentVertex = vertices.get(start);

        if (currentVertex == null)
            throw new NoSuchElementException("No such vertex: " + start);

        queue.enqueue(currentVertex);
        visited.add(currentVertex.value);

        while (!queue.isEmpty()) {
            currentVertex = queue.dequeue();
            System.out.println(currentVertex.value);

            // Iterate the DynamicArray directly via its Iterable<E>
            // implementation rather than calling .toArray() - see the
            // comment on DynamicArray.toArray() for why that used to be a
            // ClassCastException waiting to happen.
            for (Vertex<E> neigh : currentVertex.neighbors) {
                if (!visited.contains(neigh.value)) {
                    visited.add(neigh.value);
                    queue.enqueue(neigh);
                }
            }
        }

    }

    public void dfs(E start) {
        HashSet<E> visited = new HashSet<>();
        ArrayStack<Vertex<E>> stack = new ArrayStack<>();

        Vertex<E> currentVertex = vertices.get(start);

        if (currentVertex == null)
            throw new NoSuchElementException("Element doesn't exist in the graph");

        stack.push(currentVertex);
        visited.add(currentVertex.value);

        while (!stack.isEmpty()) {
            currentVertex = stack.pop();
            System.out.println(currentVertex.value);

            for (Vertex<E> neigh : currentVertex.neighbors) {
                if (!visited.contains(neigh.value)) {
                    visited.add(neigh.value);
                    stack.push(neigh);
                }
            }
        }
    }

    public boolean containsVertex(E value) {
        if (value == null) throw new IllegalArgumentException("Cannot look for a null value");

        return this.vertices.get(value) != null;
    }

    public boolean containsEdge(E from, E to) {
        if (from == null || to == null) throw new IllegalArgumentException("Cannot look for null values");

        Vertex<E> fromVertex = this.vertices.get(from);
        Vertex<E> toVertex = this.vertices.get(to);

        if (fromVertex == null || toVertex == null) throw new NoSuchElementException("Element doesn't exist in the graph");

        return fromVertex.neighbors.contains(toVertex);
    }

    private void cleanRemovedVertex(Vertex<E> vertexToRemove) {
        for (Vertex<E> vert : this.vertices.values()) {
            for (Vertex<E> neigh : vert.neighbors) {
                if (Objects.equals(neigh.value, vertexToRemove.value)) {
                    int index = vert.neighbors.indexOf(neigh);
                    vert.neighbors.remove(index);
                }
            }
        }
    }
}
