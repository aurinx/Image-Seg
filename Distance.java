public class Distance{
        public double dist(Pixel one, Pixel two){
            int sumred = (two.getred() - one.getred())*(two.getred() - one.getred());
            int sumgreen = (two.getgreen() - one.getgreen())*(two.getgreen() - one.getgreen());
            int sumblue = (two.getblue() - one.getblue())*(two.getblue() - one.getblue());
            double sum  = sumred + sumgreen + sumblue;
            return sum;
        }
}
