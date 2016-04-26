public class pixel {
    private int locx;
    private int locy;
    private int 
    private int red;
    private int green;
    private int blue;

    public pixel(){
        locx = -1;
        locy = -1;
        red = -1;
        blue = -1;
        green = -1;
    }

    public pixel(int x, int y, int r, int g, int b){
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
