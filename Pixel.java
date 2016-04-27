public class Pixel {
    private int locx;
    private int locy;
    private int nothing;
    private int red;
    private int green;
    private int blue;

    public Pixel(){
        locx = -1;
        locy = -1;
        red = -1;
        blue = -1;
        green = -1;
    }

    public Pixel(int x, int y, int r, int g, int b){
        locx = x;
        locy = y;
        red = r;
        green = g;
        blue = b;
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
