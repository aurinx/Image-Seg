import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;


public class WGraphP4<VT> implements WGraph<VT> {

  private int nextID;  // next id for vertex
  private ArrayList <ArrayList <WEdge<VT>>>  vertedges;  //list of edgelists for each vertex
  private ArrayList<GVertex<VT>> verts; // list of vertex
  private int numEdge; // num of edges

  // No real constructor neede

  // Initialize the graph with max n vertices
  public WGraphP4() {
    vertedges = new ArrayList<ArrayList<WEdge<VT>>>();
    //add list to each vertex
    verts = new ArrayList<GVertex<VT>>();
    numEdge = 0;
    nextID = 0;
  }


  // Return the current number of edges
  public int numEdges() { return numEdge; }
  // Return the number of vertices
  public int numVerts() { return verts.size(); }


  //get the next ID for vert
  public int nextID() {
      return nextID++;
  }
  //add vertex
  public boolean addVertex(VT data) {
      for (int i =0; i < verts.size(); i++){
          if (data == verts.get(i).data()){
              return false;
          }
      }
      GVertex<VT> v = new GVertex(data, nextID++);
      this.verts.add(v); //add vertex with data, and next id 
      vertedges.add(positioninlist(v), new ArrayList<WEdge<VT>>());
      return true;
  }

  public boolean addVertex(GVertex<VT> v) {
      for (int i =0; i < verts.size(); i++){
          if (v.data() == verts.get(i).data()){
              return false;
          }
      }
      this.verts.add(v); // add vertex to vertex list
      vertedges.add(positioninlist(v),new ArrayList<WEdge<VT>>());
      return true;
  }

  public boolean addEdge(WEdge<VT> e) {
      boolean added = false;
      added = addEdge(e.source(), e.end(), e.weight());
      return added;
  }
  private int positioninlist(GVertex<VT> v){
      for (int i = 0; i < verts.size(); i ++){
          if (v == verts.get(i)){
              return i;
          }
      }
      return -1;
  }
  public boolean addEdge(GVertex<VT> v, GVertex<VT> u, double weight) {
      boolean success = true;
      if(!this.verts.contains(v)) // if verts does not contain begin vertex yet
          success = this.addVertex(v); //add vertex to vert
      if(success && !this.verts.contains(u))//if verts does not contain end
          success = this.addVertex(u);//add it
      if (!success)
          return false;
      if (vertedges.get(positioninlist(v)) != null){ // if ths size of the arrlist at a vert is not 0
         int a = 0;
         for (WEdge<VT> temp : vertedges.get(positioninlist(v))) { // see if the dege is there
             if (temp.end() == u)
                 a = 1;
         }
         if (a == 1) //if there return
             return false; //already there
         vertedges.get(positioninlist(v)).add(new WEdge<VT>(v, u, weight)); //add edge to list
      }
      if (vertedges.get(positioninlist(u)) != null){ // do again because edge is undircetional
         int b = 0;
         for (WEdge<VT> temp2 : vertedges.get(positioninlist(u))) {
             if (temp2.end() == v)
                 b = 1;
         }
         if (b == 1)
             return false; //already there
         vertedges.get(positioninlist(u)).add(new WEdge<VT>(u, v, weight));
         this.numEdge++;
         return true;
      }
      return false;

  }
  public boolean deleteEdge(GVertex<VT> v, GVertex<VT> u) {
      if(this.verts.contains(v) && this.verts.contains(u)) {//see if both verts there
          int a = -1;
          if(vertedges.get(positioninlist(v)).size() != 0){//see if vertex edge list is there
              for (WEdge<VT> temp : vertedges.get(positioninlist(v))) {
                  if (temp.end() == u)
                      a = vertedges.get(positioninlist(v)).indexOf(temp);
              }
          }
          int b = -1;
          if(vertedges.get(positioninlist(u)).size() != 0){//do same for other edge direction
              for (WEdge<VT> temp1 : vertedges.get(positioninlist(u))) {
                  if (temp1.end() == v)
                      b = vertedges.get(positioninlist(v)).indexOf(temp1);
              }
          }
          if (a > -1 && b > -1){
              vertedges.get(positioninlist(v)).remove(a);
              vertedges.get(positioninlist(u)).remove(b);
              this.numEdge--;
              return true;
          }
      }
      return false;
  }


  public boolean areAdjacent(GVertex<VT> v, GVertex<VT> u) {
      if(vertedges.get(positioninlist(v)) != null ){//see if node is connected
          for (WEdge<VT> temp1 : vertedges.get(positioninlist(v))) {
              if (temp1.end() == u)
                  return true;
          }
      }
      return false;
  }

  public ArrayList<GVertex<VT>> neighbors(GVertex<VT> v) {
      ArrayList<GVertex<VT>> nbs = new ArrayList<GVertex<VT>>();
      int row = positioninlist(v);
      for (WEdge<VT> temp : vertedges.get(row)) {
          nbs.add(temp.end());//add the end of each edge from a node
      }
      return nbs;
  }

  public int degree (GVertex<VT> v) {
      return this.neighbors(v).size();
  }

  public boolean areIncident(WEdge<VT> e, GVertex<VT> v) {
      return e.source().equals(v) || e.end().equals(v);
  }

  public List<WEdge<VT>> allEdges() {
      int nv = this.numVerts();
      ArrayList<WEdge<VT>> edges = new ArrayList<WEdge<VT>>(nv);
      for (ArrayList<WEdge<VT>> temp : vertedges) {
          for (WEdge<VT> temp1 : temp) {
              edges.add(new WEdge<VT>(temp1.source(), temp1.end(), temp1.weight()));
          }
      }
      return edges;
  }

  public List<GVertex<VT>> allVertices() {
      return this.verts;
  }

  public List<GVertex<VT>> depthFirst(GVertex<VT> v) {
      ArrayList<GVertex<VT>> reaches = new ArrayList<GVertex<VT>>(this.numVerts());
      LinkedList<GVertex<VT>> stack = new LinkedList<GVertex<VT>>();
      boolean[] visited = new boolean[this.numVerts()];
      stack.addFirst(v);
      while(!stack.isEmpty()) {
          v = stack.removeFirst();
          reaches.add(v);
          for (GVertex<VT> u: this.neighbors(v)) {
              if (! visited[positioninlist(u)]) {
                  visited[positioninlist(u)] = true;
                  stack.addFirst(u);
              }
          }
       }
       return reaches;
  }
  public List<WEdge<VT>> incidentEdges(GVertex<VT> v) {
      return vertedges.get((positioninlist(v)));
  }

}
