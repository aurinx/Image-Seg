/** Class to represent a vertex (in a graph).
 * @param <VT> vertex generic
 */
public class GVertex<VT> implements Comparable<GVertex> {

    /* Note that the nextID variable had to be moved to the graph class. */

    /** GVertex  ID number. */
    private int num;
    /** Data stored in the vertex. */
    private VT data;
    /** Unique number for each vertex.*/
    private int uniqueid;
    /** Create a new vertex.
     *  @param d the data to store in the node
     *  @param id the unique id of the node
     */
    public GVertex(VT d, int id) {
        this.data = d;
        this.num = id;
        this.uniqueid = -1; // set to -1 until it enters a graph
    }
    /**
     * Create a new vertex with unique id.
     * @param d data to store in the node
     * @param id is the id of the vertex
     * @param auniqueid graph's unique id for the vertex
     */
    public GVertex(VT d, int id, int auniqueid) {
        this.data = d;
        this.num = id;
        this.uniqueid = auniqueid;
    }

    /** Get the id of this vertex.
     *  @return the id
     */
    public int id() {
        return this.num;
    }
    /**
     * Get data of the vertex.
     * @return the data
     */
    public VT data() {
        return this.data;
    }
    /**
     * Get unique ID of vertex.
     * @return the unique id
     */
    public int uniqueid() {
        return this.uniqueid;
    }
    /**
     * Change the unique id of a certain numbered vertex.
     * @param temp number of the id
     */
    public void changeunique(int temp) {
        this.uniqueid = temp;
    }
    /** Get a string representation of the vertex.
     *  @return the string 
     */
    public String toString() {
        return this.num + "";
    }

    /** Check if two vertices are the same based on ID.
     *  @param other the vertex to compare to this
     *  @return true if the same, false otherwise
     */
    public boolean equals(Object other) {
        if (other instanceof GVertex) {
            GVertex v = (GVertex) other;
            return this.num == v.num;  // want these to be unique
        }
        return false;
    }

    /** Get the hashcode of a vertex based on its ID.
     *  @return the hashcode
     */
    public int hashCode() {
        return (new Integer(this.num)).hashCode();
    }

    /** Compare two vertices based on their IDs.
     *  @param other the vertex to compare to this
     *  @return negative if this < other, 0 if equal, positive if this > other
     */
    public int compareTo(GVertex other) {
        return this.uniqueid -  other.uniqueid;
    }
}
