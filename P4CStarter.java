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
    static WGraphP4<Pixel> imageToGraph(BufferedImage image, PixelDistance pd) { // input is image and istance function
        WGraphP4<Pixel> g = new WGraphP4<Pixel>();//init a graph
        ArrayList<ArrayList<GVertex<Pixel>>> plist= new ArrayList<ArrayList<GVertex<Pixel>>>(image.getWidth()); //make lists of list to represent 2d array . width is first array
        for (int i =0; i < image.getWidth(); i++) { // initialize all columnn list
            plist.add(new ArrayList<GVertex<Pixel>>(image.getHeight()));
        }
        System.out.println("Start: " + System.currentTimeMillis());
        for (int i = 0; i < image.getWidth(); i++) { // make vertex with pixel for each pixel in image
            for (int j = 0; j < image.getHeight(); j++) {
                Color mycolor = new Color(image.getRGB(i,j));
                Pixel newpixel = new Pixel(i, j,mycolor.getRed(),mycolor.getGreen(),mycolor.getBlue(), mycolor); 
                GVertex<Pixel> newv = new GVertex(newpixel, g.nextID());
                g.addVertex(newv);
                plist.get(i).add(newv);
            }
        }
        System.out.println("Start: " + System.currentTimeMillis());

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight() - 1 ; j++) {
                GVertex<Pixel> a1 = plist.get(i).get(j);
                GVertex<Pixel> a2 = plist.get(i).get(j+1);
                g.addEdge(a1,a2,pd.distance(a1.data(), a2.data()));
            }
        }
        System.out.println("Start: " + System.currentTimeMillis());

        for (int i = 0; i < image.getWidth() - 1; i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                GVertex<Pixel> a1 = plist.get(i).get(j);
                GVertex<Pixel> a2 = plist.get(i+1).get(j);
                g.addEdge(a1,a2,pd.distance(a1.data(), a2.data()));

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
//      PQHeap<WEdge<Pixel>> Q = new PQHeap<WEdge<Pixel>>();
      PriorityQueue<WEdge<Pixel>> Q = new PriorityQueue<WEdge<Pixel>>();
//      Q.init(edges);

      for (WEdge<Pixel> temp7 : edges){ 
          Q.add(temp7);
      }

      for (GVertex<Pixel> temp2 : verti){
          newstuff.addVertex(temp2);
      }

      ArrayList<maxmin> mmlist = new ArrayList<maxmin>(verti.size()); //max an arraylist of maxmin class according to unique id
      for (int i = 0; i < verti.size(); i++){ // add to mm to list
          mmlist.add(i, new maxmin(verti.get(i)));
      }
      System.out.println(System.currentTimeMillis());
      while (Q.size() > 0) {
          WEdge<Pixel> temp = Q.peek();
          Q.poll();
          GVertex<Pixel> u = temp.source();
          GVertex<Pixel> v = temp.end();
          int a1 = P.find(u.uniqueid());
          int a2 = P.find(v.uniqueid());
          if(a1 != a2){
              maxmin mmu = mmlist.get(a1); // uses partition to get to root
              maxmin mmv = mmlist.get(a2); // uses partition to get to root
              if((double)mmu.diffrc(mmv) <= ((double)Math.min(mmu.diffr(), mmv.diffr()) + (K/(double)(mmu.getCount() + mmv.getCount())))){//if red
                  if((double)mmu.diffgc(mmv) <= ((double)Math.min(mmu.diffg(), mmv.diffg()) + (K/(double)(mmu.getCount() + mmv.getCount())))){//if green
                      if(((double)mmu.diffbc(mmv) <= (double)Math.min(mmu.diffb(), mmv.diffb()) + (K/(double)(mmu.getCount() + mmv.getCount())))){//if blue
                          mmu.combine(mmv);//unions the two but really only edits the bigger root
                          newstuff.addEdge(temp);

                          P.union(u.uniqueid(),v.uniqueid()); // union them in partition
                      }
                  }
              }

          }
      }
      System.out.println(System.currentTimeMillis());

      return newstuff.allEdges();



    }

    public static void main(String[] args) {
    long StartTime = System.currentTimeMillis();
        final int gray = 0x202020;

        try {
          // the line that reads the image file
            long diff = System.currentTimeMillis();
            long a;

            BufferedImage image = ImageIO.read(new File(args[0]));
            WGraphP4<Pixel> g = imageToGraph(image, new PixelDistance());
            a = System.currentTimeMillis();
            System.out.println("After image " + (a - diff));
            List<WEdge<Pixel>> res = segmenter(g, Double.parseDouble(args[1]));
            diff = System.currentTimeMillis();
            System.out.println("After res " + (diff - a));
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
            for(GVertex<Pixel> temp22: g.allVertices()){
                h.addVertex(temp22);
            }
            for (WEdge<Pixel> tempog : res){
                h.addEdge(tempog);
            }
            a = System.currentTimeMillis();
            System.out.println("After newgraph " + (a - diff));
            List<GVertex<Pixel>> og = h.allVertices();
            List<GVertex<Pixel>> og1 = h.allVertices();
            int [] newarray = new int [og.size()];
            for (int i = 0; i < newarray.length; i++)
                newarray[i] = 0;
            int z = 0;
            for(GVertex<Pixel> temp : og){
                if(newarray[temp.uniqueid()] == 0){
                   List<GVertex<Pixel>> innerlist = h.depthFirst(temp);
                   for(GVertex<Pixel> temp3 : innerlist){
                      newarray[temp3.uniqueid()] =1;
                   }
                   if(innerlist.size() > 100){
                       z++;
                       for(GVertex<Pixel> i : innerlist) {
                           Pixel d = i.data();
                           image.setRGB(d.getx(), d.gety(), d.getrgb().getRGB()); // make it rgb
                       }
                       File f = new File("output" + z +".png");
                       ImageIO.write(image,"png",f);
                       for (int i = 0; i < image.getHeight(); i++) {
                          for (int j = 0; j < image.getWidth(); j++) {
                          image.setRGB(j, i, gray);
                          }
                       }

                   }
                } else {
                }
            }
            diff = System.currentTimeMillis();
            System.out.println("After out " + (diff - a));
                System.out.println(z);

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
