/** Data Structures EN.600.226.02
 * @author: Joo Sung Kim, Aurin Chakravarty, Dimitri Nikitopoulous
 * JHED: jkim469, achakar16, dnikito1
 */

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

/**Graph that implements WEdge.
 * @param <VT> generic datatype
 */
public class WGraphP4<VT> implements WGraph<VT> {
    /** Next ID. */
    private int nextID;
    /** ArrayList of edge lists for each vertex. */
    private ArrayList<ArrayList<WEdge<VT>>>  vertedges; 
    /** ArrayList of vertices. */
    private ArrayList<GVertex<VT>> verts;
    /** Number of edges. */
    private int numEdge;
    /** Number of uniques. */
    private int uniques;
    /** Height of the graph. */
    private int height;
    /** Width of the graph. */
    private int width;
    /** Empty constructor.
     */
    public WGraphP4() {
        this.vertedges = new ArrayList<ArrayList<WEdge<VT>>>();
        //add list to each vertex
        this.verts = new ArrayList<GVertex<VT>>();
        this.numEdge = 0;
        this.nextID = 0;
        this.uniques = 0;
    }

    /**Constructor with given width and height.
     * @param width1 given width
     * @param height1 given height
     */
    public WGraphP4(int width1, int height1) {
        this.vertedges = new ArrayList<ArrayList<WEdge<VT>>>(height1 * width1);
        //add list to each vertex
        this.verts = new ArrayList<GVertex<VT>>(height1 * width1);
        this.numEdge = 0;
        this.nextID = 0;
        this.uniques = 0;
        this.height = height1;
        this.width = width1;
    }

    @Override
    public int numEdges() { 
        return this.numEdge; 
    }
    
    @Override
    public int numVerts() {
        return this.verts.size();
    }

    @Override
    public int nextID() {
        return this.nextID++;
    }

    @Override
    public boolean addVertex(VT data) { 
        GVertex<VT> v = new GVertex(data, this.nextID++, this.uniques++);
        this.verts.add(v.uniqueid(), v); //add vertex with data, and next id 
        this.vertedges.add(v.uniqueid(), new ArrayList<WEdge<VT>>());
        return true;
    }

    @Override
    public boolean addVertex(GVertex<VT> v) {
        if (v.uniqueid() < this.uniques && v.uniqueid() != -1) {
            return false;
        }
        if (v.uniqueid() == -1) {
            v.changeunique(this.uniques++);
        }
        this.verts.add(v.uniqueid(), v); // add vertex to vertex list
        this.vertedges.add(v.uniqueid(), new ArrayList<WEdge<VT>>());
        return true;
    }

    @Override
    public boolean addEdge(WEdge<VT> e) {
        boolean added = false;
        added = this.addEdge(e.source(), e.end(), e.weight());
        return added;
    }

    /** Helmer method to get the index of vertex in verts list.
     * @param v Vertex we are trying to figure out
     * @return returns the position of v in verts
     */
    private int positioninlist(GVertex<VT> v) {
        return this.verts.indexOf(v);
    }

