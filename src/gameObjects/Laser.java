package gameObjects;

import math.Vector2D;
import states.GameState;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Laser extends MovingObject{
    private int potencia;
    public static boolean bonusactive;
    private static long timer;
    private static int damagebonus;

    public Laser(Vector2D posicion, Vector2D speed, double maxSpeed, double angle, BufferedImage texture, GameState gameState, int potencia) {
        super(posicion, speed, maxSpeed, texture, gameState);
        this.angle = angle;
        this.speed = speed.escalar(maxSpeed+10);
        this.potencia = potencia;
    }

    @Override
    protected void collidesWith(){
        ArrayList<MovingObject> movingObjects = gameState.getMovingObjects();
        for(int i=0;i<movingObjects.size();i++){
            MovingObject m = movingObjects.get(i);
            if(m instanceof Asteroide && colisionaCon(m)){if(!bonusactive){
                m.damage(potencia);
            }else{
                m.damage(potencia + damagebonus);
            }
                Destruir();
                break;
            }
            if(m instanceof Ufo && colisionaCon(m)){if(!bonusactive){
                m.damage(potencia);
            }else{
                m.damage(potencia + damagebonus);
            }
                Destruir();
                break;
            }
            if(m instanceof Player && colisionaCon(m)){
                if(!bonusactive){
                    m.damage(potencia);
                }else{
                    m.damage(potencia + damagebonus);
                }
                Destruir();
                break;
            }
        }
    }

    public static void setDamageBonus(int bonus){
        damagebonus = bonus;
        bonusactive = true;
        timer = System.currentTimeMillis();
    }

    public boolean colisionaCon(MovingObject m){
        double distancia = m.getCenter().substract(getCenter()).getMagnitud();
        return distancia < m.ancho/2 + ancho/2;
    }
    @Override
    public void actualizar(float dt) {

        posicion = posicion.add(speed);
        if(posicion.getX() <-30 || posicion.getX() >Constantes.WIDTH+30 ||
                posicion.getY() < -30 || posicion.getY() > Constantes.HEIGHT+30){
            Destruir();
        }
        collidesWith();
        if(bonusactive && System.currentTimeMillis() - timer > 12000){
            bonusactive = false;
            timer = 0;
        }
    }

    @Override
    public void dibujar(Graphics graphics) {
        Graphics2D g2d = (Graphics2D)graphics;

        at = AffineTransform.getTranslateInstance(posicion.getX(),posicion.getY());

        at.rotate(angle);

        g2d.drawImage(texture,at,null);
    }

    @Override
    public Vector2D getCenter(){
        return new Vector2D(posicion.getX() + ancho/2,posicion.getY() + ancho/2);
    }
}
