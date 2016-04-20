import java.util.ArrayList;

class WGraphP4<VT> implements WGraph<VT> {

/**    private class Edge { // Doubly linked list node
      GVertex vertex; 
      double weight;
      Edge prev, next;
    }
    Edge(GVertex v, double w, Edge p, Edge n) {
      vertex = v;
      weight = w;
      prev = p;
      next = n;
    }
   */
  private int nextID;
  private ArrayList <ArrayList <WEdge<VT>>> vertedges;
  private ArrayList<GVertex<VT>> verts;
  private int numEdge;

  // No real constructor neede

  // Initialize the graph with max n vertices
  void WGraphP4(int n) {
    vertedges = new ArrayList<ArrayList<WEdge<VT>>>();
    // List headers;
    for (int i=0; i<n; i++)  {
        vertedges.add(new ArrayList<WEdge<VT>>());
    }
    verts = new ArrayList<GVertex<VT>>(n);
    numEdge = 0;
    nextID = 0;
  }


  // Return the current number of edges
  public int numEdges() { return numEdge; }
  // Return the number of vertices
  public int nodeVerts() { return vertedges.size(); }


  //get the next ID for vert
  public int nextID() {
      return nextID++;
  }
  //add vertex
  public boolean addVertex(Object data) {
      if (this.verts.size() == this.vertedges.size())
          return false;
      this.verts.add(new GVertex(data, nextID++));
      return true;
  }

  public boolean addVertex(GVertex<VT> v) {
      if (this.verts.size() == this.vertedges.size())
          return false;
      if (this.verts.contains(v))
          return false;
      this.verts.add(v);
      return true;
  }

  public boolean addEdge(WEdge<VT> e) {
      boolean added = false;
      added = addEdge(e.source(), e.end(), e.weight());
      if (added) {
          added = addEdge(e.end(), e.source(), e.weight());
          this.numEdge--;
      }
      return added;
  }

  public boolean addEdge(GVertex<VT> v, GVertex<VT> u, double weight) {
      boolean success = true;
      if(!this.verts.contains(v))
          success = this.addVertex(v);
      if(success && !this.verts.contains(u))
          success = this.addVertex(u);
      if (!success)
          return false;
      if (vertedges.get(v.id()) != null){
         int a = 0;
         for (WEdge<VT> temp : vertedges.get(v.id())) {
             if (temp.end() == u)
                 a = 1;
         }
         if (a == 1)
             return false; //already there
         vertedges.get(v.id()).add(new WEdge<VT>(v, u, weight));
         return true;
      }
      return false;

  }
  public boolean deleteEdge(GVertex<VT> v, GVertex<VT> u) {
      if(this.verts.contains(v) && this.verts.contains(u)) {
          int a = 0;
          if(vertedges.get(v.id()) != null){
              for (WEdge<VT> temp : vertedges.get(v.id())) {
                  if (temp.end() == u)
                      a = 1;
                      vertedges.get(v.id()).remove(temp);
              }
          }
          int b = 0;
          if(vertedges.get(u.id()) != null){
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
      if(vertedges.get(v.id()) != null ){
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
          nbs.add(temp.end());
      }
      return nbs;
  }
}
