package project20280.priorityqueue;

/*
 */

import project20280.interfaces.Entry;
import project20280.tree.LinkedBinaryTree;

import java.util.ArrayList;
import java.util.Comparator;


/**
 * An implementation of a priority queue using an array-based heap.
 */

public class HeapPriorityQueue<K, V> extends AbstractPriorityQueue<K, V> {

    protected ArrayList<Entry<K, V>> heap = new ArrayList<>();

    /**
     * Creates an empty priority queue based on the natural ordering of its keys.
     */
    public HeapPriorityQueue() {
        super();
    }

    /**
     * Creates an empty priority queue using the given comparator to order keys.
     *
     * @param comp comparator defining the order of keys in the priority queue
     */
    public HeapPriorityQueue(Comparator<K> comp) {
        super(comp);
    }

    /**
     * Creates a priority queue initialized with the respective key-value pairs. The
     * two arrays given will be paired element-by-element. They are presumed to have
     * the same length. (If not, entries will be created only up to the length of
     * the shorter of the arrays)
     *
     * @param keys   an array of the initial keys for the priority queue
     * @param values an array of the initial values for the priority queue
     */
    public HeapPriorityQueue(K[] keys, V[] values) {
        // TODO
        super();
        for (int j=0; j < Math.min(keys.length, values.length); j++)
            heap.add(new PQEntry<>(keys[j], values[j]));
        heapify();
    }

    // protected utilities
    protected int parent(int j) {
        // TODO
        return (j - 1) / 2;
    }

    protected static int left(int j) {
        // TODO
        return 2 * j + 1;
    }

    protected static int right(int j) {
        // TODO
        return 2 * j + 2;
    }

    protected boolean hasLeft(int j) {
        // TODO
        return left(j) < heap.size();
    }

    protected boolean hasRight(int j) {
        // TODO
        return right(j) < heap.size();
    }

