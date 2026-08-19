package com.alexexe19.datastructures.core.linear;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DynamicArrayTest {

    private DynamicArray<String> array;

    @BeforeEach
    void setUp() {
        array = new DynamicArray<>();
    }

    @Test
    void add_appendsAtEnd() {
        array.add("a");
        array.add("b");

        assertEquals(2, array.size());
        assertEquals("a", array.get(0));
        assertEquals("b", array.get(1));
    }

    @Test
    void add_atIndex_shiftsSubsequentElements() {
        array.add("a");
        array.add("c");

        array.add(1, "b");

        assertEquals("[a, b, c]", array.toString());
    }

    @Test
    void set_replacesValueAtIndex_andReturnsNewValue() {
        array.add("a");

        String returned = array.set(0, "z");

        assertEquals("z", returned);
        assertEquals("z", array.get(0));
    }

    @Test
    void remove_middleIndex_shiftsSubsequentElementsLeft() {
        array.add("a");
        array.add("b");
        array.add("c");

        String removed = array.remove(1);

        assertEquals("b", removed);
        assertEquals("[a, c]", array.toString());
    }

    @Test
    void get_invalidIndex_throwsException() {
        array.add("a");

        assertThrows(RuntimeException.class, () -> array.get(-1));
        assertThrows(RuntimeException.class, () -> array.get(1));
    }

    @Test
    void contains_andIndexOf_existingAndMissingValue() {
        array.add("a");
        array.add("b");

        assertTrue(array.contains("b"));
        assertEquals(1, array.indexOf("b"));

        assertFalse(array.contains("z"));
        assertEquals(-1, array.indexOf("z"));
    }

    @Test
    void add_beyondInitialCapacity_resizesWithoutLosingElements() {
        int elementsToAdd = 35;

        for (int i = 0; i < elementsToAdd; i++) {
            array.add("item" + i);
        }

        assertEquals(elementsToAdd, array.size());
        for (int i = 0; i < elementsToAdd; i++) {
            assertEquals("item" + i, array.get(i));
        }
    }

    @Test
    void toArray_returnsExactlySizeElements_noTrailingNulls() {
        // Regression test: toArray() used to return the raw backing array,
        // whose length is the internal *capacity* (e.g. 10), not the
        // logical *size*. With only 3 elements added, that meant 7 trailing
        // nulls leaked into the result - which is exactly what made
        // Graph.bfs()/dfs() throw when iterating a vertex's neighbor list.
        array.add("a");
        array.add("b");
        array.add("c");

        Object[] result = array.toArray();

        assertEquals(3, result.length);
        for (Object element : result) {
            assertNotNull(element);
        }
    }

    @Test
    void iterator_visitsExactlySizeElementsInOrder() {
        array.add("a");
        array.add("b");
        array.add("c");

        StringBuilder collected = new StringBuilder();
        for (String s : array) {
            collected.append(s);
        }

        assertEquals("abc", collected.toString());
    }

    @Test
    void clear_emptiesArray() {
        array.add("a");
        array.add("b");

        array.clear();

        assertTrue(array.isEmpty());
        assertEquals(0, array.size());
    }
}
