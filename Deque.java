import java.util.Iterator;
import java.util.NoSuchElementException;

//LinkedListImplementation
public class Deque<Item> implements Iterable<Item> {

    private int size = 0;
    private Node head;
    private Node tail;

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        validateInputParameters(item);


        Node newNode = new Node(item);

        if (isEmpty()) {
            this.head = newNode;
            this.tail = this.head;
        }
        else {
            Node head = this.head;

            head.previous = newNode;
            newNode.next = head;

            this.head = newNode;
        }

        size++;
    }

    public void addLast(Item item) {
        validateInputParameters(item);


        Node newNode = new Node(item);

        if (isEmpty()) {
            this.head = newNode;
            this.tail = this.head;
        }
        else {
            Node tail = this.tail;

            tail.next = newNode;
            newNode.previous = tail;

            this.tail = newNode;
        }

        size++;
    }

    public Item removeFirst() {
        if (head == null) {
            throw new NoSuchElementException();
        }

        Node head = this.head;

        size--;

        if (isEmpty()) {
            this.head = null;
            this.tail = null;
        }
        else {
            this.head = head.next;
            this.head.previous = null;
        }

        return head.value;
    }

    public Item removeLast() {
        if (tail == null) {
            throw new NoSuchElementException();
        }

        Node tail = this.tail;

        size--;

        if (isEmpty()) {
            this.head = null;
            this.tail = null;
        }
        else {
            this.tail = this.tail.previous;
            this.tail.next = null;
        }

        return tail.value;
    }

    public Iterator<Item> iterator() {
        return new ItemIterator();
    }

    private void validateInputParameters(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
    }

    private class ItemIterator implements Iterator<Item> {

        private Node currentEntry;

        public ItemIterator() {
            Node pointerOnHead = new Node(null);
            pointerOnHead.next = head;
            this.currentEntry = pointerOnHead;
        }

        @Override
        public boolean hasNext() {
            return currentEntry.next != null;
        }

        @Override
        public Item next() {
            if (currentEntry.next == null) {
                throw new NoSuchElementException();
            }
            currentEntry = currentEntry.next;
            return currentEntry.value;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class Node {
        public Item value;
        public Node next;
        public Node previous;

        public Node(Item value) {
            this.value = value;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();

        deque.addFirst(1);
        System.out.println(deque.removeLast());


    }

}
