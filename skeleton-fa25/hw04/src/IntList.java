import edu.princeton.cs.algs4.In;

public class IntList {
    int first;
    IntList rest;

    public IntList(int f, IntList r) {
        first = f;
        rest = r;
    }

    /** Return the size of the list using... recursion! */
    public int size() {
        if (rest == null) {
            return 1;
        }
        return 1 + this.rest.size();
    }

    /** Return the size of the list using no recursion! */
    public int iterativeSize() {
        IntList p = this;
        int totalSize = 0;
        while (p != null) {
            totalSize += 1;
            p = p.rest;
        }
        return totalSize;
    }

    /** Returns the ith item of this IntList. */
    public int get(int i) {
        if (i == 0) {
            return first;
        }
        return rest.get(i - 1);
    }

    /**
     * Returns an IntList identical to L, but with
     * each element incremented by x. L is not allowed
     * to change. Must use recursion.
     *
     * This method is non-destructive, i.e. it must not modify the original list.
     */
    public static IntList incrRecursiveNondestructive(IntList L, int x) {
        // TODO: Fill in this code

        if (L == null) {
            return null;
        }

        return new IntList(L.first + x,incrRecursiveNondestructive(L.rest, x));
    }

    /**
     * Returns an IntList identical to L, but with
     * each element incremented by x. Modifies the original list.
     * You are not allowed to use "new" in this method.
     */
    public static IntList incrRecursiveDestructive(IntList L, int x) {
        // TODO: Fill in this code
        if (L == null) {
            return null;
        }
        L.first += x;
        incrRecursiveDestructive(L.rest, x);

        return L;
    }

    /**
     * Returns an IntList identical to L, but with
     * each element incremented by x. Not allowed
     * to use recursion. May not modify the original list.
     */
    public static IntList incrIterativeNondestructive(IntList L, int x) {
        // TODO: Fill in this code
        IntList head = new IntList(L.first + x, null);
        IntList Q = head;

        while (L.rest != null) {
            L = L.rest;
            Q.rest = new IntList(L.first + x, null);
            Q = Q.rest;
        }

        return head;
    }

    /**
     * Returns an IntList identical to L, but with
     * each element incremented by x. Not allowed
     * to use recursion. Modifies the original list.
     * You are not allowed to use "new" in this method.
     */
    public static IntList incrIterativeDestructive(IntList L, int x) {
        // TODO: Fill in this code
        IntList p = L;

        while (p != null) {
            p.first += x;
            p = p.rest;
        }

        return L;
    }

    /**
     * Returns an IntList consisting of the elements of L1 followed by the
     * elements of L2.
     */
    public static IntList concatenate(IntList L1, IntList L2) {
        // TODO: Fill in this code

        // 1. 直接修改的方法，不稳健，有例外，易出问题
//        IntList p = L1;
//
//        if (L1 == null) {
//            return L2;
//        }
//
//        while (p.rest != null) {
//            p = p.rest;
//        }
//
//        p.rest = L2;
//
//        return L1;

        // 2. 递归，稳健，简洁
        if (L1 == null) {
            return L2;
        }

        return new IntList(L1.first, concatenate(L1.rest, L2));
    }

    /*
     * =================================================================
     * OPTIONAL METHODS
     * =================================================================
     */

    /**
     * Returns the sum of all elements in the IntList.
     */
    public int sum() {
        // Optional: Fill in this code

        if (rest == null) {
            return first;
        }

        return first + this.rest.sum();
    }

    /**
     * Destructively adds x to the end of the list.
     */
    public void addLast(int x) {
        // Optional: Fill in this code
        if (rest == null) {
            rest = new IntList(x, null);
            return;
        }
        this.rest.addLast(x);
    }

    /**
     * Destructively adds x to the front of this IntList.
     * This is a bit tricky to implement. The standard way to do this would be
     * to return a new IntList, but for practice, this implementation should
     * be destructive.
     */
    public void addFirst(int x) {
        // Optional: Fill in this code
        IntList newInt = new IntList(first, rest);
        this.first = x;
        this.rest = newInt;
    }
}
