package com.alexexe19.datastructures.core.graph;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    private Graph<String> graph;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream capturedOut;

    @BeforeEach
    void setUp() {
        graph = new Graph<>();
        capturedOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capturedOut));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void addVertex_thenContainsVertex_isTrue() {
        graph.addVertex("A");

        assertTrue(graph.containsVertex("A"));
        assertFalse(graph.containsVertex("B"));
    }

    @Test
    void addVertex_duplicate_throwsKeyAlreadyExistsException() {
        graph.addVertex("A");

        assertThrows(KeyAlreadyExistsException.class, () -> graph.addVertex("A"));
    }

    @Test
    void addEdge_thenContainsEdge_isTrue() {
        graph.addVertex("A");
        graph.addVertex("B");

        graph.addEdge("A", "B");

        assertTrue(graph.containsEdge("A", "B"));
        // Directed graph - A->B does not imply B->A.
        assertFalse(graph.containsEdge("B", "A"));
    }

    @Test
    void addEdge_missingVertex_throwsNoSuchElementException() {
        graph.addVertex("A");

        assertThrows(NoSuchElementException.class, () -> graph.addEdge("A", "B"));
    }

    @Test
    void removeEdge_existingEdge_removesIt() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");

        graph.removeEdge("A", "B");

        assertFalse(graph.containsEdge("A", "B"));
    }

    @Test
    void removeVertex_alsoRemovesItFromOtherVertices_neighborLists() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addEdge("A", "B");
        graph.addEdge("C", "B");

        graph.removeVertex("B");

        assertFalse(graph.containsVertex("B"));
        assertThrows(NoSuchElementException.class, () -> graph.containsEdge("A", "B"));
        assertThrows(NoSuchElementException.class, () -> graph.containsEdge("C", "B"));
    }

    @Test
    void bfs_onGraphWithFewerNeighborsThanArrayCapacity_doesNotThrow() {
        // Regression test for the DynamicArray generic-array bug: a
        // vertex's neighbor list starts with capacity 10, so any vertex
        // with fewer than 10 neighbors used to make bfs()/dfs() throw a
        // ClassCastException. Every vertex here has 1-2 neighbors - exactly
        // the crashing case.
        graph.addVertex("Iasi");
        graph.addVertex("Bucuresti");
        graph.addVertex("Cluj");
        graph.addVertex("Timisoara");
        graph.addEdge("Iasi", "Bucuresti");
        graph.addEdge("Bucuresti", "Cluj");
        graph.addEdge("Bucuresti", "Timisoara");

        assertDoesNotThrow(() -> graph.bfs("Iasi"));

        String output = capturedOut.toString();
        assertTrue(output.contains("Iasi"));
        assertTrue(output.contains("Bucuresti"));
        assertTrue(output.contains("Cluj"));
        assertTrue(output.contains("Timisoara"));
    }

    @Test
    void dfs_onGraphWithFewerNeighborsThanArrayCapacity_doesNotThrow() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");

        assertDoesNotThrow(() -> graph.dfs("A"));

        String output = capturedOut.toString();
        assertTrue(output.contains("A"));
        assertTrue(output.contains("B"));
        assertTrue(output.contains("C"));
    }

    @Test
    void bfs_onUnknownStartVertex_throwsNoSuchElementException() {
        graph.addVertex("A");

        assertThrows(NoSuchElementException.class, () -> graph.bfs("Z"));
    }
}
