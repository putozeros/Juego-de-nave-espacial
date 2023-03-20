package math;

public class Vector2D {
    private double x,y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Vector2D(Vector2D v){
        this.x = v.x;
        this.y = v.y;
    }
    public Vector2D() {
        x=0;
        y=0;
    }

    public Vector2D add(Vector2D v){
        return  new Vector2D(x + v.getX(), y + v.getY());
    }

    public Vector2D substract(Vector2D v){
        return  new Vector2D(x - v.getX(), y - v.getY());
    }

    public Vector2D escalar (double value){
        return new Vector2D(x*value,y*value);
    }

    public Vector2D limite(double value){
        if(getMagnitud()>value){
            return this.normalizar().escalar(value);
        }
        return this;
    }

    public Vector2D normalizar(){

        double magnitud = getMagnitud();
        return new Vector2D(x/magnitud,y/magnitud);
    }

    public double getMagnitud(){
        return Math.sqrt(x*x + y*y);
    }
    public Vector2D setDireccion(double angle){

        double magnitud = getMagnitud();
        return new Vector2D(Math.cos(angle)*magnitud,Math.sin(angle)*magnitud);
    }

    public double getAngle(){
        return Math.asin(y/getMagnitud());
    }
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}