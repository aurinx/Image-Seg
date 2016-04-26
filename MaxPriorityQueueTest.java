import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.AbstractMap;
import java.util.Set;
import java.util.Collection;

public class MaxPriorityQueueTest {
    static MaxPQHeap<Integer> first;
    static MaxPQHeap<String> second;
    // makes some arrays of different types
    static int []  iray = {0,1,2,3,4,5,6,7,8,9,10};
    static String [] sray = {"zro", "one", "two", "tre", "for", "fiv", "six", "sev"};
    static Collection<Integer> icol;
    static Collection<String> scol;
    //sees if collection is same
    public static <T> boolean sameCollection(Collection<T> c1, Collection<T> orig) {
        ArrayList<T> c2 = new ArrayList<T>(orig);
        if (c1.size() != c2.size()){
            return false;
        }
        for (T val : c1) {
            if (!c2.remove(val))
                return false;
        }
        if (c2.size() != 0) {
            return false;
        }
        return true;
    }
    //make collection of original array types
    @BeforeClass
    public static void init() {
        icol = new ArrayList<Integer>();
        scol = new ArrayList<String>();
        for(int i = 0; i < iray.length; i++) {
            icol.add(iray[i]);
        }
        for(int i = 0; i < sray.length; i++){
            scol.add(sray[i]);
        }
    }

    @Before 
    public void setup() {
    }
    @Test // test if new pq is empty
    public void testEmpty() {
        MaxPQHeap<Integer> EmptyTest = new MaxPQHeap<Integer>();
        assertTrue("size of initialized heap not 0",0 == EmptyTest.size());
//        assertTrue("new PQ is not empty",EmptyTest.isEmpty());
    }
    @Test //tests if inserting in order make size bigger
    public void testinsert(){
        first = new MaxPQHeap<Integer>();
        second = new MaxPQHeap<String>();
        for (int i = 0; i < iray.length; i++){
            first.insert(iray[i]);
        }
        for (int i = 0; i < sray.length; i++){
            second.insert(sray[i]);
        }
        assertTrue("did not correctly get size", 11 == first.size());
        assertTrue("did not correctly get size", 8 == second.size());
    }

    @Test // test if inserting out of order makes size bigger
    public void sizetests(){
        MaxPQHeap<Integer> SizeTest = new MaxPQHeap<Integer>();
        assertTrue("not correct size of new PQ",0 == SizeTest.size());
        SizeTest.insert(5);
        assertTrue("did not insert to size",1 == SizeTest.size());
        SizeTest.insert(8);
        SizeTest.insert(2);
        SizeTest.insert(1);
        assertTrue(4 == SizeTest.size());
    }

    @Test (expected=QueueEmptyException.class) // tests if get from new pq causes error
    public void getempty() {
        MaxPQHeap<Integer> Getempty = new MaxPQHeap<Integer>();
        Getempty.getMax();
    }

    @Test (expected=QueueEmptyException.class)//tests if remove from new pq causes error
    public void removeempty() {
        MaxPQHeap<Integer> Getempty = new MaxPQHeap<Integer>();
        Getempty.removeMax();
    }

    @Test // makes sure get max is updated
    public void getMaxafterinsert(){
        MaxPQHeap<Integer> GetTest = new MaxPQHeap<Integer>();
        assertTrue("not correct size of new PQ",0 == GetTest.size());
        GetTest.insert(5);
        assertTrue("did not insert to size",1 == GetTest.size());
        assertTrue("did not get max value",5 == GetTest.getMax());
        GetTest.insert(8);
        assertTrue("did not get max value",8 == GetTest.getMax());
        GetTest.insert(2);
        GetTest.insert(1);
        assertTrue("did not get max value",8 == GetTest.getMax());
        assertTrue(4 == GetTest.size());
    }

    @Test // makes sure inserting a dup increases size
    public void insertdup(){
        MaxPQHeap<Integer> DupTest = new MaxPQHeap<Integer>();
        assertTrue(0 == DupTest.size());
        DupTest.insert(5);
        assertTrue(1 == DupTest.size());
        DupTest.insert(8);
        DupTest.insert(2);
        DupTest.insert(1);
        assertTrue(4 == DupTest.size());
        DupTest.insert(1);
        assertTrue("did not insert duplicate",5 == DupTest.size());
    }

    @Test (expected=QueueEmptyException.class) // makes sure removes works
    public void emptyafterremove() {
        MaxPQHeap<Integer> EmptyRemove = new MaxPQHeap<Integer>();
        EmptyRemove.insert(3);
        EmptyRemove.insert(1);
        EmptyRemove.insert(4);
        EmptyRemove.insert(2);
        assertTrue("did not get size right",4 == EmptyRemove.size());
        assertTrue("did not return max",4 == EmptyRemove.removeMax());
        assertTrue("did not remove max", 3 == EmptyRemove.size());
        EmptyRemove.removeMax();
        EmptyRemove.removeMax();
        assertTrue(1 ==  EmptyRemove.size());
        EmptyRemove.removeMax();
        assertTrue("did not remove with 1 left",0 ==  EmptyRemove.size());
        assertTrue("did not return empty",EmptyRemove.isEmpty());
        EmptyRemove.removeMax();
    }


    @Test (expected=QueueEmptyException.class)
    public void getMaxafterremove() { // makes sure get max is updated after remove
        MaxPQHeap<Integer> MaxRemove = new MaxPQHeap<Integer>();
        MaxRemove.insert(3);
        MaxRemove.insert(1);
        MaxRemove.insert(4);
        MaxRemove.insert(2);
        assertTrue(4 == MaxRemove.size());
        assertTrue(4 == MaxRemove.getMax());
        assertTrue(4 == MaxRemove.removeMax());
        assertTrue(3 == MaxRemove.size());
        assertTrue(3 == MaxRemove.getMax());
        MaxRemove.removeMax();
        MaxRemove.removeMax();
        assertTrue("did not get correct max after removeMax",1 == MaxRemove.getMax());
        MaxRemove.removeMax();
        assertTrue(0 == MaxRemove.size());
        MaxRemove.getMax();

    }

    @Test
    public void deletedup(){ // makes sure removing a dup only decreases size by 1
        MaxPQHeap<Integer> DupDel = new MaxPQHeap<Integer>();
        assertTrue(0 == DupDel.size());
        DupDel.insert(5);
        assertTrue(1 == DupDel.size());
        DupDel.insert(8);
        DupDel.insert(2);
        DupDel.insert(1);
        assertTrue(4 == DupDel.size());
        DupDel.insert(8);
        assertTrue("did not insert duplicate",5 == DupDel.size());
        assertTrue("did not delete max", 8  == DupDel.removeMax());
        assertTrue("did not delete only 1 dup", 4 == DupDel.size());
    }

    @Test
    public void testinit(){ // tests if it can make a pq from a collection
        MaxPQHeap<Integer> inittest = new MaxPQHeap<Integer>();
        inittest.init(icol);
        assertTrue("did not insert all value of Collec", 11 == inittest.size());
        assertTrue(11 == inittest.removeMax());
        MaxPQHeap<String> inittest2 = new MaxPQHeap<String>();
        inittest2.init(scol);
        assertTrue("did not insert all value of Collec", 8 == inittest2.size());

    }

}
