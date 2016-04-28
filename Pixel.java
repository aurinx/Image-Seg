import java.awt.Color;
/**
 * Pixel class to use with the graphs.
 *
 */
public class Pixel {
    /** x axis location. */
    private int locx;
    /** y axis location. */
    private int locy;
    /** nothing color. */
    private int nothing;
    /** red color. */
    private int red;
    /** green color. */
    private int green;
    /** blue color. */
    private int blue;
    /** Color class of color. */
    private Color color1;
    /**
     * Pixel constructor, default.
     */
    public Pixel() {
        this.locx = -1;
        this.locy = -1;
        this.red = -1;
        this.blue = -1;
        this.green = -1;
    }
    /**
     * Pixel constructor with color and loc parameters.
     * @param x x axis
     * @param y y axis
     * @param r red
     * @param g green
     * @param b blue
     * @param mycolor other color
     */
    public Pixel(int x, int y, int r, int g, int b, Color mycolor) {
        this.locx = x;
        this.locy = y;
        this.red = r;
        this.green = g;
        this.blue = b;
        this.color1 = mycolor;
    }
    /**
     * Return the color.
     * @return the color
     */
    public Color getrgb() {
        return this.color1;
    }
    /**
     * Return x axis.
     * @return the x axis
     */
    public int getx() {
        return this.locx;
    }
    /**
     * Return y axis.
     * @return the y axis
     */
    public int gety() {
        return this.locy;
    }
    /**
     * Return red component.
     * @return the red component
     */
    public int getred() {
        return this.red;
    }
    /**
     * Return the green component.
     * @return the green component
     */
    public int getgreen() {
        return this.green;
    }
    /**
     * Return the blue component.
     * @return the blue component
     */
    public int getblue() {
        return this.blue;
    }
}
