
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
public class MaxPQHeap<T extends Comparable<? super T>> implements MaxPriorityQueue {

    private static final int DEFAULT_SIZE = 10;
    private T[] data;
    private int size;
    private Comparator<? super T> comp;

    /**
     * Creates a heap with a backing array of default initial size.
     */
    public MaxPQHeap() {
        this(DEFAULT_SIZE);
    }

    /**
     * Creates an empty heap with a backing array of the given initial capacity.
     * 
     * @param initialCapacity
     *            the initial size of the backing array
     */
    public MaxPQHeap(int initialCapacity) {
        // Unchecked warning unavoidable. Note we have to create
        // an array of Comparable, not Object, in order
        // to cast to E[]
        data = (T[]) new Comparable[initialCapacity];
        size = 0;

        // create and cache the comparator to reuse in
        // the static percolateUp/Down methods
        Comparator<T> comp = new Comparator<T>() {
            @Override
            public int compare(T lhs, T rhs) {
                return rhs.compareTo(lhs);
            }
        };
        this.comp = comp;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public void clear() {
        data = (T[]) new Comparable[DEFAULT_SIZE];
        this.size = 0;
    }

    @Override
    public Comparable getMax() throws QueueEmptyException {
        if (size == 0) throw new QueueEmptyException();
        return data[0];
    }

    @Override
    public Comparable removeMax() throws QueueEmptyException {
        if (size == 0) throw new QueueEmptyException();
        T ret = data[0];
        data[0] = data[size - 1];
        data[size - 1] = null;
        --size;
        percolateDown(data, comp, size, 0);
        return ret;
    }
    private void checkCapacity()
    {
      if (size == data.length)
      {
        // create a copy of the data array with double the capacity
        data = Arrays.copyOf(data, data.length * 2);
      }
    }

    @Override
    public void insert(Comparable val) {
        checkCapacity();
        data[size] = (T) val;
        percolateUp(data, comp, size);
        ++size;
    }
    
    private static <T> void percolateUp(T[] data, Comparator<? super T> comp, int current)
    {
      int parent = (current - 1) / 2;
      while (current > 0 && comp.compare(data[current], data[parent]) < 0)
      {
        swap(data, current, parent);
        current = parent;
        parent = (current - 1) / 2;
      }
    }
    private static <T> void percolateDown(T[] data, Comparator<? super T> comp, int size, int current)
    {
      int child = 2 * current + 1; // left child, if any
      while (child < size)
      {
        if (child + 1 < size)
        {
          // there's a right child too, pick the smallest child
          if (comp.compare(data[child], data[child + 1]) > 0)
          {
            child = child + 1;
          }
        }
        if (comp.compare(data[current], data[child]) > 0)
        {
          swap(data, current, child);
        }
        current = child;
        child = 2 * current + 1;
      }
    }
    private static void swap(Object[] data, int i, int j)
    {
      Object temp = data[i];
      data[i] = data[j];
      data[j] = temp;
    }
    @Override
    public void init(Collection values) {
        Iterator iter = values.iterator();
        while (iter.hasNext()) {
            this.insert((Comparable) iter.next());
        }
    }
    public String toString() {
        String output = "";
        for (int i = 0; i < this.size; i++) {
            output += data[i] + " ";
        }
        return output;
    }
}
