package gameObjects;

import math.Vector2D;
import states.GameState;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Laser extends MovingObject{
    public Laser(Vector2D posicion, Vector2D speed, double maxSpeed, double angle, BufferedImage texture, GameState gameState) {
        super(posicion, speed, maxSpeed, texture, gameState);
        this.angle = angle;
        this.speed = speed.escalar(maxSpeed+10);
    }

    @Override
    public void actualizar(float dt) {
        posicion = posicion.add(speed);
        if(posicion.getX() <-30 || posicion.getX() >Constantes.WIDTH+30 ||
                posicion.getY() < -30 || posicion.getY() > Constantes.HEIGHT+30){
            Destruir();
        }
        collidesWith();
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
