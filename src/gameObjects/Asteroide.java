package gameObjects;

import graficos.Assets;
import graficos.Sonido;
import math.Vector2D;
import states.GameState;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Asteroide extends MovingObject{

    private Size size;

    public Asteroide(Vector2D posicion, Vector2D speed, double maxSpeed, BufferedImage texture, GameState gameState, Size size) {
        super(posicion, speed, maxSpeed, texture, gameState);
        this.size = size;
        this.speed = speed.escalar(maxSpeed);
    }

    @Override
    public void actualizar(float dt) {

        Vector2D playerPos = new Vector2D(gameState.getPlayer().getCenter());
        int distanciaAJugador = (int) playerPos.substract(getCenter()).getMagnitud();

        if(distanciaAJugador < 150/2 + ancho/2){
            if(gameState.getPlayer().isShieldOn()){
                Vector2D fleeForce = fleeForce();
                speed = speed.add(fleeForce);
            }
        }

        if(speed.getMagnitud() >= this.maxSpeed){
            Vector2D reversedSpeed = new Vector2D(-speed.getX(), -speed.getY());
            speed = speed.add(reversedSpeed.normalizar().escalar(0.01f));
        }
        speed = speed.limite(6.0);

        posicion = posicion.add(speed);
        if(posicion.getX() > Constantes.WIDTH){
            posicion.setX(-ancho);
        }
        if(posicion.getY() >Constantes.HEIGHT){
            posicion.setY(-alto);
        }
        if (posicion.getX()<-ancho){
            posicion.setX(Constantes.WIDTH);
        }
        if (posicion.getY()<-alto){
            posicion.setY(Constantes.HEIGHT);
        }
        angle += Constantes.DELTAANGLE/2;
    }

    private Vector2D fleeForce(){
        Vector2D desiredSpeed = gameState.getPlayer().getCenter().substract(getCenter());
        desiredSpeed = (desiredSpeed.normalizar()).escalar(6.0);
        Vector2D v = new Vector2D(speed);
        return v.substract(desiredSpeed);
    }

    @Override
    public void Destruir(){
        Sonido sonido = new Sonido(Assets.explosion);
        sonido.play();
        gameState.dividirAsteroide(this);
        gameState.playExplosion(new Vector2D(this.getCenter()));
        gameState.addpuntuacion(Constantes.ASTEROIDE_SCORE, posicion);
        super.Destruir();
    }

    @Override
    public void dibujar(Graphics graphics) {

        Graphics2D g2d = (Graphics2D)graphics;

        at = AffineTransform.getTranslateInstance(posicion.getX(),posicion.getY());

        at.rotate(angle, ancho/2, alto/2);

        g2d.drawImage(texture, at, null);
    }

    public Size getSize() {
        return size;
    }
}
