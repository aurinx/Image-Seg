/**Function for pixel distance.
  * @param <T> an input object
  */
public class PixelDistance<T> implements Distance<T> {
    @Override
    /** Distance function.
      * @param p first pixel
      * @param p2 second pixel
      * @return the sum squared
      */
    public double distance(Object p, Object p2) {
        if (p instanceof Pixel && p2 instanceof Pixel) {
            Pixel one = (Pixel) p;
            Pixel two = (Pixel) p2;
            int sumred = (two.getred() - one.getred())
                * (two.getred() - one.getred());
            int sumgreen = (two.getgreen() - one.getgreen())
                * (two.getgreen() - one.getgreen());
            int sumblue = (two.getblue() - one.getblue())
                * (two.getblue() - one.getblue());
            double sum  = sumred + sumgreen + sumblue;
            return sum;
        }
        return 0;
    }
}
