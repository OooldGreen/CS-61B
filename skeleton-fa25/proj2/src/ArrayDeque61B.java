import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class ArrayDeque61B<T> implements Deque61B<T> {
    private T[] items;
    private int N;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque61B() {
        N = 8;
        items = (T[]) new Object[N];
        size = 0;
        nextFirst = 3;
        nextLast = 4;
    }

    /** pointer minus one */
    private int minusOne(int x) {
        return (x - 1 + N) % N;
    }

    /** pointer plus one */
    private int plusOne(int x) {
        return (x + 1) % N;
    }

    /** Resize the capacity of items to twice its original size */
    private T[] resize() {
        T[] newArr = (T[]) new Object[N * 2];
        int first = plusOne(nextFirst);
        for (int i = 0; i < size; i++) {
            newArr[i] = items[first];
            first = plusOne(first);
        }
        N = N * 2;
        nextFirst = N - 1;
        nextLast = size;
        return newArr;
    }

    /**
     *  When the amount of memory used is less than 25%,
     *  the total memory of the array should be resized down.
     */
    private boolean toResizeDown() {
        return (N >= 16 && (1.0 * size)/N < 0.25);
    }

    /**
     *  If there are little memories used,
     *  resize down the total capacity of items to half its original size
     * */
    private T[] resizeDown() {
        T[] newArr = (T[]) new Object[N / 2];
        int first = plusOne(nextFirst);
        for (int i = 0; i < size; i++) {
            newArr[i] = items[first];
            first = plusOne(first);
        }
        N = N / 2;
        nextFirst = N - 1;
        nextLast = size;
        return newArr;
    }

    /**
     * Add {@code x} to the front of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addFirst(T x) {
        if (size >= N) {
            items = resize();
        }
        items[nextFirst] = x;
        size += 1;
        nextFirst = minusOne(nextFirst);
    }

    /**
     * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addLast(T x) {
        if (size >= N) {
            items = resize();
        }
        items[nextLast] = x;
        size += 1;
        nextLast = plusOne(nextLast);
    }

    /**
     * Returns a List copy of the deque. Does not alter the deque.
     *
     * @return a new list copy of the deque.
     */
    @Override
    public List<T> toList() {
        List<T> returnedList = new ArrayList<>();
        int first = plusOne(nextFirst);

        for (int i = 0; i < size; i++) {
            returnedList.add(items[first]);
            first = plusOne(first);
        }
        return returnedList;
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
        if (size > 0) {
            nextFirst = plusOne(nextFirst);
            size -= 1;
            if (toResizeDown()) {
                items = resizeDown();
            }
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
        if (size > 0) {
            nextLast = minusOne(nextLast);
            size -= 1;
            if (toResizeDown()) {
                items = resizeDown();
            }
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
        if (index < size && index >= 0) {
            return items[plusOne(nextFirst + index)];
        }
        return null;
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
        throw new UnsupportedOperationException("No need to implement getRecursive for ArrayDeque61B.");
    }

    @Override
    public Iterator<T> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<T> {
        private int wizPos;
        private int iterateNum = 0;

        public DequeIterator() {
            wizPos = plusOne(nextFirst);
            iterateNum = 0;
        }

        public boolean hasNext() {
            return iterateNum < size;
        }

        public T next() {
            T returnItem = items[wizPos];
            wizPos = plusOne(wizPos);
            iterateNum += 1;
            return returnItem;
        }
    }

    /** Rewrite the equals method to compare two elements but not two addresses.*/
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ArrayDeque61B newDeque) {
            if (this.size != newDeque.size) {
                return false;
            }
            for (T x : this) {
                if (!newDeque.contains(x)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private boolean contains(T x) {
        int first = plusOne(nextFirst);
        for (int i = 0; i < size; i++) {
            if (items[first].equals(x)) {
                return true;
            }
            first = plusOne(first);
        }
        return false;
    }

    @Override
    public String toString() {
        String s = "";
        int first = plusOne(nextFirst);
        for(int i = 0; i < size; i++) {
            s = s.concat(String.valueOf(items[first]));
            first = plusOne(first);

            if (i != size - 1) {
                s += ", ";
            }
        }
        return s;
    }
}
