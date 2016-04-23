import org.junit.Test;
import org.junit.Before;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
/** Set of Junit tests for our Graph implementations.
 */
public class WGraphTest {
    WGraphP4<Character> g;
    GVertex<Character> t, u, v, w, x, y, z;
    WEdge<Character> a, b, c, d, e, f, h, i, j, k,l;

    @Before
    public void setupGraph() {

        g = new WGraphP4<Character>();
        v = new GVertex<Character>('v', g.nextID());
        u = new GVertex<Character>('u', g.nextID());
        x = new GVertex<Character>('x', g.nextID());
        y = new GVertex<Character>('y', g.nextID());
        z = new GVertex<Character>('z', g.nextID());
        w = new GVertex<Character>('w', g.nextID());
        t = new GVertex<Character>('t', g.nextID());
        a = new WEdge<Character>(t, u, 15);
        b = new WEdge<Character>(t, y, 7);
        c = new WEdge<Character>(t, w, 23);
        d = new WEdge<Character>(u, x, 13);
        e = new WEdge<Character>(v, u, 20);
        f = new WEdge<Character>(v, x, 10);
        h = new WEdge<Character>(v, z, 18);
        i = new WEdge<Character>(w, z, 11);
        j = new WEdge<Character>(w, x, 21);
        k = new WEdge<Character>(x, y, 12);
        l = new WEdge<Character>(y, z, 17);
    }

    @Test
    public void testEmpty() {
        assertEquals(0, g.numEdges());
        assertEquals(0, g.numVerts());
    }

    @Test
    public void testAddVertex() {
        assertEquals(true, g.addVertex(v));
        assertEquals(true, g.addVertex(u));
        assertEquals(false, g.addVertex(v));
        assertEquals(true, g.addVertex(w));
        //adding vertex by data
        assertEquals(true, g.addVertex('s'));
        assertEquals(false, g.addVertex('s'));
    }

    @Test
    public void testAddWEdge() {
        assertEquals(true, g.addEdge(e));
        assertEquals(true, g.addEdge(v, x, 10));
        assertEquals(false, g.addEdge(v, u, 50));
        assertEquals(false, g.addEdge(f));
    }

    @Test
    public void testAdjacency() {
        g.addVertex(v);
        g.addVertex(u);
        g.addVertex(x);
        g.addVertex(y);
        g.addVertex(t);
        g.addVertex(z);
        g.addVertex(w);
        assertEquals(false, g.areAdjacent(u, v));
        g.addEdge(a);
        g.addEdge(b);
        g.addEdge(c);
        g.addEdge(d);
        g.addEdge(e);
        g.addEdge(f);
        g.addEdge(h);
        g.addEdge(i);
        g.addEdge(j);
        g.addEdge(k);
        g.addEdge(l);
        assertEquals(true, g.areAdjacent(u, v));
        assertEquals(true, g.areAdjacent(v, u));
        assertEquals(true, g.areAdjacent(v, x));
        assertEquals(true, g.areAdjacent(t, w));
        assertEquals(true, g.areAdjacent(t, y));
        assertEquals(true, g.areAdjacent(y, x));
        assertEquals(true, g.areAdjacent(x, u));
        assertEquals(false, g.areAdjacent(v, y));
        assertEquals(false, g.areAdjacent(w, v));
        assertEquals(false, g.areAdjacent(t, z));
    }

    @Test
    public void testIncidence() {
        g.addVertex(v);
        g.addVertex(u);
        g.addVertex(x);
        g.addVertex(y);
        g.addVertex(t);
        g.addVertex(z);
        g.addVertex(w);
        g.addEdge(e);
        assertEquals(false, g.areIncident(e, x));
        assertEquals(false, g.areIncident(e, y));
        assertEquals(true, g.areIncident(e, v));
        assertEquals(true, g.areIncident(e, u));
        g.addEdge(f);
        assertEquals(true, g.areIncident(f, x));
        assertEquals(false, g.areIncident(f, u));
        assertEquals(7, g.numVerts());
        assertEquals(2, g.numEdges());
        g.addEdge(a);
        g.addEdge(b);
        g.addEdge(c);
        assertEquals(true, g.areIncident(a, u));
        assertEquals(false, g.areIncident(a, x));
        assertEquals(true, g.areIncident(b, y));
        assertEquals(false, g.areIncident(b, w));
        assertEquals(true, g.areIncident(c, t));
        assertEquals(false, g.areIncident(c, z));
        assertEquals(5, g.numEdges());
    }

