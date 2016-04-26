import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.List;
import java.util.ArrayList;


public class P4C {
    private class Distance<T>{

        public double Distance(<Pixel> one, <Pixel> two){
            int sumred = (two.getred() - one.getred())*(two.getred() - one.getred());
            int sumgreen = (two.getgreen() - one.getgreen())*(two.getgreen() - one.getgreen());
            int sumblue = (two.getblue() - one.getblue())*(two.getblue() - one.getblue());
            double sum  = sumred + sumgreen + sumblue;
            return sum;
        }
     }
    /** Convert an image to a graph of Pixels with edges between
     *  north, south, east and west neighboring pixels.
     *  @param image the image to convert
     *  @param pd the distance object for pixels
     *  @return the graph that was created
     */
    static WGraph<Pixel> imageToGraph(BufferedImage image, Distance<Pixel> pd) {
        WGraphP4<Pixel> g = new WGraphP4<Pixel>();
        GVertex<Pixel>[][] parray= new GVertex<Pixel>[image.getWidth()][image.getHeight()];
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getheight(); j++) {
                Color mycolor = new Color(img.getRGB(i,j));
                Pixel newpixel = new Pixel(i, j,mycolor.getRed(),mycolor.getGreen(),mycolor.getBlue()); 
                GVertex<Pixel> newv = new GVertex(newpixel, g.nextid());
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

    static List<WEdge<Pixel>> segmenter(WGraph<Pixel> g, double kvalue) {
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
