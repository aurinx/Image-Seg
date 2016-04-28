import java.util.*;
public class maxmin { // this stores max rgb for a set. this is stored in an array in p4cstarter, so you can combine to form sets
   private int redmin =-1;
   private int greenmin = -1;
   private int bluemin = -1;
   private int redmax =-1;
   private int greenmax = -1;
   private int bluemax = -1;
   private int count = 1;

   public maxmin(){
   }

   public maxmin(GVertex<Pixel> v){ // init
       redmin = v.data().getred();
       greenmin = v.data().getgreen();
       bluemin = v.data().getblue();
       redmax = v.data().getred();
       greenmax = v.data().getgreen();
       bluemax = v.data().getblue();
   }
   public int getrmin(){
       return redmin;
   }
   public int getgmin(){
       return greenmin;
   }
   public int getbmin(){
       return bluemin;
   }
   public int getrmax(){
       return redmax;
   }
   public int getgmax(){
       return greenmax;
   }
   public int getbmax(){
       return bluemax;
   }
   public void setrmin(int r){
       this.redmin = r;
   }
   public void setgmin(int r){
       this.greenmin = r;
   }
   public void setbmin(int r){
       this.bluemin = r;
   }
   public void setrmax(int r){
       this.redmax = r;
   }
   public void setgmax(int r){
       this.greenmax = r;
   }
   public void setbmax(int r){
       this.bluemax = r;
   }
   public int getCount(){
       return count;
   }
   public void setCount(int r){
       this.count = r;
   }
   public void combine(maxmin j){
       if(this.count >= j.getCount()){ // if this is greater/equal, add everything to ti and increase count
           this.redmin = Math.min(this.redmin, j.getrmin());
           this.greenmin = Math.min(this.greenmin, j.getgmin());
           this.bluemin = Math.min(this.bluemin, j.getbmin());
           this.redmax = Math.max(this.redmax, j.getrmax());
           this.greenmax = Math.max(this.greenmax, j.getgmax());
           this.bluemax = Math.max(this.bluemax, j.getbmax());
           this.count = count + j.getCount();
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
   public int diffr(){
       int red = this.redmax - this.redmin;
       return red;
   }
   public int diffg(){
       int green = this.greenmax - this.greenmin;
       return green;
   }
   public  int diffb(){
       int blue = this.bluemax - this.bluemin;
       return blue;
   }
   public int diffrc(maxmin j){ // calc diff for each color if they would be combined with something else
       int temprmax = this.redmax;
       int temprmin = this.redmin;
       if(temprmax < j.getrmax()){
           temprmax = j.getrmax();
       }
       if(temprmin > j.getrmin()){
           temprmin = j.getrmin();
       }
       int red = temprmax - temprmin;
       return red;
   }
   public int diffgc(maxmin j){
       int tempgmax = this.greenmax;
       int tempgmin = this.greenmin;
       if(tempgmax < j.getgmax()){
           tempgmax = j.getgmax();
       }
       if(tempgmin > j.getgmin()){
           tempgmin = j.getgmin();
       }
       int green = tempgmax - tempgmin;
       return green;
   }
   public int diffbc(maxmin j){
       int tempbmax = this.bluemax;
       int tempbmin = this.bluemin;
       if(tempbmax < j.getbmax()){
           tempbmax = j.getbmax();
       }
       if(tempbmin > j.getbmin()){
           tempbmin = j.getbmin();
       }
       int blue = tempbmax - tempbmin;
       return blue;
   }
}
