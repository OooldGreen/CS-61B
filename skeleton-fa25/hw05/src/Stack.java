public class Stack {
    private static class Node {
       int item;
       Node next;

       Node (int i, Node n) {
           item = i;
           next = n;
       }
    }

    private int size;
    private Node sentinel;

    public Stack () {
        sentinel = new Node (0, null);
        size = 0;
    }

    public void push (int x) {
        sentinel.next = new Node(x, sentinel.next);
        size = size + 1;
    }

    public int pop () {
        int x = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        size -= 1;

        return x;
    }

    public int size () {
        return size;
    }

    public int sum () {
        int sum = 0;
        Node p = sentinel.next;

        while (p != null) {
            sum += p.item;
            p = p.next;
        }

        return sum;
    }
}
