import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int count = Integer.parseInt(args[0]);

        RandomizedQueue<String> queue = new RandomizedQueue<>();

        for (int i = 0; i < count; i++) {
            String word = StdIn.readString();
            queue.enqueue(word);
        }

        for (int i = 0; i < count; i++) {
            StdOut.println(queue.dequeue());
        }
    }
}
