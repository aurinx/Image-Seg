import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;


public class P4CStarter{

    /** Convert an image to a graph of Pixels with edges between
     *  north, south, east and west neighboring pixels.
     *  @param image the image to convert
     *  @param pd the distance object for pixels
     *  @return the graph that was created
     */
    static WGraphP4<Pixel> imageToGraph(BufferedImage image, PixelDistance pd) {
        WGraphP4<Pixel> g = new WGraphP4<Pixel>();
        ArrayList<GVertex<Pixel>> plist= new ArrayList<GVertex<Pixel>>(image.getWidth()*image.getHeight());
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                Color mycolor = new Color(image.getRGB(i,j));
                Pixel newpixel = new Pixel(i, j,mycolor.getRed(),mycolor.getGreen(),mycolor.getBlue(), mycolor); 
                GVertex<Pixel> newv = new GVertex(newpixel, g.nextID());
                g.addVertex(newv);
                plist.add(image.getHeight()*i + j,newv);
            }
        }
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight() - 1 ; j++) {
                int a = image.getHeight()*i + j;
                g.addEdge(plist.get(a),plist.get(a+1), pd.distance(plist.get(a).data(), plist.get(a+1).data()));
            }
        }
        for (int i = 0; i < image.getWidth() - 1; i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int a = image.getHeight()*i + j;
                g.addEdge(plist.get(a),plist.get(a+image.getHeight()), pd.distance(plist.get(a).data(), plist.get(a+image.getHeight()).data()));
            }
        }
        return g;

    }


    /** Return a list of edges in a minimum spanning forest by
     *  implementing Kruskal's algorithm using fast union/finds.
     *  @param g the graph to segment
     *  @param kvalue the value to use for k in the merge test
     *  @return a list of the edges in the minimum spanning forest
     */

    static List<WEdge<Pixel>> segmenter(WGraph<Pixel> g, double K) { // normal kruskals but adds that fuckign equation
      WGraphP4<Pixel> newstuff = new WGraphP4();
      List<GVertex<Pixel>> verti = g.allVertices();
      List<WEdge<Pixel>> edges = g.allEdges();
      Partition P = new Partition(verti.size()); 
      //PQHeap<WEdge<Pixel>> Q = new PQHeap<WEdge<Pixel>>();
      PriorityQueue<WEdge<Pixel>> Q = new PriorityQueue<WEdge<Pixel>>();
//      Q.init(edges);
      for (WEdge<Pixel> temp7 : edges){ 
          Q.add(temp7);
      }
      for (GVertex<Pixel> temp2 : verti){
          newstuff.addVertex(temp2);
      }
      ArrayList<maxmin> mmlist = new ArrayList<maxmin>(); //max an arraylist of maxmin class according to unique id
      for (int i = 0; i < verti.size(); i++){ // add to mm to list
          mmlist.add(i, new maxmin(verti.get(i)));
      }
      while (Q.size() > 0) {
          WEdge<Pixel> temp = Q.peek();
          Q.poll();
          GVertex<Pixel> u = temp.source();
          GVertex<Pixel> v = temp.end();
          if(P.find(u.uniqueid()) != P.find(v.uniqueid())){
              maxmin mmu = mmlist.get(P.find(u.uniqueid())); // uses partition to get to root
              maxmin mmv = mmlist.get(P.find(v.uniqueid())); // uses partition to get to root
              if((double)mmu.diffrc(mmv) <= (Math.min(mmu.diffr(), mmv.diffr()) + (double)K/(double)(mmu.getCount() + mmv.getCount()))){//if red
                  if((double)mmu.diffgc(mmv) <= (Math.min(mmu.diffg(), mmv.diffg()) + (double)K/(double)(mmu.getCount() + mmv.getCount()))){//if green
                      if(((double)mmu.diffbc(mmv) <= Math.min(mmu.diffb(), mmv.diffb()) + (double)K/(double)(mmu.getCount() + mmv.getCount()))){//if blue
                          mmlist.get(P.find(u.uniqueid())).combine(mmlist.get(P.find(v.uniqueid())));//unions the two but really only edits the bigger root
                          newstuff.addEdge(temp);
                          P.union(u.uniqueid(),v.uniqueid()); // union them in partition
                      }
                  }
              }

          }
      }
      return newstuff.allEdges();



    }

    public static void main(String[] args) {

        final int gray = 0x202020;

        try {
          // the line that reads the image file

            BufferedImage image = ImageIO.read(new File(args[0]));
            WGraphP4<Pixel> g = imageToGraph(image, new PixelDistance());
            List<WEdge<Pixel>> res = segmenter(g, Double.parseDouble(args[1]));

            System.out.print("result =  " + res.size() + "\n");
            System.out.print("NSegments =  "
                             + (g.numVerts() - res.size()) + "\n");

            // make a background image to put a segment into
            for (int i = 0; i < image.getHeight(); i++) {
                for (int j = 0; j < image.getWidth(); j++) {
                    image.setRGB(j, i, gray);
                }
            }
            WGraphP4<Pixel> h = new WGraphP4<Pixel>();
            for (WEdge<Pixel> tempog : res){
                h.addEdge(tempog);
            }
            List<GVertex<Pixel>> og = h.allVertices();
            List<GVertex<Pixel>> og1 = h.allVertices();
            int z = 0;
            for(GVertex<Pixel> temp : og){
                if(og1.contains(temp)){
                   z++;
                   List<GVertex<Pixel>> innerlist = h.depthFirst(temp);
                   for(GVertex<Pixel> temp3 : innerlist){
                      og1.remove(temp3);
                   }
                   for(GVertex<Pixel> i : innerlist) {
                       Pixel d = i.data();
                       image.setRGB(d.getx(), d.gety(), d.getrgb().getRGB()); // make it rgb
                   }
                   File f = new File("output" + z +".png");
                   ImageIO.write(image,"png",f);
                 }
            }
            // After you have a spanning tree connected component x, 
            // you can generate an output image like this:
            // You'll need to do that for each connected component,
            // writing each one to a different file, clearing the
            // image buffer first

        } catch (IOException e) {
            System.out.print("Missing File!\n");

            // log the exception
            // re-throw if desired
        }
    }

}
