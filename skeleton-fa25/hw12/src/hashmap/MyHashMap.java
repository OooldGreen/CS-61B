package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author Shiyu
 */
public class MyHashMap<K, V> implements Map61B<K, V> {
    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private int size;
    private int initialCapacity;
    private double loadFactor;
    // You should probably define some more!

    /** Constructors */
    public MyHashMap() {
        this.initialCapacity = 16;
        this.loadFactor = 0.75;

        this.buckets = (Collection<Node>[]) new Collection[initialCapacity];
        this.size = 0;

        for (int i = 0; i < this.initialCapacity; i++) {
            this.buckets[i] = createBucket();
        }
    }

    public MyHashMap(int initialCapacity) {
        this.initialCapacity = initialCapacity;
        this.loadFactor = 0.75;
        this.buckets = (Collection<Node>[]) new Collection[initialCapacity];
        this.size = 0;

        for (int i = 0; i < this.initialCapacity; i++) {
            this.buckets[i] = createBucket();
        }
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        this.initialCapacity = initialCapacity;
        this.loadFactor = loadFactor;
        this.buckets = (Collection<Node>[]) new Collection[initialCapacity];
        this.size = 0;

        for (int i = 0; i < this.initialCapacity; i++) {
            this.buckets[i] = createBucket();
        }
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that this is referring to the hash table bucket itself,
     *  not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        // TODO: Fill in this method.
        return new LinkedList<>();
    }

    private int getMod(K key) {
        return Math.floorMod(key.hashCode(), initialCapacity);
    }

    private void resize() {
        initialCapacity *= 2;
        int index;
        Collection<Node>[] newBuckets =(Collection<Node>[]) new Collection[initialCapacity];

        for (int i = 0; i < initialCapacity; i++) {
            newBuckets[i] = createBucket();
        }

        for (Collection<Node> bucket : buckets) {
            for (Node node : bucket) {
                index = getMod(node.key);
                newBuckets[index].add(node);
            }
        }

        buckets = newBuckets;
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!
    /**
     * Associates the specified value with the specified key in this map.
     * If the map already contains the specified key, replaces the key's mapping
     * with the value specified.
     *
     * @param key
     * @param value
     */
    @Override
    public void put(K key, V value) {
        int index = getMod(key);
        Collection<Node> bucket = buckets[index];

        for (Node node : bucket) {
            if (key.equals(node.key)) {
                node.value = value;
                return;
            }
        }

        Node newNode = new Node(key, value);
        bucket.add(newNode);
        size += 1;

        double factor = (double) size / initialCapacity;
        if (factor >= loadFactor) {
            resize();
        }
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        int index = getMod(key);

        for (Node node : buckets[index]) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }

        return null;
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        int index = getMod(key);

        for (Node node : buckets[index]) {
            if (node.key.equals(key)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        for (Collection<Node> bucket : buckets) {
            bucket.clear();
        }
        size = 0;
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for this lab.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();

        for (Collection<Node> bucket : buckets) {
            for (Node node : bucket) {
                keySet.add(node.key);
            }
        }

        return keySet;
    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public V remove(K key) {
        int index = getMod(key);
        Node target = null;

        for (Node node : buckets[index]) {
            if (node.key.equals(key)) {
                target = node;
                break;
            }
        }

        if (target != null) {
            V value = target.value;
            // 删除逻辑
            buckets[index].remove(target);
            size -= 1;
            return value;
        }

        return null;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
        return null;
    }
}