    @Override
    public boolean addEdge(GVertex<VT> v, GVertex<VT> u, double weight) {
        boolean success = true;
        if (v.uniqueid() == -1) {
            this.addVertex(v);
        }
        if (u.uniqueid() == -1) {
            this.addVertex(u);
        }
        if (this.vertedges.get(v.uniqueid()) != null) { 
            int a = 0;
            for (WEdge<VT> temp : this.vertedges.get(v.uniqueid())) {
                if (temp.end() == u) {
                    a = 1;
                    return false;
                }
            }
            this.vertedges.get(v.uniqueid())
                .add(new WEdge<VT>(v, u, weight)); //add edge to list
        }
        if (this.vertedges.get(u.uniqueid()) != null) { //redo
            int b = 0;
            for (WEdge<VT> temp2 : this.vertedges.get(u.uniqueid())) {
                if (temp2.end() == v) {
                    b = 1;
                    return false;
                }
            }
            this.vertedges.get(u.uniqueid()).add(new WEdge<VT>(u, v, weight));
            this.numEdge++;
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteEdge(GVertex<VT> v, GVertex<VT> u) {
        if (this.verts.contains(v) && this.verts.contains(u)) {
            int a = -1;
            if (this.vertedges.get(v.uniqueid()).size() != 0) {
                a = this.findEdge(v, u);
            }
            int b = -1;
            if (this.vertedges.get(u.uniqueid()).size() != 0) {
                b = this.findEdge(u, v);
            }
            if (a > -1 && b > -1) {
                this.vertedges.get(v.uniqueid()).remove(a);
                this.vertedges.get(u.uniqueid()).remove(b);
                this.numEdge--;
                return true;
            }
        }
        return false;
    }

    /** Helper method for deleteEdge.
     * @param v vertex v
     * @param u vertex u
     * @return int value of the index
     */
    private int findEdge(GVertex<VT> v, GVertex<VT> u) {
        for (WEdge<VT> temp : this.vertedges.get(v.uniqueid())) {
            if (temp.end() == u) {
                return this.vertedges.get(v.uniqueid()).indexOf(temp);
            }
        }
        return -1;
    }

    @Override
    public boolean areAdjacent(GVertex<VT> v, GVertex<VT> u) {
        if (this.vertedges.get(v.uniqueid()) != null) { //if node is connected
            if (this.vertedges.get(v.uniqueid()).size()
                < this.vertedges.get(u.uniqueid()).size()) {
                for (WEdge<VT> temp1 : this.vertedges.get(v.uniqueid())) {
                    if (temp1.end() == u) {
                        return true;
                    }
                }
            } else {
                for (WEdge<VT> temp1 : this.vertedges.get(u.uniqueid())) {
                    if (temp1.end() == v) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public ArrayList<GVertex<VT>> neighbors(GVertex<VT> v) {
        ArrayList<GVertex<VT>> nbs = new ArrayList<GVertex<VT>>();
        int row = v.uniqueid();
        for (WEdge<VT> temp : this.vertedges.get(row)) {
            nbs.add(temp.end()); //add the end of each edge from a node
        }
        return nbs;
    }

    @Override
    public int degree(GVertex<VT> v) {
        return this.neighbors(v).size();
    }

    @Override
    public boolean areIncident(WEdge<VT> e, GVertex<VT> v) {
        return e.source().equals(v) || e.end().equals(v);
    }

    @Override
    public List<WEdge<VT>> allEdges() {
        int nv = this.numVerts();
        ArrayList<WEdge<VT>> edges = new ArrayList<WEdge<VT>>();
        for (ArrayList<WEdge<VT>> temp : this.vertedges) {
            for (WEdge<VT> temp1 : temp) {
                if (temp1.source().uniqueid() > temp1.end().uniqueid()) {
                    edges.add(temp1);
                }
            }
        }
        return edges;
    }

    @Override
    public List<GVertex<VT>> allVertices() {
        ArrayList<GVertex<VT>> temp = new ArrayList<GVertex<VT>>();
        for (GVertex<VT> temp2: this.verts) {
            temp.add(temp2);
        }
        return temp;
    }

    @Override
    public List<GVertex<VT>> depthFirst(GVertex<VT> v) {
        ArrayList<GVertex<VT>> reaches = 
            new ArrayList<GVertex<VT>>(this.numVerts());
        LinkedList<GVertex<VT>> stack = new LinkedList<GVertex<VT>>();
        boolean[] visited = new boolean[this.numVerts()];
        stack.addFirst(v);
        while (!stack.isEmpty()) {
            v = stack.removeFirst();
            reaches.add(v);
            for (GVertex<VT> u: this.neighbors(v)) {
                if (!visited[u.uniqueid()]) {
                    visited[u.uniqueid()] = true;
                    stack.addFirst(u);
                }
            }
        }
        return reaches;
    }

    @Override
    public List<WEdge<VT>> incidentEdges(GVertex<VT> v) {
        return this.vertedges.get(v.uniqueid());
    }

    @Override
    public List<WEdge<VT>> kruskals() {
        WGraphP4<VT> newstuff = new WGraphP4();
        List<GVertex<VT>> verti = this.allVertices();
        List<WEdge<VT>> edges = this.allEdges();
        Partition pt = new Partition(verti.size()); 
        PQHeap<WEdge<VT>> pq = new PQHeap<WEdge<VT>>();
        pq.init(edges);
        while (pq.size() > 0) {
            WEdge<VT> temp = pq.peek();
            pq.remove();
            GVertex<VT> u = temp.source();
            GVertex<VT> v = temp.end();
            if (pt.find(u.uniqueid()) != pt.find(v.uniqueid())) {
                newstuff.addEdge(temp);
                pt.union(u.uniqueid(), v.uniqueid());
            }
        }
        return newstuff.allEdges();
    }
}
