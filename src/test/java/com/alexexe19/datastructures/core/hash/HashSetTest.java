package com.alexexe19.datastructures.core.hash;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashSetTest {

    private HashSet<String> set;

    @BeforeEach
    void setUp() {
        set = new HashSet<>();
    }

    @Test
    void add_newValue_returnsTrue() {
        assertTrue(set.add("a"));
    }

    @Test
    void add_duplicateValue_returnsFalseAndDoesNotGrowSize() {
        set.add("a");

        boolean addedAgain = set.add("a");

        assertFalse(addedAgain);
        assertEquals(1, set.size());
    }

    @Test
    void contains_presentAndAbsentValue() {
        set.add("a");

        assertTrue(set.contains("a"));
        assertFalse(set.contains("z"));
    }

    @Test
    void remove_existingValue_returnsTrueAndRemovesIt() {
        set.add("a");

        boolean removed = set.remove("a");

        assertTrue(removed);
        assertFalse(set.contains("a"));
    }

    @Test
    void remove_missingValue_returnsFalse() {
        assertFalse(set.remove("never-added"));
    }

    @Test
    void isEmpty_beforeAndAfterClear() {
        assertTrue(set.isEmpty());

        set.add("a");
        assertFalse(set.isEmpty());

        set.clear();
        assertTrue(set.isEmpty());
    }
}
