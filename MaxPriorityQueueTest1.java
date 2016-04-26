import static org.junit.Assert.*;
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
import java.util.HashSet;
import java.util.ArrayList;
import java.util.AbstractMap;
import java.util.Set;
import java.util.Collection;

public class MaxPriorityQueueTest1 {

    /** Base Queue. */
    MaxPQHeap<String> e1;
    /** Base Queue with Integer. */
    MaxPQHeap<Integer> e2;
    /** Empty Queue. */
    MaxPQHeap<Integer> e7;
    /** Integer Array. */
    static Integer[] iray = { 11, 20, 4, 2, 1, 7, 5, 9, 0, 3, 38 };
    /** String Array. */
    static String[] sray = { "zro", "one", "two", "tre", "for", "fyv", "six", "svn", "ate", "nyn", "ten" };
    /** Integer Collection. */
    static Collection<Integer> colli;
    /** String Collection. */
    static Collection<String> colls;
    @Before
    public void setup() {
        colli = new ArrayList<Integer>();
        colls = new ArrayList<String>();
        e1 = new MaxPQHeap<String>();
        e2 = new MaxPQHeap<Integer>();
        for (int i = 0; i < iray.length; i++) {
            e2.insert(iray[i]);
            e1.insert(sray[i]);
            colli.add(iray[i]);
            colls.add(sray[i]);
        }
        e7 = new MaxPQHeap<Integer>();
    }

    @Test
    public void testSize() throws QueueEmptyException {
        // Check if initial size equals 0
        MaxPQHeap<Integer> e3 = new MaxPQHeap<Integer>();
        assertEquals("not starting with 0 size", 0, e3.size());
        assertTrue("not creating empty set", e3.isEmpty());
        // Check if size is updated after insertion
        assertEquals("size not updated", 11, e1.size());
        assertEquals("size not updated", 11, e2.size());
        assertFalse("not updating entry", e1.isEmpty());
        // check clear updates size to 0
        e1.clear();
        assertEquals("size not updated after clear", 0, e1.size());
        assertTrue("not cleared", e1.isEmpty());
        // check after duplicate key insertion
        e1.insert(3);
        e1.insert(4);
        e1.insert(3);
        assertEquals("duplicate keys are allowed", 3, e1.size());
    }

    @Test
    public void testClear() throws QueueEmptyException {
        assertTrue(e7.isEmpty());
        for (int i = 0; i < iray.length; i++) {
            e7.insert(iray[i]);
        }
        int size = e7.size();
        assertTrue(size == iray.length);
        assertEquals("cannot find correct maximum value", 38, e7.getMax());
        e7.clear();
        assertTrue(e7.isEmpty());
        assertEquals(0, e7.size());
        try {
            e7.getMax();
            fail("no exception thrown");
        } catch (QueueEmptyException e) {

        }
        try {
            e7.removeMax();
            fail("no exception thrown");
        } catch (QueueEmptyException e) {

        }
    }

    @Test
    public void testClearEmpty() throws QueueEmptyException {
        assertFalse(e1.isEmpty());
        assertTrue(e7.isEmpty());
        e7.clear();
        e1.clear();
        assertTrue("clear incorrect", e7.isEmpty());
        assertTrue("clear incorrect", e1.isEmpty());
        assertEquals("size not updated", 0, e7.size());
        assertEquals("size not updated", 0, e1.size());
        boolean isCatch = false;
        try {
            e7.getMax();
            fail("no exception thrown");
        } catch (QueueEmptyException e) {
            isCatch = true;
        }
        assertTrue("exception not thrown from getMax", isCatch);
        isCatch = false;
        try {
            e7.removeMax();
            fail("no exception thrown");
        } catch (QueueEmptyException e) {
            isCatch = true;
        }
        assertTrue("exception not thrown from removeMax", isCatch);
        isCatch = false;
        try {
            e1.getMax();
            fail("no exception thrown");
        } catch (QueueEmptyException e) {
            isCatch = true;
        }
        assertTrue("exception not thrown from getMax", isCatch);
        isCatch = false;
        try {
            e1.removeMax();
            fail("no exception thrown");
        } catch (QueueEmptyException e) {
            isCatch = true;
        }
        assertTrue("exception not thrown from removeMax", isCatch);
    }

