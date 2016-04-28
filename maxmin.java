/**
 * Stores the Max RGB for a set, stored in an array
 * in P4CStarter, used to combine the sets.
 *
 */
public class maxmin {
    /** Variable for min red part of pixel. */
    private int redmin = -1;
    /** Variable for min green part of pixel. */
    private int greenmin = -1;
    /** Variable for min blue part of pixel. */
    private int bluemin = -1;
    /** Variable for max red part of pixel. */
    private int redmax = -1;
    /** Variable for max green part of pixel. */
    private int greenmax = -1;
    /** Variable for max blue part of pixel. */
    private int bluemax = -1;
    /** Count for keeping track of combines.*/
    private int count = 1;
    /**
     * Default constructor.
     */
    public maxmin() {
    }
    /**
     * Constructor that takes in a vertex.
     * @param v generic vertex to take in.
     */
    public maxmin(GVertex<Pixel> v) { // init
        this.redmin = v.data().getred();
        this.greenmin = v.data().getgreen();
        this.bluemin = v.data().getblue();
        this.redmax = v.data().getred();
        this.greenmax = v.data().getgreen();
        this.bluemax = v.data().getblue();
    }
    /**
     * Get min red part.
     * @return min red part
     */
    public int getrmin() {
        return this.redmin;
    }
    /**
     * Get min green part.
     * @return min green part
     */
    public int getgmin() {
        return this.greenmin;
    }
    /**
     * Get min blue part.
     * @return min blue part
     */
    public int getbmin() {
        return this.bluemin;
    }
    /**
     * Get max red part.
     * @return max red part
     */
    public int getrmax() {
        return this.redmax;
    }
    /**
     * Get max green part.
     * @return max green part
     */
    public int getgmax() {
        return this.greenmax;
    }
    /**
     * Get max blue part.
     * @return max blue part
     */
    public int getbmax() {
        return this.bluemax;
    }
    /**
     * Set min red part.
     * @param r red component
     */
    public void setrmin(int r) {
        this.redmin = r;
    }
    /**
     * Set min green part.
     * @param r green component
     */
    public void setgmin(int r) {
        this.greenmin = r;
    }
    /**
     * Set min blue part.
     * @param r blue component
     */
    public void setbmin(int r) {
        this.bluemin = r;
    }
    /**
     * Set max red part.
     * @param r red component
     */
    public void setrmax(int r) {
        this.redmax = r;
    }
    /**
     * Set max green part.
     * @param r grenn component
     */
    public void setgmax(int r) {
        this.greenmax = r;
    }
    /**
     * Set max blue part.
     * @param r blue component
     */
    public void setbmax(int r) {
        this.bluemax = r;
    }
    /**
     * Get pixels in sets.
     * @return int of count
     */
    public int getCount() {
        return this.count;
    }
    /**
     * Set pixels in sets.
     * @param r 
     */
    public void setCount(int r) {
        this.count = r;
    }
    /**
     * If greater/equal, add all to it and increase count.
     * @param j a maxmin to combine.
     */
    public void combine(maxmin j) {
        if (this.count >= j.getCount()) { 
            this.redmin = Math.min(this.redmin, j.getrmin());
            this.greenmin = Math.min(this.greenmin, j.getgmin());
            this.bluemin = Math.min(this.bluemin, j.getbmin());
            this.redmax = Math.max(this.redmax, j.getrmax());
            this.greenmax = Math.max(this.greenmax, j.getgmax());
            this.bluemax = Math.max(this.bluemax, j.getbmax());
            this.count = this.count + j.getCount();
        } else {
            j.setrmin(Math.min(this.redmin, j.getrmin())); // edit j
            j.setgmin(Math.min(this.greenmin, j.getgmin()));
            j.setbmin(Math.min(this.bluemin, j.getbmin()));
            j.setrmax(Math.max(this.redmax, j.getrmax()));
            j.setgmax(Math.max(this.greenmax, j.getgmax()));
            j.setbmax(Math.max(this.bluemax, j.getbmax()));
            j.setCount(j.getCount() + this.count);
        }

    }
    /**
     * Diff in max and min components of red.
     * @return the difference
     */
    public int diffr() {
        int red = this.redmax - this.redmin;
        return red;
    }
    /**
     * Diff in max and min components of green.
     * @return the difference
     */
    public int diffg() {
        int green = this.greenmax - this.greenmin;
        return green;
    }
   /**
    * Diff in max and min components of blue.
    * @return the difference
    */
    public  int diffb() {
        int blue = this.bluemax - this.bluemin;
        return blue;
    }
    /**
     * Calculate the diff for each color if they
     * would be combined with something else for red.
     * @param j diff to calculate for color
     * @return the difference.
     */
    public int diffrc(maxmin j) {
        int temprmax = this.redmax;
        int temprmin = this.redmin;
        if (temprmax < j.getrmax()) {
            temprmax = j.getrmax();
        }
        if (temprmin > j.getrmin()) {
            temprmin = j.getrmin();
        }
        int red = temprmax - temprmin;
        return red;
    }
    /**
     * Calculate the diff for each color if they
     * would be combined with something else for green.
     * @param j diff to calculate for color
     * @return the difference.
     */    
    public int diffgc(maxmin j) {
        int tempgmax = this.greenmax;
        int tempgmin = this.greenmin;
        if (tempgmax < j.getgmax()) {
            tempgmax = j.getgmax();
        }
        if (tempgmin > j.getgmin()) {
            tempgmin = j.getgmin();
        }
        int green = tempgmax - tempgmin;
        return green;
    }
    /**
     * Calculate the diff for each color if they
     * would be combined with something else for blue.
     * @param j diff to calculate for color
     * @return the difference.
     */
    public int diffbc(maxmin j) {
        int tempbmax = this.bluemax;
        int tempbmin = this.bluemin;
        if (tempbmax < j.getbmax()) {
            tempbmax = j.getbmax();
        }
        if (tempbmin > j.getbmin()) {
            tempbmin = j.getbmin();
        }
        int blue = tempbmax - tempbmin;
        return blue;
    }
}
