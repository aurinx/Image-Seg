import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.List;
import java.util.ArrayList;


public class P4CStarter extends Distance{

     static int K = 10;
    /** Convert an image to a graph of Pixels with edges between
     *  north, south, east and west neighboring pixels.
     *  @param image the image to convert
     *  @param pd the distance object for pixels
     *  @return the graph that was created
     */
    static WGraph<Pixel> imageToGraph(BufferedImage image, Distance pd) {
        WGraphP4<Pixel> g = new WGraphP4<Pixel>();
        GVertex<Pixel>[][] parray= new GVertex<Pixel>[image.getWidth()][image.getHeight()];
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                Color mycolor = new Color(img.getRGB(i,j));
                Pixel newpixel = new Pixel(i, j,mycolor.getRed(),mycolor.getGreen(),mycolor.getBlue()); 
                GVertex<Pixel> newv = new GVertex(newpixel, g.nextID());
                g.addVertex(newv);
                parray[i][j] = newv;
            }
        }
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight() - 1 ; j++) {
                g.addEdge(parray[i][j], parray[i][j+1], pd.distance(parray[i][j].data(), parray[i][j+1].data()));
            }
        }
        for (int i = 0; i < image.getWidth() - 1; i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                g.addEdge(parray[i][j], parray[i+1][j], pd.distance(parray[i][j].data(), parray[i+1][j].data()));
            }
        }

    }


    /** Return a list of edges in a minimum spanning forest by
     *  implementing Kruskal's algorithm using fast union/finds.
     *  @param g the graph to segment
     *  @param kvalue the value to use for k in the merge test
     *  @return a list of the edges in the minimum spanning forest
     */

    static List<WEdge<Pixel>> segmenter(WGraph<Pixel> g, double kvalue) { // normal kruskals but adds that fuckign equation
      WGraphP4<Pixel> newstuff = new WGraphP4();
      List<GVertex<Pixel>> verti = g.allVertices();
      List<WEdge<Pixel>> edges = g.allEdges();
      Partition P = new Partition(verti.size()); 
      PQHeap<WEdge<Pixel>> Q = new PQHeap<WEdge<Pixel>>();
      Q.init(edges);
      ArrayList<maxmin> mmlist = new ArrayList<maxmin>(); //max an arraylist of maxmin class according to unique id
      for (int i = 0; i < verti.size(); i++){ // add to mm to list
          mmlist.add(i, new maxmin(verti.get(i)));
      }
      while (Q.size() > 0) {
          WEdge<Pixel> temp = Q.peek();
          Q.remove();
          GVertex<Pixel> u = temp.source();
          GVertex<Pixel> v = temp.end();
          if(P.find(u.uniqueid()) != P.find(v.uniqueid())){
              newstuff.addEdge(temp);
              maxmin mmu = mmlist.get(P.find(u.uniqueid())); // uses partition to get to root
              maxmin mmv = mmlist.get(P.find(v.uniqueid())); // uses partition to get to root
              if(mmu.diffrc(mmv) <= Math.min(mmu.diffr(), mmv.diffr()) + K/(mmu.getCount() + mmv.getCount())){//if red
                  if(mmu.diffgc(mmv) <= Math.min(mmu.diffg(), mmv.diffg()) + K/(mmu.getCount() + mmv.getCount())){//if green
                      if(mmu.diffbc(mmv) <= Math.min(mmu.diffb(), mmv.diffb()) + K/(mmu.getCount() + mmv.getCount())){//if blue
                          mmlist.get(P.find(u.uniqueid())).combine(mmlist.get(P.find(v.uniqueid())));//unions the two but really only edits the bigger root
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
            WGraph<Pixel> g = imageToGraph(image, new PixelDistance());
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

            // After you have a spanning tree connected component x, 
            // you can generate an output image like this:
            for (GVertex<Pixel> i: x)  {
                Pixel d = i.data();
                image.setRGB(d.col(), d.row(), d.value());
            }

            File f = new File("output.png");
            ImageIO.write(image, "png", f);

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
