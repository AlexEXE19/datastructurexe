package com.alexexe19.datastructures.core.hash;

public class HashSet<E> {

    private static final Object PRESENT = new Object();

    private final HashMap<E, Object> map = new HashMap<>();

    public boolean add(E value) {
        return map.put(value, PRESENT) == null;
    }

    public boolean contains(E value) {
        return map.containsKey(value);
    }

    public boolean remove(E value) {
        return map.remove(value) != null;
    }

    public int size() {
        return map.getSize();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public void clear() {
        map.clear();
    }
}
