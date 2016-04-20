/** Implementation of an edge class (for graphs), could be directed or not.
 */
public class WEdge<VT> implements Comparable<WEdge> {

    /** Starting vertex of an WEdge. */
    private GVertex source;
    /** Ending vertex of an edge. */
    private GVertex end;
    /** Whether or not the edge is directed. */
    private boolean directed;
    /**The weight of the edge */
    private double weight;
    /** Create an undirected edge.
     *  @param u the start
     *  @param v the end
     *  @param inweight the input weight
     */
    public WEdge(GVertex u, GVertex v, double inweight) {
        this.source = u;
        this.end = v;
        this.directed = false;
        this.weight = inweight;
    }

    /** Create an edge.
     *  @param u the start
     *  @param v the end
     *  @param dir true if directed, false otherwise
     *  @param inweight the input weight
     */
    public WEdge(GVertex u, GVertex v, boolean dir, double inweight) {
        this.source = u;
        this.end = v;
        this.directed = dir;
        this.weight = weight;
    }

    /** Is the edge directed.
     *  @return true if yes, false otherwise
     */
    public boolean isDirected() {
        return this.directed;
    }

    /** Is a vertex incident to this edge.
     *  @param v the vertex
     *  @return true if source or end, false otherwise
     */
    public boolean isIncident(GVertex v) {
        return this.source.equals(v) || this.end.equals(v);
    }

    /** Get the starting endpoint vertex.
     *  @return the vertex
     */
    public GVertex source() {
        return this.source;
    }

    /** Get the ending endpoint vertex.
     *  @return the vertex
     */
    public GVertex end() {
        return this.end;
    }

    /** Get the weight of an edge.
     *  @return the edge weight;
     */
    public double weight() {
        return this.weight;
    }

    /** Create a string representation of the edge.
     *  @return the string as (source,end)
     */
    public String toString() {
        return "(" + this.source + "," + this.end + "," + this.weight + ")";
    }

    /** Check if two edges are the same.
     *  @param other the edge to compare to this
     *  @return true if directedness and endpoints match, false otherwise
     */
    public boolean equals(Object other) {
        if (other instanceof WEdge) {
            WEdge e = (WEdge) other;
            if (this.directed != e.directed) {
                return false;
            }
            if (this.directed) {
                return this.source.equals(e.source)
                    && this.end.equals(e.end);
            } else {
                return this.source.equals(e.source)
                    && this.end.equals(e.end)
                    || this.source.equals(e.end)
                    && this.end.equals(e.source);
            }
        }
        return false;
    }

    /** Make a hashCode based on the toString.
     *  @return the hashCode
     */
    public int hashCode() {
        return this.toString().hashCode();
    }
    public int compareTo(WEdge other) {
        return (int)(this.weight - other.weight);
    }
}
