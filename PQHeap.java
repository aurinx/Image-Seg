import java.util.Collection;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Class to implement a general case priority queue heap.
 * @author AurinC
 *
 * @param <T> Generic var
 */
public class PQHeap<T extends Comparable<? super T>> implements PriorityQueue {

    /**
     * ArrayList that will hold the "nodes" in the heap.
     */
    private ArrayList<T> heap;
    /**
     * Comparator object with compare method to give ordering
     * on keys.
     */
    private Comparator<? super T> comp;
    /**
     * Empty priority queue with default comparator.
     */
    public PQHeap() {
        this.heap = new ArrayList<T>();
        this.heap.add(null);
        Comparator<T> compare = new Comparator<T>() {
            @Override
            public int compare(T first, T second) {
                return first.compareTo(second);
            }
        };
        this.comp = compare;
    }
    /**
     * Empty priority queue with comparator compare.
     * @param compare comparator parameter to pass in
     */
    public PQHeap(Comparator<T> compare) {
        this.heap = new ArrayList<T>();
        this.heap.add(null);
        this.comp = compare;
    }
    /** Main Method. 
     * @param args Commandline arguments
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }
    /**
     * Checks if a part of the heap is a leaf.
     * @param i index to look at
     * @return true if it is a leaf, false if not.
     */
    private boolean isLeaf(int i) {
        return (i >= this.size() / 2) && (i < this.size());
    }
    /**
     * Method to calculate parent of a node.
     * @param i index to look at
     * @return int of target node.
     */
    private int parent(int i) {
        if (i <= 0) {
            return -1;
        }
        return (i - 1) / 2;
    }
    /**
     * Method to calculate left child of a node.
     * @param i index to look at
     * @return int of target node.
     */
    private int leftChild(int i) {
        if (i >= this.size() / 2) {
            return -1;
        }
        return (2 * i) + 1;
    }
    /**
     * Method to calculate right child of a node.
     * @param i index to look at
     * @return int of target node.
     */
    private int rightChild(int i) {
        if (i >= this.size() / 2) {
            return -1;
        }
        return (2 * i) + 2;
    }
    /**
     * Swapping two values in the list.
     * @param a first value
     * @param b second value
     * @return true if swapped, false if not
     */
    private boolean swap(int a, int b) {
        if (a < 0 || a >= this.size() || b < 0 || b >= this.size()) {
            return false;
        }
        T tempVar = this.heap.get(a);
        this.heap.set(a, this.heap.get(b));
        this.heap.set(b, tempVar);
        return true;
    }
    @SuppressWarnings("unchecked")
    @Override
    public void insert(Comparable t) {
        this.heap.add((T) t);
        this.bubbleUp(this.size());
    }

    @Override
    public void remove() throws QueueEmptyException {
        if (this.size() == 1) {
            throw new QueueEmptyException();
        }  else {
            this.swap(1, this.size() - 1);
            T temp = this.heap.remove(this.size() - 1);
            this.bubbleDown(1);
        }
    
    }

    @Override
    public T peek() throws QueueEmptyException {
        return this.heap.get(1);
    }

    /**
     * Return if queue is empty or not.
     * @return true if queue is empty
     */
    public boolean isEmpty() {
        return (this.size() == 0);
    }

    /**
     * Return size of the queue.
     * @return Number of items in queue.
     */
    public int size() {
        return this.heap.size();
    }

    @Override
    public void clear() {
        this.heap = new ArrayList<T>();
        this.heap.add(null);
    }

    @Override
    public void init(Collection values) {
        Iterator iter = values.iterator();
        this.heap = new ArrayList<T>();
        this.heap.add(null);
        while (iter.hasNext()) {
            Object ele = iter.next();
            this.insert((T) ele);
        }
        
    }
    /**
     * Bubble up a value after insertion.
     * @param curr Current value to look at. 
     * @return true if bubbled up, false if it didn't
     */
    private boolean bubbleUp(int curr) {
        int parent = this.parent((curr));
        if ((curr <= 0) || (curr >= this.size())) {
            return false;     
        }
        // while curr is smaller than parent
        while ((curr >= 0) && (this.heap.get(curr).compareTo(this.heap.get(
                parent))) < 0) {
            this.swap(curr, parent);
            curr = this.parent(curr);
            parent = this.parent(curr);
        }
        return true;
    }
    /**
     * Bubble down after removal of best node.
     * @param curr Current value to look at.
     * @return true if bubbled down, false if it didn't
     */
    private boolean bubbleDown(int curr) {
        if ((curr <= 0) || curr >= this.size()) {
            return false;
        }
        while (!this.isLeaf(curr)) {
            int child = this.leftChild(curr);
            if ((child < (this.size())) && (this.heap.get(child).
                    compareTo(this.heap.get(child + 1)) > 0)) {
                child++;
            }
            if (this.heap.get(curr).compareTo(this.heap.get(child)) <= 0) {
                return true;
            }
            this.swap(curr, child);
            curr = child;
        }
        return true;
    }
    
}

