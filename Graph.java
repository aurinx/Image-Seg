/*
 * Dimitri Nikitopoulos: dnikito1
 * Aurin Chakravarty: achakr16
 * Joo Sung Kim: jkim469
 * Data Structures 600.226.02
 * Dr. Selinski & Dr. Hager 
 * Project 4
 */import java.util.List;
/**
 * Graph interface.
 *
 */
public interface Graph {

    /** Get the number of edges.
     * @return int number of edges
     */
    int numEdges();

    /** Get the number of vertices. 
     * @return int number of vertices
     */
    int numVerts();

    /** Get the next ID to use in making a vertex.
     * @return int the ID in the sequence
     */
    int nextID();

    /** Create and add a vertex to the graph.
     *  @param d the data to store in the vertex
     *  @return true if successful, false otherwise
     */
    boolean addVertex(Object d);

    /** Add a vertex if it doesn't exist yet. 
     * @param v vertex to add
     * @return true if vertex was added
     */
    boolean addVertex(Vertex v);

    /** Add an edge, may also add the incident vertices.
     * @param e edge to add
     * @return true if edge was added
     */
    boolean addEdge(Edge e);

    /** Add a (directed) edge, may also add the incident vertices.
     * @param v first vertex
     * @param u second vertex
     * @return add a directed edge
     */
    boolean addEdge(Vertex v, Vertex u);

    /** Remove a (directed) edge if there. 
     * @param v first vertex to look at
     * @param u second vertex to look at
     * @return whether edge was deleted
     */
    boolean deleteEdge(Vertex v, Vertex u);

    /** Return true if there is an edge between v and u.
     * @param v first vertex to check
     * @param u second vertex to check 
     * @return true if the vertices are adjacent
     */
    boolean areAdjacent(Vertex v, Vertex u);

    /** Return a list of all the neighbors of vertex v.
     * @param v vertex to find list of neighbors for
     * @return The list of neighbors from v
     */
    List<Vertex> neighbors(Vertex v);

    /** Return the number of edges incident to v. 
     * @param v Number edges that include v
     * @return int degree number
     */
    int degree(Vertex v);

    /** Return true if v is an endpoint of edge e.
     * @param e edge to check
     * @param v vertex to check
     * @return true if they are incident, false if not
     */
    boolean areIncident(Edge e, Vertex v);

    /** Return a list of all the vertices that can be reached from v,
     * in the order in which they would be visited in a depthfirst
     * search starting at v.  
     * @param v vertex to start the search from
     * @return List of vertices found from v
     */
    List<Vertex> depthFirst(Vertex v);

    /** Return a list of all the edges.
     * @return List of all the edges in the graph
     */
    List<Edge> allEdges();

    /** Return a list of all the vertices.
     * @return Get list of all the vertices
     */
    List<Vertex> allVertices();

}
