package com.alexexe19.datastructures;

import com.alexexe19.datastructures.core.graph.Graph;
import com.alexexe19.datastructures.core.hash.HashMap;
import com.alexexe19.datastructures.core.hierarchical.BST;
import com.alexexe19.datastructures.core.linear.SinglyLinkedList;

/**
 * Small manual smoke test that exercises a few of the data structures.
 * The real correctness checks live in src/test/java - run them with:
 *   mvn test
 */
public class Main {
    public static void main(String[] args) {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.addFirst(2);
        list.addFirst(5);
        list.addFirst(10);
        list.add(1, 22);
        System.out.println("SinglyLinkedList: " + list);

        BST<Integer> bst = new BST<>();
        for (int value : new int[]{8, 3, 10, 1, 6, 14, 4, 7, 13}) {
            bst.insertV2(value);
        }
        System.out.println("BST contains 6: " + bst.contains(6));
        System.out.println("BST contains 42: " + bst.contains(42));

        HashMap<String, Integer> ages = new HashMap<>();
        ages.put("alex", 23);
        ages.put("maria", 25);
        System.out.println("HashMap: " + ages);

        Graph<String> graph = new Graph<>();
        for (String city : new String[]{"Iasi", "Bucuresti", "Cluj", "Timisoara"}) {
            graph.addVertex(city);
        }
        graph.addEdge("Iasi", "Bucuresti");
        graph.addEdge("Bucuresti", "Cluj");
        graph.addEdge("Bucuresti", "Timisoara");
        System.out.println("Graph BFS from Iasi:");
        graph.bfs("Iasi");
    }
}