    @Test
    public void testDegree() {
        g.addVertex(v);
        g.addVertex(u);
        g.addVertex(x);
        g.addVertex(y);
        assertEquals(0, g.degree(v));
        g.addEdge(e);
        assertEquals(1, g.degree(v));
        g.addEdge(f);
        assertEquals(2, g.degree(v));
        assertEquals(1, g.degree(x));
        assertEquals(0, g.degree(y));
        g.addEdge(d);
        assertEquals(2, g.degree(v));
        assertEquals(2, g.degree(x));
    }


    @Test
    public void testNeighbors() {
        g.addVertex(v);
        g.addVertex(u);
        g.addVertex(x);
        g.addVertex(y);
        assertEquals("[]", g.neighbors(v).toString());
        g.addEdge(e);
        //        System.out.println(g.neighbors(v).toString());
        assertEquals("[1]", g.neighbors(v).toString());
        assertEquals("[0]", g.neighbors(u).toString());
        g.addEdge(f);
        assertEquals("[1, 2]", g.neighbors(v).toString());
        assertEquals("[0]", g.neighbors(u).toString());
        assertEquals("[0]", g.neighbors(x).toString());
        assertEquals("[]", g.neighbors(y).toString());
    }

    @Test
    public void testDelete() {
        g.addVertex(t);
        g.addVertex(u);
        g.addVertex(v);
        g.addVertex(w);
        g.addVertex(x);
        g.addVertex(y);
        g.addVertex(z);
        g.addEdge(a);
        g.addEdge(b);
        g.addEdge(c);
        g.addEdge(d);
        g.addEdge(e);
        g.addEdge(f);
        g.addEdge(h);
        g.addEdge(i);
        g.addEdge(j);
        g.addEdge(k);
        g.addEdge(l);
        assertEquals(7, g.numVerts());
        assertEquals(11, g.numEdges());
        //added properly so far
        System.out.println("hello");
        assertEquals(true, g.deleteEdge(t, u));
        assertEquals(false, g.deleteEdge(t, z));
        assertEquals(false, g.deleteEdge(u, t)); // should this be false? are there two edges created by this case?
        assertEquals(true, g.deleteEdge(u, v));
        assertEquals(false, g.deleteEdge(u, v));
    }

    @Test
    public void testAllEdges() {
        g.addVertex(v);
        g.addVertex(u);
        g.addVertex(x);
        g.addVertex(y);
        g.addVertex(z);
        g.addVertex(w);
        g.addVertex(t);
        g.addEdge(a);
        g.addEdge(b);
        g.addEdge(c);
        g.addEdge(d);
        g.addEdge(e);
        g.addEdge(f);
        g.addEdge(h);
        g.addEdge(i);
        g.addEdge(j);
        g.addEdge(k);

        assertEquals(7, g.numVerts());
        assertEquals(10, g.numEdges());

        List<WEdge<Character>> test = g.allEdges();
        assertTrue(test.contains(a));
        assertFalse(test.contains(l));
    }

    @Test
    public void testAllVertices() {
        g.addVertex(t);
        g.addVertex(u);
        g.addVertex(v);
        g.addVertex(w);
        g.addVertex(x);
        g.addVertex(y);
        g.addEdge(a);
        g.addEdge(b);
        g.addEdge(c);
        g.addEdge(d);
        g.addEdge(e);
        g.addEdge(f);
        g.addEdge(j);
        g.addEdge(k);
        assertEquals(6, g.numVerts());
        assertEquals(8, g.numEdges());
        List<GVertex<Character>> test = g.allVertices();
        assertTrue(test.contains(t));
        assertTrue(test.contains(w));
        assertFalse(test.contains(z));
    }

    @Test
    public void testDepth() {
        g.addVertex(v);
        g.addVertex(u);
        g.addVertex(x);
        g.addVertex(w);
        assertFalse("added already made vertex",g.addVertex(x));
        g.addVertex(y);
        g.addVertex(z);
        assertEquals("correct",6, g.numVerts());
        g.addEdge(a);
        g.addEdge(b);
        g.addEdge(c);
        g.addEdge(d);
        g.addEdge(e);
        g.addEdge(f);
        g.addEdge(h);
        g.addEdge(i);
        g.addEdge(j);
        g.addEdge(k);
        g.addEdge(l);
        assertEquals("added vertex from edge",7, g.numVerts());
        assertEquals(11, g.numEdges());
        List<GVertex<Character>> testt = g.depthFirst(t);
        List<GVertex<Character>> testw = g.depthFirst(w);
        assertTrue(testt.contains(u));
        assertTrue(testt.contains(w));
        assertTrue(testt.contains(x)); ////
        assertTrue(testw.contains(z));
        assertTrue(testw.contains(x));
        assertTrue(testw.contains(v));
    }
}
