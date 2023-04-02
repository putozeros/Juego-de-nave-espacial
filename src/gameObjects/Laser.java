package gameObjects;

import IO.JSONParser;
import math.Vector2D;
import states.GameState;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Laser extends MovingObject{
    private int potencia;
    public static boolean bonusactive;
    private static long timer;
    private static int damagebonus;
    private static int potencia1bonus = 0,potencia2bonus =0,potencia3bonus = 0,potencia4bonus = 0;

    public Laser(Vector2D posicion, Vector2D speed, double maxSpeed, double angle, BufferedImage texture, GameState gameState, int potencia) {
        super(posicion, speed, maxSpeed, texture, gameState);
        this.angle = angle;
        this.speed = speed.escalar(maxSpeed+10);
        this.potencia = potencia + potencia1bonus + potencia2bonus + potencia3bonus + potencia4bonus;
        try {
            if(JSONParser.leerConfiguracion("Potencia 1")){
                potencia1bonus=10;
            }
            if(JSONParser.leerConfiguracion("Potencia 2")){
                potencia2bonus=10;
            }
            if(JSONParser.leerConfiguracion("Potencia 3")){
                potencia3bonus=20;
            }
            if(JSONParser.leerConfiguracion("Potencia 4")){
                potencia4bonus=45;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void setPotencia1Bonus(int potencia1Bonus) {
        Laser.potencia1bonus = potencia1Bonus;
    }
    public static void setPotencia2Bonus(int potencia2Bonus) {
        Laser.potencia2bonus = potencia2Bonus;
    }
    public static void setPotencia3Bonus(int potencia3Bonus) {
        Laser.potencia3bonus = potencia3Bonus;
    }
    public static void setPotencia4Bonus(int potencia4Bonus) {
        Laser.potencia4bonus = potencia4Bonus;
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
            if(m instanceof Limpiador && colisionaCon(m)){
                if(!bonusactive){
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
            if(m instanceof UfoBig && colisionaCon(m)){
                if(!bonusactive){
                    m.damage(potencia);
                }else{
                    m.damage(potencia+damagebonus);
                }
                Destruir();
                break;
            }
            if(m instanceof Player && colisionaCon(m)){
                m.damage(25);
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
