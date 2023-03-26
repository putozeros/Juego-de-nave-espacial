package gameObjects;

import graficos.Assets;
import math.Vector2D;
import states.GameState;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import gameObjects.Mensaje;

public abstract class MovingObject extends GameObject{

    protected Vector2D speed;
    protected AffineTransform at;
    protected double angle;
    protected double maxSpeed;
    protected int ancho;
    protected int alto;
    protected GameState gameState;
    protected boolean Dead;
    protected int vitalidad;
    private ArrayList<Mensaje> mensajes = new ArrayList<Mensaje>();

    public MovingObject(Vector2D posicion, Vector2D speed, double maxSpeed, BufferedImage texture, GameState gameState) {
        super(posicion, texture);
        this.speed = speed;
        this.maxSpeed= maxSpeed;
        this.gameState = gameState;
        ancho = texture.getWidth();
        alto = texture.getHeight();
        angle = 0;
        Dead = false;
    }
    public MovingObject(Vector2D posicion, Vector2D speed, double maxSpeed, BufferedImage texture, GameState gameState, int vitalidad) {
        super(posicion, texture);
        this.speed = speed;
        this.maxSpeed= maxSpeed;
        this.gameState = gameState;
        ancho = texture.getWidth();
        alto = texture.getHeight();
        angle = 0;
        Dead = false;
        this.vitalidad = vitalidad;
    }


    protected void collidesWith(){

        ArrayList<MovingObject> movingObjects = gameState.getMovingObjects();

        for(int i = 0; i<movingObjects.size();i++){

            MovingObject m = movingObjects.get(i);

            if(m.equals(this)){
                continue;
            }
            if(m instanceof Asteroide && this instanceof Ufo){
                continue;
            }
            double distancia = m.getCenter().substract(getCenter()).getMagnitud();

            if(distancia < m.ancho/2 + ancho/2 && movingObjects.contains(this)){
                objectColision(m,this);
            }
        }
    }

    private void objectColision(MovingObject a, MovingObject b){

        Player p = null;

        if(a instanceof Player)
            p = (Player)a;
        else if(b instanceof Player)
            p = (Player)b;

        if(p != null && p.isSpawning())
            return;

        if(a instanceof Asteroide && b instanceof Asteroide)
            return;

        if(!(a instanceof PowerUps || b instanceof PowerUps)){
            a.Destruir();
            b.Destruir();
            return;
        }

        if(b instanceof Player && a instanceof MonedaPowerUps){
            ((MonedaPowerUps)a).hacerAccion();
            a.Destruir();
        }

        if (a instanceof Player && b instanceof PowerUps) {
                ((PowerUps)b).ejecutarAccion();
                b.Destruir();
        }
    }

    public void damage(int danio){
        System.out.println(""+vitalidad);
        System.out.println(""+danio);
        vitalidad -= danio;
        System.out.println(""+vitalidad);
        if(vitalidad <=0){
            Destruir();
        }
    }

    protected void Destruir(){
        gameState.getMovingObjects().remove(this);
    }

    protected Vector2D getCenter(){
        return new Vector2D(posicion.getX() + ancho/2,posicion.getY() + alto/2);
    }
    public boolean isDead(){
        return Dead;
    }
}