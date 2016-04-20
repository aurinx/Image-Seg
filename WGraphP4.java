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
      this.verts.add(new GVertex(data, nextID++)); //add vertex with data, and next id 
      vertedges.add(nextID,new ArrayList<WEdge<VT>>());
      return true;
  }

  public boolean addVertex(GVertex<VT> v) {
      if (this.verts.contains(v)) // see if vertex already there
          return false;
      vertedges.add(v.id(),new ArrayList<WEdge<VT>>());
      this.verts.add(v); // add vertex to vertex list
      return true;
  }

  public boolean addEdge(WEdge<VT> e) {
      boolean added = false;
      added = addEdge(e.source(), e.end(), e.weight());
      return added;
  }

  public boolean addEdge(GVertex<VT> v, GVertex<VT> u, double weight) {
      boolean success = true;
      if(!this.verts.contains(v)) // if verts does not contain begin vertex yet
          success = this.addVertex(v); //add vertex to vert
      if(success && !this.verts.contains(u))//if verts does not contain end
          success = this.addVertex(u);//add it
      if (!success)
          return false;
      if (vertedges.get(v.id()) != null){ // if ths size of the arrlist at a vert is not 0
         int a = 0;
         for (WEdge<VT> temp : vertedges.get(v.id())) { // see if the dege is there
             if (temp.end() == u)
                 a = 1;
         }
         if (a == 1) //if there return
             return false; //already there
         vertedges.get(v.id()).add(new WEdge<VT>(v, u, weight)); //add edge to list
      }
      if (vertedges.get(u.id()) != null){ // do again because edge is undircetional
         int b = 0;
         for (WEdge<VT> temp2 : vertedges.get(u.id())) {
             if (temp2.end() == v)
                 b = 1;
         }
         if (b == 1)
             return false; //already there
         vertedges.get(u.id()).add(new WEdge<VT>(u, v, weight));
         this.numEdge++;
         return true;
      }
      return false;

  }
  public boolean deleteEdge(GVertex<VT> v, GVertex<VT> u) {
      if(this.verts.contains(v) && this.verts.contains(u)) {//see if both verts there
          int a = 0;
          if(vertedges.get(v.id()) != null){//see if vertex edge list is there
              for (WEdge<VT> temp : vertedges.get(v.id())) {
                  if (temp.end() == u)
                      a = 1;
                      vertedges.get(v.id()).remove(temp);//remove edge from list
              }
          }
          int b = 0;
          if(vertedges.get(u.id()) != null){//do same for other edge direction
              for (WEdge<VT> temp1 : vertedges.get(u.id())) {
                  if (temp1.end() == v)
                      b = 1;
                      vertedges.get(u.id()).remove(temp1);
              }
          }
          if(a == 1 && b == 1)
              this.numEdge--;
              return true;
      }
      return false;
  }


  public boolean areAdjacent(GVertex<VT> v, GVertex<VT> u) {
      if(vertedges.get(v.id()) != null ){//see if node is connected
          for (WEdge<VT> temp1 : vertedges.get(v.id())) {
              if (temp1.end() == u)
                  return true;
          }
      }
      return false;
  }

  public ArrayList<GVertex<VT>> neighbors(GVertex<VT> v) {
      ArrayList<GVertex<VT>> nbs = new ArrayList<GVertex<VT>>();
      int row = v.id();
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
              if (! visited[u.id()]) {
                  visited[u.id()] = true;
                  stack.addFirst(u);
              }
          }
       }
       return reaches;
  }


}
