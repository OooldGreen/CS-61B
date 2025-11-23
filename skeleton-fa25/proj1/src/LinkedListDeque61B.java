import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {
    private class Node {
        public T item;
        public Node prev;
        public Node next;

        Node (T i, Node p, Node n) {
            item = i;
            prev = p;
            next = n;
        }
    }

    int size;
    Node sentF;
    Node sentL;

    public LinkedListDeque61B() {
        size = 0;
        sentF = new Node(null, null, null);
        sentL = new Node(null, null, null);
        sentF.next = sentL;
        sentL.prev = sentF;
    }

    /**
     * Add {@code x} to the front of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addFirst(T x) {
        Node oldNode = sentF.next;
        Node newNode = new Node(x, sentF, sentF.next);
        sentF.next = newNode;
        oldNode.prev = newNode;
        size = size + 1;
    }

    /**
     * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addLast(T x) {
        Node oldNode = sentL.prev;
        Node newNode = new Node(x, sentL.prev, sentF);
        sentL.prev = newNode;
        oldNode.next = newNode;
        size = size + 1;
    }

    /**
     * Returns a List copy of the deque. Does not alter the deque.
     *
     * @return a new list copy of the deque.
     */
    @Override
    public List<T> toList() {
        if (size == 0) {
            return new ArrayList<>();
        }

        List<T> returnList = new ArrayList<>();
        Node p = sentF.next;

        for(int i = 0; i < size; i++) {
            returnList.add(p.item);
            p = p.next;
        }

        return returnList;
    }

    /**
     * Returns if the deque is empty. Does not alter the deque.
     *
     * @return {@code true} if the deque has no elements, {@code false} otherwise.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the size of the deque. Does not alter the deque.
     *
     * @return the number of items in the deque.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Remove and return the element at the front of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeFirst() {
        if (!isEmpty()) {
            Node d = sentF.next;
            sentF.next = sentF.next.next;
            sentF.next.prev = sentF;
            size = size - 1;

            return d.item;
        }

        return null;
    }

    /**
     * Remove and return the element at the back of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeLast() {
        if (!isEmpty()) {
            Node d = sentL.prev;
            sentL.prev = sentL.prev.prev;
            sentL.prev.next = sentL;
            size = size - 1;

            return d.item;
        }
        return null;
    }

    /**
     * The Deque61B abstract data type does not typically have a get method,
     * but we've included this extra operation to provide you with some
     * extra programming practice. Gets the element, iteratively. Returns
     * null if index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T get(int index) {
        Node p = sentF;
        if (index < size && index >= 0) {
            for(int i = 0; i <= index; i++) {
                p = p.next;
            }
            return p.item;
        }
        return null;
    }

    private T getRecursiveHelp(int i, Node first) {
        if (i == 0) {
            return first.item;
        }
        return getRecursiveHelp(i - 1, first.next);
    }
    /**
     * This method technically shouldn't be in the interface, but it's here
     * to make testing nice. Gets an element, recursively. Returns null if
     * index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T getRecursive(int index) {

        if (index < size && index >= 0) {
            return getRecursiveHelp(index, sentF.next);
        }

        return null;
    }

    public static void main(String[] args) {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();
        lld.addLast(0);
        lld.addLast(1);
        lld.addFirst(-1);
        System.out.println(lld.toList());
    }
}
