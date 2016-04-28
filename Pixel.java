import java.awt.Color;

public class Pixel {
    private int locx;
    private int locy;
    private int nothing;
    private int red;
    private int green;
    private int blue;
    private Color color1;

    public Pixel(){
        locx = -1;
        locy = -1;
        red = -1;
        blue = -1;
        green = -1;
    }

    public Pixel(int x, int y, int r, int g, int b, Color mycolor){
        locx = x;
        locy = y;
        red = r;
        green = g;
        blue = b;
        color1 = mycolor;
    }
    public Color getrgb(){
        return color1;
    }
    public int getx(){
        return locx;
    }
    public int gety(){
        return locy;
    }
    public int getred(){
        return red;
    }
    public int getgreen(){
        return green;
    }
    public int getblue(){
        return blue;
    }
}
