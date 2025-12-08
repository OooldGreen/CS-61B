import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>  {
    private class BSTNode {
        K key;
        V value;
        BSTNode left;
        BSTNode right;

        BSTNode (K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private BSTNode root;
    int size = 0;

    private BSTNode put(BSTNode root, K key, V value) {
        if(root == null ){
            size += 1;
            return new BSTNode(key, value);
        }

        if (key.compareTo(root.key) < 0) {
            root.left = put(root.left, key, value);
        } else if (key.compareTo(root.key) > 0) {
            root.right = put(root.right, key, value);
        } else if (root.key.compareTo(key) == 0) {
            root.value = value;
        }
        return root;
    }

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
        root = put(root, key, value);
    }

    private BSTNode findHelper(BSTNode node, K key) {
        if (node != null && node.key != null) {
            if (key.compareTo(node.key) < 0 ) {
                return findHelper(node.left, key);
            } else if (key.compareTo(node.key) > 0) {
                return findHelper(node.right, key);
            } else if (key.compareTo(node.key) == 0) {
                return node;
            }
        }
        return null;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        BSTNode node = findHelper(root, key);
        if (node == null) {
            return null;
        }
        return node.value;
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        BSTNode node = findHelper(root, key);
        return node != null;
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return size;
    }

    private void clearHelper(BSTNode node) {
        if (node != null) {
            node.value = null;
            node.key = null;
            size -= 1;
            clearHelper(node.left);
            clearHelper(node.right);
        }
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        clearHelper(root);
    }

    private Set<K> keySetHelper(Set<K> set, BSTNode node) {
        if (node == null) {
            return set;
        }
        keySetHelper(set, node.left);
        set.add(node.key);
        keySetHelper(set, node.right);
        return set;
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        Set<K> newSet = new TreeSet<>();

        return keySetHelper(newSet, root);
    }

    /**
     * 1. 对比第一个node的key和目标key
     * 2. 如果目标key大，往右边找
     * 3. 如果目标key小，往左边找
     * 4. 如果相等，实行删除程序：
     *          1. 如果没有孩子节点，返回 null，
     *          2. 如果一个孩子节点，返回这个孩子节点
     *          3. 如果两个孩子节点， 找到右子树最小的节点，复制并删除这个节点
     * */
    private V removeValue = null;

    private BSTNode remove(BSTNode node, K key) {
        if (node == null) {
            return null;
        }

        if (key.compareTo(node.key) > 0) {
            node.right = remove(node.right, key);
        } else if (key.compareTo(node.key) < 0) {
            node.left = remove(node.left, key);
        } else {
            removeValue = node.value;
            size -= 1;
            node = removeNode(node);
        }

        return node;
    }

    private BSTNode removeNode(BSTNode node) {
        if (node.left == null && node.right == null) {
            return null;
        }
        if (node.right == null ) {
            return node.left;
        }
        if (node.left == null) {
            return node.right;
        }

        // 有两个孩子节点
        BSTNode min = findMin(node.right);
        node.key = min.key;
        node.value = min.value;
        node.right = remove(node.right, min.key);
        return node;
    }

    private BSTNode findMin(BSTNode node) {
        if (node.left == null) {
            return node;
        }
        return findMin(node.left);
    }
    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public V remove(K key) {
        removeValue = null;
        root = remove(root, key);
        return removeValue;
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
