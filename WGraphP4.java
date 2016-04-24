import java.util.ArrayList;
import java.util.Set;
import java.util.List;
import java.util.LinkedList;
import java.util.Collections;

public class WGraphP4<VT> implements WGraph<VT> {

  private int nextID;  // next id for vertex
  private ArrayList <ArrayList <WEdge<VT>>>  vertedges;  //list of edgelists for each vertex
  private ArrayList<GVertex<VT>> verts; // list of vertex
  private int numEdge; // num of edges
  private int uniques;
  // No real constructor neede

  // Initialize the graph with max n vertices
  public WGraphP4() {
    vertedges = new ArrayList<ArrayList<WEdge<VT>>>();
    //add list to each vertex
    verts = new ArrayList<GVertex<VT>>();
    numEdge = 0;
    nextID = 0;
    uniques = 0;
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
      for (GVertex<VT> temp : verts){
          if (data == temp.data()){
              return false;
          }
      }
      GVertex<VT> v = new GVertex(data, nextID++, uniques++);
      this.verts.add(v.uniqueid(),v); //add vertex with data, and next id 
      vertedges.add(v.uniqueid(), new ArrayList<WEdge<VT>>());
      return true;
  }

  public boolean addVertex(GVertex<VT> v) {
      for (GVertex<VT> temp : verts){
          if (v.data() == temp.data()){
              return false;
          }
      }
      if(v.uniqueid() == -1){
          v.changeunique(uniques++);
      }
      this.verts.add(v.uniqueid(),v); // add vertex to vertex list
      vertedges.add(v.uniqueid(),new ArrayList<WEdge<VT>>());
      return true;
  }

  public boolean addEdge(WEdge<VT> e) {
      boolean added = false;
      added = addEdge(e.source(), e.end(), e.weight());
      return added;
  }
  private int positioninlist(GVertex<VT> v){

      return verts.indexOf(v);
  }
  public boolean addEdge(GVertex<VT> v, GVertex<VT> u, double weight) {
      boolean success = true;
      if(!this.verts.contains(v)) // if verts does not contain begin vertex yet
          success = this.addVertex(v); //add vertex to vert
      if(success && !this.verts.contains(u))//if verts does not contain end
          success = this.addVertex(u);//add it
      if (!success)
          return false;
      if (vertedges.get(v.uniqueid()) != null){ // if ths size of the arrlist at a vert is not 0
         int a = 0;
         for (WEdge<VT> temp : vertedges.get(v.uniqueid())) { // see if the dege is there
             if (temp.end() == u)
                 a = 1;
         }
         if (a == 1) //if there return
             return false; //already there
         vertedges.get(v.uniqueid()).add(new WEdge<VT>(v, u, weight)); //add edge to list
      }
      if (vertedges.get(u.uniqueid()) != null){ // do again because edge is undircetional
         int b = 0;
         for (WEdge<VT> temp2 : vertedges.get(u.uniqueid())) {
             if (temp2.end() == v)
                 b = 1;
         }
         if (b == 1)
             return false; //already there
         vertedges.get(u.uniqueid()).add(new WEdge<VT>(u, v, weight));
         this.numEdge++;
         return true;
      }
      return false;

  }
  public boolean deleteEdge(GVertex<VT> v, GVertex<VT> u) {
      if(this.verts.contains(v) && this.verts.contains(u)) {//see if both verts there
          int a = -1;
          if(vertedges.get(v.uniqueid()).size() != 0){//see if vertex edge list is there
              for (WEdge<VT> temp : vertedges.get(v.uniqueid())) {
                  if (temp.end() == u)
                      a = vertedges.get(v.uniqueid()).indexOf(temp);
              }
          }
          int b = -1;
          if(vertedges.get(u.uniqueid()).size() != 0){//do same for other edge direction
              for (WEdge<VT> temp1 : vertedges.get(u.uniqueid())) {
                  if (temp1.end() == v)
                      b = vertedges.get(u.uniqueid()).indexOf(temp1);
              }
          }
          if (a > -1 && b > -1){
              vertedges.get(v.uniqueid()).remove(a);
              vertedges.get(u.uniqueid()).remove(b);
              this.numEdge--;
              return true;
          }
      }
      return false;
  }


  public boolean areAdjacent(GVertex<VT> v, GVertex<VT> u) {
      if(vertedges.get(v.uniqueid()) != null ){//see if node is connected
          if(vertedges.get(v.uniqueid()).size() < vertedges.get(u.uniqueid()).size()){
              for (WEdge<VT> temp1 : vertedges.get(v.uniqueid())) {
                  if (temp1.end() == u)
                      return true;
              }
          } else {
              for (WEdge<VT> temp1 : vertedges.get(u.uniqueid())) {
                  if (temp1.end() == v)
                      return true;
                  }
          }
      }
      return false;
  }

  public ArrayList<GVertex<VT>> neighbors(GVertex<VT> v) {
      ArrayList<GVertex<VT>> nbs = new ArrayList<GVertex<VT>>();
      int row = v.uniqueid();
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
      ArrayList<WEdge<VT>> edges = new ArrayList<WEdge<VT>>();
      for (ArrayList<WEdge<VT>> temp : vertedges) {
          for (WEdge<VT> temp1 : temp) {
              if (temp1.source().uniqueid() > temp1.end().uniqueid()){
                  edges.add(new WEdge<VT>(temp1.source(), temp1.end(), temp1.weight()));
              }
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
              if (! visited[u.uniqueid()]) {
                  visited[u.uniqueid()] = true;
                  stack.addFirst(u);
              }
          }
       }
       return reaches;
  }
  public List<WEdge<VT>> incidentEdges(GVertex<VT> v) {
      return vertedges.get(v.uniqueid());
  }
  public List<WEdge<VT>> Kruskals() {
      WGraphP4<VT> newstuff = new WGraphP4();
      List<GVertex<VT>> verti = this.allVertices();
      List<WEdge<VT>> edges = this.allEdges();
      Partition P = new Partition(verti.size()); 
      MaxPQHeap Q = new  MaxPQHeap(edges);
      while (Q.size() > 0) {
          WEdge<VT> temp = Q.removeMinElement();
          GVertex<VT> u = temp.source();
          GVertex<VT> v = temp.end();
          if(P.find(u.uniqueid()) != P.find(v.uniqueid())){
              newstuff.addEdge(temp);
              P.union(u.uniqueid(),v.uniqueid());
          }
      }
      return newstuff.allEdges();
  }
}