    @Test
    public void testGetMax() throws QueueEmptyException {
        // check if they are not empty
        assertFalse("insert not working", e1.isEmpty());
        assertFalse("insert not working", e2.isEmpty());
        int max = 0;
        String maxs = "";
        try {
            max = (int) e1.getMax();
            maxs = (String) e2.getMax();
        } catch (QueueEmptyException e) {
            fail("should not throw exception");
        }
        assertEquals("Maximum value is not correct", 38, max);
        assertTrue("Maximum value is not correct", maxs.compareTo("zro") == 0);
        assertEquals("size should not be updated", 11, e1.size());
        assertEquals("size should not be updated", 11, e2.size());
        // assuming removeMax() works
        try {
            e1.removeMax();
            e2.removeMax();
        } catch (QueueEmptyException e) {
            fail("remove max is not working properly");
        }
        try {
            max = (int) e1.getMax();
            maxs = (String) e2.getMax();
        } catch (QueueEmptyException e) {
            fail("should not throw exception");
        }
        System.out.println(e1.toString());
        assertEquals("Maximum value is not correct", 20, max);
        assertTrue("Maximum value is not correct", maxs.compareTo("two") == 0);
        // after remove all
        e1.clear();
        e2.clear();
        try {
            e1.getMax();
            e2.getMax();
            fail("no exception thrown from getMax() function");
        } catch (QueueEmptyException e) {

        }
    }

    @Test
    public void testRemoveMax() throws QueueEmptyException {
        // check if they are not empty
        assertFalse("insert not working", e1.isEmpty());
        assertFalse("insert not working", e2.isEmpty());
        int max = 0;
        String maxs = "";
        // declare sorted array of descending order
        Integer[] checki = { 38, 20, 11, 9, 7, 5, 4, 3, 2, 1, 0 };
        String[] checks = { "zro", "two", "tre", "ten", "svn", "six", "one", "nyn", "fyv", "for", "ate"};
        int size = e1.size();
        for (int i = 0; i < checki.length; i++) {
            assertEquals("size is not updated", size - i, e1.size());
            assertEquals("size is not updated", size - i, e2.size());
            try {
                max = (int) e1.removeMax();
                maxs = (String) e2.removeMax();
            } catch (QueueEmptyException e) {
                fail("Should not throw exception");
            }
            assertTrue("not removing max properly", checki[i].compareTo(max) == 0);
            assertTrue("not removing max properly", checks[i].compareTo(maxs) == 0);
        }
        //after removing every entry
        assertTrue("not actually erasing", e1.isEmpty());
        assertTrue("not actually erasing", e2.isEmpty());
        assertEquals("size is not updated", 0, e1.size());
        assertEquals("size is not updated", 0, e2.size());
        try {
            e1.removeMax();
            fail("no exception thrown");
        } catch (QueueEmptyException e) {

        }
        try {
            e2.removeMax();
            fail("no exception thrown");
        } catch (QueueEmptyException e) {

        }
    }
    
    @Test
    public void testInsert() throws QueueEmptyException {
        Integer[] checki = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int max = 0;
        for (int i = 0; i < checki.length; i++) {
            e7.insert(checki[i]);
            assertEquals("size is not updated", i + 1, e7.size());
            try {
                max = (int) e7.getMax();
            } catch (QueueEmptyException e) {
                fail("should not throw exception");
            }
            assertTrue("maximum value is not correct", checki[i].compareTo(max) == 0);
        }
        assertEquals("size is not updated", checki.length, e7.size());
        assertFalse("not updated Heap", e1.isEmpty());
        // now insert some duplicate values
        Integer[] checki2 = {0, 1, 2, 7, 8, 9, 10, 11};
        int size = e7.size();
        for (int i = 0; i < checki2.length; i++) {
            e7.insert(checki2[i]);
            assertEquals("size is not updated", size + 1 + i, e7.size());
        }
        assertEquals("duplicate key does not increment size", 19, e7.size());
        int max2 = 0;
        int max3 = 0;
        try {
            max = (int) e7.removeMax();
            max2 = (int) e7.removeMax();
            max3 = (int) e7.removeMax();
        } catch (QueueEmptyException e) {
            fail("should not throw exception");
        }
        // max2 = 10, max3 = 10 (duplicate keys are allowed)
        assertEquals("maximum is not correct", 11, max);
        assertEquals("maximum is not correct", 10, max2);
        assertEquals("maximum is not correct", 10, max3);
        assertEquals("size not updated with duplicate keys", 16, e7.size());
    }
    @Test
    public void testInit() throws QueueEmptyException {
       //create two empty Heap
        MaxPQHeap<Integer> heapInt = new MaxPQHeap<Integer>();
        MaxPQHeap<Integer> heapStr = new MaxPQHeap<Integer>();
        //use init function to create heap with collection
        heapInt.init(colli);
        heapStr.init(colls);
        assertEquals("size not updated", 11, heapInt.size());
        assertEquals("size not updated", 11, heapStr.size());
        assertTrue("max value incorrect", heapInt.getMax().compareTo(38) == 0);
        assertTrue("max value incorrect", heapStr.getMax().compareTo("zro") == 0);
        // null case
        Collection<Integer> nullColl = new ArrayList<Integer>();
        e7.init(nullColl);
        assertEquals("null size is wrong", 0, e7.size());
        try {
            e7.removeMax();
            fail("no exception thrown");
        } catch (QueueEmptyException e) {
        }
        try {
            e7.getMax();
            fail("no exception thrown");
        } catch (QueueEmptyException e) {
        }
        
    }
}