    /**
     * Exchanges the entries at indices i and j of the array list.
     */
    protected void swap(int i, int j) {
        // TODO
        Entry<K, V> temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    /**
     * Moves the entry at index j higher, if necessary, to restore the heap
     * property.
     */
    protected void upheap(int j) {
        // TODO
        while(j > 0){
            int p = parent(j);
            if(compare(heap.get(j), heap.get(p)) >= 0){
                break;
            }
            swap(j, p);
            j = p;
        }
    }

    /**
     * Moves the entry at index j lower, if necessary, to restore the heap property.
     */
    protected void downheap(int j) {
        // TODO
        while(hasLeft(j)) {
            int leftIndex = left(j);
            int smallChildIndex = leftIndex;
            if (hasRight(j)) {
                int rightIndex = right(j);
                if (compare(heap.get(leftIndex), heap.get(rightIndex)) > 0) {
                    smallChildIndex = rightIndex;
                }
            }
            if (compare(heap.get(smallChildIndex), heap.get(j)) >= 0) {
                break;
            }
            swap(j, smallChildIndex);
            j = smallChildIndex;
        }
    }

    /**
     * Performs a bottom-up construction of the heap in linear time.
     */
    protected void heapify() {
        // TODO
        int startIndex = parent(size() - 1);
        for (int j = (heap.size() / 2) - 1; j >= 0; j--){
            downheap(j);
        }

    }

    /**
     * Returns the number of items in the priority queue.
     *
     * @return number of items
     */
    @Override
    public int size() {
        return heap.size();
    }

    /**
     * Returns (but does not remove) an entry with minimal key.
     *
     * @return entry having a minimal key (or null if empty)
     */
    @Override
    public Entry<K, V> min() {
        return heap.get(0);
    }

    /**
     * Inserts a key-value pair and return the entry created.
     *
     * @param key   the key of the new entry
     * @param value the associated value of the new entry
     * @return the entry storing the new key-value pair
     * @throws IllegalArgumentException if the key is unacceptable for this queue
     */
    @Override
    public Entry<K, V> insert(K key, V value) throws IllegalArgumentException {
        // TODO
        checkKey(key);
        Entry<K,V> newest = new PQEntry<>(key, value);
        heap.add(newest);
        upheap(heap.size() - 1);
        return newest;
    }

    /**
     * Removes and returns an entry with minimal key.
     *
     * @return the removed entry (or null if empty)
     */
    @Override
    public Entry<K, V> removeMin() {
        // TODO
        if (heap.isEmpty()) return null;
        Entry<K,V> min = heap.get(0);

            swap(0, heap.size() - 1);
            heap.remove(heap.size() - 1);

            if(!heap.isEmpty()){
                downheap(0);
            }
        return min;
    }

    public String toString() {
        return heap.toString();
    }

    /**
     * Used for debugging purposes only
     */
    private void sanityCheck() {
        for (int j = 0; j < heap.size(); j++) {
            int left = left(j);
            int right = right(j);
            //System.out.println("-> " +left + ", " + j + ", " + right);
            Entry<K, V> e_left, e_right;
            e_left = left < heap.size() ? heap.get(left) : null;
            e_right = right < heap.size() ? heap.get(right) : null;
            if (left < heap.size() && compare(heap.get(left), heap.get(j)) < 0) {
                System.out.println("Invalid left child relationship");
                System.out.println("=> " + e_left + ", " + heap.get(j) + ", " + e_right);
            }
            if (right < heap.size() && compare(heap.get(right), heap.get(j)) < 0) {
                System.out.println("Invalid right child relationship");
                System.out.println("=> " + e_left + ", " + heap.get(j) + ", " + e_right);
            }
        }
    }

    //Q6
    private static void pqSort(){
        for (int n = 1000; n <= 1000000; n += 100000) {
            long totalTime = 0;
            int trials = 10;

            for (int i = 0; i < trials; i++) {
                Integer[] data = new Integer[n];
                for (int j = 0; j < n; j++) data[j] = (int) (Math.random() * n);
                HeapPriorityQueue<Integer, Integer> pq1 = new HeapPriorityQueue<>();

                long start = System.nanoTime();

                for(Integer val : data){
                    pq1.insert(val,val);
                }

                while(!pq1.isEmpty()){
                    pq1.removeMin();
                }
                long end = System.nanoTime();

                totalTime += (end - start);
            }

            double avgTime = (double) totalTime / trials;
            System.out.println(n + "," + avgTime);

        }
    }

   private static void inPlaceHeapSort(){

        for(int n = 1000; n <= 1000000; n += 100000){
            long totalTime = 0;
            int trials = 10;
            for (int i = 0; i < trials; i++) {
                Integer[] data = new Integer[n];
                for (int j = 0; j < n; j++) data[j] = (int) (Math.random() * n);
                long start = System.nanoTime();
                for(int j = data.length/2 -1; j >= 0; j--){
                    downheapInPlace(data,j,data.length);
                }
                for(int j = data.length -1; j > 0; j--){
                    swapInPlace(data,0,j);
                    downheapInPlace(data,0,j);
                }
                long end = System.nanoTime();
                totalTime += (end - start);
            }

            double avgTime = (double) totalTime / trials;
            System.out.println(n + "," + avgTime);
        }

    }
    public static void swapInPlace(Integer[] data,int i,int j){
        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    public  static void downheapInPlace(Integer[] data,int j,int limit){
        while(left(j) < limit) {
            int leftIndex = left(j);
            int smallChildIndex = leftIndex;
            if (right(j) < limit) {
                int rightIndex = right(j);
                if (data[leftIndex] > data[rightIndex]) {
                    smallChildIndex = rightIndex;
                }
            }
            if (data[smallChildIndex] >= data[j]) {
                break;
            }
            swapInPlace(data,j, smallChildIndex);
            j = smallChildIndex;
        }
    }
/*
    public static void main(String[] args) {
        Integer[] rands = new Integer[]{35, 26, 15, 24, 33, 4, 12, 1, 23, 21, 2, 5};
        HeapPriorityQueue<Integer, Integer> pq = new HeapPriorityQueue<>(rands, rands);

        System.out.println("elements: " + rands);
        System.out.println("after adding elements: " + pq);

        System.out.println("min element: " + pq.min());

        pq.removeMin();
        System.out.println("after removeMin: " + pq);
        // [             1,
        //        2,            4,
        //   23,     21,      5, 12,
        // 24, 26, 35, 33, 15]
    }*/

    public static void main(String[] args) {
        Integer[] rands = new Integer[]{35, 26, 15, 24, 33, 4, 12, 1, 23, 21, 2, 5};

        // Way 1: Bottom-up construction (O(n))
        System.out.println("--- Way 1: Bottom-up Heapify ---");
        HeapPriorityQueue<Integer, Integer> pq1 = new HeapPriorityQueue<>(rands, rands);
        System.out.println("After heapify: " + pq1);

        // Way 2: Successive insertions (O(n log n))
        System.out.println("\n--- Way 2: Successive Insertions ---");
        HeapPriorityQueue<Integer, Integer> pq2 = new HeapPriorityQueue<>();
        for(Integer i : rands) {
            pq2.insert(i, i);
        }
        System.out.println("After insertions: " + pq2);

        // Testing removeMin
        System.out.println("\nMin element: " + pq1.min());
        pq1.removeMin();
        System.out.println("After removeMin: " + pq1);

        System.out.println("\nQ6: PQSort Timing Measurements");
        System.out.println("n,average_time_nanos");
        pqSort();

        System.out.println("\nQ7: InPlace heapSort Timing Measurements");
        inPlaceHeapSort();

    }
}
