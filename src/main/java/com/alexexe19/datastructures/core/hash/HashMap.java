package com.alexexe19.datastructures.core.hash;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HashMap<K, V> {
    private Entry<K, V>[] buckets;
    private int size;
    private int capacity = 16;
    private final double loadFactor = 0.75;

    private static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public HashMap() {
        this.buckets = new Entry[this.capacity];
    }

    private int indexFor(K key) {
        int rawHash = key.hashCode();
        int spread = rawHash ^ (rawHash >>> 16);

        return spread & (buckets.length - 1);
    }


    private void resize() {
        Entry<K, V>[] oldBuckets = buckets;

        this.capacity *= 2;
        this.buckets = new Entry[capacity];

        for (Entry<K, V> bucket : oldBuckets) {
            while (bucket != null) {
                int newIndex = this.indexFor(bucket.key);

                Entry<K, V> nextInOldChain = bucket.next;
                bucket.next = this.buckets[newIndex];
                this.buckets[newIndex] = bucket;
                bucket = nextInOldChain;
            }
        }
    }

    private V checkAndOverwriteExistingKey(Entry<K, V> existingEntry, Entry<K, V> newEntry) {
        Entry<K, V> currentEntry = existingEntry;
        Entry<K, V> lastEntry = null;

        while (currentEntry != null) {
            if (currentEntry.key.equals(newEntry.key)) {

                V valueToReturn = currentEntry.value;
                currentEntry.value = newEntry.value;

                return valueToReturn;
            }
            if (currentEntry.next == null) lastEntry = currentEntry;
            currentEntry = currentEntry.next;
        }

        if (lastEntry != null)
            lastEntry.next = newEntry;

        this.size++;

        return null;
    }

    public V put(K key, V value) {
        if (key == null) throw new NullPointerException("Cannot put with a null key");

        int index = indexFor(key);
        Entry<K, V> existingEntry = this.buckets[index];
        Entry<K, V> newEntry = new Entry<>(key, value);

        V valueToReturn = null;

        if (existingEntry == null) {
            this.buckets[index] = newEntry;
            this.size++;
        } else {
            valueToReturn = checkAndOverwriteExistingKey(existingEntry, newEntry);
        }

        if (this.size > this.capacity * this.loadFactor) resize();

        return valueToReturn;

    }

    public V get(K key) {
        if (key == null) throw new NullPointerException("Cannot get a null key");

        int index = indexFor(key);
        Entry<K, V> currentEntry = this.buckets[index];

        while (currentEntry != null) {
            if (currentEntry.key.equals(key)) {
                return currentEntry.value;
            }
            currentEntry = currentEntry.next;
        }

        return null;
    }

    public V remove(K key) {
        if (key == null) throw new NullPointerException("Cannot remove with a null key");

        int index = indexFor(key);

        Entry<K, V> currentEntry = this.buckets[index];
        Entry<K, V> previousEntry = null;

        while (currentEntry != null) {
            if (currentEntry.key.equals(key)) {
                if (previousEntry == null) {
                    this.buckets[index] = currentEntry.next;
                } else {
                    previousEntry.next = currentEntry.next;
                }

                size--;

                return currentEntry.value;
            }

            previousEntry = currentEntry;
            currentEntry = currentEntry.next;
        }

        return null;
    }

    public boolean containsValue(V value) {
        for (Entry<K, V> bucket : this.buckets) {
            while (bucket != null) {
                if (Objects.equals(bucket.value, value)) return true;
                bucket = bucket.next;
            }
        }
        return false;
    }

    public boolean containsKey(K key) {
        if (key == null) throw new NullPointerException("Cannot look for a null key");

        int index = indexFor(key);
        Entry<K, V> currentEntry = this.buckets[index];

        while (currentEntry != null) {
            if (Objects.equals(currentEntry.key, key)) return true;
            currentEntry = currentEntry.next;
        }

        return false;
    }

    public int getSize() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public void clear() {
        this.buckets = new Entry[this.capacity];
        this.size = 0;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");
        boolean isFirst = true;

        for (Entry<K, V> bucket : this.buckets) {

            while (bucket != null) {
                if (!isFirst) {
                    stringBuilder.append(", ");
                }

                stringBuilder.append(bucket.key).append("=").append(bucket.value);
                isFirst = false;
                bucket = bucket.next;
            }
        }
        stringBuilder.append("}");

        return stringBuilder.toString();
    }

    // Returns a List<K>, not a K[]. A generic array (K[]) can't be created
    // safely in Java - the JVM erases K to Object at runtime, so the only
    // way to actually build one is an unchecked cast like
    // `(K[]) new Object[size]`. That "compiles", but the object is still
    // really an Object[] underneath, and the very first time a caller
    // assigns the result to a concretely-typed variable (e.g.
    // `String[] keys = map.keySet();`) the compiler's implicit cast throws
    // ClassCastException at runtime. Returning a List sidesteps the whole
    // problem - same reasoning as DynamicArray.toArray().
    public List<K> keySet() {
        List<K> keySet = new ArrayList<>(this.size);

        for (Entry<K, V> bucket : this.buckets) {
            while (bucket != null) {
                keySet.add(bucket.key);
                bucket = bucket.next;
            }
        }

        return keySet;
    }

    public List<V> values() {
        List<V> values = new ArrayList<>(this.size);

        for (Entry<K, V> bucket : this.buckets) {
            while (bucket != null) {
                values.add(bucket.value);
                bucket = bucket.next;
            }
        }

        return values;
    }

}
