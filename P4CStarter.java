import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.List;
import java.util.ArrayList;
/** Program that breaks picture into different parts.*/
final class P4CStarter {
    /** % pixels needed of image to print it.*/
    static final double POINT5 = 0;

    /**For javadocs.*/
    private P4CStarter() {
    }

    /** Convert an image to a graph of Pixels with edges between
     *  north, south, east and west neighboring pixels.
     *  @param image the image to convert
     *  @param pd the distance object for pixels
     *  @return the graph that was created
     */


    static WGraphP4<Pixel> imageToGraph(BufferedImage image, PixelDistance pd) {
        WGraphP4<Pixel> g = new WGraphP4<Pixel>(); //init a graph
        ArrayList<ArrayList<GVertex<Pixel>>> plist = new ArrayList<ArrayList
            <GVertex<Pixel>>>(image.getWidth()); //make lists of list
        for (int i = 0; i < image.getWidth(); i++) { //init all column list
            plist.add(new ArrayList<GVertex<Pixel>>(image.getHeight()));
        }
        // make vertex for each pixel in image
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                Color mycolor = new Color(image.getRGB(i, j));
                Pixel newpixel = new Pixel(i, j, mycolor.getRed(),
                    mycolor.getGreen(), mycolor.getBlue(), mycolor);
                GVertex<Pixel> newv = new GVertex(newpixel, g.nextID());
                g.addVertex(newv);
                plist.get(i).add(newv);
            }
        }
        // add edges between up and down pixels, dont do the last row
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight() - 1; j++) {
                GVertex<Pixel> a1 = plist.get(i).get(j);
                GVertex<Pixel> a2 = plist.get(i).get(j + 1);
                g.addEdge(a1, a2, pd.distance(a1.data(), a2.data()));
            }
        }
        // add edges between left and right pixels, dont do that last col
        for (int i = 0; i < image.getWidth() - 1; i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                GVertex<Pixel> a1 = plist.get(i).get(j);
                GVertex<Pixel> a2 = plist.get(i + 1).get(j);
                g.addEdge(a1, a2, pd.distance(a1.data(), a2.data()));

            }
        }

        return g;

    }


    /** Make an existing image all gray.
      * @param image the input
      * @return the output gray
      */
    private static BufferedImage makegray(BufferedImage image) {
        final int gray = 0x202020;
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                image.setRGB(j, i, gray);
            }
        }
        return image;
    }


    /** Return a list of edges in a minimum spanning forest by
     *  implementing Kruskal's algorithm using fast union/finds.
     *  @param g the graph to segment
     *  @param kvalue the value to use for k in the merge test
     *  @return a list of the edges in the minimum spanning forest
     */

    private static List<WEdge<Pixel>> segmenter(WGraph<Pixel> g,
        double kvalue) {
        WGraphP4<Pixel> newstuff = new WGraphP4();
        List<GVertex<Pixel>> verti = g.allVertices();
        List<WEdge<Pixel>> edges = g.allEdges();
        Partition newPartition = new Partition(verti.size()); 
        PQHeap<WEdge<Pixel>> newQueue = new PQHeap<WEdge<Pixel>>();
//      PriorityQueue<WEdge<Pixel>> Q = new PriorityQueue<WEdge<Pixel>>();
        newQueue.init(edges);

//      for (WEdge<Pixel> temp7 : edges){ 
 //         Q.add(temp7);
 //     }
        for (GVertex<Pixel> temp2 : verti) {
            newstuff.addVertex(temp2);
        }
        // make a max min list that stores the max/min rgb values for a pixel
        ArrayList<MaxMin> mmlist = new ArrayList<MaxMin>(verti.size());
        for (int i = 0; i < verti.size(); i++) { // add to mm to list
            mmlist.add(i, new MaxMin(verti.get(i)));
        }
        while (newQueue.size() > 0) {
            WEdge<Pixel> temp = newQueue.peek();
            newQueue.remove();
            GVertex<Pixel> u = temp.source();
            GVertex<Pixel> v = temp.end();
            int a1 = newPartition.find(u.uniqueid());
            int a2 = newPartition.find(v.uniqueid());
            if (a1 != a2) {
                MaxMin mmu = mmlist.get(a1); // uses partition to get to root
                MaxMin mmv = mmlist.get(a2); // uses partition to get to root
                if ((double) mmu.diffrc(mmv) <= ((double) Math.min(mmu.diffr(),
                    mmv.diffr()) + (kvalue / (double) (mmu.getCount() + mmv.
                    getCount())))) { //if red
                    if ((double) mmu.diffgc(mmv) <= ((double) Math.min(
                        mmu.diffg(),
                        mmv.diffg()) + (kvalue / (double) (mmu.getCount()
                        + mmv.getCount())))) { //if green
                        if (((double) mmu.diffbc(mmv) <= (double)
                            Math.min(mmu.diffb()
                            , mmv.diffb()) + (kvalue / (double) (
                            mmu.getCount() + mmv.getCount())))) { //if blue
                            mmu.combine(mmv);
                            newstuff.addEdge(temp); // add the edge
                            newPartition.union(u.uniqueid(),
                                v.uniqueid()); // union them in partition
                        }
                    }
                }
            }
        }
      //System.out.println(System.currentTimeMillis());

        return newstuff.allEdges();



    }
    /**The main function.
      * @param args the input file name and k
      */
    public static void main(String[] args) {

        try {
          // the line that reads the image file

            BufferedImage image = ImageIO.read(new File(args[0]));
            WGraphP4<Pixel> g = imageToGraph(image, new PixelDistance());
            List<WEdge<Pixel>> res = segmenter(g, Double.parseDouble(args[1]));
            System.out.print("result =  " + res.size() + "\n");
            System.out.print("NSegments =  "
                             + (g.numVerts() - res.size()) + "\n");
            // make new graph that has the edges in a forest
            WGraphP4<Pixel> h = new WGraphP4<Pixel>();
            for (GVertex<Pixel> temp22: g.allVertices()) {
                h.addVertex(temp22);
            }
            for (WEdge<Pixel> tempog : res) {
                h.addEdge(tempog);
            }
            image = makegray(image);

            List<GVertex<Pixel>> og = h.allVertices();
            int[] newarray = new int[og.size()];
            for (int i = 0; i < newarray.length; i++) {
                newarray[i] = 0;
            }
            int z = 0;
            for (GVertex<Pixel> temp : og) {
                if (newarray[temp.uniqueid()] == 0) {
                    List<GVertex<Pixel>> innerlist = h.depthFirst(temp);
                    for (GVertex<Pixel> temp3 : innerlist) {
                        newarray[temp3.uniqueid()] = 1;
                    }
                    if (innerlist.size() > (double) 10 ){//og.size() * POINT5) {
                        z++;
                        for (GVertex<Pixel> i : innerlist) {
                            Pixel d = i.data();
                            image.setRGB(d.getx(), d.gety(), d.getrgb().
                                getRGB());
                        }
                        File f = new File("output" + z + ".png");
                        ImageIO.write(image, "png", f);
                        image = makegray(image);

                    }
                }
            }
            //diff = System.currentTimeMillis();
            //System.out.println("After out " + (diff - a));
            // Print number of pictures made
            System.out.println("Number of Outputs: "+ z);


        } catch (IOException e) {
            System.out.print("Missing File!\n");

            // log the exception
            // re-throw if desired
        }
    }

}

