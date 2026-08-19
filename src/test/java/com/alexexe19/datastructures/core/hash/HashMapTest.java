package com.alexexe19.datastructures.core.hash;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HashMapTest {

    private HashMap<String, Integer> map;

    @BeforeEach
    void setUp() {
        map = new HashMap<>();
    }

    @Test
    void put_thenGet_returnsStoredValue() {
        map.put("alex", 23);

        assertEquals(23, map.get("alex"));
    }

    @Test
    void get_missingKey_returnsNull() {
        assertNull(map.get("missing"));
    }

    @Test
    void put_sameKeyTwice_overwritesAndReturnsPreviousValue() {
        map.put("alex", 23);
        Integer previous = map.put("alex", 24);

        assertEquals(23, previous);
        assertEquals(24, map.get("alex"));
        assertEquals(1, map.getSize());
    }

    @Test
    void put_newKey_returnsNull() {
        Integer result = map.put("alex", 23);

        assertNull(result);
    }

    @Test
    void remove_existingKey_returnsValueAndRemovesEntry() {
        map.put("alex", 23);

        Integer removed = map.remove("alex");

        assertEquals(23, removed);
        assertNull(map.get("alex"));
        assertEquals(0, map.getSize());
    }

    @Test
    void remove_missingKey_returnsNull() {
        assertNull(map.remove("missing"));
    }

    @Test
    void containsKey_andContainsValue_workIndependently() {
        map.put("alex", 23);

        assertTrue(map.containsKey("alex"));
        assertFalse(map.containsKey("maria"));

        assertTrue(map.containsValue(23));
        assertFalse(map.containsValue(999));
    }

    @Test
    void put_nullKey_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> map.put(null, 1));
    }

    @Test
    void put_manyEntries_triggersResizeAndKeepsAllRetrievable() {
        int entryCount = 200;

        for (int i = 0; i < entryCount; i++) {
            map.put("key" + i, i);
        }

        assertEquals(entryCount, map.getSize());
        for (int i = 0; i < entryCount; i++) {
            assertEquals(i, map.get("key" + i));
        }
    }

    @Test
    void keySet_andValues_haveSizeMatchingEntryCount() {
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);

        List<String> keys = map.keySet();
        List<Integer> values = map.values();

        assertEquals(3, keys.size());
        assertEquals(3, values.size());
        assertTrue(keys.containsAll(Arrays.asList("a", "b", "c")));
        assertTrue(values.containsAll(Arrays.asList(1, 2, 3)));
    }

    @Test
    void isEmpty_beforeAndAfterClear() {
        assertTrue(map.isEmpty());

        map.put("a", 1);
        assertFalse(map.isEmpty());

        map.clear();
        assertTrue(map.isEmpty());
        assertEquals(0, map.getSize());
    }
}
